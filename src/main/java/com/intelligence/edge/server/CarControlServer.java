package com.intelligence.edge.server;

import com.alibaba.fastjson.JSONObject;
import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.dao.CarENVDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.pojo.CurrentPostion;
import com.intelligence.edge.pojo.Position;
import com.intelligence.edge.utils.SpringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**

 **/
@Data

@Slf4j(topic = "d.DeviceControlServer")
public class CarControlServer {
    private String carID;
    private int port;
    private ServerSocket server;//tcp
    private Socket socket;
    private Thread socketThread;
    private DataOutputStream out;

    public CarControlServer(String carID, int port) throws IOException {
        this.carID = carID;
        this.port = port;
    }
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    @Autowired
    public CarBasicDataMapper carBasicDataMapper = applicationContext.getBean(CarBasicDataMapper.class);;


    //private CarENVDataMapper carENVDataMapper = applicationContext.getBean(CarENVDataMapper.class);

    public void openConnect() throws IOException {
//        log.info(carID + ":" + port);
        server = new ServerSocket(port);//创建ServerSocket类

        socketThread = new Thread(new Task(socket));
        socketThread.start();
        log.info("开启控制服务端："+carID);
    }

    class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                socket = server.accept();// 等待客户连接
                out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());// 读取客户端传过来信息的DataInputStream
                String regex = "/^id=.*/";
                int flag = 0;
                while (true) {
                    String msg = in.readLine();// 读取来自客户端的信息
                    log.info("收到信息" + msg);
                    log.info(carID + "可连接");
                    CarTempData.carState.put(carID, 1);//表示设备已经在线


                    Position position = new Position(120.349512,30.320546);
                    CarTempData.carPos.put(carID,position); //初始化小车位置

                    //推送消息给客户端
                    String message = carID + "可连接通信";
                    WebSocketServer.sendInfo(message,"client1");
                    CurrentPostion currentPostion = new CurrentPostion(carID, position.getLongitude(), position.getLongitude());
                    String jsonString = JSONObject.toJSONString(currentPostion);
                    WebSocketServer.sendInfo(jsonString,"client1");

                    flag = carBasicDataMapper.setConnectState(carID,1);
                    break;
                }
                if(flag==1) {
                    log.info("设备状态修改完毕");
                }else {
                    log.info("设备状态好像出了点问题");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void control(String instruction) {
        log.info("发送指令：" + instruction);
        try {
            out.write(instruction.getBytes());//将客户端的信息传递给服务器
        } catch (IOException e) {
            log.info("发送指令失败");
        }
    }

    public int destination(Position position){
        String pos = position.toString();
        log.info("发送目的地址：" + pos);
        try {
            out.write(pos.getBytes());
        } catch (IOException e) {
            log.info("发送目的地址失败");
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public void reset() {
        try {
            server.close();
            out.close();
            CarTempData.carState.put(carID, 0);
            openConnect();
            log.info("------！");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("关闭异常！");
        }
    }
}

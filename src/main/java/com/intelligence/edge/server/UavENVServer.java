package com.intelligence.edge.server;

import com.google.gson.Gson;
import com.intelligence.edge.dao.UavENVDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.EnvironmentInfo;
import com.intelligence.edge.pojo.Position;
import com.intelligence.edge.pojo.UavEnvironmentInfo;
import com.intelligence.edge.utils.SpringUtils;
import javafx.application.Application;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @description: 无人机环境数据传输
 **/
@Data
@Slf4j(topic = "u.UavENVServer")

public class UavENVServer {
    private String deviceID;
    private int port;
    private Thread socketThread;
    private DatagramSocket socket;
    private volatile boolean runFlag = true;

    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();

    private UavENVDataMapper uavENVDataMapper = applicationContext.getBean(UavENVDataMapper.class);

    public UavENVServer(String deviceID, int port) {
        this.deviceID = deviceID;
        this.port = port;
    }

    //开启数据接收
    public void start() throws SocketException {

        runFlag = true;
        socket = new DatagramSocket(port);
        socketThread = new Thread(new Task(socket));
        socketThread.start();
    }

    //关闭数据接收
    public void end(){
        socket.close();
        runFlag = false;  //标志位改变
    }

    //多线程  实现Runnable接口
    class Task implements Runnable {

        private DatagramSocket socket;

        public Task(DatagramSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            byte[] buf = new byte[1024];
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                log.info(deviceID + "：无人机环境数据接收端打开");
                while (runFlag) {
                    socket.receive(packet);
                    String strReceive =
                            new String(packet.getData(), 0, packet.getLength()) +
                                    "from" + packet.getAddress().getHostAddress() + ":" + packet.getPort();
                    log.info(deviceID + "收到:" + strReceive);

                    String objStr = new String(packet.getData(), 0, packet.getLength());

                    Gson gson = new Gson();

                    UavEnvironmentInfo uavEnvironmentInfo = gson.fromJson(objStr, UavEnvironmentInfo.class);
                    uavEnvironmentInfo.setDeviceID(deviceID);

                    //save
                    uavENVDataMapper.insertUavENVData(uavEnvironmentInfo);
                    //Position position = new Position(uavEnvironmentInfo.getLongitude(), uavEnvironmentInfo.getLatitude());
                    //TempData？？？
                    log.info("数据:"+ uavEnvironmentInfo + "  已保存！");

                    packet.setLength(buf.length);

                }
            } catch (SocketException se){
                se.printStackTrace();
                return;

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.intelligence.edge.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.thymeleaf.expression.Maps;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import org.apache.commons.lang3.StringUtils;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**

 **/
@ServerEndpoint("/webSocket/{userID}")
@Component
@CrossOrigin
@Data
@Slf4j(topic = "WebSocketServer")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userID*/
    private String userID="";


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息到客户端
     * */
    public static void sendInfo(String message,@PathParam("userID") String userID) throws IOException {
        log.info("发送消息到:"+userID+"，报文:"+message);
        if(StringUtils.isNotBlank(userID)&&webSocketMap.containsKey(userID)){
            webSocketMap.get(userID).sendMessage(message);
        }else{
            log.error("设备："+userID+",不在线！");
        }
    }
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userID") String userID) {
        this.session = session;
        this.userID=userID;
        //userID
        if(webSocketMap.containsKey(userID)){
            webSocketMap.remove(userID);
            webSocketMap.put(userID,this);
            //加入set中
        }else{
            webSocketMap.put(userID,this);
            //加入set中
            addOnlineCount();
            //在线数加1
            System.out.println(userID + "加入webSocket");
        }
        log.info("收到用户"+userID);
        log.info("连接到客户端:"+userID+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:"+userID+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userID)){
            webSocketMap.remove(userID);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userID+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到用户:"+userID+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromuserID",this.userID);
                String tocarID=jsonObject.getString("tocarID");
                //传送给对应tocarID用户的websocket
                if(StringUtils.isNotBlank(tocarID)&&webSocketMap.containsKey(tocarID)){
                    webSocketMap.get(tocarID).sendMessage(jsonObject.toJSONString());
                }else{
                    log.error("请求的carID:"+tocarID+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userID+",原因:"+error.getMessage());
        error.printStackTrace();
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
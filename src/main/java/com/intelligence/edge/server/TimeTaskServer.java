package com.intelligence.edge.server;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @description: 定时器
 * @author: uu
 * @create: 09-22
 **/

/**
@Component
@EnableScheduling
public class TimeTaskServer {
    private static Logger logger = LoggerFactory.getLogger(TimeTaskServer.class);
    @Scheduled(cron = "0/1 * * * * ?")   //每分钟执行一次
    public void test(){
        System.err.println("*********   定时任务执行   **************");
        // CopyOnWriteArraySet<WebSocketServer> webSocketSet =  WebSocketServer.getWebSocketMap();
        int i = 0 ;
        //定时发送消息
        webSocketSet.forEach(c->{
            try {
                c.sendMessage("  定时发送  " + new Date().toLocaleString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.err.println("/n 定时任务完成.......");
    }
}
 **/

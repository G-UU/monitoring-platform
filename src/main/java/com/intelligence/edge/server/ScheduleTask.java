package com.intelligence.edge.server;

import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.dao.CarENVDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @description: 定时任务
 * @author: uu
 * @create: 11-26
 **/
@Component
@EnableScheduling
public class ScheduleTask {

    //private ApplicationContext applicationContext = SpringUtils.getApplicationContext();

    //private CarBasicDataMapper carBasicDataMapper = applicationContext.getBean(CarBasicDataMapper.class);

    //private CarENVDataMapper carENVDataMapper = applicationContext.getBean(CarENVDataMapper.class);

    @Autowired
    CarBasicDataMapper carBasicDataMapper;

    @Autowired
    CarENVDataMapper carENVDataMapper;


    private Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    //检查在线状态
    @Scheduled(fixedRate = 300000)
    public void checkState() throws IOException {
        for(CarBasicData carBasicData : CarTempData.carList){
            String deviceId = carBasicData.getCarID();
            int mapperstate = carBasicDataMapper.getConnectState(deviceId);
            int cachestate = CarTempData.carState.get(deviceId);
            String message = "";
            int flag = 1;
            if((cachestate==mapperstate)||(cachestate==1&&mapperstate==3)){

            }else{
                message = deviceId+": 状态异常";
                WebSocketServer.sendInfo(message,"client1");
                flag = 0;
            }
            if(flag==1){
                WebSocketServer.sendInfo("设备目前状态良好","client1");
            }

        }

    }

    //删除一个月前的环境信息数据
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void cleanEnvData(){
        Date now = new Date();
        String cleanDate = getCleanDate(now,30);
        try{
            int cleanState = carENVDataMapper.cleanByDate(cleanDate);
        }catch (Exception e){
            logger.error("清理30天前的数据失败"+ e.getMessage());
        }

    }

    //获取需要删除的时间
    public static String getCleanDate(Date now, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(calendar.DATE, -days);
        Date delete=calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=sdf.format(delete);
        return dateString;
    }

}

package com.intelligence.edge.service.impl;

import com.intelligence.edge.config.CarConfig;
import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.server.CarENVServer;
import com.intelligence.edge.server.CarVideoServer;
import com.intelligence.edge.server.UavENVServer;
import com.intelligence.edge.server.UavVideoServer;
import com.intelligence.edge.service.CarBasicDataService;
import com.intelligence.edge.service.UavNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 无人机通信
 * @author: uu
 * @create: 08-04
 **/
@Service
@Slf4j(topic = "u.UavNetServiceImpl")
public class UavNetServiceImpl implements UavNetService {

    @Autowired
    private CarConfig carConfig;

    @Autowired
    private CarBasicDataMapper carBasicDataMapper;

    //无人机环境数据
    List<UavENVServer> uenvList = new ArrayList<>();
    List<UavVideoServer> uvList = new ArrayList<>();

    @Override
    public int connect(String deviceID) {
        log.info("连接无人机："+deviceID);

        if(carBasicDataMapper.getConnectState(deviceID) == 1){
            log.info("该机端口已占用");
            return 0;
        }else{
            CarBasicData device = new CarBasicData();
            device.setState(1);
            device.setCarID(deviceID);

            if(carBasicDataMapper.getConnectState(deviceID) == 1){
                receiveENVInfo(deviceID);
                receiveVideo(deviceID);
                return 1;
            }
            return 2;
        }
    }

    @Override
    public int closeConnect(String deviceID) {
        if(carBasicDataMapper.getConnectState(deviceID) == 0){
            log.info("无人机已断开连接！");
            return 0;
        }else{
            CarBasicData device = new CarBasicData();
            device.setState(0);
            device.setCarID(deviceID);
            if(carBasicDataMapper.getConnectState(deviceID) ==1) {
                closeConnection(deviceID);
                closeVideo(deviceID);
                log.info(deviceID + "成功断开！");
                return 1;
            }
            return 2;
        }
    }


    public void receiveENVInfo(String deviceID){

        try{
            int flag=0;
            UavENVServer uavENVServer =null;
            for ( UavENVServer ues : uenvList){
                if(ues.getDeviceID().equals(deviceID)){
                    uavENVServer = ues;
                    log.info("已有无人机"+deviceID+"进行数据连接");
                    flag = 1;
                    break;
                }
            }
            if(flag==0){
                uavENVServer = new UavENVServer(deviceID,carConfig.getCarENVPort().get(deviceID));
                uenvList.add(uavENVServer);
                log.info("创建新无人机连接");
            }
            uavENVServer.start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void receiveVideo(String deviceID){

        try{
            int flag=0;
            UavVideoServer uavVideoServer = null;
            for(UavVideoServer uvs : uvList){
                if(uvs.getDeviceID().equals(deviceID)) {
                    uavVideoServer = uvs;
                    log.info("使用已有无人机连接");
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                uavVideoServer = new UavVideoServer(deviceID,carConfig.getCarVideoPort().get(deviceID));
                uvList.add(uavVideoServer);
                log.info("创建新的无人机连接");
            }
            uavVideoServer.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeConnection(String deviceID){
        for (UavENVServer ues : uenvList ) {
            if (ues.getDeviceID().equals(deviceID)) {
                ues.end();
                log.info("关闭已有无人机数据连接");
                break;
            }
        }
    }

    public void closeVideo(String deviceID){
        for(UavVideoServer uvs : uvList){
            if(uvs.getDeviceID().equals(deviceID)){
                uvs.end();
                log.info("关闭无人机"+ deviceID + "的视频连接");
            }
        }
    }
}

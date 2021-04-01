package com.intelligence.edge.service.impl;

import com.intelligence.edge.dao.CarENVDataMapper;
import com.intelligence.edge.pojo.EnvironmentInfo;
import com.intelligence.edge.pojo.Position;
import com.intelligence.edge.service.CarENVDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class CarENVDataServiceImpl implements CarENVDataService {

    @Autowired
    private CarENVDataMapper carENVDataMapper;


    @Override
    public int insertCarENVData(EnvironmentInfo environmentInfo) {
        return 0;
    }

    @Override
    public List<String> getCarIDByTime(String time1, String time2) {
        List<String> IdList = carENVDataMapper.getCarIDByTime(time1, time2);
        return IdList;
    }

    @Override
    public List<Position> getEnvDataById(String Id) {
        List<Position> InfoList = carENVDataMapper.getEnvDataById(Id);
        return InfoList;
    }

    @Override
    public List<EnvironmentInfo> getLatestCarEnvInfo() {
        return carENVDataMapper.getLatestCarEnvInfo();
    }

    @Override
    public List<String> getEnvDaysById(String deviceId) {
        return carENVDataMapper.getEnvDaysById(deviceId);
    }

    @Override
    public List<Map<String, Object>> getPositionByIdAndTime(String deviceId, String time) {
        List<Map<String,Object>> PositionList;
        PositionList = carENVDataMapper.getPositionByIdAndTime(deviceId, time);
        return PositionList;
    }

    @Override
    public List<Map<String, Object>> getLatestCarEnvInfoById(String deviceId) {
        return carENVDataMapper.getLatestCarEnvInfoById(deviceId);
    }

    /*
    @Override
    public List<EnvironmentInfo> getEnvInfoByTime(String time) {
        List<EnvironmentInfo> EnvList = carENVDataMapper.getEnvInfoByTime(time);
        return EnvList;
    }
     */


}

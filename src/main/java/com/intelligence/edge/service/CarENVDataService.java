package com.intelligence.edge.service;

import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.pojo.EnvironmentInfo;
import com.intelligence.edge.pojo.Position;

import java.util.List;
import java.util.Map;

public interface CarENVDataService {

    int insertCarENVData(EnvironmentInfo environmentInfo);

    //List<EnvironmentInfo> getEnvInfoByTime(String time);

    List<String> getCarIDByTime(String time1, String time2);

    List<Position> getEnvDataById(String Id);

    List<EnvironmentInfo> getLatestCarEnvInfo();

    List<String> getEnvDaysById(String deviceId);

    List<Map<String, Object>> getPositionByIdAndTime(String deviceId, String time);

    List<Map<String, Object>> getLatestCarEnvInfoById(String deviceId);

}

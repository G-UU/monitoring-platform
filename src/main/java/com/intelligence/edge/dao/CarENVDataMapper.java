package com.intelligence.edge.dao;

import com.intelligence.edge.pojo.EnvironmentInfo;
import com.intelligence.edge.pojo.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CarENVDataMapper {
    int insertCarENVData(EnvironmentInfo environmentInfo);


    //查找某天某车的数据
    //List<EnvironmentInfo> getEnvInfoByTime(String time);

    List<String> getCarIDByTime(@Param("time1") String time1, @Param("time2") String time2);

    List<Position> getEnvDataById(String Id);

    List<EnvironmentInfo> getLatestCarEnvInfo();

    List<String> getEnvDaysById(@Param("deviceId") String deviceId);

    List<Map<String, Object>> getPositionByIdAndTime(@Param("deviceId") String deviceId, @Param("date") String date);

    int cleanByDate(@Param("cleanDate") String cleanDate);

    List<Map<String, Object>> getLatestCarEnvInfoById(@Param("deviceId") String deviceId);
}

package com.intelligence.edge.dao;

import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.pojo.DeviceTravelData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarBasicDataMapper {

    List<CarBasicData> getAllCarBasicData();

    List<CarBasicData> getAllUavBasicData();

    CarBasicData getCarBasicDataByID(String carID);

    int insertCarBasicData(CarBasicData carBasicData);

    int deleteCarBasicDataByID(String carID);

    int updateCarBasicData(CarBasicData carBasicData);

    // 获取对应设备的连接状态
    int getConnectState(String carID);

    // 修改设备的连接状态
    int setConnectState(@Param("carID") String carID, @Param("state") int state);

    // 重置所有连接状态
    int resetState();

    // 获取设备流地址
    String getVideoAddressByID(String deviceID);

    //获取设备统计数据
    DeviceTravelData getDeviceTravelDataById(@Param("deviceId") String deviceId);
}

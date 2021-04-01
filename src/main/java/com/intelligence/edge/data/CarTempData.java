package com.intelligence.edge.data;

import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.pojo.Position;
import com.intelligence.edge.server.CarControlServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shik2
 * @date 2020/06/29
 **/
@Component
public class CarTempData {
    public static Map<String, Position> carPos = new HashMap<>();
    public static List<CarBasicData> carList;
    public static List<CarBasicData> uavList;
    public static Map<String,CarControlServer> ccsMap = new HashMap<>();
    // 无人车视频数据接收端口map
    public static Map<String, String> carIP = new HashMap<>();

    // 无人车连接状态
    public static Map<String, Integer> carState = new HashMap<>();

    @Autowired
    private CarBasicDataMapper carBasicDataMapper;

    @PostConstruct
    public void init() {
        //加载无人车设备
        carList = carBasicDataMapper.getAllCarBasicData();
        uavList = carBasicDataMapper.getAllUavBasicData();
        for (CarBasicData carBasicData : carList) {
            Position position = new Position(120.35,30.32);
            //？设为固定值？
            carPos.put(carBasicData.getCarID(),position);
            carState.put(carBasicData.getCarID(),0);
        }

        for (CarBasicData uavBasicData : uavList) {
            Position position = new Position(120.35,30.32);
            //？设为固定值？
            carPos.put(uavBasicData.getCarID(),position);
            carState.put(uavBasicData.getCarID(),0);
        }
        System.out.println("data");
    }
    @PreDestroy
    public void destroy() {
        //系统运行结束
    }

}

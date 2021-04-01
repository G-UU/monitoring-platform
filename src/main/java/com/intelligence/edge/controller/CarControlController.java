package com.intelligence.edge.controller;

import com.intelligence.edge.config.CarConfig;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.Position;
import com.intelligence.edge.result.StandardResult;
import com.intelligence.edge.service.CarBasicDataService;
import com.intelligence.edge.service.CarControlService;
import com.intelligence.edge.service.CarNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

 **/
@RestController
@CrossOrigin
@RequestMapping("/control/device")
@Slf4j(topic = "c.CarNetController")
public class CarControlController {
    @Autowired
    private CarControlService carControlService;

    @Autowired
    private CarBasicDataService carBasicDataService;


    @Autowired
    private CarNetService carServer;

    @Autowired
    private CarConfig carConfig;


    @RequestMapping("/reset")
    public int reset(@RequestParam("deviceID") String deviceID) {
        /*if(CarTempData.carState.get(carID)==0){
            log.info("设备离线：" + carID);
            return 0;
        }*/
        carControlService.reset(deviceID);
        log.info("设备控制端口重启成功：" + deviceID);
        return 1;
    }


    @RequestMapping("/send")
    public StandardResult control(@RequestParam("deviceID") String deviceID, @RequestParam("instruction") String instruction) {
        StandardResult standardResult = new StandardResult();
        StandardResult result  = null;
        String carIP = CarTempData.carIP.get(deviceID);
        /*if (!carServer.ping(carIP)) {
            log.info("设备离线：" + carID);
            return -1;
        }*/
        if(CarTempData.carState.get(deviceID)==0){
            log.info("设备离线：" + deviceID);
            result = standardResult.build(0,"该设备已离线");
            return result;
        }
        carControlService.control(deviceID,instruction);
        result = standardResult.success();
        return result;
    }

    //自动控制  车 和 终点  自动导航
    @RequestMapping("/destination")
    public StandardResult navagationControl(@RequestParam("deviceID") String deviceID, @RequestParam("lng") double lng, @RequestParam("lat") double lat){
        StandardResult standardResult = new StandardResult();
        StandardResult result  = null;

        if(CarTempData.carState.get(deviceID)==0){
            log.info("设备离线：" + deviceID);
            result = standardResult.build(0,"该设备已离线");
            return result;
        }
        Position position = new Position(lng,lat);
        int sendState = carControlService.destination(deviceID,position);
        if (sendState == 0){
            result = standardResult.build(0,"发送失败");}
        else{
            result = standardResult.success();
        }
        return result;
    }
    //websocket  通知前端

    @RequestMapping("/getcamera")
    public String getCamera(@RequestParam("deviceID") String deviceID){
        String carIP = CarTempData.carIP.get(deviceID);
        if(CarTempData.carState.get(deviceID)==0){
            log.info("设备离线：" + deviceID);
            return "device offline";
        }
        carControlService.control(deviceID,"getcamera");
        return carBasicDataService.getVideoAddressByID(deviceID);
    }


}
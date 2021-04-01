package com.intelligence.edge.controller;

import com.intelligence.edge.config.CarConfig;
import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.CarBasicData;
import com.intelligence.edge.service.UavNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/data/uav")
@Slf4j(topic = "u.UavNetController")
public class UavNetController {

    @Autowired
    private UavNetService uavNetService;

    @Autowired
    private CarBasicDataMapper carBasicDataMapper;

    @Autowired
    private CarConfig carConfig;


    @RequestMapping("/connect")
    public int connect(@RequestParam("deviceID") String deviceID){
        if(CarTempData.carState.get(deviceID) == 0){
            log.info("设备已离线");
            return 0;
        }
        return uavNetService.connect(deviceID);
    }


}
package com.intelligence.edge.controller;

import com.intelligence.edge.config.CarConfig;
import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.result.StandardResult;
import com.intelligence.edge.service.CarNetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**

 */
@RestController
@CrossOrigin
@RequestMapping("/data/car")
@Slf4j(topic = "c.CarNetController")
public class CarNetController {

    @Autowired
    private CarNetService carServer;

    @Autowired
    private CarBasicDataMapper carBasicDataMapper;


    @Autowired
    private CarConfig carConfig;

    /**
     * 测试新添加设备是否可以连接
     *
     * @param IP
     * @return
     */
    @RequestMapping("/testConnect")
    public int testConnect(@RequestParam("IP") String IP) {
        log.info("设备ip:" + IP);
        return carServer.ping(IP) ? 1 : 0;
    }


    /**
     * 传入设备id，开启对应无人车环境数据和视频数据的udp接收端,修改对应车
     *
     * @param carID
     * @return
     * @throws IOException
     */
    @RequestMapping("/connect")
    public StandardResult connect(@RequestParam("carID") String carID) {
        // 设备无法连通
        StandardResult standardResult = new StandardResult();
        /*String carIP = CarTempData.carIP.get(carID);
        if (!carServer.ping(carIP)) {
            log.info("设备离线：" + carID);
            return 0;
        }*/
        if(CarTempData.carState.get(carID) == 0){
            log.info("设备离线：" + carID);
            return standardResult.build(0,"设备离线，连接失败");
        }
        return  standardResult.build(carServer.connect(carID),"连接成功");
    }

    /**
     * 关闭对应id设备的数据接收端
     *
     * @param carID
     * @return
     */
    @RequestMapping("/closeConnect")
    public StandardResult closeConnect(@RequestParam("carID") String carID) {
        // 设备无法连通
        StandardResult standardResult = new StandardResult();
        String carIP = CarTempData.carIP.get(carID);
        /*
        if (!carServer.ping(carIP)) {
            log.info("设备离线：" + carID);
            return standardResult.build(-1,"设备已经离线");
        }*/
        int state = carServer.closeConnect(carID);
        if(state == 0){
            return standardResult.build(0,"关闭失败，设备已经不在线");
        }else if(state == 1){
            return standardResult.build(1,"成功断开！");
        }else{
            return standardResult.build(555,"好像哪里出了问题");
        }

    }
}

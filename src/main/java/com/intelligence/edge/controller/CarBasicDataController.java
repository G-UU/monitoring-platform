package com.intelligence.edge.controller;

import com.intelligence.edge.dao.CarBasicDataMapper;
import com.intelligence.edge.data.CarTempData;
import com.intelligence.edge.pojo.*;
import com.intelligence.edge.result.StandardResult;
import com.intelligence.edge.service.CarBasicDataService;
import com.intelligence.edge.service.CarENVDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device/car")
@CrossOrigin
@Slf4j(topic = "c.CarBasicDataController")
public class CarBasicDataController {
    @Autowired
    CarBasicDataService carBasicDataService;
    @Autowired
    CarENVDataService carENVDataService;

    @RequestMapping(value = "test")
    public Integer hello() {

        return 1;
    }

    /**
     *
     * @return 获取所有无人车的基本信息
     */
    @RequestMapping(value = "allCarData")
    public StandardResult getAllCarBasicData(@RequestParam("type") int type ) {
        ;
        StandardResult standardResult = new StandardResult();

        StandardResult result  = null;
        if( type == 1){
            List<CarBasicData> carBasicData = carBasicDataService.getAllCarBasicData();
            if(carBasicData != null){
                result = standardResult.success(carBasicData);
            } else {
                result = standardResult.build(555,"查找失败");
            }

        } else if(type == 0 ){
            List<CarBasicData> carBasicData = carBasicDataService.getAllUavBasicData();
            result = standardResult.success(carBasicData);
        } else {
            result = standardResult.build(555, "查找类型错误");
        }
        return result;
    }

    @RequestMapping(value = "onlinedevice")
    public StandardResult getAllOnlineDeviceData(){

        StandardResult standardResult = new StandardResult();
        StandardResult result = null;
        //获取在线列表  取出在线车辆 获取在线车辆位置 封装
        List<CurrentPostion> olList = new ArrayList<CurrentPostion>();

        for(CarBasicData carBasicData : CarTempData.carList){
            String carID = carBasicData.getCarID();
            if(CarTempData.carState.get(carID) == 1){
                Position pos = CarTempData.carPos.get(carID);
                //System.out.println(pos);
                log.info("onlinedevice");
                log.info(String.valueOf(pos));
                CurrentPostion currentPostion = new CurrentPostion(carID,pos.getLongitude(),pos.getLatitude());
                System.out.println(currentPostion);
                olList.add(currentPostion);
            }
            result = standardResult.success(olList);

        }

        return  result;
    }

    @RequestMapping(value = "latestInfo")
    public StandardResult getCarLatestInfo(){
        List<EnvironmentInfo> carLatestInfo= carENVDataService.getLatestCarEnvInfo();
        StandardResult standardResult = new StandardResult();
        log.info("latestInfo");
        return  standardResult.success(carLatestInfo);
    }
    //获取车辆详细信息？
    /**
     * 根据设备id获取对应无人车基本信息
     * @param carID
     * @return
     */
    @RequestMapping(value = "getCarData")
    public StandardResult getCarBasicDataByID(String carID) {
        CarBasicData carBasicData =  carBasicDataService.getCarBasicDataByID(carID);
        StandardResult standardResult = new StandardResult();

        return standardResult.success(carBasicData);
    }

    /**
     * 新增无人车
     * @param car
     * @return
     */
    @PostMapping(value = "insertCar")
    public StandardResult insertCarBasicData(@RequestBody CarBasicData car) {
        car.setElectricity(100);
        car.setState(0);
        log.info("新增car:"+car);
        if(car.getCarID()==null||car.getIp()==null){
            return StandardResult.build(0,"新增失败");
        }
        return StandardResult.build(carBasicDataService.insertCarBasicData(car)," ");
    }

    // 删除无人车
    @RequestMapping(value = "deleteCar")
    public StandardResult deleteCarBasicDataByID(String carID) {
        log.info("删除device:"+carID);

        return StandardResult.build(carBasicDataService.deleteCarBasicDataByID(carID),"");
    }

    // 更新无人车信息
    @PostMapping(value = "updateCar")
    public StandardResult updateCarBasicData(@RequestBody CarBasicData carBasicData) {
        log.info("更新设备信息:"+carBasicData);
        return StandardResult.build(carBasicDataService.updateCarBasicData(carBasicData),"");
    }
    //发送视频端口号
    @RequestMapping(value= "videoAddress")
    public String getVideoAddressByID(@RequestBody String deviceID){
        log.info("send videoAddress " + deviceID);
        return "";
    }

    //查询时间内的车辆情况
    @RequestMapping(value = "search")
    public StandardResult getCarIdByTime(@RequestParam("time1") long time1, @RequestParam("time2") long time2){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StandardResult standardResult = new StandardResult();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stime1 = sdf.format(new Date(Long.parseLong(String.valueOf(time1))));
        String stime2 = sdf.format(new Date(Long.parseLong(String.valueOf(time2))));
        log.info("search CarId from:"+ stime1 + "to " + stime2);
        List<String> carList = carENVDataService.getCarIDByTime(stime1, stime2);
        System.out.println(carList);
        return standardResult.success(carList);

    }

    //历史数据 历史轨迹
    @RequestMapping(value= "history")
    public List<Position> getPositionById(@RequestParam("Id") String Id) {
        return carENVDataService.getEnvDataById(Id);
    }

    //查询小车行驶数据
    @RequestMapping(value= "drivingdata")
    public StandardResult getDeviceDrivingDataById(@RequestParam("carID") String carId) {
        StandardResult standardResult = new StandardResult();

        return standardResult;
    }

    //查询小车行驶时间 按天统计
    @RequestMapping(value = "days")
    public StandardResult getCarInfoInDays(@RequestParam("deviceId") String deviceId){
        StandardResult result = new StandardResult();
        List<String> daysList = carENVDataService.getEnvDaysById(deviceId);
        log.info("deviceUsedDays");
        System.out.println(daysList);

        return result.success(daysList);
    }

    //查询一天中的路径
    @RequestMapping(value = "route/oneday")
    public StandardResult getDeviceRouteOnOneDay(@RequestParam("deviceId") String deviceId, @RequestParam("date")  String date){
        StandardResult standardResult = new StandardResult();
        List<Map<String,Object>> routeList = carENVDataService.getPositionByIdAndTime(deviceId,date);
        log.info("route On Oneday");
        //System.out.println(routeList);

        return  standardResult.success(routeList);
    }

    @RequestMapping(value = "traveldata")
    public StandardResult getTravelDataById(@RequestParam("deviceId") String deviceId){
        StandardResult standardResult = new StandardResult();
        DeviceTravelData deviceTravelData = carBasicDataService.getDeviceTravelDataById(deviceId);
        log.info("travel data of "+ deviceId);

        return standardResult.success(deviceTravelData);
    }

    @RequestMapping(value="EnvInfo")
    public StandardResult getLatestCarEnvInfoById(@RequestParam("deviceId") String deviceId){
        StandardResult standardResult = new StandardResult();
        List<Map<String, Object>> deviceInfo = carENVDataService.getLatestCarEnvInfoById(deviceId);
        log.info("EnvInfo" + deviceId);

        return standardResult.success(deviceInfo);
    }
}

package com.intelligence.edge.controller;

import com.intelligence.edge.service.DeviceCameraService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 摄像头
 * @author: uu
 * @create: 08-07
 **/
@RestController
@CrossOrigin
@RequestMapping("/device/camera")
@Slf4j(topic = "d.CameraController")
public class DeviceCameraController {
    @Autowired
    private DeviceCameraService deviceCameraService;

    @RequestMapping("/connect")
    public int getVedioStream(@RequestParam("deviceID") String deviceID) throws FrameGrabber.Exception, InterruptedException, FrameRecorder.Exception {
        //if(deviceID=="camera"){
            return  deviceCameraService.getVideoStream();
        //}else {
          //  return -1;
        //}
        //return 开启的值
    }


    @RequestMapping("/closeconnect")
    public int closeVedioStream(@RequestParam("deviceID") String deviceID) throws FrameGrabber.Exception, FrameRecorder.Exception {
        //if(deviceID=="camera"){
            return deviceCameraService.closeVideoStream();
        //}else{
        //    return -1;
        //}
    }
}

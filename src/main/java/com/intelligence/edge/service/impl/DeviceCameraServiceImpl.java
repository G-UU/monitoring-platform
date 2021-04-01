package com.intelligence.edge.service.impl;

import com.intelligence.edge.server.CameraServer;
import com.intelligence.edge.service.DeviceCameraService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.stereotype.Service;

/**
 * @description: 拉推流
 * @author: uu
 * @create: 08-07
 **/
@Service
@Slf4j(topic = "d.DeviceCameraServiceImpl")
public class DeviceCameraServiceImpl implements DeviceCameraService {

    //ArrayList
    CameraServer cameraServer = new CameraServer();
    @Override
    //需要传入DeviceID
    public int getVideoStream() throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException {
        /*
        FrameGrabber grabber = FrameGrabber.createDefault(0);//0：本机摄像头
        grabber.start();//开启抓取器
        */
        //判断摄像头的开启情况
        log.info("开始连接摄像头，抓取画面");
        //CameraServer cameraServer =null;
        //CameraServer cameraServer = new CameraServer();
        cameraServer.start();
        log.info("开始抓取画面，并推流");
        return 1;

    }

    /*
    public void run() throws FrameRecorder.Exception, InterruptedException, FrameGrabber.Exception {
        cameraServer.run();
    }*/

    @Override
    public int closeVideoStream() throws FrameGrabber.Exception, FrameRecorder.Exception {

            cameraServer.end();
            return 1;
    }
}

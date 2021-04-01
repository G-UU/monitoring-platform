package com.intelligence.edge.server;

import com.intelligence.edge.utils.SpringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * @description: 获取摄像头视频流 后并推流至服务器
 * @author: uu
 * @create: 08-07
 **/
@Data
@Slf4j(topic = "c.CameraServer")
public class CameraServer extends Thread {
    /**
    private String deviceID;
    private int port;
     **/
    String outputFile;
    int frameRate;
    FrameGrabber grabber;
    FrameRecorder recorder;
    CanvasFrame frame;

    //private Thread thread;
    @Override
    public void run() {
        outputFile = "rtmp://localhost:1935/live/mp4test";
        frameRate = 25;
        try{
            grabber = FrameGrabber.createDefault(0);//0：本机摄像头

            log.info("开启抓取器");

            grabber.start();//开启抓取器
            log.info("抓取器开启成功！");
        }catch (FrameGrabber.Exception e){
            e.printStackTrace();
        }
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
        try {
        IplImage grabbedImage = converter.convert(grabber.grab());
        int width = grabbedImage.width();
        int height = grabbedImage.height();

            recorder = FrameRecorder.createDefault(outputFile, width, height);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
            recorder.setFormat("flv");//rtmp
            recorder.setFrameRate(frameRate);

            log.info("开启录制器");
            recorder.start();//开启录制器
            log.info("录制器开启成功");

            long startTime=0;
            long videoTS=0;
            Frame rotatedFrame=converter.convert(grabbedImage);//转换

            while ((grabbedImage = converter.convert(grabber.grab())) != null) {

                rotatedFrame = converter.convert(grabbedImage);
                //frame.showImage(rotatedFrame);
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                }
                videoTS = 1000 * (System.currentTimeMillis() - startTime);
                recorder.setTimestamp(videoTS);
                recorder.record(rotatedFrame);
                Thread.sleep(20);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

  //  public void start() {


        /*
        if(thread==null){
            thread.start();
        }
        */
        /*
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
        IplImage grabbedImage = converter.convert(grabber.grab());
        int width = grabbedImage.width();
        int height = grabbedImage.height();

        recorder = FrameRecorder.createDefault(outputFile, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");//rtmp
        recorder.setFrameRate(frameRate);

        log.info("开启录制器");
        recorder.start();//开启录制器
        log.info("录制器开启成功");

        long startTime = 0;
        long videoTS = 0;
        Frame rotatedFrame = converter.convert(grabbedImage);//转换

        while ((grabbedImage = converter.convert(grabber.grab())) != null) {

            rotatedFrame = converter.convert(grabbedImage);
            //frame.showImage(rotatedFrame);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            videoTS = 1000 * (System.currentTimeMillis() - startTime);
            recorder.setTimestamp(videoTS);
            recorder.record(rotatedFrame);
            Thread.sleep(20);
        }

         */

   // }


    public void end() throws FrameGrabber.Exception, FrameRecorder.Exception {
       // frame.dispose();//关闭窗口、
        //设置Flag
        recorder.close();//关闭推流录制器，释放资源，停止
        grabber.close();//关闭抓取器
        log.info("推流结束");

    }

}


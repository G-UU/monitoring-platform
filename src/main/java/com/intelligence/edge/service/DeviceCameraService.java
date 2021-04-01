package com.intelligence.edge.service;


import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

public interface DeviceCameraService {

    int getVideoStream() throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException;

    int closeVideoStream() throws FrameGrabber.Exception, FrameRecorder.Exception;
}

/**
 * @description: 本地摄像头视频推流测试
 * @author: uu
 * @create: 08-06
 **/


import javax.swing.JFrame;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.opencv.opencv_core.IplImage;

public class CameraTest {
    public static void main(String[] args) throws Exception, FrameRecorder.Exception, InterruptedException {
        String outputFile = "rtmp://localhost:1935/live/mp4test";
        int frameRate = 25;
        FrameGrabber grabber = FrameGrabber.createDefault(0);//0：本机摄像头
        grabber.start();//开启抓取器

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
        IplImage grabbedImage = converter.convert(grabber.grab());
        int width = grabbedImage.width();
        int height = grabbedImage.height();

        FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");//rtmp
        recorder.setFrameRate(frameRate);

        recorder.start();//开启录制器
        long startTime=0;
        long videoTS=0;
        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        Frame rotatedFrame=converter.convert(grabbedImage);//转换
        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {

            rotatedFrame = converter.convert(grabbedImage);
            frame.showImage(rotatedFrame);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            videoTS = 1000 * (System.currentTimeMillis() - startTime);
            recorder.setTimestamp(videoTS);
            recorder.record(rotatedFrame);
            Thread.sleep(40);
        }
        frame.dispose();//关闭窗口
        recorder.close();//关闭推流录制器，释放资源，停止
        grabber.close();//关闭抓取器
    }
}

import org.bytedeco.javacv.*;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.opencv.opencv_core.IplImage;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
使用javacv推流
 **/

public class CameraPushTest {

    public static void recordPush(String inputFile,String outputFile,int v_rs) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception, InterruptedException{

        FrameGrabber grabber =new FFmpegFrameGrabber(inputFile);
        try {
            grabber.start();
        } catch (Exception e) {
            throw e;
        }
        //一个opencv视频帧转换器
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        Frame grabframe =grabber.grab();
        IplImage grabbedImage =null;
        if(grabframe!=null){
            System.out.println("取到第一帧");
            grabbedImage = converter.convert(grabframe);
        }else{
            System.out.println("没有取到第一帧");
        }
        //如果想要保存图片,可以使用 opencv_imgcodecs.cvSaveImage("hello.jpg", grabbedImage);来保存图片

        FrameRecorder recorder;
        try {
            recorder = FrameRecorder.createDefault(outputFile, 1280, 720);
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            throw e;
        }

        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264
        recorder.setFormat("flv");
        recorder.setFrameRate(v_rs);
        recorder.setGopSize(v_rs);

        try {
            recorder.start();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            System.out.println("录制器启动失败");
            throw e;

        }
        /*
        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);

        while (frame.isVisible() && (grabframe=grabber.grab()) != null) {
            EnumSet<Type> videoOrAudio=frame.getTypes();
            if(videoOrAudio.contains(Type.VIDEO)) {
                frame.showImage(grabframe);
            }
            if(rotatedFrame!=null){
                recorder.record(rotatedFrame);
            }
        }
        recorder.close();
        grabber.close();

         */
        Frame rotatedFrame=converter.convert(grabbedImage);//转换

        while ((grabbedImage = converter.convert(grabber.grab())) != null) {
            rotatedFrame = converter.convert(grabbedImage);
            if(rotatedFrame!=null){
                recorder.record(rotatedFrame);
            }
        }
        recorder.close();
        grabber.close();
        System.out.println("发送完成");
    }



    public static void main(String[] args) throws Exception {
        String inputFile = "D:\\demo.mp4";

        String outputFile="rtmp://localhost:1935/live/mp4test";
        recordPush(inputFile, outputFile,25);

    }
}

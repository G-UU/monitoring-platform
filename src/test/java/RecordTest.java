import org.bytedeco.javacv.*;

import java.io.File;

/**
 * @description:
 * @author: uu
 * @create: 08-07
 **/

//测试javacv的录制视频功能 录制与存储
public class RecordTest {


    public static void main(String[] args) throws Exception {
        String inputFile = "rtmp://58.200.131.2:1935/livetv/hunantv";
        String OutputFile = "D:\\record.mp4";
        int flag=0;
        boolean isStart=true;//该变量建议设置为全局控制变量，用于控制录制结束
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(OutputFile, 1280, 720, 1);
        // 开始取视频源
        System.out.println("开始录制！");
        try {//建议在线程中使用该方法
            grabber.start();
            recorder.start();
            Frame frame = null;
            while (isStart&& (frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
                flag++;
                if(flag==240){
                    isStart=false;
                }
            }
            System.out.println("录制结束");
            recorder.stop();
            grabber.stop();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            if (grabber != null) {
                grabber.stop();
            }
        }

    }

    /**
     public static void frameRecord(String inputFile, String outputFile, int audioChannel)
     throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {

     boolean isStart=true;//该变量建议设置为全局控制变量，用于控制录制结束
     // 获取视频源
     FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
     // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
     FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
     // 开始取视频源
     System.out.println("开始录制！");
     recordByFrame(grabber, recorder, isStart);
     }

     public static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, boolean status) throws FrameGrabber.Exception {
     try {//建议在线程中使用该方法
     grabber.start();
     recorder.start();
     Frame frame = null;
     while (status&& (frame = grabber.grabFrame()) != null) {
     recorder.record(frame);
     }
     recorder.stop();
     grabber.stop();
     } catch (FrameRecorder.Exception e) {
     e.printStackTrace();
     } finally {
     if (grabber != null) {
     grabber.stop();
     }
     }

     }
     **/



}

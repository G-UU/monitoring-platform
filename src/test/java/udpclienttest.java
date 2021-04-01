/**
 * @description:
 * @author: uu
 * @create: 12-19
 **/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class udpclienttest {
    public static void main(String[] args) throws IOException {

        System.out.println("发送端启动......");

        // 创建 UDP 的 Socket，使用 DatagramSocket 对象
        DatagramSocket ds = new DatagramSocket();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = bufr.readLine()) != null) {
            // 使用 DatagramPacket 将数据封装到该对象的包中
            byte[] buf = line.getBytes();
            DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("175.24.59.243"), 10000);
            // 通过 UDP 的 Socket 服务将数据包发送出去，使用 send 方法
            ds.send(dp);
            // 如果输入信息为 over，则结束循环
            if ("over".equals(line))
                break;
        }
        // 关闭 Socket 服务
        ds.close();
    }
}

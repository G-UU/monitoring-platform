import com.intelligence.edge.dao.CarENVDataMapper;
import com.intelligence.edge.service.CarENVDataService;
import com.intelligence.edge.service.impl.CarENVDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 测试时间转换与查找
 * @author: uu
 * @create: 12-22
 **/
public class timeTest {
    @Autowired
    private static CarENVDataService carENVDataService;

    //CarENVDataServiceImpl carENVDataService;

    public static void main(String[] args) throws ParseException {
        //CarENVDataService carENVDataService = new CarENVDataServiceImpl();
        Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        System.out.println(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));      // 时间戳转换成时间
        System.out.println("格式化结果：" + sd);
        String stime1 = "2020-06-19 14:15:22";
        String stime2 = "2020-06-19 14:15:31";

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long d1 = sf.parse(stime1).getTime();
        long d2 = sf.parse(stime2).getTime();

        System.out.println(d1);
        System.out.println(d2);
        // List<String> carList  = carENVDataService.getCarIDByTime(stime1,stime2);
        //System.out.println(carList);
    }
}

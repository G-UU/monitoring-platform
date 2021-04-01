import com.intelligence.edge.Starter;
import com.intelligence.edge.pojo.UavEnvironmentInfo;
import com.intelligence.edge.service.UavENVDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Starter.class)
public class UavEnvSQLTest {

    @Autowired
    UavENVDataService uavENVDataService;

    @Test
    public void test(){
        UavEnvironmentInfo uavEnvironmentInfo = new UavEnvironmentInfo("2020-08-01 17:04:54","uav1",118.72,29.3,15.91,3.58,11.11,11.11,11.11,1.11,11.11);
        uavENVDataService.insertUavENVData(uavEnvironmentInfo);
    }

}

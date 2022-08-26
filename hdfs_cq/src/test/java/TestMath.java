import com.cq.Application;
import com.cq.util.CommunityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestMath {

    @Autowired
    private CommunityUtil communityUtil;

    @Test
    public void test1() {
        int a = 1;
        {
            a = 2;
        }
        System.out.println(a);
    }

    @Test
    public void test2() {
        int[] res = communityUtil.selectServer23(3, 50000);
        System.out.println(res[0]);
        System.out.println(res[1]);
    }

}

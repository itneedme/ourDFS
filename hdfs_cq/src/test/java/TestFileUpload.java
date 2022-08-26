import com.cq.Application;
import com.cq.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestFileUpload {

    @Autowired
    private FileService fileService;

    @Test
    public void testFileUpload() {
        fileService.uploadFile("D:\\bbbbb.txt", 1);
    }

}

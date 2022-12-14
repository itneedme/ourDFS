import com.cq.Application;
import com.cq.controller.FileController;
import com.cq.service.CommandService;
import com.cq.service.DataService;
import com.cq.service.FileService;
import com.cq.util.CommunityUtil;
import okhttp3.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestFilePart {

    @Autowired
    private FileController fileController;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataService dataService;

    @Autowired
    private CommunityUtil communityUtil;

    @Autowired
    private CommandService commandService;

    @Test
    public void test1() {
        try {
            testFilePart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        File file = new File("D:/aaaaa.txt");
        System.out.println(file);
    }

    @Test
    public void test3() {
        int a = 52062103;
        int b = 52428800;
        double res = (double) a / b;
        System.out.println(res);
    }

    @Test
    public void test4() {
        // communityUtil.selectServer23(1);
    }

    @Test
    public void test5() {
        // commandService.getCommand("cmd /c nping --tcp-connect -p 8080 129.211.209.15");
        // commandService.getCommand("cmd /c dir");
        // commandService.execCommand(new String[]{"cmd", "/k", "nping --tcp-connect -p 8000 129.211.209.15"});
        // commandService.execCommand(new String[]{"cmd", "/k", "powershell -h"});
        String returnData = commandService.getReturnData("129.211.209.15");
        System.out.println(returnData);
    }

    @Test
    public void test6() throws IOException {
        // fileService.fileMerge();
    }

    @Test
    public void test7() {

        // dirTree.genDirTree("D:\code_java\hdfs_bytedance_cq\hdfs_cq", );
    }

    @Test
    public void testFileMoveToAnother() throws IOException {
        dataService.fromOneServerToAnother(2, 7);
    }

    public static void testFilePart() throws IOException {
        File sourceFile = new File("D:/BaiduNetdiskDownload/testDocument/Matlab??????.rar");
        String chunkPath = "E:/filePart/";
        File chunkFolder = new File(chunkPath);
        // ??????????????????
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        // ????????????????????????,??????100M
        long chunkSize = 1024 * 1024 * 128;
        // ????????????
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        if(chunkNum<=0){
            chunkNum=1;
        }
        // ???????????????
        byte[] bytes = new byte[1024];  // ?????????????????????byte[]??????????????????
//        byte[] bytes = new byte[(int) sourceFile.length()];
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(sourceFile));
        // ????????????
        for (int i = 1; i <= chunkNum; i++) {
            // ??????????????????
            File file = new File(chunkPath + i);
            if(file.createNewFile()){
                // ???????????????????????????
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
                int len = -1;
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len); // ????????????
                    if(file.length()>chunkSize){
                        break;
                    }
                }
                fos.close();
            }
        }
        fis.close();
    }

    public void testUploadToServer() {
        File file = new File("D:/2021???????????????.zip");
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "D:/2021???????????????.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();
        Request request = new Request.Builder()
                .url("http://1.13.181.152:8000")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.post("http://1.13.181.152:8000")
                    .field("file", new File("D:/2021???????????????.zip"))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }*/
    }

    public void testMultiToFile(MultipartFile multipartfile) throws IOException {
        //??????2???????????????????????????????????????
        if(!multipartfile.isEmpty()){
            CommonsMultipartFile commonsmultipartfile = (CommonsMultipartFile) multipartfile;
            DiskFileItem diskFileItem = (DiskFileItem) commonsmultipartfile.getFileItem();
            File file = diskFileItem.getStoreLocation();
            System.out.println("????????????????????????"+file);
            String name = file.getName();
            System.out.println("???????????????" + name);
            //???????????????File???????????????
            if(file.exists()){
                file.delete();
            }
        }
    }

}

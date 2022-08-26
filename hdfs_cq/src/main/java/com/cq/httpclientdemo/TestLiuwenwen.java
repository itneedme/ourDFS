package com.cq.httpclientdemo;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

// 刘文文
@Service
public class TestLiuwenwen {

    // 上传文件
    public void testUpload() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","D:/aaaaa.txt",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("D:/aaaaa.txt")))
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
            HttpResponse<String> response1 = Unirest.post("http://1.13.181.152:8000")
                    .field("file", new File("D:/aaaaa.txt"))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }*/
    }

    public void testDelete() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://1.13.181.152:8000/font.zip")
                .method("DELETE", body)
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .addHeader("Authorization", "Basic Y3hrOmN4azEyMw==")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", "_ga=GA1.1.846005249.1661165675; _gid=GA1.1.1187716334.1661165675; _gat=1")
                .addHeader("Origin", "http://1.13.181.152:8000")
                .addHeader("Referer", "http://1.13.181.152:8000/")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.63")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

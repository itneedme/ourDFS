package com.cq.demo.io;

import java.io.FileOutputStream;
import java.io.IOException;

// FileOutputStream
public class Demo5 {

    // IO流
    public static void main(String[] args) throws IOException {
        // true表示追加
        FileOutputStream fos = new FileOutputStream("E://a.txt");
        byte[] bytes = "abcdefg".getBytes();
        fos.write(bytes);
        // fos.write(bytes, 1, 2); // 选择性范围写
        /*byte[] bytes2 = {65, 66, 67, 68, 69};
        fos.write(bytes2);*/
        fos.close(); // 流再关闭之后就不用再写了
        System.out.println("写出成功");
    }

}

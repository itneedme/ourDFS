package com.cq.demo.io;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// InputStream
public class Demo6 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("E://a.txt");

        // 方法1：
        /* // 以下代码循环实现
        // 读取的第一个字节
        byte b = (byte) fis.read();
        System.out.println((char)b);
        // 读取的第二个字节
        byte b2 = (byte) fis.read();
        System.out.println((char)b2);*/

        // 方法2：
        // 一个字节一个字节读取，读取完之后停止
        /*while(true) {
            byte b = (byte) fis.read();
            if(b == -1) {
                break;
            }
            System.out.print((char)b);
        }*/

        // 方法3：
        /*byte[] bytes = new byte[10];
        int len = fis.read(bytes);
        System.out.println(new String(bytes, 0, len));
        len = fis.read(bytes);
        System.out.println(new String(bytes, 0, len));
        len = fis.read(bytes);
        System.out.println(new String(bytes, 0, len));*/



        // 之后再读的话len为-1
        fis.close();
    }

}

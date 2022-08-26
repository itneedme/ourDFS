package com.cq.demo.io;

import java.io.*;

// 转换流
// 字节流装饰为字符流: 使用了装饰者设计模式
public class Demo9 {

    public static void main(String[] args) throws IOException {
        /*FileInputStream fis = new FileInputStream("E://b.txt");
        // 将要转换的字节流转换为字符流
        // 第二个参数确定编码名称
        InputStreamReader isr = new InputStreamReader(fis, "GBK");
        while(true) {
            int c = isr.read();
            if(c == -1) {
                break;
            }
            System.out.println((char)c);
        }*/

        FileOutputStream fos = new FileOutputStream("E://a.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write("床前明月光，疑是地上霜。");
        osw.flush();
        osw.close();


    }

}

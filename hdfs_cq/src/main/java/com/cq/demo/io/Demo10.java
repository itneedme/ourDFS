package com.cq.demo.io;

import java.awt.image.BufferedImage;
import java.io.*;

// 打印流、缓存读取流
public class Demo10 {

    public static void main(String[] args) throws IOException {
        // 字符输出 打印流
        // System.out.
        // 基于字符的打印流
        /*PrintStream ps = new PrintStream("E://b.txt");
        ps.println("锄禾日当午");*/

        // 基于字符的打印流，不刷新管道看不到结果
        /*PrintWriter pw = new PrintWriter("E://a.txt");
        pw.println("汗滴禾下土");
        pw.flush();
        pw.close();*/

        // 不刷新管道看不到结果
        /*FileOutputStream fos = new FileOutputStream("E://a.txt");
        PrintWriter ps = new PrintWriter(fos);
        ps.println("楚河");*/

        // 缓存读取流。将字符输入流转换成转换程带有缓存，且可以一次读取一行的缓存字符读取流
        FileReader fr = new FileReader("E://a.txt");
        BufferedReader br = new BufferedReader(fr);
        // readLine 读取一行
        System.out.println(br.readLine());
    }

}

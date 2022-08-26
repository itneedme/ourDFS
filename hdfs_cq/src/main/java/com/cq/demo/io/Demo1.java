package com.cq.demo.io;

import java.io.File;
import java.io.IOException;

// 文件创建
public class Demo1 {

    public static void main(String[] args) throws IOException {
        // 方案1 创建文件
        /*File file = new File("D://1.txt");
        boolean flag = file.createNewFile(); // 创建文件
        System.out.println(flag);*/

        // 方案2 创建文件夹
        File dir = new File("D://haha");
        // 区分mkdir(创建最后一个文件夹)和mkdirs(创建路径下的所有文件夹)
        // boolean res = dir.mkdir();

        // 方案3 创建文件夹下的文件
        File a = new File(dir, "a.txt");
        a.createNewFile();

        // 方案4 创建文件夹下的文件
        File b = new File("D://haha", "b.txt");
        b.createNewFile();

        a.delete();
        b.delete();

        // 文件的移动
        /*File file = new File("D://test.jpg");
        File newFile = new File("E://a.jpg");
        boolean res = file.renameTo(newFile);
        System.out.println(res);*/

        // 不同系统输出的结果不同
        System.out.println(File.pathSeparator);
        System.out.println(File.separator);



    }

}

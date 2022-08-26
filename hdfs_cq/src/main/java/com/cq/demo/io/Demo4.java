package com.cq.demo.io;

import java.io.File;

// 相对路径和绝对路径
public class Demo4 {

    // 绝对路径: 从盘符开始，是一个完整的路径
    // 相对路径:
    public static void main(String[] args) {
        File file1 = new File("c://a.txt");
        File file2 = new File("a.txt");
        System.out.println("File1的路径"+file1.getAbsolutePath());
        System.out.println("File2的路径"+file2.getAbsolutePath());
    }

}

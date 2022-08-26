package com.cq.demo.io;

import java.io.File;

// 文件遍历
public class Demo2 {

    public static void main(String[] args) {
        File e = new File("e:\\");
        File[] files = e.listFiles();
        listFiles(files);
    }

    // 对文件的遍历
    public static void listFiles(File[] files) {
        // 对文件夹进行深度遍历
        if(files != null && files.length > 0) {
            for(File file : files) {
                if(file.isFile()) {
                    if(file.getName().endsWith(".pdf")) {
                        // 找到了一个pdf文件
                        if(file.length() > 100 * 1024 * 1024) { // 输出大于100MB的文件
                            System.out.println("找到了一个pdf文件: " + file.getAbsolutePath());
                            // file.delete(); // 删除相应文件
                        }

                    }
                } else {
                    File[] files2 = file.listFiles();
                    listFiles(files2);
                }
            }
        }
    }

}

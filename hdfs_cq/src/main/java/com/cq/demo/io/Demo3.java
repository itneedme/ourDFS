package com.cq.demo.io;

import java.io.File;
import java.io.FileFilter;

// 文件过滤器
public class Demo3 {

    public static void main(String[] args) {
        File e = new File("e:\\");
        listFiles(e);
    }

    // 对文件的遍历
    public static void listFiles(File file) {
        // 1 创建一个过滤器规则并描述规则
        FileFilter filter = new AVIFileFilter();
        File[] files = file.listFiles(filter);
        if(files != null || files.length > 0) {
            for(File f : files) {
                if(f.isDirectory()) {
                    listFiles(f);
                } else {
                    System.out.println("发现了一个avi文件: " + f.getAbsolutePath());
                }
            }
        }

    }

    static class AVIFileFilter implements FileFilter {

        public boolean accept(File pathname) {
            if(pathname.getName().endsWith(".pdf") || pathname.isDirectory()) {
                return true;
            }
            return false;
        }
    }

}

package com.cq.util;

import java.io.File;

public class FileTree {

    public static void main(String[] args) {
        genDirTree("D:\\code_java\\hdfs_bytedance_cq\\hdfs_cq", 0, "");
    }

    public static void genDirTree(String path, int level, String dir) {
        level++;
        File file = new File(path);
        File[] files = file.listFiles();
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (files.length != 0) {
            for (File f : files) {
                if (f.isDirectory()) {
                    dir = f.getName();
                    System.out.println(levelSign(level) + dir);
                    genDirTree(f.getAbsolutePath(), level, dir);
                } else {
                    System.out.println(levelSign(level) + f.getName());
                }
            }
        }
    }

    //文件层级信息
    private static String levelSign(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ├─");
        for (int x = 0; x < level; x++) {
            sb.insert(0, " │   ");
        }
        return sb.toString();
    }

}

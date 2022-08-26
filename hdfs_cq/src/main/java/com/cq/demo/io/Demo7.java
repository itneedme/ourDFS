package com.cq.demo.io;

import java.io.FileWriter;
import java.io.IOException;

// 字符流写入文件
public class Demo7 {

    public static void main(String[] args) throws IOException {
        // Writer 将内容追加至文件底部
        // 这里的true参数决定是否追加
        FileWriter fw = new FileWriter("E://b.txt");
        // fw.write("锄禾日当午，汗滴禾下土");
        // fw.append("锄禾日当午"); // 并不是追加内容！可以返回FileWriter
        fw.append("11").append("vas").append("vrfefea");
        // 刷新缓存空间，强制性写到文件里
        fw.flush();
        fw.close();
    }

}

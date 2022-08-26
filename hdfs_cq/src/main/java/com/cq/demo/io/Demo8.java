package com.cq.demo.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// 字符流读取文件
public class Demo8 {

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("E://b.txt");
        /*int c = fr.read();
        System.out.println((char)c);
        c = fr.read();
        System.out.println((char)c);*/

        /*while(true) {
            int c = fr.read();
            if(c == -1) {
                break;
            }
            System.out.println((char)c);
        }*/

        char[] chars = new char[100];
        int len = fr.read(chars);
        // System.out.println((int)chars[0]);
        // System.out.println(new String(chars));
        String text = new String(chars, 0, len);
        System.out.println(text);
        /*String text = new String(chars);
        System.out.println(text);
        System.out.println(text.length());*/
        fr.close();
    }

}

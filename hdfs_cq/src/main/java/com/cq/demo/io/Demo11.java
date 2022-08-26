package com.cq.demo.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// 异常
public class Demo11 {

    public static void main(String[] args) throws FileNotFoundException {
        //收集异常信息
        try {
            String s = null;
            s.toString();
        }catch (Exception e){
            PrintWriter pw = new PrintWriter("c://bug.txt");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            pw.print(format.format(new Date()));
            e.printStackTrace(pw);
            pw.close();
        }
    }

}

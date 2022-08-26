package com.cq.service;

import org.jcp.xml.dsig.internal.SignerOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@Service
public class StandAloneService {

    /**
     * 判断文件类型是否符合规则
     * @param fileName 传入的文件名称
     * @return
     */
    public static boolean fileType(String fileName) {
        String[] fileType = {"MD","HTML","TXT","DOC","XLS","PPT","DOCX","XLSX","PPTX","JPG","PNG","PDF","TIFF","SWF",
                "FLV","RMVB","MP4","MVB","WMA","MP3","RAR","EXE","WORD","ZIP"};
        boolean flag = false;
        if (fileName.contains(".")) {
            fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String upperCase = fileName.toUpperCase();
            for (int i = 0; i < fileType.length; i++) {
                if (fileType[i].equals(upperCase)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**判断文件上传大小是否符合规则
     *
     * @param file 为接收的文件
     * @param size 为限制文件的大小
     * @param unit 为上传文件的限制单位
     * @return
     */
    public static boolean checkFileSize(MultipartFile file, int size, String unit) {
        long len = file.getSize();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("KB".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("MB".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("GB".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

}

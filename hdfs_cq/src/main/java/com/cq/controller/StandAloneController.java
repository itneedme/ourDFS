package com.cq.controller;

import com.cq.util.JsonUtil;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 实现单机版的 GET和 PUT功能
 */
@Controller
@RequestMapping("/standFile")
public class StandAloneController {

    private static final Logger logger = LoggerFactory.getLogger(StandAloneController.class);

    // 获得上传文件名称
    /*@Value("${spring.servlet.multipart.file-type}")
    private String fileType;*/

    /*// 上传文件的临时目录
    @Value("${spring.servlet.multipart.location}")
    private String noticeFileLocation;

    // 最大支持文件大小
    @Value("${spring.servlet.multipart.max-file-size}")
    private int maxFileSize;*/


    /*@RequestMapping(path = "/storage")
    @ResponseBody
    public String getFile() {

    }*/

    @RequestMapping(path = "/download/{pathname}", method = RequestMethod.GET)
    public String download(@PathVariable("pathname") String pathname, HttpServletResponse response, Model model) {
        // System.out.println(pathname);
        String path = "E://" + pathname;
        //得到要下载的文件
        File file = new File(path);
        //如果文件不存在进行处理
        if (!file.exists()) {
            return JsonUtil.getJSONString(404, "您要下载的文件并不存在");
        }
        // 将文件从硬盘读取到服务器
        InputStream inputStream = null;
        // 服务器将指定的内容输出到浏览器
        OutputStream outputStream = null;
        //创建缓冲区
        byte[] b = new byte[1024];
        int len = 0;
        try {
            //读取要下载的文件，保存到文件输入流
            inputStream = new FileInputStream(file);
            // 获取字节输出流对象
            outputStream = response.getOutputStream();
            // 与下载相关（后续继续研究）
            response.setContentType("application/force-download");
            String filename = file.getName();
            //设置响应头，控制浏览器下载该文件
            // URLEncoder说明：将中文、一些特殊符号（如/）等进行编码，分为URLEncoder和URLDecoder，此处采用前者
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength((int) file.length());
            //循环将输入流中的内容读取到缓冲区当中
            while ((len = inputStream.read(b)) != -1) {
                //输出缓冲区的内容到浏览器，实现文件下载
                outputStream.write(b, 0, len);
            }

        } catch (Exception e) {
            logger.error("单机文件下载失败。" + e.getMessage());
            return JsonUtil.getJSONString(500, "下载失败，请重新下载");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    logger.error("单机下载：输入流关闭失败");
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    logger.error("单机下载：输出流关闭失败");
                }
            }
        }
        return JsonUtil.getJSONString(200, "下载成功");
    }


    @RequestMapping(path = "/upload", method = RequestMethod.PUT)
    public String upload() {
        return "";
    }

    //文件上传

//FileName 为文件名

//file 为文件

//path 为文件的路径，最好是绝对路径

    /*@RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(value = "file", required = true) MultipartFile file,
                             @RequestParam(value = "name", required = true) String name,
                             HttpServletRequest request) {
        try {
            byte[] fileByte = file.getBytes();
            //获取文件的源文件名称
            String oldFileName = file.getOriginalFilename();
            //获取文件保存路径，此处可配置化，而不是写死，暂时写死
            String filePath = "D://upload//";

            //时间戳生成新的文件名，防止因同名文件被覆盖
            Date date = new Date();
            String newFileName = String.valueOf(date.getTime());

            //上传时间(系统当前时间)
            // String uploadTime = DateUtils.dateParseString(date);

            //文件后缀名
            String suffixName = oldFileName.substring(oldFileName.lastIndexOf("."));

            //组合生成新的文件名
            newFileName = newFileName + suffixName;
            //文件上传
            boolean flag = FileUtils.uploadFile(fileByte, filePath, newFileName);
            //文件上传成功之后，文件相关信息入库操作
            if (flag) {
                fileBean = new FileBean(newFileName, "夏炳成", uploadTime, filePath, oldFileName);
                //这里是文件数据入库操作，根据自己需求自己更改
                addFile(fileBean);
            }
        } catch (IOException ioException) {
            fileBean = new FileBean();
        }
        return fileBean;
    }*/


}

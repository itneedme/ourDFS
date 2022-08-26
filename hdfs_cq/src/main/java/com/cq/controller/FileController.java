package com.cq.controller;

import com.cq.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(path = "/upload/{parentId}", method = RequestMethod.GET)
    public String getUploadFilePage(@PathVariable(value = "parentId") int parentId) {
        return "/file/upload";
    }

    // 上传文件到云服务器
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile,
    int parentId, HttpServletResponse response, Model model) {
        // 校验有没有重命名的文件
        String originalName = multipartFile.getOriginalFilename();
        boolean res = fileService.checkSameName(originalName, 1, parentId);
        if(!res) {
            model.addAttribute("uploadFileMsg", "上传失败，请检查是否有重名文件");
            model.addAttribute("nullValue", parentId);
            return "/file/test1";
        }
        fileService.uploadFile(multipartFile, parentId);
        model.addAttribute("nullValue", parentId);
        return "/file/test1";
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public String getDownloadPage() {
        return "/file/download";
    }

    @RequestMapping(path = "/download/{id}", method = RequestMethod.GET)
    public String download(@PathVariable(value = "id") int id) throws IOException {
        fileService.downloadFile(id);
        return "/file/test";
    }


    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteFile(@PathVariable(value = "id") int id) {
        fileService.deleteFile(id);
        return "/file/test";
    }


}

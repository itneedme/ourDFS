package com.cq.controller;

import com.cq.entity.FilePath;
import com.cq.service.FileDirectoryService;
import com.cq.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fileDir")
public class FileDirectoryController {

    private static final Logger logger = LoggerFactory.getLogger(FileDirectoryController.class);

    @Autowired
    private FileDirectoryService fileDirectoryService;

    @Autowired
    private FileService fileService;

    // 查询所有文件夹
    @RequestMapping(value = {"/list/{parentId}", "/list"} , method = RequestMethod.GET)
    public String getFileDirPage(Model model,
    @PathVariable(value = "parentId", required = false) Integer parentId) {
        if(parentId == null) {
            parentId = 1;
        }
        Map<String, Object> res = fileDirectoryService.getDirectoryPage(parentId);
        if(res.containsKey("dirNullMsg")) {
            model.addAttribute("nullValue", parentId);
            return "/file/test1";
        }
        if(res.containsKey("dirErrorMsg")) {
            model.addAttribute("dirErrorMsg", res.get("dirErrorMsg"));
            logger.error("获取文件夹信息失败");
            return "/file/test";
        }
        List<FilePath> dirs = new ArrayList<>();
        List<Map<String, Object>> filePaths = new ArrayList<>();
        dirs = (List<FilePath>) res.get("dirMsg");
        dirs.forEach(filePath -> {
            Map<String, Object> map = new HashMap<>();
            map.put("filePath", filePath);
            filePaths.add(map);
        });
        model.addAttribute("filePaths", filePaths);
        return "/file/test";
    }

    @RequestMapping(path = "/add/{parentId}", method = RequestMethod.GET)
    public String getDirectoryPage() {
        return "/file/addDir";
    }

    // 增加文件夹
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String getFileDirPage(Model model, String name, Integer parentId) {
        if(parentId == null) {
            parentId = 1;
            model.addAttribute("nullValue", parentId);
            return "/file/test1";
        }
        // 校验有没有重命名的文件夹
        boolean ans = fileService.checkSameName(name, 0, parentId);
        if(!ans) {
            model.addAttribute("addDirErrorMsg", "上传失败，请检查是否有重名文件");
            model.addAttribute("nullValue", parentId);
            return "/file/test1";
        }
        Map<String, Object> res = fileDirectoryService.addDirectory(parentId, name);
        if(res.containsKey("addDirErrorMsg")) {
            model.addAttribute("addDirErrorMsg", res.get("addDirErrorMsg"));
            model.addAttribute("nullValue", parentId);
            return "/file/test1";
        }
        model.addAttribute("nullValue", parentId);
        model.addAttribute("addDirMsg", res.get("addDirMsg"));
        return "/file/test1";
    }

    // 修改文件夹的名称
    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    public String modifyDirName(Model model, String newName, String oldName, Integer parentId) {
        if(parentId == null) {
            parentId = 1;
        }
        Map<String, Object> res = fileDirectoryService.modifyDirName(parentId, oldName, newName);
        if(res.containsKey("changeDirErrorMsg")) {
            model.addAttribute("changeDirErrorMsg", res.get("changeDirErrorMsg"));
            return "/file/test1";
        }
        model.addAttribute("changeDirMsg", res.get("changeDirMsg"));
        return "/file/test1";
    }

    @RequestMapping(path = "/modify/{parentId}", method = RequestMethod.GET)
    public String getModifyNamePage() {
        return "/file/modify";
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDir(Model model, @PathVariable(value = "id") Integer id) {
        Map<String, Object> map = fileDirectoryService.deleteDir(id);
        if(map.containsKey("deleteDirErrorMsg")) {
            model.addAttribute("deleteDirErrorMsg", map.get("deleteDirErrorMsg"));
            return "/file/test";
        }
        model.addAttribute("deleteDirMsg", map.get("deleteDirMsg"));
        return "/file/test";
    }



}

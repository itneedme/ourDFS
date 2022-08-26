package com.cq.service;

import com.cq.controller.FileDirectoryController;
import com.cq.dao.FilePathMapper;
import com.cq.entity.FilePath;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.mockito.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileDirectoryService {

    @Autowired
    private FilePathMapper filePathMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileDirectoryService.class);

    // 获取到父目录文件夹下的所有文件
    public Map<String, Object> getDirectoryPage(int parentId) {
        Map<String, Object> map = new HashMap<>();
        if(parentId <= 0) {
            map.put("dirErrorMsg", "请求值不合法");
            return map;
        }
        List<FilePath> res = filePathMapper.selectDir(parentId);
        if(res.size() == 0) {
            map.put("dirNullMsg", "null");
            return map;
        }
        map.put("dirMsg", res);
        return map;
    }

    // 添加文件夹
    public Map<String, Object> addDirectory(int parentId, String name) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(name)) {
            map.put("addDirErrorMsg", "新增文件夹名称不允许为空");
            return map;
        }
        // 先查询有没有重名文件夹
        /*List<FilePath> res = filePathMapper.selectDir(parentId);
        for(int i = 0; i < res.size(); i++) {
            FilePath filePath = res.get(i);
            if(filePath.getName().equals(name)) {
                map.put("addDirErrorMsg", "新增文件夹名称和其他文件夹重名，请重新命名");
                return map;
            }
        }*/
        int ans = filePathMapper.insertDir(parentId, name);
        if(ans > 0) {
            map.put("addDirMsg", "文件新建成功");
            return map;
        } else {
            map.put("addDirErrorMsg", "文件新建失败");
            return map;
        }
    }

    // 修改文件夹的名称
    public Map<String, Object> modifyDirName(int parentId, String oldName, String newName) {
        Map<String, Object> map = new HashMap<>();
        if(parentId <= 0) {
            map.put("changeDirErrorMsg", "请求致不合法，请重试");
            return map;
        }
        if(StringUtils.isBlank(oldName)) {
            map.put("changeDirErrorMsg", "请求致不合法，请重试");
            return map;
        }
        if(StringUtils.isBlank(newName)) {
            map.put("changeDirErrorMsg", "修改的新文件夹名称不能为空");
            return map;
        }
        List<String> name = filePathMapper.selectNameByParentId(parentId);
        for(int i = 0; i < name.size(); i++) {
            if(Objects.equals(name.get(i), newName)) {
                map.put("changeDirErrorMsg", "您要修改的文件夹和已有的文件夹重名，请重新命名");
                return map;
            }
        }
        // 可以验证一下oldName是否存在
        int res = filePathMapper.changeDirName(parentId, oldName, newName);
        if(res > 0) {
            map.put("changeDirMsg", "文件夹名称修改成功");
            return map;
        } else {
            map.put("changeDirErrorMsg", "文件夹名称修改失败");
            return map;
        }
    }

    // 删除文件夹
    public Map<String, Object> deleteDir(int id) {
        Map<String, Object> map = new HashMap<>();
        if(id <= 0) {
            map.put("deleteDirErrorMsg", "请检验传入参数值是否合法");
            return map;
        }
        // 手写BFS广度搜索实现
        Queue<Integer> queue = new LinkedList<>();
        queue.add(id);
        while(!queue.isEmpty()) {
            // 弹出这个节点
            int i = queue.poll();
            // 将这个点的状态更新为1
            filePathMapper.updateDirStatus(i);
            // 获取这个节点下的子树的节点
            List<Integer> res = filePathMapper.selectIdByParentId(i);
            /*boolean flag = true;
            if(res == null) {
                flag = false;
                break;
            }*/
            if(res.size() > 0) {
                for(int j = 0; j < res.size(); j++) {
                    queue.add(res.get(j));
                }
            }
        }
        map.put("deleteDirMsg", "删除文件夹成功");
        return map;
    }

}

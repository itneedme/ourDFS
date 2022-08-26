package com.cq.service;

import com.cq.dao.*;
import com.cq.entity.DataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private FileService fileService;

    @Autowired
    private DataNodeMapper dataNodeMapper;

    @Autowired
    private DataInfoMapper dataInfoMapper;

    @Autowired
    private FileDataInfoMapper fileDataInfoMapper;

    @Autowired
    private FileMetaMapper fileMetaMapper;

    @Autowired
    private FilePathMapper filePathMapper;

    /*// 检查空间占用率是否小于20%，小于返回true，不小于返回false
    public boolean checkOccupancyRate(int id) {
        int freeSpace = dataNodeMapper.selectFreeSpaceById(id);
        int allSpace = dataNodeMapper.selectAllSpaceById(id);
        double occupy = (double) freeSpace / allSpace;
        return occupy < 0.2;
    }

    // 当容量小于20%时会触发这个函数或者手动实现功能
    public void moveFile(int id) {
        if(checkOccupancyRate(id)) {
            int idTo = 7;
            fromOneServerToAnother(id, idTo);
        }
    }*/

    // 将文件从一个移动到另一个，实现文件的转移
    public void fromOneServerToAnother(int dataNodeFrom, int dataNodeTo) throws IOException {
        String urlPath = dataNodeMapper.selectNameById(dataNodeFrom);
        DataInfo dataInfo = dataInfoMapper.selectMaxSizeByDataNodeId(dataNodeFrom);
        int dataInfoDataId = dataInfo.getDataId();
        int dataInfoId = dataInfo.getId();
        dataInfoMapper.updateDataNodeIdByIdAndDataId(dataInfoId, dataInfoDataId, dataNodeTo);
        int metaId = fileDataInfoMapper.selectMetaIdById(dataInfoDataId);
        int fileId = fileMetaMapper.selectFileIdById(metaId);
        String name = filePathMapper.selectNameById(fileId);
        List<String> fileNames = fileDataInfoMapper.selectNameByMetaId(metaId);
        // 1 将文件从一台服务器下载到本地
        fileService.downloadFileFromServer(urlPath, name, "E:/downloadTmp/");
        // 2 从服务器上删除
        for(String fileName: fileNames) {
            String name1 = fileName.substring(fileName.lastIndexOf("/") + 1);
            fileService.deleteFileFromServer(urlPath, name1);
            // 2 上传
            // uploadFileToOneServer(fileName, dataNodeTo);
            fileService.uploadToServer(fileName, dataNodeTo);
        }
    }


}

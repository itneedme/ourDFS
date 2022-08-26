package com.cq.dao;

import com.cq.entity.FileMeta;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMetaMapper {

    int insertFileMeta(FileMeta fileMeta);

    int selectIdByMD5(String md5);

    int getIdByFileId(int fileId);

    int updateStatusByFileId(int fileId);

    int selectIdByFileId(int fileId);

    int selectFileIdById(int id);

}

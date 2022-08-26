package com.cq.dao;

import com.cq.entity.FileDataInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDataInfoMapper {

    // 根据md5值找到相应的文件
    int selectIdByMD5(String md5);

    int insert(FileDataInfo fileDataInfo);

    List<Integer> getIdByMetaId(int metaId);

    String selectNameById(int id);

    String selectMd5ById(int id);

    List<Integer> selectIdByMetaId(int metaId);

    int updateStatusById(int id);

    int selectMetaIdById(int id);

    List<String> selectNameByMetaId(int metaId);

}

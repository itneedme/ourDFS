package com.cq.dao;

import com.cq.entity.DataInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataInfoMapper {

    int insert(DataInfo dataInfo);

    List<Integer> selectDataNodeIdByMd5(String md5);

    List<DataInfo> selectByDataId(int dataId);

    int updateStatusById(int id);

    DataInfo selectMaxSizeByDataNodeId(int dataNodeId);

    int updateDataNodeIdByIdAndDataId(int id, int dataId, int newDataNodeId);

}

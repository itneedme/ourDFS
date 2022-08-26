package com.cq.dao;

import com.cq.entity.DataNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataNodeMapper {

    DataNode selectById(int id);

    int updateFreeSpaceById(int id, int freeSpace);

    List<DataNode> selectAllSpaces();

    String selectNameById(int id);

    int selectAllSpaceById(int id);

    int selectFreeSpaceById(int id);

}

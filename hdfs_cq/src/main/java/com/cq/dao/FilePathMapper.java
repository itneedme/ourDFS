package com.cq.dao;

import com.cq.entity.FilePath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FilePathMapper {

    // 文件夹操作
    List<FilePath> selectDir(int parentId);

    int insertDir(int parentId, String name);

    int changeDirName(int parentId, String oldName, String newName);

    int updateDirStatus(int id);

    List<Integer> selectIdByParentId(int parentId);

    // 文件操作
    // 插入一个文件记录
    int insertFile(int parentId, String name);

    int selectIdByParentIdAndName(int parentId, String name);

    String selectNameById(int id);

    // 查询是否有相同名字的文件
    FilePath selectIdByNameAndType(String name, int type, int parentId);

    int updateStatusById(int id);

    int selectParentIdById(int id);

    List<String> selectNameByParentId(int parentId);

}

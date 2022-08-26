package com.cq.entity;

import java.util.Date;

public class FilePath {

    private int id;
    private String name;
    private int type;
    private int status;
    private Date createTime;
    private int parentId;

    public FilePath() {
    }

    public FilePath(int id, String name, int type, int status, Date createTime, int parentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "FilePath{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", parentId=" + parentId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}

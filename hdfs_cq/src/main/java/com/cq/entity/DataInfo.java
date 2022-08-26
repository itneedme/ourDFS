package com.cq.entity;

public class DataInfo {

    private int id;
    private int dataId;
    private int dataNodeId;
    private String name;
    private String md5;
    private int size;
    private int version;
    private int status;

    public DataInfo() {
    }

    public DataInfo(int id, int dataId, int dataNodeId, String name, String md5, int size, int version, int status) {
        this.id = id;
        this.dataId = dataId;
        this.dataNodeId = dataNodeId;
        this.name = name;
        this.md5 = md5;
        this.size = size;
        this.version = version;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "id=" + id +
                ", dataId=" + dataId +
                ", dataNodeId=" + dataNodeId +
                ", name='" + name + '\'' +
                ", md5='" + md5 + '\'' +
                ", size=" + size +
                ", version=" + version +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getDataNodeId() {
        return dataNodeId;
    }

    public void setDataNodeId(int dataNodeId) {
        this.dataNodeId = dataNodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

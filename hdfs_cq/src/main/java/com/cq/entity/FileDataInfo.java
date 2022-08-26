package com.cq.entity;

public class FileDataInfo {

    private int id;
    private int metaId;
    private String name;
    private int order;
    private int size;
    private String md5;
    private int status;
    private int version;
    private int data1;
    private int version1;
    private int data2;
    private int version2;
    private int data3;
    private int version3;

    public FileDataInfo() {
    }

    public FileDataInfo(int id, int metaId, String name, int order, int size, String md5, int status, int version, int data1, int version1, int data2, int version2, int data3, int version3) {
        this.id = id;
        this.metaId = metaId;
        this.name = name;
        this.order = order;
        this.size = size;
        this.md5 = md5;
        this.status = status;
        this.version = version;
        this.data1 = data1;
        this.version1 = version1;
        this.data2 = data2;
        this.version2 = version2;
        this.data3 = data3;
        this.version3 = version3;
    }

    @Override
    public String toString() {
        return "FileDataInfo{" +
                "id=" + id +
                ", metaId=" + metaId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", size=" + size +
                ", md5='" + md5 + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", data1=" + data1 +
                ", version1=" + version1 +
                ", data2=" + data2 +
                ", version2=" + version2 +
                ", data3=" + data3 +
                ", version3=" + version3 +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMetaId() {
        return metaId;
    }

    public void setMetaId(int metaId) {
        this.metaId = metaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getVersion1() {
        return version1;
    }

    public void setVersion1(int version1) {
        this.version1 = version1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }

    public int getVersion2() {
        return version2;
    }

    public void setVersion2(int version2) {
        this.version2 = version2;
    }

    public int getData3() {
        return data3;
    }

    public void setData3(int data3) {
        this.data3 = data3;
    }

    public int getVersion3() {
        return version3;
    }

    public void setVersion3(int version3) {
        this.version3 = version3;
    }
}

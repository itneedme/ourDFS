package com.cq.entity;

public class FileMeta {

    private int id;
    private int fileId;
    private String suffix;
    private int size;
    private String md5;
    private int status;
    private int version;

    public FileMeta() {
    }

    public FileMeta(int id, int fileId, String suffix, int size, String md5, int status, int version) {
        this.id = id;
        this.fileId = fileId;
        this.suffix = suffix;
        this.size = size;
        this.md5 = md5;
        this.status = status;
        this.version = version;
    }

    @Override
    public String toString() {
        return "FileMeta{" +
                "id=" + id +
                ", fileId=" + fileId +
                ", suffix='" + suffix + '\'' +
                ", size=" + size +
                ", md5='" + md5 + '\'' +
                ", status=" + status +
                ", version=" + version +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
}

package com.cq.entity;

public class DataNode {

   private int id;
   private String name;
   private int status;
   private int freeSpace;
   private int allSpace;

    public DataNode() {
    }

    public DataNode(int id, String name, int status, int freeSpace, int allSpace) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.freeSpace = freeSpace;
        this.allSpace = allSpace;
    }

    @Override
    public String toString() {
        return "DataNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", freeSpace=" + freeSpace +
                ", allSpace=" + allSpace +
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public int getAllSpace() {
        return allSpace;
    }

    public void setAllSpace(int allSpace) {
        this.allSpace = allSpace;
    }
}

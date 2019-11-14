package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/5.
 */

public class ImagesBean {
    private String path;
    private int num;

    public ImagesBean(String path, int num) {
        this.path = path;
        this.num = num;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

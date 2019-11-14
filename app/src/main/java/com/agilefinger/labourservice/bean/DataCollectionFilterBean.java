package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/7/23.
 */

public class DataCollectionFilterBean {
    private int id;
    private String name;
    private boolean isCheck;

    public DataCollectionFilterBean(int id, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

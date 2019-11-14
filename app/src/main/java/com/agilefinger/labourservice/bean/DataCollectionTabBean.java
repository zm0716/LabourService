package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/7/23.
 */

public class DataCollectionTabBean {
    private String id;
    private String name;
    private String isbatch;
    private boolean isCheck;

    //张孟加  修改
//    private long count;
//    private long number;
//
//    public long getCount() {
//        return count;
//    }
//
//    public void setCount(long count) {
//        this.count = count;
//    }
//
//    public long getNumber() {
//        return number;
//    }

//    public void setNumber(long number) {
//        this.number = number;
//    }

    public String getIsbatch() {
        return isbatch;
    }

    public void setIsbatch(String isbatch) {
        this.isbatch = isbatch;
    }

    public DataCollectionTabBean(String id, String name, String isbatch, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isbatch = isbatch;
//        this.count = count;
//        this.number = number;
        this.isCheck = isCheck;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

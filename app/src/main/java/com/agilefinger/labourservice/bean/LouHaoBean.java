package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LouHaoBean implements Serializable{
    private String title;
    private String id;
    private List<BuildMoreImageBean.DataBean.ListBean> mList;

    public LouHaoBean(String title, String id, List<BuildMoreImageBean.DataBean.ListBean> mList) {
        this.title = title;
        this.mList = mList;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BuildMoreImageBean.DataBean.ListBean> getmList() {
        return mList;
    }

    public void setmList(List<BuildMoreImageBean.DataBean.ListBean> mList) {
        this.mList = mList;
    }
}

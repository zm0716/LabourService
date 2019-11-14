package com.agilefinger.labourservice.bean;

import java.util.List;

public class DateImage2Bean {
    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    private List<BuildMoreImageBean.DataBean.ListBean> mList;

    public DateImage2Bean(String time, int type, List<BuildMoreImageBean.DataBean.ListBean> mList) {
        this.time = time;
        this.mList = mList;
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<BuildMoreImageBean.DataBean.ListBean> getmList() {
        return mList;
    }

    public void setmList(List<BuildMoreImageBean.DataBean.ListBean> mList) {
        this.mList = mList;
    }
}

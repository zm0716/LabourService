package com.agilefinger.labourservice.bean;

import java.util.ArrayList;
import java.util.List;

public class DateImageBean {
    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    private List<MoreImgBean.DataBean.ListBean> mList;

    public DateImageBean(String time, int type, List<MoreImgBean.DataBean.ListBean> mList) {
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

    public List<MoreImgBean.DataBean.ListBean> getmList() {
        return mList;
    }

    public void setmList(List<MoreImgBean.DataBean.ListBean> mList) {
        this.mList = mList;
    }
}

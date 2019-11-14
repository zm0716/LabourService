package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FromDataImageBean implements Serializable {
    private String title;
    private int type;
    private ArrayList<MoreImgBean.DataBean.ListBean> DateBean;

    public FromDataImageBean(String title, int type, ArrayList<MoreImgBean.DataBean.ListBean> dateBean) {
        this.title = title;
        this.type = type;
        DateBean = dateBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<MoreImgBean.DataBean.ListBean> getDateBean() {
        return DateBean;
    }

    public void setDateBean(ArrayList<MoreImgBean.DataBean.ListBean> dateBean) {
        DateBean = dateBean;
    }
}

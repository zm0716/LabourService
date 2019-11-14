package com.agilefinger.labourservice.bean;

import java.util.List;

/**
 * Created by 86251 on 2019/6/6.
 */

public class ReadBean {
    private int count;
    private List<StaffBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StaffBean> getList() {
        return list;
    }

    public void setList(List<StaffBean> list) {
        this.list = list;
    }
}

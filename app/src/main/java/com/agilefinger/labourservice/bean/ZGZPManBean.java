package com.agilefinger.labourservice.bean;

import java.util.List;

/**
 * Created by 86251 on 2019/6/6.
 */

public class ZGZPManBean {
    private List<StaffBean> correct_man_list;
    private List<StaffBean> assign_man_list;

    public List<StaffBean> getCorrect_man_list() {
        return correct_man_list;
    }

    public void setCorrect_man_list(List<StaffBean> correct_man_list) {
        this.correct_man_list = correct_man_list;
    }

    public List<StaffBean> getAssign_man_list() {
        return assign_man_list;
    }

    public void setAssign_man_list(List<StaffBean> assign_man_list) {
        this.assign_man_list = assign_man_list;
    }
}

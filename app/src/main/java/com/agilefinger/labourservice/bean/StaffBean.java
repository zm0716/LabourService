package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.io.Serializable;

/**
 * Created by 86251 on 2019/5/30.
 */

public class StaffBean implements PickerView.PickerItem, Serializable {
    private boolean isAdd = false;
    private boolean isCheck = false;
    private String id;
    private String portrait;
    private String name;
    private String head_img;

    public StaffBean() {
    }

    public StaffBean(boolean isAdd, boolean isCheck, String id, String portrait, String name, String head_img) {
        this.isAdd = isAdd;
        this.isCheck = isCheck;
        this.id = id;
        this.portrait = portrait;
        this.name = name;
        this.head_img = head_img;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    @Override
    public String getText() {
        return name;
    }
}

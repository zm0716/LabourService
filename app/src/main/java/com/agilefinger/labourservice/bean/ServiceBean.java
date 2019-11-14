package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.io.Serializable;
import java.util.ArrayList;

public class ServiceBean implements PickerView.PickerItem, Serializable {
    private boolean isCheck = false;
    private String id;
    private String name;
    private ArrayList<KBService2Bean> mlist;

    public ArrayList<KBService2Bean> getMlist() {
        return mlist;
    }

    public void setMlist(ArrayList<KBService2Bean> mlist) {
        this.mlist = mlist;
    }

    public ServiceBean() {
    }

    public ServiceBean(boolean isAdd, boolean isCheck, String id, String portrait, String name) {
        this.isCheck = isCheck;
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getText() {
        return name;
    }
}
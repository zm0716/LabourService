package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.io.Serializable;

public class KanBanTypeBean implements PickerView.PickerItem, Serializable {
    private boolean isCheck;
    private String name;
//    private int id;
    private String code;

    public KanBanTypeBean(boolean isCheck, String name, String code) {
        this.isCheck = isCheck;
        this.name = name;
        this.code = code;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return null;
    }
}

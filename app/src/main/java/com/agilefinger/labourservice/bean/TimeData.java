package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

public class TimeData implements PickerView.PickerItem{
    private String time;
    private int index;

    public TimeData(String time, int index) {
        this.time = time;
        this.index = index;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getText() {
        return time;
    }
}

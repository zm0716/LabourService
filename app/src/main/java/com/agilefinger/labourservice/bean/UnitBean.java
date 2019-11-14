package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

/**
 * Created by 86251 on 2019/6/11.
 */

public class UnitBean implements PickerView.PickerItem {
    private String unit;

    public UnitBean(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String getText() {
        return unit;
    }
}

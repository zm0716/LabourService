package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

/**
 * Created by 86251 on 2019/6/11.
 */

public class ConstructionProcessBean implements PickerView.PickerItem {
    private String progress;

    public ConstructionProcessBean(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String getText() {
        return progress;
    }
}

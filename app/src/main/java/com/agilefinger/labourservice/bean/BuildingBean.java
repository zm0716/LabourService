package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.io.Serializable;

public class BuildingBean implements PickerView.PickerItem, Serializable {
    private boolean isAdd;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    private boolean isChecked;
    private String name;
    private String building_id;
    private String floor_id;
    private String inside_id;

    public BuildingBean() {
    }

    public BuildingBean(boolean isAdd, boolean isChecked, String name, String building_id, String floor_id, String inside_id) {
        this.isAdd = isAdd;
        this.isChecked = isChecked;
        this.name = name;
        this.building_id = building_id;
        this.floor_id = floor_id;
        this.inside_id = inside_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getInside_id() {
        return inside_id;
    }

    public void setInside_id(String inside_id) {
        this.inside_id = inside_id;
    }

    @Override
    public String getText() {
        return null;
    }
}

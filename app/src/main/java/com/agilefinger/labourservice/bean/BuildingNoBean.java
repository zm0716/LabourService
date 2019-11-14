package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86251 on 2019/5/30.
 */

public class BuildingNoBean implements Serializable{
    private String id;
    private String name;
    private List<FloorBean> floor;
    private List<DirectionBean> surface;
    private boolean isCheck;

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

    public List<FloorBean> getFloor() {
        return floor;
    }

    public void setFloor(List<FloorBean> floor) {
        this.floor = floor;
    }

    public List<DirectionBean> getSurface() {
        return surface;
    }

    public void setSurface(List<DirectionBean> surface) {
        this.surface = surface;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

package com.agilefinger.labourservice.bean;

public class BuindingFIllBean {
    private String building_id;
    private String floor_id;
    private String inside_id;

    public BuindingFIllBean(String building_id, String floor_id, String inside_id) {
        this.building_id = building_id;
        this.floor_id = floor_id;
        this.inside_id = inside_id;
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
}

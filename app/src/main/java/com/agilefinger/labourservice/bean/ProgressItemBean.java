package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 86251 on 2019/6/5.
 */
@DatabaseTable(tableName = "ProgressItem")
public class ProgressItemBean implements Serializable{
    @DatabaseField(useGetSet = true, generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true, columnName = "pid")
    private String pid; //
    @DatabaseField(useGetSet = true, columnName = "building_id")
    private String building_id;//楼ID
    @DatabaseField(useGetSet = true, columnName = "building_no")
    private String building_no;//楼号
    @DatabaseField(useGetSet = true, columnName = "quantity")
    private String quantity;//完成量
    @DatabaseField(useGetSet = true, columnName = "rate")
    private String rate;//完成比例
    @DatabaseField(useGetSet = true, columnName = "group")
    private String group;//施工班组
    @DatabaseField(useGetSet = true, columnName = "num")
    private String num;//人数
    @DatabaseField(useGetSet = true, columnName = "unit")
    private String unit;//单位
    @DatabaseField(useGetSet = true, columnName = "order")
    private String order;//施工工序
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(String building_no) {
        this.building_no = building_no;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

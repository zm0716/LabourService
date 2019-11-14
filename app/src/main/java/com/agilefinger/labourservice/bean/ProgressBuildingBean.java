package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86251 on 2019/6/5.
 */

public class ProgressBuildingBean implements Serializable {
     private String id;
     private String name;
    private List<ProgressItemBean>item;

    public List<ProgressItemBean> getItem() {
        return item;
    }

    public void setItem(List<ProgressItemBean> item) {
        this.item = item;
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
}

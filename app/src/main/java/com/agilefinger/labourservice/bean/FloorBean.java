package com.agilefinger.labourservice.bean;

import java.io.Serializable;

/**
 * Created by 86251 on 2019/5/30.
 */

public class FloorBean implements Serializable{

    private String id;
    private String name;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

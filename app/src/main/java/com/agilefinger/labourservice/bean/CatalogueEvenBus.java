package com.agilefinger.labourservice.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @author yangchao
 * @description: TODO
 * @date 2019-08-17
 */
public class CatalogueEvenBus {
    private String id;
    private int coupon;
    private String childId;
    private JSONObject childJsonObject;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public JSONObject getChildJsonObject() {
        return childJsonObject;
    }

    public void setChildJsonObject(JSONObject childJsonObject) {
        this.childJsonObject = childJsonObject;
    }

    public CatalogueEvenBus() {
    }

    public CatalogueEvenBus(String id, int coupon) {
        this.id = id;
        this.coupon = coupon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }
}

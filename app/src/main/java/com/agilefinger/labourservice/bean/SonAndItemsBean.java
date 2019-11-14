package com.agilefinger.labourservice.bean;


import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author yangchao
 * @description: TODO
 * @date 2019-08-17
 */
public class SonAndItemsBean implements Serializable {
    private JSONObject jsonObject;
    private String mct_id;
    private String mct_p_id;
    private String name;
    private String mct_is_batch;//n是没有批量按钮

    private String refush;

    public String getRefush() {
        return refush;
    }

    public void setRefush(String refush) {
        this.refush = refush;
    }

    /**
     * item
     */
    private String mci_id;
    private String mci_mct_id;
    private String mci_no;
    private int mci_min_point;//最少数量
    private int p_count;//已有数量

    private boolean isSon;//item不会展示按钮//son判断mct_is_batch

    public void addData(JSONObject jsonObject, boolean isSon) {
        this.jsonObject = jsonObject;
        this.isSon = isSon;
        if (isSon) {
            this.mct_id = jsonObject.getString("mct_id");
            this.mct_p_id = jsonObject.getString("mct_p_id");
            this.name = jsonObject.getString("mct_name");
            this.mct_is_batch = jsonObject.getString("mct_is_batch");
        } else {
            this.mci_id = jsonObject.getString("mci_id");
            this.mci_mct_id = jsonObject.getString("mci_mct_id");
            this.name = jsonObject.getString("mci_name");
            this.mci_no = jsonObject.getString("mci_no");
            this.mci_min_point = jsonObject.getInteger("mci_min_point");
            this.p_count = jsonObject.getInteger("p_count");
        }
        if (TextUtils.isEmpty(name)) {
            this.name = jsonObject.getString("name");
        }

    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getMct_id() {
        return mct_id;
    }

    public void setMct_id(String mct_id) {
        this.mct_id = mct_id;
    }

    public String getMct_p_id() {
        return mct_p_id;
    }

    public void setMct_p_id(String mct_p_id) {
        this.mct_p_id = mct_p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMct_is_batch() {
        return mct_is_batch;
    }

    public void setMct_is_batch(String mct_is_batch) {
        this.mct_is_batch = mct_is_batch;
    }

    public String getMci_id() {
        return mci_id;
    }

    public void setMci_id(String mci_id) {
        this.mci_id = mci_id;
    }

    public String getMci_mct_id() {
        return mci_mct_id;
    }

    public void setMci_mct_id(String mci_mct_id) {
        this.mci_mct_id = mci_mct_id;
    }


    public String getMci_no() {
        return mci_no;
    }

    public void setMci_no(String mci_no) {
        this.mci_no = mci_no;
    }

    public int getMci_min_point() {
        return mci_min_point;
    }

    public void setMci_min_point(int mci_min_point) {
        this.mci_min_point = mci_min_point;
    }

    public int getP_count() {
        return p_count;
    }

    public void setP_count(int p_count) {
        this.p_count = p_count;
    }

    public boolean isSon() {
        return isSon;
    }

    public void setSon(boolean son) {
        isSon = son;
    }
}

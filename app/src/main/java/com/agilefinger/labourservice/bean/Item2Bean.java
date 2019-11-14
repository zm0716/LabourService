package com.agilefinger.labourservice.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @author yangchao
 * @description: TODO
 * @date 2019-08-15
 */
public class Item2Bean {

    private FatherBean fatherBean;
    private String mci_id;
    private String mci_mct_id;
    private String mci_name;
    private String mci_no;
    private long mci_min_point;
    private long p_count;
    private boolean isItem;



    //son的
    private String mct_id;
    private String mct_p_id;
    private String mct_name;
    private String mct_is_batch;
    public Item2Bean(JSONObject jsonObject, boolean isItem) {
        this.isItem = isItem;
        if (isItem){
            this.mci_id = jsonObject.getString("mci_id");
            this.mci_mct_id = jsonObject.getString("mci_mct_id");
            this.mci_name = jsonObject.getString("mci_name");
            this.mci_no = jsonObject.getString("mci_no");
            this.mci_min_point = jsonObject.getLong("mci_min_point");
            this.p_count = jsonObject.getLong("p_count");
        }else {
            this.mct_id=jsonObject.getString("mct_id");
            this.mct_p_id=jsonObject.getString("mct_p_id");
            this.mct_name=jsonObject.getString("mct_name");
            this.mct_is_batch=jsonObject.getString("mct_is_batch");
        }

    }

    public String getMct_is_batch() {
        return mct_is_batch;
    }

    public void setMct_is_batch(String mct_is_batch) {
        this.mct_is_batch = mct_is_batch;
    }

    public boolean isItem() {
        return isItem;
    }

    public void setItem(boolean item) {
        isItem = item;
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

    public String getMct_name() {
        return mct_name;
    }

    public void setMct_name(String mct_name) {
        this.mct_name = mct_name;
    }

    public FatherBean getFatherBean() {
        return fatherBean;
    }

    public void setFatherBean(FatherBean fatherBean) {
        this.fatherBean = fatherBean;
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

    public String getMci_name() {
        return mci_name;
    }

    public void setMci_name(String mci_name) {
        this.mci_name = mci_name;
    }

    public String getMci_no() {
        return mci_no;
    }

    public void setMci_no(String mci_no) {
        this.mci_no = mci_no;
    }

    public long getMci_min_point() {
        return mci_min_point;
    }

    public void setMci_min_point(long mci_min_point) {
        this.mci_min_point = mci_min_point;
    }

    public long getP_count() {
        return p_count;
    }

    public void setP_count(long p_count) {
        this.p_count = p_count;
    }
}

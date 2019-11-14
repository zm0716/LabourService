package com.agilefinger.labourservice.bean;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangchao
 * @description: TODO
 * @date 2019-08-15
 */
public class FatherBean {
    private String mct_id;
    private String mct_p_id;
    private String mct_name;
    private String mct_is_batch;
    private FatherBean fatherBean;
    private  List<Item2Bean> items;
    private String path="";
    private String path_id="";
    public FatherBean(JSONObject object) {
        this.mct_id=object.getString("mct_id");
        this.mct_p_id=object.getString("mct_p_id");
        this.mct_name=object.getString("mct_name");
        this.mct_is_batch=object.getString("mct_is_batch");
    }

    public String getPath_id() {
        return path_id;
    }

    public void setPath_id(String path_id) {
        this.path_id = path_id;
    }

    public String getPath() {
        return path;
    }

    public String getMct_is_batch() {
        return mct_is_batch;
    }

    public void setMct_is_batch(String mct_is_batch) {
        this.mct_is_batch = mct_is_batch;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Item2Bean> getItems() {
        return items==null ? new ArrayList<Item2Bean>():items;
    }

    public void setItems(List<Item2Bean> items) {
        this.items = items;
    }

    public FatherBean getFatherBean() {
        return fatherBean;
    }

    public void setFatherBean(FatherBean fatherBean) {
        this.fatherBean = fatherBean;
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

}

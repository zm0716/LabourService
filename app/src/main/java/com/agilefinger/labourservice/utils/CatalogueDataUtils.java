package com.agilefinger.labourservice.utils;


import android.os.Bundle;

import com.agilefinger.labourservice.bean.Item2Bean;
import com.agilefinger.labourservice.bean.SonAndItemsBean;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qbt
 * @description: TODO
 * @date 2019-08-17
 */
public class CatalogueDataUtils {

    private static CatalogueDataUtils utils = new CatalogueDataUtils();

    public static CatalogueDataUtils getInstance() {
        return utils;
    }

    private JSONObject jsonObject;
    private List<Item2Bean> listAllFather;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public List<Item2Bean> getListAllFather() {
        return listAllFather;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }


    public void setJsonFather(List<Item2Bean> listAllFather) {
        this.listAllFather = listAllFather;
    }

    public ArrayList<SonAndItemsBean> next() {
        ArrayList<SonAndItemsBean> list = new ArrayList<>();
        if (null == jsonObject) {
            return list;
        }
        JSONArray son = jsonObject.getJSONArray("son");
        if (son != null && !son.isEmpty()) {
            for (int i = 0; i < son.size(); i++) {
                if (son.getJSONObject(i).getString("mct_front_show").equals("y")) {
                    SonAndItemsBean e = new SonAndItemsBean();
                    e.addData(son.getJSONObject(i), true);
                    list.add(e);
                }
            }
        }
        JSONArray items = jsonObject.getJSONArray("items");
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                SonAndItemsBean e = new SonAndItemsBean();
                e.addData(items.getJSONObject(i), false);
                list.add(e);
            }
        }
        return list;
    }

    public ArrayList<SonAndItemsBean> next2() {
        ArrayList<SonAndItemsBean> list = new ArrayList<>();
        if (null == jsonObject) {
            return list;
        }
        JSONArray son = jsonObject.getJSONArray("son");
        if (son != null && !son.isEmpty()) {
            for (int i = 0; i < son.size(); i++) {
                if (son.getJSONObject(i).getString("mct_front_show").equals("y")) {
                    SonAndItemsBean e = new SonAndItemsBean();
                    e.addData(son.getJSONObject(i), true);

//                    JSONArray items = son.getJSONObject(i).getJSONArray("items");
//                    for (int j = 0; i < items.size(); i++) {
//                        SonAndItemsBean e1 = new SonAndItemsBean();
//                        e1.addData(items.getJSONObject(i), false);
//                        e1.setP_count(items.getJSONObject(i).getInteger("p_count") + 1);
//                    }
//                    e.setP_count(son.getJSONObject(i).getInteger("p_count") + 1);
                    list.add(e);
                }
            }
        }
        JSONArray items = jsonObject.getJSONArray("items");
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                SonAndItemsBean e = new SonAndItemsBean();
                e.addData(items.getJSONObject(i), false);
                e.setP_count(items.getJSONObject(i).getInteger("p_count") + 1);
                list.add(e);
            }
        }
        return list;
    }


    public ArrayList<SonAndItemsBean> getItems(SonAndItemsBean item) {
        ArrayList<SonAndItemsBean> list = new ArrayList<>();
        if (item.isSon()) {
            getItem(item.getJsonObject(), list);
        }
        return list;
    }

    private void getItem(JSONObject jsonObject, ArrayList<SonAndItemsBean> list) {
        JSONArray son = jsonObject.getJSONArray("son");
        if (son != null && !son.isEmpty()) {
            for (int i = 0; i < son.size(); i++) {
                getItem(son.getJSONObject(i), list);
            }
        }
        JSONArray items = jsonObject.getJSONArray("items");
        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                SonAndItemsBean e = new SonAndItemsBean();
                e.addData(items.getJSONObject(i), false);
                list.add(e);
            }

        }
    }
}

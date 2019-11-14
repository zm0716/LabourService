package com.agilefinger.labourservice.bean;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 86251 on 2019/6/6.
 */

public class ViewBean {
    private String id;
    private String name;
    private View view;
    private List<View> child = new ArrayList<>();

    public ViewBean(String id,String name, View view) {
        this.id=id;
        this.name=name;
        this.view=view;
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

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<View> getChild() {
        return child;
    }

    public void setChild(List<View> child) {
        this.child = child;
    }
}

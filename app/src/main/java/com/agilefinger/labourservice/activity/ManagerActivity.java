package com.agilefinger.labourservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class ManagerActivity extends BaseActivity {

    @Override
    public int initLayoutView() {
        return R.layout.activity_manager;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("移交项目经理", false, false, "");
    }
}

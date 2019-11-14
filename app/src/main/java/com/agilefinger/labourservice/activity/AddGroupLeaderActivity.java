package com.agilefinger.labourservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class AddGroupLeaderActivity extends BaseActivity {

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_group_leader;
    }
    @Override
    public void initView() {
        super.initView();
        setToolbar("添加组长", false, false, "");
    }
}

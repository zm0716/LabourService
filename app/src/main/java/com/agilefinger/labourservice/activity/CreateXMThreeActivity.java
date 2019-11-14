package com.agilefinger.labourservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class CreateXMThreeActivity extends BaseActivity {

    @Override
    public int initLayoutView() {
        return R.layout.activity_create_xmthree;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("创建项目(3/6)", false, false, "");
    }
}

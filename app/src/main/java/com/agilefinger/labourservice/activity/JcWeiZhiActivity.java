package com.agilefinger.labourservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class JcWeiZhiActivity extends BaseActivity {

    @Override
    public int initLayoutView() {
        return R.layout.activity_jc_wei_zhi;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("检测位置", false, false, "");
    }
}

package com.agilefinger.labourservice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class RemovemembersActivity extends BaseActivity {
    @Override
    public int initLayoutView() {
        return R.layout.activity_removemembers;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("移除成员", false, false, "");
    }
}

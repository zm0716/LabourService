package com.agilefinger.labourservice.activity;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

public class Main3Activity extends BaseActivity {
    @Override
    public int initLayoutView() {
        return R.layout.activity_main3;
    }
    @Override
    public void initView() {
        super.initView();
        setToolbar("检测位置", false, false, "");
    }
  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }*/
}

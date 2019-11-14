package com.agilefinger.labourservice.activity;

import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.utils.IntentUtils;

import butterknife.OnClick;

public class AccountSecurityActivity extends BaseActivity {

    @Override
    public int initLayoutView() {
        return R.layout.activity_account_security;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("账号与安全", false, false,"");
    }

    @Override
    @OnClick({R.id.activity_account_security_ll_change_pwd})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_account_security_ll_change_pwd:

                IntentUtils.startActivity(AccountSecurityActivity.this, ChangePwdActivity.class);
                break;
        }
    }
}

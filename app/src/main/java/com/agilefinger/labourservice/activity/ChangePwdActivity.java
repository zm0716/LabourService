package com.agilefinger.labourservice.activity;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.activity_change_pwd_iv_eye_old)
    ImageView ivEyeOld;
    @BindView(R.id.activity_change_pwd_iv_eye_new)
    ImageView ivEyeNew;
    @BindView(R.id.activity_change_pwd_et_old)
    EditText etOld;
    @BindView(R.id.activity_change_pwd_et_new)
    EditText etNew;
    private boolean isCheckedOld = false, isCheckedNew = false;

    @Override
    public int initLayoutView() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("修改密码", false, false,"");
    }
    @Override
    @OnClick({R.id.activity_change_pwd_iv_eye_old, R.id.activity_change_pwd_iv_eye_new,
            R.id.activity_change_pwd_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_change_pwd_iv_eye_old:
                isCheckedOld = !isCheckedOld;
                if (isCheckedOld) {
                    //如果选中，显示密码
                    ivEyeOld.setImageResource(R.drawable.eye_open);
                    etOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    ivEyeOld.setImageResource(R.drawable.eye_close);
                    etOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.activity_change_pwd_iv_eye_new:
                isCheckedNew = !isCheckedNew;
                if (isCheckedNew) {
                    //如果选中，显示密码
                    ivEyeNew.setImageResource(R.drawable.eye_open);
                    etNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    ivEyeNew.setImageResource(R.drawable.eye_close);
                    etNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.activity_change_pwd_rtv_ok:
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        String oPwd = etOld.getText().toString().trim();
        String oNew = etNew.getText().toString().trim();
        if (TextUtils.isEmpty(oPwd)) {
            showToast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(oNew)) {
            showToast("请输入新密码");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().updatePwd(loginBean.getUser_id(), oPwd, oNew, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                LoadingDialog.disDialog();
                showToast(retmsg);
                if (result != 0) {
                    return;
                }
                finish();
            }
        });
    }
}

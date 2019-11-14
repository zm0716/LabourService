package com.agilefinger.labourservice.activity;

import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.activity_forget_pwd_et_code)
    EditText etCode;
    @BindView(R.id.activity_forget_pwd_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_forget_pwd_tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.activity_forget_pwd_et_pwd)
    EditText etPwd;
    @BindView(R.id.activity_forget_pwd_et_pwd_confirm)
    EditText etPwdConfirm;
    private CountDownTimer countDownTimer;
    private String phone;

    @Override
    public int initLayoutView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("找回密码", false, false,"");
    }


    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(59000 + 50, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSendCode.setText("已发送(" + millisUntilFinished / 1000 + "秒)");
            }

            @Override
            public void onFinish() {
                tvSendCode.setEnabled(true);
                tvSendCode.setText("重新发送");
            }
        };
        countDownTimer.start();
    }

    @Override
    @OnClick({R.id.activity_forget_pwd_tv_send_code, R.id.activity_forget_pwd_rtv_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_forget_pwd_tv_send_code://发送短信验证码
                sendSMS();
                break;
            case R.id.activity_forget_pwd_rtv_ok:
                forgetPassword();
                break;
        }
    }

    private void forgetPassword() {
        String p = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String pwdConfirm = etPwdConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showToast("请填写手机号");
            return;
        }
        if (!p.equals(phone)) {
            showToast("更换手机号后，请重新发送验证码！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showToast("请填写验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdConfirm)) {
            showToast("请填写密码、确认密码");
            return;
        }
        if (!pwd.equals(pwdConfirm)) {
            showToast("密码确认密码不一致");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().forgetPwd(code, phone, pwd, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
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

    private void sendSMS() {
        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showToast("请填写正确手机号");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().send_sms(phone, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                showToast(retmsg);
                tvSendCode.setEnabled(false);
                startCountDownTimer();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

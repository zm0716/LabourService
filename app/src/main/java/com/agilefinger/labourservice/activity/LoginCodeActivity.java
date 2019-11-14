package com.agilefinger.labourservice.activity;

import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.LoginBean;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.db.LoginDao;
import com.agilefinger.labourservice.db.RoleDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.CheckPermissionUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginCodeActivity extends BaseActivity {

    @BindView(R.id.activity_login_code_et_code)
    EditText etCode;
    @BindView(R.id.activity_login_code_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_login_code_tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.activity_login_code_tv_login_pwd)
    TextView tvLoginPwd;
    private CountDownTimer countDownTimer;
    private String phone;

    @Override
    public int initLayoutView() {
        return R.layout.activity_login_code;
    }

    @Override
    public void initView() {
        super.initView();
        tvSendCode.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tvLoginPwd.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        initPermission();
    }

    @Override
    @OnClick({R.id.activity_login_code_rtv_login, R.id.activity_login_code_tv_login_pwd, R.id.activity_login_code_tv_send_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_code_rtv_login://登录
                toLogin();
                /*IntentUtils.startActivity(LoginCodeActivity.this, MainActivity.class);
                finish();*/
                break;
            case R.id.activity_login_code_tv_login_pwd://手机号 密码登录
                IntentUtils.startActivity(LoginCodeActivity.this, LoginPwdActivity.class);
                finish();
                break;
            case R.id.activity_login_code_tv_send_code://发送短信验证码
                sendSMS();
                break;
        }
    }

    private void toLogin() {
        String p = etPhone.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        if (!p.equals(phone)) {
            showToast("更换手机号后，请重新发送验证码！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showToast("请填写验证码");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().loginCode(p, code, new HttpEngine.OnResponseCallback<HttpResponse.Login>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Login data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                getCompany(data.getData());
            }
        });
    }
    //获取权限
    private void initPermission() {
        String[] permissions = CheckPermissionUtils.checkPermission(LoginCodeActivity.this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(LoginCodeActivity.this, permissions, 100);
        }
    }
    /**
     * 获取公司列表
     *
     * @param login
     */
    private void getCompany(final LoginBean login) {
        HttpManager.getInstance().getCompany(login.getUser_id(), new HttpEngine.OnResponseCallback<HttpResponse.Company>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Company data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                LoginDao loginDao = new LoginDao(LSApplication.context);
                loginDao.deleteLogin();
                LoginBean loginBean = new LoginBean();
                loginBean.setUser_id(login.getUser_id());
                loginBean.setLogin_token(login.getLogin_token());
                loginBean.setUser_account(login.getUser_account());
                loginBean.setUser_name(login.getUser_name());
                loginDao.addLogin(loginBean);
                RoleDao roleDao = new RoleDao(LSApplication.context);
                roleDao.deleteRole();
                roleDao.addRole(login.getRole_code());
                CompanyDao companyDao = new CompanyDao(LSApplication.context);
                companyDao.deleteCompany();
                companyDao.addCompany(data.getData());
                IntentUtils.startActivity(LoginCodeActivity.this, MainActivity.class);
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
                tvSendCode.setEnabled(false);
                startCountDownTimer();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

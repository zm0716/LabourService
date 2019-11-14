package com.agilefinger.labourservice.activity;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

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

import org.apache.http.protocol.HTTP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginPwdActivity extends BaseActivity {
    @BindView(R.id.activity_login_pwd_et_phone)
    EditText etPhone;
    @BindView(R.id.activity_login_pwd_et_pwd)
    EditText etPwd;
    @BindView(R.id.activity_login_pwd_tv_login_code)
    TextView tvLoginCode;
    @BindView(R.id.activity_login_pwd_tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.activity_login_pwd_iv_eye)
    ImageView ivEye;
    private boolean isChecked = false;
    private String phone, pwd;

    @Override
    public int initLayoutView() {
        return R.layout.activity_login_pwd;
    }

    @Override
    public void initView() {
        super.initView();
        initPermission();
        tvLoginCode.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tvForgetPwd.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        etPhone.setText("");
        etPwd.setText("");
    }
    //获取权限
    private void initPermission() {
        String[] permissions = CheckPermissionUtils.checkPermission(LoginPwdActivity.this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(LoginPwdActivity.this, permissions, 100);
        }
    }
    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    @OnClick({R.id.activity_login_pwd_rtv_login, R.id.activity_login_pwd_tv_login_code, R.id.activity_login_pwd_tv_forget_pwd, R.id.activity_login_pwd_iv_eye})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_login_pwd_rtv_login://登录
                toLogin();
                break;
            case R.id.activity_login_pwd_tv_login_code://手机号 验证码登录
                IntentUtils.startActivity(LoginPwdActivity.this, LoginCodeActivity.class);
                finish();
                break;
            case R.id.activity_login_pwd_tv_forget_pwd://忘记密码
                IntentUtils.startActivity(LoginPwdActivity.this, ForgetPwdActivity.class);
                break;
            case R.id.activity_login_pwd_iv_eye:
                isChecked = !isChecked;
                if (isChecked){
                    //如果选中，显示密码
                    ivEye.setImageResource(R.drawable.eye_open);
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    ivEye.setImageResource(R.drawable.eye_close);
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }

    private void toLogin() {
        if (checkParam()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().login("nak80s7emcq84gtscglffdoo51", phone, pwd, "3", "1", new HttpEngine.OnResponseCallback<HttpResponse.Login>() {
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
                IntentUtils.startActivity(LoginPwdActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    private boolean checkParam() {
        phone = etPhone.getText().toString().trim();
        pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("请填写手机号/用户名");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("请填写密码");
            return false;
        }
        Pattern p = Pattern.compile("^[^\\s]{6,18}$");
        Matcher m = p.matcher(pwd);
        if (!m.matches()) {
            showToast("请输入6-18位密码，不含空格!");
            return false;
        }
        return true;
    }
}

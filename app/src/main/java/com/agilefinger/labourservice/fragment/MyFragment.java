package com.agilefinger.labourservice.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.AccountSecurityActivity;
import com.agilefinger.labourservice.activity.LoginPwdActivity;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.db.LoginDao;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.VersionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 86251 on 2019/5/28.
 */

public class MyFragment extends JBaseFragment {

    @BindView(R.id.fragment_my_tv_name)
    TextView tvName;
    @BindView(R.id.fragment_my_tv_company)
    TextView tvCompany;
    @BindView(R.id.fragment_my_tv_version)
    TextView tvVersion;

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        setToolbar();
    }


    @Override
    protected void lazyLoadOnlyOne() {
        if (loginBean.getUser_name()!=null){
            tvName.setText(loginBean.getUser_name()+"");
        }
        CompanyDao companyDao = new CompanyDao(LSApplication.context);
        List<CompanyBean> companyList = companyDao.queryCompanyAll();
        if (companyList != null && companyList.size() > 0) {
            tvCompany.setText(companyList.get(0).getCompany_name());
        }
        tvVersion.setText(VersionUtils.getVersionName(getActivity()));
    }

    protected void setToolbar() {
        LinearLayout llTop = mView.findViewById(R.id.fragment_my_ll_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = getStatusBarHeight();
            llTop.setPadding(0, result+3, 0, 0);
        }
    }


    @Override
    @OnClick({R.id.fragment_my_ll_account_security, R.id.fragment_my_rtv_logout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_my_ll_account_security:
                IntentUtils.startActivity(getActivity(), AccountSecurityActivity.class);
                break;
            case R.id.fragment_my_rtv_logout:
                LoginDao loginDao = new LoginDao(LSApplication.context);
               // RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
                //VoiceDao voiceDao = new VoiceDao(LSApplication.context);
                loginDao.deleteLogin();
                //rectificationDao.deleteRectification();
                //voiceDao.deleteVoice();
                IntentUtils.startActivity(getActivity(), LoginPwdActivity.class);
                getActivity().finish();
                break;
        }
    }
}

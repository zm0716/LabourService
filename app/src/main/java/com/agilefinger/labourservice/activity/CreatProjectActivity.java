package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.flyco.roundview.RoundTextView;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 创建项目（1/4）
 * */
public class CreatProjectActivity extends BaseActivity {
    @BindView(R.id.layout_toolbar_iv_back)
    ImageView layoutToolbarIvBack;
    @BindView(R.id.layout_toolbar_tv_title)
    TextView layoutToolbarTvTitle;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView layoutToolbarIvOperate;
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView layoutToolbarIvOperate2;
    @BindView(R.id.layout_toolbar_tv_operate)
    TextView layoutToolbarTvOperate;
    @BindView(R.id.layout_toolbar_ll_wrapper)
    LinearLayout layoutToolbarLlWrapper;
    @BindView(R.id.m_project_name)
    TextView mProjectName;
    @BindView(R.id.m_project_name_editext)
    EditText mProjectNameEditext;
    @BindView(R.id.m_project_class)
    TextView mProjectClass;
    @BindView(R.id.m_project_class_text)
    TextView mProjectClassText;
    @BindView(R.id.m_project_class_rela)
    RelativeLayout mProjectClassRela;
    @BindView(R.id.m_project_technology)
    TextView mProjectTechnology;
    @BindView(R.id.m_project_technology_text)
    TextView mProjectTechnologyText;
    @BindView(R.id.m_project_technology_rela)
    RelativeLayout mProjectTechnologyRela;
    @BindView(R.id.m_project_state)
    TextView mProjectState;
    @BindView(R.id.m_project_state_text)
    TextView mProjectStateText;
    @BindView(R.id.m_project_state_rela)
    RelativeLayout mProjectStateRela;
    @BindView(R.id.m_project_construction_area)
    TextView mProjectConstructionArea;
    @BindView(R.id.m_project_construction_area_editext)
    EditText mProjectConstructionAreaEditext;
    @BindView(R.id.m_project_start_date)
    TextView mProjectStartDate;
    @BindView(R.id.m_project_start_date_text)
    TextView mProjectStartDateText;
    @BindView(R.id.m_project_start_date_rela)
    RelativeLayout mProjectStartDateRela;
    @BindView(R.id.m_project_construction_period)
    TextView mProjectConstructionPeriod;
    @BindView(R.id.m_project_construction_period_editext)
    EditText mProjectConstructionPeriodEditext;
    @BindView(R.id.m_project_address)
    TextView mProjectAddress;
    @BindView(R.id.m_project_address_editext)
    EditText mProjectAddressEditext;
    @BindView(R.id.m_project_service_provider)
    TextView mProjectServiceProvider;
    @BindView(R.id.m_project_service_provider_text)
    TextView mProjectServiceProviderText;
    @BindView(R.id.m_project_service_provider_rela)
    RelativeLayout mProjectServiceProviderRela;
    @BindView(R.id.m_project_developers)
    TextView mProjectDevelopers;
    @BindView(R.id.m_project_developers_text)
    TextView mProjectDevelopersText;
    @BindView(R.id.m_project_developers_rela)
    RelativeLayout mProjectDevelopersRela;
    @BindView(R.id.m_project_signing_unit)
    TextView mProjectSigningUnit;
    @BindView(R.id.m_project_signing_unit_editext)
    EditText mProjectSigningUnitEditext;
    @BindView(R.id.m_project_manager)
    TextView mProjectManager;
    @BindView(R.id.m_project_manager_text)
    TextView mProjectManagerText;
    @BindView(R.id.m_project_manager_rela)
    RelativeLayout mProjectManagerRela;
    @BindView(R.id.m_project_level)
    TextView mProjectLevel;
    @BindView(R.id.m_project_level_text)
    TextView mProjectLevelText;
    @BindView(R.id.m_project_level_rela)
    RelativeLayout mProjectLevelRela;
    @BindView(R.id.activity_next_creat_project)
    RoundTextView activityNextCreatProject;

    @Override
    public int initLayoutView() {
        return R.layout.activity_creat_project;
    }


    @Override
    public void initView() {
        super.initView();
        setToolbar("创建项目（1/4）", false, false, "");
    }

    @OnClick({R.id.activity_next_creat_project})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_next_creat_project:
                IntentUtils.startActivity(this, AddProjectActivity.class);
                break;
        }
    }

}

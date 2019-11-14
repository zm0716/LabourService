package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.flyco.roundview.RoundTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInspectionOneActivity extends BaseActivity {

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
    @BindView(R.id.activity_create_inspection_task_iv_step)
    ImageView activityCreateInspectionTaskIvStep;
    @BindView(R.id.activity_create_inspection_task_tv_step_one)
    TextView activityCreateInspectionTaskTvStepOne;
    @BindView(R.id.activity_create_inspection_task_tv_step_two)
    TextView activityCreateInspectionTaskTvStepTwo;
    @BindView(R.id.activity_create_inspection_task_tv_step_three)
    TextView activityCreateInspectionTaskTvStepThree;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.activity_create_inspection_task_rv_list)
    RecyclerView activityCreateInspectionTaskRvList;
    @BindView(R.id.fragment_create_inspection_task_step_one_rtv_next)
    RoundTextView fragmentCreateInspectionTaskStepOneRtvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inspection_one);
        ButterKnife.bind(this);
    }


}

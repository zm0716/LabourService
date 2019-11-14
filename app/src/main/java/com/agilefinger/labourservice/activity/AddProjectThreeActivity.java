package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.PeopleAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *创建项目（3/4）
 * */
public class AddProjectThreeActivity extends BaseActivity {

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
    @BindView(R.id.m_group_img)
    ImageView mGroupImg;
    @BindView(R.id.m_group_name)
    TextView mGroupName;
    @BindView(R.id.m_group_time)
    TextView mGroupTime;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.m_title_view)
    View mTitleView;
    @BindView(R.id.m_people_list)
    RecyclerView mPeopleList;
    @BindView(R.id.m_add_project_people)
    RoundTextView mAddProjectPeople;
    @BindView(R.id.m_null_line)
    LinearLayout mNullLine;
    @BindView(R.id.m_yj_btn)
    TextView mYjBtn;
    @BindView(R.id.m_add_people_btn)
    View mAddPeopleBtn;
    @BindView(R.id.activity_next_creat_project_three)
    RoundTextView activityNextCreatProjectThree;
    @BindView(R.id.m_line)
    LinearLayout mLine;
    private PeopleAdapter peopleAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_project_three;
    }


    @Override
    public void initView() {
        super.initView();
        setToolbar("创建项目（3/4）", false, true, "跳过");

        initRv();
    }


    private void initRv() {
        mPeopleList.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> mlist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mlist.add("");
        }
        if (mlist.size() == 0) {
            mNullLine.setVisibility(View.VISIBLE);
            mPeopleList.setVisibility(View.GONE);
        } else {
            mNullLine.setVisibility(View.GONE);
            mPeopleList.setVisibility(View.VISIBLE);
        }

        peopleAdapter = new PeopleAdapter();
        peopleAdapter.addData(mlist);

        mPeopleList.setAdapter(peopleAdapter);

        peopleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_shanchu:
                        mlist.remove(position);
                        peopleAdapter.remove(position);
                        if (mlist.size() == 0) {
                            mNullLine.setVisibility(View.VISIBLE);
                            mPeopleList.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });


    }

    @OnClick({R.id.activity_next_creat_project_three})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_next_creat_project_three:
                IntentUtils.startActivity(this, CreateXMFourActivity.class);
                break;
        }
    }
}

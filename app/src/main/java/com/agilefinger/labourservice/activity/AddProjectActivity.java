package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.AddProjectAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProjectActivity extends BaseActivity {


    @BindView(R.id.activity_next_xinxi)
    RoundTextView activityNextXinxi;
    @BindView(R.id.m_add_buildings)
    LinearLayout mAddBuildings;
    @BindView(R.id.m_add_buildings_pl)
    TextView mAddBuildingsPl;
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
    @BindView(R.id.text_lou)
    TextView textLou;
    @BindView(R.id.relayout_one)
    LinearLayout relayoutOne;
    @BindView(R.id.m_project_recy)
    RecyclerView mProjectRecy;
    @BindView(R.id.activity_qd_detail_rtv_upload)
    RoundTextView activityQdDetailRtvUpload;
    @BindView(R.id.m_null_line)
    RelativeLayout mNullLine;
    @BindView(R.id.m_add_buildings_two)
    TextView mAddBuildingsTwo;
    @BindView(R.id.m_batches_add_buildings_two)
    TextView mBatchesAddBuildingsTwo;
    @BindView(R.id.m_add_line)
    LinearLayout mAddLine;

    private ArrayList<String> mlist = new ArrayList<>();
    private AddProjectAdapter addProjectAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_project;

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("创建项目(2/4)", false, false, "");
        for (int i = 0; i < 5; i++) {
            mlist.add("");
        }
        invw();
    }

    private void invw() {
        if (mlist.size() != 0) {
            mNullLine.setVisibility(View.GONE);
            mProjectRecy.setLayoutManager(new LinearLayoutManager(this));
            mProjectRecy.setNestedScrollingEnabled(false);//禁止滑动
//            if (addProjectAdapter == null) {
            addProjectAdapter = new AddProjectAdapter(mlist, this);
//            mProjectRecy.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
            mProjectRecy.setAdapter(addProjectAdapter);

//            } else {
//                addProjectAdapter.notifyDataSetChanged();
//            }
        } else {
            mNullLine.setVisibility(View.VISIBLE);
        }

        addProjectAdapter.setOnItemClick(new AddProjectAdapter.OnItemClickListern() {
            @Override
            public void onClickLisern(int postion) {
                if (mlist.size() != 0) {
                    mlist.remove(postion);
                    invw();
                }
//                addProjectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickBianjiListern(int postion) {
                IntentUtils.startActivity(AddProjectActivity.this, CreatProjectActivity.class);
            }
        });


    }


    @OnClick(R.id.activity_next_xinxi)
    public void onViewClicked() {
//        IntentUtils.startActivity(AddProjectActivity.this,CreateXMFourActivity.class);
    }


    @Override
    @OnClick({R.id.activity_next_xinxi, R.id.m_add_buildings, R.id.m_add_buildings_pl, R.id.m_add_buildings_two,
            R.id.m_batches_add_buildings_two})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_next_xinxi:
                IntentUtils.startActivity(AddProjectActivity.this, AddProjectThreeActivity.class);
//                IntentUtils.startActivity(AddProjectActivity.this,AddProjectThreeActivity.class);
                break;

//            case R.id.m_add_buildings:
//                IntentUtils.startActivity(AddProjectActivity.this, AddBuildingActivity.class);
//                break;
//            case R.id.m_add_buildings_pl:
//                IntentUtils.startActivity(AddProjectActivity.this, BatchesBuildingsActivity.class);
//                break;
            case R.id.m_add_buildings_two:
                IntentUtils.startActivity(AddProjectActivity.this, AddBuildingActivity.class);
                break;
            case R.id.m_batches_add_buildings_two:
                IntentUtils.startActivity(AddProjectActivity.this, BatchesBuildingsActivity.class);
                break;
        }
    }
}

package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BuildingSGAdapter;
import com.agilefinger.labourservice.adapter.PointAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.AddBuildingDiaLog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.flyco.roundview.RoundTextView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 新增楼栋
 * */
public class AddBuildingActivity extends BaseActivity {

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
    @BindView(R.id.m_down_number)
    TextView mDownNumber;
    @BindView(R.id.m_down_number_editext)
    EditText mDownNumberEditext;
    @BindView(R.id.m_up_number)
    TextView mUpNumber;
    @BindView(R.id.m_up_number_editext)
    EditText mUpNumberEditext;
    @BindView(R.id.m_dong)
    CheckBox mDong;
    @BindView(R.id.m_nan)
    CheckBox mNan;
    @BindView(R.id.m_dongbei)
    CheckBox mDongbei;
    @BindView(R.id.m_xibei)
    CheckBox mXibei;
    @BindView(R.id.activity_next_creat_project)
    RoundTextView activityNextCreatProject;
    @BindView(R.id.m_sg_name)
    RecyclerView mSgName;

    private Map<Integer, String> map = new HashMap<>();


    private ArrayList<String> mlist = new ArrayList<>();
    private View header;
    private AddBuildingDiaLog addBuildingDiaLog;
    private BuildingSGAdapter buildingSGAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_building;
    }


    @Override
    public void initView() {
        super.initView();
        setToolbar("新增楼栋", false, false, "");

        iniRv();

        mDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDong.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(0, "东立面");
                } else {
                    mDong.setTextColor(Color.parseColor("#999999"));
                    map.remove(0);
                }
            }
        });

        mNan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNan.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(1, "南立面");
                } else {
                    mNan.setTextColor(Color.parseColor("#999999"));
                    map.remove(1);
                }
            }
        });
        mDongbei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDongbei.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(2, "东北立面");
                } else {
                    mDongbei.setTextColor(Color.parseColor("#999999"));
                    map.remove(2);
                }
            }
        });
        mXibei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mXibei.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(3, "西北立面");

                } else {
                    mXibei.setTextColor(Color.parseColor("#999999"));
                    map.remove(3);
                }
            }
        });
    }

    private void iniRv() {
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mSgName.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.bottom = 10;
                outRect.right = 7;
            }
        });

        mSgName.setLayoutManager(manager);

//
//        mlist.add("看来你福建南方");
//        mlist.add("看来你福");
//        mlist.add("看来你");
//        mlist.add("看来");
//        mlist.add("看");


        header = View.inflate(AddBuildingActivity.this, R.layout.add_building_heard, null);
        buildingSGAdapter = new BuildingSGAdapter(this, mlist);
        buildingSGAdapter.setHeaderView(header);
        mSgName.setAdapter(buildingSGAdapter);

        buildingSGAdapter.setOnItemClickListener(new BuildingSGAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, BuildingSGAdapter.ViewHolder holder, List<String> list) {
                mlist.remove(position);
                buildingSGAdapter.notifyDataSetChanged();
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        addBuildingDiaLog = new AddBuildingDiaLog(AddBuildingActivity.this, new AddBuildingDiaLog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, String text) {
                addBuildingDiaLog.dismiss();

                mlist.add(text);
                buildingSGAdapter.notifyDataSetChanged();

            }
        });
        addBuildingDiaLog.show();
    }

    @Override
    @OnClick({R.id.activity_next_creat_project})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_next_creat_project:
                addBuilding();
                break;
        }
    }

    private void addBuilding() {
        Bundle extras = getIntent().getExtras();
        String project_id = extras.getString("project_id");

        String mProjectNumberText = mProjectNameEditext.getText().toString().trim();
        String downNumber = mDownNumberEditext.getText().toString().trim();
        String upNumber = mUpNumberEditext.getText().toString().trim();
        String user_id = loginBean.getUser_id();
        LoadingDialog.showLoading(AddBuildingActivity.this);
        HttpManager.getInstance().addBuilding(project_id, upNumber, downNumber, mProjectNumberText, user_id, map, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
//                ToastUtil.showShort(BatchesBuildingsActivity.this, retmsg);
                LoadingDialog.disDialog();
                finish();
            }

        });
    }
}

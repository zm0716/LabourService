package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BuildingStaffAdapter;
import com.agilefinger.labourservice.adapter.Staff2Adapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AddressImagBean;
import com.agilefinger.labourservice.bean.BuildingBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class ImageAddressActivity extends BaseActivity {

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
    @BindView(R.id.activity_staff_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_staff_selection_rtv_ok)
    RoundTextView activityStaffSelectionRtvOk;
    private String projectId;
    private List<BuildingBean> mStaffList;
    BuildingStaffAdapter staffAdapter;
    private String data;

    @Override
    public int initLayoutView() {
        return R.layout.activity_image_address;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("按位置筛选", false, false, "");
        projectId = getIntent().getExtras().getString(Constants.EXTRA_DATA_PROJECT);
        data = getIntent().getExtras().getString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
        mStaffList = (List<BuildingBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
        initRV();
    }

    private void initRV() {
        staffAdapter = new BuildingStaffAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(staffAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getImageAddress(projectId, new HttpEngine.OnResponseCallback<HttpResponse.KanBanImageAddressData>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KanBanImageAddressData data) {
                if (result == 0) {
                    final List<BuildingBean> mList = new ArrayList<>();
                    mList.clear();
                    ArrayList<AddressImagBean> data1 = data.getData();
                    for (int i = 0; i < data1.size(); i++) {
                        BuildingBean staffBean = new BuildingBean();
                        staffBean.setBuilding_id(String.valueOf(data1.get(i).getId()));
                        staffBean.setName(data1.get(i).getBuilding_num());
                        mList.add(staffBean);
                    }
                    if (mStaffList != null && mList != null) {
                        if (mStaffList.size() > 0) {
                            mStaffList.remove(mStaffList.size() - 1);
                        }
                        if (mStaffList.size() <= mList.size()) {
                            for (int i = 0; i < mStaffList.size(); i++) {
                                for (int j = 0; j < mList.size(); j++) {
                                    if (!TextUtils.isEmpty(mStaffList.get(i).getBuilding_id()) && mStaffList.get(i).getBuilding_id().equals(mList.get(j).getBuilding_id())) {
                                        mList.get(j).setChecked(true);
                                    }
                                }
                            }
                        }
                    }
                    staffAdapter.setNewData(mList);

                    LoadingDialog.disDialog();
                }
            }
        });
    }

    private int curPosition = -1;

    @Override
    public void initListener() {
        super.initListener();
        staffAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BuildingBean staff = (BuildingBean) adapter.getItem(position);
                if (Constants.CHOOSE_TYPE_SINGLE.equals(data)) {//单选
                    if (curPosition != position && curPosition != -1) {
                        BuildingBean oldStaff = (BuildingBean) adapter.getItem(curPosition);
                        oldStaff.setChecked(false);
                        staffAdapter.notifyItemChanged(curPosition);
                    }
                    staff.setChecked(true);
                    curPosition = position;
                } else {//多选
                    staff.setChecked(!staff.isChecked());
                }
                staffAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<BuildingBean> pList = staffAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isChecked()) count++;
        }
        String txt = "确定（" + count + "）";
        activityStaffSelectionRtvOk.setText(txt);
    }

    @Override
    @OnClick({R.id.activity_staff_selection_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_staff_selection_rtv_ok:
                List<BuildingBean> nPList = new ArrayList<>();
                List<BuildingBean> pList = staffAdapter.getData();
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).isChecked())
                        nPList.add(pList.get(i));
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA, (Serializable) nPList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

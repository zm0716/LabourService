package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.Staff2Adapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.ProjectConditionBean;
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
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/*
 * 看板项目经理
 * */
public class KBXJActivity extends BaseActivity {
    @BindView(R.id.activity_staff_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_staff_selection_rtv_ok)
    RoundTextView rtvOk;

    private ArrayList<StaffBean> mlist;
    private String companyId;
    Staff2Adapter staffAdapter;
    private String data, type;
    private List<StaffBean> mStaffList;
    private int curPosition = -1;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kbxj;
    }

    @Override
    public void initView() {
        super.initView();

        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        type = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
        setToolbar("选择" + type, false, false, "");
        data = getIntent().getExtras().getString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        mStaffList = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
        initRV();
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void initRV() {
        staffAdapter = new Staff2Adapter();
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

    private void getData() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getKBKBProjectCondition(companyId, new HttpEngine.OnResponseCallback<HttpResponse.KBProjectConditionBean>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBProjectConditionBean data) {
                if (result == 0) {
                    if (data.getData() != null) {
                        final List<StaffBean> mList = new ArrayList<>();
                        mList.clear();
                        if (type.equals("项目经理")) {
                            List<ProjectConditionBean.ManagerListBean> managerList = data.getData().getManagerList();
                            for (int i = 0; i < managerList.size(); i++) {
                                StaffBean staffBean = new StaffBean();
                                staffBean.setId(String.valueOf(managerList.get(i).getId()));
                                staffBean.setName(managerList.get(i).getUser_name());
                                mList.add(staffBean);
                            }
                            if (mStaffList != null && mList != null) {
                                if (mStaffList.size() > 0) {
                                    mStaffList.remove(mStaffList.size() - 1);
                                }
                                if (mStaffList.size() <= mList.size()) {
                                    for (int i = 0; i < mStaffList.size(); i++) {
                                        for (int j = 0; j < mList.size(); j++) {
                                            if (!TextUtils.isEmpty(mStaffList.get(i).getId()) && mStaffList.get(i).getId().equals(mList.get(j).getId())) {
                                                mList.get(j).setCheck(true);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            List<ProjectConditionBean.DeveloperListBean> developerList = data.getData().getDeveloperList();
                            for (int i = 0; i < developerList.size(); i++) {
                                StaffBean staffBean = new StaffBean();
                                staffBean.setId(String.valueOf(developerList.get(i).getId()));
                                staffBean.setName(developerList.get(i).getName());
                                mList.add(staffBean);
                            }
                            if (mStaffList != null && mList != null) {
                                if (mStaffList.size() > 0) {
                                    mStaffList.remove(mStaffList.size() - 1);
                                }
                                if (mStaffList.size() <= mList.size()) {
                                    for (int i = 0; i < mStaffList.size(); i++) {
                                        for (int j = 0; j < mList.size(); j++) {
                                            if (!TextUtils.isEmpty(mStaffList.get(i).getId()) && mStaffList.get(i).getId().equals(mList.get(j).getId())) {
                                                mList.get(j).setCheck(true);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        staffAdapter.setNewData(mList);

                        LoadingDialog.disDialog();
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        staffAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StaffBean staff = (StaffBean) adapter.getItem(position);
                if (Constants.CHOOSE_TYPE_SINGLE.equals(data)) {//单选
                    if (curPosition != position && curPosition != -1) {
                        StaffBean oldStaff = (StaffBean) adapter.getItem(curPosition);
                        oldStaff.setCheck(false);
                        staffAdapter.notifyItemChanged(curPosition);
                    }
                    staff.setCheck(true);
                    curPosition = position;
                } else {//多选
                    staff.setCheck(!staff.isCheck());
                }
                staffAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<StaffBean> pList = staffAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        rtvOk.setText(txt);
    }

    @Override
    @OnClick({R.id.activity_staff_selection_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_staff_selection_rtv_ok:
                List<StaffBean> nPList = new ArrayList<>();
                List<StaffBean> pList = staffAdapter.getData();
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).isCheck())
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

package com.agilefinger.labourservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ProjectAdapter;
import com.agilefinger.labourservice.adapter.StaffAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
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

public class StaffSelectionActivity extends BaseActivity {

    @BindView(R.id.activity_staff_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_staff_selection_rtv_ok)
    RoundTextView rtvOk;
//    @BindView(R.id.layout_toolbar_search_et_search)
//    EditText etSearch;
    StaffAdapter staffAdapter;
    private String data, type, companyId;
    private int curPosition = -1;
    private List<StaffBean> mStaffList;

    @Override
    public int initLayoutView() {
        return R.layout.activity_staff_selection;
    }

    @Override
    public void initData() {
        super.initData();
        type = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
        data = getIntent().getExtras().getString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        mStaffList = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("选择员工",false,false,"");
        initRV();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getEmployeeList();
    }


    private void initRV() {
        staffAdapter = new StaffAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(staffAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        //staffAdapter.setNewData(LoadData.getStaffList());
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
        /*etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String content = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        showToast("请输入搜索内容");
                    } else {
                        getEmployeeList();
                    }
                }
                return false;
            }
        });*/
    }

    private void getEmployeeList() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().zg_zp_man(loginBean.getUser_id(), companyId, new HttpEngine.OnResponseCallback<HttpResponse.ZGZPMan>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.ZGZPMan data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                List<StaffBean> mList;
                if (Constants.ZP_MAN.equals(type)) {
                    mList = data.getData().getAssign_man_list();
                } else {
                    mList = data.getData().getCorrect_man_list();
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
                staffAdapter.setNewData(mList);
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
                    if (pList.get(i).isCheck()) nPList.add(pList.get(i));
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

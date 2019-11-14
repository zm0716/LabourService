package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.Staff2Adapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StaffSelection2Activity extends BaseActivity {

    @BindView(R.id.activity_staff_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_staff_selection_rtv_ok)
    RoundTextView rtvOk;
    //    @BindView(R.id.layout_toolbar_search_et_search)
//    EditText etSearch;
    Staff2Adapter staffAdapter;
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
        setToolbar("选择检测人", false, false, "");
        initRV();
//        if (mStaffList != null) {
//            rtvOk.setText("确定（" + (mStaffList.size() - 1) + "）");
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getEmployeeList();
    }


    private void initRV() {
        staffAdapter = new Staff2Adapter();
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

        Map<String, Object> pram = new HashMap<>();
        pram.put("companyID", companyId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_check_user_list_with_company", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                String string = response.body().string();
                Log.d("公司下检测人", string);
                Gson gson = new Gson();
                final JianCeRenBean jianCeRenBean = gson.fromJson(string, JianCeRenBean.class);
                if (null != jianCeRenBean) {
                    if (jianCeRenBean.getCode() == 0) {
//                        ThreadUtils.runOnMainThread(new Runnable() {
//                            @Override
//                            public void run() {
                        List<JianCeRenBean.DataBean> jList = jianCeRenBean.getData();
                        final List<StaffBean> mList = new ArrayList<>();
                        mList.clear();
                        for (int i = 0; i < jList.size(); i++) {
                            StaffBean staffBean = new StaffBean();
                            staffBean.setId(jList.get(i).getId());
                            staffBean.setName(jList.get(i).getName());
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


//                                if (!mList.equals("")&&mList!=null){
//                                    Log.d("已选中",mStaffList.size()+"::");
//                                    if (mStaffList != null && mList != null) {
//                                        if (mStaffList.size() > 0) {
//                                            mStaffList.remove(mStaffList.size() - 1);
//                                        }
//                                        if (mStaffList.size() <= mList.size()) {
//                                            for (int i = 0; i < mStaffList.size(); i++) {
//                                                for (int j = 0; j < mList.size(); j++) {
//                                                    if (mStaffList.get(i).getId()==mList.get(j).getId()) {
//                                                        mList.get(j).setCheck(true);
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                staffAdapter.setNewData(mList);
                            }
                        });
//                                }else {
//                                    ThreadUtils.runOnMainThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            MyToast.getInstands().toastShow(StaffSelection2Activity.this,"没有开发商");
//                                        }
//                                    });
//                                }

//                            }
//                        });

                    }

                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.disDialog();
                        }
                    });

                }

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

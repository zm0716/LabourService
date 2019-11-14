package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.StaffAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.CompanyListBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.APPUtils;
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

public class TesterActivity extends BaseActivity {

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
        if (type.equals("检测人")) {
            setToolbar("检测人", false, false, "");
        } else {
            setToolbar("开发商", false, false, "");
        }
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
        staffAdapter = new StaffAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(staffAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
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

    //获取数据
    private void getEmployeeList() {
        LoadingDialog.showLoading(this);
        if (type.equals("检测人")) {
            //获取检测人列表
            getTesterList();
        } else {
            getCompanyList();
        }

      /*  HttpManager.getInstance().zg_zp_man(loginBean.getUser_id(), companyId, new HttpEngine.OnResponseCallback<HttpResponse.ZGZPMan>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.ZGZPMan data) {

            }
        });*/
    }

    //获取检测人列表
    private void getTesterList() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("companyID", companyId);
        Log.d("公司id", companyId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_check_user_list_with_company", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("检测人列表", string);
                LoadingDialog.disDialog();
             /*   LoadingDialog.disDialog();
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
                staffAdapter.setNewData(mList);*/
            }
        });
    }

    //获取公司接口
    private void getCompanyList() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("companyID", companyId);
        pram.put("userID", loginBean.getUser_id());
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_dev_list", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("服务商列表", string);
                Gson gson = new Gson();
                CompanyListBean companyListBean = gson.fromJson(string, CompanyListBean.class);
                if (companyListBean.getCode() == 0) {
                    List<CompanyListBean.DataBean> cList = companyListBean.getData();
                    final List<StaffBean> mList = new ArrayList<>();
                    mList.clear();
                    for (int i = 0; i < cList.size(); i++) {
                        StaffBean staffBean = new StaffBean();
                        staffBean.setId(cList.get(i).getId());
                        staffBean.setName(cList.get(i).getName());
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
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            staffAdapter.setNewData(mList);
                        }
                    });

                }
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.disDialog();
                    }
                });

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
                //传集合
                bundle.putSerializable(Constants.EXTRA_DATA, (Serializable) nPList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

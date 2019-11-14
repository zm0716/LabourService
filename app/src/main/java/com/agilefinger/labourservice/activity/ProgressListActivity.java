package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ProgressAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AddProgressBean;
import com.agilefinger.labourservice.bean.AddProgressContentBean;
import com.agilefinger.labourservice.bean.JsonProgressBean;
import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.ProgressBuildingBean;
import com.agilefinger.labourservice.bean.ProgressItemBean;
import com.agilefinger.labourservice.bean.WeatherBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.db.ProgressDao;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.ConfirmCusDelDialog;
import com.agilefinger.labourservice.view.dialog.ConfirmDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class ProgressListActivity extends BaseActivity {

    @BindView(R.id.activity_progress_rv_list)
    RecyclerView rvList;
    /*@BindView(R.id.layout_toolbar_search_et_search)
    EditText etSearch;*/
    private ProgressAdapter progressAdapter;
    private String companyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_progress_list;
    }

    @Override
    public void initData() {
        super.initData();
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("施工进度", false, false,"");
        progressAdapter = new ProgressAdapter();
        progressAdapter.setEmptyView(emptyView(rvList, "暂无施工进度"));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 10)));
        rvList.setAdapter(progressAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
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
                        //getEmployeeList();
                    }
                }
                return false;
            }
        });*/
        progressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProgressBean progress = (ProgressBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_TYPE, Constants.TYPE_EDIT);
                bundle.putSerializable(Constants.EXTRA_DATA, progress);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(ProgressListActivity.this, AddProgressActivity.class, bundle);
            }
        });
        progressAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                ProgressBean progressBean = (ProgressBean) adapter.getItem(position);
                showConfirmDialog(progressBean, position);
                return false;
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProgressList();
    }

    private void getProgressList() {
        List<ProgressBean> mProList = new ArrayList<>();
        ProgressDao progressDao = new ProgressDao(LSApplication.context);
        List<JsonProgressBean> mJsonList = progressDao.queryProgressAll();
        int size = mJsonList.size();
        for (int i = 0; i < size; i++) {
            String cJson = mJsonList.get(i).getContent();
            Gson gson = new Gson();
            ProgressBean progressBean = gson.fromJson(cJson, new TypeToken<ProgressBean>() {
            }.getType());
            progressBean.setId(String.valueOf(mJsonList.get(i).getId()));
            List<ProgressBuildingBean> bList = progressBean.getContent();
            if (bList != null) {
                String content = "施工内容：";
                for (int k = 0; k < bList.size(); k++) {
                    List<ProgressItemBean> piList = bList.get(k).getItem();
                    if (piList != null) {
                        for (int l = 0; l < piList.size(); l++) {
                            ProgressItemBean progressItem = piList.get(l);
                            content += bList.get(k).getName() + " 完成" + progressItem.getQuantity() + progressItem.getUnit() + "已完成" + progressItem.getRate() + "% 人数" + progressItem.getNum() + "人";
                            progressBean.setDescription(content);
                        }
                    }
                }
            }
            mProList.add(progressBean);
        }
        progressAdapter.setNewData(mProList);
    }

    @Override
    @OnClick({R.id.activity_progress_rtv_add})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_progress_rtv_add:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(ProgressListActivity.this, AddProgressActivity.class, bundle);
                break;
        }
    }

    private ConfirmCusDelDialog confirmDialog;

    private void showConfirmDialog(final ProgressBean progressBean, final int position) {
        closeConfirmDialog();
        confirmDialog = new ConfirmCusDelDialog(this, "确定删除施工进度？", true, new ConfirmCusDelDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    if (!TextUtils.isEmpty(progressBean.getId())) {
                        ProgressDao progressDao = new ProgressDao(LSApplication.context);
                        progressDao.deleteProgress(Integer.parseInt(progressBean.getId()));
                        progressAdapter.remove(position);
                    }
                }
                closeConfirmDialog();
            }
        });
        confirmDialog.show();
    }

    private void closeConfirmDialog() {
        if (confirmDialog != null) {
            confirmDialog.cancel();
            confirmDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeConfirmDialog();
    }
}

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
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class ProjectSelectionActivity extends BaseActivity {

    @BindView(R.id.activity_project_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_project_selection_rtv_ok)
    RoundTextView rtvOk;
    @BindView(R.id.layout_toolbar_search_et_search)
    EditText etSearch;
    ProjectAdapter projectAdapter;
    private String type, companyId;
    private int curPosition = -1;
    private List<ProjectBean> mProjectList;

    @BindView(R.id.layout_toolbar_search_rll_txt)
    RoundLinearLayout layout_toolbar_search_rll_txt;

    @Override
    public int initLayoutView() {
        return R.layout.activity_project_selection;
    }

    @Override
    public void initData() {
        super.initData();
        type = getIntent().getExtras().getString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        mProjectList = (List<ProjectBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarSearch("选择项目");
        initRV();
    }

    private void initRV() {
        projectAdapter = new ProjectAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(projectAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        //projectAdapter.setNewData(LoadData.getProjectList());
    }

    @Override
    public void initListener() {
        super.initListener();
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProjectBean project = (ProjectBean) adapter.getItem(position);
                if (Constants.CHOOSE_TYPE_SINGLE.equals(type)) {//单选
                    if (curPosition != position && curPosition != -1) {
                        ProjectBean oldProject = (ProjectBean) adapter.getItem(curPosition);
                        oldProject.setCheck(false);
                        projectAdapter.notifyItemChanged(curPosition);
                    }
                    project.setCheck(true);
                    curPosition = position;
                } else {//多选
                    project.setCheck(!project.isCheck());
                }
                projectAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    String content = etSearch.getText().toString().trim();
                    /*if (TextUtils.isEmpty(content)) {
                        showToast("请输入搜索内容");
                    } else {*/
                    getProjectList(content);
                    /*}*/
                }
                return false;
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<ProjectBean> pList = projectAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        rtvOk.setText(txt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProjectList("");
    }

    private void getProjectList(String search) {
        LoadingDialog.showLoading(this);
        curPosition = -1;
        HttpManager.getInstance().getProjectList(loginBean.getUser_id(), companyId, search, new HttpEngine.OnResponseCallback<HttpResponse.Project>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Project data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                List<ProjectBean> mList = data.getData();
                if (mProjectList != null && mProjectList != null) {
                    if (mProjectList.size() <= mList.size()) {
                        for (int i = 0; i < mProjectList.size(); i++) {
                            for (int j = 0; j < mList.size(); j++) {
                                if (!TextUtils.isEmpty(mProjectList.get(i).getId()) && mProjectList.get(i).getId().equals(mList.get(j).getId())) {
                                    mList.get(j).setCheck(true);
                                    curPosition = j;
                                }
                            }
                        }
                    }
                }
                projectAdapter.setNewData(mList);
            }
        });
    }

    @Override
    @OnClick({R.id.activity_project_selection_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_project_selection_rtv_ok:
                List<ProjectBean> nPList = new ArrayList<>();
                List<ProjectBean> pList = projectAdapter.getData();
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).isCheck()) nPList.add(pList.get(i));
                }
                if (Constants.CHOOSE_TYPE_SINGLE.equals(type) && nPList.size() < 1) {
                    showToast("请选择项目");
                    return;
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

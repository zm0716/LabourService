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
import com.agilefinger.labourservice.adapter.BuildingSelectionAdapter;
import com.agilefinger.labourservice.adapter.StaffAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildingNoBean;
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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class BuildingSelectionActivity extends BaseActivity {

    @BindView(R.id.activity_building_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_building_selection_rtv_ok)
    RoundTextView rtvOk;
    BuildingSelectionAdapter buildingSelectionAdapter;
    private String pid;

    @Override
    public int initLayoutView() {
        return R.layout.activity_building_selection;
    }

    @Override
    public void initData() {
        super.initData();
        pid = getIntent().getExtras().getString(Constants.EXTRA_DATA);
        if (TextUtils.isEmpty(pid)) {
            showToast("请选择项目");
            finish();
        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("选择施工楼号", false, false,"");
        initRV();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getBuildingList();
    }


    private void initRV() {
        buildingSelectionAdapter = new BuildingSelectionAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(buildingSelectionAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void initListener() {
        super.initListener();
        buildingSelectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BuildingNoBean buildingNo = (BuildingNoBean) adapter.getItem(position);
                buildingNo.setCheck(!buildingNo.isCheck());
                buildingSelectionAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
    }

    private void getBuildingList() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getFloorList(loginBean.getUser_id(), "249", pid, new HttpEngine.OnResponseCallback<HttpResponse.BuildingNo>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.BuildingNo data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                buildingSelectionAdapter.setNewData(data.getData());
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<BuildingNoBean> pList = buildingSelectionAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        rtvOk.setText(txt);
    }

    @Override
    @OnClick({R.id.activity_building_selection_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_building_selection_rtv_ok:
                List<BuildingNoBean> nBList = new ArrayList<>();
                List<BuildingNoBean> bList = buildingSelectionAdapter.getData();
                for (int i = 0; i < bList.size(); i++) {
                    if (bList.get(i).isCheck()) nBList.add(bList.get(i));
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA, (Serializable) nBList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

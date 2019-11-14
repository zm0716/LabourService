package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.DataCollectionKind2FilterAdapter;
import com.agilefinger.labourservice.adapter.InspectionPositionAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.InspectionPositionBean;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.AddInspectionPositionDialog;
import com.agilefinger.labourservice.view.dialog.EndTaskDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 检测位置
 */
public class InspectionPositionActivity extends BaseActivity {

    @BindView(R.id.activity_inspection_position_ll_wrapper)
    LinearLayout llWrapper;
    @BindView(R.id.activity_inspection_position_rv_list)
    RecyclerView rvList;
    private InspectionPositionAdapter inspectionPositionAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_position;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarWhite("检测位置");
        initRV();
    }

    private void initRV() {
        inspectionPositionAdapter = new InspectionPositionAdapter();
        inspectionPositionAdapter.setEmptyView(emptyInspectionPositionView(rvList));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(inspectionPositionAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        inspectionPositionAdapter.setNewData(LoadData.getInspectionPositionList());
        setWrapperBg();
    }

    @Override
    @OnClick({R.id.activity_inspection_position_rtv_add_inspection_building, R.id.activity_inspection_position_rtv_next})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_inspection_position_rtv_add_inspection_building:
                showAddInspectionPositionDialog();
                break;
            case R.id.activity_inspection_position_rtv_next:
                //IntentUtils.startActivity(InspectionPositionActivity.this, DataCollectionActivity.class);
                break;
        }
    }

    private AddInspectionPositionDialog addInspectionPositionDialog;

    private void showAddInspectionPositionDialog() {
        closeAddInspectionPositionDialog();
        addInspectionPositionDialog = new AddInspectionPositionDialog(this, new AddInspectionPositionDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                closeAddInspectionPositionDialog();

            }
        });
        addInspectionPositionDialog.show();
    }

    private void closeAddInspectionPositionDialog() {
        if (addInspectionPositionDialog != null) {
            addInspectionPositionDialog.cancel();
            addInspectionPositionDialog = null;
        }
    }

    private void setWrapperBg() {
        if (inspectionPositionAdapter != null && inspectionPositionAdapter.getData().size() > 0) {
            llWrapper.setBackgroundColor(getResources().getColor(R.color.gray_eeeeee));
        } else {
            llWrapper.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
}

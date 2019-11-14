package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.AddInspectionPositionAdapter;
import com.agilefinger.labourservice.data.LoadData;

public class AddInspectionPositionDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private RecyclerView rvList;
    private TextView tvBuilding, tvGroup;
    private View vBuilding, vGroup;
    private TextView[] tvTab = new TextView[2];
    private View[] vIndicator = new View[2];
    private AddInspectionPositionAdapter addInspectionPositionAdapter;
    private int curPosition = 0;//0 楼号 1 班组

    public AddInspectionPositionDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_inspection_position);
        initView();
    }

    private void initView() {
        tvBuilding = findViewById(R.id.dialog_add_inspection_position_tv_building);
        tvBuilding.setOnClickListener(this);
        tvGroup = findViewById(R.id.dialog_add_inspection_position_tv_group);
        tvGroup.setOnClickListener(this);
        vBuilding = findViewById(R.id.dialog_add_inspection_position_v_building);
        vGroup = findViewById(R.id.dialog_add_inspection_position_v_group);
        addInspectionPositionAdapter = new AddInspectionPositionAdapter();
        rvList = findViewById(R.id.dialog_add_inspection_position_rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setAdapter(addInspectionPositionAdapter);
      //  addInspectionPositionAdapter.setNewData(LoadData.getStringList(items));
        findViewById(R.id.dialog_add_inspection_position_rtv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_add_inspection_position_rtv_save).setOnClickListener(this);

        tvTab = new TextView[]{tvBuilding, tvGroup};
        vIndicator = new View[]{vBuilding, vGroup};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_add_inspection_position_rtv_save:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            case R.id.dialog_add_inspection_position_rtv_cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                break;
            case R.id.dialog_add_inspection_position_tv_building:
                setTab(0);
                break;
            case R.id.dialog_add_inspection_position_tv_group:
                setTab(1);
                break;
        }
    }

    private void setTab(int i) {
        tvTab[curPosition].setTextColor(mContext.getResources().getColor(R.color.black_333333));
        vIndicator[curPosition].setVisibility(View.INVISIBLE);
        tvTab[i].setTextColor(mContext.getResources().getColor(R.color.blue_0079e4));
        vIndicator[i].setVisibility(View.VISIBLE);
        if (i == 0) {

        } else {

        }
        curPosition = i;
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String... param);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
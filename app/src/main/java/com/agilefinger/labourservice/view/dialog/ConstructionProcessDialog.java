package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.ConstructionProcessBean;
import com.agilefinger.labourservice.bean.UnitBean;
import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.util.List;

public class ConstructionProcessDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private ConstructionProcessBean constructionProcess;
    private List<ConstructionProcessBean> mList;

    public ConstructionProcessDialog(Context context, List<ConstructionProcessBean> mList, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.mList = mList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_construction_process_picker);
        initView();
    }

    private void initView() {
        PickerView pickerView = findViewById(R.id.dialog_construction_process_picker_c_picker);
        pickerView.setItems(this.mList, new PickerView.OnItemSelectedListener<ConstructionProcessBean>() {
            @Override
            public void onItemSelected(ConstructionProcessBean item) {
                constructionProcess = item;
            }
        });
        constructionProcess = this.mList.get(0);
        findViewById(R.id.dialog_construction_process_picker_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_construction_process_picker_tv_complete).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_construction_process_picker_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_construction_process_picker_tv_complete:
                listener.onClick(this, true, constructionProcess);
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, Object... param);
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
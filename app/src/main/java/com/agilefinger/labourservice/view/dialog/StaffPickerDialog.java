package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.util.List;

public class StaffPickerDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private StaffBean staffBean;
    private List<StaffBean> data;

    public StaffPickerDialog(Context context, List<StaffBean> data, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_staff_picker);
        initView();
    }

    private void initView() {
        PickerView pickerView = findViewById(R.id.dialog_staff_picker_s_picker);
        pickerView.setItems(data, new PickerView.OnItemSelectedListener<StaffBean>() {
            @Override
            public void onItemSelected(StaffBean item) {
                staffBean = item;
            }
        });
        staffBean = data.get(0);
        findViewById(R.id.dialog_staff_picker_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_staff_picker_tv_complete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_staff_picker_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_staff_picker_tv_complete:
                listener.onClick(this, true, staffBean);
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
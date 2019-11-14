package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.LaunchActivity;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.util.Calendar;
import java.util.List;

public class CompanyPickerDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private CompanyBean company;
    private List<CompanyBean> mList;

    public CompanyPickerDialog(Context context, List<CompanyBean> mList, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.mList = mList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_company_picker);
        initView();
    }

    private void initView() {
        PickerView pickerView = findViewById(R.id.dialog_company_picker_c_picker);
        pickerView.setItems(this.mList, new PickerView.OnItemSelectedListener<CompanyBean>() {
            @Override
            public void onItemSelected(CompanyBean item) {
                company = item;
            }
        });
        company = this.mList.get(0);
        findViewById(R.id.dialog_company_picker_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_company_picker_tv_complete).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_company_picker_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_company_picker_tv_complete:
                listener.onClick(this, true, company);
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
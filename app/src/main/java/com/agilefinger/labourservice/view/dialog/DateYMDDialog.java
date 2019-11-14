package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateYMDDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private String deadline, deadlineF;

    public DateYMDDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_ymd);
        initView();
    }

    private void initView() {
        DateTimePickerView dateTimePickerView = findViewById(R.id.dialog_date_picker_dt_picker);
        dateTimePickerView.setType(DateTimePickerView.TYPE_YEAR_MONTH_DAY);
        dateTimePickerView.setStartDate(new GregorianCalendar(1970, 1, 1));
        dateTimePickerView.setSelectedDate(Calendar.getInstance());
        dateTimePickerView.setEndDate(DateFormatUtils.postponeYears(10));
        dateTimePickerView.setOnSelectedDateChangedListener(new DateTimePickerView.OnSelectedDateChangedListener() {
            @Override
            public void onSelectedDateChanged(Calendar date) {
                deadline = DateFormatUtils.formatDate(date);
                deadlineF = DateFormatUtils.formatDate_YMD(date);
            }
        });
        findViewById(R.id.dialog_date_picker_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_date_picker_tv_complete).setOnClickListener(this);
        deadline = DateFormatUtils.formatDate(dateTimePickerView.getSelectedDate());
        deadlineF = DateFormatUtils.formatDate_YMD(dateTimePickerView.getSelectedDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_date_picker_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_date_picker_tv_complete:
                listener.onClick(this, true, deadline, deadlineF);
                break;
        }
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
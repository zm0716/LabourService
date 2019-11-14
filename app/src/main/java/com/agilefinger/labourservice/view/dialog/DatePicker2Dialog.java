package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePicker2Dialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private String deadline, deadlineF;
    private String begin;
    private int type = -1;

    public DatePicker2Dialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    public DatePicker2Dialog(Context context, String begin, int type, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.begin = begin;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_picker);
        initView();
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance();
        DateTimePickerView dateTimePickerView = findViewById(R.id.dialog_date_picker_dt_picker);
        dateTimePickerView.setStartDate(new GregorianCalendar(1970, 1, 1));
        if (!TextUtils.isEmpty(this.begin)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(this.begin);
                calendar = Calendar.getInstance();
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        final SimpleDateFormat format;
        dateTimePickerView.setType(DateTimePickerView.TYPE_YEAR_MONTH_DAY_HOUR_MINUTE);
        dateTimePickerView.setMinutesInterval(1);
        if (type != -1) {
            dateTimePickerView.setType(type);
            format = new SimpleDateFormat("yyyy/MM/dd");
        } else {
            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }

        dateTimePickerView.setSelectedDate(calendar);
        dateTimePickerView.setEndDate(DateFormatUtils.postponeYears(10));
        dateTimePickerView.setOnSelectedDateChangedListener(new DateTimePickerView.OnSelectedDateChangedListener() {
            @Override
            public void onSelectedDateChanged(Calendar date) {
                deadline = format.format(date.getTime());
                deadlineF = DateFormatUtils.formatDeadline(date);
            }
        });
        findViewById(R.id.dialog_date_picker_tv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_date_picker_tv_complete).setOnClickListener(this);
        deadline = format.format(calendar.getTime());
        deadlineF = DateFormatUtils.formatDeadline(calendar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_date_picker_tv_cancel:
                listener.onClick(this, false);
                break;
            case R.id.dialog_date_picker_tv_complete:
                if (!TextUtils.isEmpty(this.begin)) {
                    if (this.begin.compareTo(deadline)==9) {
                        listener.onClick(this, true, deadline, deadlineF);
                    }else if (this.begin.compareTo(deadline)>0){
                        ToastUtils.showShortSafe("结束日期不小于开始日期");
                        return;
                    }
                }
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
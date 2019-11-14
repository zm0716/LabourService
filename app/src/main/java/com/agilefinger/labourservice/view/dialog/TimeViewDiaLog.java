package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.view.custom.CalendarList;
import com.agilefinger.labourservice.widget.spinner.MaterialSpinner;

import java.util.List;

public class TimeViewDiaLog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private MaterialSpinner spinnerReason;
    private EditText etExtra;
    //    private boolean isCanBack = false;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> reasonList;
    private CalendarList calendarList;

    public TimeViewDiaLog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    public TimeViewDiaLog(Context context, boolean isCanBack, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
//        this.isCanBack = isCanBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_view_dialog);
        setCanceledOnTouchOutside(true);
//        if (!isCanBack) {
//            setCancelable(false);
//        }
        initView();
    }

    private void initView() {

        findViewById(R.id.m_back).setOnClickListener(this);
        findViewById(R.id.m_queding).setOnClickListener(this);
        calendarList = findViewById(R.id.calendarList);
        calendarList.setOnDateSelected(new CalendarList.OnDateSelected() {
            @Override
            public void selected(String startDate, String endDate) {
//                        Toast.makeText(getApplicationContext(),"s:"+startDate+"e:"+endDate,Toast.LENGTH_LONG).show();
                start_time = startDate;
                end_time = endDate;
            }
        });

    }

    String start_time, end_time;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                dismiss();
                break;
            case R.id.m_queding:
                if (start_time != null && end_time != null) {
                    listener.onClick(TimeViewDiaLog.this, start_time, end_time);
                    dismiss();
                }
                break;

        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, String begin, String end);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().getDecorView().setPadding(AutoSizeUtils.dp2px(mContext, 0), 0, AutoSizeUtils.dp2px(mContext, 24), 0);

        getWindow().setAttributes(layoutParams);
    }
}
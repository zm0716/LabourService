package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.utils.CommonUtils;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.widget.spinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class TimeDiaLog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private MaterialSpinner spinnerReason;
    private EditText etExtra;
    //    private boolean isCanBack = false;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> reasonList;
    private KanBanDetailBean.DetailsBean detlis;
    private static Date parse;

    public TimeDiaLog(Context context, KanBanDetailBean.DetailsBean detlis, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.detlis = detlis;
    }

    public TimeDiaLog(Context context, boolean isCanBack, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
//        this.isCanBack = isCanBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        setCanceledOnTouchOutside(true);
//        if (!isCanBack) {
//            setCancelable(false);
//        }
        initView();
    }

    private void initView() {

        findViewById(R.id.m_text_all).setOnClickListener(this);
        findViewById(R.id.m_zhou).setOnClickListener(this);
        findViewById(R.id.m_month).setOnClickListener(this);
        findViewById(R.id.m_zidingyi).setOnClickListener(this);

    }

    Calendar calendars;
    //chek上
    String start_time, end_time;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_text_all:
                //获取当前年月日
                calendars = Calendar.getInstance();
                calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                String year = String.valueOf(calendars.get(Calendar.YEAR));
                String month = String.valueOf(calendars.get(Calendar.MONTH) + 1);
                String day = String.valueOf(calendars.get(Calendar.DATE));
                start_time = year + "/" + month + "/" + day;
                end_time = detlis.getCreate_time();

                if (listener != null) {
                    listener.onClickCreat(getNowDateShort(end_time));
                }
                break;
            case R.id.m_zhou:
                showWeekOrMonth("WEEK");
                if (listener != null) {
                    listener.onClick(this, start_time, end_time);
                }
                break;
            case R.id.m_month:
                showWeekOrMonth("MONTH");
                if (listener != null) {
                    listener.onClick(this, start_time, end_time);
                }
                break;
            case R.id.m_zidingyi:
//                TimeViewDiaLog timeViewDiaLog = new TimeViewDiaLog(mContext, new TimeViewDiaLog.OnCloseListener() {
//                    @Override
//                    public void onClick(Dialog dialog, String begin, String end) {
//                        if (listener != null) {
//                            listener.onClick(TimeDiaLog.this, end, begin);
//                        }
//                        dismiss();
//                    }
//                });
//                timeViewDiaLog.show();
                if (listener != null) {
                    listener.onClick2(this);
                }
                break;

        }
    }


    private void showWeekOrMonth(String w) {
        if ("WEEK".equals(w)) {
            end_time = nearWeek(-1);
            start_time = formatDate2(new GregorianCalendar());
        } else if ("MONTH".equals(w)) {
            end_time = nearMonth2(-1);
            start_time = formatDate2(new GregorianCalendar());
        }
    }

    public String formatDate2(Calendar calendar) {
        Calendar cal = calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd ");
        return format.format(date);
    }

    public String nearWeek(int week) {
        week = week * 7;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, week);
        return format.format(calendar.getTime());
    }

    public String nearMonth2(int month) {
        month = month * 30;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, month);
        return format.format(calendar.getTime());
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, String begin, String end);

        void onClick2(Dialog dialog);

        void onClickCreat(String creaTime);
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

    public static String getNowDateShort(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parse = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(parse);
        return dateString;
    }

}
package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.MainActivity;
import com.flyco.roundview.RoundTextView;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class DonLodDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private TextView tvTitle;
    private TextView m_time;
    private String title;
    private String time;
    private String type;
//    private boolean isShowCancel = false, isCanBack = false;

    public DonLodDialog(Context context, int themeResId, String title, String time, String type, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.time = time;
        this.type = type;
//        this.isShowCancel = isShowCancel;
    }

    public DonLodDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
//        this.isCanBack = true;
//        this.isShowCancel = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_log_dialog);

        setCanceledOnTouchOutside(false);
        if (type.equals("y")) {
            setCancelable(false);
        }else {
            setCancelable(true);
        }
        initView();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.m_title);
        m_time = (TextView) findViewById(R.id.m_time);
        tvTitle.setText("发现新版本:" + title);
        m_time.setText("发布日期：" + time);
        findViewById(R.id.m_sure).setOnClickListener(this);
        Button tvCancel = (Button) findViewById(R.id.m_back);
//        if (type.equals("y")) {
//            setCancelable(false);
//        }else {
//            setCancelable(true);
//        }
        tvCancel.setOnClickListener(this);


//        if (isShowCancel) {
//            tvCancel.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_sure:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            case R.id.m_back:
                if (type.equals("y")) {
                    listener.onBackClick(this);
                } else {
                    listener.onBackClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String... param);

        void onBackClick(Dialog dialog);

        void onBackClick(Dialog dialog, Boolean a);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(AutoSizeUtils.dp2px(mContext, 24), 0, AutoSizeUtils.dp2px(mContext, 24), 0);
        getWindow().setAttributes(layoutParams);
    }
}
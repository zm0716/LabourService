package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.agilefinger.labourservice.R;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class ConfirmDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private TextView tvTitle;
    private String title;
    private boolean isShowCancel = false, isCanBack = false;

    public ConfirmDialog(Context context, String title, boolean isShowCancel, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.isShowCancel = isShowCancel;
    }

    public ConfirmDialog(Context context, String title, boolean isShowCancel, boolean isCanBack, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.title = title;
        this.isCanBack = isCanBack;
        this.isShowCancel = isShowCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        setCanceledOnTouchOutside(false);
        if (!isCanBack) {
            setCancelable(false);
        }
        initView();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.dialog_confirm_tv_title);
        tvTitle.setText(title);
        findViewById(R.id.dialog_confirm_rtv_ok).setOnClickListener(this);
        TextView tvCancel = (TextView) findViewById(R.id.dialog_confirm_tv_cancel);
        tvCancel.setOnClickListener(this);
        if (isShowCancel) {
            tvCancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_rtv_ok:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            case R.id.dialog_confirm_tv_cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
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
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(AutoSizeUtils.dp2px(mContext, 24), 0, AutoSizeUtils.dp2px(mContext, 24), 0);
        getWindow().setAttributes(layoutParams);
    }
}
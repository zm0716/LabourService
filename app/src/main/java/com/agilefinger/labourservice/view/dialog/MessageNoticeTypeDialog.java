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

public class MessageNoticeTypeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;

    public MessageNoticeTypeDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message_notice_type);
        initView();
    }

    private void initView() {
        findViewById(R.id.dialog_message_notice_type_rtv_correct).setOnClickListener(this);
        findViewById(R.id.dialog_message_notice_type_rtv_all).setOnClickListener(this);
        findViewById(R.id.dialog_message_notice_type_rtv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_message_notice_type_rtv_progress).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_message_notice_type_rtv_all:
                listener.onClick(this, true, "全部通知");
                break;
            case R.id.dialog_message_notice_type_rtv_correct:
                listener.onClick(this, true, "整改");
                break;
            case R.id.dialog_message_notice_type_rtv_progress:
                listener.onClick(this, true, "进度");
                break;
            case R.id.dialog_message_notice_type_rtv_cancel:
                listener.onClick(this, false);
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
        getWindow().getDecorView().setPadding(AutoSizeUtils.dp2px(mContext, 6), 0, AutoSizeUtils.dp2px(mContext, 6), AutoSizeUtils.dp2px(mContext, 6));
        getWindow().setAttributes(layoutParams);
    }
}
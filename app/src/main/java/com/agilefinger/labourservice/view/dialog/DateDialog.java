package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BuildingNoAdapter;
import com.agilefinger.labourservice.adapter.DirectionAdapter;
import com.agilefinger.labourservice.adapter.FloorAdapter;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;

import java.util.logging.ConsoleHandler;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class DateDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;

    public DateDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date);
        initView();
    }

    private void initView() {
        findViewById(R.id.dialog_date_rtv_6).setOnClickListener(this);
        findViewById(R.id.dialog_date_rtv_12).setOnClickListener(this);
        findViewById(R.id.dialog_date_rtv_24).setOnClickListener(this);
        findViewById(R.id.dialog_date_rtv_customize).setOnClickListener(this);
        findViewById(R.id.dialog_date_rtv_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_date_rtv_6:
                listener.onClick(this, true, String.valueOf(24 * 1));
                break;
            case R.id.dialog_date_rtv_12:
                listener.onClick(this, true, String.valueOf(24 * 3));
                break;
            case R.id.dialog_date_rtv_24:
                listener.onClick(this, true, String.valueOf(24 * 7));
                break;
            case R.id.dialog_date_rtv_customize:
                listener.onClick(this, true, Constants.CUSTOMIZE);
                break;
            case R.id.dialog_date_rtv_cancel:
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
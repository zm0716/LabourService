package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.EndTaskAdapter;
import com.agilefinger.labourservice.data.LoadData;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class EndTaskDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private RecyclerView rvList;
    private EndTaskAdapter endTaskAdapter;

    public EndTaskDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_end_task);
        initView();
    }

    private void initView() {
        endTaskAdapter = new EndTaskAdapter();
        rvList = findViewById(R.id.dialog_end_task_rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.setAdapter(endTaskAdapter);
      //  endTaskAdapter.setNewData(LoadData.getStringList(items));
        findViewById(R.id.dialog_end_task_rll_sign).setOnClickListener(this);
        findViewById(R.id.dialog_end_task_rll_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_end_task_rll_sign:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
            case R.id.dialog_end_task_rll_ok:
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
        getWindow().getDecorView().setPadding(AutoSizeUtils.dp2px(mContext, 14), 0, AutoSizeUtils.dp2px(mContext, 14), 0);
        getWindow().setAttributes(layoutParams);
    }
}
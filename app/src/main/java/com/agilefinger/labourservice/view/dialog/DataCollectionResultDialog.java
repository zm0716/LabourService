package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.AddInspectionPositionAdapter;
import com.agilefinger.labourservice.data.LoadData;

public class DataCollectionResultDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;

    public DataCollectionResultDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data_collection_result);
        initView();
    }

    private void initView() {
        findViewById(R.id.dialog_data_collection_result_rtv_position).setOnClickListener(this);
        findViewById(R.id.dialog_data_collection_result_rtv_illustration).setOnClickListener(this);
        findViewById(R.id.dialog_data_collection_result_rtv_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_data_collection_result_rtv_position:
                if (listener != null) {
                    listener.onClick(this, true, "0");
                }
                break;
            case R.id.dialog_data_collection_result_rtv_illustration:
                if (listener != null) {
                    listener.onClick(this, true, "1");
                }
                break;
            case R.id.dialog_data_collection_result_rtv_cancel:
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
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
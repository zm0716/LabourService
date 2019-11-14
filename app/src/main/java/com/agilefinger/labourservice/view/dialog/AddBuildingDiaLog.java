package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class AddBuildingDiaLog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private EditText m_add_editext;
    private Button m_back;
    private Button m_add;
    private boolean isShowCancel = false, isCanBack = false;

    public AddBuildingDiaLog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_building_dialog);
        setCanceledOnTouchOutside(false);
        if (!isCanBack) {
            setCancelable(false);
        }
        initView();
    }

    private void initView() {
        m_add_editext = (EditText) findViewById(R.id.m_add_editext);
        findViewById(R.id.m_back).setOnClickListener(this);
        findViewById(R.id.m_add).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                dismiss();
                break;

            case R.id.m_add:
                String trim = m_add_editext.getText().toString().trim();
                listener.onClick(this, trim);
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, String text);
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
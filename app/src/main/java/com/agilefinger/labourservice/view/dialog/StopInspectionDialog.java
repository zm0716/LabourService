package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.utils.CommonUtils;
import com.agilefinger.labourservice.widget.spinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class StopInspectionDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnCloseListener listener;
    private MaterialSpinner spinnerReason;
    private EditText etExtra;
    private boolean isCanBack = false;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> reasonList;

    public StopInspectionDialog(Context context, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    public StopInspectionDialog(Context context, boolean isCanBack, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.isCanBack = isCanBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_stop_inspection);
        setCanceledOnTouchOutside(false);
        if (!isCanBack) {
            setCancelable(false);
        }
        initView();
    }

    private void initView() {
        reasonList = new ArrayList<>();
        reasonList = CommonUtils.getResourceArray(mContext, R.array.stop_inspection_reason);
        spinnerReason = (MaterialSpinner) findViewById(R.id.dialog_stop_inspection_sp_reason);
        spinnerReason.setItems(reasonList);
        spinnerReason.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
        etExtra = (EditText) findViewById(R.id.dialog_stop_inspection_et_extra);
        findViewById(R.id.dialog_stop_inspection_rtv_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_stop_inspection_rtv_ok).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_stop_inspection_rtv_ok:
                if (listener != null) {
                    listener.onClick(this, true,etExtra.getText().toString());
                }
                break;
            case R.id.dialog_stop_inspection_rtv_cancel:
                if (listener != null) {
                    listener.onClick(this, false,etExtra.getText().toString());
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String param);
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
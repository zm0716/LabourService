package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JcbgBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class JceBaoGaoAdapter extends BaseQuickAdapter<JcbgBean,BaseViewHolder> {

    public JceBaoGaoAdapter(int layoutResId, @Nullable List<JcbgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JcbgBean item) {
        helper.setText(R.id.text_name,item.getName());
        helper.setText(R.id.text_score,item.getScore()+"分");
    }
}

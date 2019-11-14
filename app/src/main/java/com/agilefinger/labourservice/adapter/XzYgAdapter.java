package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JcbgBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class XzYgAdapter extends BaseQuickAdapter<JcbgBean,BaseViewHolder> {

    public XzYgAdapter(int layoutResId, @Nullable List<JcbgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JcbgBean item) {
        helper.setText(R.id.text_naame,item.getName());
    }
}

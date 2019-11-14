package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.EndBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TextListAdapter extends BaseQuickAdapter<EndBean.DataBean.PositionBean, BaseViewHolder> {

    public TextListAdapter() {
        super(R.layout.text_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, EndBean.DataBean.PositionBean item) {
        helper.setText(R.id.m_buing_name, item.getName());
        helper.setText(R.id.buildingtv, item.getGroup());

    }
}

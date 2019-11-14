package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class KanBanListTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public KanBanListTypeAdapter() {
        super(R.layout.item_list_type, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.m_type, item);
    }
}

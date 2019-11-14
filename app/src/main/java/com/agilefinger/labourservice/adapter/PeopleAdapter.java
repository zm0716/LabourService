package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PeopleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PeopleAdapter() {
        super(R.layout.people_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.)
        helper.addOnClickListener(R.id.m_shanchu);
    }
}

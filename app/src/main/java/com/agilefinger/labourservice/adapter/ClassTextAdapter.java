package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;
import android.text.Html;

import com.agilefinger.labourservice.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ClassTextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ClassTextAdapter() {
        super(R.layout.item_class_text, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.m_text_name, Html.fromHtml(item));
    }
}

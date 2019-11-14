package com.agilefinger.labourservice.adapter;

import android.text.TextUtils;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ProgressAdapter extends BaseQuickAdapter<ProgressBean, BaseViewHolder> {
    public ProgressAdapter() {
        super(R.layout.item_progress, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProgressBean item) {
        helper.setText(R.id.item_progress_tv_title, item.getUsername() + "的施工进度");
        helper.setText(R.id.item_progress_tv_project, item.getProject_name());//项目名
        helper.setText(R.id.item_progress_tv_content, item.getDescription());
        helper.setText(R.id.item_progress_tv_time, TextUtils.isEmpty(item.getDate()) ? "" : item.getDate());
    }
}

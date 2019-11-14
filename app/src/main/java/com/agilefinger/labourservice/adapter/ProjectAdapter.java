package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ProjectAdapter extends BaseQuickAdapter<ProjectBean, BaseViewHolder> {
    public ProjectAdapter() {
        super(R.layout.item_project, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectBean item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_project_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_project_iv_check, R.mipmap.ic_uncheck);
        }
        helper.setText(R.id.item_project_tv_title, item.getTitle());
        helper.setText(R.id.item_project_tv_subtitle, item.getSubtitle());
    }
}

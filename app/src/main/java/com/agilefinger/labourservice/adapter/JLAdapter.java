package com.agilefinger.labourservice.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.TeamGroupPeopleBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class JLAdapter extends BaseQuickAdapter<TeamGroupPeopleBean.DataBean.ManagerBean, BaseViewHolder> {
    public JLAdapter() {
        super(R.layout.jl_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamGroupPeopleBean.DataBean.ManagerBean item) {

        helper.setText(R.id.item_jl_name, item.getUser_name());
        helper.setText(R.id.join_tv, item.getJoin_time());
    }
}

package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TeamBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class TeamAdapter extends BaseQuickAdapter<TeamBean, BaseViewHolder> {
    public TeamAdapter() {
        super(R.layout.item_team, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamBean item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_team_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_team_iv_check, R.mipmap.ic_uncheck);
        }
        helper.setText(R.id.item_team_tv_name,item.getTeam_name());
    }
}

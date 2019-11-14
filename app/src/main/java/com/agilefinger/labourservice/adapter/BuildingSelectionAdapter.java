package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class BuildingSelectionAdapter extends BaseQuickAdapter<BuildingNoBean, BaseViewHolder> {
    public BuildingSelectionAdapter() {
        super(R.layout.item_building, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuildingNoBean item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_building_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_building_iv_check, R.mipmap.ic_uncheck);
        }
        helper.setText(R.id.item_building_tv_name, item.getName());
    }
}

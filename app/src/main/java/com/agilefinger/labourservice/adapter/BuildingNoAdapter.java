package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class BuildingNoAdapter extends BaseQuickAdapter<BuildingNoBean, BaseViewHolder> {
    public BuildingNoAdapter() {
        super(R.layout.item_building_no, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuildingNoBean item) {
        helper.setText(R.id.item_building_no_tv_title, item.getName());
        if (item.isCheck()) {
            helper.setTextColor(R.id.item_building_no_tv_title, mContext.getResources().getColor(android.R.color.white));
            helper.setBackgroundColor(R.id.item_building_no_tv_title, mContext.getResources().getColor(R.color.blue_349fff));
        } else {
            helper.setTextColor(R.id.item_building_no_tv_title, mContext.getResources().getColor(R.color.black_333333));
            helper.setBackgroundColor(R.id.item_building_no_tv_title, mContext.getResources().getColor(android.R.color.white));
        }
    }
}

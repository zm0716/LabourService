package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class FloorAdapter extends BaseQuickAdapter<FloorBean, BaseViewHolder> {
    public FloorAdapter() {
        super(R.layout.item_floor, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, FloorBean item) {
        helper.setText(R.id.item_floor_tv_title, item.getName());
        RoundTextView rtvTxt = helper.getView(R.id.item_floor_tv_title);
        if (item.isCheck()) {
            helper.setTextColor(R.id.item_floor_tv_title, mContext.getResources().getColor(android.R.color.white));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.blue_349fff));
        } else {
            helper.setTextColor(R.id.item_floor_tv_title, mContext.getResources().getColor(R.color.black_333333));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }
    }
}

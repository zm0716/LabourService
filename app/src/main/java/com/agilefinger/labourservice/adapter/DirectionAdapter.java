package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class DirectionAdapter extends BaseQuickAdapter<DirectionBean, BaseViewHolder> {
    public DirectionAdapter() {
        super(R.layout.item_direction, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DirectionBean item) {
        helper.setText(R.id.item_direction_tv_title, item.getName());
        RoundTextView rtvTxt = helper.getView(R.id.item_direction_tv_title);
        if (item.isCheck()) {
            helper.setTextColor(R.id.item_direction_tv_title, mContext.getResources().getColor(android.R.color.white));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.blue_349fff));
        } else {
            helper.setTextColor(R.id.item_direction_tv_title, mContext.getResources().getColor(R.color.black_333333));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }
    }
}

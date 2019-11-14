package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class LookStateAdapter extends BaseQuickAdapter<StaffBean, BaseViewHolder> {
    public LookStateAdapter() {
        super(R.layout.item_staff, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffBean item) {
        helper.setGone(R.id.item_staff_iv_check, false);
        helper.setText(R.id.item_staff_tv_name, item.getName());
    }
}

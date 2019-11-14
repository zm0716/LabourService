package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class Staff2Adapter extends BaseQuickAdapter<StaffBean, BaseViewHolder> {
    public Staff2Adapter() {
        super(R.layout.item_staff, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffBean item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_uncheck);
        }
        helper.setText(R.id.item_staff_tv_name,item.getName());
    }
}
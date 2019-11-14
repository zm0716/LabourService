package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.InspectionPositionBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class InspectionPositionAdapter extends BaseQuickAdapter<InspectionPositionBean, BaseViewHolder> {
    public InspectionPositionAdapter() {
        super(R.layout.item_inspection_position, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionPositionBean item) {

    }
}

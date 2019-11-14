package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.EndBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ConfirmSubmitInspectionDataAdapter extends BaseQuickAdapter<EndBean.DataBean.ItemsBean, BaseViewHolder> {
    public ConfirmSubmitInspectionDataAdapter(List<EndBean.DataBean.ItemsBean> items) {
        super(R.layout.item_search_inspection_item, items);
    }
    @Override
    protected void convert(BaseViewHolder helper, EndBean.DataBean.ItemsBean item) {
        helper.setText(R.id.item_search_inspection_item_tv_title,item.getMci_name());
        helper.setText(R.id.item_search_inspection_item_tv_subtitle,item.getNames());
        helper.setText(R.id.item_search_inspection_item_tv_num,""+item.getP_count()+"");
    }


}

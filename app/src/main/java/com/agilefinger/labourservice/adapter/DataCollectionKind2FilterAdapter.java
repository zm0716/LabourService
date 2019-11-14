package com.agilefinger.labourservice.adapter;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DataCollectionFilterBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class DataCollectionKind2FilterAdapter extends BaseQuickAdapter<DataCollectionFilterBean, BaseViewHolder> {
    public DataCollectionKind2FilterAdapter() {
        super(R.layout.item_data_collection_filter, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataCollectionFilterBean item) {
        RoundTextView rtvTxt = helper.getView(R.id.item_data_collection_filter_tv_txt);
        rtvTxt.setText(item.getName());
        if (item.isCheck()) {
            rtvTxt.setTextColor(mContext.getResources().getColor(R.color.white));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.blue_0079e4));
        } else {
            rtvTxt.setTextColor(mContext.getResources().getColor(R.color.blue_349fff));
            rtvTxt.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.blue_d6ecff));
        }
    }
}

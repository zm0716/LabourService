package com.agilefinger.labourservice.adapter;

import android.media.Image;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DataCollectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DataCollectionMultiAdapter extends BaseQuickAdapter<DataCollectionBean, BaseViewHolder> {
    public DataCollectionMultiAdapter() {
        super(R.layout.item_data_collection_multi, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataCollectionBean item) {
        ImageView ivCheck = helper.getView(R.id.item_data_collection_multi_iv_check);
        if (item.isCheck()) {
            ivCheck.setImageResource(R.mipmap.ic_checked);
        } else {
            ivCheck.setImageResource(R.mipmap.ic_unchecked);
        }
    }
}

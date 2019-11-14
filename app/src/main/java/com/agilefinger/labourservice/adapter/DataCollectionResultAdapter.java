package com.agilefinger.labourservice.adapter;

import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DataCollectionResultAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DataCollectionResultAdapter() {
        super(R.layout.item_data_collection_result, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}

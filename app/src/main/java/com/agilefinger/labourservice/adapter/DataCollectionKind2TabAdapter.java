package com.agilefinger.labourservice.adapter;

import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DataCollectionKind2TabAdapter extends BaseQuickAdapter<DataCollectionTabBean, BaseViewHolder> {
    public DataCollectionKind2TabAdapter() {
        super(R.layout.item_data_collection_tab, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataCollectionTabBean item) {
        TextView tvTxt = helper.getView(R.id.item_data_collection_tab_tv_txt);
        View vLine = helper.getView(R.id.item_data_collection_tab_v_line);
        tvTxt.setText(item.getName());
        if (item.isCheck()) {
            tvTxt.setTextColor(mContext.getResources().getColor(R.color.blue_0079e4));
            vLine.setVisibility(View.VISIBLE);
        } else {
            tvTxt.setTextColor(mContext.getResources().getColor(R.color.black_333333));
            vLine.setVisibility(View.INVISIBLE);
        }
    }
}

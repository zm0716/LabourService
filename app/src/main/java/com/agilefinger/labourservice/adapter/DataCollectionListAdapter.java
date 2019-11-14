package com.agilefinger.labourservice.adapter;

import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class DataCollectionListAdapter extends BaseQuickAdapter<DataCollectionTabBean, BaseViewHolder> {
    public DataCollectionListAdapter() {
        super(R.layout.item_data_collection_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataCollectionTabBean item) {
        TextView tvTxt = helper.getView(R.id.item_data_collection_list_tv_title);
//        helper.getView(R.id.item_data_collection_list_tv_num).setVisibility(View.VISIBLE);
//        String s = "(" + item.getNumber() + "/" + item.getCount() + ")";
//        helper.setText(R.id.item_data_collection_list_tv_num, "(" + item.getNumber() + "/" + item.getCount() + ")");
        RoundTextView piliang = helper.getView(R.id.item_catalogue_rtv_multi);
        tvTxt.setText(item.getName());
        if (item.getIsbatch().equals("y")) {
            piliang.setVisibility(View.VISIBLE);
        } else {
            piliang.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.item_catalogue_rtv_multi);
    }
}

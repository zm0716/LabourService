package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CatalogNodeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ClassificationAdapter extends BaseQuickAdapter<CatalogNodeBean,BaseViewHolder> {

    public ClassificationAdapter(int layoutResId, @Nullable List<CatalogNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatalogNodeBean item) {
        TextView tvTxt = helper.getView(R.id.text_fl_name);
        View vLine = helper.getView(R.id.item_data_collection_tab_v_linee);
        tvTxt.setText(item.getTct_name());

        if (item.isCheck()) {
            tvTxt.setTextColor(mContext.getResources().getColor(R.color.blue_0079e4));
            vLine.setVisibility(View.VISIBLE);
        } else {
            tvTxt.setTextColor(mContext.getResources().getColor(R.color.black_333333));
            vLine.setVisibility(View.INVISIBLE);
        }
    }
}

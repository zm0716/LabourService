package com.agilefinger.labourservice.adapter;

import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.ImagesBean;
import com.agilefinger.labourservice.bean.RedLineBean;
import com.agilefinger.labourservice.common.URL;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;
import com.squareup.picasso.Picasso;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class RedLineAdapter extends BaseQuickAdapter<RedLineBean, BaseViewHolder> {
    public RedLineAdapter() {
        super(R.layout.red_line_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedLineBean item) {
        helper.setText(R.id.m_name, item.getMci_name());
        helper.setText(R.id.m_number, item.getMci_number() + "");
        helper.setText(R.id.m_rate, item.getRate());
    }

}

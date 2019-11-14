package com.agilefinger.labourservice.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KBServiceBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class SerViceSearchAdapter extends BaseQuickAdapter<KBServiceBean, BaseViewHolder> {
    public SerViceSearchAdapter() {
        super(R.layout.serviece_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KBServiceBean item) {
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_uncheck);
        }
        ImageView viewById = helper.getView(R.id.m_right_img);

        TextView view = helper.getView(R.id.item_staff_tv_name);
        if (item.getSubCompany() != null && item.getSubCompany().size() > 0) {
//            viewById.setVisibility(View.VISIBLE);
            view.setTextColor(Color.parseColor("#349fff"));
        } else {
//            viewById.setVisibility(View.GONE);
            view.setTextColor(Color.parseColor("#333333"));
        }

        helper.setText(R.id.item_staff_tv_name, item.getName());
        helper.addOnClickListener(R.id.item_staff_iv_check);
    }
}
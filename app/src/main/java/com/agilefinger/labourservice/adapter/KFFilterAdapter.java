package com.agilefinger.labourservice.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class KFFilterAdapter extends BaseQuickAdapter<StaffBean, BaseViewHolder> {
    public KFFilterAdapter() {
        super(R.layout.item_filter_kf, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffBean item) {
        if (item.isAdd() || TextUtils.isEmpty(item.getName())) {
            helper.getView(R.id.m_delete).setVisibility(View.GONE);
            helper.getView(R.id.m_view).setVisibility(View.VISIBLE);
            TextView view = helper.getView(R.id.m_text_name);
            view.setTextColor(Color.parseColor("#349FFF"));
            helper.setText(R.id.m_text_name, "添加");
//            view.setText("添加");


        } else {
            helper.getView(R.id.m_view).setVisibility(View.GONE);
            TextView view = helper.getView(R.id.m_text_name);
            view.setTextColor(Color.parseColor("#333333"));
//            view.setText("添加");
            helper.getView(R.id.m_delete).setVisibility(View.VISIBLE);
//            ((ImageView) helper.getView(R.id.item_zg_zp_iv_image)).setImageResource(R.mipmap.portrait);
            helper.setText(R.id.m_text_name, item.getName());
        }
        helper.addOnClickListener(R.id.m_delete);
    }
}
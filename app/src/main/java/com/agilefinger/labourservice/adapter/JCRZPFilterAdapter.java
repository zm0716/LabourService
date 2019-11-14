package com.agilefinger.labourservice.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class JCRZPFilterAdapter extends BaseQuickAdapter<JianCeRenBean.DataBean, BaseViewHolder> {
    public JCRZPFilterAdapter() {
        super(R.layout.item_zg_zp, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, JianCeRenBean.DataBean item) {
        if (item.isAdd() || TextUtils.isEmpty(item.getName())) {
            helper.getView(R.id.item_zg_zp_iv_del).setVisibility(View.GONE);
            ((ImageView) helper.getView(R.id.item_zg_zp_iv_image)).setImageResource(R.mipmap.ic_add);
        } else {
            helper.getView(R.id.item_zg_zp_iv_del).setVisibility(View.VISIBLE);
            ((ImageView) helper.getView(R.id.item_zg_zp_iv_image)).setImageResource(R.mipmap.portrait);
        }
        helper.setText(R.id.item_zg_zp_tv_name, item.getName());
        helper.addOnClickListener(R.id.item_zg_zp_iv_del);
    }
}

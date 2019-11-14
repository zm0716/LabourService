package com.agilefinger.labourservice.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.SonAndItemsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class CatalogueAdapter extends BaseQuickAdapter<SonAndItemsBean, BaseViewHolder> {

    public CatalogueAdapter() {
        super(R.layout.item_catalogue, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SonAndItemsBean item) {
        RoundTextView rtvMulti = helper.getView(R.id.item_catalogue_rtv_multi);
        LinearLayout buju = helper.getView(R.id.buju);
        LinearLayout llNum = helper.getView(R.id.item_catalogue_ll_num);
        helper.setText(R.id.item_catalogue_tv_title, item.getName());
        helper.addOnClickListener(R.id.item_catalogue_rtv_multi);
        boolean son = item.isSon();
        if (son) {
            if (TextUtils.equals("n", item.getMct_is_batch())) {
                rtvMulti.setVisibility(View.GONE);
                //llNum.setVisibility(View.VISIBLE);
            } else {
                rtvMulti.setVisibility(View.VISIBLE);
                // llNum.setVisibility(View.GONE);

            }
            buju.setBackgroundColor(Color.WHITE);
        } else {
            rtvMulti.setVisibility(View.GONE);
            llNum.setVisibility(View.VISIBLE);
            buju.setBackgroundColor(Color.parseColor("#ededed"));
            if ((""+item.getMci_min_point()+"").equals("0")){
                helper.setText(R.id.item_catalogue_tv_num,""+item.getP_count());
            }else {
                helper.setText(R.id.item_catalogue_tv_num,"("+item.getP_count()+"/"+item.getMci_min_point()+")");
            }

        }

    }
}

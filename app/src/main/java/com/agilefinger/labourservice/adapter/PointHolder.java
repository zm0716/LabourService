package com.agilefinger.labourservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.flyco.roundview.RoundLinearLayout;


/**
 * Created by Shinelon on 2018/9/7.
 */

public class PointHolder extends RecyclerView.ViewHolder{

    RoundLinearLayout rllWrapper;
    TextView item_inspection_item_tv_num;
    TextView item_inspection_item_tv_state;
    public PointHolder(View view) {
        super(view);
        rllWrapper = (RoundLinearLayout) view.findViewById(R.id.item_inspection_item_rll_wrapper);
        item_inspection_item_tv_num= (TextView) view.findViewById(R.id.item_inspection_item_tv_num);
        item_inspection_item_tv_state= (TextView) view.findViewById(R.id.item_inspection_item_tv_state);
    }

}

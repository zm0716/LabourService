package com.agilefinger.labourservice.adapter;

import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.InspectionItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundLinearLayout;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class InspectionItemAdapter extends BaseQuickAdapter<InspectionItemBean, BaseViewHolder> {
    public InspectionItemAdapter() {
        super(R.layout.item_inspection_item, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionItemBean item) {
        RoundLinearLayout rllWrapper = helper.getView(R.id.item_inspection_item_rll_wrapper);
        TextView tvNum = helper.getView(R.id.item_inspection_item_tv_num);
        TextView tvState = helper.getView(R.id.item_inspection_item_tv_state);
        int bgColor = mContext.getResources().getColor(R.color.blue_0079e4);
        String state = "未设置";
        if (item.getState() == 1) { //合格
            bgColor = mContext.getResources().getColor(R.color.blue_0079e4);
            state = "合格";
        } else if (item.getState() == 2) { //不合格
            bgColor = mContext.getResources().getColor(R.color.red_ff0000);
            state = "不合格";
        } else if (item.getState() == 3) {//未设置
            bgColor = mContext.getResources().getColor(R.color.gray_eeeeee);
            state = "未设置";
        } else if (item.getState() == 4) {//添加点位
            bgColor = mContext.getResources().getColor(R.color.gray_999999);
            state = "增加点位";
        }
        rllWrapper.getDelegate().setStrokeColor(bgColor);
        rllWrapper.getDelegate().setStrokeWidth(1);
        tvNum.setText((helper.getLayoutPosition() + 1) + "");
        tvState.setText(state);
        if (item.isCheck()) {
            tvNum.setTextColor(mContext.getResources().getColor(R.color.white));
            tvState.setTextColor(mContext.getResources().getColor(R.color.white));
            rllWrapper.getDelegate().setBackgroundColor(bgColor);
        } else {
            tvState.setTextColor(bgColor);
            tvNum.setTextColor(bgColor);
            rllWrapper.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        if (item.getState() == 3) {
            rllWrapper.getDelegate().setBackgroundColor(bgColor);
            tvNum.setTextColor(mContext.getResources().getColor(R.color.black_333333));
            tvState.setTextColor(mContext.getResources().getColor(R.color.black_333333));
        }
        if (item.getState() == 4) {
            tvNum.setText("+");
            rllWrapper.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            tvNum.setTextColor(mContext.getResources().getColor(R.color.gray_999999));
            tvState.setTextColor(mContext.getResources().getColor(R.color.gray_999999));
        }
    }
}

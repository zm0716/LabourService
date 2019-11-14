package com.agilefinger.labourservice.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.SearchBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<SearchBean,BaseViewHolder> {
    public SearchAdapter(int layoutResId, @Nullable List<SearchBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, SearchBean item) {
        helper.setText(R.id.item_rectification_tv_title,item.getProjectName());
        helper.setText(R.id.item_rectification_tv_task,item.getMissionID());
        helper.setText(R.id.item_rectification_tv_zg_man,item.getDoUser());
        helper.setText(R.id.item_rectification_tv_deadline,item.getCreateTime()+"截止");
        helper.setText(R.id.item_rectification_tv_create,item.getEndTime());
        RoundTextView item_rectification_rtv_state = helper.getView(R.id.item_rectification_xjian_state);
        item_rectification_rtv_state.setText(item.getStatus());
    }
}

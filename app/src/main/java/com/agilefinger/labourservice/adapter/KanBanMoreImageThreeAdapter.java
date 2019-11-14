package com.agilefinger.labourservice.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DateImage2Bean;
import com.agilefinger.labourservice.bean.DateImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class KanBanMoreImageThreeAdapter extends BaseQuickAdapter<DateImage2Bean, BaseViewHolder> {
    public KanBanMoreImageThreeAdapter() {
        super(R.layout.item_more_two_img, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateImage2Bean item) {
        RecyclerView view = helper.getView(R.id.m_more_two_recy);
        view.setLayoutManager(new GridLayoutManager(mContext, 3));
        KanBanMoreImageFourAdapter kanBanMoreImageAdapter = new KanBanMoreImageFourAdapter();
        view.setAdapter(kanBanMoreImageAdapter);
        kanBanMoreImageAdapter.setNewData(item.getmList());

    }


}
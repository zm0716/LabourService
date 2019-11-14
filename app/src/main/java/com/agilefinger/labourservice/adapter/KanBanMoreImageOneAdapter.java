package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.DateImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.gavin.com.library.listener.OnGroupClickListener;

public class KanBanMoreImageOneAdapter extends BaseQuickAdapter<DateImageBean, BaseViewHolder> {

    public KanBanMoreImageOneAdapter() {
        super(R.layout.item_more_two_img, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, final DateImageBean item) {
        RecyclerView view = helper.getView(R.id.m_more_two_recy);
        view.setLayoutManager(new GridLayoutManager(mContext, 3));
        KanBanMoreImageAdapter kanBanMoreImageAdapter = new KanBanMoreImageAdapter();
        view.setAdapter(kanBanMoreImageAdapter);
        kanBanMoreImageAdapter.setNewData(item.getmList());

    }
}
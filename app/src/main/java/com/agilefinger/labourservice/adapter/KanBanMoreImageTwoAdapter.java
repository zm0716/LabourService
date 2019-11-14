package com.agilefinger.labourservice.adapter;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.BigImage2Activity;
import com.agilefinger.labourservice.bean.DateImageBean;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.StickyItemDecoration;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class KanBanMoreImageTwoAdapter extends BaseQuickAdapter<DateImageBean, BaseViewHolder> {
    private String compyId;


    public void setCompyId(String compyId) {
        this.compyId = compyId;
    }

    public KanBanMoreImageTwoAdapter() {
        super(R.layout.item_more_two_img, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateImageBean item) {
        RecyclerView view = helper.getView(R.id.m_more_two_recy);
        view.setLayoutManager(new GridLayoutManager(mContext, 3));
        KanBanMoreImageAdapter kanBanMoreImageAdapter = new KanBanMoreImageAdapter();
        view.setAdapter(kanBanMoreImageAdapter);
        kanBanMoreImageAdapter.setNewData(item.getmList());


        kanBanMoreImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MoreImgBean.DataBean.ListBean item = (MoreImgBean.DataBean.ListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Bean", (Serializable) item);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, compyId);
//                bundle.putString("Image", item.getImg_url());
                IntentUtils.startActivity(mContext, BigImage2Activity.class, bundle);
            }
        });

    }


}
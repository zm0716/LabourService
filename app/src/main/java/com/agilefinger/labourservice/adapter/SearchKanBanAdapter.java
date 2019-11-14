package com.agilefinger.labourservice.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.agilefinger.labourservice.bean.KanBanSarchBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class SearchKanBanAdapter extends BaseQuickAdapter<KanBanSarchBean, BaseViewHolder> {
    public SearchKanBanAdapter(int layoutResId, @Nullable List<KanBanSarchBean> data) {
        super(layoutResId, data);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, KanBanSarchBean item) {
        helper.setText(R.id.item_rectification_tv_title, item.getProject_title());
        helper.setText(R.id.item_rectification_rtv_state, item.getCustom_state_name());//状态

        helper.setText(R.id.item_rectification_tv_task, item.getCompany_name());//公司
        helper.setText(R.id.item_rectification_tv_zg_man, "项目经理：" + item.getManager_name());//经理

//
        List<String> en_name = item.getProject_en_name();

        RecyclerView view = (RecyclerView) helper.getView(R.id.m_type);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(mContext
                , FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        view.setLayoutManager(manager);

        KanBanListTypeAdapter kanBanListTypeAdapter = new KanBanListTypeAdapter();
        view.setAdapter(kanBanListTypeAdapter);
        kanBanListTypeAdapter.setNewData(en_name);

//        helper.setText(R.id.item_rectification_tv_deadline, item.getProject_en_name());//种类
    }
}

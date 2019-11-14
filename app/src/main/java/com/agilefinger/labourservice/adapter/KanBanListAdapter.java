package com.agilefinger.labourservice.adapter;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class KanBanListAdapter extends BaseQuickAdapter<KanBanListBean.DataBean.ProjectDataBean, BaseViewHolder> {

    public KanBanListAdapter() {
        super(R.layout.item_zi_kanban, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KanBanListBean.DataBean.ProjectDataBean item) {
        helper.setText(R.id.item_rectification_tv_title, item.getProject_title());
        helper.setText(R.id.item_rectification_rtv_state, item.getCustom_state_name());//状态

        helper.setText(R.id.item_rectification_tv_task, item.getCompany_name());//公司
        helper.setText(R.id.item_rectification_tv_zg_man, "项目经理：" + item.getManager_name());//经理

        List<String> en_name = item.getProject_en_name();
//        for (int i = 0; i <10 ; i++) {
//            en_name.add("保温");
//        }

        RecyclerView view = (RecyclerView) helper.getView(R.id.m_type);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(mContext
                , FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        view.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                       RecyclerView.State state) {
//                //设置距离为20dp
//                outRect.bottom = 10;
//                outRect.right = 7;
//            }
//        });

        view.setLayoutManager(manager);

        KanBanListTypeAdapter kanBanListTypeAdapter = new KanBanListTypeAdapter();
        view.setAdapter(kanBanListTypeAdapter);
        kanBanListTypeAdapter.setNewData(en_name);

//        String enName = "";
//        for (int i = 0; i < en_name.size(); i++) {
//            if (enName.equals("")) {
//                enName = en_name.get(i);
//            } else {
//                enName = enName + "   " + en_name.get(i);
//            }
//
//        }

//        helper.setText(R.id.item_rectification_tv_deadline, enName);//种类


    }
}

package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.BigImage2Activity;
import com.agilefinger.labourservice.bean.BuildMoreImageBean;
import com.agilefinger.labourservice.bean.LouHaoBean;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<LouHaoBean> mlist;
    private Context context;
    private String comptyId;

    public ExpandableListViewAdapter(List<LouHaoBean> mlist, Context context, String comptyId) {
        this.mlist = mlist;
        this.context = context;
        this.comptyId = comptyId;
    }

    /*一级列表个数*/
    @Override
    public int getGroupCount() {
        return mlist.size();
    }

    /*每个二级列表的个数*/
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /*一级列表中单个item*/
    @Override
    public Object getGroup(int groupPosition) {
        return mlist.get(groupPosition);
    }

    /*二级列表中单个item*/
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mlist.get(groupPosition).getmList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /*每个item的id是否固定，一般为true*/
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /*#TODO 填充一级列表
     * isExpanded 是否已经展开
     * */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chapter, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_item_chapter_practise);
            holder.iv_group = (ImageView) convertView.findViewById(R.id.iv_item_chapter_arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mlist.get(groupPosition).getTitle());
        //控制是否展开图标
        if (isExpanded) {
            holder.iv_group.setImageResource(R.mipmap.drop_down_selected_icon);
        } else {
            holder.iv_group.setImageResource(R.mipmap.drop_down_unselected_icon);
        }
        return convertView;
    }

    /*#TODO 填充二级列表*/
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_section, null);
            holder = new ViewHolder();
            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.m_img_recy);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setNestedScrollingEnabled(false);
            KanBanMoreImageFourAdapter kanBanMoreImageAdapter = new KanBanMoreImageFourAdapter();

            holder.recyclerView.setAdapter(kanBanMoreImageAdapter);
            if (mlist.get(groupPosition).getmList() != null && mlist.get(groupPosition).getmList().size() > 0) {
                kanBanMoreImageAdapter.setNewData(mlist.get(groupPosition).getmList());
            } else {
                holder.recyclerView.setVisibility(View.GONE);
            }


            kanBanMoreImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    BuildMoreImageBean.DataBean.ListBean item = (BuildMoreImageBean.DataBean.ListBean) adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("class", 1);
                    bundle.putSerializable("Bean", (Serializable) item);
                    bundle.putString(Constants.EXTRA_DATA_COMPANY, comptyId);
//                bundle.putString("Image", item.getImg_url());
                    IntentUtils.startActivity(context, BigImage2Activity.class, bundle);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
//
//    @Override
//    public int getHeaderState(int groupPosition, int childPosition) {
//        final int childCount = getChildrenCount(groupPosition);
//        if (childPosition == childCount - 1) {
//            return PINNED_HEADER_PUSHED_UP;
//        } else if (childPosition == -1) {
//            return PINNED_HEADER_GONE;
//        } else {
//            return PINNED_HEADER_VISIBLE;
//        }
//    }


//    @Override
//    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
//        String groupData = mlist.get(groupPosition).getTitle();//悬浮条的显示信息
//        ((TextView) header.findViewById(R.id.tv_msg_listview_label)).setText(groupData);
//    }


    class ViewHolder {
        TextView title;
        ImageView iv_group;
        RecyclerView recyclerView;
    }

    /*二级列表中每个能否被选中，如果有点击事件一定要设为true*/
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
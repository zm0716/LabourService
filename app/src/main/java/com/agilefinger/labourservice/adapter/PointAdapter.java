package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.PointDailsBean;

import java.util.List;


/**
 * Created by Shinelon on 2018/9/7.
 */

public class PointAdapter extends RecyclerView.Adapter<PointHolder>{
    private View mHeaderView;
    Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mLayoutInflater;
    private final static int ITEM_HEADER=0;
    private final static int ITEM_CONTENT=1;
    private final static int ITEM_FOOT=2;
    List<PointDailsBean.DataBean.PointBean> list;
    private int mHeader=1;
    String type;
    private int mFoot=1;
    public PointAdapter(Context context, List<PointDailsBean.DataBean.PointBean> list, String type) {
        this.mContext=context;
        this.list=list;
        this.type=type;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public PointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_FOOT){
            return new PointHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_inspection_item, null);
        return new PointHolder(view);
    }

    @Override
    public void onBindViewHolder(final PointHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_FOOT) {
            return;
        }else {
            int bgColor = mContext.getResources().getColor(R.color.blue_0079e4);

            if (type.equals("4")||type.equals("5")){
                String rp_is_pass = list.get(position).getRp_is_pass();
                if (rp_is_pass.equals("y")) { //合格
                    bgColor = mContext.getResources().getColor(R.color.blue_0079e4);
                    holder.item_inspection_item_tv_state.setText("合格");
                } else {
                    bgColor = mContext.getResources().getColor(R.color.red_ff0000);
                    holder.item_inspection_item_tv_state.setText("不合格");
                }
            }else {
                bgColor = mContext.getResources().getColor(R.color.blue_0079e4);
                holder.item_inspection_item_tv_state.setText(list.get(position).getRp_result());
            }

            holder.rllWrapper.getDelegate().setBackgroundColor(bgColor);
            holder.rllWrapper.getDelegate().setStrokeWidth(1);
            holder.item_inspection_item_tv_num.setText((position + 1) + "");

            holder.rllWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,position,holder,list);
                }
            });
        }

    }

    //View.OnKeyListener
    @Override
    public int getItemViewType(int position) {
        if (mFoot!=0&&position>=list.size()){
            return ITEM_FOOT;
        }
        return ITEM_CONTENT;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, PointHolder holder, List<PointDailsBean.DataBean.PointBean> list);


    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}

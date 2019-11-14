package com.agilefinger.labourservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.AddTwoBean;
import com.agilefinger.labourservice.bean.ItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MoKuaiAdapter extends RecyclerView.Adapter<MoKuaiAdapter.MyHolder> {

    private Context context;
    private List<AddTwoBean.DefaultTemplateBean> list;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick(View view, int position, MyHolder holder, List<AddTwoBean.DefaultTemplateBean> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
    public MoKuaiAdapter(Context context, List<AddTwoBean.DefaultTemplateBean> defaultTemplate) {
        this.context=context;
        this.list=defaultTemplate;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mk_list, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.text_mb.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v,position,holder,list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView text_mb;
        public MyHolder(final View itemView) {
            super(itemView);
            text_mb = itemView.findViewById(R.id.text_mb);

        }
    }



}

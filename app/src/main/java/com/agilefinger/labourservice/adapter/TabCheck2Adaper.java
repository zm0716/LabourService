package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.ItemBean;

import java.util.List;


/**
 * Created by Shinelon on 2018/11/29.
 */

public class TabCheck2Adaper extends RecyclerView.Adapter<TabCheck2Adaper.Hm2Holder> {
    private OnItemClickListener mOnItemClickListener;
    Context context;
    List<ItemBean> list;
    public TabCheck2Adaper(Context context, List<ItemBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public Hm2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_inspection_item, parent,false);
        return new Hm2Holder(view);
    }

    @Override
    public void onBindViewHolder(final Hm2Holder holder, final int position) {
       // Log.d("重新刷新",list.get(position).getNumber_point()+"::");
        holder.name.setText(list.get(position).getMci_name());
        holder.cengji.setText(""+list.get(position).getPath()+"");

        if ((""+list.get(position).getMci_min_point()+"").equals("0")){
            holder.num_textview.setText( ""+list.get(position).getP_count()+"");
        }else {
            holder.num_textview.setText( list.get(position).getP_count()+"/"+list.get(position).getMci_min_point()+"");
        }

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
    public static class Hm2Holder extends RecyclerView.ViewHolder {

        public TextView cengji;
        public TextView name;
        public TextView num_textview;
        public Hm2Holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_search_inspection_item_tv_title);
            num_textview = itemView.findViewById(R.id.item_search_inspection_item_tv_num);
            cengji = itemView.findViewById(R.id.item_search_inspection_item_tv_subtitle);
        }
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, Hm2Holder holder, List<ItemBean> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}

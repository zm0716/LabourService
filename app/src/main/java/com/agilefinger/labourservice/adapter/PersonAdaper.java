package com.agilefinger.labourservice.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.JianCeRenBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Created by Shinelon on 2018/11/29.
 */

public class PersonAdaper extends RecyclerView.Adapter<PersonAdaper.HmHolder> {
    private OnItemClickListener mOnItemClickListener;
    Activity context;
    List<JianCeRenBean.DataBean> list;
    public   Map<Integer,Boolean> gongMap=new HashMap<>();// 存放已被选中的CheckBox

    public PersonAdaper(Activity context, List<JianCeRenBean.DataBean> list) {
        this.context=context;
        this.list=list;

    }


    @Override
    public HmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xz_yg,parent,false);
        return new HmHolder(view);
    }

    @Override
    public void onBindViewHolder(final HmHolder holder, final int position) {
        Log.d("渲染数据",list.get(position).getName()+"::");
        holder.name.setText(list.get(position).getName());
        holder.xuanze.setChecked(list.get(position).isCheck());


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
    public static class HmHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RadioButton xuanze;


        public HmHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_naame);
            xuanze = itemView.findViewById(R.id.image_xuan);

        }
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, HmHolder holder, List<JianCeRenBean.DataBean> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}

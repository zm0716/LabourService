package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.TeamGroupPeopleBean;

import java.util.List;

public class TeamGroupAdaptere extends RecyclerView.Adapter<TeamGroupAdaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<TeamGroupPeopleBean.DataBean.MemberBean> dataList;
    private Context context;

    public TeamGroupAdaptere(Context context, List<TeamGroupPeopleBean.DataBean.MemberBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.item_tv_name.setText(dataList.get(position).getUser_name());
        holder.join_tv.setText("" + dataList.get(position).getJoin_time() + "");

        //此功能暂时注释
//        holder.m_delate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnItemClickListener.onDeleteClick(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView item_tv_name;
        public TextView join_tv;
        public ImageView m_delate;


        public MyHolder(View itemView) {
            super(itemView);
            item_tv_name = itemView.findViewById(R.id.item_tv_name);
            join_tv = itemView.findViewById(R.id.join_tv);
            m_delate = itemView.findViewById(R.id.m_delate);

        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

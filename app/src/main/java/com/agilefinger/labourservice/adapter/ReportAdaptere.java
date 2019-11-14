package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.LouDongBean;

import java.util.List;

public class ReportAdaptere extends RecyclerView.Adapter<ReportAdaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<LouDongBean.DataBean> dataaList;
    private Context context;
    public ReportAdaptere(Context context, List<LouDongBean.DataBean> dataaList) {
        this.context=context;
        this.dataaList=dataaList;
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v,position,holder,dataaList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataaList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
       public TextView tv_score;
        public TextView biaozhuan;
        public TextView time;
        public TextView username;
        public MyHolder(View itemView) {
            super(itemView);
            tv_score = itemView.findViewById(R.id.activity_inspection_detail_tv_score);
            biaozhuan = itemView.findViewById(R.id.biaozhuan);
            time= itemView.findViewById(R.id.time);

            username = itemView.findViewById(R.id.username);
        }
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, MyHolder holder, List<LouDongBean.DataBean> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}

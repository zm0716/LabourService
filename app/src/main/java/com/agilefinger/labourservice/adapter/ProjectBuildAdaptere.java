package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.LouDongBean;

import java.util.List;

public class ProjectBuildAdaptere extends RecyclerView.Adapter<ProjectBuildAdaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<LouDongBean.DataBean> dataaList;
    private Context context;

    public ProjectBuildAdaptere(Context context, List<LouDongBean.DataBean> dataaList) {
        this.context = context;
        this.dataaList = dataaList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_project_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.buildname.setText(dataaList.get(position).getBuilding_num());
        //  holder.del.setVisibility(View.VISIBLE);
        holder.text_louhao.setVisibility(View.VISIBLE);
        if (dataaList.get(position).getB_floor() != 0) {
            holder.text_louhao.setText("B" + dataaList.get(position).getB_floor() + "-F" + dataaList.get(position).getF_floor());
        } else {
            holder.text_louhao.setText("F1" + "-F" + dataaList.get(position).getF_floor());

        }
        holder.buildbuwei.setText(dataaList.get(position).getInside_name());
      /*  if (dataaList.get(position)..equals("user")){
            holder.buildtype.setText("自选检测位置");
            holder.del.setVisibility(View.VISIBLE);
        }else {
            holder.del.setVisibility(View.GONE);
            holder.buildtype.setText("指定检测位置");
        }*/


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v, position, holder, dataaList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataaList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView text_louhao;
        public ImageView del;
        public ImageView bj;
        public TextView buildbuwei;
        public TextView buildname;

        public MyHolder(View itemView) {
            super(itemView);
            text_louhao = itemView.findViewById(R.id.m_building_lou);
            buildbuwei = itemView.findViewById(R.id.m_building_buwei);
            buildname = itemView.findViewById(R.id.m_building_name);

            del = itemView.findViewById(R.id.m_delete);
            bj = itemView.findViewById(R.id.m_bianji);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, MyHolder holder, List<LouDongBean.DataBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.AllBuildBean;

import java.util.List;

public class Team2Adaptere extends RecyclerView.Adapter<Team2Adaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<AllBuildBean.DataBean.BuildingBean> dataaList;
    private Context context;

    public Team2Adaptere(Context context, List<AllBuildBean.DataBean.BuildingBean> dataaList) {
        this.context = context;
        this.dataaList = dataaList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banzu, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.text_louhao.setText(dataaList.get(position).getBuilding_num());
        int is_select = dataaList.get(position).getIs_select();
        if (is_select == 0) {
            holder.text_louhao.setTextColor(Color.parseColor("#333333"));
            holder.dianji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v, position, holder, dataaList);
                }
            });
        } else {
            holder.text_louhao.setTextColor(Color.parseColor("#CCCCCC"));
        }
    }

    @Override
    public int getItemCount() {
        return dataaList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView text_louhao;
        public ImageView image_checked;
        public LinearLayout dianji;

        public MyHolder(View itemView) {
            super(itemView);
            text_louhao = itemView.findViewById(R.id.text_louhao);
            image_checked = itemView.findViewById(R.id.image_checked);
            dianji = itemView.findViewById(R.id.dianji);

        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, MyHolder holder, List<AllBuildBean.DataBean.BuildingBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.AllBuildBean;

import java.util.List;

public class Team3Adaptere extends RecyclerView.Adapter<Team3Adaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<AllBuildBean.DataBean.HasBean> dataaList;
    private Context context;

    public Team3Adaptere(Context context, List<AllBuildBean.DataBean.HasBean> dataaList) {
        this.context = context;
        this.dataaList = dataaList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bulid, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.buildname.setText(dataaList.get(position).getName());
        if (dataaList.get(position).getCpi_type().equals("user")) {
            holder.buildtype.setText("自选检测位置");
            holder.del.setVisibility(View.VISIBLE);
        } else {
            holder.del.setVisibility(View.GONE);
            holder.buildtype.setText("指定检测位置");
        }
//        String group = "";
//        for (int i = 0; i < dataaList.get(position).getGroupList().size(); i++) {
//            group += dataaList.get(position).getGroupList().get(i) + "</n>";
//        }
//        holder.text_louhao.setText(Html.fromHtml(group));

        if (null != dataaList.get(position).getFloorList()) {
            String group = "";
            if (dataaList.get(position).getFloorList().size() != 0) {
                for (int i = 0; i < dataaList.get(position).getFloorList().size(); i++) {
                    group += dataaList.get(position).getFloorList().get(i).toString() + "<div/>";
                }
                Log.e("Group", group);
                holder.text_floor.setText(Html.fromHtml(group));
            } else {
                holder.text_floor.setText("不限楼层-不限立面");
            }
        }
        if (null != dataaList.get(position).getGroupList()) {
            String name = "";
            if (dataaList.get(position).getGroupList().size() != 0) {
                for (int i = 0; i < dataaList.get(position).getGroupList().size(); i++) {
                    name += dataaList.get(position).getGroupList().get(i).toString() + "<div/>" + "<div/>";
                }
                Log.e("Group1", name);
                holder.text_louhao.setText(Html.fromHtml(name));
            } else {
                holder.text_louhao.setBottom(10);
                holder.text_louhao.setText("无指定班组");
            }
        }
//        String name = "";
//        for (int i = 0; i < dataaList.get(position).getFloorList().size(); i++) {
//            name += dataaList.get(position).getFloorList().get(i) + "</n>";
//        }
//        holder.text_floor.setText(Html.fromHtml(name));

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
        public TextView text_floor;
        public ImageView del;
        public TextView buildtype;
        public TextView buildname;

        public MyHolder(View itemView) {
            super(itemView);
            text_louhao = itemView.findViewById(R.id.text_louhao);
            text_floor = itemView.findViewById(R.id.text_floor);
            buildtype = itemView.findViewById(R.id.buildtype);
            buildname = itemView.findViewById(R.id.buildname);

            del = itemView.findViewById(R.id.del);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, MyHolder holder, List<AllBuildBean.DataBean.HasBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

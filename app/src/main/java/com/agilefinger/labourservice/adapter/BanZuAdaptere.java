package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BanZuBean;
import com.agilefinger.labourservice.bean.TeamGroupBean;

import java.util.List;

public class BanZuAdaptere extends RecyclerView.Adapter<BanZuAdaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<BanZuBean.DataBean> dataList;
    private Context context;

    public BanZuAdaptere(Context context, List<BanZuBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ban_zu, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.username.setText(dataList.get(position).getGroup_name());
        holder.teamname.setText(dataList.get(position).getGroup_mobile() + "");
        holder.buildname.setText(dataList.get(position).getTeam_name());
        String build = "";

//        List<BanZuBean.DataBean.BuildingBean> building = dataList.get(position).getBuilding();
//        if (!building.isEmpty()) {
//            for (int i = 0; i < building.size(); i++) {
//                if (build.equals("")) {
//                    build = building.get(i).getBuilding_name() + "-" + building.get(i).getEn_name();
//                } else {
//                    build = build + "   " + (building.get(i).getBuilding_name() + "-" + building.get(i).getEn_name());
//                }
//            }
//        }
        holder.build_louhao.setText(dataList.get(position).getBuilding_str());



       /* holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v,position,holder,dataList);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public TextView teamname;
        public TextView buildname;
        public TextView build_louhao;

        public MyHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            teamname = itemView.findViewById(R.id.teamname);
            buildname = itemView.findViewById(R.id.buildname);
            build_louhao = itemView.findViewById(R.id.build_louhao);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, MyHolder holder, List<TeamGroupBean.DataBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

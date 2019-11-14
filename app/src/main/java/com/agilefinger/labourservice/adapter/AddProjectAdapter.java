package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProjectAdapter extends RecyclerView.Adapter<AddProjectAdapter.ViewHolder> {

    private ArrayList<String> mlist;
    private Context context;

    public AddProjectAdapter(ArrayList<String> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    private OnItemClickListern onItemClick;

    public interface OnItemClickListern {
        void onClickLisern(int postion);

        void onClickBianjiListern(int postion);
    }

    public void setOnItemClick(OnItemClickListern onItemClick) {
        this.onItemClick = onItemClick;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.add_project_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            holder.mRela.setVisibility(View.GONE);
        }


        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClickLisern(position);
            }
        });
        holder.mBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClickBianjiListern(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.m_rela)
        RelativeLayout mRela;
//        @BindView(R.id.m_building_name)
        TextView mBuildingName;
//        @BindView(R.id.m_bianji)
        ImageView mBianji;
//        @BindView(R.id.m_delete)
        ImageView mDelete;
//        @BindView(R.id.m_building_lou)
        TextView mBuildingLou;
//        @BindView(R.id.m_building_buwei)
        TextView mBuildingBuwei;

        public ViewHolder(View itemView) {
            super(itemView);
            mRela = itemView.findViewById(R.id.m_rela);
            mBuildingName = itemView.findViewById(R.id.m_building_name);
            mBianji = itemView.findViewById(R.id.m_bianji);
            mDelete = itemView.findViewById(R.id.m_delete);
            mBuildingLou = itemView.findViewById(R.id.m_building_lou);
            mBuildingBuwei = itemView.findViewById(R.id.m_building_buwei);
        }
    }


}

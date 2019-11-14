package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.DataCollectionResultActivity;
import com.agilefinger.labourservice.bean.ItemBean;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Shinelon on 2018/11/29.
 */

public class TabCheck3Adaper extends RecyclerView.Adapter<TabCheck3Adaper.Hm2Holder> {
    private OnItemClickListener mOnItemClickListener;
    Context context;
    List<ItemBean> list;
    public static List<ItemBean> checkList = new ArrayList<>();
    public static Map<String, Boolean> map = new HashMap<>();// 存放已被选中的CheckBox
    RoundTextView next;
    String taskId;
    String projectid;

    public TabCheck3Adaper(Context context, List<ItemBean> list, RoundTextView next, String taskId, String projectid) {
        this.context = context;
        this.list = list;
        this.next = next;
        this.taskId = taskId;
        this.projectid = projectid;

    }

    @Override
    public Hm2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_collection_multi, parent, false);
        return new Hm2Holder(view);
    }

    @Override
    public void onBindViewHolder(final Hm2Holder holder, final int position) {
        // Log.d("重新刷新",list.get(position).getNumber_point()+"::");
        holder.name.setText(list.get(position).getMci_name());
        holder.cengji.setText("" + list.get(position).getPath() + "");
        holder.num_textview.setVisibility(View.VISIBLE);
        holder.zhanshi.setVisibility(View.VISIBLE);
        if (("" + list.get(position).getMci_min_point() + "").equals("0")) {
            holder.num_textview.setText("" + list.get(position).getP_count() + "");
        } else {
            holder.num_textview.setText(list.get(position).getP_count() + "/" + list.get(position).getMci_min_point() + "");
        }
//#349fff
        if (map != null && map.containsKey(list.get(position).getMci_id())) {
            list.get(position).setIsxuan(true);
            holder.imageView.setImageResource(R.mipmap.ic_checked);
            holder.name.setTextColor(Color.parseColor("#349fff"));
        } else {
            list.get(position).setIsxuan(false);
            holder.imageView.setImageResource(R.mipmap.ic_unchecked);
            holder.name.setTextColor(Color.parseColor("#333333"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (list.get(position).isIsxuan()) {
                            checkList.remove(list.get(position));
                            list.get(position).setIsxuan(false);
                            map.remove(list.get(position).getMci_id());
                            holder.imageView.setImageResource(R.mipmap.ic_unchecked);

                        } else {
                            map.put(list.get(position).getMci_id(), true);
                            list.get(position).setIsxuan(true);
                            holder.imageView.setImageResource(R.mipmap.ic_unchecked);
                            checkList.add(list.get(position));
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkList.size() == 0) {
                            ToastUtil.showShort(context, "请选择检查项！！！");
                        } else {
                            String itemid = "";
                            for (int i = 0; i < checkList.size(); i++) {
                                if (checkList.get(i).isIsxuan()) {
                                    if (itemid.equals("")) {
                                        itemid = checkList.get(i).getMci_id();
                                    } else {
                                        itemid = itemid + "," + checkList.get(i).getMci_id();
                                    }
                                }
                            }
                            Log.d("选中后的数据", GsonUtils.toJson(checkList));
                            Bundle bundle = new Bundle();

                            Intent intent = new Intent(context, DataCollectionResultActivity.class);
                            intent.putExtra("projectid", projectid + "");
                            intent.putExtra("taskid", taskId + "");
                            intent.putExtra("itemID", itemid + "");
                            intent.putExtra("type", "0");
//                            IntentUtils.startActivity(context, DataCollectionResultActivity.class, bundle);
                            context.startActivity(intent);
                        }
                    }
                });


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
        public ImageView imageView;
        public ImageView zhanshi;

        public Hm2Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_data_collection_multi_iv_check);
            zhanshi = itemView.findViewById(R.id.zhanshi);
            name = itemView.findViewById(R.id.item_data_collection_multi_tv_jcx);
            num_textview = itemView.findViewById(R.id.item_search_inspection_item_tv_num);
            cengji = itemView.findViewById(R.id.item_data_collection_multi_tv_subtitle);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, Hm2Holder holder, List<ItemBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

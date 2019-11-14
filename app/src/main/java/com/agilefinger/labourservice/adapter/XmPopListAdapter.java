package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CheckBean;
import com.agilefinger.labourservice.bean.XmuPopBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XmPopListAdapter extends RecyclerView.Adapter<XmPopListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<XmuPopBean> list;

    private LinkedHashMap<Integer, CheckBean> map;

    public XmPopListAdapter(Context context, ArrayList<XmuPopBean> list) {
        this.context = context;
        this.list = list;
        initData();
    }

    private void initData() {
        map = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, new CheckBean(false, false));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_xm_popo_xinxi, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.textLouhao.setText(list.get(position).getName());

        final CheckBean checkBean = map.get(position);
        if (checkBean.getOne()) {
//            holder.buttonQx.setBackgroundColor(Color.parseColor("#2093ff"));
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
            holder.buttonQx.setBackground(drawable);
            holder.buttonQx.setTextColor(Color.parseColor("#ffffff"));
        } else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop_nochack);
            holder.buttonQx.setBackground(drawable);
            holder.buttonQx.setTextColor(Color.parseColor("#999999"));
        }
        if (checkBean.getTwo()) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
            holder.buttonWc.setBackground(drawable);
//            holder.buttonWc.setBackgroundColor(Color.parseColor("#2093ff"));
            holder.buttonWc.setTextColor(Color.parseColor("#ffffff"));
        } else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop_nochack);
            holder.buttonWc.setBackground(drawable);
//            holder.buttonWc.setBackgroundColor(Color.parseColor("#2093ff"));
            holder.buttonWc.setTextColor(Color.parseColor("#999999"));
        }


        holder.buttonQx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBean.getTwo()) {
                    Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
                    holder.buttonQx.setBackground(drawable);
                    holder.buttonQx.setTextColor(Color.parseColor("#ffffff"));
                    checkBean.setOne(true);


                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.shape_pop_nochack);
                    holder.buttonWc.setBackground(drawable2);
//            holder.buttonWc.setBackgroundColor(Color.parseColor("#2093ff"));
                    holder.buttonWc.setTextColor(Color.parseColor("#999999"));

                    checkBean.setTwo(false);

                }else {
                    Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
                    holder.buttonQx.setBackground(drawable);
                    holder.buttonQx.setTextColor(Color.parseColor("#ffffff"));
                    checkBean.setOne(true);
                    checkBean.setTwo(false);
                }
            }
        });

        holder.buttonWc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBean.getOne()) {
                    Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
                    holder.buttonWc.setBackground(drawable);
                    holder.buttonWc.setTextColor(Color.parseColor("#ffffff"));
                    checkBean.setTwo(true);


                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.shape_pop_nochack);
                    holder.buttonQx.setBackground(drawable2);
//            holder.buttonWc.setBackgroundColor(Color.parseColor("#2093ff"));
                    holder.buttonQx.setTextColor(Color.parseColor("#999999"));

                    checkBean.setOne(false);

                }else {
                    Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
                    holder.buttonWc.setBackground(drawable);
                    holder.buttonWc.setTextColor(Color.parseColor("#ffffff"));
                    checkBean.setOne(false);
                    checkBean.setTwo(true);
                }

//
//                Drawable drawable = context.getResources().getDrawable(R.drawable.shape_pop);
//                holder.buttonWc.setBackground(drawable);
//                holder.buttonWc.setTextColor(Color.parseColor("#ffffff"));
            }
        });

//        holder.buttonQx.setOnCheckedChangeListener(null);
////        holder.buttonWc.setOnCheckedChangeListener(null);
////        holder.buttonQx.setChecked(checkBean.getOne());
////        holder.buttonWc.setChecked(checkBean.getTwo());
////
////        holder.radioPop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                CheckBean checkBean = map.get(position);
////                switch (checkedId) {
////                    case R.id.button_qx:
////                        map.get(position).setOne(true);
//////                        holder.buttonQx.setChecked(true);
////
////                        holder.buttonQx.setChecked(checkBean.getOne());
////                        break;
////                    case R.id.button_wc:
////                        map.get(position).setTwo(true);
//////                        holder.buttonWc.setChecked(true);
////                        holder.buttonWc.setChecked(checkBean.getTwo());
////                        break;
////                }
////            }
////        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_louhao)
        TextView textLouhao;
        @BindView(R.id.button_qx)
        TextView buttonQx;
        @BindView(R.id.button_wc)
        TextView buttonWc;
        //        @BindView(R.id.radio_pop)
//        RadioGroup radioPop;
        @BindView(R.id.relayout_one)
        RelativeLayout relayoutOne;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

//    public XmPopListAdapter(int item_xm_popo_xinxi, ArrayList<XmuPopBean> list) {
//        super(item_xm_popo_xinxi, list);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, XmuPopBean item) {
//        helper.setText(R.id.text_louhao,item.getName());
//    }


}

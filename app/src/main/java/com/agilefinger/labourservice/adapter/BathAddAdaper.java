package com.agilefinger.labourservice.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.DataCollectionResultActivity;
import com.agilefinger.labourservice.bean.PiLiangBean;
import com.agilefinger.labourservice.utils.EditTextUtils;
import com.flyco.roundview.RoundTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Shinelon on 2018/11/29.
 */

public class BathAddAdaper extends RecyclerView.Adapter<BathAddAdaper.Hm2Holder> {
    private OnItemClickListener mOnItemClickListener;
    DataCollectionResultActivity context;
    List<PiLiangBean.DataBean.InfoBean> list;
    RoundTextView checktv;
    RoundTextView endtv;
    Map<Integer,String>  edMap=new HashMap<>();


    public BathAddAdaper(DataCollectionResultActivity context,
                         List<PiLiangBean.DataBean.InfoBean> list, RoundTextView checktv, RoundTextView endtv) {
        this.context=context;
        this.list=list;
        this.checktv=checktv;
        this.endtv=endtv;
    }

    @Override
    public Hm2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_collection_result, parent,false);
        return new Hm2Holder(view);
    }


    @Override
    public void onBindViewHolder(final Hm2Holder holder, final int position) {
        holder.name.setText(list.get(position).getMci_name());
        if ((""+list.get(position).getMci_min_point()+"").equals("0")){
            holder.number.setText("( "+ list.get(position).getP_count()+" )");
          //  holder.num_textview.setText( ""+list.get(position).getP_count()+"");
        }else {
//            holder.name.setText(list.get(position).getMci_name() +"( "+ list.get(position).getP_count()
//                    +"/"+list.get(position).getMci_min_point()+" )");

            holder.number.setText("( "+ list.get(position).getP_count()
                    +"/"+list.get(position).getMci_min_point()+" )");

          //  holder.num_textview.setText( list.get(position).getP_count()+"/"+list.get(position).getMci_min_point()+"");
        }

        if (list.get(position).getMci_type().equals("4")||list.get(position).getMci_type().equals("5")){
            holder.shuru.setVisibility(View.GONE);
            holder.danwei.setVisibility(View.GONE);
            holder.hege.setVisibility(View.VISIBLE);
            holder.buhege.setVisibility(View.VISIBLE);

        }else {
            holder.shuru.setVisibility(View.VISIBLE);
            holder.danwei.setVisibility(View.VISIBLE);
            holder.hege.setVisibility(View.GONE);
            holder.buhege.setVisibility(View.GONE);
            holder.danwei.setText(list.get(position).getUnit());
        }
        //绑定
        holder.shuru.addTextChangedListener(new TextSwitcher(holder));
        holder.shuru.setTag(position);
        boolean b = edMap.containsKey(position);
        if (b){
            holder.shuru.setText(edMap.get(position).toString());
        }

        EditTextUtils.editWatcher(holder.shuru, new EditTextUtils.EditTextChanged() {
            @Override
            public void beforeTextChanged() { }
        });

        Log.d("当前点击的值",position+"::"+list.get(position).getCheck()+"::"+list.get(position).isIsxuan());
        if (list.get(position).isIsxuan()){
            if (list.get(position).getCheck().equals("不合格")){
                list.get(position).setCheck("不合格");
                holder.buhege.setBackgroundColor(Color.parseColor("#66a7e1"));
                holder.hege.setBackgroundColor(Color.parseColor("#e5e5e5"));
            }else if (list.get(position).getCheck().equals("合格")){
                list.get(position).setCheck("合格");
                holder.hege.setBackgroundColor(Color.parseColor("#66a7e1"));
                holder.buhege.setBackgroundColor(Color.parseColor("#e5e5e5"));
            }else {
                list.get(position).setCheck("合格");
                holder.hege.setBackgroundColor(Color.parseColor("#66a7e1"));
                holder.buhege.setBackgroundColor(Color.parseColor("#e5e5e5"));
            }
        }else {
            holder.hege.setBackgroundColor(Color.parseColor("#e5e5e5"));
            holder.buhege.setBackgroundColor(Color.parseColor("#e5e5e5"));
            list.get(position).setIsxuan(false);
            list.get(position).setCheck("");

        }

        holder.hege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isIsxuan()){
                    if (list.get(position).getCheck().equals("不合格")){
                        list.get(position).setIsxuan(true);
                        list.get(position).setCheck("合格");
                    }else {
                        list.get(position).setIsxuan(false);
                        list.get(position).setCheck("1");
                    }
                }else {
                    list.get(position).setIsxuan(true);
                    list.get(position).setCheck("合格");
                }
                notifyItemChanged(position);
            }
        });

        holder.buhege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isIsxuan()){
                    if (list.get(position).getCheck().equals("合格")){
                        list.get(position).setIsxuan(true);
                        list.get(position).setCheck("不合格");
                    }else {
                        list.get(position).setIsxuan(false);
                        list.get(position).setCheck("");
                    }

                }else {
                    list.get(position).setIsxuan(true);
                    list.get(position).setCheck("不合格");

                }
                notifyItemChanged(position);
            }
        });

        checktv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checktv.getText().toString().equals("全部选中")){
                    for (int i=0;i<list.size();i++){
                        list.get(i).setIsxuan(true);
                    }
                    checktv.setText("全部清除");
                }else {
                    for (int i=0;i<list.size();i++){
                        list.get(i).setIsxuan(false);
                        list.get(i).setCheck("");
                    }
                    checktv.setText("全部选中");
                }
                notifyDataSetChanged();
            }
        });

        endtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(view,position,holder,list);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class Hm2Holder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView number;
        public EditText shuru;
        public TextView hege;
        public TextView buhege;
        public TextView danwei;
        public RadioGroup rg;
        public Hm2Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_data_collection_result_tv_title);
            number = itemView.findViewById(R.id.item_data_collection_result_tv_title_number);
            shuru = itemView.findViewById(R.id.jieguo);
            hege = itemView.findViewById(R.id.hege);
            buhege = itemView.findViewById(R.id.buhege);
            danwei = itemView.findViewById(R.id.danwei);
            rg = itemView.findViewById(R.id.rg);

        }
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, Hm2Holder holder,List<PiLiangBean.DataBean.InfoBean> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    class TextSwitcher implements TextWatcher {
        private Hm2Holder mHolder;

        public TextSwitcher(Hm2Holder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) mHolder.shuru.getTag();//取tag值
            Log.d("qqqqqqqqqq",s.toString());
            edMap.put(position,s.toString());
            list.get(position).setResult(s.toString());
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }



}

package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.CreateInspectionTaskActivity;
import com.agilefinger.labourservice.bean.AddOneBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreateInspectionTaskAdapter extends RecyclerView.Adapter<CreateInspectionTaskAdapter.MyViewHolder> {
    private List<AddOneBean> data1;
    private Context mContext;
    private int thisPosition = -1;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //再定义一个int类型的返回值方法
    private int getPosition() {
        return thisPosition;
    }

    public void setPosition(int thisPosition) {
        this.thisPosition = thisPosition;
        notifyDataSetChanged();
    }

    public CreateInspectionTaskAdapter(List<AddOneBean> data1, Context mContext) {
        this.data1 = data1;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_create_inspection_task, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item_rectification_tv_title.setText(data1.get(position).getProjectName());
        //holder.item_create_inspection_task_tv_source.setText(data1.get(position).getProjectFrom());
        holder.item_create_inspection_task_tv_no.setText(data1.get(position).getProjectNo());
        holder.item_create_inspection_task_tv_company.setText(data1.get(position).getCompanyName());
        try {
            String time = dateToStamp(data1.get(position).getEndTime());
            String s = stampToDate(Long.parseLong(time));
            holder.item_create_inspection_task_tv_deadline.setText("预计开工日期：" + s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.item_rectification_rtv_state.setText(data1.get(position).getProjectType());

        if (holder.itemView.isClickable()) {
            Log.d("选中id", thisPosition + "::" + position);
            if (thisPosition == position) {

                holder.item_rectification_tv_title.setTextColor(Color.parseColor("#349fff"));
                holder.imageView.setBackgroundResource(R.drawable.ic_checked);
                SharedPreferences login = mContext.getSharedPreferences("login", mContext.MODE_PRIVATE);
                SharedPreferences.Editor edit = login.edit();
                edit.clear();
                edit.commit();
                edit.putString("projectId", data1.get(position).getProjectID());
                edit.putString("companyName", data1.get(position).getProjectName());
                edit.commit();

//                CreateInspectionTaskActivity.createInspectionTaskStepTwoFragment.setId(data1.get(position).getProjectID());

                if (CreateInspectionTaskActivity.createInspectionTaskStepTwoFragment.textDx != null) {
                    CreateInspectionTaskActivity.createInspectionTaskStepTwoFragment.setId(data1.get(position).getProjectID());
                }

            } else {
                holder.item_rectification_tv_title.setTextColor(Color.parseColor("#333333"));
                holder.imageView.setBackgroundResource(R.drawable.ic_unchecked);
            }
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /*
     * 将时间转换为时间戳
     */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }


    @Override
    public int getItemCount() {
        return data1.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imageView;
        TextView item_rectification_tv_title;
        TextView item_create_inspection_task_tv_source;
        TextView item_create_inspection_task_tv_no;
        TextView item_create_inspection_task_tv_company;
        TextView item_create_inspection_task_tv_deadline;
        TextView item_rectification_rtv_state;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.text_checkbox);
            item_rectification_tv_title = itemView.findViewById(R.id.item_rectification_tv_title);
            item_create_inspection_task_tv_source = itemView.findViewById(R.id.item_create_inspection_task_tv_source);
            item_create_inspection_task_tv_no = itemView.findViewById(R.id.item_create_inspection_task_tv_no);
            item_create_inspection_task_tv_company = itemView.findViewById(R.id.item_create_inspection_task_tv_company);
            item_create_inspection_task_tv_deadline = itemView.findViewById(R.id.item_create_inspection_task_tv_deadline);
            item_rectification_rtv_state = itemView.findViewById(R.id.item_rectification_rtv_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickIem.getClickItem(v, getAdapterPosition());
                }
            });
        }
    }

    private IClickIem iClickIem;

    public void setiClickIem(IClickIem iClickIem) {
        this.iClickIem = iClickIem;
    }

    public interface IClickIem {
        void getClickItem(View view, int position);
    }

}

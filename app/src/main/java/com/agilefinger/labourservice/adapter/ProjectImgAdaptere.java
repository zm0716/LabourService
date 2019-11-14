package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.BigImageActivity;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.bean.TeamGroupBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProjectImgAdaptere extends RecyclerView.Adapter<ProjectImgAdaptere.MyHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<KanBanDetailBean.ProjectImgBean> dataList;
    private Context context;

    public ProjectImgAdaptere(Context context, List<KanBanDetailBean.ProjectImgBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_more_img, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        if (dataList.get(position).getType() == 1) {
            holder.username.setText("巡检任务");
//            holder.username.setText(dataList.get(position).getType());
        } else {
            holder.username.setText("整改派单");
        }
        holder.teamname.setText(dataList.get(position).getTime());
//     // 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
//        //顶部左边圆角
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (10, 0, RoundedCornersTransformation.CornerType.TOP_LEFT);
        //顶部右边圆角
        RoundedCornersTransformation transformation1 = new RoundedCornersTransformation
                (10, 0, RoundedCornersTransformation.CornerType.TOP_RIGHT);

        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation, transformation1);

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.portrait)//图片加载出来前，显示的图片
                .fallback(R.mipmap.portrait) //url为空的时候,显示的图片
                .error(R.mipmap.portrait)//图片加载失败后，显示的图片
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(mation);

        String s = URL.IMAGE_URL + dataList.get(position).getImg_url();
        Log.e("ImagURL", s);
        Glide.with(context).load(s).apply(options).into(holder.item_staff_iv_portrait);


        holder.item_staff_iv_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position, dataList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public TextView teamname;
        public ImageView item_staff_iv_portrait;

        // public TextView buildname;
        public MyHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            teamname = itemView.findViewById(R.id.teamname);
            item_staff_iv_portrait = itemView.findViewById(R.id.item_staff_iv_portrait);
            // buildname = itemView.findViewById(R.id.buildname);
        }
    }

    public interface OnItemClickListener {
        void onClick(int postion, List<KanBanDetailBean.ProjectImgBean> dataList);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}

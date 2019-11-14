package com.agilefinger.labourservice.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.PicBean;
import com.agilefinger.labourservice.common.URL;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Shinelon on 2018/9/7.
 */

public class TuiJianAdapter extends RecyclerView.Adapter<TuiJianHolder>{
    private View mHeaderView;
    Context context;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mLayoutInflater;
    private final static int ITEM_HEADER=0;
    private final static int ITEM_CONTENT=1;
    private final static int ITEM_FOOT=2;
    List<PicBean> list;
    private int mHeader=1;
    private int mFoot=1;
    public TuiJianAdapter(Context context, List<PicBean> list) {
        this.context=context;
        this.list=list;
        mLayoutInflater = LayoutInflater.from(context);
    }



    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public TuiJianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_FOOT){
            return new TuiJianHolder(mHeaderView);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_zg_images, parent,false);
        return new TuiJianHolder(view);
    }

    @Override
    public void onBindViewHolder(final TuiJianHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_FOOT) {
            return;
        }else {
            Log.d("返回图片",URL.BASE_URL+list.get(position).getPi_watermark_url());
            String item = URL.BASE_URL + list.get(position).getPi_watermark_url();
            if (item.contains("/storage/")) {
                item = "file://" + item;
            }else {

            }
            Picasso.with(context).load(item).placeholder(R.mipmap.ic_default_img).into(holder.imgView);
           // Glide.with(context).load().into(holder.imgView);

            holder.reomve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,position,holder,list);
                }
            });

            final String finalItem = item;
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context, R.style.Theme_Light_Dialog);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.tanchu2, null);
                    //获得dialog的window窗口
                    Window window = dialog.getWindow();
                    //设置dialog在屏幕底部
                    window.setGravity(Gravity.BOTTOM);
                    //设置dialog弹出时的动画效果，从屏幕底部向上弹出
                    window.setWindowAnimations(R.style.dialogStyle);
                    window.getDecorView().setPadding(0, 0, 0, 0);
                    //获得window窗口的属性
                    WindowManager.LayoutParams lp = window.getAttributes();
                    //设置窗口宽度为充满全屏
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    //设置窗口高度为包裹内容
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    //将设置好的属性set回去
                    window.setAttributes(lp);
                    //将自定义布局加载到dialog上
                    dialog.setContentView(dialogView);
                    final ImageView tututu = dialogView.findViewById(R.id.tututu);

           /*     File file=new File(list.get(position));
                Bitmap smallBitmap = getSmallBitmap(file.getPath());
                tututu.setImageBitmap(smallBitmap);*/
                    Glide.with(context).load(finalItem)
                            .into(tututu);


                    tututu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }


    }

    //View.OnKeyListener
    @Override
    public int getItemViewType(int position) {
        if (mFoot!=0&&position>=list.size()){
            return ITEM_FOOT;
        }
        return ITEM_CONTENT;
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    public interface OnItemClickListener{
        void onClick(View view, int position, TuiJianHolder holder,List<PicBean> list);


    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}

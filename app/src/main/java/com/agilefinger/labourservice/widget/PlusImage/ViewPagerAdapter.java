package com.agilefinger.labourservice.widget.PlusImage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agilefinger.labourservice.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<String> imgList; //图片的数据源
    public interface OnItemClickListener{
        void onClick(int position, List<String> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
    public ViewPagerAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    //判断当前的View 和 我们想要的Object(值为View) 是否一样;返回 true/false
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    //instantiateItem()：将当前view添加到ViewGroup中，并返回当前View
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = getItemView(R.layout.view_pager_img);
        PhotoView imageView = (PhotoView) itemView.findViewById(R.id.img_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position,imgList);
            }
        });
        Picasso.with(context).load(imgList.get(position)).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    //destroyItem()：删除当前的View;  
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private View getItemView(int layoutId) {
        View itemView = LayoutInflater.from(this.context).inflate(layoutId, null, false);
        return itemView;
    }
}

package com.agilefinger.labourservice.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KanBanMoreImageAdapter extends BaseQuickAdapter<MoreImgBean.DataBean.ListBean, BaseViewHolder> {

    private static Date parse;

    public KanBanMoreImageAdapter() {
        super(R.layout.item_more_img, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoreImgBean.DataBean.ListBean item) {
        if (item.getType() == 1) {
            helper.setText(R.id.username, "巡检任务");
        } else {
            helper.setText(R.id.username, "整改派单");
        }
//        helper.setText(R.id.username, item.getBuilding_name());
        helper.setText(R.id.teamname, getNowDateShort(item.getTime()));
        String imgUrl = URL.IMAGE_URL + item.getImg_url();

        // 圆角图片 new RoundedCornersTransformation 参数为 ：半径 , 外边距 , 圆角方向(ALL,BOTTOM,TOP,RIGHT,LEFT,BOTTOM_LEFT等等)
        //顶部左边圆角
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

        ImageView imageView = helper.getView(R.id.item_staff_iv_portrait);

        Glide.with(mContext).asBitmap().load(imgUrl)
                .apply(options).into(imageView);
        helper.addOnClickListener(R.id.item_staff_iv_portrait);

    }

    /**
     * 修改时间格式
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getNowDateShort(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parse = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(parse);
        return dateString;
    }
}

package com.agilefinger.labourservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.ImagesBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;
import com.squareup.picasso.Picasso;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class RectificationImagesAdapter extends BaseQuickAdapter<ImagesBean, BaseViewHolder> {
    public RectificationImagesAdapter() {
        super(R.layout.item_rectification_image, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImagesBean item) {
        ImageView image = helper.getView(R.id.item_rectification_image_iv_image);
        if (!item.getPath().contains(URL.BASE_URL_)) {
            item.setPath(URL.BASE_URL_ + item.getPath());
        }
        int wh = AutoSizeUtils.dp2px(mContext, 70);
        Picasso.with(mContext).load(item.getPath()).resize(wh, wh).centerCrop().placeholder(R.mipmap.ic_default_img).into(image);
        RoundTextView rtvNum = helper.getView(R.id.item_rectification_image_rtv_num);
        if (item.getNum() > 0) {
            rtvNum.setText(item.getNum() + "图");
            rtvNum.setVisibility(View.VISIBLE);
        } else {
            rtvNum.setVisibility(View.GONE);
        }
    }

}

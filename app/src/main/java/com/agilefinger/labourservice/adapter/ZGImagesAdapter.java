package com.agilefinger.labourservice.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class ZGImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ZGImagesAdapter() {
        super(R.layout.item_zg_images, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView image = helper.getView(R.id.item_zg_images_iv_image);
        if (TextUtils.isEmpty(item)) {
            helper.getView(R.id.item_zg_images_iv_del).setVisibility(View.GONE);
            image.setImageResource(R.mipmap.ic_add);
        } else {
            helper.getView(R.id.item_zg_images_iv_del).setVisibility(View.VISIBLE);
            if (item.contains("/storage/")) {
                item = "file://" + item;
            } else if (!item.contains(URL.BASE_URL_)) {
                item = URL.BASE_URL_ + item;
            } else {
                item = URL.BASE_URL_ + item;
            }
            Picasso.with(mContext).load(item).placeholder(R.mipmap.ic_default_img).into(image);
            helper.addOnClickListener(R.id.item_zg_images_iv_del);
        }
    }
}

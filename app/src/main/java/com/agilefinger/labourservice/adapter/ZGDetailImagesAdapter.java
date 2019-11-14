package com.agilefinger.labourservice.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.common.URL;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class ZGDetailImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ZGDetailImagesAdapter() {
        super(R.layout.item_zg_images, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.getView(R.id.item_zg_images_iv_del).setVisibility(View.GONE);
        ImageView image = helper.getView(R.id.item_zg_images_iv_image);
        if (item.contains("/storage/")) {
            item = "file://" + item;
        }
        Picasso.with(mContext).load(item).placeholder(R.mipmap.ic_default_img).into(image);
        helper.addOnClickListener(R.id.item_zg_images_iv_del);

    }
}

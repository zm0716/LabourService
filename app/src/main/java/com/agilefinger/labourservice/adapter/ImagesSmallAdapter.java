package com.agilefinger.labourservice.adapter;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.common.URL;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

public class ImagesSmallAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImagesSmallAdapter() {
        super(R.layout.item_images_small, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        final ImageView image = helper.getView(R.id.item_images_small_iv_image);
        if (TextUtils.isEmpty(item)) {
            helper.getView(R.id.item_images_small_iv_del).setVisibility(View.GONE);
            image.setImageResource(R.mipmap.ic_add);
        } else {
            helper.getView(R.id.item_images_small_iv_del).setVisibility(View.GONE);
            if (item.contains("/storage/")) {
                item = "file://" + item;
            } else if (!item.contains(URL.BASE_URL_)) {
                item = URL.BASE_URL_ + item;
            } else {
                item = URL.BASE_URL_ + item;
            }
            Picasso.with(mContext).load(item).placeholder(R.mipmap.ic_default_img).into(image);
            final String finalItem = item;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
                    View dialogView = LayoutInflater.from(mContext).inflate(R.layout.tanchu2, null);
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
                    String allitem= finalItem;
                    if (TextUtils.isEmpty(allitem)) {
                        tututu.setImageResource(R.mipmap.ic_add);
                    } else {

                        Picasso.with(mContext).load(allitem).placeholder(R.mipmap.ic_default_img).into(tututu);
                    }

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
}

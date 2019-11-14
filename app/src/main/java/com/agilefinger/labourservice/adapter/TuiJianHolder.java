package com.agilefinger.labourservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;


/**
 * Created by Shinelon on 2018/9/7.
 */

public class TuiJianHolder extends RecyclerView.ViewHolder{

    ImageView imgView;
    ImageView reomve;
    public TuiJianHolder(View view) {
        super(view);
        imgView = (ImageView) view.findViewById(R.id.item_zg_images_iv_image);
        reomve= (ImageView) view.findViewById(R.id.item_zg_images_iv_del);
    }

}

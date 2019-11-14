package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.AddressBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BuildingAdapter extends BaseQuickAdapter<AddressBean.CanChooseBean,BaseViewHolder>{

    private ImageView image_checked;

    public BuildingAdapter(int layoutResId, @Nullable List<AddressBean.CanChooseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.CanChooseBean item) {
        helper.setText(R.id.text_louhao,item.getName());
        image_checked = helper.getView(R.id.image_checked);

        image_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_checked.setImageResource(R.mipmap.ic_checked);
            }
        });
    }
}

package com.agilefinger.labourservice.adapter;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CatalogNodeBean;
import com.agilefinger.labourservice.bean.CheckItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AnQuanAdapter extends BaseQuickAdapter<CatalogNodeBean, BaseViewHolder> {
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemData(CatalogNodeBean item);
    }

    public AnQuanAdapter(int layoutResId, @Nullable List<CatalogNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CatalogNodeBean item) {
        final ImageView imgChkBox = helper.getView(R.id.imageView_checkebox);
        if (item!=null){
            helper.setText(R.id.item_data_collection_list_tv_title, item.getTct_name());
            if(item.isChecked()){
                imgChkBox.setImageResource(R.drawable.ic_checked);
            }else {
                imgChkBox.setImageResource(R.drawable.ic_unchecked);
            }
        }

        imgChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();
                if(item.isChecked()){
                    item.setChecked(false, true, true);
                    imgChkBox.setImageResource(R.drawable.ic_unchecked);
                }else {
                    item.setChecked(true, true, true);
                    imgChkBox.setImageResource(R.drawable.ic_checked);
//                    item.checkAllParent();
//                    item.checkAllSub();
                }
            }
        });

    }
}

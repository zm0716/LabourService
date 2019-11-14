package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CheckItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ItemAdapter extends BaseQuickAdapter<CheckItemBean,BaseViewHolder> {

    public ItemAdapter(int layoutResId, @Nullable List<CheckItemBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, final CheckItemBean item) {



        final ImageView imageView_list = helper.getView(R.id.imageView_list);

        if (item!=null){
            helper.setText(R.id.item_data_collection_list_tv_item,item.getTci_name());
            if (!item.isSelected()){
                imageView_list.setImageResource(R.drawable.ic_unchecked);
            }else {
                imageView_list.setImageResource(R.drawable.ic_checked);
            }
        }

        imageView_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();
                if(item.isSelected()){
                    item.setSelected(false);
                    imageView_list.setImageResource(R.drawable.ic_unchecked);
                }else {
                    item.setSelected(true);
                    imageView_list.setImageResource(R.drawable.ic_checked);
                }
            }
        });
    }
}

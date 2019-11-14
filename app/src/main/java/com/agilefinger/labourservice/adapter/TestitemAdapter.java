package com.agilefinger.labourservice.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.CheckItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TestitemAdapter extends BaseQuickAdapter<CheckItemBean,BaseViewHolder> {

    public TestitemAdapter(int layoutResId, @Nullable List<CheckItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckItemBean item) {
        helper.setText(R.id.item_data_collection_multi_tv_subtitle,item.getCatalogFullName());
        //点击选中
        final ImageView item_fexz_collection_multi_iv_check = helper.getView(R.id.item_data_collection_multi_iv_check);
        if (item!=null){
            helper.setText(R.id.item_data_collection_multi_tv_jcx,item.getTci_name());
            if (!item.isSelected()){
                item_fexz_collection_multi_iv_check.setImageResource(R.drawable.ic_unchecked);
            }else {
                item_fexz_collection_multi_iv_check.setImageResource(R.drawable.ic_checked);
            }
        }


        item_fexz_collection_multi_iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isSelected()){
                    item.setSelected(false);
                    item_fexz_collection_multi_iv_check.setImageResource(R.drawable.ic_unchecked);
                }else {
                    item.setSelected(true);
                    item_fexz_collection_multi_iv_check.setImageResource(R.drawable.ic_checked);
                }
            }
        });
    }
}

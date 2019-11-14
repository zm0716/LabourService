package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.BuildingBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BuildingAddressAdapter extends BaseQuickAdapter<BuildingBean, BaseViewHolder> {
    public BuildingAddressAdapter() {
        super(R.layout.item_building_img, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuildingBean item) {
        if (item.isAdd() || TextUtils.isEmpty(item.getName())) {
            helper.getView(R.id.item_image).setVisibility(View.VISIBLE);
            helper.getView(R.id.m_rela).setVisibility(View.GONE);
            ((ImageView) helper.getView(R.id.item_image)).setImageResource(R.mipmap.ic_add);
        } else {
            helper.getView(R.id.item_image).setVisibility(View.GONE);
            helper.getView(R.id.m_rela).setVisibility(View.VISIBLE);
//            helper.getView(R.id.item_zg_zp_iv_del).setVisibility(View.VISIBLE);
            helper.setText(R.id.m_add_text, item.getName());
//            ((ImageView) helper.getView(R.id.item_zg_zp_iv_image)).setImageResource(R.mipmap.portrait);
        }
//        helper.setText(R.id.item_zg_zp_tv_name, item.getName());
        helper.addOnClickListener(R.id.m_delete);

    }
}

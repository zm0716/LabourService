package com.agilefinger.labourservice.adapter;

import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class KanBanTypeAdapter extends BaseQuickAdapter<KanBanTypeBean, BaseViewHolder> {
    public KanBanTypeAdapter() {
        super(R.layout.item_staff, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KanBanTypeBean item) {
//        helper.getView(R.id.item_staff_iv_portrait).setVisibility(View.GONE);
        if (item.isCheck()) {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_checked);
        } else {
            helper.setImageResource(R.id.item_staff_iv_check, R.mipmap.ic_uncheck);
        }
        helper.setText(R.id.item_staff_tv_name, item.getName());
    }
}

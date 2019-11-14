package com.agilefinger.labourservice.adapter;

import android.widget.RelativeLayout;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.jessyan.autosize.utils.AutoSizeUtils;
import me.jessyan.autosize.utils.ScreenUtils;

public class ZGDetailVoiceAdapter extends BaseQuickAdapter<VoiceBean, BaseViewHolder> {
    public ZGDetailVoiceAdapter() {
        super(R.layout.item_zg_voice, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoiceBean item) {
        int width = ScreenUtils.getScreenSize(mContext)[0] - AutoSizeUtils.dp2px(mContext, 40);
        RelativeLayout rl = helper.getView(R.id.item_zg_voice_rl_wrapper);
        if (item.getLen() >= 60) {
            rl.getLayoutParams().width = width;
        } else {
            int w = Math.round((item.getLen() * width / 60));
            if (w < width / 3) {
                rl.getLayoutParams().width = width / 3;
            } else {
                rl.getLayoutParams().width = w;
            }
        }
        helper.setText(R.id.item_zg_voice_tv_len, String.valueOf(Math.round(item.getLen())) + "''");
        helper.setGone(R.id.item_zg_voice_iv_del, false);
        helper.addOnClickListener(R.id.item_zg_voice_rll_voice);
    }
}

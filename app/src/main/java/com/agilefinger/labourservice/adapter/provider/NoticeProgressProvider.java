package com.agilefinger.labourservice.adapter.provider;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.NoticeMultipleBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

public class NoticeProgressProvider extends BaseItemProvider<MessageBean, BaseViewHolder> {
    private SparseArray<CountDownTimer> countDownCounters;

    public NoticeProgressProvider() {

    }

    @Override
    public int viewType() {
        return NoticeMultipleBean.TYPE_PROGRESS;
    }

    @Override
    public int layout() {
        return R.layout.item_notice_progress;
    }

    @Override
    public void convert(BaseViewHolder helper, MessageBean data, int position) {

    }

}

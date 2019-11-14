package com.agilefinger.labourservice.adapter;

import android.support.annotation.Nullable;

import com.agilefinger.labourservice.adapter.provider.NoticeCorrectProvider;
import com.agilefinger.labourservice.adapter.provider.NoticeProgressProvider;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.NoticeMultipleBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

public class NoticeAdapter extends MultipleItemRvAdapter<MessageBean, BaseViewHolder> {

    public NoticeCorrectProvider noticeCorrectProvider;
    public NoticeProgressProvider noticeProgressProvider;

    public NoticeAdapter(@Nullable List<MessageBean> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(MessageBean entity) {
        if (entity.getType() == NoticeMultipleBean.TYPE_CORRECT) {
            return NoticeMultipleBean.TYPE_CORRECT;
        } else if (entity.getType() == NoticeMultipleBean.TYPE_PROGRESS) {
            return NoticeMultipleBean.TYPE_PROGRESS;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(noticeCorrectProvider = new NoticeCorrectProvider());
        mProviderDelegate.registerProvider(noticeProgressProvider = new NoticeProgressProvider());
    }

}

package com.agilefinger.labourservice.adapter;

import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.utils.DateTimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public MessageAdapter() {
        super(R.layout.item_message, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        RoundTextView rtvUnread = helper.getView(R.id.item_message_rtv_unread);
        if ("0".equals(item.getUnread())) {
            rtvUnread.setVisibility(View.GONE);
        } else {
            rtvUnread.setVisibility(View.VISIBLE);
        }
        try {
            if (Integer.parseInt(item.getUnread()) > 99) {
                helper.setText(R.id.item_message_rtv_unread, 99 + "+");
            } else {
                helper.setText(R.id.item_message_rtv_unread, item.getUnread());
            }
        } catch (Exception exception) {
            helper.setText(R.id.item_message_rtv_unread, item.getUnread());
        }
        helper.setText(R.id.item_message_rtv_subtitle, item.getSubtitle());
        helper.setText(R.id.item_message_rtv_title, item.getTitle());
        helper.setText(R.id.item_message_rtv_time, DateTimeUtil.formatFriendly2(DateTimeUtil.parseDate(item.getTime()), true));
    }
}

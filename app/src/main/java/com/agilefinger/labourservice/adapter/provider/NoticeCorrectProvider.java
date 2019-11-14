package com.agilefinger.labourservice.adapter.provider;

import android.graphics.Paint;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.NoticeMultipleBean;
import com.agilefinger.labourservice.utils.DateTimeUtil;
import com.agilefinger.labourservice.utils.DateUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.flyco.roundview.RoundTextView;

public class NoticeCorrectProvider extends BaseItemProvider<MessageBean, BaseViewHolder> {


    @Override
    public int viewType() {
        return NoticeMultipleBean.TYPE_CORRECT;
    }

    @Override
    public int layout() {
        return R.layout.item_notice_correct;
    }

    @Override
    public void convert(BaseViewHolder helper, MessageBean data, int position) {
        helper.setText(R.id.item_notice_correct_tv_title, data.getTitle());
        helper.setText(R.id.item_notice_correct_tv_project, "项目名称:" + data.getProject());


        helper.setText(R.id.item_notice_correct_tv_deadline, "整改期限:" + data.getDeadline());
        TextView tvLook = helper.getView(R.id.item_notice_correct_tv_look);
        tvLook.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        RoundTextView rtvState = helper.getView(R.id.item_notice_correct_rtv_state);
        if ("1".equals(data.getRead())) {
            rtvState.setText("已读");
            rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.gray_999999));
        } else {
            rtvState.setText("未读");
            rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.red_e27966));
        }
        String timeStamp = DateUtil.timeStamp1(data.getTime());
        String s = DateUtil.convertTimeToFormat(timeStamp);
        //helper.setText(R.id.item_rectification_tv_create,"创建于:  "+s+"  星期"+s1+"  "+s2);

//        helper.setText(R.id.item_notice_correct_rtv_time, s + "  " + DateTimeUtil.
//                formatFriendly2(DateTimeUtil.parseDate(data.getTime()), true));

        //张孟改时间显示错误
        helper.setText(R.id.item_notice_correct_rtv_time, DateTimeUtil.
                formatFriendly2(DateTimeUtil.parseDate(data.getTime()), true));
    }
}

package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.RectificationDetailActivity;
import com.agilefinger.labourservice.activity.RectificationListActivity;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.DateTimeUtil;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;

public class RectificationAdapter extends BaseQuickAdapter<RectificationBean, BaseViewHolder> {
    private CallbackListener callbackListener;
    private long beginTime;

    public RectificationAdapter() {
        super(R.layout.item_rectification, null);
    }

    public RectificationAdapter(CallbackListener callbackListener) {
        super(R.layout.item_rectification, null);
        this.callbackListener = callbackListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RectificationBean item) {
        RectificationImagesAdapter rectificationImagesAdapter = new RectificationImagesAdapter();
        final RecyclerView rvImages = helper.getView(R.id.item_rectification_rv_images);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);
        rvImages.setAdapter(rectificationImagesAdapter);
        rvImages.setNestedScrollingEnabled(false);
        rectificationImagesAdapter.setNewData(item.getImagesList());

        rectificationImagesAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                callbackListener.todoLong(item, helper.getLayoutPosition());
                return true;
            }
        });
        rectificationImagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                callbackListener.todo(item, helper.getLayoutPosition());
            }
        });
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    beginTime = System.currentTimeMillis();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - beginTime > 500) {
                        callbackListener.todoLong(item, helper.getLayoutPosition());
                    } else {
                        callbackListener.todo(item, helper.getLayoutPosition());
                    }
                }
                return true;
            }
        });

        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_rectification_tv_title, item.getTitle());
        } else {
            helper.setText(R.id.item_rectification_tv_title, "暂未选择整改项目");
        }
        if (!TextUtils.isEmpty(item.getZgMan())) {
            helper.setText(R.id.item_rectification_tv_zg_man, item.getZgMan() + "负责整改");
        } else {
            helper.setText(R.id.item_rectification_tv_zg_man, "暂未选择整改负责人");
        }
        if (!TextUtils.isEmpty(item.getDeadline())) {
            helper.setText(R.id.item_rectification_tv_deadline, item.getDeadline());
        } else {
            helper.setText(R.id.item_rectification_tv_deadline, "暂未选择整改期限");
        }
        helper.setText(R.id.item_rectification_tv_create, item.getPerson() + " 创建于" + DateTimeUtil.formatFriendly2(DateTimeUtil.parseDate(item.getTime()), true));
        LinearLayout llVoice = helper.getView(R.id.item_rectification_ll_voice);
        RoundLinearLayout rllVoice = helper.getView(R.id.item_rectification_rll_voice);
        TextView tvVoiceLen = helper.getView(R.id.item_rectification_tv_voice_len);
        TextView tvVoiceNum = helper.getView(R.id.item_rectification_tv_voice_num);

        if (item.getVoice() != null) {
            if (item.getVoice_second() != null && item.getVoice_second().size() > 0) {
                llVoice.setVisibility(View.VISIBLE);
                tvVoiceLen.setText((int) Float.parseFloat(item.getVoice_second().get(0)) + "''");
            } else {
                llVoice.setVisibility(View.GONE);
            }
            if (item.getVoice().size() > 1) {
                tvVoiceNum.setText("更多" + item.getVoice().size() + "条语音");
                tvVoiceNum.setVisibility(View.VISIBLE);
            } else {
                tvVoiceNum.setVisibility(View.GONE);
            }
        } else {
            llVoice.setVisibility(View.GONE);
        }
        RoundTextView rtvState = helper.getView(R.id.item_rectification_rtv_state);
        if (!TextUtils.isEmpty(item.getState())) {
            rtvState.setVisibility(View.VISIBLE);
            switch (item.getState()) {
                case Constants.ZG_STATE_1:
                    rtvState.setText("整改中");
                    rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.blue_66a7e1));
                    break;
                case Constants.ZG_STATE_2:
                    rtvState.setText("待验收");
                    rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.yellow_fecb1b));
                    break;
                case Constants.ZG_STATE_3:
                    rtvState.setText("已完成");
                    rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.green_58b888));
                    break;
                case Constants.ZG_STATE_4:
                    rtvState.setText("已忽略");
                    rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.red_cc0000));
                    break;
                case Constants.ZG_STATE_5:
                    rtvState.setText("已撤销");
                    rtvState.getDelegate().setBackgroundColor(mContext.getResources().getColor(R.color.gray_999999));
                    break;
            }
        } else {
            rtvState.setVisibility(View.GONE);
        }
    }

    public interface CallbackListener {
        void todo(Object object, int position);

        void todoLong(Object object, int position);
    }
}

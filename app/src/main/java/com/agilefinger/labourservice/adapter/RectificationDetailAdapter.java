package com.agilefinger.labourservice.adapter;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.RectificationItemBean;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.agilefinger.labourservice.utils.DateTimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RectificationDetailAdapter extends BaseQuickAdapter<RectificationItemBean, BaseViewHolder> {
    private PlayVoiceListener playVoiceListener;

    public RectificationDetailAdapter(PlayVoiceListener playVoiceListener) {
        super(R.layout.item_rectification_detail, null);
        this.playVoiceListener = playVoiceListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RectificationItemBean item) {
        helper.setText(R.id.item_rectification_detail_tv_name1, item.getName1());
        helper.setText(R.id.item_rectification_detail_tv_operate, item.getOperate());
        if (TextUtils.isEmpty(item.getName2())) {
            helper.setGone(R.id.item_rectification_detail_tv_name2, false);
        } else {
            helper.setGone(R.id.item_rectification_detail_tv_name2, true);
            helper.setText(R.id.item_rectification_detail_tv_name2, item.getName2());
        }
        if (!TextUtils.isEmpty(item.getReply())) {
            helper.setGone(R.id.item_rectification_detail_tv_reply, true);
            helper.setText(R.id.item_rectification_detail_tv_reply, item.getReply());
        } else {
            helper.setGone(R.id.item_rectification_detail_tv_reply, false);
        }
        helper.setText(R.id.item_rectification_detail_tv_time,  DateTimeUtil.formatFriendly2(DateTimeUtil.parseDate(item.getTime()), true));
        //图片
        ZGDetailImagesAdapter zgDetailImagesAdapter = new ZGDetailImagesAdapter();
        RecyclerView rvImages = helper.getView(R.id.item_rectification_detail_rv_images);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);
        rvImages.setAdapter(zgDetailImagesAdapter);
        zgDetailImagesAdapter.setNewData(item.getImages());
        zgDetailImagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                playVoiceListener.plusImageCallback(item.getImages(), position);
            }
        });
        //语音
        ZGDetailVoiceAdapter zgDetailVoiceAdapter = new ZGDetailVoiceAdapter();
        RecyclerView rvVoice = helper.getView(R.id.item_rectification_detail_rv_voice);
        rvVoice.setAdapter(zgDetailVoiceAdapter);
        rvVoice.setLayoutManager(new LinearLayoutManager(mContext));
        List<VoiceBean> mVoiceList = new ArrayList<>();
        List<String> voiceList = item.getVoice();
        List<String> voiceSecondList = item.getVoice_second();
        if (voiceList != null && voiceSecondList != null && voiceList.size() == voiceSecondList.size()) {
            rvVoice.setVisibility(View.VISIBLE);
            for (int i = 0; i < voiceList.size(); i++) {
                VoiceBean voice = new VoiceBean(Float.parseFloat(voiceSecondList.get(i)), voiceList.get(i), voiceList.get(i));
                mVoiceList.add(voice);
            }
            zgDetailVoiceAdapter.setNewData(mVoiceList);
            zgDetailVoiceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    VoiceBean voice = (VoiceBean) adapter.getItem(position);
                    playVoiceListener.playVoiceCallback(voice.getPath(), (ImageView) view.findViewById(R.id.item_zg_voice_iv_play));
                }
            });
        } else {
            rvVoice.setVisibility(View.GONE);
        }

        TextView tvLook = helper.getView(R.id.item_rectification_detail_iv_look);
        tvLook.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        helper.addOnClickListener(R.id.item_rectification_detail_iv_look);
    }

    public interface PlayVoiceListener {
        void playVoiceCallback(String path, ImageView imageView);

        void plusImageCallback(List<String> images, int position);
    }
}

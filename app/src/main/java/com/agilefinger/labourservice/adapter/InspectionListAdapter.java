package com.agilefinger.labourservice.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.bean.InspectionPositionBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.TaskBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InspectionListAdapter extends BaseQuickAdapter<TaskBean, BaseViewHolder> {

    public InspectionListAdapter(int item_inspection, List<TaskBean> mList) {
        super(item_inspection, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskBean item) {
        helper.setText(R.id.item_rectification_tv_title, item.getProjectName());
        helper.setText(R.id.item_rectification_tv_task, item.getMissionNo());
        helper.setText(R.id.item_rectification_tv_zg_man, item.getDoUser());
        helper.setText(R.id.item_rectification_tv_deadline, item.getEndTime());

        TextView rtv_state = helper.getView(R.id.item_rectification_xjian_state);
        rtv_state.setText(item.getStatus());

        String timeStamp = DateUtil.timeStamp1(item.getCreateTime());
        String s = DateUtil.convertTimeToFormat(timeStamp);
        String s1 = DateUtil.caculateWeeks(item.getCreateTime());
//        String s2 = DateUtil.timeStampToDate(timeStamp, "HH:mm:ss");
        //张孟修改
        String s2 = DateUtil.timeStampToDate(timeStamp, "HH:mm");
        helper.setText(R.id.item_rectification_tv_create, "创建于:  " + s + "  星期" + s1 + "  " + s2);


        if (!TextUtils.isEmpty(item.getStatus())) {
            rtv_state.setVisibility(View.VISIBLE);
            switch (item.getStatus()) {
                case "检测中":
                    rtv_state.setText("检测中");
                    Resources resources = mContext.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.textz_bg);
                    rtv_state.setBackground(drawable);
                    //  rtv_state.setBackgroundColor(mContext.getResources().getColor(R.color.blue_66a7e1));
                    break;
                case "未启动":
                    rtv_state.setText("未启动");
                    Resources resources1 = mContext.getResources();
                    Drawable drawable1 = resources1.getDrawable(R.drawable.textw_bg);
                    rtv_state.setBackground(drawable1);
                    // rtv_state.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_fecb1b));
                    break;
                case "已停止":
                    rtv_state.setText("已停止");
                    Resources resources2 = mContext.getResources();
                    Drawable drawable2 = resources2.getDrawable(R.drawable.textc_bg);
                    rtv_state.setBackground(drawable2);
                    //  rtv_state.setBackgroundColor(mContext.getResources().getColor(R.color.green_58b888));
                    break;
                case "已完成":
                    rtv_state.setText("已完成");
                    Resources resources4 = mContext.getResources();
                    Drawable drawable4 = resources4.getDrawable(R.drawable.texty_bg);
                    rtv_state.setBackground(drawable4);
                    //   rtv_state.setBackgroundColor(mContext.getResources().getColor(R.color.red_cc0000));
                    break;
            }
        } else {
            rtv_state.setVisibility(View.GONE);
        }
    }


}

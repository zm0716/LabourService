package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.XmPopListAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.XmuPopBean;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateXMFourActivity extends BaseActivity {

    @BindView(R.id.activity_wc_xinxi)
    RoundTextView activityWcXinxi;
    @BindView(R.id.m_add_group_leader)
    TextView mAddGroupLeader;

    @Override
    public int initLayoutView() {
        return R.layout.activity_create_xmfour;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("创建项目(4/4)", false, false, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    @OnClick({R.id.activity_wc_xinxi,R.id.m_add_group_leader})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_wc_xinxi:
                showPop();
                break;
            case R.id.m_add_group_leader:
                IntentUtils.startActivity(CreateXMFourActivity.this, AddGroupLeaderActivity.class);
                break;
        }
    }

    private void showPop() {
        View view = View.inflate(CreateXMFourActivity.this, R.layout.item_popwindow, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        popupWindow.setHeight(1000);
        popupWindow.setAnimationStyle(R.style.pop_animatiom_style);
        popupWindow.setOutsideTouchable(true);
        if (popupWindow.isShowing()) {
            darkenBackground(1.0f);
        } else {
            darkenBackground(0.5f);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1.0f);
            }
        });
        popupWindow.showAtLocation(activityWcXinxi, Gravity.BOTTOM, 0, 0);

        RecyclerView recyclerview_pop = view.findViewById(R.id.recyclerview_pop);
        ArrayList<XmuPopBean> list = new ArrayList<>();
        XmuPopBean xmuPopBean = new XmuPopBean();
        for (int i = 0; i < 15; i++) {
            xmuPopBean.setName("A1");
            list.add(xmuPopBean);
        }
        XmPopListAdapter xmPopListAdapter = new XmPopListAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview_pop.setLayoutManager(linearLayoutManager);
        recyclerview_pop.setAdapter(xmPopListAdapter);

        view.findViewById(R.id.button_qx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.button_qx:
                popupWindow.dismiss();
//                        break;
//                    case R.id.button_wc:
//
//                        break;
//                }
            }
        });


//        //弹框消失
//        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }
}

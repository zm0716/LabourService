package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.fragment.NoticeFragment;
import com.agilefinger.labourservice.fragment.ToDoFragment;
import com.agilefinger.labourservice.view.dialog.MessageNoticeTypeDialog;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.activity_message_detail_tv_notice_type)
    TextView tvNoticeType;
    @BindView(R.id.activity_message_detail_v_to_do)
    View vToDo;
    @BindView(R.id.activity_message_detail_v_notice)
    View vNotice;
    private MessageNoticeTypeDialog messageNoticeTypeDialog;
    private List<Fragment> mFragments;
    private Fragment[] fragments = new Fragment[2];
    private int currentFragment = 1;
    private View[] vLine = new View[2];
    private String title, companyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_message_detail;
    }

    @Override
    public void initData() {
        super.initData();
        title = getIntent().getExtras().getString(Constants.EXTRA_DATA);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarTrans(title);
        initIndicator();
        initFragment();
    }

    private void initIndicator() {
        vLine[0] = vToDo;
        vLine[1] = vNotice;
    }

    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        mFragments = new ArrayList<>();
        mFragments.add(new ToDoFragment());
        NoticeFragment noticeFragment = new NoticeFragment();
        noticeFragment.setArguments(bundle);
        mFragments.add(noticeFragment);
        fragments[1] = mFragments.get(1);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.activity_message_detail_fl_wrapper, fragments[1]);
        tx.commitAllowingStateLoss();
    }

    @Override
    @OnClick({R.id.activity_message_detail_ll_notice, R.id.activity_message_detail_tv_to_do})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_message_detail_tv_to_do: //待办
                switchFragment(0);
                break;
            case R.id.activity_message_detail_ll_notice://通知
                if (currentFragment == 0) {
                    switchFragment(1);
                } else {
                    showNoticeTypeDialog();
                }
                break;
        }
    }

    private void switchFragment(int index) {
        if (index != currentFragment) {
            FragmentTransaction tx = getFragmentManager().beginTransaction();
            if (fragments[index] == null) {
                fragments[index] = mFragments.get(index);
                tx.add(R.id.activity_message_detail_fl_wrapper, fragments[index]);
            }
            tx.hide(fragments[currentFragment]).show(fragments[index]);
            tx.commitAllowingStateLoss();
            vLine[index].setVisibility(View.VISIBLE);
            vLine[currentFragment].setVisibility(View.INVISIBLE);
            currentFragment = index;
        }
    }

    private void showNoticeTypeDialog() {
        closeNoticeTypeDialog();
        messageNoticeTypeDialog = new MessageNoticeTypeDialog(this, new MessageNoticeTypeDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    tvNoticeType.setText(param[0]);
                    if (fragments[1] != null) {
                        ((NoticeFragment) fragments[1]).getMessageList(Constants.NETWORK_REFRESH);
                    }
                }
                closeNoticeTypeDialog();
            }
        });
        messageNoticeTypeDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeNoticeTypeDialog();
    }

    private void closeNoticeTypeDialog() {
        if (messageNoticeTypeDialog != null) {
            messageNoticeTypeDialog.cancel();
            messageNoticeTypeDialog = null;
        }
    }
}

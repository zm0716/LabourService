package com.agilefinger.labourservice.activity;

import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.LookStateAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ReadBean;
import com.agilefinger.labourservice.bean.ReadModel;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 查阅状态
 */
public class LookStateActivity extends BaseActivity {
    @BindView(R.id.activity_look_state_tv_unread)
    TextView tvUnread;
    @BindView(R.id.activity_look_state_tv_read)
    TextView tvRead;
    @BindView(R.id.activity_look_state_v_read)
    View vRead;
    @BindView(R.id.activity_look_state_v_unread)
    View vUnread;
    @BindView(R.id.activity_look_state_rv_list)
    RecyclerView rvList;
    private String correct_id, msg_id;
    private LookStateAdapter lookStateAdapter;
    private int current = 0;
    private View[] vLine = new View[2];
    private ReadBean read, unread;

    @Override
    public int initLayoutView() {
        return R.layout.activity_look_state;
    }

    @Override
    public void initData() {
        super.initData();
        correct_id = getIntent().getExtras().getString(Constants.EXTRA_DATA);
        msg_id = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarTrans("查阅状态");
        initRV();
        initIndicator();
    }

    private void initIndicator() {
        vLine[0] = vRead;
        vLine[1] = vUnread;
    }

    private void initRV() {
        lookStateAdapter = new LookStateAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(lookStateAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLookState();
    }

    private void getLookState() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().lookState(loginBean.getUser_id(), correct_id, msg_id, new HttpEngine.OnResponseCallback<HttpResponse.Read>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Read data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                showInfo(data.getData());
            }
        });
    }

    private void showInfo(ReadModel data) {
        read = data.getRead();
        unread = data.getUn_read();
        if (read != null) {
            tvRead.setText("已读（" + read.getCount() + "）");
            lookStateAdapter.setNewData(read.getList());
        }
        if (unread != null) {
            tvUnread.setText("未读（" + unread.getCount() + "）");
        }
    }

    private void showList(int index) {
        if (index == 0) {
            if (read != null) {
                lookStateAdapter.setNewData(read.getList());
            }
        } else {
            if (unread != null) {
                lookStateAdapter.setNewData(unread.getList());
            }
        }
    }

    @Override
    @OnClick({R.id.activity_look_state_tv_read, R.id.activity_look_state_tv_unread})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_look_state_tv_read:
                switchState(0);
                break;
            case R.id.activity_look_state_tv_unread:
                switchState(1);
                break;
        }
    }

    private void switchState(int index) {
        if (index != current) {
            vLine[index].setVisibility(View.VISIBLE);
            vLine[current].setVisibility(View.INVISIBLE);
            showList(index);
            current = index;
        }
    }
}

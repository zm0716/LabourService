package com.agilefinger.labourservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.MessageDetailActivity;
import com.agilefinger.labourservice.adapter.MessageAdapter;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 86251 on 2019/5/28.
 */

public class MessageFragment extends JBaseFragment {

    @BindView(R.id.fragment_message_rv_list)
    RecyclerView rvList;
    private MessageAdapter messageAdapter;

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        setToolbar(false, "消息");
        initRV();
    }

    private void initRV() {
        messageAdapter = new MessageAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(messageAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        messageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageBean messageBean = (MessageBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, messageBean.getId());
                bundle.putString(Constants.EXTRA_DATA, messageBean.getTitle());
                IntentUtils.startActivity(getActivity(), MessageDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void lazyLoadOnlyOne() {
        getIndex();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getIndex();
    }

    private void getIndex() {
        LoadingDialog.showLoading(getActivity());
        HttpManager.getInstance().getIndex(loginBean.getUser_id(), new HttpEngine.OnResponseCallback<HttpResponse.Message>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Message data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    ToastUtils.showShortSafe(retmsg);
                    return;
                }
                messageAdapter.setNewData(data.getData());
            }
        });
    }
}

package com.agilefinger.labourservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.RectificationDetailActivity;
import com.agilefinger.labourservice.adapter.NoticeAdapter;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.NoticeMultipleBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * Created by 86251 on 2019/5/28.
 * 待办
 */

public class NoticeFragment extends JBaseFragment {

    @BindView(R.id.fragment_notice_rv_list)
    RecyclerView rvList;
    private NoticeAdapter noticeAdapter;
    private String companyId;
    private int page = 1;

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    public void initData(View view) {
        super.initData(view);
        companyId = getArguments().getString(Constants.EXTRA_DATA_COMPANY);
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        initRV();
    }

    private void initRV() {
        noticeAdapter = new NoticeAdapter(new ArrayList<MessageBean>());
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(getActivity(), 10)));
        rvList.setAdapter(noticeAdapter);
    }


    @Override
    protected void lazyLoadOnlyOne() {
        getMessageList(Constants.NETWORK_REFRESH);
    }

    @Override
    protected void initListener() {
        super.initListener();
        noticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageBean message = (MessageBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                if (message.getType() == NoticeMultipleBean.TYPE_CORRECT) {
                    bundle.putString(Constants.EXTRA_DATA, message.getCorrect_id());
                    bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                    IntentUtils.startActivity(getActivity(), RectificationDetailActivity.class, bundle);
                } else if (message.getType() == NoticeMultipleBean.TYPE_PROGRESS) {

                }
            }
        });
        noticeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMessageList(Constants.NETWORK_LOAD_MORE);
            }
        });
    }

    public void getMessageList(final String state) {
        LoadingDialog.showLoading(getActivity());
        if (Constants.NETWORK_REFRESH.equals(state))
            page = 1;
        else
            page++;
        LoadingDialog.showLoading(getActivity());
        HttpManager.getInstance().getMessageList(loginBean.getUser_id(), companyId, String.valueOf(page), String.valueOf(Constants.PAGE_SIZE), new HttpEngine.OnResponseCallback<HttpResponse.Message>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Message data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    ToastUtils.showShortSafe(retmsg);
                    if (Constants.NETWORK_LOAD_MORE.equals(state))
                        page--;
                }
                List<MessageBean> mList = data == null ? null : data.getData();
                final int size = mList == null ? 0 : mList.size();
                if (Constants.NETWORK_REFRESH.equals(state)) {
                    noticeAdapter.setEnableLoadMore(true);
                    if (result == 0) {
                        noticeAdapter.setNewData(mList);//刷新成功
                    }
                } else {
                    if (result == 0) {
                        noticeAdapter.addData(mList);//加载成功
                    } else {
                        noticeAdapter.loadMoreFail();
                    }
                }
                //不够一页
                if (size < Constants.PAGE_SIZE) {
                    noticeAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                } else {
                    noticeAdapter.loadMoreComplete();
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        getMessageList(Constants.NETWORK_REFRESH);
    }
}

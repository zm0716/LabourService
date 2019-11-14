package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.InspectionListAdapter;
import com.agilefinger.labourservice.adapter.SearchKanBanAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.agilefinger.labourservice.bean.KanBanSarchBean;
import com.agilefinger.labourservice.bean.TaskBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
 * 看板搜索
 * */
public class KanBanSearchActivity extends BaseActivity {

    @BindView(R.id.layout_toolbar_search_2_et_search)
    EditText etSearch;
    @BindView(R.id.activity_inspection_search_rv_list)
    RecyclerView rvList;
    private int page = 0;

    private SearchKanBanAdapter searchAdapter;
    private ImageView image_search;
    private String companyId;
    private String search;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kan_ban_search;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarSearch2("搜索项目名称");
        companyId = getIntent().getExtras().getString("company_id");
//        image_search = findViewById(R.id.image_search);
        initSwipeRefreshLayout();
        initRV();
    }

    private void initRV() {
        searchAdapter = new SearchKanBanAdapter(R.layout.item_zi_kanban, mList);

        searchAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                searchControl(Constants.NETWORK_LOAD_MORE);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(searchAdapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = etSearch.getText().toString().trim();
                    searchControl(Constants.NETWORK_REFRESH);
                    return true;
                }
                return false;
            }
        });

    }

    private List<KanBanSarchBean> mList;
    private List<KanBanSarchBean> mList2 = new ArrayList<>();

    private void searchControl(final String state) {
        if (Constants.NETWORK_REFRESH.equals(state))
            page = 0;
        else
            page += page+Constants.PAGE_SIZE;
        HttpManager.getInstance().getKBProjectSearch(page, Constants.PAGE_SIZE, companyId, search, loginBean.getUser_id(), new HttpEngine.OnResponseCallback<HttpResponse.KBSarchBean>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBSarchBean data) {
                if (result != 0) {
                    showToast(retmsg);
                    if (Constants.NETWORK_LOAD_MORE.equals(state))
                        page--;
                }

                //处理数据
                mList = data == null ? null : data.getData();
                final int size = mList == null ? 0 : mList.size();
                if (Constants.NETWORK_REFRESH.equals(state)) {
                    mList2.clear();
                    searchAdapter.setEnableLoadMore(true);
                    swipeRefreshLayout.setRefreshing(false);
                    if (result == 0) {
                        searchAdapter.setNewData(mList);//刷新成功
                        mList2.addAll(mList);
                    }
                } else {
                    if (result == 0) {
                        searchAdapter.addData(mList);//加载成功
                        mList2.addAll(mList);

                    } else {
                        searchAdapter.loadMoreFail();
                    }
                }
                //不够一页
                if (size < Constants.PAGE_SIZE) {
                    searchAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                } else {
                    searchAdapter.loadMoreComplete();
                }

                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.disDialog();
                    }
                });


            }
        });

    }

    @Override
    public void initListener() {
        super.initListener();
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("projectid", "" + mList2.get(position).getProject_id() + "");
                bundle.putString("projectname", mList2.get(position).getProject_title());

                bundle.putString("projectjl", mList2.get(position).getManager_name());
                bundle.putString("projectfw", mList2.get(position).getCompany_name());
                bundle.putString("startTime", mList2.get(position).getProject_work_start());

                IntentUtils.startActivity(KanBanSearchActivity.this, KanBanDetilsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void refresh() {
        searchAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        searchControl(Constants.NETWORK_REFRESH);
    }

}

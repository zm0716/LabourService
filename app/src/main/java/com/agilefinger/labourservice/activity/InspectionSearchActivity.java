package com.agilefinger.labourservice.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.agilefinger.labourservice.adapter.SearchAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.SearchBean;
import com.agilefinger.labourservice.bean.TaskBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class InspectionSearchActivity extends BaseActivity {
    @BindView(R.id.layout_toolbar_search_2_et_search)
    EditText etSearch;
    @BindView(R.id.activity_inspection_search_rv_list)
    RecyclerView rvList;

    //    private String companyID;
    private int page = 1;
    private int pageSize = 10;
    private SearchAdapter searchAdapter;
    private ImageView image_search;
    private String companyId;
    private InspectionListAdapter inspectionListAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_search;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarSearch2("搜索任务名称");
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        initSwipeRefreshLayout();
        initRV();
    }

    private String search;
    private List<TaskBean> mList;
    private List<TaskBean> mList2 = new ArrayList<>();

    private void initRV() {


        inspectionListAdapter = new InspectionListAdapter(R.layout.item_inspection, mList);
        inspectionListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getInspectionList(Constants.NETWORK_LOAD_MORE);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(inspectionListAdapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = etSearch.getText().toString().trim();
                    getInspectionList(Constants.NETWORK_REFRESH);
                    return true;
                }
                return false;
            }
        });
    }

    private void getInspectionList(final String state) {

        LoadingDialog.showLoading(this);
        if (Constants.NETWORK_REFRESH.equals(state))
            page = 1;
        else
            page++;
        //需要整理参数

        HttpManager.getInstance().search(search, companyId, loginBean.getUser_id(), String.valueOf(page),
                String.valueOf(Constants.PAGE_SIZE),
                new HttpEngine.OnResponseCallback<HttpResponse.Task>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Task data) {
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
                            inspectionListAdapter.setEnableLoadMore(true);
                            swipeRefreshLayout.setRefreshing(false);
                            if (result == 0) {
                                inspectionListAdapter.setNewData(mList);//刷新成功
                                mList2.addAll(mList);
                            }
                        } else {
                            if (result == 0) {
                                inspectionListAdapter.addData(mList);//加载成功
                                mList2.addAll(mList);

                            } else {
                                inspectionListAdapter.loadMoreFail();
                            }
                        }
                        //不够一页
                        if (size < Constants.PAGE_SIZE) {
                            inspectionListAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                        } else {
                            inspectionListAdapter.loadMoreComplete();
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

//        inspectionListAdapter.setEmptyView(emptySearchInspectionProjectView(rvList, "暂无搜索结果"));
//        rvList.setLayoutManager(new LinearLayoutManager(this));
//        rvList.setAdapter(inspectionListAdapter);

//    private void searchControl(String search) {
//        HttpManager.getInstance().search(search, companyID, page, pageSize, new HttpEngine.OnResponseCallback<HttpResponse.Search>() {
//            @Override
//            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Search data) {
//                if (result != 0) {
//                    showToast(retmsg);
//                    return;
//                }
//                final List<SearchBean> dataList = data.getData();
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InspectionSearchActivity.this);
//                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//                rvList.setLayoutManager(linearLayoutManager);
//                searchAdapter = new SearchAdapter(R.layout.item_inspection, dataList);
//                rvList.setAdapter(searchAdapter);
//                searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        Bundle bundle = new Bundle();
//                        String missionID = dataList
//                                .get(position).getMissionID();
//                        bundle.putString("taskId", missionID);
//                        bundle.putString("taskName", dataList.get(position).getProjectName());
//                        Log.i("vvvv", "" +missionID);
//                        IntentUtils.startActivity(InspectionSearchActivity.this, InspectionDetailActivity.class,bundle);
//                    }
//                });
//            }
//        });
//    }

    @Override
    public void initListener() {
        super.initListener();
       /* etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                       imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    content = etSearch.getText().toString().trim();
                    ToastUtils.showShortSafe("搜索中……");
                }
                return false;
            }
        });*/
    }
}

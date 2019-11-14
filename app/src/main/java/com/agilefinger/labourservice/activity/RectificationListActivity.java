package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ListDropDownAdapter;
import com.agilefinger.labourservice.adapter.RectificationAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ImagesBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RoleBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.db.RoleDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.widget.DropDownMenu;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 整改派单
 */
public class RectificationListActivity extends BaseActivity implements DropDownMenu.DropDownMenuCallback, RectificationAdapter.CallbackListener {

    @BindView(R.id.activity_rectification_list_ll_ddm_filter)
    DropDownMenu mDropDownMenu;
    RectificationAdapter rectificationAdapter;
    private String headers[] = {"全部", "全部", "项目"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter personAdapter, stateAdapter;
    private String person[] = {"全部", "我负责的", "我分派的"};
    private String state[] = {"全部", "整改中", "待验收", "已完成", "已忽略", "已撤回"};
    private String responsibilityFilter = "0", stateFilter = "0";//0全部 1我负责的 2我分派的 ~~~~~~ //0全部  1 整改中（默认）2待验收 3已完成 4已拒绝 5已撤销
    private String projectFilter = "", beginFilter = "", endFilter = "", zgManString = "", zpManString = "";
    private RecyclerView rvList;
    private LinearLayout llBottom;
    private List<StaffBean> zgManFilter, zpManFilter;
    private int page = 1;
    private String extraPerson, extraState, companyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_rectification_list;
    }

    @Override
    public void initData() {
        super.initData();
        if (!checkLogin()) finish();
        if (getIntent().getExtras() != null) {
            extraPerson = getIntent().getExtras().getString(Constants.EXTRA_DATA, "");
            extraState = getIntent().getExtras().getString(Constants.EXTRA_TYPE, "");
            companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
            String project = getIntent().getExtras().getString(Constants.EXTRA_DATA_PROJECT);
            if (project != null) {
                projectFilter = project;
            }

            String begin = getIntent().getExtras().getString(Constants.FILTER_BEGIN_TIME);//2019-07-11
            String end = getIntent().getExtras().getString(Constants.FILTER_END_TIME);//2019-08-11

            if (begin != null) {
                beginFilter = begin;
            }
            if (end != null) {
                endFilter = end;
            }
        }
    }


    @Override
    public void initView() {
        super.initView();
        setToolbar("整改派单", false, true, "筛选");
        initDropMenu();
        initSwipeRefreshLayout();
        initRV();
        initRole();
        if (Constants.STATE_ME_FUZE.equals(extraPerson)) {//我负责
            responsibilityFilter = "1";
            mDropDownMenu.setTabText(0, "我负责的");
        } else if (Constants.STATE_ME_FENPAI.equals(extraPerson)) {
            responsibilityFilter = "2";
            mDropDownMenu.setTabText(0, "我分派的");
        }
        if (!TextUtils.isEmpty(extraState)) {
            stateFilter = extraState;
            mDropDownMenu.setTabText(2, state[Integer.parseInt(stateFilter)]);
        }
    }

    private void initRole() {
        RoleDao roleDao = new RoleDao(LSApplication.context);
        List<RoleBean> roleList = roleDao.queryRoleAll();
        for (int i = 0; i < roleList.size(); i++) {
            if (Constants.AUTH_94.equals(roleList.get(i).getAuth())) {
                llBottom.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initDropMenu() {
        //init 谁负责的 menu
        ListView personView = new ListView(this);
        personAdapter = new ListDropDownAdapter(this, Arrays.asList(person));
        personView.setDividerHeight(0);
        personView.setAdapter(personAdapter);
        //init 状态 menu
        ListView stateView = new ListView(this);
        stateAdapter = new ListDropDownAdapter(this, Arrays.asList(state));
        stateView.setDividerHeight(0);
        stateView.setAdapter(stateAdapter);

        //init popupViews
        popupViews.add(personView);
        popupViews.add(stateView);

        //add item click event
        personView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(person[position]);
                mDropDownMenu.closeMenu();
                responsibilityFilter = position + "";
                getRectificationList(Constants.NETWORK_REFRESH);
            }
        });
        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateFilter = (position) + "";// (position + 1) + ""
                stateAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : state[position]);
                mDropDownMenu.closeMenu();

                getRectificationList(Constants.NETWORK_REFRESH);
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.layout_rectification_content, null);
        rvList = view.findViewById(R.id.layout_rectification_content_rv_list);
        llBottom = view.findViewById(R.id.layout_rectification_content_ll_bottom);
        view.findViewById(R.id.layout_rectification_content_rtv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(RectificationListActivity.this, AddRectificationActivity.class, bundle);
            }
        });
        view.findViewById(R.id.layout_rectification_content_rtv_draft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(RectificationListActivity.this, DraftListActivity.class, bundle);
            }
        });
        mDropDownMenu.setDropDownMenu(this, Arrays.asList(headers), popupViews, view, 1);
    }

    private void initRV() {
        rectificationAdapter = new RectificationAdapter(this);
        rectificationAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getRectificationList(Constants.NETWORK_LOAD_MORE);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(rectificationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRectificationList(Constants.NETWORK_REFRESH);
    }

    private void getRectificationList(final String state) {
        LoadingDialog.showLoading(this);
        if (Constants.NETWORK_REFRESH.equals(state))
            page = 1;
        else
            page++;
        HttpManager.getInstance().getRectificationList(loginBean.getUser_id(), companyId,
                responsibilityFilter, stateFilter, projectFilter,
                beginFilter, endFilter, zgManString, zpManString,
                String.valueOf(page), String.valueOf(Constants.PAGE_SIZE), new HttpEngine.OnResponseCallback<HttpResponse.Rectification>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Rectification data) {
                        LoadingDialog.disDialog();
                        if (result != 0) {
                            showToast(retmsg);
                            if (Constants.NETWORK_LOAD_MORE.equals(state))
                                page--;
                        }

                        Log.d("列表数据", GsonUtils.toJson(data));
                /*if (data == null) {
                    rectificationAdapter.setNewData(new ArrayList<RectificationBean>());
                    return;
                }*/
                        //处理数据
                        List<RectificationBean> mList = data == null ? null : data.getData();
                        if (mList != null) {
                            for (int i = 0; i < mList.size(); i++) {
                                List<ImagesBean> imagesList = new ArrayList<>();
                                if (mList.get(i).getImages() != null) {
                                    int size = mList.get(i).getImages().size() > 4 ? 4 : mList.get(i).getImages().size();
                                    for (int j = 0; j < size; j++) {
                                        ImagesBean imagesBean = new ImagesBean(mList.get(i).getImages().get(j), 0);
                                        imagesList.add(imagesBean);
                                    }
                                    if (mList.get(i).getImages().size() > 4) {
                                        imagesList.get(3).setNum(mList.get(i).getImages().size());
                                    }
                                }
                                mList.get(i).setImagesList(imagesList);
                            }
                        }
                        final int size = mList == null ? 0 : mList.size();
                        if (Constants.NETWORK_REFRESH.equals(state)) {
                            rectificationAdapter.setEnableLoadMore(true);
                            swipeRefreshLayout.setRefreshing(false);
                            if (result == 0) {
                                rectificationAdapter.setNewData(mList);//刷新成功
                            }
                        } else {
                            if (result == 0) {
                                rectificationAdapter.addData(mList);//加载成功
                            } else {
                                rectificationAdapter.loadMoreFail();
                            }
                        }
                        //不够一页
                        if (size < Constants.PAGE_SIZE) {
                            rectificationAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                        } else {
                            rectificationAdapter.loadMoreComplete();
                        }
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        rectificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RectificationBean rectification = (RectificationBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, rectification.getId());
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(RectificationListActivity.this, RectificationDetailActivity.class, bundle);
            }
        });


    }


    @Override
    public void callback(String... params) {
        mDropDownMenu.closeMenu();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) mProjectList);
        IntentUtils.startActivityForResult(this, ProjectSelectionActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT);
    }


    @Override
    public void callback2(String... params) {

    }

    @Override
    protected void toOperate() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FILTER_BEGIN_TIME, beginFilter);
        bundle.putString(Constants.FILTER_END_TIME, endFilter);
        bundle.putSerializable(Constants.FILTER_ZP_MAN, (Serializable) zpManFilter);
        bundle.putSerializable(Constants.FILTER_ZG_MAN, (Serializable) zgManFilter);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        IntentUtils.startActivityForResult(this, RectificationFilterActivity.class, bundle, Constants.REQUEST_FILTER);
    }

    @Override
    protected void refresh() {
        rectificationAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        getRectificationList(Constants.NETWORK_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_PROJECT:
                    List<ProjectBean> list = (List<ProjectBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showProject(list);
                    break;
                case Constants.REQUEST_FILTER:
                    zpManString = zgManString = "";
                    zpManFilter = (List<StaffBean>) data.getExtras().getSerializable(Constants.FILTER_ZP_MAN);
                    zgManFilter = (List<StaffBean>) data.getExtras().getSerializable(Constants.FILTER_ZG_MAN);
                    beginFilter = data.getExtras().getString(Constants.FILTER_BEGIN_TIME);//2019-07-11
                    endFilter = data.getExtras().getString(Constants.FILTER_END_TIME);//2019-08-11
                    if (TextUtils.isEmpty(beginFilter)) {
                        beginFilter = "";
                    }
                    if (TextUtils.isEmpty(endFilter)) {
                        endFilter = "";
                    }
                    if (zpManFilter != null) {
                        for (int i = 0; i < zpManFilter.size() - 1; i++) {
                            if (i == zpManFilter.size() - 1) {
                                zpManString += zpManFilter.get(i).getId();
                            } else {
                                zpManString += zpManFilter.get(i).getId() + ",";
                            }
                        }
                    }
                    if (zgManFilter != null) {
                        for (int i = 0; i < zgManFilter.size() - 1; i++) {
                            if (i == zgManFilter.size() - 1) {
                                zgManString += zgManFilter.get(i).getId();
                            } else {
                                zgManString += zgManFilter.get(i).getId() + ",";
                            }
                        }
                    }
                    getRectificationList(Constants.NETWORK_REFRESH);
                    break;
            }
        }
    }

    private List<ProjectBean> mProjectList;

    private void showProject(List<ProjectBean> list) {
        mProjectList = list;
        projectFilter = "";
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                projectFilter += list.get(i).getId();
            } else {
                projectFilter += list.get(i).getId() + ",";
            }
        }
        getRectificationList(Constants.NETWORK_REFRESH);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void todo(Object object, int position) {
        RectificationBean rectification = (RectificationBean) object;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA, rectification.getId());
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        IntentUtils.startActivity(RectificationListActivity.this, RectificationDetailActivity.class, bundle);
    }

    @Override
    public void todoLong(Object object, int position) {

    }
}

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.InspectionListAdapter;
import com.agilefinger.labourservice.adapter.ListDropDownAdapter;
import com.agilefinger.labourservice.adapter.RectificationAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RoleBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TaskBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.db.RoleDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.widget.DropDownMenu;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * 巡检任务
 */
public class InspectionListActivity extends BaseActivity implements DropDownMenu.DropDownMenuCallback, RectificationAdapter.CallbackListener {
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView ivOperate2;
    @BindView(R.id.activity_inspection_list_ll_ddm_filter)
    DropDownMenu mDropDownMenu;
    InspectionListAdapter inspectionListAdapter;
    private String headers[] = {"全部", "状态", "项目"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter personAdapter, stateAdapter;
    private String person[] = {"全部", "我创建的", "我执行的"};
    private String state[] = {"全部", "未启动", "检测中", "已完成", "已停止"};
    private String responsibilityFilter = "0", stateFilter = "0";//0全部 1我创建的 2我执行的 ~~~~~~ //0全部  1 未启动 2检测中 3已完成 4已停止
    private String projectFilter = "", beginFilter = "", endFilter = "", beginEndFilter = "", endEndFilter = "", zgManString = "", zpManString = "", miNo = "";
    private RecyclerView rvList;
    private LinearLayout llBottom;
    private List<StaffBean> zgManFilter, zpManFilter;
    private int page = 1;
    private String extraPerson, extraState, companyId;
    private List<TaskBean> mList;
    private List<TaskBean> mList2 = new ArrayList<>();
    private String userStatus = "";
    private String missionStatus = "";

    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_list;
    }

    @Override
    public void initData() {
        super.initData();
        //if (!checkLogin()) finish();
        if (getIntent().getExtras() != null) {
            extraPerson = getIntent().getExtras().getString(Constants.EXTRA_DATA, "");
            extraState = getIntent().getExtras().getString(Constants.EXTRA_TYPE, "");

            companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
            userStatus = getIntent().getExtras().getString("ischuang", "");
            String begin = getIntent().getExtras().getString(Constants.FILTER_BEGIN_END_TIME);
            String endEnd = getIntent().getExtras().getString(Constants.FILTER_END_END_TIME);

            String mci_no = getIntent().getExtras().getString("mci_no");
            if (mci_no != null) {
                miNo = mci_no;
            }
            String project = getIntent().getExtras().getString(Constants.EXTRA_DATA_PROJECT);
            if (project != null) {
                projectFilter = project;
            }

            if (begin != null) {
                beginFilter = begin;
            }
            if (endEnd != null) {
                endFilter = endEnd;
            }

            missionStatus = extraState;
        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("检测任务", false, true, "筛选");
        ivOperate2.setVisibility(View.VISIBLE);
        initDropMenu();
        initSwipeRefreshLayout();
        initRV();
        initRole();
        if (Constants.STATE_ME_CHUANGJIAN.equals(extraPerson)) {//我负责
            responsibilityFilter = "1";
            mDropDownMenu.setTabText(0, "我创建的");
        } else if (Constants.STATE_ME_ZHIXING.equals(extraPerson)) {
            responsibilityFilter = "2";
            mDropDownMenu.setTabText(0, "我执行的");
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
//                getInspectionList(Constants.NETWORK_REFRESH);
                //  Toast.makeText(InspectionListActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    userStatus = "";
                } else if (position == 1) {
                    userStatus = "2";
                } else if (position == 2) {
                    userStatus = "1";
                }

                getInspectionList(Constants.NETWORK_REFRESH);
            }
        });
        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : state[position]);
                mDropDownMenu.closeMenu();
                stateFilter = (position) + "";// (position + 1) + ""
                Log.d("当前下标值", position + "");
                missionStatus = "" + position + "";
                getInspectionList(Constants.NETWORK_REFRESH);
                // Toast.makeText(InspectionListActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                //  getInspectionList(Constants.NETWORK_LOAD_MORE);
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.layout_inspection_content, null);
        rvList = view.findViewById(R.id.layout_inspection_content_rv_list);
        llBottom = view.findViewById(R.id.layout_inspection_content_ll_bottom);
        //新增巡检任务
        view.findViewById(R.id.layout_inspection_content_rtv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(InspectionListActivity.this, CreateInspectionTaskActivity.class, bundle);
            }
        });
        mDropDownMenu.setDropDownMenu(this, Arrays.asList(headers), popupViews, view, 1);
    }

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInspectionList(Constants.NETWORK_REFRESH);
    }


    private void getInspectionList(final String state) {

        LoadingDialog.showLoading(InspectionListActivity.this);
        if (Constants.NETWORK_REFRESH.equals(state))
            page = 1;
        else
            page++;
        //需要整理参数

        Log.d("当前选中的", "::" + userStatus);
        HttpManager.getInstance().getTaskList(loginBean.getUser_id(), companyId,
                beginEndFilter, endEndFilter, zgManString, zpManString, userStatus,
                missionStatus, beginFilter, endFilter, projectFilter, String.valueOf(page),
                String.valueOf(Constants.PAGE_SIZE), projectFilter, miNo,
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

                /*if (mList != null) {
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
                }*/
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


    @Override
    public void initLoad() {
        super.initLoad();
        //inspectionListAdapter.setNewData(mList);
    }

    @Override
    public void initListener() {
        super.initListener();
        inspectionListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                String missionID = mList2.get(position).getMissionID();
                bundle.putString("taskId", missionID);
                bundle.putString("taskName", mList2.get(position).getProjectName());
                IntentUtils.startActivity(InspectionListActivity.this, InspectionDetailActivity.class, bundle);
            }
        });
    }

    @Override
    @OnClick({R.id.layout_toolbar_iv_operate_2})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_toolbar_iv_operate_2:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivityForResult(InspectionListActivity.this, InspectionSearchActivity.class, bundle, 1);
                break;
        }
    }

    /**
     * 搜索条件回调
     *
     * @param params
     */
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
        bundle.putString(Constants.FILTER_BEGIN_END_TIME, beginEndFilter);
        bundle.putString(Constants.FILTER_END_END_TIME, endEndFilter);
        bundle.putSerializable(Constants.FILTER_ZP_MAN, (Serializable) zpManFilter);
        bundle.putSerializable(Constants.FILTER_ZG_MAN, (Serializable) zgManFilter);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);

        Log.d("选中数据", beginFilter + "::" + endFilter + "::" + beginEndFilter + "::" + endEndFilter);

        IntentUtils.startActivityForResult(this, InspectionFilterActivity.class, bundle, Constants.REQUEST_FILTER);
    }

    @Override
    protected void refresh() {
        inspectionListAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        getInspectionList(Constants.NETWORK_REFRESH);
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
                    beginFilter = data.getExtras().getString(Constants.FILTER_BEGIN_TIME);
                    endFilter = data.getExtras().getString(Constants.FILTER_END_TIME);

                    beginEndFilter = data.getExtras().getString(Constants.FILTER_BEGIN_END_TIME);
                    endEndFilter = data.getExtras().getString(Constants.FILTER_END_END_TIME);
                    Log.d("接收数据", beginFilter + "::" + endFilter + "::" + beginEndFilter + "::" + endEndFilter);

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
                    getInspectionList(Constants.NETWORK_REFRESH);
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
        getInspectionList(Constants.NETWORK_REFRESH);
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
        IntentUtils.startActivity(InspectionListActivity.this, RectificationDetailActivity.class, bundle);
    }

    @Override
    public void todoLong(Object object, int position) {
    }
}

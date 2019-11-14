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
import com.agilefinger.labourservice.adapter.KanBanListAdapter;
import com.agilefinger.labourservice.adapter.ListDropDown2Adapter;
import com.agilefinger.labourservice.adapter.ListDropDownAdapter;
import com.agilefinger.labourservice.adapter.RectificationAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.agilefinger.labourservice.bean.KanBanProjectBoardBean;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RoleBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TypeBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.RoleDao;
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
import com.agilefinger.labourservice.widget.DropDownMenu;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class KanBanListActivity extends BaseActivity implements DropDownMenu.DropDownMenuCallback, RectificationAdapter.CallbackListener {
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView ivOperate2;
    @BindView(R.id.activity_kanban_list_ll_ddm_filter)
    DropDownMenu mDropDownMenu;
    private String headers[] = {"类型", "状态", "服务商"};
    private String person[] = {"类型", "涂料", "保温"};
    private String state[] = {"状态", "检测中", "未启动", "已停止", "已完成"};
    private String responsibilityFilter = "0", stateFilter = "0";//0全部 1我创建的 2我执行的 ~~~~~~ //0全部  1 未启动 2检测中 3已完成 4已停止
    private ListDropDown2Adapter personAdapter, stateAdapter;
    private List<View> popupViews = new ArrayList<>();
    private RecyclerView rvList;
    private LinearLayout llBottom;
    private List<StaffBean> kfManFilter, jlManFilter;
    private String companyId;
    private String projectFilter = "", beginFilter = "", endFilter = "", beginEndFilter = "", endEndFilter = "", kfManString = "", jlManString = "";
    private int page = 0;
    private String provinceName, cityModelName, districtName;
    private KanBanListAdapter kanBanListAdapter;
    private List<KanBanListBean.DataBean.ProjectDataBean> mList2 = new ArrayList<>();
    private List<KanBanListBean.DataBean.ProjectDataBean> mList;

    //看板状态分类
    private ArrayList<TypeBean> mEngineeringType = new ArrayList<>();
    private ArrayList<TypeBean> mProjectStatus = new ArrayList<>();

    private ArrayList<KanBanTypeBean> kanBanTypeBeanArrayList = new ArrayList<>();
    private ArrayList<KanBanTypeBean> kanBanTypeBeanArrayList2 = new ArrayList<>();
    private ArrayList<KanBanTypeBean> kanBanStatusBeanArrayList = new ArrayList<>();
    private ArrayList<KanBanTypeBean> kanBanStatusBeanArrayList2 = new ArrayList<>();

    @Override
    public int initLayoutView() {
        return R.layout.activity_kan_ban_list;
    }

    @Override
    public void initData() {
        super.initData();
        //if (!checkLogin()) finish();
        if (getIntent().getExtras() != null) {
            companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("项目看板", false, true, "筛选");
        ivOperate2.setVisibility(View.VISIBLE);

        initDropMenu();
        initSwipeRefreshLayout();
        initRV();
        initRole();
       /* if (Constants.STATE_ME_CHUANGJIAN.equals(extraPerson)) {//我负责
            responsibilityFilter = "1";
            mDropDownMenu.setTabText(0, "我创建的");
        } else if (Constants.STATE_ME_ZHIXING.equals(extraPerson)) {
            responsibilityFilter = "2";
            mDropDownMenu.setTabText(0, "我执行的");
        }
        if (!TextUtils.isEmpty(extraState)) {
            stateFilter = extraState;
            mDropDownMenu.setTabText(2, state[Integer.parseInt(stateFilter)]);
        }*/
    }

    private void initRole() {
        RoleDao roleDao = new RoleDao(LSApplication.context);
        List<RoleBean> roleList = roleDao.queryRoleAll();
        for (int i = 0; i < roleList.size(); i++) {
            if (Constants.AUTH_94.equals(roleList.get(i).getAuth())) {
                llBottom.setVisibility(View.GONE);
            }
        }
    }

//
//    private void getTypeData() {
//        HttpManager.getInstance().getKBProjectBoard(companyId, new HttpEngine.OnResponseCallback<HttpResponse.KBProjectBoardBean>() {
//            @Override
//            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBProjectBoardBean data) {
////                mEngineeringType = new ArrayList<>();
////                mProjectStatus = new ArrayList<>();
//                if (result == 0) {
//                    if (data != null) {
//                        List<KanBanProjectBoardBean.EngineeringTypeBean> engineeringType = data.getData().getEngineeringType();
//                        if (engineeringType != null) {
//
//                            TypeBean bean = new TypeBean(null, "全部");
//                            mEngineeringType.add(bean);
//                            mProjectStatus.add(bean);
//                            for (int i = 0; i < engineeringType.size(); i++) {
//                                TypeBean typeBean = new TypeBean(String.valueOf(engineeringType.get(i).getId()), engineeringType.get(i).getName());
//                                mEngineeringType.add(typeBean);
//                            }
//                        }
//
//
//                        List<KanBanProjectBoardBean.ProjectStatusBean> projectStatus = data.getData().getProjectStatus();
//                        if (projectStatus != null) {
//                            for (int i = 0; i < projectStatus.size(); i++) {
//                                TypeBean typeBean = new TypeBean(String.valueOf(projectStatus.get(i).getId()), projectStatus.get(i).getName());
//                                mProjectStatus.add(typeBean);
//                            }
//                        }
//                    }
//                }
//
//            }
//        });
//    }

    private void initDropMenu() {
        //init 谁负责的 menu
        ListView personView = new ListView(this);
        personAdapter = new ListDropDown2Adapter(this, mEngineeringType);
        personView.setDividerHeight(0);
        personView.setAdapter(personAdapter);
        //init 状态 menu
        ListView stateView = new ListView(this);
        stateAdapter = new ListDropDown2Adapter(this, mProjectStatus);
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
                mDropDownMenu.setTabText(mEngineeringType.get(position).getName());
                mDropDownMenu.closeMenu();
                engineering_type = mEngineeringType.get(position).getId();
                getInspectionList(Constants.NETWORK_REFRESH);
            }
        });
        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(mProjectStatus.get(position).getName());
                mDropDownMenu.closeMenu();
                custom_state_code = mProjectStatus.get(position).getCode() + "";// (position + 1) + ""
                getInspectionList(Constants.NETWORK_REFRESH);
            }
        });


        View view = LayoutInflater.from(this).inflate(R.layout.layout_kanban_content, null);
        rvList = view.findViewById(R.id.layout_kanban_content_rv_list);
        llBottom = view.findViewById(R.id.layout_kanban_content_ll_bottom);
        //新增项目
        view.findViewById(R.id.layout_kanban_content_rtv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(KanBanListActivity.this, CreatProjectActivity.class, bundle);
            }
        });
        mDropDownMenu.setDropDownMenu(this, Arrays.asList(headers), popupViews, view, 0);
    }


    private void initRV() {
        kanBanListAdapter = new KanBanListAdapter();
        kanBanListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getInspectionList(Constants.NETWORK_LOAD_MORE);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(kanBanListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getTypeData();
        getInspectionList(Constants.NETWORK_REFRESH);
    }

    //是否获取首页看板的状态
    private String other = "";
    //工程分类id拼成的字符串
    private String engineering_type = "";
    //项目经理id拼成的字符串
    private String manager_id = "";
    //状态码拼成的字符串
    private String custom_state_code = "";
    //开发商id拼成的字符串
    private String develop_id = "";
    //省的编码
    private String project_province = "";
    //市的编码
    private String project_city = "";
    //区的编码
    private String project_country = "";
    //开工时间搜索的开始时间
    private String start_time = "";
    //开工时间搜索的结束时间
    private String end_time = "";
    //服务商ID
    private String serviceID = "";

    private void getInspectionList(final String state) {
        LoadingDialog.showLoading(this);

        if (Constants.NETWORK_REFRESH.equals(state)) {
            other = "1";
            page = 0;
        } else {
            other = "2";
            page += page + Constants.PAGE_SIZE;
        }

        Map<String, Object> pram = new HashMap<>();
        pram.put("start", String.valueOf(page));
        pram.put("limit", String.valueOf(Constants.PAGE_SIZE));
        pram.put("companyID", companyId);
        pram.put("other", other);
        pram.put("engineering_type", engineering_type);
        pram.put("manager_id", manager_id);
        pram.put("custom_state_code", custom_state_code);
        pram.put("developer_id", develop_id);
        pram.put("project_province", project_province);
        pram.put("project_city", project_city);
        pram.put("project_country", project_country);
        pram.put("start_time", start_time);
        pram.put("end_time", end_time);
        pram.put("serviceID", serviceID);
        pram.put("user_id", loginBean.getUser_id());

        Log.d("项目看板传值", GsonUtils.toJson(pram));
        OkHttp3Util.doPost(URL.URL_PROJECT + "index/ProjectBoard/projectList", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.d("获取项目看板", string);
                Gson gson = new Gson();
                final KanBanListBean kanBanListBean = gson.fromJson(string, KanBanListBean.class);
                if (null != kanBanListBean) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (kanBanListBean.getCode() == 0) {
                                TypeBean bean = new TypeBean("", "全部", "");
                                List<KanBanListBean.DataBean.EngineeringTypeBean> engineeringType = kanBanListBean.getData().getEngineeringType();
                                if (!engineeringType.isEmpty() && engineeringType != null) {
                                    mEngineeringType.clear();
                                    kanBanTypeBeanArrayList.clear();
                                    mEngineeringType.add(bean);
                                    for (int i = 0; i < engineeringType.size(); i++) {
                                        TypeBean typeBean = new TypeBean(String.valueOf(engineeringType.get(i).getId()), engineeringType.get(i).getName(), null);
                                        mEngineeringType.add(typeBean);
                                        KanBanTypeBean kanBanTypeBean = new KanBanTypeBean(false, engineeringType.get(i).getName(), engineeringType.get(i).getCode());
                                        kanBanTypeBeanArrayList.add(kanBanTypeBean);
                                    }
                                    personAdapter.notifyDataSetChanged();
                                }
                                List<KanBanListBean.DataBean.ProjectStatusBean> projectStatus = kanBanListBean.getData().getProjectStatus();
                                if (!projectStatus.isEmpty() && projectStatus != null) {
                                    mProjectStatus.clear();
                                    kanBanStatusBeanArrayList.clear();
                                    mProjectStatus.add(bean);
                                    for (int i = 0; i < projectStatus.size(); i++) {
                                        TypeBean typeBean = new TypeBean(String.valueOf(projectStatus.get(i).getId()), projectStatus.get(i).getName(), projectStatus.get(i).getCode());
                                        mProjectStatus.add(typeBean);
                                        KanBanTypeBean kanBanTypeBean = new KanBanTypeBean(false, projectStatus.get(i).getName(), projectStatus.get(i).getCode());
                                        kanBanStatusBeanArrayList.add(kanBanTypeBean);
                                    }
                                    stateAdapter.notifyDataSetChanged();
                                }


                                mList = kanBanListBean.getData().getProjectData();

                                final int size = mList == null ? 0 : mList.size();
                                if (Constants.NETWORK_REFRESH.equals(state)) {
                                    mList2.clear();
                                    kanBanListAdapter.setEnableLoadMore(true);
                                    swipeRefreshLayout.setRefreshing(false);
                                    kanBanListAdapter.setNewData(mList);//刷新成功
                                    mList2.addAll(mList);

                                } else {
                                    kanBanListAdapter.addData(mList);//刷新成功
                                    mList2.addAll(mList);
                                }
                                //不够一页
                                if (size < Constants.PAGE_SIZE) {
                                    kanBanListAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                                } else {
                                    kanBanListAdapter.loadMoreComplete();
                                }
                            } else {
                                kanBanListAdapter.loadMoreFail();
                                MyToast.getInstands().toastShow(KanBanListActivity.this, kanBanListBean.getMessage() + "");
                            }
                        }
                    });

                }

            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        kanBanListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("projectid", "" + mList2.get(position).getProject_id() + "");
                bundle.putString("projectname", mList2.get(position).getProject_title());
//                String enName = mList2.get(position).getProject_en_name();
//                String enName = "";
//                for (int i = 0; i < en_name.size(); i++) {
//                    if (enName.equals("")) {
//                        enName = en_name.get(i);
//                    } else {
//                        enName = enName + "   " + en_name.get(i);
//                    }
//                }

//                bundle.putString("projecttype", enName);
//                bundle.putString("projectbh", mList2.get(position).getProject_number());
                bundle.putString("projectjl", mList2.get(position).getManager_name());
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString("projectfw", mList2.get(position).getCompany_name());
                bundle.putString("startTime", mList2.get(position).getProject_work_start());
//                mList2.get(position).getProject_work_start()
                //  bundle.putString("projectdate", mList2.get(position).g);

                IntentUtils.startActivity(KanBanListActivity.this, KanBanDetilsActivity.class, bundle);
            }
        });
    }

    //项目看板搜索
    @Override
    @OnClick({R.id.layout_toolbar_iv_operate_2})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_toolbar_iv_operate_2:
                Bundle bundle = new Bundle();
                bundle.putString("company_id", companyId);
                IntentUtils.startActivity(KanBanListActivity.this, KanBanSearchActivity.class, bundle);
                break;
        }
    }

    @Override
    public void callback(String... params) {
        mDropDownMenu.closeMenu();
        Bundle bundle = new Bundle();
        if (kanBanTypeBeanArrayList2.size() > 0) {
            bundle.putSerializable(Constants.EXTRA_DATA_TYPE, (Serializable) kanBanTypeBeanArrayList2);
        } else {
            bundle.putSerializable(Constants.EXTRA_DATA_TYPE, (Serializable) kanBanTypeBeanArrayList);
        }
        IntentUtils.startActivityForResult(this, KanBanTypeActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT_TYPE);

    }

//    @Override
//    public void callback1(String... params) {
//        mDropDownMenu.closeMenu();
//        Bundle bundle = new Bundle();
//        if (kanBanStatusBeanArrayList2.size() > 0) {
//            bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) kanBanStatusBeanArrayList2);
//        } else {
//            bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) kanBanStatusBeanArrayList);
//        }
//
//
//        IntentUtils.startActivityForResult(this, KanBanStatusActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT_STATUS);
//
//    }

    @Override
    public void callback2(String... params) {
        mDropDownMenu.closeMenu();
//        Log.e("AAAA",params[1]+params[2]);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        bundle.putString("type", "one");
//        bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) mProjectList);
        IntentUtils.startActivityForResult(this, KBFWSActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT);

    }

    @Override
    protected void toOperate() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FILTER_BEGIN_TIME, start_time);
        bundle.putString(Constants.FILTER_END_TIME, end_time);
        bundle.putString(Constants.FILTER_BEGIN_END_TIME, beginEndFilter);
        bundle.putString(Constants.FILTER_END_END_TIME, endEndFilter);
        bundle.putSerializable(Constants.FILTER_JL_MAN, (Serializable) jlManFilter);
        bundle.putSerializable(Constants.FILTER_KF_MAN, (Serializable) kfManFilter);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        bundle.putString(Constants.FILTER_PC_NAME, provinceName);
        bundle.putString(Constants.FILTER_CC_NAME, cityModelName);
        bundle.putString(Constants.FILTER_DC_NAME, districtName);
        bundle.putString(Constants.FILTER_PC, project_province);
        bundle.putString(Constants.FILTER_CC, project_city);
        bundle.putString(Constants.FILTER_DC, project_country);
        IntentUtils.startActivityForResult(this, KanBanFilterActivity.class, bundle, Constants.REQUEST_FILTER);
    }

    @Override
    protected void refresh() {
        kanBanListAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        getInspectionList(Constants.NETWORK_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_PROJECT:
                    List<ProjectBean> list = (List<ProjectBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA_TYPE);
                    showProject(list);
                    break;
                case Constants.REQUEST_CHOOSE_PROJECT_TYPE:
                    List<KanBanTypeBean> typelist = (List<KanBanTypeBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA_TYPE);
                    if (typelist != null) {
                        showProjectType(typelist);
                    }
                case Constants.REQUEST_CHOOSE_PROJECT_STATUS:
                    List<KanBanTypeBean> statuslist = (List<KanBanTypeBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA_STATUS);
                    if (statuslist != null) {
                        showProjectStatus(statuslist);
                    }
                    break;
                case Constants.REQUEST_FILTER:
                    manager_id = develop_id = "";
                    project_province = project_city = project_country = "";
                    jlManFilter = (List<StaffBean>) data.getExtras().getSerializable(Constants.FILTER_JL_MAN);
                    kfManFilter = (List<StaffBean>) data.getExtras().getSerializable(Constants.FILTER_KF_MAN);
                    start_time = data.getExtras().getString(Constants.FILTER_BEGIN_TIME);
                    end_time = data.getExtras().getString(Constants.FILTER_END_TIME);

                    project_province = data.getExtras().getString(Constants.FILTER_PC);
                    project_city = data.getExtras().getString(Constants.FILTER_CC);
                    project_country = data.getExtras().getString(Constants.FILTER_DC);
                    provinceName = data.getExtras().getString(Constants.FILTER_PC_NAME);
                    cityModelName = data.getExtras().getString(Constants.FILTER_CC_NAME);
                    districtName = data.getExtras().getString(Constants.FILTER_DC_NAME);
                    if (TextUtils.isEmpty(beginFilter)) {
                        beginFilter = "";
                    }
                    if (TextUtils.isEmpty(endFilter)) {
                        endFilter = "";
                    }
                    if (jlManFilter != null) {
                        for (int i = 0; i < jlManFilter.size() - 1; i++) {
                            if (i == jlManFilter.size() - 1) {
                                manager_id += jlManFilter.get(i).getId();
                            } else {
                                manager_id += jlManFilter.get(i).getId() + ",";
                            }
                        }
                    }
                    if (kfManFilter != null) {
                        for (int i = 0; i < kfManFilter.size() - 1; i++) {
                            if (i == kfManFilter.size() - 1) {
                                develop_id += kfManFilter.get(i).getId();
                            } else {
                                develop_id += kfManFilter.get(i).getId() + ",";
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

    List<KanBanTypeBean> projectType;

    private void showProjectType(List<KanBanTypeBean> list) {
//        projectType = list;
        engineering_type = "";
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                if (i == list.size() - 1) {
                    engineering_type += list.get(i).getCode();
                } else {
                    engineering_type += list.get(i).getCode() + ",";
                }
            }
        }
        getInspectionList(Constants.NETWORK_REFRESH);

        kanBanTypeBeanArrayList2.clear();
        for (int i = 0; i < list.size(); i++) {
            kanBanTypeBeanArrayList2.add(list.get(i));
        }
    }

    private List<KanBanTypeBean> projectStatus;

    private void showProjectStatus(List<KanBanTypeBean> list) {
//        projectStatus = list;
        custom_state_code = "";
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                if (i == list.size() - 1) {
                    custom_state_code += list.get(i).getCode();
                } else {
                    custom_state_code += list.get(i).getCode() + ",";
                }
            }
        }
        getInspectionList(Constants.NETWORK_REFRESH);
        kanBanStatusBeanArrayList2.clear();
        for (int i = 0; i < list.size(); i++) {
            kanBanStatusBeanArrayList2.add(list.get(i));
        }
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
        IntentUtils.startActivity(KanBanListActivity.this, RectificationDetailActivity.class, bundle);
    }

    @Override
    public void todoLong(Object object, int position) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        serviceID = event;
        getInspectionList(Constants.NETWORK_REFRESH);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}

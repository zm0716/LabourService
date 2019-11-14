package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ExpandableListViewAdapter;
import com.agilefinger.labourservice.adapter.FromeExpandableListViewAdapter;
import com.agilefinger.labourservice.adapter.KanBanMoreImageAdapter;
import com.agilefinger.labourservice.adapter.KanBanMoreImageOneAdapter;
import com.agilefinger.labourservice.adapter.KanBanMoreImageThreeAdapter;
import com.agilefinger.labourservice.adapter.KanBanMoreImageTwoAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildBean;
import com.agilefinger.labourservice.bean.BuildMoreImageBean;
import com.agilefinger.labourservice.bean.BuildingBean;
import com.agilefinger.labourservice.bean.BuindingFIllBean;
import com.agilefinger.labourservice.bean.DateImage2Bean;
import com.agilefinger.labourservice.bean.DateImageBean;
import com.agilefinger.labourservice.bean.FromDataImageBean;
import com.agilefinger.labourservice.bean.KanBanListBean;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.bean.LouHaoBean;
import com.agilefinger.labourservice.bean.MoreImgBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TypeBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.StickyItemDecoration;
import com.agilefinger.labourservice.utils.ThreadUtils;

import com.agilefinger.labourservice.view.custom.QExListView;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.gavin.com.library.listener.OnGroupClickListener;
import com.gavin.com.library.listener.PowerGroupListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MoreImgActivity extends BaseActivity {
    @BindView(R.id.m_rb_one)
    RadioButton mRbOne;
    @BindView(R.id.m_rb_two)
    RadioButton mRbTwo;
    @BindView(R.id.m_rb_three)
    RadioButton mRbThree;
    @BindView(R.id.m_rb_four)
    RadioButton mRbFour;
    @BindView(R.id.m_rg)
    RadioGroup mRg;
    @BindView(R.id.m_img_recy)
    RecyclerView mImgRecy;
    @BindView(R.id.m_img_recy2)
    QExListView mImgRecy2;
    @BindView(R.id.m_img_recy3)
    RecyclerView mImgRecy3;
    @BindView(R.id.m_img_recy4)
    QExListView mImgRecy4;
    @BindView(R.id.m_frame2)
    FrameLayout m_frame2;
    @BindView(R.id.m_frame4)
    FrameLayout m_frame4;
    @BindView(R.id.m_line_all)
    LinearLayout m_line_all;


    private String projectId;
    private KanBanMoreImageAdapter kanBanMoreImageAdapter;

    //时间
    private Set<String> set = new HashSet<>();
    //来源
    private Set<Integer> set1 = new HashSet<>();
    //楼好
    private Set<String> buildset = new HashSet<>();
    private List<MoreImgBean.DataBean.ListBean> timData2;
    private ArrayList<MoreImgBean.DataBean.ListBean> fromData2 = new ArrayList<>();
    private List<BuildMoreImageBean.DataBean.ListBean> buildingList;
    private List<BuildMoreImageBean.DataBean.ListBean> buildData2;
    private DateImageBean dateImageBean;
    private FromDataImageBean fromDataImageBean;
    private DateImage2Bean buildDataImageBean;
    private KanBanMoreImageTwoAdapter kanBanMoreImageTwoAdapter;
    private KanBanMoreImageOneAdapter kanBanMoreImageOneAdapter;
    private KanBanMoreImageThreeAdapter kanBanMoreImageThreeAdapter;
    private static Date parse;
    private boolean show = true;
    private boolean show2 = true;
    private PowerfulStickyDecoration decoration;
    private PowerfulStickyDecoration decoration2;

    private List<BuildMoreImageBean.DataBean.BuildingsBean> buildings;
    private List<BuildingBean> mStaffList;
    private String[] postion;
    private JSONArray jsonArray = new JSONArray();
    private String compyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_more_img;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("项目图片", false, true, "筛选");
        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        compyId = intent.getStringExtra(Constants.EXTRA_DATA_COMPANY);

        initSwipeRefreshLayout();
        initSwipeRefreshLayout2();
        initRV();
//        initRV1();
        initRV2();
//        initRV3();

        getData(Constants.NETWORK_REFRESH);


        fromImageBeanList.add(new FromDataImageBean("巡检任务", 1, fromData2));
        fromImageBeanList.add(new FromDataImageBean("整改派单", 2, fromData2));
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.m_rb_one:
                        mRbOne.setChecked(true);
                        mRbOne.setTextColor(getResources().getColor(R.color.white));

                        mRbThree.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbThree.setChecked(false);
                        mRbTwo.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbTwo.setChecked(false);
                        mRbFour.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbFour.setChecked(false);


                        sort = 0;

                        m_line_all.setVisibility(View.VISIBLE);
                        mImgRecy.setVisibility(View.VISIBLE);
                        m_frame2.setVisibility(View.GONE);
                        m_frame4.setVisibility(View.GONE);
                        mImgRecy2.setVisibility(View.GONE);
                        mImgRecy3.setVisibility(View.GONE);
                        mImgRecy4.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        swipeRefreshLayout2.setVisibility(View.GONE);
//                        initRV();
                        getData(Constants.NETWORK_REFRESH);

                        break;
                    case R.id.m_rb_two:
                        mRbOne.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbOne.setChecked(false);
                        mRbThree.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbThree.setChecked(false);
                        mRbFour.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbFour.setChecked(false);

                        mRbTwo.setChecked(true);
                        mRbTwo.setTextColor(getResources().getColor(R.color.white));

                        sort = 1;
                        m_line_all.setVisibility(View.GONE);
                        m_frame2.setVisibility(View.VISIBLE);
                        m_frame4.setVisibility(View.GONE);
                        mImgRecy2.setVisibility(View.VISIBLE);
                        mImgRecy.setVisibility(View.GONE);
                        mImgRecy3.setVisibility(View.GONE);
                        mImgRecy4.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayout2.setVisibility(View.GONE);
//                        initRV1();
                        getMorePicBySource();


                        break;
                    case R.id.m_rb_three:
                        mRbFour.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbFour.setChecked(false);
                        mRbTwo.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbTwo.setChecked(false);
                        mRbOne.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbOne.setChecked(false);

                        mRbThree.setChecked(true);
                        mRbThree.setTextColor(getResources().getColor(R.color.white));

                        sort = 2;
                        m_line_all.setVisibility(View.GONE);
                        m_frame2.setVisibility(View.GONE);
                        m_frame4.setVisibility(View.GONE);
                        mImgRecy2.setVisibility(View.GONE);
                        mImgRecy3.setVisibility(View.VISIBLE);
                        mImgRecy.setVisibility(View.GONE);
                        mImgRecy4.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayout2.setVisibility(View.VISIBLE);
//                        initRV2();

                        getData(Constants.NETWORK_REFRESH);
                        break;
                    case R.id.m_rb_four:
                        mRbThree.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbThree.setChecked(false);
                        mRbTwo.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbTwo.setChecked(false);
                        mRbOne.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbOne.setChecked(false);

                        mRbFour.setChecked(true);
                        mRbFour.setTextColor(getResources().getColor(R.color.white));
                        mImgRecy2.setVisibility(View.GONE);
                        m_frame2.setVisibility(View.GONE);
                        m_frame4.setVisibility(View.VISIBLE);
                        mImgRecy3.setVisibility(View.GONE);
                        mImgRecy4.setVisibility(View.VISIBLE);
                        mImgRecy.setVisibility(View.GONE);
                        m_line_all.setVisibility(View.GONE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayout2.setVisibility(View.GONE);

                        sort = 3;
                        getMorePicByBuilding();
                        break;
                }
            }
        });

    }

    private View inflate;

    //按时间
    private void initRV2() {
        PowerGroupListener groupListener = new PowerGroupListener() {

            @Override
            public View getGroupView(int position) {
                inflate = getLayoutInflater().inflate(R.layout.decoration_item, null, false);
                TextView textView = inflate.findViewById(R.id.m_text_title);
//                RelativeLayout m_time_rela = inflate.findViewById(R.id.m_time_rela);
//                String name;
                if (dateImageBeanList.size() > position) {
                    textView.setText(dateImageBeanList.get(position).getTime());
                }
                return inflate;
            }

            @Override
            public String getGroupName(int position) {
                if (dateImageBeanList.size() > position) {
                    return dateImageBeanList.get(position).getTime();
                }
                return null;
            }
        };
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder.init(groupListener).setGroupBackground(Color.parseColor("#FFFFFF")).setOnClickListener(new OnGroupClickListener() {
            @Override
            public void onClick(int position, int id) {
//                CheckBox viewById = inflate.findViewById(R.id.m_img);
//                RecyclerView viewByPosition = (RecyclerView) kanBanMoreImageTwoAdapter.getViewByPosition(mImgRecy3,position, R.id.m_more_two_recy);
//                if (viewById.isChecked()) {
//                    viewById.setChecked(false);
//
//
//                    viewByPosition.setVisibility(View.GONE);
//
//                } else {
//                    viewById.setChecked(true);
//                    viewByPosition.setVisibility(View.VISIBLE);
//                }
            }
        }).build();

        mImgRecy3.setLayoutManager(new LinearLayoutManager(this));
        mImgRecy3.addItemDecoration(decoration);

        kanBanMoreImageTwoAdapter = new KanBanMoreImageTwoAdapter();
        kanBanMoreImageTwoAdapter.setCompyId(compyId);
        kanBanMoreImageTwoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(Constants.NETWORK_LOAD_MORE);
            }
        });
        mImgRecy3.setAdapter(kanBanMoreImageTwoAdapter);

    }


    //默认
    private void initRV() {
        mImgRecy.setPadding(10, 0, 10, 0);
        mImgRecy.setLayoutManager(new GridLayoutManager(this, 3));
//        mImgRecy.addItemDecoration(null);
        kanBanMoreImageAdapter = new KanBanMoreImageAdapter();
        kanBanMoreImageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(Constants.NETWORK_LOAD_MORE);
            }
        });
        mImgRecy.setAdapter(kanBanMoreImageAdapter);


        kanBanMoreImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MoreImgBean.DataBean.ListBean item = (MoreImgBean.DataBean.ListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Bean", (Serializable) item);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, compyId);
                IntentUtils.startActivity(MoreImgActivity.this, BigImage2Activity.class, bundle);
            }
        });
    }

    private int page = 1;
    private String start_time = "";
    private String end_time = "";
    private int source = 0;
    private int sort = 0;
    private int page_size = 15;
    private List<MoreImgBean.DataBean.ListBean> mList;
    private List<MoreImgBean.DataBean.ListBean> mList2 = new ArrayList<>();
    private List<MoreImgBean.DataBean.ListBean> timData = new ArrayList<>();
    //时间
    private List<DateImageBean> dateImageBeanList = new ArrayList<>();
    //来源
    private List<FromDataImageBean> fromImageBeanList = new ArrayList<>();
    //楼号
    private List<LouHaoBean> buildImageBeanList = new ArrayList<>();
    private List<LouHaoBean> buildImageBeanList2 = new ArrayList<>();


    //默认排序
    private void getData(final String state) {
        LoadingDialog.showLoading(this);

        if (Constants.NETWORK_REFRESH.equals(state)) {
            page = 1;
        } else {
            page++;
        }
        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
        pram.put("page", page);
        pram.put("page_size", page_size);
        pram.put("source", source);
        pram.put("sort", 0);
        pram.put("start_time", start_time);
        pram.put("end_time", end_time);

        pram.put("position", jsonArray);


        OkHttp3Util.doPost(URL.NEW_URL + "api/project_board/projectMorePic", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.d("默认排序", string);
                Gson gson = new Gson();
                final MoreImgBean moreImgBean = gson.fromJson(string, MoreImgBean.class);
                if (null != moreImgBean) {
                    ThreadUtils.runOnMainThread(new Runnable() {

                        @Override
                        public void run() {
                            if (moreImgBean.getCode() == 0) {
                                mList = moreImgBean.getData().getList();

                                final int size = mList == null ? 0 : mList.size();
                                if (sort == 0) {
                                    if (Constants.NETWORK_REFRESH.equals(state)) {
                                        mList2.clear();
                                        kanBanMoreImageAdapter.setEnableLoadMore(true);
                                        swipeRefreshLayout.setRefreshing(false);
                                        kanBanMoreImageAdapter.setNewData(mList);//刷新成功
                                        mList2.addAll(mList);

                                    } else {
                                        kanBanMoreImageAdapter.addData(mList);//刷新成功
                                        mList2.addAll(mList);
                                    }
                                } else if (sort == 2) {
                                    if (Constants.NETWORK_REFRESH.equals(state)) {
//                                        timData.clear();
                                        kanBanMoreImageTwoAdapter.setEnableLoadMore(true);
                                        swipeRefreshLayout2.setRefreshing(false);
                                        timData = new ArrayList<>();
                                        timData.addAll(mList);
                                        getDataDate(timData);///刷新成功
                                    } else {
//                                        timData = new ArrayList<>();
                                        timData.addAll(mList);
                                        getDataDate(timData);// 刷新成功
                                    }
                                }

                                //不够一页
                                if (size < page_size) {
                                    kanBanMoreImageAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                                    kanBanMoreImageTwoAdapter.loadMoreEnd(Constants.NETWORK_REFRESH.equals(state));
                                } else {
                                    kanBanMoreImageAdapter.loadMoreComplete();
                                    kanBanMoreImageTwoAdapter.loadMoreComplete();
                                }
                            } else {
                                kanBanMoreImageAdapter.loadMoreFail();
                                kanBanMoreImageTwoAdapter.loadMoreFail();
                                MyToast.getInstands().toastShow(MoreImgActivity.this, moreImgBean.getMsg() + "");
                            }
                        }
                    });
                }
            }
        });

    }

    //按来源
    private void getMorePicBySource() {
        LoadingDialog.showLoading(this);

        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
        if (fromtype == 1) {
            pram.put("source", type);
        } else {
            pram.put("source", source);
        }
        pram.put("sort", 1);
        pram.put("start_time", start_time);
        pram.put("end_time", end_time);
        pram.put("position", jsonArray);


        OkHttp3Util.doPost(URL.NEW_URL + "api/project_board/projectMorePicBySource", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.d("按来源", string);
                Gson gson = new Gson();
                final MoreImgBean moreImgBean = gson.fromJson(string, MoreImgBean.class);
                if (null != moreImgBean) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreImgBean.getCode() == 0) {
                                mList = moreImgBean.getData().getList();
                                getMorePicBySourceDate(mList);
                            }
                        }
                    });
                }
            }
        });

    }

    private int fromIndex = 0;
    private int fromtype = 0;
    private int type;

    //按来源
    private void getMorePicBySourceDate(List<MoreImgBean.DataBean.ListBean> mList) {
        for (int i = 0; i < fromImageBeanList.size(); i++) {
            fromData2 = new ArrayList<>();
            for (int j = 0; j < mList.size(); j++) {
                if (mList.get(j).getType() == fromImageBeanList.get(i).getType()) {
                    fromData2.add(mList.get(j));
                }
            }
            fromImageBeanList.get(i).setDateBean(fromData2);
        }

        FromeExpandableListViewAdapter fromeExpandableListViewAdapter = new FromeExpandableListViewAdapter(fromImageBeanList, MoreImgActivity.this, compyId);
        mImgRecy2.setAdapter(fromeExpandableListViewAdapter);

        //打开分组
//        if (type.equals("Return")) {
//            mImgRecy2.expandGroup(source - 1, true);
//        } else {
        mImgRecy2.expandGroup(fromIndex, true);
//        }

        mImgRecy2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //如果分组被打开 直接关闭
                if (mImgRecy2.isGroupExpanded(groupPosition)) {
                    mImgRecy2.collapseGroup(groupPosition);
                } else {
                    fromtype = 1;
                    type = fromImageBeanList.get(groupPosition).getType();
                    fromIndex = groupPosition;
                    getMorePicBySource();
                }
                return true;
            }
        });

        mImgRecy2.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = mImgRecy2.getExpandableListAdapter().getGroupCount(); i < count; i++)
                    if (groupPosition != i) {// 关闭其他分组
                        mImgRecy2.collapseGroup(i);
                    }
            }
        });

    }

    //按日期排序
    private void getDataDate(List<MoreImgBean.DataBean.ListBean> mList) {
        dateImageBeanList = new ArrayList<>();
        set = new HashSet<>();
        for (int i = 0; i < mList.size(); i++) {
            set.add(getNowDateShort(mList.get(i).getTime()));
        }
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            timData2 = new ArrayList<>();
            String str = it.next();
            for (int i = 0; i < mList.size(); i++) {
                if (getNowDateShort(mList.get(i).getTime()).equals(str)) {
                    timData2.add(mList.get(i));
                    dateImageBean = new DateImageBean(str, 1, timData2);
                }
            }
            dateImageBeanList.add(dateImageBean);
        }
        kanBanMoreImageTwoAdapter.setNewData(dateImageBeanList);
    }


    /**
     * 修改时间格式
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getNowDateShort(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parse = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(parse);
        return dateString;
    }


    String building_id = "0";
    private int index;

    //按楼好
    private void getMorePicByBuilding() {
        LoadingDialog.showLoading(this);

        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
        pram.put("source", source);
        pram.put("sort", 3);
        pram.put("start_time", start_time);
        pram.put("end_time", end_time);
        pram.put("building_id", building_id);
        pram.put("position", jsonArray);


        OkHttp3Util.doPost(URL.NEW_URL + "api/project_board/projectMorePicByBuilding", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.d("按楼号", string);
                Gson gson = new Gson();
                final BuildMoreImageBean buildMoreImageBean = gson.fromJson(string, BuildMoreImageBean.class);
                if (null != buildMoreImageBean) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (buildMoreImageBean.getCode() == 0) {
                                buildings = buildMoreImageBean.getData().getBuildings();
                                buildingList = buildMoreImageBean.getData().getList();
                                getMorePicByBuildingDate(buildings, buildingList);
                            }
                        }
                    });
                }
            }
        });

    }

    //按楼好
    private void getMorePicByBuildingDate(List<BuildMoreImageBean.DataBean.BuildingsBean> buildings, List<BuildMoreImageBean.DataBean.ListBean> buildingList) {
        buildImageBeanList = new ArrayList<>();
        buildImageBeanList2 = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < buildings.size(); i++) {
                buildData2 = new ArrayList<>();
                for (int j = 0; j < buildingList.size(); j++) {
                    if (String.valueOf(buildings.get(i).getId()).equals(buildingList.get(j).getBuilding_id())) {
                        buildData2.add(buildingList.get(j));
                    }
                }
                LouHaoBean buildDataImageBean = new LouHaoBean(buildings.get(i).getBuilding_num(), String.valueOf(buildings.get(i).getId()), buildData2);
                buildImageBeanList2.add(buildDataImageBean);
            }

            for (int i = 0; i < buildImageBeanList2.size(); i++) {
                for (int j = 0; j < jsonArray.length(); j++) {
                    try {
                        JSONObject o = (JSONObject) jsonArray.get(j);
                        if (String.valueOf(buildImageBeanList2.get(i).getId()).equals(o.getString("building_id"))) {
                            buildImageBeanList.add(buildImageBeanList2.get(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {

            for (int i = 0; i < buildings.size(); i++) {
                buildData2 = new ArrayList<>();
                for (int j = 0; j < buildingList.size(); j++) {
                    if (String.valueOf(buildings.get(i).getId()).equals(buildingList.get(j).getBuilding_id())) {
                        buildData2.add(buildingList.get(j));
                    }
                }
                LouHaoBean buildDataImageBean = new LouHaoBean(buildings.get(i).getBuilding_num(), String.valueOf(buildings.get(i).getId()), buildData2);
                buildImageBeanList.add(buildDataImageBean);
            }

        }
        //#TODO 去掉自带箭头，在一级列表中动态添加
        mImgRecy4.setGroupIndicator(null);

        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(buildImageBeanList, MoreImgActivity.this, compyId);
//        mImgRecy4.setHeaderView(getLayoutInflater().inflate(R.layout.item_header_msg_listview,mImgRecy4, false));
        mImgRecy4.setAdapter(expandableListViewAdapter);
        //打开分组
        mImgRecy4.expandGroup(index, true);

        mImgRecy4.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //如果分组被打开 直接关闭
                if (mImgRecy4.isGroupExpanded(groupPosition)) {
                    mImgRecy4.collapseGroup(groupPosition);
                } else {
                    building_id = buildImageBeanList.get(groupPosition).getId();
                    index = groupPosition;
                    getMorePicByBuilding();
                }
                return true;
            }
        });

        mImgRecy4.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = mImgRecy4.getExpandableListAdapter().getGroupCount(); i < count; i++)
                    if (groupPosition != i) {// 关闭其他分组
                        mImgRecy4.collapseGroup(i);
                    }
            }
        });


    }

    @Override
    protected void refresh() {
        kanBanMoreImageAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        getData(Constants.NETWORK_REFRESH);
    }

    @Override
    protected void refresh2() {
        kanBanMoreImageTwoAdapter.setEnableLoadMore(false);//刷新的时候不能加载更多
        getData(Constants.NETWORK_REFRESH);
    }

    @Override
    protected void toOperate() {
        super.toOperate();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", projectId);
        bundle.putInt(Constants.FILTER_SOURCE, source);
        bundle.putString(Constants.FILTER_END_TIME, end_time);
        bundle.putString(Constants.FILTER_BEGIN_TIME, start_time);
        bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) mStaffList);
        IntentUtils.startActivityForResult(MoreImgActivity.this, ImageViwFillActivity.class, bundle, Constants.REQUEST_FILTER);
    }

//    String type = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_FILTER:
//                    type = "Return";
                    fromtype = 0;
                    source = data.getExtras().getInt(Constants.FILTER_SOURCE);
                    start_time = data.getExtras().getString(Constants.FILTER_BEGIN_TIME);
                    end_time = data.getExtras().getString(Constants.FILTER_END_TIME);
                    mStaffList = (List<BuildingBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA_);

                    ArrayList<BuindingFIllBean> mlist = new ArrayList<>();
                    for (int i = 0; i < mStaffList.size(); i++) {
                        if (mStaffList.get(i).isChecked()) {
                            BuindingFIllBean buindingFIllBean = new BuindingFIllBean(mStaffList.get(i).getBuilding_id(), mStaffList.get(i).getFloor_id(), mStaffList.get(i).getInside_id());
                            mlist.add(buindingFIllBean);
                        }
                    }
                    jsonArray = new JSONArray();
                    try {
                        for (int i = 0; i < mlist.size(); i++) {
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("building_id", mlist.get(i).getBuilding_id());
                            jsonObject.put("floor_id", mlist.get(i).getFloor_id());
                            jsonObject.put("inside_id", mlist.get(i).getInside_id());
                            jsonArray.put(jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    index = 0;
                    fromIndex = 0;

                    getData(Constants.NETWORK_REFRESH);
                    getMorePicByBuilding();
                    getMorePicBySource();

                    break;
            }
        }
    }


}

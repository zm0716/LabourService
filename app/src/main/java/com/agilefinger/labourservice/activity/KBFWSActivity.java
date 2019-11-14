package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ClassTextAdapter;
import com.agilefinger.labourservice.adapter.SerViceAdapter;
import com.agilefinger.labourservice.adapter.Staff2Adapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.KBService2Bean;
import com.agilefinger.labourservice.bean.KBServiceBean;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.bean.ServiceBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.ActivityTaskManager;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/*
 * 看板服务商
 * */
public class KBFWSActivity extends BaseActivity {

    @BindView(R.id.layout_toolbar_iv_back)
    ImageView layoutToolbarIvBack;
    @BindView(R.id.layout_toolbar_tv_title)
    TextView layoutToolbarTvTitle;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView layoutToolbarIvOperate;
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView layoutToolbarIvOperate2;
    @BindView(R.id.layout_toolbar_tv_operate)
    TextView layoutToolbarTvOperate;
    @BindView(R.id.layout_toolbar_ll_wrapper)
    LinearLayout layoutToolbarLlWrapper;
    @BindView(R.id.m_search)
    TextView mSearch;
    @BindView(R.id.m_line)
    LinearLayout mLine;
    @BindView(R.id.m_fu_recy)
    RecyclerView mFuRecy;
    @BindView(R.id.m_ok)
    RoundTextView mOk;
    @BindView(R.id.m_text_class)
    RecyclerView m_text_class;
    private String company_id = "0";
    private String type;
    private List<KBServiceBean> mStaffList;
    SerViceAdapter serViceAdapter;
    private String classText;
    private String text;
    public static String serviceID;
    public static HashMap<Integer, String> map = new HashMap<>();

    public static ArrayList<String> classList = new ArrayList<>();
    public ArrayList<String> classListTwo = new ArrayList<>();
    private ClassTextAdapter classTextAdapter;
    private int index;

    public static ArrayList<KBServiceBean> data1;
    public static List<KBServiceBean> kbServiceBeanList;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kbfws;
    }

    @Override
    public void initView() {
        super.initView();
        company_id = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        type = getIntent().getExtras().getString("type");
        classText = getIntent().getExtras().getString("classText");
        index = getIntent().getExtras().getInt("index");
        if (classText != null) {
            text = classText;
        }
        mStaffList = (List<KBServiceBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
        setToolbar2("选择服务商", false, false, "");
        initRV();

    }

    @Override
    protected void back2() {
        super.back2();
//        if (classList.size() > index) {
            classList.remove(index);
//        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                classList.remove(index);
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initLoad() {
        super.initLoad();
        if (type.equals("one")) {
            kbServiceBeanList = new ArrayList<>();
            mStaffList = new ArrayList<>();
            map = new HashMap<>();
            getData();
            serviceID = "";
            classList = new ArrayList<>();
            classListTwo = new ArrayList<>();
            data1 = new ArrayList<>();
            mlist = new ArrayList<>();

            index = 0;
            classList.add("首级");
            ActivityTaskManager.getInstance().putActivity(index, this);
        } else {
            index++;
            ActivityTaskManager.getInstance().putActivity(index, this);
            m_text_class.setVisibility(View.VISIBLE);
            classList.add(classText);
            classListTwo.clear();
            for (int i = 0; i < classList.size(); i++) {
                if (i == classList.size() - 1) {
                    classListTwo.add("<font color='#333333'>" + classList.get(i) + "</font");
                } else {
                    classListTwo.add("<font color='#349fff'>" + classList.get(i) + "</font>" + "/");
                }
            }
            classTextAdapter.setNewData(classListTwo);
//            m_text_class.setText(Html.fromHtml(text));
            serViceAdapter.setNewData(mStaffList);
            if (kbServiceBeanList != null && kbServiceBeanList.size() > 0) {
                getNewData(kbServiceBeanList);
            }
        }
    }

    private void initRV() {
        serViceAdapter = new SerViceAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(mFuRecy);
        mFuRecy.setLayoutManager(new LinearLayoutManager(this));
        mFuRecy.setNestedScrollingEnabled(false);
        mFuRecy.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        mFuRecy.setAdapter(serViceAdapter);


        //指定列表线性布局，横向水平
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        m_text_class.setLayoutManager(lm);

        classTextAdapter = new ClassTextAdapter();
        m_text_class.setAdapter(classTextAdapter);

    }


    public void getData() {
        HttpManager.getInstance().getServiceList(company_id, "", new HttpEngine.OnResponseCallback<HttpResponse.KBServicData>() {

            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBServicData data) {
                if (result == 0) {
//                    final List<ServiceBean> mList = new ArrayList<>();
//                    mList.clear();
                    data1 = data.getData();
                    getArrayList(data1);
//                    for (int i = 0; i < data1.size(); i++) {
//                        ServiceBean serviceBean = new ServiceBean();
//                        serviceBean.setId(String.valueOf(data1.get(i).getId()));
//                        serviceBean.setName(data1.get(i).getName());
//
//                        ArrayList<KBService2Bean> service2BeanArrayList = new ArrayList<>();
//                        if (data1.get(i).getSubCompany() != null && data1.get(i).getSubCompany().size() > 0) {
//                            for (int j = 0; j < data1.get(i).getSubCompany().size(); j++) {
//                                KBService2Bean kbService2Bean = new KBService2Bean();
//                                kbService2Bean.setId(data1.get(i).getSubCompany().get(j).getId());
//                                kbService2Bean.setName(data1.get(i).getSubCompany().get(j).getName());
//                                service2BeanArrayList.add(kbService2Bean);
//                                serviceBean.setMlist(service2BeanArrayList);
//                            }
//                        }
//                        mList.add(serviceBean);
//                    }
//                    if (mStaffList != null && mList != null) {
//                        if (mStaffList.size() > 0) {
//                            mStaffList.remove(mStaffList.size() - 1);
//                        }
//                        if (mStaffList.size() <= mList.size()) {
//                            for (int i = 0; i < mStaffList.size(); i++) {
//                                for (int j = 0; j < mList.size(); j++) {
//                                    if (!TextUtils.isEmpty(mStaffList.get(i).getId()) && mStaffList.get(i).getId().equals(mList.get(j).getId())) {
//                                        mList.get(j).setCheck(true);
//                                    }
//                                }
//                            }
//                        }
//                    }
                    for (int i = 0; i < data1.size(); i++) {
                        mStaffList.add(data1.get(i));
                    }

                    serViceAdapter.setNewData(mStaffList);
//                    staffAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                        }
//                    });

                }
            }
        });
    }

    private int curPosition = -1;
    private int posti;

    @Override
    public void initListener() {
        super.initListener();
        serViceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KBServiceBean serviceBean = (KBServiceBean) adapter.getItem(position);
                List<KBServiceBean> subCompany = serviceBean.getSubCompany();
//                List<KBServiceBean.SubCompanyBean> mlist = serviceBean.getSubCompany();
                if (subCompany != null && subCompany.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) subCompany);
//                    String s = "<font color='#349fff'>" + text + "</font>" + "/" + "<font color='#999999'>"+ serviceBean.getName() + "</font>";
                    bundle.putString("classText", serviceBean.getName());
                    bundle.putString("type", "two");
                    bundle.putInt("index", index);
                    IntentUtils.startActivity(KBFWSActivity.this, KBFWSActivity.class, bundle);
                }
            }
        });

        serViceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                KBServiceBean staff = (KBServiceBean) adapter.getItem(position);
                if (!Constants.CHOOSE_TYPE_SINGLE.equals("SINGLE")) {//单选
                    if (curPosition != position && curPosition != -1) {
                        KBServiceBean oldStaff = (KBServiceBean) adapter.getItem(curPosition);
                        oldStaff.setCheck(false);
                        serViceAdapter.notifyItemChanged(curPosition);
                    }
                    staff.setCheck(true);
                    curPosition = position;

                } else {//多选
                    staff.setCheck(!staff.isCheck());
                    if (staff.isCheck() == true) {
//                        serviceID += String.valueOf(staff.getId()) + ",";
                        map.put(staff.getId(), String.valueOf(staff.getId()));

                    } else {

                        if (map != null) {
                            map.remove(staff.getId());
                        }

                        //避免在进入导致再次选中
                        if (kbServiceBeanList != null && kbServiceBeanList.size() > 0) {
                            for (int i = 0; i < kbServiceBeanList.size(); i++) {
                                if (kbServiceBeanList.get(i).getId() == staff.getId()) {
                                    kbServiceBeanList.remove(i);
                                }
                            }
                        }
                    }
                }
                serViceAdapter.notifyItemChanged(position);
//                showChooseNum();
            }
        });

        classTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                position = posti;
                if (position == 0) {
                    classList.clear();
                    classList.add("首级");
                    int i1 = position + 1;
                    for (int i = i1; i <= index; i++) {
                        ActivityTaskManager.getInstance().removeActivity(i);
                    }
                } else {
                    int i1 = position + 1;
                    for (int i = i1; i <= index; i++) {
                        if (classList.size() > position + 1) {
                            classList.remove(position + 1);
                            ActivityTaskManager.getInstance().removeActivity(i);
                        }
//                        i1++;
                    }

                }

            }
        });
    }


    private void showChooseNum() {
        int count = 0;
        List<KBServiceBean> pList = serViceAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        mOk.setText(txt);
    }

    @OnClick({R.id.m_ok, R.id.m_search})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.m_ok:
                if (kbServiceBeanList != null && kbServiceBeanList.size() > 0) {
                    for (int i = 0; i < kbServiceBeanList.size(); i++) {
                        if (kbServiceBeanList.get(i).isCheck()) {
                            map.put(kbServiceBeanList.get(i).getId(), String.valueOf(kbServiceBeanList.get(i).getId()));
                        }
                    }
                }
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    serviceID += entry.getValue() + ",";
                }
                EventBus.getDefault().post(serviceID);
                ActivityTaskManager.getInstance().closeAllActivity();
                break;
            case R.id.m_search:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA_COMPANY, mlist);
                IntentUtils.startActivityForResult(this, KBFWSearchActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT);
                Log.e("AAA", mlist.size() + "");
                break;
        }
    }


    public static ArrayList<KBServiceBean> mlist = new ArrayList<>();

    private void getArrayList(ArrayList<KBServiceBean> data1) {
        for (int i = 0; i < data1.size(); i++) {
            mlist.add(data1.get(i));
            if (data1.get(i).getSubCompany() != null && data1.get(i).getSubCompany().size() > 0) {
                List<KBServiceBean> subCompany = data1.get(i).getSubCompany();
                getArrayList((ArrayList<KBServiceBean>) subCompany);
            }
        }
        Log.e("AAA", mlist.size() + "");
    }

    private void getNewData(List<KBServiceBean> kbServiceBeanList) {
        for (int i = 0; i < mStaffList.size(); i++) {
            for (int j = 0; j < kbServiceBeanList.size(); j++) {
                if (mStaffList.get(i).getName().endsWith(kbServiceBeanList.get(j).getName())) {
                    mStaffList.get(i).setCheck(kbServiceBeanList.get(j).isCheck());
                    serViceAdapter.notifyItemChanged(i);
                }
            }
        }
//        serViceAdapter.setNewData(mStaffList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ArrayList<KBServiceBean> mSearchList) {
        for (int i = 0; i < mStaffList.size(); i++) {
            for (int j = 0; j < mSearchList.size(); j++) {
                if (mSearchList.get(j).getId() == mStaffList.get(i).getId()) {
                    mStaffList.get(i).setCheck(mSearchList.get(j).isCheck());
                }
            }
        }
        serViceAdapter.setNewData(mStaffList);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_PROJECT:
                    kbServiceBeanList = (List<KBServiceBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    getNewData(kbServiceBeanList);
                    break;
            }
        }
    }
}

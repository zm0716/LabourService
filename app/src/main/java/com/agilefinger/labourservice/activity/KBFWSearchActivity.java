package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.SerViceAdapter;
import com.agilefinger.labourservice.adapter.SerViceSearchAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.KBServiceBean;
import com.agilefinger.labourservice.bean.MessageBean;
import com.agilefinger.labourservice.bean.SonAndItemsBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class KBFWSearchActivity extends BaseActivity {


    @BindView(R.id.m_search)
    EditText mSearch;
    @BindView(R.id.m_fu_recy)
    RecyclerView mFuRecy;
    @BindView(R.id.m_ok)
    RoundTextView mOk;
    private ArrayList<KBServiceBean> mlist;
    private ArrayList<KBServiceBean> mSearchList = new ArrayList<>();
    SerViceSearchAdapter serViceAdapter;


    @Override
    public int initLayoutView() {
        return R.layout.activity_kbfwsearch;
    }


    @Override
    public void initView() {
        super.initView();
        setToolbar("搜索", false, false, "");
        mlist = (ArrayList<KBServiceBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_COMPANY);
        initRV();


        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = mSearch.getText().toString().trim();
                    searchControl(search);
                    return true;
                }
                return false;
            }
        });

    }

    private void initRV() {
        serViceAdapter = new SerViceSearchAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(mFuRecy);
        mFuRecy.setLayoutManager(new LinearLayoutManager(this));
        mFuRecy.setNestedScrollingEnabled(false);
        mFuRecy.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        mFuRecy.setAdapter(serViceAdapter);
    }


    private void searchControl(String search) {
        mSearchList = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            if (mlist.get(i).getName().contains(search)) {
                mSearchList.add(mlist.get(i));
            }
        }
        serViceAdapter.setNewData(mSearchList);

    }

    @Override
    public void initListener() {
        super.initListener();
        serViceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                KBServiceBean staff = (KBServiceBean) adapter.getItem(position);
                if (!Constants.CHOOSE_TYPE_SINGLE.equals("SINGLE")) {//单选
//                    if (curPosition != position && curPosition != -1) {
//                        KBServiceBean oldStaff = (KBServiceBean) adapter.getItem(curPosition);
//                        oldStaff.setCheck(false);
//                        serViceAdapter.notifyItemChanged(curPosition);
//                    }
//                    staff.setCheck(true);
//                    curPosition = position;

                } else {//多选
                    staff.setCheck(!staff.isCheck());
//                    MessageBean messageBean = new MessageBean();
//                    messageBean.setId(String.valueOf(staff.getId()));

//                    Class clazz = null;
//                    try {
//                        clazz = Class.forName("com.agilefinger.labourservice.bean.KBServiceBean");
//                        Object o = null;
//                        try {
//                            o = clazz.newInstance();
//                            Field field_name = null;
//                            try {
//                                field_name = clazz.getDeclaredField("isCheck");
//                                field_name.setAccessible(true);
//                                field_name.set(o, true);
//                            } catch (NoSuchFieldException e) {
//                                e.printStackTrace();
//                            }
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InstantiationException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }

//                    try {
//                        Class<? extends KBServiceBean> aClass = staff.getClass();
//                        Field isCheck = aClass.getDeclaredField("isCheck");
//                        try {
//                            isCheck.setAccessible(true);
//                            isCheck.set(staff, staff.isCheck());
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    }
//
                }
                serViceAdapter.notifyItemChanged(position);
//                showChooseNum();
            }
        });
    }

    @OnClick({R.id.m_ok})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.m_ok:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA, mSearchList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                EventBus.getDefault().post(mSearchList);
                finish();
                break;
        }
    }
}

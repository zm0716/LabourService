package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.KanBanListTypeAdapter;
import com.agilefinger.labourservice.adapter.ProjectImgAdaptere;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.DetliaTopBean;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.fragment.CreateKanBanDatilsFiveFragment;
import com.agilefinger.labourservice.fragment.CreateKanBanDatilsFourFragment;
import com.agilefinger.labourservice.fragment.CreateKanBanDatilsOneFragment;
import com.agilefinger.labourservice.fragment.CreateKanBanDatilsTwoFragment;
import com.agilefinger.labourservice.fragment.CreateKanBanDetailsThreeFragment;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.flyco.roundview.RoundTextView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KanBanDetilsActivity extends BaseActivity {

    //    @BindView(R.id.recyclerview_kanban)
//    RecyclerView recyclerviewKanban;
    @BindView(R.id.framelayout_details)
    FrameLayout framelayoutDetails;
    @BindView(R.id.redio_one)
    RadioButton redioOne;
    @BindView(R.id.redio_two)
    RadioButton redioTwo;
    @BindView(R.id.redio_three)
    RadioButton redioThree;
    @BindView(R.id.redio_four)
    RadioButton redioFour;
    @BindView(R.id.redio_five)
    RadioButton redioFive;
    @BindView(R.id.redio_six)
    RadioButton redioSix;
    @BindView(R.id.rediogroup)
    RadioGroup rediogroup;

    @BindView(R.id.item_rectification_tv_title)
    TextView item_rectification_tv_title;

    @BindView(R.id.item_rectification_tv_deadline)
    TextView item_rectification_tv_deadline;

    @BindView(R.id.bianhao_tv)
    TextView bianhao_tv;
    @BindView(R.id.jingli_tv)
    TextView jingli_tv;
    @BindView(R.id.gongsi_tv)
    TextView gongsi_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.m_recy)
    RecyclerView m_recy;
    @BindView(R.id.item_rectification_rtv_state)
    RoundTextView item_rectification_rtv_state;


    private CreateKanBanDatilsOneFragment createKanBanDatilsOneFragment;
    private CreateKanBanDatilsTwoFragment createKanBanDatilsTwoFragment;
    private CreateKanBanDetailsThreeFragment createKanBanDetailsThreeFragment;
    private CreateKanBanDatilsFourFragment createKanBanDatilsFourFragment;
    private CreateKanBanDatilsFiveFragment createKanBanDatilsFiveFragment;
    private String projectId;
    private String projectName;
    private String startTime;
    private static Date parse;
    private String companyId;
    private KanBanListTypeAdapter kanBanListTypeAdapter;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kan_ban_detils;
    }


    @Override
    public void initView() {
        super.initView();

        Bundle bundle = getIntent().getExtras();
        projectId = bundle.getString("projectid");
        projectName = bundle.getString("projectname");
        startTime = bundle.getString("startTime");
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        setToolbar(projectName, false, false, "");
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        m_recy.setLayoutManager(manager);

        kanBanListTypeAdapter = new KanBanListTypeAdapter();
        m_recy.setAdapter(kanBanListTypeAdapter);

        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getDetailsData(projectId, "", "", "", "", new HttpEngine.OnResponseCallback<HttpResponse.KanBanDetailBeanData>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KanBanDetailBeanData data) {
                LoadingDialog.disDialog();
                if (result == 0) {
                    if (data != null) {
                        KanBanDetailBean.DetailsBean details = data.getData().getDetails();
                        if (details != null) {
                            if (details.getProject_title() != null) {
                                item_rectification_tv_title.setText(details.getProject_title());
                            }
//                        item_rectification_tv_deadline.setText(data1.getEn_name());
                            ArrayList<String> mlist = new ArrayList<>();
                            List<KanBanDetailBean.DetailsBean.ProjectTypeBean> project_type = details.getProject_type();
                            for (int i = 0; i < project_type.size(); i++) {
                                mlist.add(project_type.get(i).getEn_name());
                            }
                            kanBanListTypeAdapter.setNewData(mlist);

                            bianhao_tv.setText(details.getProject_number());
                            jingli_tv.setText(details.getProject_member());
//                        String nowDateShort = getNowDateShort(details.getProject_work_start());
                            date_tv.setText(details.getProject_work_start());
                            gongsi_tv.setText(details.getCompany_name());
                            item_rectification_rtv_state.setText(details.getCustom_state_name());
                        }
                    }
                }
            }
//            }
        });

//        HttpManager.getInstance().getKBdetailTop(projectId, new HttpEngine.OnResponseCallback<HttpResponse.KBDetliaTopData>() {
//            @Override
//            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBDetliaTopData data) {
//                if (result == 0) {
//                    if (data.getData() != null) {
//                        DetliaTopBean data1 = data.getData();
//                        if (data1 != null) {
//                            item_rectification_tv_title.setText(data1.getProject_title());
//                            item_rectification_tv_deadline.setText(data1.getEn_name());
//                            bianhao_tv.setText(data1.getProject_number());
//                            jingli_tv.setText(data1.getManager_user_name());
//                            String nowDateShort = getNowDateShort(data1.getProject_work_start());
//                            date_tv.setText(nowDateShort);
//                            gongsi_tv.setText(data1.getCompany_name());
//                            item_rectification_rtv_state.setText(data1.getProject_status());
//                        }
//                    }
//                }
//            }
//        });

//        item_rectification_tv_title.setText(projectName);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <= 0; i++) {
            list.add("项目概览");
            list.add("详细信息");
            list.add("楼栋信息");
            list.add("项目组");
            list.add("班组信息");
//            list.add("项目检测范围");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        createKanBanDatilsOneFragment = new CreateKanBanDatilsOneFragment(projectId, companyId);
        createKanBanDatilsTwoFragment = new CreateKanBanDatilsTwoFragment(projectId);
        createKanBanDetailsThreeFragment = new CreateKanBanDetailsThreeFragment(projectId);
//        createKanBanDatilsFourFragment = new CreateKanBanDatilsFourFragment(projectId, projectjl);
        createKanBanDatilsFourFragment = new CreateKanBanDatilsFourFragment(projectId);
        createKanBanDatilsFiveFragment = new CreateKanBanDatilsFiveFragment(projectId);
        transaction.add(R.id.framelayout_details, createKanBanDatilsOneFragment);
        transaction.add(R.id.framelayout_details, createKanBanDatilsTwoFragment);
        transaction.add(R.id.framelayout_details, createKanBanDetailsThreeFragment);
        transaction.add(R.id.framelayout_details, createKanBanDatilsFourFragment);
        transaction.add(R.id.framelayout_details, createKanBanDatilsFiveFragment);
        transaction.show(createKanBanDatilsOneFragment).hide(createKanBanDatilsTwoFragment).hide(createKanBanDetailsThreeFragment).hide(createKanBanDatilsFourFragment).hide(createKanBanDatilsFiveFragment);
        transaction.commit();

        rediogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.hide(createKanBanDatilsOneFragment);
                transaction1.hide(createKanBanDatilsTwoFragment);
                transaction1.hide(createKanBanDetailsThreeFragment);
                transaction1.hide(createKanBanDatilsFourFragment);
                transaction1.hide(createKanBanDatilsFiveFragment);
                switch (checkedId) {
                    case R.id.redio_one:
                        transaction1.show(createKanBanDatilsOneFragment);
                        break;
                    case R.id.redio_two:
                        transaction1.show(createKanBanDatilsTwoFragment);
                        break;
                    case R.id.redio_three:
                        transaction1.show(createKanBanDetailsThreeFragment);
                        break;
                    case R.id.redio_four:
                        transaction1.show(createKanBanDatilsFourFragment);
                        break;
                    case R.id.redio_five:
                        transaction1.show(createKanBanDatilsFiveFragment);
                        break;
                }
                transaction1.commit();
            }
        });


    }

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


}

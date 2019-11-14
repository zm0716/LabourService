package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.KFFilterAdapter;
import com.agilefinger.labourservice.adapter.ZGZPFilterAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ProjectConditionBean;
import com.agilefinger.labourservice.bean.ProvinceBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.AddressDiaLog;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KanBanFilterActivity extends BaseActivity {


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
    @BindView(R.id.activity_inspection_filter_rtv_week)
    RoundTextView rtvWeek;
    @BindView(R.id.activity_inspection_filter_rtv_month)
    RoundTextView rtvMonth;
    @BindView(R.id.activity_inspection_filter_tv_begin)
    TextView tvBegin;
    @BindView(R.id.activity_inspection_filter_ll_begin)
    LinearLayout activityInspectionFilterLlBegin;
    @BindView(R.id.activity_inspection_filter_tv_end)
    TextView tvEnd;
    @BindView(R.id.m_address_text)
    TextView m_address_text;
    @BindView(R.id.activity_inspection_filter_ll_end)
    LinearLayout activityInspectionFilterLlEnd;
    @BindView(R.id.activity_inspection_filter_rv_zg_man)
    RecyclerView xjRecy;
    @BindView(R.id.activity_inspection_filter_rv_zp_man)
    RecyclerView kfRecy;
    @BindView(R.id.m_address_rela)
    RelativeLayout mAddressRela;
    @BindView(R.id.activity_inspection_filter_rtv_reset)
    RoundTextView activityInspectionFilterRtvReset;
    @BindView(R.id.activity_inspection_filter_rtv_ok)
    RoundTextView activityInspectionFilterRtvOk;

    private DatePickerDialog beginPickerDialog, endPickerDialog;//创建日期筛选
    private String begin, end;
    private ZGZPFilterAdapter xjAdapter;
    private KFFilterAdapter kfAdapter;
    private String companyId;
    private String beginFilter, endFilter;//创建日期筛选
    private String province = "", cityModel = "", district = "";

    private List<StaffBean> xjManFilter, kfManFilter;

    private String provinceCode, cityModelCode, districtCode;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kan_ban_filter;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("筛选", false, false, "");
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        xjManFilter = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.FILTER_JL_MAN);
        kfManFilter = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.FILTER_KF_MAN);
        beginFilter = getIntent().getExtras().getString(Constants.FILTER_BEGIN_TIME);
        endFilter = getIntent().getExtras().getString(Constants.FILTER_END_TIME);
        province = getIntent().getExtras().getString(Constants.FILTER_PC_NAME);
        cityModel = getIntent().getExtras().getString(Constants.FILTER_CC_NAME);
        district = getIntent().getExtras().getString(Constants.FILTER_DC_NAME);

        provinceCode = getIntent().getExtras().getString(Constants.FILTER_PC);
        cityModelCode = getIntent().getExtras().getString(Constants.FILTER_CC);
        districtCode = getIntent().getExtras().getString(Constants.FILTER_DC);

        begin = beginFilter;
        end = endFilter;
        provincecode = provinceCode;
        cityModelcode = cityModelCode;
        districtcode = districtCode;
//        provinceName = province;
//        cityModelName = cityModel;
//        provinceName = district;
        initRV();
        showData();
    }

    private void showData() {
        tvBegin.setText(beginFilter);
        tvEnd.setText(endFilter);
        if (province != null && cityModel != null && district != null) {
            m_address_text.setText(province + "-" + cityModel + "-" + district);
        }
        xjAdapter.setNewData(xjManFilter);
        kfAdapter.setNewData(kfManFilter);
    }


    @Override
    public void initLoad() {
        super.initLoad();
//        JianCeRenBean.DataBean staffBean = new JianCeRenBean.DataBean();
//        staffBean.setAdd(true);
        StaffBean staffBean = new StaffBean();
        staffBean.setAdd(true);
//        StaffBean staffBean1=new StaffBean();
//        staffBean1.setAdd(true);
        if (xjManFilter == null || xjManFilter.size() == 0) {
            xjAdapter.addData(staffBean);
        }
        if (kfManFilter == null || kfManFilter.size() == 0) {
            kfAdapter.addData(staffBean);
        }
    }

    private void initRV() {
        //项目经理
        LinearLayoutManager xjLinearLayoutManager = new LinearLayoutManager(this);
        xjLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        xjAdapter = new ZGZPFilterAdapter();
        xjRecy.setLayoutManager(xjLinearLayoutManager);
        xjRecy.setAdapter(xjAdapter);

        LinearLayoutManager kfLinearLayoutManager = new LinearLayoutManager(this);
        kfLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        kfAdapter = new KFFilterAdapter();
        kfRecy.setLayoutManager(kfLinearLayoutManager);
        kfRecy.setAdapter(kfAdapter);

    }

    @Override
    public void initListener() {
        super.initListener();
        xjAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundleS = new Bundle();
                Log.d("公司id", companyId);
                bundleS.putString(Constants.EXTRA_TYPE, "项目经理");
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
                bundleS.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundleS.putSerializable(Constants.EXTRA_DATA_, (Serializable) xjAdapter.getData());
                IntentUtils.startActivityForResult(KanBanFilterActivity.this, KBXJActivity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF_XJ);
            }
        });
        xjAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                xjAdapter.remove(position);
            }
        });
        kfAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundleS = new Bundle();
                bundleS.putString(Constants.EXTRA_TYPE, "开发商");
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
                bundleS.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundleS.putSerializable(Constants.EXTRA_DATA_, (Serializable) kfAdapter.getData());
                IntentUtils.startActivityForResult(KanBanFilterActivity.this, KBXJActivity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF_KF);
            }
        });
        kfAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                kfAdapter.remove(position);
            }
        });
    }


    @OnClick({R.id.activity_inspection_filter_rtv_week, R.id.activity_inspection_filter_rtv_month, R.id.activity_inspection_filter_ll_begin,
            R.id.activity_inspection_filter_ll_end,
            R.id.activity_inspection_filter_rtv_reset, R.id.m_address_rela, R.id.activity_inspection_filter_rtv_ok})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_inspection_filter_rtv_week://近一周
                showWeekOrMonth("WEEK");
                break;
            case R.id.activity_inspection_filter_rtv_month://近一个月
                showWeekOrMonth("MONTH");
                break;
            //开始时间
            case R.id.activity_inspection_filter_ll_begin:
                showDatePickerDialog();
                break;
            //结束时间
            case R.id.activity_inspection_filter_ll_end:
                showEndPickerDialog();
                break;
            case R.id.m_address_rela:
                addAddressData();
                break;
            case R.id.activity_inspection_filter_rtv_ok:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.FILTER_JL_MAN, (Serializable) xjAdapter.getData());
                bundle.putSerializable(Constants.FILTER_KF_MAN, (Serializable) kfAdapter.getData());
                bundle.putSerializable(Constants.FILTER_BEGIN_TIME, begin);
                bundle.putSerializable(Constants.FILTER_END_TIME, end);
//                bundle.putSerializable(Constants.FILTER_BEGIN_END_TIME,beginEnd);
//                bundle.putSerializable(Constants.FILTER_END_END_TIME, endEnd);
                bundle.putString(Constants.FILTER_PC, String.valueOf(provincecode));
                bundle.putString(Constants.FILTER_PC_NAME, provinceName);
                bundle.putString(Constants.FILTER_CC, String.valueOf(cityModelcode));
                bundle.putString(Constants.FILTER_CC_NAME, cityModelName);
                bundle.putString(Constants.FILTER_DC, String.valueOf(districtcode));
                bundle.putString(Constants.FILTER_DC_NAME, districtName);

                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.activity_inspection_filter_rtv_reset:
                reset();
                break;
        }
    }

    private void reset() {
        showWeekOrMonth("");

        begin = end = "";
        tvEnd.setText("");
        tvBegin.setText("");
        province = cityModel = district = "";
        provincecode = cityModelcode = districtcode = "";

        m_address_text.setText("");
//        List<JianCeRenBean.DataBean> mList = new ArrayList<>();
//        mList.add(new JianCeRenBean.DataBean());
        List<StaffBean> mList2 = new ArrayList<>();
        mList2.add(new StaffBean());
        xjAdapter.replaceData(mList2);
        kfAdapter.replaceData(mList2);
        showToast("重置成功");
    }

    private void showEndPickerDialog() {
        final String begin = tvBegin.getText().toString().trim();
        if (TextUtils.isEmpty(begin)) {
            showToast("请选择开始日期");
            return;
        }

        closeEndPickerDialog();
        endPickerDialog = new DatePickerDialog(this, begin, DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    showWeekOrMonth("");
                    end = param[0] + " 23:59:59";
                    tvEnd.setText(end);
                }
                closeEndPickerDialog();
            }
        });
        endPickerDialog.show();
    }

    private void closeBeginPickerDialog() {
        if (beginPickerDialog != null) {
            beginPickerDialog.cancel();
            beginPickerDialog = null;
        }
    }

    private void closeEndPickerDialog() {
        if (endPickerDialog != null) {
            endPickerDialog.cancel();
            endPickerDialog = null;
        }
    }


    private void showWeekOrMonth(String w) {
        if ("WEEK".equals(w)) {
            rtvWeek.setTextColor(getResources().getColor(android.R.color.white));
            rtvWeek.getDelegate().setBackgroundColor(getResources().getColor(R.color.blue_349fff));
            rtvMonth.setTextColor(getResources().getColor(R.color.black_333333));
            rtvMonth.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            begin = DateFormatUtils.nearWeek(-1);
            end = DateFormatUtils.formatDate2(new GregorianCalendar());
            tvBegin.setText("");
            tvEnd.setText("");
        } else if ("MONTH".equals(w)) {
            rtvMonth.setTextColor(getResources().getColor(android.R.color.white));
            rtvMonth.getDelegate().setBackgroundColor(getResources().getColor(R.color.blue_349fff));
            rtvWeek.setTextColor(getResources().getColor(R.color.black_333333));
            rtvWeek.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            begin = DateFormatUtils.nearMonth2(-1);
            end = DateFormatUtils.formatDate2(new GregorianCalendar());
            tvBegin.setText("");
            tvEnd.setText("");
        } else {
            rtvWeek.setTextColor(getResources().getColor(R.color.black_333333));
            rtvWeek.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            rtvMonth.setTextColor(getResources().getColor(R.color.black_333333));
            rtvMonth.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void showDatePickerDialog() {
        closeBeginPickerDialog();
        beginPickerDialog = new DatePickerDialog(this, "", DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
//                Log.d("返回日期",i+":;"+param[0]+"::"+param[1]);
                if (confirm) {
                    showWeekOrMonth("");
                    begin = param[0] + " 00:00:00";
                    end = "";
                    tvBegin.setText(begin);
                    tvEnd.setText(end);

                }
                closeBeginPickerDialog();
            }
        });
        beginPickerDialog.show();
    }

    private String provincecode, cityModelcode, districtcode;
    private String provinceName, cityModelName, districtName;

    private void addAddressData() {
        HttpManager.getInstance().getKBKBProvinceData(new HttpEngine.OnResponseCallback<HttpResponse.KBProvinceBean>() {

            private AddressDiaLog addressDiaLog;

            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBProvinceBean data) {
                if (result == 0) {
                    if (data.getData() != null) {

                        addressDiaLog = new AddressDiaLog(KanBanFilterActivity.this, data.getData(), new AddressDiaLog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, String p, String c, String d, String... param) {
                                provincecode = p;
                                cityModelCode = c;
                                districtcode = d;
                                provinceName = param[0];
                                cityModelName = param[1];
                                districtName = param[2];
                                m_address_text.setText(param[0] + "-" + param[1] + "-" + param[2]);
                                addressDiaLog.dismiss();
                            }
                        });
                        addressDiaLog.show();
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_STAFF_XJ:
                    List<StaffBean> listXJ = (List<StaffBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    listXJ.add(new StaffBean());
                    xjAdapter.setNewData(listXJ);
                    break;
                case Constants.REQUEST_CHOOSE_STAFF_KF:
                    List<StaffBean> listKF = (List<StaffBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    listKF.add(new StaffBean());
                    kfAdapter.setNewData(listKF);
                    break;
            }
        }
    }

}

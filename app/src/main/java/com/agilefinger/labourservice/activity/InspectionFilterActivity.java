package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.JCRZPFilterAdapter;
import com.agilefinger.labourservice.adapter.ZGZPFilterAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InspectionFilterActivity extends BaseActivity {

    @BindView(R.id.activity_inspection_filter_rv_zg_man)
    RecyclerView rvZGMan;//检测人
    @BindView(R.id.activity_inspection_filter_rv_zp_man)
    RecyclerView rvZPMan;//开发商
    //按创建日期
    @BindView(R.id.activity_inspection_filter_rtv_week)
    RoundTextView rtvWeek;
    @BindView(R.id.activity_inspection_filter_rtv_month)
    RoundTextView rtvMonth;
    @BindView(R.id.activity_inspection_filter_tv_begin)
    TextView tvBegin;
    @BindView(R.id.activity_inspection_filter_tv_end)
    TextView tvEnd;
    private DatePickerDialog beginPickerDialog, endPickerDialog;//创建日期筛选
    private String beginFilter, endFilter;//创建日期筛选
    private String begin, end;
    //按截止日期
    @BindView(R.id.activity_inspection_filter_rtv_week_end)
    RoundTextView rtvWeekEnd;
    @BindView(R.id.activity_inspection_filter_rtv_month_end)
    RoundTextView rtvMonthEnd;
    @BindView(R.id.activity_inspection_filter_tv_begin_end)
    TextView tvBeginEnd;
    @BindView(R.id.activity_inspection_filter_tv_end_end)
    TextView tvEndEnd;
    private DatePickerDialog beginEndPickerDialog, endEndPickerDialog;
    private String beginEndFilter, endEndFilter;
    private String beginEnd, endEnd;
//    private JCRZPFilterAdapter zgAdapter;
//    private List<JianCeRenBean.DataBean> zgManFilter;
    private String companyId;

    private List<StaffBean>  zpManFilter,zgManFilter;
    private ZGZPFilterAdapter zpAdapter,zgAdapter;
    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_filter;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("筛选", false, false, "");
        zpManFilter = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.FILTER_ZP_MAN);
        zgManFilter = (List<StaffBean>) getIntent().getExtras().getSerializable(Constants.FILTER_ZG_MAN);
//        zgManFilter = (List<JianCeRenBean.DataBean>) getIntent().getExtras().getSerializable(Constants.FILTER_ZG_MAN);
        beginFilter = getIntent().getExtras().getString(Constants.FILTER_BEGIN_TIME);
        endFilter = getIntent().getExtras().getString(Constants.FILTER_END_TIME);
        beginEndFilter = getIntent().getExtras().getString(Constants.FILTER_BEGIN_END_TIME);
        endEndFilter = getIntent().getExtras().getString(Constants.FILTER_END_END_TIME);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);

        begin=beginFilter;
        end=endFilter;
        beginEnd=beginEndFilter;
        endEnd=endEndFilter;
        initRV();
        showData();
    }

    private void showData() {
        tvBegin.setText(beginFilter);
        tvEnd.setText(endFilter);
        tvBeginEnd.setText(beginEndFilter);
        tvEndEnd.setText(endEndFilter);
        zgAdapter.setNewData(zgManFilter);
        zpAdapter.setNewData(zpManFilter);
    }

    private void initRV() {
        //检测人
        LinearLayoutManager zgLinearLayoutManager = new LinearLayoutManager(this);
        zgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zgAdapter = new ZGZPFilterAdapter();
        rvZGMan.setLayoutManager(zgLinearLayoutManager);
        rvZGMan.setAdapter(zgAdapter);

        //开发商
        LinearLayoutManager zpLinearLayoutManager = new LinearLayoutManager(this);
        zpLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zpAdapter = new ZGZPFilterAdapter();
        rvZPMan.setLayoutManager(zpLinearLayoutManager);
        rvZPMan.setAdapter(zpAdapter);

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
        if (zgManFilter == null || zgManFilter.size() == 0) {
            zgAdapter.addData(staffBean);
        }
        if (zpManFilter == null || zpManFilter.size() == 0) {
            zpAdapter.addData(staffBean);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        zgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundleS = new Bundle();
                Log.d("公司id",companyId);
                bundleS.putString(Constants.EXTRA_TYPE, "检测人");
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
                bundleS.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundleS.putSerializable(Constants.EXTRA_DATA_, (Serializable) zgAdapter.getData());
                IntentUtils.startActivityForResult(InspectionFilterActivity.this, StaffSelection2Activity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF_ZG);
            }
        });
        zgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                zgAdapter.remove(position);
            }
        });
        zpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundleS = new Bundle();
                bundleS.putString(Constants.EXTRA_TYPE,"开发商");
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
                bundleS.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundleS.putSerializable(Constants.EXTRA_DATA_, (Serializable) zpAdapter.getData());
                IntentUtils.startActivityForResult(InspectionFilterActivity.this, TesterActivity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF_ZP);
            }
        });
        zpAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                zpAdapter.remove(position);
            }
        });
    }

    @Override
    @OnClick({
            R.id.layout_toolbar_iv_operate_2,
            R.id.activity_inspection_filter_rtv_reset,
            R.id.activity_inspection_filter_rtv_ok,
            R.id.activity_inspection_filter_rtv_week,
            R.id.activity_inspection_filter_rtv_month,
            R.id.activity_inspection_filter_ll_begin,
            R.id.activity_inspection_filter_ll_end,
            R.id.activity_inspection_filter_rtv_week_end,
            R.id.activity_inspection_filter_rtv_month_end,
            R.id.activity_inspection_filter_ll_begin_end,
            R.id.activity_inspection_filter_ll_end_end
    })
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_inspection_filter_rtv_week://近一周
                showWeekOrMonth("WEEK");
                break;
            case R.id.activity_inspection_filter_rtv_month://近一个月
                showWeekOrMonth("MONTH");
                break;
            case R.id.activity_inspection_filter_rtv_week_end://截止-近一周
                showEndWeekOrMonth("END_WEEK");
                break;
            case R.id.activity_inspection_filter_rtv_month_end://截止-近一个月
                showEndWeekOrMonth("END_MONTH");
                break;
            case R.id.activity_inspection_filter_ll_begin:
                showDatePickerDialog(1);
                break;
            case R.id.activity_inspection_filter_ll_end:
                showEndPickerDialog(1);
                break;
            case R.id.activity_inspection_filter_ll_begin_end:
                showDatePickerDialog(2);
                break;
            case R.id.activity_inspection_filter_ll_end_end:
                showEndPickerDialog(2);
                break;
            case R.id.activity_inspection_filter_rtv_reset://重置
                reset();
                break;
            case R.id.activity_inspection_filter_rtv_ok:
                ok();
                break;
        }
    }

    /**
     * 确认
     */
    private void ok() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FILTER_ZP_MAN, (Serializable) zpAdapter.getData());
        bundle.putSerializable(Constants.FILTER_ZG_MAN, (Serializable) zgAdapter.getData());
        bundle.putSerializable(Constants.FILTER_BEGIN_TIME, begin);
        bundle.putSerializable(Constants.FILTER_END_TIME, end);
        bundle.putSerializable(Constants.FILTER_BEGIN_END_TIME,beginEnd);
        bundle.putSerializable(Constants.FILTER_END_END_TIME, endEnd);

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void reset() {
        showWeekOrMonth("");
        showEndWeekOrMonth("");

        begin = end = beginEnd = endEnd = "";
        tvEnd.setText("");
        tvBegin.setText("");
        tvEndEnd.setText("");
        tvBeginEnd.setText("");
//        List<JianCeRenBean.DataBean> mList = new ArrayList<>();
//        mList.add(new JianCeRenBean.DataBean());
        List<StaffBean> mList2 = new ArrayList<>();
        mList2.add(new StaffBean());
        zgAdapter.replaceData(mList2);
        zpAdapter.replaceData(mList2);
        showToast("重置成功");
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

    private void showEndWeekOrMonth(String w) {
        if ("END_WEEK".equals(w)) {
            rtvWeekEnd.setTextColor(getResources().getColor(android.R.color.white));
            rtvWeekEnd.getDelegate().setBackgroundColor(getResources().getColor(R.color.blue_349fff));
            rtvMonthEnd.setTextColor(getResources().getColor(R.color.black_333333));
            rtvMonthEnd.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            endEnd = DateFormatUtils.formatDate2(new GregorianCalendar());
            beginEnd = DateFormatUtils.nearWeek(-1);
            tvBeginEnd.setText("");
            tvEndEnd.setText("");
        } else if ("END_MONTH".equals(w)) {
            rtvMonthEnd.setTextColor(getResources().getColor(android.R.color.white));
            rtvMonthEnd.getDelegate().setBackgroundColor(getResources().getColor(R.color.blue_349fff));
            rtvWeekEnd.setTextColor(getResources().getColor(R.color.black_333333));
            rtvWeekEnd.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            endEnd = DateFormatUtils.formatDate2(new GregorianCalendar()) ;
            beginEnd =DateFormatUtils.nearMonth2(-1);
            tvBeginEnd.setText("");
            tvEndEnd.setText("");
        } else {
            rtvWeekEnd.setTextColor(getResources().getColor(R.color.black_333333));
            rtvWeekEnd.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
            rtvMonthEnd.setTextColor(getResources().getColor(R.color.black_333333));
            rtvMonthEnd.getDelegate().setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void showDatePickerDialog(final int i) {
        closeBeginPickerDialog();
        beginPickerDialog = new DatePickerDialog(this, "", DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                Log.d("返回日期",i+":;"+param[0]+"::"+param[1]);
                if (confirm) {
                    if (i==1){
                        showWeekOrMonth("");
                        begin = param[0]+" 00:00:00";
                        end = "";
                        tvBegin.setText(begin);
                        tvEnd.setText(end);
                    }else {
                        showEndWeekOrMonth("");
                        beginEnd =param[0]+" 00:00:00";
                        endEnd="";
                        tvBeginEnd.setText(beginEnd);
                        tvEndEnd.setText(endEnd);
                    }

                }
                closeBeginPickerDialog();
            }
        });
        beginPickerDialog.show();
    }

    private void showEndPickerDialog(final int i) {
        final String begin = tvBegin.getText().toString().trim();
        final String trim = tvBeginEnd.getText().toString().trim();
        if (i==1){
            if (TextUtils.isEmpty(begin)) {
                showToast("请选择开始日期");
                return;
            }
        }else {
            if (TextUtils.isEmpty(trim)) {
                showToast("请选择开始日期");
                return;
            }
        }
        closeEndPickerDialog();
        endPickerDialog = new DatePickerDialog(this, begin, DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    if (i==1){
                        showWeekOrMonth("");
                        end = param[0]+ " 23:59:59";
                        tvEnd.setText(end);
                    }else {
                        showEndWeekOrMonth("");
                        endEnd = param[0]+ " 23:59:59";
                        tvEndEnd.setText(endEnd);
                    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_STAFF_ZG:
                    List<StaffBean> listZG = (List<StaffBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    listZG.add(new StaffBean());
                    zgAdapter.setNewData(listZG);
                    break;
                case Constants.REQUEST_CHOOSE_STAFF_ZP:
                    List<StaffBean> listZP = (List<StaffBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    listZP.add(new StaffBean());
                    zpAdapter.setNewData(listZP);
                    break;
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeBeginPickerDialog();
        closeEndPickerDialog();
    }
}

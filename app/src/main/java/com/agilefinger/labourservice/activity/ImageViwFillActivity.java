package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BuildingAddressAdapter;
import com.agilefinger.labourservice.adapter.BuildingSGAdapter;
import com.agilefinger.labourservice.adapter.ZGZPFilterAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildBean;
import com.agilefinger.labourservice.bean.BuildingBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageViwFillActivity extends BaseActivity {


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
    @BindView(R.id.m_rb_all)
    RadioButton mRbAll;
    @BindView(R.id.m_rb_zg)
    RadioButton mRbZg;
    @BindView(R.id.m_rb_xj)
    RadioButton mRbXj;
    @BindView(R.id.m_rg)
    RadioGroup mRg;
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
    @BindView(R.id.activity_inspection_filter_ll_end)
    LinearLayout activityInspectionFilterLlEnd;
    @BindView(R.id.m_address_recy)
    RecyclerView mAddressRecy;
    private int source;
    private View header;
    private BuildingAddressAdapter imageAdapter;
    private List<BuildingBean> xjManFilter;
    private String projectId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_image_viw_fill;
    }

    @Override
    public void initData() {
        super.initData();
        projectId = getIntent().getExtras().getString("projectId");
        source = getIntent().getExtras().getInt(Constants.FILTER_SOURCE);
        begin = getIntent().getExtras().getString(Constants.FILTER_BEGIN_TIME);
        end = getIntent().getExtras().getString(Constants.FILTER_END_TIME);
        xjManFilter = (List<BuildingBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);

    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("筛选", false, false, "");
        initRV();
        showData();
        if (source == 0) {
            mRbAll.setChecked(true);
            mRbAll.setTextColor(getResources().getColor(R.color.white));

            mRbZg.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbZg.setChecked(false);
            mRbXj.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbXj.setChecked(false);
        } else if (source == 1) {
            mRbAll.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbAll.setChecked(false);
            mRbZg.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbZg.setChecked(false);


            mRbXj.setChecked(true);
            mRbXj.setTextColor(getResources().getColor(R.color.white));

        } else {
            mRbAll.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbAll.setChecked(false);
            mRbXj.setTextColor(getResources().getColor(R.color.blue_2093ff));
            mRbXj.setChecked(false);
            mRbZg.setChecked(true);
            mRbZg.setTextColor(getResources().getColor(R.color.white));

        }

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.m_rb_all:
                        mRbAll.setChecked(true);
                        mRbAll.setTextColor(getResources().getColor(R.color.white));

                        mRbZg.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbZg.setChecked(false);
                        mRbXj.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbXj.setChecked(false);

                        source = 0;

                        break;
                    case R.id.m_rb_zg:
                        mRbAll.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbAll.setChecked(false);
                        mRbXj.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbXj.setChecked(false);
                        mRbZg.setChecked(true);
                        mRbZg.setTextColor(getResources().getColor(R.color.white));

                        source = 2;


                        break;
                    case R.id.m_rb_xj:
                        mRbAll.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbAll.setChecked(false);
                        mRbZg.setTextColor(getResources().getColor(R.color.blue_2093ff));
                        mRbZg.setChecked(false);


                        mRbXj.setChecked(true);
                        mRbXj.setTextColor(getResources().getColor(R.color.white));

                        source = 1;
                        break;
                }
            }
        });
        showData();

    }

    @Override
    public void initLoad() {
        super.initLoad();
//        JianCeRenBean.DataBean staffBean = new JianCeRenBean.DataBean();
//        staffBean.setAdd(true);
        BuildingBean staffBean = new BuildingBean();
        staffBean.setAdd(true);
//        StaffBean staffBean1=new StaffBean();
//        staffBean1.setAdd(true);
        if (xjManFilter == null || xjManFilter.size() == 0) {
            imageAdapter.addData(staffBean);
        }
    }

    private void showData() {
        tvBegin.setText(begin);
        tvEnd.setText(end);
//        if (xjManFilter!=null) {
//            imageAdapter.setNewData(xjManFilter);
//        }
        imageAdapter.setNewData(xjManFilter);
    }

    private void initRV() {
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mAddressRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.bottom = 10;
                outRect.right = 7;
            }
        });

        mAddressRecy.setLayoutManager(manager);
        imageAdapter = new BuildingAddressAdapter();
        mAddressRecy.setAdapter(imageAdapter);

    }

    @Override
    public void initListener() {
        super.initListener();
        imageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundleS = new Bundle();
                Log.d("公司id", projectId);
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_MULTI);
                bundleS.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundleS.putSerializable(Constants.EXTRA_DATA_, (Serializable) imageAdapter.getData());
                IntentUtils.startActivityForResult(ImageViwFillActivity.this, ImageAddressActivity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF_XJ);
            }
        });
        imageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                imageAdapter.remove(position);
            }
        });
    }

    @OnClick({R.id.activity_inspection_filter_rtv_week, R.id.activity_inspection_filter_rtv_month, R.id.activity_inspection_filter_ll_begin,
            R.id.activity_inspection_filter_ll_end,
            R.id.activity_inspection_filter_rtv_reset,
            R.id.activity_inspection_filter_rtv_ok})
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
            case R.id.activity_inspection_filter_rtv_ok:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FILTER_BEGIN_TIME, begin);
                bundle.putString(Constants.FILTER_END_TIME, end);
//                bundle.putSerializable(Constants.FILTER_BEGIN_END_TIME,beginEnd);
//                bundle.putSerializable(Constants.FILTER_END_END_TIME, endEnd);
//                bundle.putString(Constants.FILTER_PC, String.valueOf(provincecode));
                bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) imageAdapter.getData());
                bundle.putInt(Constants.FILTER_SOURCE, source);
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
//        List<JianCeRenBean.DataBean> mList = new ArrayList<>();
//        mList.add(new JianCeRenBean.DataBean());
        source = 0;
        mRbAll.setChecked(true);
        List<BuildingBean> mList2 = new ArrayList<>();
        mList2.add(new BuildingBean());
        imageAdapter.replaceData(mList2);
        showToast("重置成功");
    }


    private DatePickerDialog beginPickerDialog, endPickerDialog;//创建日期筛选
    private String begin, end;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_STAFF_XJ:
                    List<BuildingBean> listXJ = (List<BuildingBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    listXJ.add(new BuildingBean());
                    imageAdapter.setNewData(listXJ);
                    break;
            }
        }
    }
}

package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.BigImageActivity;
import com.agilefinger.labourservice.activity.InspectionListActivity;
import com.agilefinger.labourservice.activity.MoreImgActivity;
import com.agilefinger.labourservice.activity.RectificationListActivity;
import com.agilefinger.labourservice.activity.RedActivity;
import com.agilefinger.labourservice.adapter.ProjectImgAdaptere;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.Bean;
import com.agilefinger.labourservice.bean.KanBanDetailBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.custom.CompletedView;
import com.agilefinger.labourservice.view.custom.CompletedXianView;
import com.agilefinger.labourservice.view.dialog.DatePicker2Dialog;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.TimeDiaLog;
import com.agilefinger.labourservice.widget.datetimepicker.DateTimePickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class CreateKanBanDatilsOneFragment extends JBaseFragment {

    @BindView(R.id.completeYuan)
    PieChart completeYuan;
    @BindView(R.id.img_rv)
    RecyclerView rvImages;
    @BindView(R.id.more_tv)
    TextView more_tv;
    @BindView(R.id.m_zg_zhong)
    TextView m_zg_zhong;
    @BindView(R.id.m_zg_wancheng)
    TextView m_zg_wancheng;
    @BindView(R.id.m_zg_yan)
    TextView m_zg_yan;
    @BindView(R.id.m_zg_hl)
    TextView m_zg_hl;
    @BindView(R.id.m_creat_task)
    TextView m_creat_task;
    @BindView(R.id.m_wancheng_task)
    TextView m_wancheng_task;
    @BindView(R.id.text_zhi)
    TextView text_zhi;
    @BindView(R.id.textview_zg)
    TextView textview_zg;
    @BindView(R.id.text_aq)
    TextView text_aq;
    @BindView(R.id.m_wei_zhi)
    TextView m_wei_zhi;
    @BindView(R.id.m_CompletedView_one)
    CompletedView m_CompletedView_one;
    @BindView(R.id.m_CompletedView_two)
    CompletedView m_CompletedView_two;
    @BindView(R.id.m_CompletedView_three)
    CompletedView m_CompletedView_three;
    @BindView(R.id.m_CompletedView_four)
    CompletedView m_CompletedView_four;

    @BindView(R.id.m_progress)
    ProgressBar m_progress;
    @BindView(R.id.m_line_char)
    LineChart m_line_char;

    Unbinder unbinder;
    String projectId;
    String startTime = "", endTime = "";
    @BindView(R.id.m_time_img)
    ImageView mTimeImg;
    @BindView(R.id.layout_zg_all)
    RelativeLayout layout_zg_all;
    @BindView(R.id.text_pj)
    TextView textPj;
    @BindView(R.id.image_jiantou)
    ImageView imageJiantou;
    @BindView(R.id.view_zhi)
    TextView viewZhi;
    @BindView(R.id.view_an)
    TextView viewAn;
    @BindView(R.id.textview_zl)
    TextView textviewZl;
    @BindView(R.id.textview_aq)
    TextView textviewAq;
    @BindView(R.id.text_pjun)
    TextView textPjun;
    @BindView(R.id.image_jt)
    ImageView imageJt;
    @BindView(R.id.text_zhilang)
    TextView textZhilang;
    @BindView(R.id.m_no_text)
    TextView m_no_text;
    @BindView(R.id.view_zl)
    TextView viewZl;
    @BindView(R.id.text_anquan)
    TextView textAnquan;
    @BindView(R.id.view_aq)
    TextView viewAq;
    @BindView(R.id.textview_zzllaing)
    TextView textviewZzllaing;
    @BindView(R.id.textview_aaquan)
    TextView textviewAaquan;
    @BindView(R.id.text_hongxian)
    TextView textHongxian;
    @BindView(R.id.view_hx)
    TextView viewHx;
    @BindView(R.id.text_fjhx)
    TextView textFjhx;
    @BindView(R.id.text_fjfenshu)
    TextView textFjfenshu;
    @BindView(R.id.text_update)
    TextView textUpdate;
    @BindView(R.id.m_pinjun)
    TextView m_pinjun;
    @BindView(R.id.relayout_hx)
    RelativeLayout relayoutHx;
    @BindView(R.id.text_fenge)
    TextView textFenge;
    @BindView(R.id.m_zg_time)
    TextView m_zg_time;
    @BindView(R.id.m_xj_time)
    TextView m_xj_time;
    @BindView(R.id.text_xjhx)
    TextView textXjhx;
    @BindView(R.id.text_xjfenshu)
    TextView textXjfenshu;
    @BindView(R.id.text_zg)
    TextView textZg;
    @BindView(R.id.relayout_xjhx)
    RelativeLayout relayoutXjhx;
    @BindView(R.id.m_zhenggai)
    ImageView mZhenggai;

    @BindView(R.id.layout_zg_do)
    LinearLayout layout_zg_do;
    @BindView(R.id.layout_zg_finish)
    LinearLayout layout_zg_finish;
    @BindView(R.id.layout_zg_yan)
    LinearLayout layout_zg_yan;
    @BindView(R.id.layout_zg_hl)
    LinearLayout layout_zg_hl;

    @BindView(R.id.completedTiao)
    CompletedXianView completedTiao;
    @BindView(R.id.line_char)
    LineChart lineChar;
    @BindView(R.id.m_img_line)
    LinearLayout m_img_line;
    @BindView(R.id.m_red_line)
    LinearLayout m_red_line;
    @BindView(R.id.scoller)
    ScrollView scoller;
    @BindView(R.id.m_creat_task_rela)
    RelativeLayout m_creat_task_rela;
    @BindView(R.id.m_finish_tassk_rela)
    RelativeLayout m_finish_tassk_rela;
    private Float mMax = 0f;
    private Float mMin = 0f;
    String companyId;
    private Bundle bundle;
    private TimeDiaLog timeDiaLog;
    private String year;
    private String month;
    private String day;

    @SuppressLint("ValidFragment")
    public CreateKanBanDatilsOneFragment(String projectId, String companyId) {
        this.projectId = projectId;
        this.companyId = companyId;
    }

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.item_kanbanone_fragment;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {

//        List<DataItem> items = new ArrayList<>();
//        items.add(new DataItem(5, "", "", getResources().getColor(R.color.blue_2093ff)));
//        items.add(new DataItem(3, "", "", getResources().getColor(R.color.red_ff0000)));
//        items.add(new DataItem(2, "", "", getResources().getColor(R.color.colorPrimary)));
//        completeYuan.setItems(items);


        LinearLayoutManager filterLinearLayout = new LinearLayoutManager(getActivity());
        filterLinearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(filterLinearLayout);
        rvImages.setNestedScrollingEnabled(false);
        rvImages.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
//                outRect.top = 10;
//                outRect.right =10;
            }
        });

    }


    private void setData(KanBanDetailBean.ProjectCorrectBean projectCorrect) {
        completeYuan.setUsePercentValues(false);
        completeYuan.getDescription().setEnabled(false);
        completeYuan.setDrawHoleEnabled(true);
        completeYuan.setHoleColor(Color.WHITE);
        completeYuan.setHighlightPerTapEnabled(false);
        completeYuan.setRotationEnabled(false);

        Legend legend = completeYuan.getLegend();
        legend.setEnabled(false);

        ArrayList<PieEntry> yVals = new ArrayList<>();
        if (projectCorrect.getNotOverDue() != 0) {
            yVals.add(new PieEntry(projectCorrect.getNotOverDue(), ""));
        }
        if (projectCorrect.getOverDueFinish() != 0) {
            yVals.add(new PieEntry(projectCorrect.getOverDueFinish(), ""));
        }
        if (projectCorrect.getOverDueNotFinish() != 0) {
            yVals.add(new PieEntry(projectCorrect.getOverDueNotFinish(), ""));
        }

        List<Integer> colors = new ArrayList<>();
        //未逾期
        if (projectCorrect.getNotOverDue() != 0) {
            colors.add(Color.parseColor("#33A0FF"));
        }
        //逾期已完成
        if (projectCorrect.getOverDueFinish() != 0) {
            colors.add(Color.parseColor("#995DFF"));
        }
        //逾期未完成
        if (projectCorrect.getOverDueNotFinish() != 0) {
            colors.add(Color.parseColor("#FF0000"));
        }

        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setSliceSpace(1f);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(13f);

        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                String str = v + "";
                if (str.length() == 0) {
                    return str;
                }
                return str.substring(0, str.indexOf("."));//设置自己的返回位数
            }
        });

        completeYuan.setNoDataText("数据为空");
        completeYuan.setData(pieData);

        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                completeYuan.animateX(1500, Easing.EaseInOutQuad);
            }
        });
    }

    @Override
    @OnClick({R.id.more_tv, R.id.m_time_img, R.id.m_zhenggai
            , R.id.m_creat_task_rela, R.id.m_finish_tassk_rela, R.id.layout_zg_all,
            R.id.layout_zg_do, R.id.layout_zg_finish, R.id.layout_zg_yan, R.id.layout_zg_hl, R.id.m_red_line})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.more_tv:
                Intent intent = new Intent(getActivity(), MoreImgActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_COMPANY, companyId);
                intent.putExtra("projectId", projectId);
                startActivity(intent);
                break;
            case R.id.m_time_img:

                showDialog();

//                showDatePickerDialog(1);
                break;
            case R.id.m_zhenggai:
                showDialog2();
//                showDatePickerDialog(2);
                break;
            case R.id.m_creat_task_rela:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_END_TIME, startTime);
                bundle.putString(Constants.FILTER_BEGIN_END_TIME, endTime);
                IntentUtils.startActivity(getActivity(), InspectionListActivity.class, bundle);
                break;
            case R.id.m_finish_tassk_rela:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_TYPE, Constants.YW_STATE_4);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_END_TIME, startTime);
                bundle.putString(Constants.FILTER_BEGIN_END_TIME, endTime);
                IntentUtils.startActivity(getActivity(), InspectionListActivity.class, bundle);
                break;

            //整改派单
            case R.id.layout_zg_all:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.FILTER_BEGIN_TIME, end);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_TIME, begin);
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                break;
            //整改中
            case R.id.layout_zg_do:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_1);
                bundle.putString(Constants.FILTER_BEGIN_TIME, end);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_TIME, begin);
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                break;
            //已完成
            case R.id.layout_zg_finish:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_3);
                bundle.putString(Constants.FILTER_BEGIN_TIME, end);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_TIME, begin);
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                break;
            //待验收
            case R.id.layout_zg_yan:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_2);
                bundle.putString(Constants.FILTER_BEGIN_TIME, end);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.FILTER_END_TIME, begin);
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                break;
            //已忽略
            case R.id.layout_zg_hl:
                bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_4);
                bundle.putString(Constants.FILTER_BEGIN_TIME, end);
                bundle.putString(Constants.FILTER_END_TIME, begin);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                break;
            //触发红线
            case R.id.m_red_line:
                bundle = new Bundle();
                bundle.putString("projectId", projectId);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString(Constants.FILTER_BEGIN_TIME, startTime);
                bundle.putString(Constants.FILTER_END_TIME, endTime);
                IntentUtils.startActivity(getActivity(), RedActivity.class, bundle);
                break;


        }
    }

    private void showDialog2() {
        timeDiaLog = new TimeDiaLog(getActivity(), details, new TimeDiaLog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, String start, String endDate) {
//                startTime = start;
//                endTime = endDate;
                begin = start;
                end = endDate;
                create_time = "";
                m_zg_time.setText("(" + end + "-" + begin + ")");
                getImgData();

                timeDiaLog.dismiss();
            }

            @Override
            public void onClick2(Dialog dialog) {
                create_time = "";
                showDatePickerDialog(2);
                timeDiaLog.dismiss();
            }

            @Override
            public void onClickCreat(String creaTime) {
                begin = year + "/" + month + "/" + day;
                end = creaTime;
                m_zg_time.setText("(" + end + "-" + begin + ")");
                getImgData();
                timeDiaLog.dismiss();
            }
        });

        timeDiaLog.show();
    }

    //展示时间弹框
    private void showDialog() {
        timeDiaLog = new TimeDiaLog(getActivity(), details, new TimeDiaLog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, String start, String endDate) {
                startTime = start;
                endTime = endDate;
                m_xj_time.setText("(" + endTime + "-" + startTime + ")");
                getImgData();

                timeDiaLog.dismiss();
            }

            @Override
            public void onClick2(Dialog dialog) {
                showDatePickerDialog(1);
                timeDiaLog.dismiss();
            }

            @Override
            public void onClickCreat(String creaTime) {
                startTime = year + "/" + month + "/" + day;
                endTime = creaTime;
                m_xj_time.setText("(" + endTime + "-" + startTime + ")");
                getImgData();
                timeDiaLog.dismiss();
            }
        });

        timeDiaLog.show();
    }


    @Override
    protected void lazyLoadOnlyOne() {
        //获取时间
        getTime();
        //获取图片数据
        getImgData();

    }

    Calendar calendars;

    @TargetApi(Build.VERSION_CODES.N)
    protected void getTime() {
        //获取当前年月日
        calendars = Calendar.getInstance();
        calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(calendars.get(Calendar.YEAR));
        month = String.valueOf(calendars.get(Calendar.MONTH) + 1);
        day = String.valueOf(calendars.get(Calendar.DATE));
        startTime = year + "/" + month + "/" + day;
        begin = year + "/" + month + "/" + day;


        endTime = year + "/" + month + "/" + 1;
        end = year + "/" + month + "/" + 1;


        m_zg_time.setText("(" + end + "-" + begin + ")");
        m_xj_time.setText("(" + endTime + "-" + startTime + ")");
//        //七天前的日期
//        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
//        Date beginDate = new Date();
//        Calendar date = Calendar.getInstance();
//        date.setTime(beginDate);
//        date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
//        Date endDate = null;
//        try {
//            endDate = dft.parse(dft.format(date.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        endTime = dft.format(endDate);
//        end = dft.format(endDate);
//        Log.e(TAG, "7天后是什么时间:" + shur_time);
//        return shur_time;


    }

    private ProjectImgAdaptere projectImgAdaptere;
    private String create_time = "";
    private String create_time1 = "";
    private String create_time2 = "";
    private KanBanDetailBean.DetailsBean details;

    //获取图片数据
    private void getImgData() {
        LoadingDialog.showLoading(getActivity());
        HttpManager.getInstance().getDetailsData(projectId, begin, end, startTime, endTime, new HttpEngine.OnResponseCallback<HttpResponse.KanBanDetailBeanData>() {

            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KanBanDetailBeanData data) {
                LoadingDialog.disDialog();
                if (result == 0) {
                    if (data != null) {
                        List<KanBanDetailBean.ProjectImgBean> project_img = data.getData().getProject_img();
                        if (!project_img.isEmpty() && project_img.size() > 0) {
                            projectImgAdaptere = new ProjectImgAdaptere(getActivity(), project_img);
                            rvImages.setAdapter(projectImgAdaptere);

                            projectImgAdaptere.setOnItemClickListener(new ProjectImgAdaptere.OnItemClickListener() {
                                @Override
                                public void onClick(int postion, List<KanBanDetailBean.ProjectImgBean> dataList) {
                                    Bundle bundle = new Bundle();
                                    KanBanDetailBean.ProjectImgBean projectImgBean = dataList.get(postion);
                                    bundle.putSerializable("Bean", projectImgBean);
                                    bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                                    IntentUtils.startActivity(getActivity(), BigImageActivity.class, bundle);

                                }
                            });

                        } else {
                            m_img_line.setVisibility(View.GONE);
                        }


                        final KanBanDetailBean.ProjectCorrectBean projectCorrect = data.getData().getProjectCorrect();
                        if (projectCorrect != null) {
//
                            m_zg_zhong.setText(projectCorrect.getDoingStatus() + "");
//                                    String s = m_zg_zhong.getText().toString();
//                                    Log.e("值", s);
                            m_zg_wancheng.setText(projectCorrect.getFinishStatus() + "");
                            m_zg_yan.setText(projectCorrect.getWaitStatus() + "");
                            m_zg_hl.setText(projectCorrect.getRefuseStatus() + "");
//                            int i = projectCorrect.getDoingStatus() + projectCorrect.getFinishStatus() + projectCorrect.getWaitStatus() + projectCorrect.getRefuseStatus();
                            textview_zg.setText(projectCorrect.getAllStatus() + "");

                            m_wei_zhi.setText(projectCorrect.getMissionRedUnfinish() + "");

                            ArrayList<KanBanDetailBean.ProjectCorrectBean.DateDataBean> dateDataList = projectCorrect.getDateData();
                            if (dateDataList != null) {
                                getData(dateDataList);
                            }
                            if (projectCorrect.getNotOverDue() != 0 || projectCorrect.getOverDueFinish() != 0 || projectCorrect.getOverDueNotFinish() != 0) {
                                setData(projectCorrect);
                            } else {
                                completeYuan.setVisibility(View.GONE);
                                m_no_text.setVisibility(View.VISIBLE);
                            }
                        }

                        //完成率 忽略旅 一次通过率 平均整改时间
                        m_CompletedView_one.setProgressNotInUiThread(1, projectCorrect.getFinishRate());
                        m_CompletedView_two.setProgressNotInUiThread(1, projectCorrect.getRefuseRate());
                        m_CompletedView_three.setProgressNotInUiThread(1, projectCorrect.getDisposableRate());
                        m_CompletedView_four.setmTxtHint1(2, projectCorrect.getAverageTime());
                    }
                    final KanBanDetailBean.PatrolBean patrol = data.getData().getPatrol();
                    if (patrol != null) {
                        m_creat_task.setText(patrol.getMissionAll() + "");
                        m_wancheng_task.setText(patrol.getMissionComplete() + "");
                        m_pinjun.setText(formatDouble(patrol.getMissionScore()) + "");
                        m_progress.setProgress((int) patrol.getMissionScore());
                        text_zhi.setText(patrol.getMissionRed() + "");
                        text_aq.setText(patrol.getMissionRedRate() + "%");

                    }
                    details = data.getData().getDetails();
                }
            }
//            }
        });

    }

    public static String formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.DOWN);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }


    private void initChar() {
        m_line_char.setDrawGridBackground(true); // 是否显示表格颜色
//        m_line_char.setDrawBorders(true);
        m_line_char.setGridBackgroundColor(Color.parseColor("#CCCCCC") & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度


//        m_line_char.setBackgroundColor(Color.WHITE);
        Legend legend = m_line_char.getLegend();
        legend.setEnabled(false);
        legend.setXEntrySpace(20f);// 条目的水平间距
        legend.setYEntrySpace(20f);// 条目的垂直间距
        legend.setFormToTextSpace(20f);

        ArrayList<Float> floats = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            float value = list.get(i).getValue();
            floats.add(value);
        }
        ArrayList<Entry> entries = new ArrayList<>();
        if (entries.size() > 0) {
            mMin = floats.get(0);
            mMax = floats.get(0);
        }
        for (int i = 0; i < floats.size(); i++) {
            if (mMin > floats.get(i)) {
                mMin = floats.get(i);
            }
            if (mMax < floats.get(i)) {
                mMax = floats.get(i);
            }
            entries.add(new Entry(i, floats.get(i)));
        }


        YAxis leftYAxis = m_line_char.getAxisLeft();
        YAxis rightYaxis = m_line_char.getAxisRight();

        //保证Y轴从0开始，不然会上移一点
//        rightYaxis.setAxisMinimum(0f);
        leftYAxis.setAxisMinimum(0f);

//        ArrayList<Float> maxfloats = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            float value = list.get(i).getValue();
//            maxfloats.add(value);
//        }

        int i = (int) ((mMax / 10) + 2) * 10;
        leftYAxis.setAxisMaximum(i);

        //虚线的宽度
        leftYAxis.setGridLineWidth(0.5f);
        leftYAxis.setDrawGridLines(true);
        if (mMax == 0) {
            leftYAxis.setGranularity(5);
        } else {
            leftYAxis.setGranularity(1);
        }
        leftYAxis.setEnabled(true);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYaxis.setEnabled(false);
//        rightYaxis.enableGridDashedLine(10f, 10f, 0f);

        XAxis xAxis = m_line_char.getXAxis();
        //X轴设置显示位置在底部
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1);
        xAxis.setGridLineWidth(0.5f);
        xAxis.setDrawGridLines(true);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        m_line_char.getDescription().setEnabled(false);


        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.e("TAG", "----->getFormattedValue: " + value);
                //设置 xAxis.setGranularity(1);后 value是从0开始的，每次加1，
                int v = (int) value;
                if (v <= list.size() && v >= 0) {
                    String st = list.get(v).getTime();
                    String tim1 = "";
                    tim1 = st;
                    return tim1;
                } else {
                    return null;
                }
            }
        };


        List<String> list_time = new ArrayList<>();
        for (Bean sleepModel : list) {
            String st = sleepModel.getTime();

            list_time.add(st);

        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list_time));
//        xAxis.s
        LineDataSet lineDataSet = new LineDataSet(entries, "");

        //设置渐变色
        lineDataSet.setDrawFilled(true);
        if (Build.VERSION.SDK_INT >= 18) {
            lineDataSet.setFillDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line_gradient_bg_shape));
        } else {
            //设置填充的颜色
            lineDataSet.setFillColor(Color.parseColor("#445dbcfe"));
        }
        lineDataSet.setFillAlpha(10);

//        m_line_char.setAlpha(0.8f);
        //设置是否显示点的坐标值
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextColor(Color.parseColor("#333333"));
        lineDataSet.setValueTextSize(10f);
        //圆圈颜色
        lineDataSet.setCircleColor(Color.parseColor("#33A0FF"));
        lineDataSet.setColor(Color.parseColor("#33A0FF"));
        lineDataSet.enableDashedLine(5f, 10f, 0f);//三个参数，第一个线宽长度，第二个线段之间宽度，第三个一般为0，是个补偿


        final LineData lineData = new LineData(lineDataSet);

        lineData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                String str = v + "";
                if (str.length() == 0) {
                    return str;
                }
                return str.substring(0, str.indexOf("."));//设置自己的返回位数
            }
        });

        m_line_char.setData(lineData);

        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                m_line_char.animateX(1500, Easing.EaseInOutQuad);
            }
        });


        m_line_char.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        scoller.requestDisallowInterceptTouchEvent(true);
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        scoller.requestDisallowInterceptTouchEvent(false);
                        break;
                    }
                }
                return false;
            }
        });

    }

    private ArrayList<Bean> list;

    private void getData(ArrayList<KanBanDetailBean.ProjectCorrectBean.DateDataBean> dateDataList) {
        list = new ArrayList<>();
        for (int i = 0; i < dateDataList.size(); i++) {
            Bean bean = new Bean(dateDataList.get(i).getDate(), dateDataList.get(i).getNumber());
            list.add(bean);
        }
        initChar();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private DatePicker2Dialog beginPickerDialog, endPickerDialog;
    private String begin = "";
    private String end = "";

    private void showDatePickerDialog(final int type) {
        closeBeginPickerDialog();
        beginPickerDialog = new DatePicker2Dialog(getActivity(), "", DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePicker2Dialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
//                Log.d("返回日期",i+":;"+param[0]+"::"+param[1]);
                if (confirm) {
//                    showWeekOrMonth("");
                    if (type == 1) {
                        endTime = param[0];
                    } else {
                        end = param[0];

                    }

                    showEndPickerDialog(type);
//                    end = "";
//                    tvBegin.setText(begin);
//                    tvEnd.setText(end);
//
                }
                closeBeginPickerDialog();
            }
        });
        beginPickerDialog.show();
    }

    private void showEndPickerDialog(final int type) {
//        if (TextUtils.isEmpty(begin)) {
//            showToast("请选择开始日期");
//            return;
//        }
        String time;
        if (type == 1) {
            time = endTime;
        } else {
            time = end;
        }

        closeEndPickerDialog();
        endPickerDialog = new DatePicker2Dialog(getActivity(), time, DateTimePickerView.TYPE_YEAR_MONTH_DAY, new DatePicker2Dialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
//                    showWeekOrMonth("");
                    if (type == 1) {
                        startTime = param[0];
                    } else {
                        begin = param[0];
                    }
                    m_zg_time.setText("(" + end + "-" + begin + ")");
                    m_xj_time.setText("(" + endTime + "-" + startTime + ")");
                    getImgData();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

}

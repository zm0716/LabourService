package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AllBuildBean;
import com.agilefinger.labourservice.bean.DetailBean;
import com.agilefinger.labourservice.bean.PicBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.StopInspectionDialog;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
* 巡检任务详情
* */
public class InspectionDetailActivity extends BaseActivity {
    public static List<PicBean> imgAllList = new ArrayList<>();

    public static String NeiRong = "";

    public static String BuildId = "";
    public static String BuildName = "";


    @BindView(R.id.text_dx)
    TextView textDx;
    @BindView(R.id.text_bh)
    TextView textBh;
    @BindView(R.id.text_person)
    TextView textPerson;
    @BindView(R.id.text_bz)
    TextView textBz;
    @BindView(R.id.text_zt)
    TextView textZt;
    @BindView(R.id.text_mc)
    TextView textMc;
    @BindView(R.id.text_bbhh)
    TextView textBbhh;
    @BindView(R.id.text_lb)
    TextView textLb;
    @BindView(R.id.text_begintime)
    TextView textBegintime;
    @BindView(R.id.text_gongqi)
    TextView textGongqi;
    @BindView(R.id.text_mianji)
    TextView textMianji;
    @BindView(R.id.text_fuwu)
    TextView textFuwu;
    @BindView(R.id.text_kfs)
    TextView textKfs;
    @BindView(R.id.text_lxr)
    TextView textLxr;
    @BindView(R.id.text_phonee)
    TextView textPhonee;
    @BindView(R.id.linearlayout_jiance)
    LinearLayout linearlayoutJiance;
    @BindView(R.id.linearlayout_wqd)
    LinearLayout linearlayoutWqd;
    @BindView(R.id.linlayout_bg_jcbg)
    LinearLayout linlayoutBgJcbg;
    @BindView(R.id.linearlayout_tz)
    LinearLayout linearlayout_tz;
    @BindView(R.id.linearlayout_shuangshenfen)
    LinearLayout linearlayout_shuangshenfen;


    @BindView(R.id.jinyong)
    RoundTextView jinyong;
    @BindView(R.id.text_qydw)
    TextView text_qydw;
    @BindView(R.id.text_gy)
    TextView text_gy;

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.biaozhuan)
    TextView biaozhun;
    @BindView(R.id.activity_inspection_detail_tv_score)
    TextView tv_score;
    @BindView(R.id.text_address)
    TextView text_address;
    @BindView(R.id.chakanrl)
    RelativeLayout chakanrl;
    @BindView(R.id.text_aret)
    TextView textAret;
    @BindView(R.id.text_sg_address)
    TextView textSgAddress;
    @BindView(R.id.text_qy_dw)
    TextView textQyDw;
    @BindView(R.id.activity_inspection_detail_rtv_upload)
    RoundTextView activityInspectionDetailRtvUpload;
    @BindView(R.id.activity_inspection_detail_rtv_stop)
    RoundTextView activityInspectionDetailRtvStop;
    @BindView(R.id.activity_inspection_detail_sqd)
    RoundTextView activityInspectionDetailSqd;
    @BindView(R.id.activity_inspection_detail_sjy)
    RoundTextView activityInspectionDetailSjy;
    @BindView(R.id.activity_rectification_detail_rtv_appoint)
    RoundTextView activityRectificationDetailRtvAppoint;
    @BindView(R.id.activity_rectification_detail_rtv_refuse)
    RoundTextView activityRectificationDetailRtvRefuse;
    @BindView(R.id.activity_rectification_detail_rtv_reply)
    RoundTextView activityRectificationDetailRtvReply;
    @BindView(R.id.activity_rectification_detail_ll_btn_appoint_refuse_reply)
    LinearLayout activityRectificationDetailLlBtnAppointRefuseReply;

    private String status;
    private String taskId;
    private String user_id;
    public static String taskName;
    private String project;
    private DetailBean data1;

    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_detail;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskId");
        taskName = bundle.getString("taskName");
        user_id = loginBean.getUser_id();
        setToolbar(taskName, false, false, "");

        Log.d("获取任务id", taskId + "::");
        //  detail(taskId, user_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgAllList.clear();
        NeiRong = "";
        BuildId = "";
        BuildName = "";
        detail(taskId, user_id);
    }


    /**
     * 获取详情信息
     *
     * @param taskId
     * @param user_id
     */
    private void detail(String taskId, String user_id) {
        LoadingDialog.showLoading2(this);
        HttpManager.getInstance().getDetail(taskId, user_id, new HttpEngine.OnResponseCallback<HttpResponse.Detail>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Detail data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                }
                try {
                    data1 = data.getData();
                    Log.d("任务详情", GsonUtils.toJson(data1));
                    if (data1 != null) {
                        textDx.setText(data1.getProject_name());
                        textBh.setText(data1.getMi_no());
                        textPerson.setText(data1.getInspector_name());
                        textBz.setText(data1.getTemplate_name() + "  ");
                        status = data1.getStatus();
                        textZt.setText(status);
                        textMc.setText(data1.getProject_name());
                        textBbhh.setText(data1.getProject_no());
                        textLb.setText(data1.getProject_type());
                        textBegintime.setText(data1.getProject_begin());
                        textGongqi.setText(data1.getProject_predict() + "天");
                        textMianji.setText(data1.getProject_area() + "m²");
                        textFuwu.setText(data1.getProject_bmc());
                        textKfs.setText(data1.getProject_developers());
                        textLxr.setText(data1.getProject_connect_user());
                        textPhonee.setText(data1.getProject_connect_phone());
                        project = data1.getMi_p_id();
                        text_address.setText(data1.getProject_address());
                        textAret.setText(data1.getProject_art());
                        textSgAddress.setText(data1.getProject_address());
                        textQyDw.setText(data1.getProject_sign());
//                        Log.d("项目id", ":::" + project + "::" + loginBean.getUser_id());

                        //已完成
                        if (status.equals("已完成")) {
                            tv_score.setText("" + data1.getResultPoint() + "");
                            username.setText(data1.getResultUser());
                            time.setText(data1.getResultTime());
                            biaozhun.setText(data1.getResultNo());
                        }
                        //启动检测任务

                        if (data1.getMi_inspector().equals(loginBean.getUser_id()) || status.equals("已完成")) {
                            startTask();
                        } else if (data1.getMi_creator().equals(loginBean.getUser_id())
                                && !data1.getMi_inspector().equals(loginBean.getUser_id()) && !status.equals("已完成")) {
                            if (status.equals("未启动")) {
                                linearlayoutWqd.setVisibility(View.GONE);
                                linlayoutBgJcbg.setVisibility(View.GONE);
                                linearlayoutJiance.setVisibility(View.GONE);
                                linearlayout_tz.setVisibility(View.VISIBLE);
                                linearlayout_shuangshenfen.setVisibility(View.GONE);
                                if (status.equals("未启动")) {
                                    jinyong.setText("禁用任务");
                                }
                            } else {
                                linearlayoutWqd.setVisibility(View.GONE);
                                linlayoutBgJcbg.setVisibility(View.GONE);
                                linearlayoutJiance.setVisibility(View.GONE);
                                linearlayout_tz.setVisibility(View.GONE);
                                linearlayout_shuangshenfen.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取是否存在楼栋
    private void getData() {
        final Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_check_position_info", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("获取楼栋", string);
                Gson gson = new Gson();
                final AllBuildBean allBuildBean = gson.fromJson(string, AllBuildBean.class);
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (allBuildBean.getCode() == 0) {
                            if (!allBuildBean.getData().getHas().isEmpty()) {
                                Bundle bundle = new Bundle();
                                bundle.putString("taskid", taskId + "");
                                bundle.putString("projectid", project + "");
                                bundle.putString("taskname", taskName + "");
                                IntentUtils.startActivity(InspectionDetailActivity.this, DataCollectionActivity.class, bundle);
                            } else {
                                Intent intent = new Intent(InspectionDetailActivity.this, AddAddressActivity.class);
                                intent.putExtra("projectid", project);
                                intent.putExtra("taskid", taskId);
                                intent.putExtra("type", "1");
                                startActivity(intent);
                            }
                        } else {
                            MyToast.getInstands().toastShow(InspectionDetailActivity.this, "接口出错！！！");
                        }
                    }
                });

            }
        });

    }


    /**
     * 启动检测任务
     */
    private void startTask() {

        if (status.equals("检测中")) {
            linearlayoutJiance.setVisibility(View.VISIBLE);
            linearlayoutWqd.setVisibility(View.GONE);
            linlayoutBgJcbg.setVisibility(View.GONE);
            linearlayout_tz.setVisibility(View.GONE);
            linearlayout_shuangshenfen.setVisibility(View.GONE);
        } else if (status.equals("未启动")) {
            if (data1.getMi_creator().equals(loginBean.getUser_id())) {
                linearlayout_tz.setVisibility(View.GONE);
                if (status.equals("未启动")) {
                    jinyong.setText("禁用任务");
                }
                linearlayout_shuangshenfen.setVisibility(View.VISIBLE);
                linearlayoutWqd.setVisibility(View.GONE);
                linlayoutBgJcbg.setVisibility(View.GONE);
                linearlayoutJiance.setVisibility(View.GONE);
            } else {
                linearlayout_shuangshenfen.setVisibility(View.GONE);
                linearlayoutWqd.setVisibility(View.VISIBLE);
                linlayoutBgJcbg.setVisibility(View.GONE);
                linearlayoutJiance.setVisibility(View.GONE);
                linearlayout_tz.setVisibility(View.GONE);
            }

            linearlayoutWqd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qiDong();
                }
            });
        } else if (status.equals("已完成")) {
            linlayoutBgJcbg.setVisibility(View.VISIBLE);
            linearlayoutWqd.setVisibility(View.GONE);

            linearlayoutJiance.setVisibility(View.GONE);
            linearlayout_tz.setVisibility(View.GONE);
            linearlayout_shuangshenfen.setVisibility(View.GONE);
        } else if (status.equals("已停止")) {

            linearlayoutWqd.setVisibility(View.GONE);
            linlayoutBgJcbg.setVisibility(View.GONE);
            linearlayoutJiance.setVisibility(View.GONE);
            linearlayout_tz.setVisibility(View.GONE);
            linearlayout_shuangshenfen.setVisibility(View.GONE);
        }

    }

    private void qiDong() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(InspectionDetailActivity.this, R.style.Theme_Light_Dialog);
                View view = LayoutInflater.from(InspectionDetailActivity.this).inflate(R.layout.pltanchu, null);

                //获得dialog的window窗口
                Window window = dialog.getWindow();
                //设置dialog在屏幕底部
                window.setGravity(Gravity.CENTER);
                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
                window.setWindowAnimations(R.style.dialogStyle);
                window.getDecorView().setPadding(50, 0, 50, 0);
                //获得window窗口的属性
                WindowManager.LayoutParams lp = window.getAttributes();
                //设置窗口宽度为充满全屏
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //设置窗口高度为包裹内容
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                //将设置好的属性set回去
                window.setAttributes(lp);
                //将自定义布局加载到dialog上
                dialog.setContentView(view);
                dialog.show();
                TextView dialog_confirm_cus_del_tv_title = view.findViewById(R.id.titileaa);
                TextView ok = view.findViewById(R.id.dialog_confirm_cus_del_tv_ok);
                TextView cancel = view.findViewById(R.id.dialog_confirm_cus_del_tv_cancel);
                dialog_confirm_cus_del_tv_title.setText("确认启动任务？");
                ok.setText("确定");
                cancel.setText("取消");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpManager.getInstance().startTask(taskId, new HttpEngine.OnResponseCallback<HttpResponse.Strings>() {
                            @Override
                            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Strings data) {
                                if (result != 0) {
                                    showToast(retmsg);
                                }
                                dialog.dismiss();
                                Intent intent = new Intent(InspectionDetailActivity.this, AddAddressActivity.class);
                                intent.putExtra("projectid", project);
                                intent.putExtra("taskid", taskId);
                                intent.putExtra("type", "1");
                                startActivity(intent);
                            }
                        });

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    @OnClick({
            R.id.activity_inspection_detail_rtv_stop,
            R.id.activity_inspection_detail_rtv_upload,
            R.id.linlayout_bg_jcbg,
            R.id.chakanrl,
            R.id.linearlayout_tz,
            R.id.activity_inspection_detail_sqd,
            R.id.activity_inspection_detail_sjy,

    })
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_inspection_detail_rtv_upload:
                //是否存在楼栋
                //getData();
                if (data1.isHas_position()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("taskid", taskId + "");
                    bundle.putString("projectid", project + "");
                    bundle.putString("taskname", taskName + "");
                    IntentUtils.startActivity(InspectionDetailActivity.this, DataCollectionActivity.class, bundle);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("taskid", taskId);
                    bundle.putString("projectid", project);
                    bundle.putString("type", "1");
                    bundle.putString("taskname", taskName + "");
                    IntentUtils.startActivity(InspectionDetailActivity.this, AddAddressActivity.class, bundle);
                }
                break;
            case R.id.linlayout_bg_jcbg://检测报告
                //MyToast.getInstands().toastShow(InspectionDetailActivity.this,"暂时无法查看");
//http://域名/index.php/admin/reportphone/report_phone/
// ?rm_mi_id=2D58AFE58D10E8E9091B19637D826EAA&rm_mi_no=1&rm_score=4333
                Intent intent = new Intent(InspectionDetailActivity.this, PdfActivity.class);
                intent.putExtra("mi_id", data1.getMi_id());
                intent.putExtra("mi_no", "" + (Integer.parseInt(data1.getMi_no_no()) - 1) + "");
                intent.putExtra("m_score", "" + data1.getResultPoint() + "");
                startActivity(intent);
                //IntentUtils.startActivity(InspectionDetailActivity.this, JceBaogaoActivity.class);
                break;
            case R.id.activity_inspection_detail_rtv_stop:
                showStopInspectionDialog("停止");
                break;
            case R.id.linearlayout_tz:
                if (jinyong.getText().toString().equals("停止任务")) {
                    showStopInspectionDialog("停止");
                } else {
                    //禁用任务
                    showDelInspectionDialog("禁用");
                }

                break;
            case R.id.chakanrl:

                break;
            case R.id.activity_inspection_detail_sqd:
                qiDong();
                break;
            case R.id.activity_inspection_detail_sjy:
                showDelInspectionDialog("禁用");
                break;


        }
    }

    private void showDelInspectionDialog(String s) {
        final Dialog dialog = new Dialog(InspectionDetailActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(InspectionDetailActivity.this).inflate(R.layout.pltanchu, null);

        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(50, 0, 50, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(view);
        dialog.show();
        TextView dialog_confirm_cus_del_tv_title = view.findViewById(R.id.titileaa);
        TextView ok = view.findViewById(R.id.dialog_confirm_cus_del_tv_ok);
        TextView cancel = view.findViewById(R.id.dialog_confirm_cus_del_tv_cancel);
        dialog_confirm_cus_del_tv_title.setText("确认禁用任务？");
        ok.setText("确定");
        cancel.setText("取消");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> pram = new HashMap<>();
                pram.put("mid", taskId);
                pram.put("creator", loginBean.getUser_id());

                Log.d("禁用传值", GsonUtils.toJson(pram));
                OkHttp3Util.doPost(URL.BASE_URL_4 + "mission/mission_disabled", pram, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("禁用任务", string);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                finish();
                            }
                        });
                    }
                });
            }
        });

    }


    private StopInspectionDialog stopInspectionDialog;

    private void showStopInspectionDialog(String type) {
        closeStopInspectionDialog();
        stopInspectionDialog = new StopInspectionDialog(this, new StopInspectionDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, final String param) {
                if (confirm) {

                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (param.equals("")) {
                                ToastUtil.showShort(InspectionDetailActivity.this, "请输入原因");
                            } else {
                                Map<String, Object> pram = new HashMap<>();
                                pram.put("task_id", taskId);
                                pram.put("task_reason", param);

                                Log.d("传参", GsonUtils.toJson(pram));
                                OkHttp3Util.doPost(URL.BASE_URL_4 + "msn/stop_task", pram, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String string = response.body().string();
                                        Log.d("修改状态", string + "::");
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                closeStopInspectionDialog();
                                                detail(taskId, user_id);

                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });


                } else {
                    closeStopInspectionDialog();
                }

            }
        });
        stopInspectionDialog.show();
    }

    private void closeStopInspectionDialog() {
        if (stopInspectionDialog != null) {
            stopInspectionDialog.cancel();
        }
        stopInspectionDialog = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ConfirmSubmitInspectionDataAdapter;
import com.agilefinger.labourservice.adapter.TextListAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.EndBean;
import com.agilefinger.labourservice.bean.EndTask2Bean;
import com.agilefinger.labourservice.bean.ProjectCountyBean;
import com.agilefinger.labourservice.bean.WeatherEndBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.query.In;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.agilefinger.labourservice.activity.InspectionDetailActivity.taskName;

/*
 * 巡检任务 结束任务
 * */
public class ConfirmSubmitInspectionActivity extends BaseActivity {

    @BindView(R.id.activity_confirm_submit_inspection_rv_data_list)
    RecyclerView rvDataList;
    @BindView(R.id.buildingtv)
    TextView buildingtv;
    @BindView(R.id.remark_et)
    EditText remark_et;
    @BindView(R.id.explian_et)
    EditText explian_et;
    @BindView(R.id.end_bt)
    RoundTextView end_bt;

    @BindView(R.id.weather_tv)
    TextView weather_tv;
    @BindView(R.id.wendu_tv)
    TextView wendu_tv;
    @BindView(R.id.fengli_tv)
    TextView fengli_tv;
    @BindView(R.id.tasknum_tv)
    TextView tasknum_tv;
    @BindView(R.id.m_buing_name)
    TextView mBuingName;
    @BindView(R.id.m_text_recy)
    RecyclerView mTextRecycle;


    private ConfirmSubmitInspectionDataAdapter confirmSubmitInspectionDataAdapter;
    private String taskid;
    private String project;
    private EndBean.DataBean data;
    private String weidu;
    private String jingdu;

    @Override
    public int initLayoutView() {
        return R.layout.activity_confirm_submit_inspection;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        taskName = savedInstanceState.getString("taskName");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("taskName", taskName);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarWhite("确认提交");
        Intent intent = getIntent();
        taskid = intent.getStringExtra("taskid");
        project = intent.getStringExtra("project");
        jingdu = intent.getStringExtra("jingdu");
        weidu = intent.getStringExtra("weidu");
        Log.d("任务id，项目id", taskid + "::" + project + "::" + jingdu + "::" + weidu);


        mTextRecycle.setLayoutManager(new LinearLayoutManager(this));

        //获取天气
        getProkect();
        //获取结束信息
        getData();

    }

    @Override
    @OnClick({R.id.end_bt})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.end_bt:
                LoadingDialog.showLoading2(ConfirmSubmitInspectionActivity.this);
                end_bt.setEnabled(false);
                ///结束任务
                endTask();
                break;

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                LoadingDialog.disDialog();
                Bundle bundle = new Bundle();
                bundle.putString("taskId", taskid);
                bundle.putString("taskName", taskName);
                IntentUtils.startActivity(ConfirmSubmitInspectionActivity.this, InspectionDetailActivity.class, bundle);
                finish();
                overridePendingTransition(0, 0);
            }
            super.handleMessage(msg);
        }
    };

    ///结束任务
    private void endTask() {

        Map<String, Object> pram = new HashMap<>();
        pram.put("mi_id", taskid);
        pram.put("weather", weather_tv.getText().toString());
        pram.put("temperature", wendu_tv.getText().toString());
        pram.put("wind", fengli_tv.getText().toString());
        pram.put("workRate", explian_et.getText().toString());
        pram.put("explain", remark_et.getText().toString());
        pram.put("userID", loginBean.getUser_id());

        Log.d("结束任务", GsonUtils.toJson(pram));
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/mission_result_new", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                LoadingDialog.disDialog();
                final String string = response.body().string();
                Log.d("结束任务返回", string);

                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            end_bt.setEnabled(true);
                            Gson gson = new Gson();
                            EndTask2Bean endTask2Bean = gson.fromJson(string, EndTask2Bean.class);
                            if (endTask2Bean.getCode() == 0) {
                                LoadingDialog.showLoading2(ConfirmSubmitInspectionActivity.this);
                                showTiaoShu(endTask2Bean.getData().getCount());

                            } else {
                                MyToast.getInstands().toastShow(ConfirmSubmitInspectionActivity.this, "结束任务失败！！！");
                            }
                        } catch (Exception e) {
                            MyToast.getInstands().toastShow(ConfirmSubmitInspectionActivity.this, "结束任务失败！！！");
                        }

                    }
                });
            }
        });

    }

    //生成整改派单条数
    private void showTiaoShu(String count) {
        if (Integer.parseInt(count) > 0) {
            final Dialog dialog = new Dialog(ConfirmSubmitInspectionActivity.this, R.style.Theme_Light_Dialog);
            View view = LayoutInflater.from(ConfirmSubmitInspectionActivity.this).inflate(R.layout.pltanchu, null);

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
            LinearLayout zhan = view.findViewById(R.id.zhan);
            Button xiaoshi = view.findViewById(R.id.xiaoshi);

            TextView dialog_confirm_cus_del_tv_title = view.findViewById(R.id.titileaa);
            TextView ok = view.findViewById(R.id.dialog_confirm_cus_del_tv_ok);
            TextView cancel = view.findViewById(R.id.dialog_confirm_cus_del_tv_cancel);
            dialog_confirm_cus_del_tv_title.setText("提交成功！此任务共生成" + count + "条整改派单，请稍后查看！");
            ok.setText("我知道了");
            cancel.setText("取消");

            zhan.setVisibility(View.GONE);
            xiaoshi.setVisibility(View.VISIBLE);

            xiaoshi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  MyToast.getInstands().toastShow(ConfirmSubmitInspectionActivity.this,"生成整改派单"+endTask2Bean.getData().getCount()+"");
                    handler.sendEmptyMessageDelayed(1, 50);
                }
            });
        } else {
            handler.sendEmptyMessageDelayed(1, 50);
        }

    }

    private ProjectCountyBean.DataBean data1;

    //获取项目
    private void getProkect() {
        LoadingDialog.showLoading2(this);
        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", project);
        OkHttp3Util.doPost(URL.URL_PROJECT + "index/project_detail/project_detail", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                ThreadUtils.runOnMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
                String string = response.body().string();
                Gson gson = new Gson();
                ProjectCountyBean projectCountyBean = gson.fromJson(string, ProjectCountyBean.class);
                if (null != projectCountyBean) {
                    if (projectCountyBean.getCode() == 0) {
                        data1 = projectCountyBean.getData();
                        if (null != data1) {
                            //获取天气接口
                            getWeather(data1.getProject_province(), data1.getProject_city(), data1.getProject_area());
                        }
                    }
                }
//                        } catch (Exception e) {
//                            MyToast.getInstands().toastShow(ConfirmSubmitInspectionActivity.this, "天气获取失败");
//                            LoadingDialog.disDialog();
//                        }
//                    }
//                });


            }
        });
    }

    //获取天气接口
    private void getWeather(String project_province, String project_city, int project_area) {
        Map<String, Object> pram = new HashMap<>();
        pram.put("longitude", jingdu);
        pram.put("latitude", weidu);
        pram.put("province", project_province);
        pram.put("city", project_city);
        pram.put("area", project_area);

        Log.e("位置", "jingdu" + jingdu + "weidu" + weidu);

        OkHttp3Util.doPost(URL.URL_WEATHER + "index/tqinfo", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.e("天气详情", string);
                Gson gson = new Gson();
                final WeatherEndBean weatherEndBean = gson.fromJson(string, WeatherEndBean.class);
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weatherEndBean.getCode() == 0) {
                            WeatherEndBean.DataBean.TqContentBean tq_content = weatherEndBean.getData().getTq_content();
                            if (null != tq_content) {
                                WeatherEndBean.DataBean.TqContentBean.NowBean now = tq_content.getNow();
                                if (null != now) {
                                    if (null != weather_tv) {
                                        weather_tv.setText(now.getCond_txt());
                                        fengli_tv.setText("" + now.getWind_dir());
                                        wendu_tv.setText("" + now.getFl() + " ℃");
                                    }

                                }
                            }
                        } else {
                            //获取天气接口
//                            getWeather(data1.getProject_province(), data1.getProject_city(), data1.getProject_area());
                            MyToast.getInstands().toastShow(ConfirmSubmitInspectionActivity.this, "获取天气信息失败~");
                        }

                    }
                });
            }
        });


    }

    //获取结束信息
    private void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskid);

        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_finish_info", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    Log.d("结束信息", string);
                    Gson gson = new Gson();
                    final EndBean endBean = gson.fromJson(string, EndBean.class);
                    if (endBean.getCode() == 0) {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                data = endBean.getData();
                                if (null != data) {
                                    remark_et.setText("" + data.getRemark());
                                    List<EndBean.DataBean.ItemsBean> items = data.getItems();
                                    if (null != items && !items.isEmpty()) {
                                        confirmSubmitInspectionDataAdapter = new ConfirmSubmitInspectionDataAdapter(items);
                                        rvDataList.setNestedScrollingEnabled(false);
                                        rvDataList.setLayoutManager(new LinearLayoutManager(ConfirmSubmitInspectionActivity.this));
                                        rvDataList.setAdapter(confirmSubmitInspectionDataAdapter);
                                    }

                                    List<EndBean.DataBean.PositionBean> position = data.getPosition();


                                    TextListAdapter textListAdapter = new TextListAdapter();
                                    textListAdapter.addData(position);
                                    mTextRecycle.setAdapter(textListAdapter);
                                    String buildString = "";
                                    String buildName = "";
                                    if (null != position && !position.isEmpty()) {
                                        for (int i = 0; i < position.size(); i++) {
//                                            if (buildString.equals("")) {
                                            if (position.get(i).getName().equals("")) {
                                                buildString += position.get(i).getGroup() + "<br/>" + "<br/>";
//                                                    buildName = position.get(i).getName() + "</n>";
                                            } else {
//                                                    buildString = "<font color='#333333'> " + position.get(i).getName() + " </font>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + position.get(i).getGroup();
                                                buildName += position.get(i).getName() + "<br/>" + "<br/>";
                                                buildString += position.get(i).getGroup() + "<br/>" + "<br/>";
                                            }


//                                            } else {
//                                                if (position.get(i).getName().equals("")) {
//                                                    buildString =position.get(i).getGroup() + "</n>";
//                                                } else {
//                                                    buildName=position.get(i).getName()+"<n/>";
////
////                                                    buildString = buildString + " <br/>" + "<font color='#333333'> " + position.get(i).getName() + " </font>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + position.get(i).getGroup();
//                                                }
//
//                                            }
                                        }
                                    }

                                    mBuingName.setText(Html.fromHtml(buildName));
//                                    buildingName = position.get(i).getName() + "</n>";
                                    buildingtv.setText(Html.fromHtml(buildString));
                                    tasknum_tv.setText("本任务共" + data.getTotal() + "个检测项，您已检测完成" + data.getDone() + "项");
                                    // confirmSubmitInspectionDataAdapter.setNewData(LoadData.getStringList(items));
                                }
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            //do something.
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }
}

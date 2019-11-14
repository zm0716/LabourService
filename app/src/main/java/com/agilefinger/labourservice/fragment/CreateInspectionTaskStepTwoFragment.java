package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.ChooseInspectionContentOneActivity;
import com.agilefinger.labourservice.activity.CreateInspectionTaskActivity;
import com.agilefinger.labourservice.adapter.MoKuaiAdapter;
import com.agilefinger.labourservice.adapter.PersonAdaper;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.AddTwoBean;
import com.agilefinger.labourservice.bean.JianCeRenBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.view.dialog.DateDialog;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.baidu.mapapi.map.Text;
import com.bigkoo.pickerview.TimePickerView;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 86251 on 2019/5/28.
 */

@SuppressLint("ValidFragment")
public class CreateInspectionTaskStepTwoFragment extends JBaseFragment {

    @BindView(R.id.layout_rw_xzjcnr)
    LinearLayout layoutRwXzjcnr;
    Unbinder unbinder;
    @BindView(R.id.text_dx)
    public TextView textDx;
    @BindView(R.id.linearlayout_zhixing)
    LinearLayout linearlayoutZhixing;
    @BindView(R.id.text_tuliao)
    TextView textTuliao;
    @BindView(R.id.template_tv)
    TextView template_tv;

    @BindView(R.id.image_one)
    ImageView imageOne;
    @BindView(R.id.image_two)
    ImageView imageTwo;
    @BindView(R.id.linearlayout_one)
    LinearLayout linearlayoutOne;
    @BindView(R.id.linearlayout_two)
    LinearLayout linearlayoutTwo;
    @BindView(R.id.text_jiyu)
    TextView textJiyu;
    @BindView(R.id.text_biaozhun)
    TextView textBiaozhun;
    @BindView(R.id.relayout_moban)
    RelativeLayout relayoutMoban;
    @BindView(R.id.text_number)
    TextView textNumber;
    @BindView(R.id.fragment_create_inspection_task_step_two_rtv_next)
    RoundTextView fragmentCreateInspectionTaskStepTwoRtvNext;
    @BindView(R.id.text_numbere)
    TextView textNumbere;
    @BindView(R.id.swich)
    CheckBox swich;
    @BindView(R.id.linearlayout_Rectification)
    LinearLayout linearlayout_Rectification;

    @BindView(R.id.noun)
    CheckBox noun;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.linearlayout_time)
    LinearLayout linearlayoutTime;

    @BindView(R.id.text_time2)
    TextView textTime2;
    @BindView(R.id.linearlayout_time2)
    LinearLayout linearlayoutTime2;

    @BindView(R.id.text_time3)
    TextView textTime3;
    @BindView(R.id.linearlayout_time3)
    LinearLayout linearlayoutTime3;

    @BindView(R.id.text_time4)
    TextView textTime4;
    @BindView(R.id.linearlayout_time4)
    LinearLayout linearlayoutTime4;

    @BindView(R.id.text_time5)
    TextView textTime5;
    @BindView(R.id.linearlayout_time5)
    LinearLayout linearlayoutTime5;

    @BindView(R.id.text_time6)
    TextView textTime6;
    @BindView(R.id.linearlayout_time6)
    LinearLayout linearlayoutTime6;

    @BindView(R.id.text_time7)
    TextView textTime7;
    @BindView(R.id.linearlayout_time7)
    LinearLayout linearlayoutTime7;

    @BindView(R.id.text_time8)
    TextView textTime8;
    @BindView(R.id.linearlayout_time8)
    LinearLayout linearlayoutTime8;

    @BindView(R.id.text_time9)
    TextView textTime9;
    @BindView(R.id.linearlayout_time9)
    LinearLayout linearlayoutTime9;

    @BindView(R.id.text_time10)
    TextView textTime10;
    @BindView(R.id.linearlayout_time10)
    LinearLayout linearlayoutTime10;

    @BindView(R.id.fragment_jian)
    RoundTextView fragmentJian;
    @BindView(R.id.fragment_jia)
    RoundTextView fragmentJia;
    @BindView(R.id.zhixingren)
    TextView zhixingren;

    @BindView(R.id.Rectification_time)
    TextView Rectification_time;
    @BindView(R.id.m_time_linear)
    LinearLayout mTimeLinear;

    private AddTwoBean data1;
    private MoKuaiAdapter moKuaiAdapter;
    private RecyclerView recyclerview_moban;
    private String tid;
    private SharedPreferences login;

    private String isBatch = "y";
    private String enableCorrect = "n";


    public static int isOk = 0;
    private String cvid;
    private String projectId;
    private int number = 1;
    private ImageView back;
    private RecyclerView personrv;
    private RoundTextView ok;
    String companyId;
    private String checkname = "";
    private String checkid = "";
    private DateDialog dateDialog;
    private DatePickerDialog datePickerDialog;
    private TextView m_text_time;
    private String id;

    @SuppressLint("ValidFragment")
    public CreateInspectionTaskStepTwoFragment(String companyId) {
        this.companyId = companyId;
    }

    public void setId(String id) {
        this.id = id;

        getIndex();
    }

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_create_inspection_task_step_two;
    }


    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        getAddView(number);
        //开关
        swich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isBatch = "y";
                    // Toast.makeText(getActivity(), "" + isBatch, Toast.LENGTH_SHORT).show();
                } else {
                    isBatch = "n";
                    // Toast.makeText(getActivity(), "" + isBatch, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //开关
        noun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableCorrect = "y";
                    linearlayout_Rectification.setVisibility(View.VISIBLE);

                    // Toast.makeText(getActivity(), "" + isBatch, Toast.LENGTH_SHORT).show();
                } else {
                    enableCorrect = "n";
                    linearlayout_Rectification.setVisibility(View.INVISIBLE);
                    // Toast.makeText(getActivity(), "" + isBatch, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void lazyLoadOnlyOne() {
        // getIndex();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

//        //  getIndex();
//        //检测项与检测分类id
//
//        //信息获取
//        //看不懂以前开发在干嘛，就这样继续做了
//        login = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
//        projectId = login.getString("projectId", "");
//        String name = login.getString("companyName", "");
//
//        Log.i("aaaa", "" + projectId + "::" + companyId);
//        textDx.setText(name);
//        getIndex();

    }


    private int num = 0;

    @Override
    public void onResume() {
        super.onResume();
        Log.d("进入当前页面", "进入");
//        Log.e("每次可见", id);
        if (num == 1 && isOk == 1) {
            final String itemID = login.getString("resultItemString", "");
            if (!itemID.equals("")) {
                String[] split = itemID.split(",");
                Log.d("筛选后条数", split.length + "::" + itemID);
                textNumber.setText(split.length + "项");
            }
        }
    }


    private void getIndex() {

        //信息获取
        //看不懂以前开发在干嘛，就这样继续做了
        login = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        projectId = login.getString("projectId", "");
        String name = login.getString("companyName", "");

        Log.i("aaaa", "" + projectId + "::" + companyId);
        textDx.setText(name);


//
//        this.id=projectId;
//
//        Log.e("AAAAAAA",projectId);
        HttpManager.getInstance().addTwoList(projectId, companyId, new HttpEngine.OnResponseCallback<HttpResponse.ZengTwo>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.ZengTwo data) {
                if (result == 1) {
                    //  ToastUtils.showShort(retmsg);
                    return;
                }
                data1 = data.getData();
                if (data1 != null) {

                    String is_batch = data1.getIs_batch();
                    if (!is_batch.equals("y")) {
                        swich.setFocusable(false);
                    }
                    String is_check = data1.getIs_check();
                    if (is_check.equals("y")) {
                        swich.setChecked(true);
                    } else {
                        swich.setChecked(false);
                    }

                    List<AddTwoBean.DefaultTemplateBean> defaultTemplate = data1.getDefaultTemplate();
                    if (null != defaultTemplate && !defaultTemplate.isEmpty()) {
                        if (data1.getPname().equals("")) {
                            linearlayoutOne.setClickable(false);
                            imageTwo.setImageResource(R.drawable.ic_checked);
                            imageOne.setImageResource(R.drawable.ic_unchecked);
                            textBiaozhun.setTextColor(getActivity().getResources().getColor(R.color.blue_2093ff));
                            textJiyu.setTextColor(getActivity().getResources().getColor(R.color.black_333333));
                        } else {
                            imageOne.setImageResource(R.drawable.ic_checked);
                            imageTwo.setImageResource(R.drawable.ic_unchecked);
                            textJiyu.setTextColor(getActivity().getResources().getColor(R.color.blue_2093ff));
                            textBiaozhun.setTextColor(getActivity().getResources().getColor(R.color.black_333333));

                        }
                        tid = data1.getCvid();
                        cvid = data1.getCvid();
                        //检测任务信息获取
                        task(data1);
                    }
                }
            }
        });

    }

    //检测任务信息获取
    private void task(AddTwoBean data1) {
        textTuliao.setText(data1.getCvname());
        template_tv.setText(data1.getCvname());
    }

    //检测模板
    private void template(List<AddTwoBean.DefaultTemplateBean> defaultTemplate) {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_xm_moban, null);
//        ((ViewGroup) view.getParent()).removeView(view);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
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

        recyclerview_moban = view.findViewById(R.id.recyclerview_moban);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview_moban.setLayoutManager(linearLayoutManager);
        moKuaiAdapter = new MoKuaiAdapter(getActivity(), defaultTemplate);
        recyclerview_moban.setAdapter(moKuaiAdapter);

        moKuaiAdapter.setOnItemClickListener(new MoKuaiAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, final int position, MoKuaiAdapter.MyHolder holder, final List<AddTwoBean.DefaultTemplateBean> list) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        tid = list.get(position).getTid();
                        textNumber.setText("0" + "项");
                        textTuliao.setText(list.get(position).getName());
                        template_tv.setText(list.get(position).getName());
                        dialog.dismiss();
                    }
                });

            }
        });
    }


  /*  //选择检测数量
    private void detectnumber(String tid) {
        HttpManager.getInstance().addNumber(tid, new HttpEngine.OnResponseCallback<HttpResponse.Strings>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Strings data) {
                if (result == 1) {
                    ToastUtils.showShort(retmsg);
                    return;
                }
                String data2 = data.getData();
                if (data2 != null) {
//                  Toast.makeText(getActivity(), "asd" + data2, Toast.LENGTH_SHORT).show();
                    textNumber.setText("0" + "项");
                }
            }
        });
    }*/

    @Override
    @OnClick({R.id.fragment_create_inspection_task_step_two_rtv_next,
            R.id.layout_rw_xzjcnr, R.id.linearlayout_zhixing,
            R.id.linearlayout_one, R.id.linearlayout_two,
            R.id.relayout_moban, R.id.linearlayout_time,
            R.id.linearlayout_time2, R.id.linearlayout_time4, R.id.linearlayout_time5,
            R.id.linearlayout_time3, R.id.linearlayout_time7, R.id.linearlayout_time6,
            R.id.linearlayout_time8, R.id.linearlayout_time9, R.id.linearlayout_time10,
            R.id.linearlayout_Rectification})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_rw_xzjcnr:
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!textTuliao.getText().toString().equals("")) {

                            Intent intent = new Intent(getActivity(), ChooseInspectionContentOneActivity.class);
                            intent.putExtra("companyID", companyId);
                            intent.putExtra("templateID", tid);
                            //  Log.d("传值",companyId+"::"+tid);
                            startActivity(intent);
                        } else {

                            MyToast.getInstands().toastShow(getActivity(), "没有检测标准模板");
                        }
                    }
                });


                break;
            case R.id.fragment_create_inspection_task_step_two_rtv_next://下一步
                //模板内码
                Log.i("templateID", "" + cvid);
                //检测项与检测分类id
                final String itemID = login.getString("resultItemString", "");
                Log.i("itemID", "" + itemID);
                final String itemType = login.getString("resultCatalogString", "");
                Log.i("itemType", "" + itemType);
                //用户id
                final String user_id = loginBean.getUser_id();
                Log.i("user_id", "" + user_id);
                //项目内码
                Log.i("projectId", "" + projectId);
                //次数
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (zhixingren.getText().toString().equals("选择执行人")) {
                            ToastUtil.showShort(getActivity(), "请选择执行人");
                        } else if (textNumber.getText().toString().equals("0项")) {
                            ToastUtil.showShort(getActivity(), "请选择检查项");
                        } else if (Rectification_time.getText().toString().equals("整改日期") && enableCorrect.equals("y")) {
                            ToastUtil.showShort(getActivity(), "请选择整改日期");
                        } else {
                            //检测人
                            String zhengTime = "";
                            if (enableCorrect.equals("y")) {
                                zhengTime = Rectification_time.getText().toString() + " 23:59";
                            }

//                            String time = "";
                            StringBuffer sb = new StringBuffer();
                            for (Integer id : map.keySet()) {
                                if (id != number) {
                                    sb.append(map.get(id)).append(",");
                                } else {
                                    sb.append(map.get(id));
                                }
                            }
//                            Log.e("AAAAAAAAAAAAAAAAA", sb.toString());
//                            Log.e("map", map.toString());

                            LoadingDialog.showLoading(getActivity());
                            HttpManager.getInstance().createWc(tid, itemID, itemType, user_id, projectId,
                                    number, isBatch, checkid, sb.toString(), enableCorrect, zhengTime, new HttpEngine.OnResponseCallback<HttpResponse.Complete>() {
                                        @Override
                                        public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Complete data) {
                                            LoadingDialog.disDialog();
                                            Log.d("创建任务是否成功", result + "::");
                                            if (result == 1) {
                                                ThreadUtils.runOnMainThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        MyToast.getInstands().toastShow(getActivity(), "创建任务失败");
                                                    }
                                                });

                                                return;
                                            } else {
                                                //不明白以前开发在干嘛，省时间就这样继续做了
                                                SharedPreferences login = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor edit = login.edit();
                                                edit.clear();
                                                edit.commit();
                                                ((CreateInspectionTaskActivity) getActivity()).toNextStepCallback("2");
                                            }

                                            // Toast.makeText(getActivity(), "" + data.getData().getP_name(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }

                    }
                });

                break;
            case R.id.linearlayout_zhixing:
                //获取检测人
                LoadingDialog.showLoading(getActivity());
                Map<String, Object> pram = new HashMap<>();
                pram.put("companyID", companyId);
                OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_check_user_list_with_company", pram, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("获取检测人", projectId + string + "::");
                        Gson gson = new Gson();
                        final JianCeRenBean jianCeRenBean = gson.fromJson(string, JianCeRenBean.class);
                        if (null != jianCeRenBean) {
                            if (jianCeRenBean.getCode() == 0) {
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.disDialog();
                                        List<JianCeRenBean.DataBean> mList = jianCeRenBean.getData();
                                        checkPerson(mList);
                                    }
                                });

                            }
                        }
                    }
                });
              /*  HttpManager.getInstance().zg_zp_man(loginBean.getUser_id(), companyId, new HttpEngine.OnResponseCallback<HttpResponse.ZGZPMan>() {
                            @Override
                            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.ZGZPMan data) {
                                LoadingDialog.disDialog();
                                if (result != 0) {
                                    ToastUtil.showShort(getActivity(),retmsg);
                                    return;
                                }
                                Log.d("获取检测人",GsonUtils.toJson(data.getData().getAssign_man_list())+"::"+GsonUtils.toJson(data));
                                checkPerson(data.getData().getAssign_man_list());
                            }
                });*/

                break;
            case R.id.linearlayout_one:
                //  Toast.makeText(getActivity(), "one", Toast.LENGTH_SHORT).show();
                imageOne.setImageResource(R.drawable.ic_checked);
                imageTwo.setImageResource(R.drawable.ic_unchecked);
                textJiyu.setTextColor(getActivity().getResources().getColor(R.color.blue_2093ff));
                textBiaozhun.setTextColor(getActivity().getResources().getColor(R.color.black_333333));
                break;
            case R.id.linearlayout_two:
                //Toast.makeText(getActivity(), "Two", Toast.LENGTH_SHORT).show();
                imageTwo.setImageResource(R.drawable.ic_checked);
                imageOne.setImageResource(R.drawable.ic_unchecked);
                textBiaozhun.setTextColor(getActivity().getResources().getColor(R.color.blue_2093ff));
                textJiyu.setTextColor(getActivity().getResources().getColor(R.color.black_333333));
                break;
            case R.id.relayout_moban:
                //选择检测标准、选择模板
                if (!template_tv.getText().toString().equals("")) {
                    List<AddTwoBean.DefaultTemplateBean> defaultTemplate = data1.getDefaultTemplate();
                    template(defaultTemplate);
                } else {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.getInstands().toastShow(getActivity(), "没有检测标准模板");
                        }
                    });
                }
                break;
            //选择截止时间
            case R.id.linearlayout_time:
                //选择截止日期
                setDateTime(textTime);
                break;
            //选择截止时间
            case R.id.linearlayout_time2:
                //选择截止日期
                setDateTime(textTime2);
                break;
            //选择截止时间
            case R.id.linearlayout_time3:
                //选择截止日期
                setDateTime(textTime3);
                break;
            //选择截止时间
            case R.id.linearlayout_time4:
                //选择截止日期
                setDateTime(textTime4);
                break;
            //选择截止时间
            case R.id.linearlayout_time5:
                //选择截止日期
                setDateTime(textTime5);
                break;
            //选择截止时间
            case R.id.linearlayout_time6:
                //选择截止日期
                setDateTime(textTime6);
                break;
            //选择截止时间
            case R.id.linearlayout_time7:
                //选择截止日期
                setDateTime(textTime7);
                break;
            //选择截止时间
            case R.id.linearlayout_time8:
                //选择截止日期
                setDateTime(textTime8);
                break;
            //选择截止时间
            case R.id.linearlayout_time9:
                //选择截止日期
                setDateTime(textTime9);
                break;
            //选择截止时间
            case R.id.linearlayout_time10:
                //选择截止日期
                setDateTime(textTime10);
                break;
            //选择截止时间
            case R.id.linearlayout_Rectification:
                hintKeyBoard();
                //获取整改时间
                getTime();
                //  showDateDialog();
                //选择截止弹框
                //timeDialog();

                break;
        }
    }

    //选择截止日期
    private void setDateTime(final TextView textTime) {
        hintKeyBoard();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        calendar.set(year, month, day);
        startDate.set(year, month, day);
        endDate.set(year + 10, month, day);
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String datee = sDateFormat.format(date);
                textTime.setText(datee);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                // 默认全部显示
                .setCancelText("取消")
                //取消按钮文字
                .setSubmitText("确定")
                //确认按钮文字
                .setOutSideCancelable(true)
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate, endDate)
                .setDate(calendar)
                //起始终止年月日设定
                .isCenterLabel(false)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();
    }


    private LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

    //选择截止日期  张孟改
    private void setDateTime2(final int id, final TextView textTime) {
        hintKeyBoard();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        calendar.set(year, month, day);
        startDate.set(year, month, day);
        endDate.set(year + 10, month, day);
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String datee = sDateFormat.format(date);
                textTime.setText(datee);
                map.put(id, datee);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                // 默认全部显示
                .setCancelText("取消")
                //取消按钮文字
                .setSubmitText("确定")
                //确认按钮文字
                .setOutSideCancelable(true)
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate, endDate)
                .setDate(calendar)
                //起始终止年月日设定
                .isCenterLabel(false)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();
    }

    private void closeDateDialog() {
        if (dateDialog != null) {
            dateDialog.cancel();
            dateDialog = null;
        }
    }

    /**
     * 整改期限选择框
     */
    private void showDateDialog() {
        closeDateDialog();
        dateDialog = new DateDialog(getActivity(), new DateDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    if (Constants.CUSTOMIZE.equals(param[0])) {
                        showDatePickerDialog();
                    } else {
                        String s = DateFormatUtils.formatDeadline2(Integer.parseInt(param[0]));
                        Rectification_time.setText(DateFormatUtils.formatDeadline(Integer.parseInt(param[0])));

                    }
                }
                closeDateDialog();
            }
        });
        dateDialog.show();
    }

    private void closeDatePickerDialog() {
        if (datePickerDialog != null) {
            datePickerDialog.cancel();
            datePickerDialog = null;
        }
    }

    /**
     * 自定义时间选择器弹框
     */
    private void showDatePickerDialog() {
        closeDatePickerDialog();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    String deadline = param[0];
                    Rectification_time.setText(param[1]);
                    //验证

                }
                closeDatePickerDialog();
            }
        });
        datePickerDialog.show();
    }


    //获取整改时间
    private void getTime() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        calendar.set(year, month, day);
        startDate.set(year, month, day);
        endDate.set(year + 10, month, day);
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = sDateFormat.format(date);
                Rectification_time.setText(format);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                // 默认全部显示
                .setCancelText("取消")
                //取消按钮文字
                .setSubmitText("确定")
                //确认按钮文字
                .setOutSideCancelable(true)
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate, endDate)
                .setDate(calendar)
                //起始终止年月日设定
                .isCenterLabel(false)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvTime.show();
    }

    //日期
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示.
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //选择执行人
    private void checkPerson(final List<JianCeRenBean.DataBean> assign_man_list) {

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Light_Dialog8);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_xzpersonnelactivity, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.RIGHT);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(view);
        dialog.setCancelable(false);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            view.setPadding(0, result+3, 0, 0);
        }*/
        dialog.show();
        back = view.findViewById(R.id.layout_toolbar_iv_back);
        personrv = view.findViewById(R.id.rectcler_xz_yg);
        ok = view.findViewById(R.id.activity_xzqd_personr_rtv_upload);

        personrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        personrv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 10;
                outRect.left = 20;
                outRect.right = 20;
            }
        });

        final PersonAdaper personAdaper = new PersonAdaper(getActivity(), assign_man_list);
        personrv.setAdapter(personAdaper);

        personAdaper.setOnItemClickListener(new PersonAdaper.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, PersonAdaper.HmHolder holder, List<JianCeRenBean.DataBean> list) {
                for (int i = 0; i < assign_man_list.size(); i++) {
                    if (i == position) {
                        if (assign_man_list.get(i).isCheck()) {
                            checkname = "";
                            checkid = "";
                            assign_man_list.get(i).setCheck(false);
                        } else {
                            checkname = assign_man_list.get(position).getName();
                            checkid = "" + assign_man_list.get(position).getId() + "";
                            assign_man_list.get(i).setCheck(true);
                        }
                    } else {
                        assign_man_list.get(i).setCheck(false);
                    }
                }
                Log.d("点击的检测人", checkname + "::" + checkid + "::" + position);
                personAdaper.notifyDataSetChanged();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkid.equals("")) {
                    zhixingren.setText(checkname);
                }
                dialog.dismiss();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (KeyEvent.ACTION_UP == event.getAction()) {
                        Log.d("点击返回", "返回");
                        dialog.dismiss();
                    }
                    return false;
                } else {
                    return false; //默认返回 false
                }

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        num = 1;
        isOk = 0;
        getIndex();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_jian, R.id.fragment_jia})
    public void onViewClicked(View view) {
        String numberr = textNumbere.getText().toString();
        number = Integer.parseInt(numberr);
        switch (view.getId()) {
            case R.id.fragment_jian:
                number--;
                if (number < 1) {
                    Toast.makeText(getActivity(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                    number = 1;
                    textNumbere.setText(String.valueOf(number));
                    break;
                }
                textNumbere.setText(String.valueOf(number));

                //移除布局
                mTimeLinear.removeViewAt(number);
                if (!map.isEmpty()) {
                    map.remove(number + 1);
                }

                //获取展示
                //getNumberSize();
                break;
            case R.id.fragment_jia:
                if (number > 9) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.getInstands().toastShow(getActivity(), "最大频次10次");
                        }
                    });
                } else {
                    number++;
                    textNumbere.setText(String.valueOf(number));
                    getAddView(number);
                    //获取展示
                    //  getNumberSize();
                }

                break;
        }
    }

    //动态添加布局
    private void getAddView(int number) {
        final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.time_item, null, true);
        final RelativeLayout m_time_item_line = inflate.findViewById(R.id.m_time_item_line);

        inflate.setId(number);
        m_time_item_line.setId(number);
        m_time_item_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = m_time_item_line.getId();
                View childAt = mTimeLinear.getChildAt(id - 1);

                if (childAt.getId() == id) {
                    m_text_time = inflate.findViewById(R.id.m_text_time);
                    setDateTime2(id, m_text_time);
                }
            }
        });


        mTimeLinear.addView(inflate);

    }


    //获取展示
    private void getNumberSize() {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                linearlayoutTime.setVisibility(View.GONE);
                linearlayoutTime2.setVisibility(View.GONE);
                linearlayoutTime3.setVisibility(View.GONE);
                linearlayoutTime4.setVisibility(View.GONE);
                linearlayoutTime5.setVisibility(View.GONE);
                linearlayoutTime6.setVisibility(View.GONE);
                linearlayoutTime7.setVisibility(View.GONE);
                linearlayoutTime8.setVisibility(View.GONE);
                linearlayoutTime9.setVisibility(View.GONE);
                linearlayoutTime10.setVisibility(View.GONE);
                if (number == 1) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    textTime2.setText("截止日期");
                    textTime3.setText("截止日期");
                    textTime4.setText("截止日期");
                    textTime5.setText("截止日期");
                    textTime6.setText("截止日期");
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 2) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    textTime3.setText("截止日期");
                    textTime4.setText("截止日期");
                    textTime5.setText("截止日期");
                    textTime6.setText("截止日期");
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 3) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    textTime4.setText("截止日期");
                    textTime5.setText("截止日期");
                    textTime6.setText("截止日期");
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 4) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    textTime5.setText("截止日期");
                    textTime6.setText("截止日期");
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 5) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    textTime6.setText("截止日期");
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 6) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    linearlayoutTime6.setVisibility(View.VISIBLE);
                    textTime7.setText("截止日期");
                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 7) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    linearlayoutTime6.setVisibility(View.VISIBLE);
                    linearlayoutTime7.setVisibility(View.VISIBLE);

                    textTime8.setText("截止日期");
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");

                } else if (number == 8) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    linearlayoutTime6.setVisibility(View.VISIBLE);
                    linearlayoutTime7.setVisibility(View.VISIBLE);
                    linearlayoutTime8.setVisibility(View.VISIBLE);
                    textTime9.setText("截止日期");
                    textTime10.setText("截止日期");
                } else if (number == 9) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    linearlayoutTime6.setVisibility(View.VISIBLE);
                    linearlayoutTime7.setVisibility(View.VISIBLE);
                    linearlayoutTime8.setVisibility(View.VISIBLE);
                    linearlayoutTime9.setVisibility(View.VISIBLE);
                    textTime10.setText("截止日期");
                } else if (number == 10) {
                    linearlayoutTime.setVisibility(View.VISIBLE);
                    linearlayoutTime2.setVisibility(View.VISIBLE);
                    linearlayoutTime3.setVisibility(View.VISIBLE);
                    linearlayoutTime4.setVisibility(View.VISIBLE);
                    linearlayoutTime5.setVisibility(View.VISIBLE);
                    linearlayoutTime6.setVisibility(View.VISIBLE);
                    linearlayoutTime7.setVisibility(View.VISIBLE);
                    linearlayoutTime8.setVisibility(View.VISIBLE);
                    linearlayoutTime9.setVisibility(View.VISIBLE);
                    linearlayoutTime10.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}

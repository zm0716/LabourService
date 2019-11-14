package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.DetliaTopBean;
import com.agilefinger.labourservice.bean.ProjectDailsBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class CreateKanBanDatilsTwoFragment extends JBaseFragment {
    String projectId;
    @BindView(R.id.p_name)
    TextView p_name;
    @BindView(R.id.p_bianhao)
    TextView p_bianhao;
    @BindView(R.id.p_status)
    TextView p_status;
    @BindView(R.id.p_gongyi)
    TextView p_gongyi;
    @BindView(R.id.p_stime)
    TextView p_stime;
    @BindView(R.id.p_alltime)
    TextView p_alltime;
    @BindView(R.id.p_address)
    TextView p_address;
    @BindView(R.id.p_fuwu)
    TextView p_fuwu;
    @BindView(R.id.p_kaifa)
    TextView p_kaifa;
    @BindView(R.id.p_qianyue)
    TextView p_qianyue;
    @BindView(R.id.p_person)
    TextView p_person;
    @BindView(R.id.p_dengji)
    TextView p_dengji;
    @BindView(R.id.p_chuangjianren)
    TextView p_chuangjianren;
    @BindView(R.id.p_chuangtime)
    TextView p_chuangtime;
    @BindView(R.id.p_xiugairen)
    TextView p_xiugairen;
    @BindView(R.id.p_endtime)
    TextView p_endtime;
    @BindView(R.id.p_mianji)
    TextView p_mianji;
    @BindView(R.id.m_project_state)
    TextView m_project_state;
    private static Date parse;
    //    TextView date_tv;

    public CreateKanBanDatilsTwoFragment(String projectId) {
        this.projectId = projectId;
//        this.date_tv = date_tv;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //frament可见时调用
        Log.d("进入frament2", "进入" + hidden);
    }


    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.item_kanbantwo_fragment;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
    }

    @Override
    protected void lazyLoadOnlyOne() {

        getData();
    }

    private void getData() {
        LoadingDialog.showLoading(getActivity());

        HttpManager.getInstance().getKBdetailTop(projectId, new HttpEngine.OnResponseCallback<HttpResponse.KBDetliaTopData>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBDetliaTopData data) {
                LoadingDialog.disDialog();
                if (result == 0) {
                    if (data.getData() != null) {
                        DetliaTopBean data1 = data.getData();

                        p_name.setText(data1.getProject_title());
                        p_bianhao.setText(data1.getProject_number());
                        p_status.setText(data1.getEn_name());
                        p_gongyi.setText(data1.getCraft());
//                        p_mianji.setText(data1.getProject_area()+"m²");

                        String nowDateShort = getNowDateShort(data1.getProject_work_start());
                        p_stime.setText(nowDateShort);
                        p_alltime.setText(data1.getProject_deadline() + "天");

//                        p_address.setText(data.getProject_province_name()+""+data.getProject_city_name()
//                                +data.getProject_county_name()+data.getProject_address());

                        p_fuwu.setText(data1.getCompany_name());

                        p_kaifa.setText("" + data1.getDeveloper_name() + "");
//                        p_qianyue.setText(""+data.getDeveloper_signed_company()+"");
                        p_person.setText("" + data1.getManager_user_name() + "");
//                        p_dengji.setText(""+data1.getLevel_name()+"");

                        p_chuangjianren.setText(data1.getCreater_name());
                        p_chuangtime.setText(getNowDateShort(data1.getCreate_time()));
                        p_xiugairen.setText(data1.getOperator_name() + "");

                        p_endtime.setText(getNowDateShort(data1.getOperating_time()));
                        m_project_state.setText(data1.getProject_status());

//                        date_tv.setText(data1.getCreate_time());
                        p_mianji.setText(data1.getProject_area() + "㎡");
                        p_address.setText(data1.getProject_province_name() + "-" + data1.getProject_city_name()
                                + "-" + data1.getProject_county_name() + "-" + data1.getProject_address());
                        p_qianyue.setText(data1.getDeveloper_signed_company());
                        p_dengji.setText(data1.getLevel_name());
                    }

                }
            }
        });


//
//        Map<String, Object> pram=new HashMap<>();
//        pram.put("project_id",projectId);
////        pram.put("tab","1");
//        Log.d("项目详情参数",GsonUtils.toJson(pram));
//
//        OkHttp3Util.doPost(URL.URL_PROJECT + "app/detail/index", pram, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) { }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                Log.d("项目详情",string);
//                Gson gson=new Gson();
//                final ProjectDailsBean projectDailsBean = gson.fromJson(string, ProjectDailsBean.class);
//                if (null!=projectDailsBean){
//                    if (projectDailsBean.getCode()==0){
//                        ThreadUtils.runOnMainThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                LoadingDialog.disDialog();
//                                ProjectDailsBean.DataBean data = projectDailsBean.getData();
//                                if (null!=data){
//                                    p_name.setText(data.getProject_title());
//                                    p_bianhao.setText(data.getProject_number());
//                                    p_status.setText(data.getEn_name());
//                                    p_gongyi.setText(data.getCraft_name());
//                                    p_mianji.setText(data.getProject_area()+"m²");
//                                    p_stime.setText(data.getProject_work_start());
//                                    p_alltime.setText("缺失");
//
//                                    p_address.setText(data.getProject_province_name()+""+data.getProject_city_name()
//                                            +data.getProject_county_name()+data.getProject_address());
//
//                                    p_fuwu.setText(data.getOrg_name());
//
//                                    p_kaifa.setText(""+data.getDeveloper_name()+"");
//                                    p_qianyue.setText(""+data.getDeveloper_signed_company()+"");
//                                    p_person.setText(""+data.getUser_name()+"");
//                                    p_dengji.setText(""+data.getLevel_name()+"");
//
//                                    p_chuangjianren.setText(data.getCreater());
//                                    p_chuangtime.setText(data.getCreate_time());
//                                    p_xiugairen.setText(data.getOperator()+"");
//
//                                    p_endtime.setText(data.getOperating_time());
//
//
//                                    date_tv.setText(data.getCreate_time());
//
//
//
//                                }
//
//                            }
//                        });
//                    }
//                }
//
//
//
//            }
//        });
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

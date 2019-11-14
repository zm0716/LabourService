package com.agilefinger.labourservice.activity;

import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ReportAdaptere;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.OkHttp3Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LookReportActivity extends BaseActivity {
    @BindView(R.id.report_rv)
    RecyclerView report_rv;


    @Override
    public int initLayoutView() {
        return R.layout.activity_look_report;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("检测报告", false, false, "");
        //获取报告数据
        getData();
        LinearLayoutManager filterLinearLayout = new LinearLayoutManager(LookReportActivity.this);
        filterLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        report_rv.setLayoutManager(filterLinearLayout);
        report_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 16;
                outRect.right =10;
                outRect.left =10;
            }
        });
        //分割线
        report_rv.addItemDecoration(new DividerItemDecoration(LookReportActivity.this, DividerItemDecoration.VERTICAL));

    }
    //获取报告数据
    private void getData() {
        Map<String, Object> pram=new HashMap<>();
        pram.put("","");

        OkHttp3Util.doPost(URL.BASE_URL_4 + "", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Log.d("获取报告",string);

            }
        });
    }

    private void setAdapter(){
        ReportAdaptere reportAdaptere=new ReportAdaptere(LookReportActivity.this,null);
        report_rv.setAdapter(reportAdaptere);

    }

}

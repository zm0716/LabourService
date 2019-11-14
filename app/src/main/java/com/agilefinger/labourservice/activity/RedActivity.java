package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.RedLineAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.RedLineBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedActivity extends BaseActivity {

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
    @BindView(R.id.m_red_recy)
    RecyclerView mRedRecy;
    private String projectId;
    private RedLineAdapter redLineAdapter;
    private String begin;
    private String end;
    private String companyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_red;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("触发红线项", false, false, "");
        projectId = getIntent().getExtras().getString("projectId");
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        //2019-07-11
        begin = getIntent().getExtras().getString(Constants.FILTER_BEGIN_TIME);
        end = getIntent().getExtras().getString(Constants.FILTER_END_TIME);
        initRV();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initRV() {
        mRedRecy.setLayoutManager(new LinearLayoutManager(this));
        redLineAdapter = new RedLineAdapter();
        mRedRecy.setAdapter(redLineAdapter);


        redLineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RedLineBean item = (RedLineBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_PROJECT, projectId);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putString("mci_no", item.getMci_no());

                IntentUtils.startActivity(RedActivity.this, InspectionListActivity.class, bundle);

            }
        });

    }

    public void getData() {
        HttpManager.getInstance().getRedLine(projectId, begin, end,
                new HttpEngine.OnResponseCallback<HttpResponse.RedLineData>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.RedLineData data) {
                        if (result == 0) {
                            ArrayList<RedLineBean> data1 = data.getData();
                            if (!data1.isEmpty() && data1.size() > 0) {
                                redLineAdapter.setNewData(data1);
                            }
                        }
                    }
                });
    }
}

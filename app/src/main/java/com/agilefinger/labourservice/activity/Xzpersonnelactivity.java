package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.XzYgAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JcbgBean;
import com.agilefinger.labourservice.fragment.CreateInspectionTaskStepTwoFragment;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Xzpersonnelactivity extends BaseActivity {


    @BindView(R.id.rectcler_xz_yg)
    RecyclerView rectclerXzYg;
    @BindView(R.id.activity_xzqd_personr_rtv_upload)
    RoundTextView activityXzqdPersonrRtvUpload;

    @Override
    public int initLayoutView() {
        return R.layout.activity_xzpersonnelactivity;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("选择员工", false, false, "");
        //员工数据
        getPersonr();
    }

    private void getPersonr() {
        ArrayList<JcbgBean> list = new ArrayList<>();
        JcbgBean jcbgBean = new JcbgBean();
        for (int i = 0; i < 5; i++) {
            jcbgBean.setName("项目经理");
            list.add(jcbgBean);
        }
        XzYgAdapter xzYgAdapter = new XzYgAdapter(R.layout.item_xz_yg, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rectclerXzYg.setLayoutManager(linearLayoutManager);
        rectclerXzYg.setAdapter(xzYgAdapter);
        //点击条目

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_xzqd_personr_rtv_upload)
    public void onViewClicked() {
//        IntentUtils.startActivity(this,CreateInspectionTaskStepTwoFragment.class);
    }
}

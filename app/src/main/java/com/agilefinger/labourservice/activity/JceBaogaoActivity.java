package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.JceBaoGaoAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.JcbgBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JceBaogaoActivity extends BaseActivity {
    @BindView(R.id.jcbg_content_rvlist)
    RecyclerView jcbgContentRvlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int initLayoutView() {
        return R.layout.activity_jce_baogao;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("项目名称", false, false, "");
        ArrayList<JcbgBean> list = new ArrayList<>();
        JcbgBean jcbgBean = new JcbgBean();
        for (int i=0;i<5;i++){
            jcbgBean.setName("钢丝卡扣压板方向");
            jcbgBean.setScore("89");
            list.add(jcbgBean);
        }
        JceBaoGaoAdapter jceBaoGaoAdapter = new JceBaoGaoAdapter(R.layout.item_jcbg,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        jcbgContentRvlist.setLayoutManager(linearLayoutManager);
        jcbgContentRvlist.setAdapter(jceBaoGaoAdapter);


    }




}

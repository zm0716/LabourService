package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.KanBanTypeAdapter;
import com.agilefinger.labourservice.adapter.Staff2Adapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.bean.ProjectConditionBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class KanBanTypeActivity extends BaseActivity {

    @BindView(R.id.m_fu_recy)
    RecyclerView rvList;
    @BindView(R.id.m_ok)
    RoundTextView rtvOk;

    private String companyId;
    KanBanTypeAdapter staffAdapter;
    private String data, type;
    private List<KanBanTypeBean> mStaffList;
    private int curPosition = -1;

    @Override
    public int initLayoutView() {
        return R.layout.activity_kan_ban_type;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("类型", false, false, "");
        mStaffList = (List<KanBanTypeBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_TYPE);
        initRV();
    }

    @Override
    public void initData() {
        super.initData();

    }

//    @Override
//    public void initLoad() {
//        super.initLoad();
//        if (mStaffList!=null){
//
//        }
//    }

    private void initRV() {
        staffAdapter = new KanBanTypeAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(staffAdapter);
        staffAdapter.setNewData(mStaffList);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initListener() {
        super.initListener();
        staffAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KanBanTypeBean staff = (KanBanTypeBean) adapter.getItem(position);
                if (!Constants.CHOOSE_TYPE_SINGLE.equals("SINGLE")) {
                    //单选
                    if (curPosition != position && curPosition != -1) {
                        KanBanTypeBean oldStaff = (KanBanTypeBean) adapter.getItem(curPosition);
                        oldStaff.setCheck(false);
                        staffAdapter.notifyItemChanged(curPosition);
                    }
                    staff.setCheck(true);
                    curPosition = position;
                } else {//多选
                    staff.setCheck(!staff.isCheck());
                }
                staffAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<KanBanTypeBean> pList = staffAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        rtvOk.setText(txt);
    }

    @Override
    @OnClick({R.id.m_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.m_ok:
                List<KanBanTypeBean> nPList = new ArrayList<>();
                List<KanBanTypeBean> pList = staffAdapter.getData();
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).isCheck())
                        nPList.add(pList.get(i));
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA_TYPE, (Serializable) pList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

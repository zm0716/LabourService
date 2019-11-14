package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.KanBanTypeAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.KanBanTypeBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.APPUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class KanBanStatusActivity extends BaseActivity {

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
        mStaffList = (List<KanBanTypeBean>) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA_);
        initRV();
    }

    @Override
    public void initData() {
        super.initData();
    }

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

//    private void getData() {
//        LoadingDialog.showLoading(this);
//        HttpManager.getInstance().getKBKBProjectCondition(companyId, new HttpEngine.OnResponseCallback<HttpResponse.KBProjectConditionBean>() {
//            @Override
//            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.KBProjectConditionBean data) {
//                if (result == 0) {
//                    if (data.getData() != null) {
//                        final List<StaffBean> mList = new ArrayList<>();
//                        mList.clear();
//
//                        List<ProjectConditionBean.ManagerListBean> managerList = data.getData().getManagerList();
//                        for (int i = 0; i < managerList.size(); i++) {
//                            StaffBean staffBean = new StaffBean();
//                            staffBean.setId(String.valueOf(managerList.get(i).getId()));
//                            staffBean.setName(managerList.get(i).getUser_name());
//                            mList.add(staffBean);
//                        }
//                        if (mStaffList != null && mList != null) {
//                            if (mStaffList.size() > 0) {
//                                mStaffList.remove(mStaffList.size() - 1);
//                            }
//                            if (mStaffList.size() <= mList.size()) {
//                                for (int i = 0; i < mStaffList.size(); i++) {
//                                    for (int j = 0; j < mList.size(); j++) {
//                                        if (!TextUtils.isEmpty(mStaffList.get(i).getId()) && mStaffList.get(i).getId().equals(mList.get(j).getId())) {
//                                            mList.get(j).setCheck(true);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        staffAdapter.setNewData(mList);
//
//                        LoadingDialog.disDialog();
//                    }
//                }
//            }
//        });
//    }

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
                bundle.putSerializable(Constants.EXTRA_DATA_STATUS, (Serializable) pList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

package com.agilefinger.labourservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.StaffAdapter;
import com.agilefinger.labourservice.adapter.TeamAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TeamBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
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

public class TeamSelectionActivity extends BaseActivity {

    @BindView(R.id.activity_team_selection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_team_selection_rtv_ok)
    RoundTextView rtvOk;
    TeamAdapter teamAdapter;
    private String data, projectId, buildingId;
    private int curPosition = -1;

    @Override
    public int initLayoutView() {
        return R.layout.activity_team_selection;
    }

    @Override
    public void initData() {
        super.initData();
        projectId = getIntent().getExtras().getString(Constants.EXTRA_DATA_PROJECT);
        data = getIntent().getExtras().getString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
        buildingId = getIntent().getExtras().getString(Constants.EXTRA_DATA_BUILDING);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("选择施工班组", false, false,"");
        initRV();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getTeamList();
    }


    private void initRV() {
        teamAdapter = new TeamAdapter();
        APPUtils.removeRecyclerViewDefaultAnimation(rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(teamAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void initListener() {
        super.initListener();
        teamAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TeamBean team = (TeamBean) adapter.getItem(position);
                if (Constants.CHOOSE_TYPE_SINGLE.equals(data)) {//单选
                    if (curPosition != position && curPosition != -1) {
                        TeamBean oldTeam = (TeamBean) adapter.getItem(curPosition);
                        oldTeam.setCheck(false);
                        teamAdapter.notifyItemChanged(curPosition);
                    }
                    team.setCheck(true);
                    curPosition = position;
                } else {//多选
                    team.setCheck(!team.isCheck());
                }
                teamAdapter.notifyItemChanged(position);
                showChooseNum();
            }
        });
    }

    private void getTeamList() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getTeamList(loginBean.getUser_id(), projectId, buildingId, new HttpEngine.OnResponseCallback<HttpResponse.Team>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Team data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                teamAdapter.setNewData(data.getData());
            }
        });
    }

    private void showChooseNum() {
        int count = 0;
        List<TeamBean> pList = teamAdapter.getData();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).isCheck()) count++;
        }
        String txt = "确定（" + count + "）";
        rtvOk.setText(txt);
    }

    @Override
    @OnClick({R.id.activity_team_selection_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_team_selection_rtv_ok:
                List<TeamBean> nPList = new ArrayList<>();
                List<TeamBean> pList = teamAdapter.getData();
                for (int i = 0; i < pList.size(); i++) {
                    if (pList.get(i).isCheck()) nPList.add(pList.get(i));
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_DATA, (Serializable) nPList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

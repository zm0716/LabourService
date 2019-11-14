package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.RectificationDetailAdapter;
import com.agilefinger.labourservice.adapter.ZGDetailImagesAdapter;
import com.agilefinger.labourservice.adapter.ZGDetailVoiceAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AuthBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RectificationItemBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.DateTimeUtil;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.AlertDialog;
import com.agilefinger.labourservice.view.dialog.ConfirmCusDelDialog;
import com.agilefinger.labourservice.view.dialog.ConfirmResubmitDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.StaffPickerDialog;
import com.agilefinger.labourservice.widget.PlusImage.MainConstant;
import com.agilefinger.labourservice.widget.PlusImage.PlusImageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 整改派单详情
 */
public class RectificationDetailActivity extends BaseActivity implements RectificationDetailAdapter.PlayVoiceListener {

    @BindView(R.id.activity_rectification_detail_rv_images)
    RecyclerView rvImages;
    @BindView(R.id.activity_rectification_detail_rv_voice)
    RecyclerView rvVoice;
    @BindView(R.id.activity_rectification_detail_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_rectification_detail_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_rectification_detail_tv_man)
    TextView tvMan;
    @BindView(R.id.activity_rectification_detail_tv_location)
    TextView tvLocation;
    @BindView(R.id.activity_rectification_detail_rtv_state)
    RoundTextView rtvState;
    @BindView(R.id.activity_rectification_detail_tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.activity_rectification_detail_tv_time)
    TextView tvTime;
    @BindView(R.id.activity_rectification_detail_ll_btn_appoint_refuse_reply)
    LinearLayout llBtnAppointRefuseReply;
    @BindView(R.id.activity_rectification_detail_ll_btn_withdraw)
    LinearLayout llBtnWithdraw;
    @BindView(R.id.activity_rectification_detail_ll_btn_reject_pass)
    LinearLayout llBtnRejectPass;
    @BindView(R.id.activity_rectification_detail_rtv_appoint)
    RoundTextView rtvAppoint;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView ivOperate;
    @BindView(R.id.activity_rectification_detail_ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.activity_rectification_detail_ll_images)
    LinearLayout llImages;
    private ZGDetailImagesAdapter zgImagesAdapter;
    private ZGDetailVoiceAdapter zgVoiceAdapter;
    private RectificationDetailAdapter rectificationDetailAdapter;
    private String zid, companyId;
    private ConfirmCusDelDialog confirmDialog;
    private RectificationBean rectification;
    private StaffPickerDialog staffPickerDialog;
    private StaffBean staffBean;
    private String project_id, building_id;

    @Override
    public int initLayoutView() {
        return R.layout.activity_rectification_detail;
    }

    @Override
    public void initData() {
        super.initData();
        if (!checkLogin()) finish();
        zid = getIntent().getExtras().getString(Constants.EXTRA_DATA);
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("整改派单", true, false, "");
        initRV();
    }

    private void initRV() {
        LinearLayoutManager zgLinearLayoutManager = new LinearLayoutManager(this);
        zgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zgImagesAdapter = new ZGDetailImagesAdapter();
        rvImages.setLayoutManager(zgLinearLayoutManager);
        rvImages.setAdapter(zgImagesAdapter);

        //问题描述 声音
        zgVoiceAdapter = new ZGDetailVoiceAdapter();
        rvVoice.setLayoutManager(new LinearLayoutManager(this));
        rvVoice.setAdapter(zgVoiceAdapter);

        //列表
        rectificationDetailAdapter = new RectificationDetailAdapter(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(rectificationDetailAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llBtnRejectPass.setVisibility(View.GONE);
        llBtnAppointRefuseReply.setVisibility(View.GONE);
        llBtnWithdraw.setVisibility(View.GONE);
        ivOperate.setVisibility(View.GONE);
        getRectificationDetail();
    }

    @Override
    public void initListener() {
        super.initListener();
        zgImagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> imageList = zgImagesAdapter.getData();
                Intent intent = new Intent(RectificationDetailActivity.this, PlusImageActivity.class);
                intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) imageList);
                intent.putExtra(MainConstant.POSITION, position);
                startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
            }
        });
        zgVoiceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                VoiceBean voice = (VoiceBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_zg_voice_rll_voice:
                        playVoice(voice.getPath(), (ImageView) view.findViewById(R.id.item_zg_voice_iv_play));//URL.BASE_URL + voice.getPath()
                        break;
                }
            }
        });
        rectificationDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RectificationItemBean rectificationItemBean = (RectificationItemBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_rectification_detail_iv_look://查阅状态
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_DATA, zid);
                        bundle.putString(Constants.EXTRA_TYPE, rectificationItemBean.getId());
                        IntentUtils.startActivity(RectificationDetailActivity.this, LookStateActivity.class, bundle);
                        break;
                }
            }
        });
    }

    private void getRectificationDetail() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getRectificationDetail(loginBean.getUser_id(), companyId, zid, new HttpEngine.OnResponseCallback<HttpResponse.RectificationDetail>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.RectificationDetail data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    finish();
                    return;
                }
                Log.d("获取整改派单详情", GsonUtils.toJson(data));
                showDetail(data.getData());
            }
        });
    }

    private void showDetail(RectificationBean data) {
        if (data == null) return;
        try {
            project_id = data.getProject_id();
            building_id = data.getBuilding_id();
            rectification = data;
            tvDeadline.setText(data.getDeadline());
            if (!data.getAddress_descrip().equals("")) {
                tvLocation.setText(data.getBuilding_name() + "" + data.getFloor_name() + "" + data.getInside_name() + "  " + data.getAddress_descrip());
            } else {
                tvLocation.setText(data.getBuilding_name() + "" + data.getFloor_name() + "" + data.getInside_name());
            }
            tvTitle.setText(data.getTitle());
            tvMan.setText(data.getZgMan());
            rtvState.setText(data.getStatus());
            //在这里加背景颜色
            showStatus(data.getStatus());
            tvTime.setText(data.getCreater() + " 创建于" + DateTimeUtil.formatFriendly2(DateTimeUtil.parseDate(data.getTime()), true));
            if (data.getImages() != null && data.getImages().size() > 0) {
                llImages.setVisibility(View.VISIBLE);
            } else {
                llImages.setVisibility(View.GONE);
            }
            zgImagesAdapter.setNewData(data.getImages());
            List<VoiceBean> mVoiceList = new ArrayList<>();
            List<String> voiceList = data.getVoice();
            List<String> voiceSecondList = data.getVoice_second();
            if (voiceList != null && voiceSecondList != null && voiceList.size() == voiceSecondList.size() && voiceList.size() > 0) {
                llVoice.setVisibility(View.VISIBLE);
                for (int i = 0; i < voiceList.size(); i++) {
                    VoiceBean voice = new VoiceBean(Float.parseFloat(voiceSecondList.get(i)), voiceList.get(i), voiceList.get(i));
                    mVoiceList.add(voice);
                }
                zgVoiceAdapter.setNewData(mVoiceList);
            } else {
                llVoice.setVisibility(View.GONE);
            }
            rectificationDetailAdapter.setNewData(data.getList());
            //判断按钮 状态
            AuthBean auth = data.getAuth();//2整改中（默认）3待验收 4已完成 5已拒绝 6已撤销
            //   Log.d("指派数据",GsonUtils.toJson(auth)+"::"+GsonUtils.toJson(data));
            if (Constants.ZG_STATE_1.equals(auth.getStatus())) {
                if ("1".equals(auth.getCorrect())) {//当前用户是否整改人？如果是，显示”指派“（灰色），”拒绝“和”整改回复“。
                    llBtnAppointRefuseReply.setVisibility(View.VISIBLE);
                    if ("1".equals(auth.getGroup())) {
                        rtvAppoint.setEnabled(true);
                        rtvAppoint.setTextColor(getResources().getColor(R.color.black_333333));
                        rtvAppoint.getDelegate().setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        rtvAppoint.setEnabled(false);
                        rtvAppoint.setTextColor(getResources().getColor(R.color.black_333333));
                        rtvAppoint.getDelegate().setBackgroundColor(getResources().getColor(R.color.gray_d7d7d7));
                    }
                } else if ("1".equals(auth.getCreater())) {//当前用户是否发起人？如果是，显示”撤回“按钮，右上角”…“显示，可以”再次提交“
                    llBtnWithdraw.setVisibility(View.VISIBLE);
                    ivOperate.setVisibility(View.VISIBLE);
                } else {
                    //不是整改人
                    if ("1".equals(auth.getCorrect_in_group())) {
                        llBtnAppointRefuseReply.setVisibility(View.VISIBLE);
                        //该整改派单当前整改人是否项目组人员（包括项目经理和项目成员），如果是，显示”指派“，”拒绝“和”整改回复“。
                        llBtnAppointRefuseReply.setVisibility(View.VISIBLE);
                        rtvAppoint.setEnabled(true);
                        rtvAppoint.setTextColor(getResources().getColor(R.color.black_333333));
                        rtvAppoint.getDelegate().setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        llBtnAppointRefuseReply.setVisibility(View.GONE);
                        rtvAppoint.setEnabled(false);
                        rtvAppoint.setTextColor(getResources().getColor(R.color.black_333333));
                        rtvAppoint.getDelegate().setBackgroundColor(getResources().getColor(R.color.gray_d7d7d7));
                    }
                }


            } else if (Constants.ZG_STATE_2.equals(auth.getStatus())) {//待验收
                if ("1".equals(auth.getGroup())) {//当前用户是否项目组成员？如果是，显示”验收驳回“和”验收通过“
                    llBtnRejectPass.setVisibility(View.VISIBLE);
                } else {
                    llBtnRejectPass.setVisibility(View.GONE);
                }
            } else {
                llBtnRejectPass.setVisibility(View.GONE);
                llBtnAppointRefuseReply.setVisibility(View.GONE);
                llBtnWithdraw.setVisibility(View.GONE);
                ivOperate.setVisibility(View.GONE);
            }

            if ("1".equals(auth.getCreater())) {//当前用户是否发起人右上角”…“显示，可以”再次提交“
                ivOperate.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showStatus(String status) {
        switch (status) {
            case "整改中":
                rtvState.getDelegate().setBackgroundColor(getResources().getColor(R.color.blue_66a7e1));
                break;
            case "待验收":
                rtvState.getDelegate().setBackgroundColor(getResources().getColor(R.color.yellow_fecb1b));
                break;
            case "已完成":
                rtvState.getDelegate().setBackgroundColor(getResources().getColor(R.color.green_58b888));
                break;
            case "已忽略":
                rtvState.getDelegate().setBackgroundColor(getResources().getColor(R.color.red_cc0000));
                break;
            case "已撤回":
                rtvState.getDelegate().setBackgroundColor(getResources().getColor(R.color.gray_999999));
                break;
        }
    }

    @Override
    @OnClick({R.id.activity_rectification_detail_rtv_reject, R.id.activity_rectification_detail_rtv_pass, R.id.activity_rectification_detail_rtv_withdraw, R.id.activity_rectification_detail_rtv_appoint, R.id.activity_rectification_detail_rtv_refuse, R.id.activity_rectification_detail_rtv_reply})
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.activity_rectification_detail_rtv_reject://驳回
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_BOHUI);
                break;
            case R.id.activity_rectification_detail_rtv_pass://通过
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_TONGGUO);
                break;
            case R.id.activity_rectification_detail_rtv_withdraw://撤回
                showConfirmDialog();
                return;
            case R.id.activity_rectification_detail_rtv_appoint://指派
                getEmployeeList();
                return;
            case R.id.activity_rectification_detail_rtv_refuse://拒绝
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_JUJUE);
                break;
            case R.id.activity_rectification_detail_rtv_reply://回复
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_HUIFU);
                break;
        }
        bundle.putString(Constants.EXTRA_DATA, zid);
        IntentUtils.startActivity(this, ToDoActivity.class, bundle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopVoice();
        closeConfirmDialog();
        closeConfirmResubmitDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeAlertDialog();
    }

    @Override
    protected void toOperate() {
        super.toOperate();
        showConfirmResubmitDialog();
    }

    @Override
    public void playVoiceCallback(String path, ImageView imageView) {
        playVoice(path, imageView);
    }

    @Override
    public void plusImageCallback(List<String> images, int position) {
        Intent intent = new Intent(RectificationDetailActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, (ArrayList<String>) images);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 撤回接口数据处理
     */
    private void withdraw() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().withdraw(loginBean.getUser_id(), zid, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showAlertDialog(retmsg);//"已产生后续操作，无法撤回，请联系整改人忽略整改"
                    return;
                }
                showToast(retmsg);
                finish();
            }
        });
    }

    private AlertDialog alertDialog;

    private void showAlertDialog(String message) {
        closeAlertDialog();
        alertDialog = new AlertDialog(this, message, false, new AlertDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    finish();//退出当前页面
                }
            }
        });
        alertDialog.show();
    }

    private void closeAlertDialog() {
        if (alertDialog != null) {
            alertDialog.cancel();
        }
        alertDialog = null;
    }

    private void showConfirmDialog() {
        closeConfirmDialog();
        confirmDialog = new ConfirmCusDelDialog(this, "确认撤回？", true, new ConfirmCusDelDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    withdraw();
                }
                closeConfirmDialog();
            }
        });
        confirmDialog.show();
    }

    private void closeConfirmDialog() {
        if (confirmDialog != null) {
            confirmDialog.dismiss();
            confirmDialog = null;
        }
    }

    private void getEmployeeList() {
        if (rectification == null) {
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getEmployeeList(loginBean.getUser_id(), companyId, project_id, building_id, new HttpEngine.OnResponseCallback<HttpResponse.Staff>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Staff data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                if (data.getData() != null && data.getData().size() > 0) {
                    showStaffPickerDialog(data.getData());
                } else {
                    showToast("暂无整改人");
                }
            }
        });
    }

    /**
     * 显示整改人选取弹框
     *
     * @param data
     */
    private void showStaffPickerDialog(List<StaffBean> data) {
        closeStaffPickerDialog();
        staffPickerDialog = new StaffPickerDialog(this, data, new StaffPickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, Object... param) {
                if (confirm) {
                    staffBean = (StaffBean) param[0];
                    //跳转至TODO
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_ZHIPAI);//指派
                    bundle.putString(Constants.EXTRA_DATA, zid);//整改ID
                    bundle.putString(Constants.EXTRA_DATA_, staffBean.getId());//指派人ID
                    bundle.putString(Constants.EXTRA_DATA__, staffBean.getName());//指派人姓名
                    IntentUtils.startActivity(RectificationDetailActivity.this, ToDoActivity.class, bundle);
                }
                closeStaffPickerDialog();
            }
        });
        staffPickerDialog.show();
    }

    private void closeStaffPickerDialog() {
        if (staffPickerDialog != null) {
            staffPickerDialog.cancel();
            staffPickerDialog = null;
        }
    }

    private ConfirmResubmitDialog confirmResubmitDialog;

    private void showConfirmResubmitDialog() {
        closeConfirmResubmitDialog();
        confirmResubmitDialog = new ConfirmResubmitDialog(this, new ConfirmResubmitDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_DATA, zid);
                    bundle.putInt("type", 0);
                    bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                    IntentUtils.startActivity(RectificationDetailActivity.this, ResubmitRectificationActivity.class, bundle);
                }
                closeConfirmResubmitDialog();
            }
        });
        confirmResubmitDialog.show();
    }

    private void closeConfirmResubmitDialog() {
        if (confirmResubmitDialog != null) {
            confirmResubmitDialog.cancel();
            confirmResubmitDialog = null;
        }
    }
}

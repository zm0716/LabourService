package com.agilefinger.labourservice.fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.DataKBActivity;
import com.agilefinger.labourservice.activity.InspectionListActivity;
import com.agilefinger.labourservice.activity.KanBanListActivity;
import com.agilefinger.labourservice.activity.ProgressListActivity;
import com.agilefinger.labourservice.activity.RectificationListActivity;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.RoleBean;
import com.agilefinger.labourservice.bean.TaskNumBean;
import com.agilefinger.labourservice.bean.WorkBean;
import com.agilefinger.labourservice.bean.WorkModel;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.db.RoleDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.agilefinger.labourservice.view.dialog.CompanyPickerDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.flyco.roundview.RoundLinearLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 86251 on 2019/5/28.
 */

public class WorkFragment extends JBaseFragment {

    @BindView(R.id.fragment_work_tv_company)
    TextView tvCompany;
    @BindView(R.id.fragment_work_ll_progress)
    LinearLayout llProgress;
    @BindView(R.id.fragment_work_ll_rectification)
    LinearLayout llRectification;

    @BindView(R.id.fragment_work_ll_inspection)
    LinearLayout ll_inspection;
    @BindView(R.id.fragment_work_tv_m_zgz)
    TextView tvM_ZGZ;
    @BindView(R.id.fragment_work_tv_m_dys)
    TextView tvM_DYS;
    @BindView(R.id.fragment_work_tv_m_ywc)
    TextView tvM_YWC;
    @BindView(R.id.fragment_work_tv_m_yjj)
    TextView tvM_YJJ;
    @BindView(R.id.fragment_work_tv_x_jcz)
    TextView tvX_JCZ;
    @BindView(R.id.fragment_work_tv_x_wqd)
    TextView tvX_WQD;
    @BindView(R.id.fragment_work_tv_x_ytz)
    TextView tvX_YTZ;
    @BindView(R.id.fragment_work_tv_x_ywc)
    TextView tvX_YWC;
    //----------------- 整改派单 tab begin --------------------//
    @BindView(R.id.fragment_work_tv_fuze)
    TextView tvFuze;
    @BindView(R.id.fragment_work_tv_fenpai)
    TextView tvFenpai;
    @BindView(R.id.fragment_work_v_fuze)
    View vFuze;
    @BindView(R.id.fragment_work_v_fenpai)
    View vFenpai;
    //----------------- 整改派单 tab end --------------------//
    //----------------- 巡检任务 tab begin --------------------//
    @BindView(R.id.fragment_work_tv_chuangjian)
    TextView tvChuangjian;
    @BindView(R.id.fragment_work_tv_zhixing)
    TextView tvZhixing;
    @BindView(R.id.fragment_work_v_chuangjian)
    View vChuangjian;
    @BindView(R.id.fragment_work_v_zhixing)
    View vZhixing;

    @BindView(R.id.zhenggaill)
    RoundLinearLayout zhenggaill;
    @BindView(R.id.xunjianll)
    RoundLinearLayout xunjianll;


    //-------------数据看板-----------//
    @BindView(R.id.fragment_data_kanban)
    LinearLayout fragment_data_kanban;


    //------------项目看板------------//
    @BindView(R.id.fragment_work_ll_kanban)
    LinearLayout fragment_work_ll_kanban;

    //----------------- 巡检任务 tab end --------------------//
    private CompanyPickerDialog companyPickerDialog;
    private List<CompanyBean> companyList = new ArrayList<>();
    private CompanyBean company = new CompanyBean();
    private TextView[] tvZG = new TextView[2];
    private View[] vZGLine = new View[2];
    private TextView[] tvXJ = new TextView[2];
    private View[] vXJLine = new View[2];
    private int curZGTab = 0, curXJTab = 0;
    private boolean isZG=false;
    public static String company_id;

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        setToolbar();
//        initSwipeRefreshLayout();
        RoleDao roleDao = new RoleDao(LSApplication.context);
        List<RoleBean> roleList = roleDao.queryRoleAll();
        Log.d("角色权限",GsonUtils.toJson(roleList));
        for (int i = 0; i < roleList.size(); i++) {
            if (Constants.AUTH_94f.equals(roleList.get(i).getAuth())) {//巡检任务
                ll_inspection.setVisibility(View.VISIBLE);
                xunjianll.setVisibility(View.VISIBLE);
            } else if (Constants.AUTH_95.equals(roleList.get(i).getAuth())) {//整改派单
                llRectification.setVisibility(View.VISIBLE);
                zhenggaill.setVisibility(View.VISIBLE);
            }else if (Constants.APP_DATA_A94.equals(roleList.get(i).getAuth())) {//整改派单
                fragment_data_kanban.setVisibility(View.VISIBLE);
//                fragment_data_kanban.setVisibility(View.VISIBLE);
            }else if (Constants.APP_BOARD_A94.equals(roleList.get(i).getAuth())){
                fragment_work_ll_kanban.setVisibility(View.VISIBLE);
            }
        }
        initCompany();
        initZGTab();
        initXJTab();
    }


    private void initZGTab() {
        tvZG[0] = tvFuze;
        tvZG[1] = tvFenpai;
        vZGLine[0] = vFuze;
        vZGLine[1] = vFenpai;
    }

    private void initXJTab() {
        tvXJ[0] = tvChuangjian;
        tvXJ[1] = tvZhixing;
        vXJLine[0] = vChuangjian;
        vXJLine[1] = vZhixing;
    }

    private void initCompany() {
        CompanyDao companyDao = new CompanyDao(LSApplication.context);
        companyList = companyDao.queryCompanyAll();
        if (companyList != null && companyList.size() > 0) {
            company = companyList.get(0);
            company_id = company.getCompany_id();
            tvCompany.setText(company.getCompany_name());
        }
    }


    @Override
    protected void lazyLoadOnlyOne() {
        getWork();
        //巡检
        getXunJianWork(isJian);
    }

    private void getWork() {
        if (companyList == null || companyList.size() == 0)
            return;
        LoadingDialog.showLoading(getActivity());
        HttpManager.getInstance().getWork(loginBean.getUser_id(), company.getCompany_id(), new HttpEngine.OnResponseCallback<HttpResponse.Work>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.Work data) {
//                swipeRefreshLayout.setRefreshing(false);
                LoadingDialog.disDialog();
                if (result != 0) {
                    ToastUtils.showShortSafe(retmsg);
                    return;
                }
                showInfo(data.getData());
            }
        });
    }
    TaskNumBean.DataBean data;
    //获取巡检
    private void getXunJianWork(final boolean is) {
        if (companyList == null || companyList.size() == 0)
            return;
        try {
            LoadingDialog.showLoading(getActivity());
            Map<String, Object> pram=new HashMap<>();
            pram.put("userID",loginBean.getUser_id());
            pram.put("companyID", company.getCompany_id());
            OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_work_info", pram, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        LoadingDialog.disDialog();
                    }catch (Exception e2){{

                    }}
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        LoadingDialog.disDialog();
                        String string = response.body().string();
                        Log.d("返回数据",string);
                      //  swipeRefreshLayout.setRefreshing(false);

                        Gson gson=new Gson();
                        TaskNumBean taskNumBean = gson.fromJson(string, TaskNumBean.class);
                        if (taskNumBean.getCode()==0){
                            data = taskNumBean.getData();
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (is){
                                        TaskNumBean.DataBean.SendBean send = data.getSend();
                                        tvX_JCZ.setText(""+send.getDoing());
                                        tvX_WQD.setText(""+send.getWait());
                                        tvX_YTZ.setText(""+send.getStop());
                                        tvX_YWC.setText(""+send.getFinish());
                                    }else {
                                        TaskNumBean.DataBean.MineBean send = data.getMine();
                                        tvX_JCZ.setText(""+send.getDoing());
                                        tvX_WQD.setText(""+send.getWait());
                                        tvX_YTZ.setText(""+send.getStop());
                                        tvX_YWC.setText(""+send.getFinish());
                                    }

                                }
                            });

                        }
                    }catch (Exception e){
                      //  swipeRefreshLayout.setRefreshing(false);
                        LoadingDialog.disDialog();
                        e.printStackTrace();
                    }

                }
            });
        }catch (Exception e){
           // swipeRefreshLayout.setRefreshing(false);
            LoadingDialog.disDialog();
            e.printStackTrace();
        }
    }


    private WorkModel workModel;
    private void showInfo(WorkModel data) {
        if (data != null) {
            workModel = data;
            WorkBean responsibility = data.getResponsibility();

            if (responsibility != null) {
                tvM_ZGZ.setText(responsibility.getRectification());
                tvM_YWC.setText(responsibility.getCompleted());
                tvM_YJJ.setText(responsibility.getRejected());
                tvM_DYS.setText(responsibility.getAcceptance());
            }

        }
    }
    String type="";
    protected void setToolbar() {
        LinearLayout llTop = mView.findViewById(R.id.fragment_work_ll_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             int result = getStatusBarHeight();
             llTop.setPadding(0, result+3, 0, 0);
        }
    }

    @Override
    @OnClick({R.id.fragment_work_ll_fuze,
            R.id.fragment_work_ll_fenpai,
            R.id.fragment_work_ll_rectification,
            R.id.fragment_work_ll_top,
            R.id.fragment_work_ll_progress,
            R.id.fragment_work_ll_m_zgz,
            R.id.fragment_work_ll_m_dys,
            R.id.fragment_work_ll_m_ywc,
            R.id.fragment_work_ll_m_yjj,
            R.id.fragment_work_ll_chuangjian,
            R.id.fragment_work_ll_zhixing,
            R.id.fragment_work_ll_inspection,
            R.id.fragment_work_ll_x_jcz,
            R.id.fragment_work_ll_x_wqd,
            R.id.fragment_work_ll_x_ytz,
            R.id.fragment_work_ll_x_ywc,
            R.id.fragment_work_ll_kanban,
            R.id.fragment_data_kanban
    })

    public void onClick(View v) {
        super.onClick(v);
        if (company == null) {
            ToastUtils.showShortSafe("未选择公司");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA_COMPANY,company.getCompany_id());
        switch (v.getId()){
            case R.id.fragment_work_ll_rectification:
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
                return;
            case R.id.fragment_data_kanban:
                IntentUtils.startActivity(getActivity(), DataKBActivity.class, bundle);
                return;
            case R.id.fragment_work_ll_progress:
                IntentUtils.startActivity(getActivity(), ProgressListActivity.class, bundle);
                return;
            case R.id.fragment_work_ll_top:
                showCompanyDialog();
                return;
            case R.id.fragment_work_ll_m_zgz://整改中
                isZG=true;
                if (curZGTab == 0) {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FUZE);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_1);
                }else{
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_1);
                }
                break;
            case R.id.fragment_work_ll_m_dys://待验收
                isZG=true;
                if (curZGTab == 0) {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FUZE);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_2);
                } else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_2);
                }
                break;
            case R.id.fragment_work_ll_m_ywc://已完成
                isZG=true;
                if (curZGTab == 0) {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FUZE);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_3);
                } else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_3);
                }
                break;
            case R.id.fragment_work_ll_m_yjj://已拒绝
                isZG=true;
                if (curZGTab == 0) {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FUZE);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_4);
                } else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_4);
                }
                break;

            case R.id.fragment_work_ll_x_jcz://检测中
                isZG=false;
                type="1";
                if (curXJTab == 0){
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_CHUANGJIAN);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.JC_STATE_1);

                }else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_ZHIXING);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.JC_STATE_1);
                }
                break;
            case R.id.fragment_work_ll_x_wqd://未启动
                type="2";
                isZG=false;
                if (curXJTab == 0){
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_CHUANGJIAN);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.WQ_STATE_2);
                }else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_ZHIXING);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.WQ_STATE_2);
                }
                break;
            case R.id.fragment_work_ll_x_ytz://已停止
                type="3";
                isZG=false;
                if (curXJTab == 0){
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_CHUANGJIAN);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.YT_STATE_3);
                }else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_ZHIXING);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.YT_STATE_3);
                }
                break;
            case R.id.fragment_work_ll_x_ywc://已完成
                type="4";
                isZG=false;
                if (curXJTab == 0){
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_CHUANGJIAN);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.YW_STATE_4);
                }else {
                    bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_ZHIXING);
                    bundle.putString(Constants.EXTRA_TYPE, Constants.YW_STATE_4);
                }
                break;
            /*case R.id.fragment_work_ll_s_zgz:
                bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_1);
                break;
            case R.id.fragment_work_ll_s_dys:
                bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_2);
                break;
            case R.id.fragment_work_ll_s_ywc:
                bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_3);
                break;
            case R.id.fragment_work_ll_s_yjj:
                bundle.putString(Constants.EXTRA_DATA, Constants.STATE_ME_FENPAI);
                bundle.putString(Constants.EXTRA_TYPE, Constants.ZG_STATE_4);
                break;*/
            case R.id.fragment_work_ll_fuze:
                switchZGTab(0);
//                getWork();
                return;
            case R.id.fragment_work_ll_fenpai:
                switchZGTab(1);
//                getWork();
                return;
            case R.id.fragment_work_ll_chuangjian:
                isJian=true;
                //巡检
                switchXJTab(0);
                getXunJianWork(isJian);

                return;
            case R.id.fragment_work_ll_zhixing:
                isJian=false;
                //巡检
                switchXJTab(1);
                getXunJianWork(isJian);

                return;
                //巡检任务
            case R.id.fragment_work_ll_inspection:
                Bundle bundle1 = new Bundle();
                //设置参数，未完成，待解决
                bundle1.putString(Constants.EXTRA_DATA, "");
                bundle1.putString(Constants.EXTRA_TYPE, "");
                bundle1.putString("ischuang", "");
                bundle1.putString(Constants.EXTRA_DATA_COMPANY, company.getCompany_id());
                IntentUtils.startActivity(getActivity(), InspectionListActivity.class,bundle1);
                return;
                //项目看板
            case R.id.fragment_work_ll_kanban:
                Bundle bundle2 = new Bundle();
                bundle2.putString(Constants.EXTRA_DATA_COMPANY, company.getCompany_id());
                IntentUtils.startActivity(getActivity(), KanBanListActivity.class,bundle2);
                return;
        }
        if (isZG){
            if (llRectification.getVisibility() == View.VISIBLE) {
                IntentUtils.startActivity(getActivity(), RectificationListActivity.class, bundle);
            }
        }else{
            if (isJian){
                bundle.putString("ischuang","2");
            }else {
                bundle.putString("ischuang","1");
            }

            IntentUtils.startActivity(getActivity(), InspectionListActivity.class, bundle);}
    }

    private void switchZGTab(int i) {
        tvZG[curZGTab].setTextColor(getResources().getColor(R.color.black_333333));
        vZGLine[curZGTab].setVisibility(View.INVISIBLE);
        tvZG[i].setTextColor(getResources().getColor(R.color.blue_0079e4));
        vZGLine[i].setVisibility(View.VISIBLE);
        if (workModel != null) {
            if (i == 0){
                WorkBean responsibility = workModel.getResponsibility();
                if (responsibility != null) {
                    tvM_ZGZ.setText(responsibility.getRectification());
                    tvM_YWC.setText(responsibility.getCompleted());
                    tvM_YJJ.setText(responsibility.getRejected());
                    tvM_DYS.setText(responsibility.getAcceptance());
                }
            } else {
                WorkBean dispatch = workModel.getDispatch();
                if (dispatch != null) {
                    tvM_ZGZ.setText(dispatch.getRectification());
                    tvM_YWC.setText(dispatch.getCompleted());
                    tvM_YJJ.setText(dispatch.getRejected());
                    tvM_DYS.setText(dispatch.getAcceptance());
                }
            }
        }
        curZGTab = i;
    }

    private void switchXJTab(int i) {
        tvXJ[curXJTab].setTextColor(getResources().getColor(R.color.black_333333));
        vXJLine[curXJTab].setVisibility(View.INVISIBLE);
        tvXJ[i].setTextColor(getResources().getColor(R.color.blue_0079e4));
        vXJLine[i].setVisibility(View.VISIBLE);
        /*if (workModel != null) {
            if (i == 0) {
                WorkBean responsibility = workModel.getResponsibility();
                if (responsibility != null) {
                    tvM_ZGZ.setText(responsibility.getRectification());
                    tvM_YWC.setText(responsibility.getCompleted());
                    tvM_YJJ.setText(responsibility.getRejected());
                    tvM_DYS.setText(responsibility.getAcceptance());
                }
            } else {
                WorkBean dispatch = workModel.getDispatch();
                if (dispatch != null) {
                    tvM_ZGZ.setText(dispatch.getRectification());
                    tvM_YWC.setText(dispatch.getCompleted());
                    tvM_YJJ.setText(dispatch.getRejected());
                    tvM_DYS.setText(dispatch.getAcceptance());
                }
            }
        }*/
        curXJTab = i;
    }
    private void showCompanyDialog() {
        if (companyList == null || companyList.size() == 0) {
            ToastUtils.showShortSafe("暂无公司可选");
            return;
        }
        closeCompanyDialog();
        companyPickerDialog = new CompanyPickerDialog(getActivity(), companyList, new CompanyPickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, Object... param) {
                if (confirm) {
                    company = (CompanyBean) param[0];
                    tvCompany.setText(company.getCompany_name());

                    switchZGTab(0);
                    getWork();

                    isJian=true;
                    //巡检
                    switchXJTab(0);
                    getXunJianWork(isJian);
                }
                closeCompanyDialog();
            }
        });
        companyPickerDialog.show();
    }


    private void closeCompanyDialog() {
        if (companyPickerDialog != null) {
            companyPickerDialog.cancel();
            companyPickerDialog = null;
        }
    }

    private boolean isJian=true;
    @Override
    protected void refresh() {
        super.refresh();
        getWork();
        //巡检
        getXunJianWork(isJian);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        switchZGTab(0);
        getWork();
        //巡检
        getXunJianWork(isJian);
    }

    @Override
    public void onPause() {
        super.onPause();
        closeCompanyDialog();
    }

}

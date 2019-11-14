package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.Team2Adaptere;
import com.agilefinger.labourservice.adapter.Team3Adaptere;
import com.agilefinger.labourservice.adapter.TeamAdaptere;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AddressBean;
import com.agilefinger.labourservice.bean.AllBuildBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.agilefinger.labourservice.activity.InspectionDetailActivity.taskName;

public class AddAddressActivity extends BaseActivity {
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        taskName = savedInstanceState.getString("taskName");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("taskName", taskName);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @BindView(R.id.activity_addinspection_position_rtvv_add_inspection_building)
    RoundTextView activityAddinspectionPositionRtvAddInspectionBuilding;
    @BindView(R.id.activity_inspection_position_rv_list)
    RecyclerView buildingrv;
    @BindView(R.id.nullimg)
    ImageView nullimg;


    @BindView(R.id.activity_inspection_position_rtv_next)
    RoundTextView activity_inspection_position_rtv_next;


    private String missionID = "89FAAD4EEFD512884862CEA1137BE9D5";
    private RadioButton pop_add_inspection_position_tv_building;
    private RadioButton pop_add_inspection_position_tv_group;
    private List<AddressBean.CanChooseBean> dataaList;
    private RecyclerView pop_address_inspection_position_rv_list;
    private String projectid;
    private String taskid;
    AllBuildBean.DataBean data;
    private RadioGroup radiogroup;
    private String buildString;
    private String groupString;
    private String type;

    @Override
    protected void back2() {
        super.back2();

        if (data.getHas().size() > 0) {
            finish();
        } else {
            LSApplication.finishActivity();
            finish();
        }
    }

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar2("检测位置", false, false, "");
        Intent intent = getIntent();
        projectid = intent.getStringExtra("projectid");
        taskid = intent.getStringExtra("taskid");
        type = intent.getStringExtra("type");

        getData();

        activity_inspection_position_rtv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.getHas().isEmpty() && data.getHas() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("projectid", projectid + "");
                    bundle.putString("taskid", taskid + "");
                    bundle.putString("taskname", taskName + "");
                    IntentUtils.startActivity(AddAddressActivity.this, DataCollectionActivity.class, bundle);
                    finish();
                } else {
                    ToastUtil.showShort(AddAddressActivity.this, "请选择楼号");
                }
            }
        });

    }

    //    private int index;
    private AllBuildBean allBuildBean;

    private void getData() {
        LoadingDialog.showLoading2(AddAddressActivity.this);
        final Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskid);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_check_position_info", pram, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                String string = response.body().string();
                Log.d("获取楼栋", string);
                Gson gson = new Gson();
                allBuildBean = gson.fromJson(string, AllBuildBean.class);
                if (allBuildBean.getCode() == 0) {
                    data = allBuildBean.getData();
                    if (!data.getHas().isEmpty()) {


                        ThreadUtils.runOnMainThread(new Runnable() {

                            @Override
                            public void run() {
                                buildingrv.setVisibility(View.VISIBLE);
                                nullimg.setVisibility(View.GONE);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddressActivity.this);
                                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                                buildingrv.setLayoutManager(linearLayoutManager);
                                final Team3Adaptere teamAdaptere = new Team3Adaptere(AddAddressActivity.this, data.getHas());
                                buildingrv.setAdapter(teamAdaptere);
                                teamAdaptere.setOnItemClickListener(new Team3Adaptere.OnItemClickListener() {
                                    @Override
                                    public void onClick(View view, final int position, Team3Adaptere.MyHolder holder, List<AllBuildBean.DataBean.HasBean> list) {
//                                        index = position;
//                                        delete(position);

                                        Map<String, Object> pram2 = new HashMap<>();
                                        pram2.put("missionID", taskid);
                                        pram2.put("buildingID", data.getHas().get(position).getCpi_b_id());
                                        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/delete_check_position", pram2, new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                String string1 = response.body().string();


                                                Log.d("删除楼号", string1);

                                            }
                                        });

                                        for (int i = 0; i < data.getBuilding().size(); i++) {
                                            if (String.valueOf(data.getBuilding().get(i).getId()).equals(data.getHas().get(position).getCpi_b_id())) {
                                                data.getBuilding().get(i).setIs_select(0);
                                                break;
                                            }
                                        }
//                                                        if (teamAdaptere2 != null) {
//                                                            teamAdaptere2.notifyDataSetChanged();
//                                                        }
                                        data.getHas().remove(position);
                                        teamAdaptere.notifyItemRemoved(position);
                                        teamAdaptere.notifyDataSetChanged();
                                        if (data.getHas().size() == 0) {
                                            ThreadUtils.runOnMainThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    buildingrv.setVisibility(View.GONE);
                                                    nullimg.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }

                                    }
                                });


                            }
                        });

                    } else {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                buildingrv.setVisibility(View.GONE);
                                nullimg.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                }
            }
        });
        //获取位置
        addAddress();
    }


    List<String> buildList = new ArrayList<>();

    private Team2Adaptere teamAdaptere2;

    /**
     * 获取位置
     *
     * @param
     */
    private void addAddress() {
        activityAddinspectionPositionRtvAddInspectionBuilding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = View.inflate(AddAddressActivity.this, R.layout.item_address, null);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setContentView(view);
                popupWindow.setHeight(800);
                popupWindow.setAnimationStyle(R.style.pop_animatiom_style);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(activityAddinspectionPositionRtvAddInspectionBuilding, Gravity.BOTTOM, 0, 0);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        activityAddinspectionPositionRtvAddInspectionBuilding.setEnabled(true);
                    }
                });

                activityAddinspectionPositionRtvAddInspectionBuilding.setEnabled(false);
                radiogroup = view.findViewById(R.id.radiogroup);
                RoundTextView pop_add_inspection_position_rtv_cancel = view.findViewById(R.id.pop_add_inspection_position_rtv_cancel);
                RoundTextView pop_add_inspection_position_rtv_save = view.findViewById(R.id.pop_add_inspection_position_rtv_save);
                pop_add_inspection_position_tv_building = view.findViewById(R.id.pop_add_inspection_position_tv_building);
                pop_add_inspection_position_tv_group = view.findViewById(R.id.pop_add_inspection_position_tv_group);
                pop_address_inspection_position_rv_list = view.findViewById(R.id.pop_address_inspection_position_rv_list);

                pop_add_inspection_position_tv_group.setTextColor(Color.parseColor("#999999"));
                pop_add_inspection_position_tv_building.setTextColor(Color.parseColor("#349fff"));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddressActivity.this);
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                pop_address_inspection_position_rv_list.setLayoutManager(linearLayoutManager);
                if (null != data) {
                    List<AllBuildBean.DataBean.BuildingBean> building = data.getBuilding();
                    if (null != building) {
                        teamAdaptere2 = new Team2Adaptere(AddAddressActivity.this, data.getBuilding());
                        pop_address_inspection_position_rv_list.setAdapter(teamAdaptere2);
                        teamAdaptere2.setOnItemClickListener(new Team2Adaptere.OnItemClickListener() {
                            @Override
                            public void onClick(View view, int position, Team2Adaptere.MyHolder holder, List<AllBuildBean.DataBean.BuildingBean> list) {
                                if (data.getBuilding().get(position).isGroupisxuan()) {
                                    data.getBuilding().get(position).setGroupisxuan(false);
                                    holder.image_checked.setImageResource(R.mipmap.ic_unchecked);
                                    holder.text_louhao.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    data.getBuilding().get(position).setGroupisxuan(true);
                                    holder.image_checked.setImageResource(R.mipmap.xuan);
                                    holder.text_louhao.setTextColor(Color.parseColor("#349fff"));
                                }
                            }
                        });
                    }
                }


                //取消
                pop_add_inspection_position_rtv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        activityAddinspectionPositionRtvAddInspectionBuilding.setEnabled(true);
                    }
                });
                //保存
                pop_add_inspection_position_rtv_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buildList.clear();
                        buildString = "";
                        groupString = "";
                        Map<String, Object> pram = new HashMap<>();
                        Log.d("当前是否选中", pop_add_inspection_position_tv_building.isChecked() + "::");
                        if (pop_add_inspection_position_tv_building.isChecked()) {
                            for (int i = 0; i < data.getBuilding().size(); i++) {
                                if (data.getBuilding().get(i).isGroupisxuan()) {
                                    if (buildString.equals("")) {
                                        buildString = "" + data.getBuilding().get(i).getId() + "";
                                    } else {
                                        buildString = buildString + "," + data.getBuilding().get(i).getId();
                                    }
                                }
                            }
                            pram.put("building", buildString);
                            pram.put("group", "");
                        } else {
                            for (int i = 0; i < data.getGroup().size(); i++) {
                                if (data.getGroup().get(i).isGroupisxuan()) {
                                    if (groupString.equals("")) {
                                        groupString = "" + data.getGroup().get(i).getGroup_id() + "";
                                    } else {
                                        groupString = groupString + "," + data.getGroup().get(i).getGroup_id();
                                    }
                                }
                            }
                            pram.put("group", groupString);
                            pram.put("building", "");
                        }
                        pram.put("missionID", taskid);
                        pram.put("userID", loginBean.getUser_id());
                        Log.d("添加传参", GsonUtils.toJson(pram) + "::" + groupString + buildString);
                        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/new_position", pram, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                String string = response.body().string();
                                Log.d("添加楼栋", string);
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData();
                                        popupWindow.dismiss();
                                        activityAddinspectionPositionRtvAddInspectionBuilding.setEnabled(true);
                                    }
                                });

                            }
                        });

                    }
                });
                //楼号
                pop_add_inspection_position_tv_building.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop_add_inspection_position_tv_group.setTextColor(Color.parseColor("#999999"));
                        pop_add_inspection_position_tv_building.setTextColor(Color.parseColor("#349fff"));

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddressActivity.this);
                        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                        pop_address_inspection_position_rv_list.setLayoutManager(linearLayoutManager);
                        Team2Adaptere teamAdaptere = new Team2Adaptere(AddAddressActivity.this, data.getBuilding());
                        pop_address_inspection_position_rv_list.setAdapter(teamAdaptere);
                        teamAdaptere.setOnItemClickListener(new Team2Adaptere.OnItemClickListener() {
                            @Override
                            public void onClick(View view, int position, Team2Adaptere.MyHolder holder, List<AllBuildBean.DataBean.BuildingBean> list) {
                                if (data.getBuilding().get(position).isGroupisxuan()) {
                                    data.getBuilding().get(position).setGroupisxuan(false);
                                    holder.image_checked.setImageResource(R.mipmap.ic_unchecked);
                                    holder.text_louhao.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    data.getBuilding().get(position).setGroupisxuan(true);
                                    holder.image_checked.setImageResource(R.mipmap.xuan);
                                    holder.text_louhao.setTextColor(Color.parseColor("#349fff"));
                                }
                            }
                        });

                    }
                });
                //班组
                pop_add_inspection_position_tv_group.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   ToastUtil.showShort(AddAddressActivity.this,"点击");
                        pop_add_inspection_position_tv_group.setTextColor(Color.parseColor("#349fff"));
                        pop_add_inspection_position_tv_building.setTextColor(Color.parseColor("#999999"));

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddressActivity.this);
                        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                        pop_address_inspection_position_rv_list.setLayoutManager(linearLayoutManager);
                        try {
                            if (null != data.getGroup()) {
                                TeamAdaptere teamAdaptere = new TeamAdaptere(AddAddressActivity.this, data.getGroup());
                                pop_address_inspection_position_rv_list.setAdapter(teamAdaptere);
                                teamAdaptere.setOnItemClickListener(new TeamAdaptere.OnItemClickListener() {
                                    @Override
                                    public void onClick(View view, int position, TeamAdaptere.MyHolder holder, List<AllBuildBean.GroupBean> list) {

                                        if (data.getGroup().get(position).isGroupisxuan()) {
                                            data.getGroup().get(position).setGroupisxuan(false);
                                            holder.image_checked.setImageResource(R.mipmap.ic_unchecked);
                                            holder.text_louhao.setTextColor(Color.parseColor("#333333"));
                                        } else {
                                            data.getGroup().get(position).setGroupisxuan(true);
                                            holder.image_checked.setImageResource(R.mipmap.xuan);
                                            holder.text_louhao.setTextColor(Color.parseColor("#349fff"));
                                        }
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.agilefinger.labourservice.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ZGImagesAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AddProgressBean;
import com.agilefinger.labourservice.bean.AddProgressContentBean;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.ConstructionProcessBean;
import com.agilefinger.labourservice.bean.JsonProgressBean;
import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.ProgressBuildingBean;
import com.agilefinger.labourservice.bean.ProgressItemBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.TeamBean;
import com.agilefinger.labourservice.bean.UnitBean;
import com.agilefinger.labourservice.bean.ViewBean;
import com.agilefinger.labourservice.bean.WeatherBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.db.ProgressDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.ConstructionProcessDialog;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.view.dialog.DateYMDDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.UnitDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 新增整改派单
 */
public class AddProgressActivity extends BaseActivity {

    @BindView(R.id.activity_add_progress_rv_images)
    RecyclerView rvImages;
    @BindView(R.id.activity_add_progress_tv_project)
    TextView tvProject;
    @BindView(R.id.activity_add_progress_et_remark)
    EditText etRemark;
    @BindView(R.id.activity_add_progress_ll_content)
    LinearLayout llParentContent;
    @BindView(R.id.activity_add_progress_tv_weather_weather)
    TextView tvWeather;
    @BindView(R.id.activity_add_progress_tv_weather_temperature)
    TextView tvTemperature;
    @BindView(R.id.activity_add_progress_tv_weather_wind_direction)
    TextView tvWindDirection;
    @BindView(R.id.activity_add_progress_tv_weather_wind_power)
    TextView tvWindPower;
    @BindView(R.id.activity_add_progress_tv_weather_precipitation)
    TextView tvPrecipitation;
    @BindView(R.id.activity_add_progress_tv_weather_dampness)
    TextView tvDampness;
    @BindView(R.id.activity_add_progress_tv_date)
    TextView tvDate;
    private TextView tvTeam;
    private ZGImagesAdapter zgImagesAdapter;
    private ProjectBean project;
    private List<ViewBean> pViewList = new ArrayList<>();
    private String companyId, type;
    private WeatherBean weather = new WeatherBean();
    private ProgressBean progress;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_progress;
    }

    @Override
    public void initData() {
        super.initData();
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        type = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
        progress = (ProgressBean) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA);
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                },
                1
        );
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("施工进度", false, false,"");
        initRV();
        if (Constants.TYPE_EDIT.equals(type)) {
            showView();
        } else {
            List<String> mList = new ArrayList<>();
            mList.add("");
            zgImagesAdapter.setNewData(mList);
        }
    }

    private void showView() {
        if (progress != null) {
            //日期
            if (!TextUtils.isEmpty(progress.getDate_ymd())) {
                tvDate.setText(progress.getDate_ymd());
            }
            //项目
            if (!TextUtils.isEmpty(progress.getProject_id())) {
                project = new ProjectBean();
                project.setTitle(progress.getProject_name());
                project.setId(progress.getId());
                tvProject.setText(project.getTitle());
            }
            //备注
            if (!TextUtils.isEmpty(progress.getRemark())) {
                etRemark.setText(progress.getRemark());
            }
            //图片
            if (!TextUtils.isEmpty(progress.getImage())) {
                List<String> images = Arrays.asList(progress.getImage().split(","));
                if (images != null && images.size() > 0) {
                    List arrList = new ArrayList(images);
                    arrList.add("");
                    zgImagesAdapter.setNewData(arrList);
                }
            }
            //天气
            if (progress.getWeather() != null) {
                weather = progress.getWeather();
                showWeather(weather);
            }
            //施工楼号
            if (progress.getContent() != null) {
                llParentContent.setVisibility(View.VISIBLE);
                List<ProgressBuildingBean> progressBuildingBeanList = progress.getContent();
                for (int i = 0; i < progressBuildingBeanList.size(); i++) {
                    final ProgressBuildingBean progressBuildingBean = progressBuildingBeanList.get(i);
                    List<ProgressItemBean> progressItemBeanList = progressBuildingBeanList.get(i).getItem();//楼号
                    View parent = addEditParent(progressBuildingBean);//楼层布局
                    ViewBean viewBean = new ViewBean(progressBuildingBean.getId(), progressBuildingBean.getName(), parent);
                    if (progressItemBeanList != null) {//楼号里面的内容
                        for (int j = 0; j < progressItemBeanList.size(); j++) {
                            //(LinearLayout) parent.findViewById(R.id.layout_progress_ll_content), bList.get(i).getId()
                            View child = addEditChild((LinearLayout) parent.findViewById(R.id.layout_progress_ll_content), progressItemBeanList.get(j), j + 1);
                            viewBean.getChild().add(child);//添加一个子内容
                        }
                    }
                    pViewList.add(viewBean);
                    llParentContent.addView(parent);
                }
            }
        }
    }

    private View addEditParent(final ProgressBuildingBean progressBuildingBean) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View parent = inflater.inflate(R.layout.layout_progress, null);
        parent.setTag(progressBuildingBean.getId());
        TextView tvBuildingNo = parent.findViewById(R.id.layout_progress_tv_building_no);
        tvBuildingNo.setText(progressBuildingBean.getName());
        final LinearLayout llContent = parent.findViewById(R.id.layout_progress_ll_content);
        LinearLayout llAdd = parent.findViewById(R.id.layout_progress_ll_add);
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild(llContent, progressBuildingBean.getId());
            }
        });
        return parent;
    }

    private View addEditChild(LinearLayout llContent, ProgressItemBean progressItemBean, int size) {
        final String id = progressItemBean.getBuilding_id();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.layout_progress_content, null);
        view.setTag(size);
        TextView tvDetail = view.findViewById(R.id.layout_progress_tv_detail);
        tvDetail.setText("明细" + size);
        TextView tvDelete = view.findViewById(R.id.layout_progress_tv_delete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChild(id, view);
            }
        });
        LinearLayout llSGGX = view.findViewById(R.id.layout_progress_ll_sggx);
        final TextView tvSGGX = view.findViewById(R.id.layout_progress_tv_sggx);
        tvSGGX.setText(progressItemBean.getOrder());
        llSGGX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConstructionProcessDialog(tvSGGX);
            }
        });
        EditText etWCL = view.findViewById(R.id.layout_progress_et_wcl);//完成量
        etWCL.setText(progressItemBean.getQuantity());
        EditText etWCRate = view.findViewById(R.id.layout_progress_et_wc_rate);//已完成比例
        etWCRate.setText(progressItemBean.getRate());
        LinearLayout llBZ = view.findViewById(R.id.layout_progress_ll_bz);//施工班组
        final TextView tvBZ = view.findViewById(R.id.layout_progress_tv_bz);//施工班组
        tvBZ.setText(progressItemBean.getGroup());
        llBZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTeam = tvBZ;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, project.getId());
                bundle.putString(Constants.EXTRA_DATA_BUILDING, id);
                IntentUtils.startActivityForResult(AddProgressActivity.this, TeamSelectionActivity.class, bundle, Constants.REQUEST_TEAM);
            }
        });
        final TextView tvWCLUnit = view.findViewById(R.id.layout_progress_tv_wcl_unit);//完成量 单位
        tvWCLUnit.setText(progressItemBean.getUnit());
        tvWCLUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnitDialog(tvWCLUnit);
            }
        });
        final TextView tvNum = view.findViewById(R.id.layout_progress_content_tv_num);//人数
        tvNum.setText(progressItemBean.getNum());
        ImageView ivReduce = view.findViewById(R.id.layout_progress_content_iv_reduce);
        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = tvNum.getText().toString().trim();
                if (!"1".equals(num)) {
                    tvNum.setText(String.valueOf(Integer.parseInt(num) - 1));
                }
            }
        });
        ImageView ivAdd = view.findViewById(R.id.layout_progress_content_iv_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = tvNum.getText().toString().trim();
                tvNum.setText(String.valueOf(Integer.parseInt(num) + 1));
            }
        });
        llContent.addView(view);
        return view;
    }

    @Override
    public void initListener() {
        super.initListener();
        zgImagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String path = adapter.getItem(position).toString();
                if (TextUtils.isEmpty(path)) {
                    hideSoftKeyboard();
                    showPhotoPop();
                } else {
                    plusImage(zgImagesAdapter.getData(), position);
                }
            }
        });
        zgImagesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_zg_images_iv_del:
                        zgImagesAdapter.remove(position);
                        break;
                }
            }
        });
    }


    private void initRV() {
        LinearLayoutManager zgLinearLayoutManager = new LinearLayoutManager(this);
        zgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zgImagesAdapter = new ZGImagesAdapter();
        rvImages.setLayoutManager(zgLinearLayoutManager);
        rvImages.setAdapter(zgImagesAdapter);
    }


    /**
     * 上传施工进度
     */
    private void uploadProgress() {
        String date = tvDate.getText().toString().trim();
        if (TextUtils.isEmpty(date)) {
            showToast("请选择日期");
            return;
        }
        if (project == null) {
            showToast("请选择项目");
            return;
        }
        if (pViewList == null || pViewList.size() <= 0) {
            showToast("请选择至少一个施工楼号");
            return;
        }

        /*if (weather == null || TextUtils.isEmpty(weather.getWeather())) {
            showToast("请上传天气");
            return;
        }*/
        String images = "";
        List<String> imageList = zgImagesAdapter.getData();
        if (imageList.size() > 1) {
            imageList.remove(imageList.size() - 1);
            for (int i = 0; i < imageList.size(); i++) {
                if (i == imageList.size() - 1) {
                    images += imageList.get(i);
                } else {
                    images += imageList.get(i) + ",";
                }
            }
        }
        //[{buildId:1,content:[{ },{ }]  },{  }]
        ProgressBean progressBean = new ProgressBean();
        progressBean.setUsername(loginBean.getUser_account());
        progressBean.setProject_id(project.getId());
        progressBean.setProject_name(project.getTitle());
        progressBean.setRemark(etRemark.getText().toString().trim());
        progressBean.setImage(images);
        progressBean.setDate(DateFormatUtils.formatDate(new GregorianCalendar()));
        progressBean.setDate_ymd(date);
        progressBean.setWeather(weather);

        List<ProgressBuildingBean> progressBuildingBeanList = new ArrayList<>();

        for (int i = 0; i < pViewList.size(); i++) {
            ViewBean viewBean = pViewList.get(i);
            List<View> child = viewBean.getChild();
            ProgressBuildingBean progressBuildingBean = new ProgressBuildingBean(); //楼层集合
            progressBuildingBean.setId(viewBean.getId());
            progressBuildingBean.setName(viewBean.getName());
            List<ProgressItemBean> progressItemBeanList = new ArrayList<>();//每个内容
            for (int j = 0; j < child.size(); j++) {
                ProgressItemBean progressItemBean = new ProgressItemBean();
                View childView = child.get(j);
                TextView tvSGGX = childView.findViewById(R.id.layout_progress_tv_sggx);//施工工序
                EditText etWCL = childView.findViewById(R.id.layout_progress_et_wcl);//完成量
                EditText etWCRate = childView.findViewById(R.id.layout_progress_et_wc_rate);//已完成比例
                TextView tvNum = childView.findViewById(R.id.layout_progress_content_tv_num);//人数
                TextView tvUnit = childView.findViewById(R.id.layout_progress_tv_wcl_unit);//单位
                TextView tvTeam = childView.findViewById(R.id.layout_progress_tv_bz);//施工班组
                progressItemBean.setBuilding_id(pViewList.get(i).getId());
                progressItemBean.setBuilding_no(pViewList.get(i).getName());
                progressItemBean.setNum(tvNum.getText().toString().trim());
                progressItemBean.setGroup("请选择".equals(tvTeam.getText().toString()) ? "" : tvTeam.getText().toString());//施工班组
                progressItemBean.setOrder("请选择".equals(tvSGGX.getText().toString()) ? "" : tvSGGX.getText().toString());//施工工序
                progressItemBean.setQuantity(etWCL.getText().toString().trim());
                progressItemBean.setRate(etWCRate.getText().toString().trim());
                progressItemBean.setUnit(tvUnit.getText().toString());
                progressItemBeanList.add(progressItemBean);
            }
            progressBuildingBean.setItem(progressItemBeanList);
            progressBuildingBeanList.add(progressBuildingBean);
        }
        progressBean.setContent(progressBuildingBeanList);
        //序列化成json
        Gson g = new Gson();
        String jsonString = g.toJson(progressBean);
        ProgressDao progressDao = new ProgressDao(LSApplication.context);
        JsonProgressBean jsonProgressBean = new JsonProgressBean();
        jsonProgressBean.setContent(jsonString);
        if (progress != null && !TextUtils.isEmpty(progress.getId())) {
            //先删除
            progressDao.deleteProgress(Integer.parseInt(progress.getId()));
        }
        progressDao.addProgress(jsonProgressBean);
        showToast("添加成功");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_PROJECT://项目
                    List<ProjectBean> list = (List<ProjectBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showProject(list);
                    break;
                case Constants.REQUEST_CHOOSE_BUILDING://施工楼号
                    List<BuildingNoBean> bList = (List<BuildingNoBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showBuilding(bList);
                    break;
                case Constants.REQUEST_WEATHER:
                    weather = (WeatherBean) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showWeather(weather);
                    break;
                case Constants.REQUEST_TEAM:
                    List<TeamBean> mTeamList = (List<TeamBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showTeam(mTeamList);
                    break;
            }
        }
    }

    private void showTeam(List<TeamBean> mTeamList) {
        if (mTeamList == null || mTeamList.size() <= 0) {
            tvTeam.setText("");
            return;
        }
        TeamBean team = mTeamList.get(0);
        tvTeam.setText(team.getTeam_name());
        tvTeam.setTextColor(getResources().getColor(R.color.black_333333));
    }

    private void showWeather(WeatherBean weather) {
        tvWeather.setText("天气：" + weather.getWeather());
        tvTemperature.setText("温度：" + weather.getTemperature_min() + "℃~" + weather.getTemperature_max() + "℃");
        tvWindDirection.setText("风向：" + weather.getWind_direction());
        tvWindPower.setText("风力：" + weather.getWind_power() + "级");
        tvPrecipitation.setText("湿度：" + weather.getPrecipitation() + "%");
        tvDampness.setText("降水量：" + weather.getDampness() + "mm");
    }

    /**
     * 显示项目名
     *
     * @param list
     */
    private void showProject(List<ProjectBean> list) {
        if (list == null || list.size() <= 0) {
            tvProject.setText("");
            return;
        }
        ProjectBean projectBean = list.get(0);
        if (project == null || !projectBean.getId().equals(project.getId())) {
            project = list.get(0);
            tvProject.setText(project.getTitle());
        }
    }

    /**
     * 显示施工楼号
     *
     * @param bList
     */
    private void showBuilding(List<BuildingNoBean> bList) {
        if (bList == null || bList.size() == 0) {
            llParentContent.setVisibility(View.GONE);
            return;
        }
        llParentContent.setVisibility(View.VISIBLE);
        for (int i = 0; i < bList.size(); i++) {
            View parent = addBuildingView(bList.get(i));
            ViewBean viewBean = new ViewBean(bList.get(i).getId(), bList.get(i).getName(), parent);
            View child = addChild((LinearLayout) parent.findViewById(R.id.layout_progress_ll_content), bList.get(i).getId());
            viewBean.getChild().add(child);//添加一个子内容
            pViewList.add(viewBean);
            llParentContent.addView(parent);
        }
    }

    private View addBuildingView(final BuildingNoBean building) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_progress, null);
        view.setTag(building.getId());
        TextView tvBuildingNo = view.findViewById(R.id.layout_progress_tv_building_no);
        tvBuildingNo.setText(building.getName());
        final LinearLayout llContent = view.findViewById(R.id.layout_progress_ll_content);
        LinearLayout llAdd = view.findViewById(R.id.layout_progress_ll_add);
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild(llContent, building.getId());
            }
        });
        return view;
    }

    private View addChild(LinearLayout llContent, String id) {
        ViewBean parent = null;
        for (ViewBean viewBean : pViewList) {
            if (viewBean.getId().equals(id)) {
                parent = viewBean;
                break;
            }
        }
        int count = 1;
        if (parent == null || parent.getChild() == null) {
            count = 1;
        } else {
            count = parent.getChild().size() + 1;
        }
        View child = addChild(id, count);
        llContent.addView(child);
        addViewList(id, child);
        return child;
    }

    private View addChild(final String id, final int size) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.layout_progress_content, null);
        view.setTag(size);
        TextView tvDetail = view.findViewById(R.id.layout_progress_tv_detail);
        tvDetail.setText("明细" + size);
        TextView tvDelete = view.findViewById(R.id.layout_progress_tv_delete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChild(id, view);
            }
        });
        LinearLayout llSGGX = view.findViewById(R.id.layout_progress_ll_sggx);
        final TextView tvSGGX = view.findViewById(R.id.layout_progress_tv_sggx);
        llSGGX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConstructionProcessDialog(tvSGGX);
            }
        });
        EditText etWCL = view.findViewById(R.id.layout_progress_et_wcl);//完成量
        EditText etWCRate = view.findViewById(R.id.layout_progress_et_wc_rate);//已完成比例
        LinearLayout llBZ = view.findViewById(R.id.layout_progress_ll_bz);//施工班组
        final TextView tvBZ = view.findViewById(R.id.layout_progress_tv_bz);//施工班组
        llBZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTeam = tvBZ;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
                bundle.putString(Constants.EXTRA_DATA_PROJECT, project.getId());
                bundle.putString(Constants.EXTRA_DATA_BUILDING, id);
                IntentUtils.startActivityForResult(AddProgressActivity.this, TeamSelectionActivity.class, bundle, Constants.REQUEST_TEAM);
            }
        });
        final TextView tvWCLUnit = view.findViewById(R.id.layout_progress_tv_wcl_unit);//完成量 单位
        tvWCLUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnitDialog(tvWCLUnit);
            }
        });
        final TextView tvNum = view.findViewById(R.id.layout_progress_content_tv_num);//人数
        ImageView ivReduce = view.findViewById(R.id.layout_progress_content_iv_reduce);
        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = tvNum.getText().toString().trim();
                if (!"1".equals(num)) {
                    tvNum.setText(String.valueOf(Integer.parseInt(num) - 1));
                }
            }
        });
        ImageView ivAdd = view.findViewById(R.id.layout_progress_content_iv_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = tvNum.getText().toString().trim();
                tvNum.setText(String.valueOf(Integer.parseInt(num) + 1));
            }
        });
        return view;
    }

    private void deleteChild(String id, View child) {
        ViewBean parent = null;
        for (int k = 0; k < pViewList.size(); k++) {
            if (pViewList.get(k).getId().equals(id)) {
                parent = pViewList.get(k);
                if (parent != null && parent.getChild() != null && parent.getChild().size() > 0) {
                    LinearLayout llParent = parent.getView().findViewById(R.id.layout_progress_ll_content);
                    llParent.removeView(child);
                    int childTag = (int) child.getTag();
                    for (int j = 0; j < parent.getChild().size(); j++) {
                        int t = (int) parent.getChild().get(j).getTag();
                        if (childTag == t) {
                            pViewList.get(k).getChild().remove(j);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * 添加到集合中
     *
     * @param id
     * @param child
     */
    private void addViewList(String id, View child) {
        for (int i = 0; i < pViewList.size(); i++) {
            if (pViewList.get(i).getId().equals(id)) {
                pViewList.get(i).getChild().add(child);
            }
        }
    }


    @Override
    protected void showImage(String image_path) {
        File file = new File(image_path);
        if (file.exists()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().uploadImage(file, new HttpEngine.OnResponseCallback<HttpResponse.UploadFile>() {
                @Override
                public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.UploadFile data) {
                    LoadingDialog.disDialog();
                    if (result != 0) {
                        showToast(retmsg);
                        return;
                    }
                    zgImagesAdapter.addData(zgImagesAdapter.getData().size() - 1, data.getData().getFile_url());
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private UnitDialog unitDialog;

    private void showUnitDialog(final TextView tvUnit) {
        closeUnitDialog();
        unitDialog = new UnitDialog(this, LoadData.getUnitList(), new UnitDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, Object... param) {
                if (confirm) {
                    tvUnit.setText(((UnitBean) param[0]).getText());
                }
                closeUnitDialog();
            }
        });
        unitDialog.show();
    }

    private void closeUnitDialog() {
        if (unitDialog != null) {
            unitDialog.dismiss();
            unitDialog = null;
        }
    }

    private ConstructionProcessDialog constructionProcessDialog;

    private void showConstructionProcessDialog(final TextView tvProcess) {
        closeConstructionProcessDialog();
        constructionProcessDialog = new ConstructionProcessDialog(this, LoadData.getConstructProgressList(), new ConstructionProcessDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, Object... param) {
                if (confirm) {
                    tvProcess.setTextColor(getResources().getColor(R.color.black_333333));
                    tvProcess.setText(((ConstructionProcessBean) param[0]).getText());
                }
                closeConstructionProcessDialog();
            }
        });
        constructionProcessDialog.show();
    }

    private void closeConstructionProcessDialog() {
        if (constructionProcessDialog != null) {
            constructionProcessDialog.dismiss();
            constructionProcessDialog = null;
        }
    }

    private DateYMDDialog beginPickerDialog;

    private void showDatePickerDialog() {
        closeBeginPickerDialog();
        beginPickerDialog = new DateYMDDialog(this, new DateYMDDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    tvDate.setText(param[1]);
                }
                closeBeginPickerDialog();
            }
        });
        beginPickerDialog.show();
    }

    private void closeBeginPickerDialog() {
        if (beginPickerDialog != null) {
            beginPickerDialog.cancel();
            beginPickerDialog = null;
        }
    }


    @Override
    @OnClick({R.id.activity_add_progress_ll_date, R.id.activity_add_progress_ll_project, R.id.activity_add_progress_ll_floor, R.id.activity_add_progress_ll_weather, R.id.activity_add_progress_rtv_add})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_add_progress_ll_project:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivityForResult(AddProgressActivity.this, ProjectSelectionActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT);
                break;
            case R.id.activity_add_progress_ll_floor:
                if (project == null) {
                    showToast("请选择项目");
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString(Constants.EXTRA_DATA, project.getId());
                bundle2.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivityForResult(AddProgressActivity.this, BuildingSelectionActivity.class, bundle2, Constants.REQUEST_CHOOSE_BUILDING);
                break;
            case R.id.activity_add_progress_ll_weather:
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable(Constants.EXTRA_DATA, weather);
                IntentUtils.startActivityForResult(AddProgressActivity.this, AddWeatherActivity.class, bundle3, Constants.REQUEST_WEATHER);
                break;
            case R.id.activity_add_progress_rtv_add:
                uploadProgress();
                break;
            case R.id.activity_add_progress_ll_date:
                showDatePickerDialog();
                break;
        }
    }
}

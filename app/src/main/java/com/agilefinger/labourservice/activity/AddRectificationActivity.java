package com.agilefinger.labourservice.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ZGImagesAdapter;
import com.agilefinger.labourservice.adapter.ZGVoiceAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.ProjectBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.StaffBean;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.RectificationDao;
import com.agilefinger.labourservice.db.VoiceDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.DateFormatUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.view.dialog.AlertDialog;
import com.agilefinger.labourservice.view.dialog.ConfirmCusDialog;
import com.agilefinger.labourservice.view.dialog.DateDialog;
import com.agilefinger.labourservice.view.dialog.DatePickerDialog;
import com.agilefinger.labourservice.view.dialog.FloorDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.view.dialog.StaffPickerDialog;
import com.agilefinger.labourservice.widget.voice.EaseVoiceRecorderView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 新增整改派单
 */
public class AddRectificationActivity extends BaseActivity {

    @BindView(R.id.activity_add_rectification_rv_images)
    RecyclerView rvImages;
    @BindView(R.id.activity_add_rectification_rv_voice)
    RecyclerView rvVoice;
    @BindView(R.id.activity_add_rectification_tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorderView;
    @BindView(R.id.activity_add_rectification_tv_record)
    TextView tvRecord;
    @BindView(R.id.activity_add_rectification_tv_project)
    TextView tvProject;
    @BindView(R.id.activity_add_rectification_tv_floor)
    TextView tvFloor;
    @BindView(R.id.activity_add_rectification_ll_bottom)
    LinearLayout linearVoice;
    @BindView(R.id.activity_add_rectification_tv_staff)
    TextView tvStaff;
    @BindView(R.id.activity_add_rectification_et_location)
    EditText etLocation;
    private ZGImagesAdapter zgImagesAdapter;
    private ZGVoiceAdapter zgVoiceAdapter;
    private FloorDialog floorDialog;
    private DateDialog dateDialog;
    private DatePickerDialog datePickerDialog;
    private StaffPickerDialog staffPickerDialog;
    private ProjectBean project;
    private BuildingNoBean buildingNoBean;
    private FloorBean floorBean;
    private DirectionBean directionBean;
    private StaffBean staffBean;
    private String deadline;
    private ConfirmCusDialog confirmDialog;
    private String uuid;//一进页面生成一个主键
    private String companyId;
    private RectificationBean rectificationBean;
    private boolean isNeedSave = false;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_rectification;
    }

    @Override
    public void initData() {
        super.initData();
        if (!checkLogin()) finish();
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                },
                1
        );
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("新增整改派单", false, true, "");
        initRV();
        initEditInfo();

    }

    private void initEditInfo() {
        if (getIntent().getExtras() != null) {
            String type = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
            if (Constants.TYPE_EDIT.equals(type)) {
                uuid = getIntent().getExtras().getString(Constants.EXTRA_DATA);
                RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
                List<RectificationBean> mList = rectificationDao.queryRectification(uuid);
                if (mList != null && mList.size() > 0) {
                    rectificationBean = mList.get(0);
                    //声音
                    VoiceDao voiceDao = new VoiceDao(LSApplication.context);
                    List<VoiceBean> voiceList = voiceDao.queryVoice(rectificationBean.getId());
                    if (voiceList != null && voiceList.size() > 0) {
                        zgVoiceAdapter.setNewData(voiceList);
                    }
                    //项目信息
                    if (!TextUtils.isEmpty(rectificationBean.getPid())) {
                        project = new ProjectBean();
                        mProjectList = new ArrayList<>();
                        project.setId(rectificationBean.getPid());
                        project.setTitle(rectificationBean.getTitle());
                        mProjectList.add(project);
                        tvProject.setText(project.getTitle());
                    }
                    //图片
                    if (!TextUtils.isEmpty(rectificationBean.getImage())) {
                        List<String> images = Arrays.asList(rectificationBean.getImage().split(","));
                        if (images != null && images.size() > 0) {
                            List arrList = new ArrayList(images);
                            arrList.add("");
                            zgImagesAdapter.setNewData(arrList);
                        }
                    } else {
                        List<String> images = new ArrayList<>();
                        images.add("");
                        zgImagesAdapter.setNewData(images);
                    }
                    //截止日期
                    if (!TextUtils.isEmpty(rectificationBean.getDeadline())) {
                        deadline = rectificationBean.getDeadline();
                        tvDeadline.setText(rectificationBean.getDeadlineFormat());
                    }
                    //所在位置
                    if (!TextUtils.isEmpty(rectificationBean.getBuildingId()) && !TextUtils.isEmpty(rectificationBean.getDirectionId()) && !TextUtils.isEmpty(rectificationBean.getFloorId())) {
                        buildingNoBean = new BuildingNoBean();
                        floorBean = new FloorBean();
                        directionBean = new DirectionBean();
                        buildingNoBean.setId(rectificationBean.getBuildingId());
                        floorBean.setId(rectificationBean.getFloorId());
                        directionBean.setId(rectificationBean.getDirectionId());
                        tvFloor.setText(rectificationBean.getBuildingContent());
                    }
                    //详细位置描述
                    if (!TextUtils.isEmpty(rectificationBean.getBuildingDes())) {
                        etLocation.setText(rectificationBean.getBuildingDes());
                    }
                    //整改人
                    if (!TextUtils.isEmpty(rectificationBean.getZgManId())) {
                        staffBean = new StaffBean();
                        staffBean.setId(rectificationBean.getZgManId());
                        staffBean.setName(rectificationBean.getZgMan());
                        tvStaff.setText(staffBean.getName());
                    }
                }
            } else {
                List<String> mList = new ArrayList<>();
                mList.add("");
                zgImagesAdapter.setNewData(mList);
                deadline = DateFormatUtils.formatDeadline4(24);
                tvDeadline.setText(DateFormatUtils.formatDeadline3(24));
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initListener() {
        super.initListener();
        zgImagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String path = adapter.getItem(position).toString();
                if (TextUtils.isEmpty(path)) {
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
                        isNeedSave = true;
                        break;
                }
            }
        });
        zgVoiceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                VoiceBean voice = (VoiceBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_zg_voice_rll_voice:
                        playVoice(!TextUtils.isEmpty(voice.getoPath()) ? voice.getoPath() : URL.BASE_URL + voice.getPath(), (ImageView) view.findViewById(R.id.item_zg_voice_iv_play));//URL.BASE_URL + voice.getPath()
                        break;
                    case R.id.item_zg_voice_iv_del:
                        zgVoiceAdapter.remove(position);
                        isNeedSave = true;
                        break;
                }
            }
        });
        linearVoice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return voiceRecorderView.onPressToSpeakBtnTouch(view, motionEvent, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {

                        uploadVoice(voiceTimeLength, voiceFilePath);
                       /* List<VoiceBean> data = zgVoiceAdapter.getData();
                        Log.d("获取当前填过长度",data.size()+"::");
                        if (data.size()>4){
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.getInstands().toastShow(AddRectificationActivity.this,"语音限制为5条以内");
                                }
                            });
                        }else {

                        }*/

                    }
                });
            }
        });
    }

    /**
     * /**
     * 上传音频
     *
     * @param len
     * @param savePath
     */
    private void uploadVoice(final float len, final String savePath) {
        File file = new File(savePath);
        if (file.exists()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().uploadVoice(file, new HttpEngine.OnResponseCallback<HttpResponse.UploadFile>() {
                @Override
                public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.UploadFile data) {
                    LoadingDialog.disDialog();
                    if (result != 0) {
                        showToast(retmsg);
                        return;
                    }
                    VoiceBean voice = new VoiceBean(len, data.getData().getFile_url(), savePath);
                    zgVoiceAdapter.addData(voice);
                    //验证
                    if (rectificationBean != null) {
                        isNeedSave = true;
                    }
                }
            });
        }
    }

    private void initRV() {
        LinearLayoutManager zgLinearLayoutManager = new LinearLayoutManager(this);
        zgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zgImagesAdapter = new ZGImagesAdapter();
        rvImages.setLayoutManager(zgLinearLayoutManager);
        rvImages.setAdapter(zgImagesAdapter);
        //问题描述 声音
        zgVoiceAdapter = new ZGVoiceAdapter();
        rvVoice.setLayoutManager(new LinearLayoutManager(this));
        rvVoice.setAdapter(zgVoiceAdapter);
    }


    @Override
    public void initLoad() {
        super.initLoad();

    }

    @Override
    protected void toOperate() {
        uploadRectification();
    }

    /**
     * 新增整改派单
     */
    private void uploadRectification() {
        List<String> imageList = zgImagesAdapter.getData();
        if (imageList == null || imageList.size() < 2) {
            showAlertDialog("至少上传1张图片");
            return;
        }
        List<VoiceBean> voiceList = zgVoiceAdapter.getData();
        if (voiceList == null || voiceList.size() < 1) {
            showAlertDialog("至少上传1段语音");
            return;
        }
        if (project == null) {
            showAlertDialog("请选择项目");
            return;
        }
        if (buildingNoBean == null || floorBean == null || directionBean == null) {
            showAlertDialog("请选择所在位置");
            return;
        }
        if (staffBean == null) {
            showAlertDialog("请选择整改人");
            return;
        }
        if (TextUtils.isEmpty(deadline)) {
            showAlertDialog("请选择整改期限");
            return;
        }
        LoadingDialog.showLoading(this);
        String images = "";
        //imageList.remove(imageList.size() - 1);
        int size = imageList.size() - 1;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                images += imageList.get(i);
            } else {
                images += imageList.get(i) + ",";
            }
        }
        String voice = "", voice_second = "";
        for (int i = 0; i < voiceList.size(); i++) {
            if (i == voiceList.size() - 1) {
                voice += voiceList.get(i).getPath();
                voice_second += voiceList.get(i).getLen();
            } else {
                voice += voiceList.get(i).getPath() + ",";
                voice_second += voiceList.get(i).getLen() + ",";
            }
        }
        String location = etLocation.getText().toString().trim();
        HttpManager.getInstance().addRectification(loginBean.getUser_id(), companyId, project.getId(), buildingNoBean.getId(), floorBean.getId(), directionBean.getId(), staffBean.getId(), deadline, images, voice, location, voice_second, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                LoadingDialog.disDialog();
                showToast(retmsg);
                if (result != 0) {
                    return;
                }
                deleteRectification();
                finish();
            }
        });
    }

    @Override
    @OnClick({R.id.activity_add_rectification_ll_floor, R.id.activity_add_rectification_ll_deadline, R.id.activity_add_rectification_ll_project, R.id.activity_add_rectification_ll_zg_man})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_add_rectification_ll_floor:
                getFloorList();
                break;
            case R.id.activity_add_rectification_ll_deadline:
                showDateDialog();
                break;
            case R.id.activity_add_rectification_ll_project:
                //选择项目
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                bundle.putSerializable(Constants.EXTRA_DATA_, (Serializable) mProjectList);
                IntentUtils.startActivityForResult(AddRectificationActivity.this, ProjectSelectionActivity.class, bundle, Constants.REQUEST_CHOOSE_PROJECT);
                break;
            case R.id.activity_add_rectification_ll_zg_man://整改人
                /*Bundle bundleS = new Bundle();
                bundleS.putString(Constants.EXTRA_DATA, Constants.CHOOSE_TYPE_SINGLE);
                IntentUtils.startActivityForResult(AddRectificationActivity.this, StaffSelectionActivity.class, bundleS, Constants.REQUEST_CHOOSE_STAFF);*/
                getEmployeeList();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CHOOSE_PROJECT:
                    List<ProjectBean> list = (List<ProjectBean>) data.getExtras().getSerializable(Constants.EXTRA_DATA);
                    showProject(list);
                    break;
            }
        }
    }

    private List<ProjectBean> mProjectList;

    private void showProject(List<ProjectBean> list) {
        mProjectList = list;
        if (list == null || list.size() <= 0) {
            tvProject.setText("");
            return;
        }
        project = list.get(0);
        tvProject.setText(project.getTitle());
        buildingNoBean = null;
        floorBean = null;
        directionBean = null;
        staffBean = null;
        tvFloor.setText("请选择");
        tvStaff.setText("请选择");
        //验证
        if (rectificationBean != null) {
            if (!project.getId().equals(rectificationBean.getPid())) {
                isNeedSave = true;
            }
        }
    }

    private void getFloorList() {
        if (project == null) {
            showToast("请选择项目");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getFloorList(loginBean.getUser_id(), companyId, project.getId(), new HttpEngine.OnResponseCallback<HttpResponse.BuildingNo>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.BuildingNo data) {
                LoadingDialog.disDialog();
                if (result != 0) {
                    showToast(retmsg);
                    return;
                }
                showFloorDialog(data.getData());
            }
        });
    }

    private void showFloorDialog(List<BuildingNoBean> data) {
        closeFloorDialog();
        floorDialog = new FloorDialog(this, data, buildingNoBean, directionBean, floorBean, new FloorDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, Object... param) {
                if (confirm) {
                    buildingNoBean = (BuildingNoBean) param[0];
                    floorBean = (FloorBean) param[1];
                    directionBean = (DirectionBean) param[2];
                    showFloorTxt();
                }
                closeFloorDialog();
            }
        });
        floorDialog.show();
    }

    private void showFloorTxt() {
        if (buildingNoBean == null || floorBean == null || directionBean == null) {
            return;
        }
        tvFloor.setText(buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName());
        staffBean = null;
        tvStaff.setText("请选择");
        //验证
        if (rectificationBean != null) {
            if (!buildingNoBean.getId().equals(rectificationBean.getBuildingId())) {
                isNeedSave = true;
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
                    smoothMoveToPosition(rvImages, zgImagesAdapter.getData().size() - 1);
                    //验证
                    if (rectificationBean != null) {
                        isNeedSave = true;
                    }
                }
            });
        }
    }


    private void getEmployeeList() {
        if (project == null) {
            showToast("请选择项目");
            return;
        }
        if (buildingNoBean == null) {
            showToast("请选择所在位置");
            return;
        }
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getEmployeeList(loginBean.getUser_id(), companyId, project.getId(), buildingNoBean.getId(), new HttpEngine.OnResponseCallback<HttpResponse.Staff>() {
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
     * 整改期限选择框
     */
    private void showDateDialog() {
        closeDateDialog();
        dateDialog = new DateDialog(this, new DateDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    if (Constants.CUSTOMIZE.equals(param[0])) {
                        showDatePickerDialog();
                    } else {
                        deadline = DateFormatUtils.formatDeadline4(Integer.parseInt(param[0])) + " 23:59:59";
                        tvDeadline.setText(DateFormatUtils.formatDeadline3(Integer.parseInt(param[0])));
                        if (rectificationBean != null) {
                            if (!deadline.equals(rectificationBean.getDeadline())) {
                                isNeedSave = true;
                            }
                        }
                    }
                }
                closeDateDialog();
            }
        });
        dateDialog.show();
    }

    /**
     * 自定义时间选择器弹框
     */
    private void showDatePickerDialog() {
        closeDatePickerDialog();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    deadline = param[0];
                    tvDeadline.setText(param[1]);
                    //验证
                    if (rectificationBean != null) {
                        if (!deadline.equals(rectificationBean.getDeadline())) {
                            isNeedSave = true;
                        }
                    }
                }
                closeDatePickerDialog();
            }
        });
        datePickerDialog.show();
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
                    tvStaff.setText(staffBean.getName());
                    //验证
                    if (rectificationBean != null) {
                        if (!staffBean.getId().equals(rectificationBean.getZgManId())) {
                            isNeedSave = true;
                        }
                    }
                }
                closeStaffPickerDialog();
            }
        });
        staffPickerDialog.show();
    }

    private void closeFloorDialog() {
        if (floorDialog != null) {
            floorDialog.cancel();
            floorDialog = null;
        }
    }

    private void closeDateDialog() {
        if (dateDialog != null) {
            dateDialog.cancel();
            dateDialog = null;
        }
    }

    private void closeDatePickerDialog() {
        if (datePickerDialog != null) {
            datePickerDialog.cancel();
            datePickerDialog = null;
        }
    }

    private void closeStaffPickerDialog() {
        if (staffPickerDialog != null) {
            staffPickerDialog.cancel();
            staffPickerDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopVoice();
        closeFloorDialog();
        closeDateDialog();
        closeDatePickerDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConfirmDialog();
    }

    @Override
    protected void back() {
        if (isNeedSave()) {
            showConfirmDialog();
        } else {
            super.back();
        }
    }

    @Override
    public void onBackPressed() {
        if (isNeedSave()) {
            showConfirmDialog();
        } else {
            super.back();
        }
    }

    private void showConfirmDialog() {
        closeConfirmDialog();
        confirmDialog = new ConfirmCusDialog(this, "尚未保存，是否保存草稿？", true, false, new ConfirmCusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    saveRectification();
                    return;
                }
                finish();
            }
        });
        confirmDialog.show();
    }

    private boolean isNeedSave() {
        boolean isNeedSave = false;
        String sLocation = etLocation.getText().toString().trim();
        if (rectificationBean != null) {
            if (TextUtils.isEmpty(rectificationBean.getBuildingDes()) && TextUtils.isEmpty(sLocation)) {
                //this.isNeedSave = false;
            } else {
                if (!sLocation.equals(rectificationBean.getBuildingDes())) {
                    this.isNeedSave = true;
                }
            }
        }
        if (rectificationBean != null && this.isNeedSave) {
            return true;
        }
        if (rectificationBean != null && !this.isNeedSave) {
            return false;
        }
        List<String> imageList = zgImagesAdapter.getData();
        if (imageList != null && imageList.size() > 1) {
            isNeedSave = true;
        }
        List<VoiceBean> voiceList = zgVoiceAdapter.getData();
        if (voiceList != null && voiceList.size() > 0) {
            isNeedSave = true;
        }
        if (project != null) {
            isNeedSave = true;
        }
        if (buildingNoBean != null && floorBean != null && directionBean != null) {
            isNeedSave = true;
        }
        if (staffBean != null) {
            isNeedSave = true;
        }
        if (!TextUtils.isEmpty(deadline)) {
            isNeedSave = true;
        }
        if (!TextUtils.isEmpty(sLocation)) {
            isNeedSave = true;
        }
        return isNeedSave;
    }

    /**
     * 本地存储
     */
    private void saveRectification() {
        if (isNeedSave()) {
            deleteRectification();
            RectificationBean rectification = new RectificationBean();
            rectification.setState("");
            rectification.setId(uuid);



            rectification.setTime2(loginBean.getUser_id());

            rectification.setPerson(loginBean.getUser_name());
            rectification.setTime(DateFormatUtils.formatDate(new GregorianCalendar()));
            if (project != null) { //项目信息
                rectification.setPid(project.getId());
                rectification.setTitle(project.getTitle());
            }
            if (buildingNoBean != null && floorBean != null && directionBean != null) { //所在位置信息
                rectification.setBuildingContent(tvFloor.getText().toString().trim());
                rectification.setBuildingId(buildingNoBean.getId());
                rectification.setDirectionId(directionBean.getId());
                rectification.setFloorId(floorBean.getId());
            }
            if (!TextUtils.isEmpty(etLocation.getText().toString().trim())) {  //详细位置描述
                rectification.setBuildingDes(etLocation.getText().toString().trim());
            }
            if (deadline != null) { //整改期限
                rectification.setDeadline(deadline);
                rectification.setDeadlineFormat(tvDeadline.getText().toString().trim());
            }
            if (staffBean != null) { //整改人
                rectification.setZgMan(staffBean.getName());
                rectification.setZgManId(staffBean.getId());
            }
            List<String> imageList = zgImagesAdapter.getData();
            if (imageList != null || imageList.size() > 1) {
                String images = "";
                imageList.remove(imageList.size() - 1);
                for (int i = 0; i < imageList.size(); i++) {
                    if (i == imageList.size() - 1) {
                        images += imageList.get(i);
                    } else {
                        images += imageList.get(i) + ",";
                    }
                }
                rectification.setImage(images);
            }
            List<VoiceBean> voiceList = zgVoiceAdapter.getData();
            if (voiceList != null || voiceList.size() > 0) {
                for (int i = 0; i < voiceList.size(); i++) {
                    voiceList.get(i).setZid(uuid);
                }
            }
            RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
            VoiceDao voiceDao = new VoiceDao(LSApplication.context);
            voiceDao.addVoice(voiceList);
            rectificationDao.addRectification(rectification);
            showToast("保存成功");
            finish();
        }
    }

    private void deleteRectification() {
        RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
        VoiceDao voiceDao = new VoiceDao(LSApplication.context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
        } else {
            rectificationDao.deleteRectification(uuid);
            voiceDao.deleteVoice(uuid);
        }
    }

    private void closeConfirmDialog() {
        if (confirmDialog != null) {
            confirmDialog.dismiss();
            confirmDialog = null;
        }
    }

    private AlertDialog alertDialog;

    private void showAlertDialog(String message) {
        closeAlertDialog();
        alertDialog = new AlertDialog(this, message, false, new AlertDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                closeAlertDialog();
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
}

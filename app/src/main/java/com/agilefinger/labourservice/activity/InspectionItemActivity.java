package com.agilefinger.labourservice.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ImagesSmallAdapter;
import com.agilefinger.labourservice.adapter.PointAdapter;
import com.agilefinger.labourservice.adapter.PointHolder;
import com.agilefinger.labourservice.adapter.TuiJian2Adapter;
import com.agilefinger.labourservice.adapter.TuiJianHolder;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.FileBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.ImgBean;
import com.agilefinger.labourservice.bean.PicBean;
import com.agilefinger.labourservice.bean.PointDailsBean;
import com.agilefinger.labourservice.bean.PointMapBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.CheckPermissionUtils;
import com.agilefinger.labourservice.utils.EditTextUtils;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.LocationService;
import com.agilefinger.labourservice.utils.LocationServiceUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.utils.UiUtils;
import com.agilefinger.labourservice.view.dialog.FloorDialog;
import com.agilefinger.labourservice.view.dialog.InspectionItemDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.agilefinger.labourservice.activity.InspectionDetailActivity.BuildId;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.BuildName;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.NeiRong;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.imgAllList;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.taskName;


public class InspectionItemActivity extends BaseActivity {

    private static final int REQUEST_CODE = 99;

    @BindView(R.id.activity_inspection_item_rv_data)
    RecyclerView rvData;
    @BindView(R.id.name)
    TextView nametv;
    @BindView(R.id.need)
    TextView need;
    @BindView(R.id.pointnum)
    TextView pointnum;
    @BindView(R.id.remark)
    TextView remark;

    @BindView(R.id.isshow)
    RelativeLayout isshow;

    @BindView(R.id.isshowly)
    LinearLayout isshowly;

    @BindView(R.id.tupian)
    ImageView tupian;
    private RecyclerView rvList;
    private ImagesSmallAdapter imagesSmallAdapter;
    private TextView yinname;
    private TextView yinshebei;
    private TextView xiangmu;
    private TextView yintime;
    private TextView jingweidu;
    private LinearLayout huoqu;
    private TextView point;
    private TextView loutextview;
    private EditText shuoming;
    private EditText miaoshu;
    private EditText jieguo;
    private RoundTextView dialog_inspection_item_rtv_upload;
    private String name;
    private String ciid;
    private PointAdapter adapter;
    private View header;
    private View header2;
    private Uri imageUri;
    private String uriImg;
    int index = 0;
    String url;
    String url2;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                url = (String) msg.obj;
                Log.d("返回", url);
                handler.sendEmptyMessage(3);

            } else if (msg.what == 2) {
                url2 = (String) msg.obj;
                Log.d("返回", url2);
                handler.sendEmptyMessage(3);
            } else if (msg.what == 3) {
                index++;
                if (index == 2) {
                    index = 0;
                    Map<String, Object> pram = new HashMap<>();
                    pram.put("missionID", taskid);
                    pram.put("userID", loginBean.getUser_id());
                    pram.put("la", weidu);
                    pram.put("lo", jingdu);
                    pram.put("height", height);
                    pram.put("url", url);
                    pram.put("wurl", url2);
                    pram.put("address", didain);

                    OkHttp3Util.doPost(URL.BASE_URL_4 + "app/upload_point_pic", pram, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            Log.d("监测数据", string);
                            Gson gson = new Gson();
                            final ImgBean imgBean = gson.fromJson(string, ImgBean.class);
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    PicBean picBean = new
                                            PicBean();

                                    picBean.setPi_id(imgBean.getData().getPi_id());
                                    picBean.setPi_url(imgBean.getData().getPi_url());
                                    picBean.setPi_watermark_url(imgBean.getData().getPi_watermark_url());

                                    // picsList.add(picBean);
                                    imgAllList2.add(picBean);

                                    setAdapterImg();
                                }
                            });

                        }
                    });
                }
            }
            super.handleMessage(msg);
        }
    };
    private String taskid;
    private LocationService locationService;
    private TuiJian2Adapter ImgAdapter;
    private PointDailsBean.DataBean data;
    private FloorDialog floorDialog;
    private BuildingNoBean buildingNoBean;
    private FloorBean floorBean;
    private DirectionBean directionBean;
    private RadioButton hege;
    private RadioButton buhege;
    private RadioGroup rg;
    private String projectid;
    private CompanyBean company = new CompanyBean();
    private List<CompanyBean> companyList;
    private boolean iszhan = false;
    private TextView ismust;
    private LinearLayout shurull;
    private TextView danwei;

    private List<PicBean> imgAllList2 = new ArrayList<>();

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


    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_item;
    }

    private void initCompany() {
        CompanyDao companyDao = new CompanyDao(LSApplication.context);
        companyList = companyDao.queryCompanyAll();
        if (companyList != null && companyList.size() > 0) {
            company = companyList.get(0);

        }
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ciid = intent.getStringExtra("ciid");
        taskid = intent.getStringExtra("taskid");
        projectid = intent.getStringExtra("projectid");
        imgAllList2.clear();
        List<PicBean> imgList = DataCollectionActivity.getImgList();
        for (int i = 0; i < imgList.size(); i++) {
            imgAllList2.add(imgList.get(i));
        }
        LoadingDialog.showLoading(this);
        initCompany();
        locationService = ((LSApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        boolean start = locationService.isStart();
        if (!start) {
            locationService.start();
        }
        if (name != null) {
            setToolbarWhite(name);
        }


        uriImg = Environment.getExternalStorageDirectory() + "/lwtxunjian";
        UiUtils.fileMkdirs(uriImg);
        yinname = (TextView) findViewById(R.id.yinname);
        yinshebei = (TextView) findViewById(R.id.yinshebei);
        yintime = (TextView) findViewById(R.id.yintime);
        jingweidu = (TextView) findViewById(R.id.jingweidu);
        xiangmu = (TextView) findViewById(R.id.xiangmuming);
        huoqu = (LinearLayout) findViewById(R.id.huoqu);


        isshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!iszhan) {
                            isshowly.setVisibility(View.GONE);
                            iszhan = true;
                            tupian.setImageResource(R.mipmap.xiala);
                        } else {
                            iszhan = false;
                            isshowly.setVisibility(View.VISIBLE);
                            tupian.setImageResource(R.mipmap.shangla);
                        }
                    }
                });

            }
        });

        rvData.setLayoutManager(new GridLayoutManager(this, 5));
        rvData.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.bottom = 5;
                outRect.right = 7;
            }
        });
        //获取数据
        getData();
    }

    private void initRV(final List<PointDailsBean.DataBean.PointBean> point, String type) {
        header = View.inflate(InspectionItemActivity.this, R.layout.item_header, null);
        adapter = new PointAdapter(InspectionItemActivity.this, point, type);

        adapter.setHeaderView(header);
        rvData.setAdapter(adapter);
        adapter.setOnItemClickListener(new PointAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, PointHolder holder, List<PointDailsBean.DataBean.PointBean> list) {
                showInspectionItemDialog("" + (position + 1), true, point);
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInput("" + (point.size() + 1) + "");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault()
                    .post(new CatalogueEvenBus(ciid, data.getPoint().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //填写数据
    private void showInput(String s) {
        final Dialog dialog = new Dialog(InspectionItemActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(InspectionItemActivity.this).inflate(R.layout.dialog_inspection_item, null);
//        ((ViewGroup) view.getParent()).removeView(view);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(view);
        dialog.show();
        rvList = view.findViewById(R.id.dialog_inspection_item_rv_image);
        point = view.findViewById(R.id.dialog_inspection_item_tv_title);
        loutextview = view.findViewById(R.id.dialog_inspection_item_tv_position);
        shuoming = view.findViewById(R.id.shuoming);
        ismust = view.findViewById(R.id.ismust);
        miaoshu = view.findViewById(R.id.miaoshu);
        jieguo = view.findViewById(R.id.jieguo);
        hege = view.findViewById(R.id.hege);
        buhege = view.findViewById(R.id.buhege);
        rg = view.findViewById(R.id.rg);

        shurull = view.findViewById(R.id.shurull);
        danwei = view.findViewById(R.id.danwei);
        dialog_inspection_item_rtv_upload = view.findViewById(R.id.dialog_inspection_item_rtv_upload);


        EditTextUtils.editWatcher(jieguo, new EditTextUtils.EditTextChanged() {
            @Override
            public void beforeTextChanged() {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buhege:

                        if (data != null && data.getMci_need_pid().equals("f") && buhege.isChecked() || data != null && data.getMci_need_pid().equals("y")) {
                            ismust.setVisibility(View.VISIBLE);
                        } else {
                            ismust.setVisibility(View.INVISIBLE);
                        }

                        break;
                    case R.id.hege:

                        if (data != null && data.getMci_need_pid().equals("y")) {
                            ismust.setVisibility(View.VISIBLE);
                        } else {
                            ismust.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }
        });

        if (data != null && data.getMci_need_pid().equals("y")) {
            ismust.setVisibility(View.VISIBLE);
        } else {
            ismust.setVisibility(View.INVISIBLE);
        }

        point.setText(s + "号点位信息");

        if (data.getMci_type().equals("4") || data.getMci_type().equals("5")) {
            rg.setVisibility(View.VISIBLE);
            shurull.setVisibility(View.GONE);
        } else {
            rg.setVisibility(View.GONE);
            shurull.setVisibility(View.VISIBLE);
        }

        danwei.setText(data.getUnit());

        // picsList.clear();
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(InspectionItemActivity.this, 4);

        rvList.setLayoutManager(gridLayoutManager);
        rvList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 14;
                outRect.right = 10;
            }
        });

        setAdapterImg();

        loutextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择楼号
                getFloorList();
            }
        });
        dialog_inspection_item_rtv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //try {
                String ischang = "1";
                Map<String, PointMapBean> pram2 = new HashMap<>();
                if (data.getMci_type().equals("4") || data.getMci_type().equals("5")) {
                    PointMapBean pointMapBean = null;
                    if (hege.isChecked()) {
                        pointMapBean = new PointMapBean("合格", "y");
                        pram2.put(ciid, pointMapBean);
                    }
                    if (buhege.isChecked()) {
                        pointMapBean = new PointMapBean("不合格", "n");
                        pram2.put(ciid, pointMapBean);
                    }

                } else {

                    if (!jieguo.getText().toString().equals("")) {

                        if (data.getMci_type().equals("1")) {
                            List<PointDailsBean.DataBean.TypeInfoBean> type_info = data.getType_info();
                            if (!type_info.isEmpty() && null != type_info) {

                                DecimalFormat df = new DecimalFormat("0.00");

                                String s1 = jieguo.getText().toString();

                                String format = df.format((float) type_info.get(0).getMv_min() / 100);
                                float min = Float.valueOf(format);

                                String format2 = df.format((float) type_info.get(0).getMv_max() / 100);
                                float max = Float.valueOf(format2);

                                float i = Float.parseFloat(s1);
                                Log.d("获取的值", min + ":;" + max + "::" + i);


                                //张孟  改
                                if (i >= min && i <= max) {
                                    PointMapBean pointMapBean = new PointMapBean(jieguo.getText().toString(), "y");
                                    pram2.put(ciid, pointMapBean);
                                } else {
                                    PointMapBean pointMapBean = new PointMapBean(jieguo.getText().toString(), "n");
                                    pram2.put(ciid, pointMapBean);
                                }

//                                if (min <= i && i <= max) {
//                                    PointMapBean pointMapBean = new PointMapBean(jieguo.getText().toString(), "y");
//                                    pram2.put(ciid, pointMapBean);
//                                } else {
//                                    PointMapBean pointMapBean = new PointMapBean(jieguo.getText().toString(), "n");
//                                    pram2.put(ciid, pointMapBean);
//                                }
                            }
                        } else {
                            if (jieguo.getText().toString().contains(".")) {
                                Log.d("获取结果", jieguo.getText().toString());
                                String[] split = jieguo.getText().toString().split("\\.");
                                if (split[0].length() > 4) {
                                    ischang = "2";
                                }
                            }
                            PointMapBean pointMapBean = new PointMapBean(jieguo.getText().toString(), "n");
                            pram2.put(ciid, pointMapBean);
                        }


                    }
                }
                Log.d("检测长度", pram2.size() + ":::");
                if (ischang.equals("2")) {
                    MyToast.getInstands().toastShow(InspectionItemActivity.this, "检测结果应为小数点前4位");
                } else if (pram2.size() == 0) {
                    ToastUtil.showShort(InspectionItemActivity.this, "请填写检测结果");
                } else if (data != null && data.getMci_need_pid().equals("y") && imgAllList2.size() == 0) {
                    ToastUtil.showShort(InspectionItemActivity.this, "请上传影像留存");
                } else if (data != null && data.getMci_need_pid().equals("f") && imgAllList2.size() == 0 && buhege.isChecked()) {
                    ToastUtil.showShort(InspectionItemActivity.this, "请上传影像留存");
                } else {
                    if (loutextview.getText().toString().equals("设置位置")) {
                        ToastUtil.showShort(InspectionItemActivity.this, "请选择楼号");
                    } else {
                        //上传点位数据
                        Map<String, Object> pram = new HashMap<>();

                        String pid = "";
                        for (int i = 0; i < imgAllList2.size(); i++) {
                            if (pid.equals("")) {
                                pid = imgAllList2.get(i).getPi_id();
                            } else {
                                pid = pid + "," + imgAllList2.get(i).getPi_id();
                            }
                        }
                        pram.put("missionID", taskid);
                        pram.put("checkResult", GsonUtils.toJson(pram2));
                        pram.put("picID", pid);

                        Log.d("拆分数据", BuildInId);
                        pram.put("bID", BuildInId.split("-")[0]);
                        pram.put("fID", BuildInId.split("-")[1]);
                        pram.put("iID", BuildInId.split("-")[2]);
                        pram.put("des", miaoshu.getText().toString());
                        pram.put("userID", loginBean.getUser_id());
                        pram.put("remark", shuoming.getText().toString());
                        Log.d("添加点位传值", GsonUtils.toJson(pram));
                        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/upload_batch_info", pram, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String string = response.body().string();
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imgAllList2.clear();

                                        imgAllList.clear();
                                        NeiRong = "";
                                        BuildId = "";
                                        BuildName = "";
                                        dialog.dismiss();
                                        LoadingDialog.showLoading(InspectionItemActivity.this);
                                        getData();
                                        Log.d("添加点位", string);

                                    }
                                });

                            }
                        });
                    }

                }
               /* }catch (Exception e){
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.getInstands().toastShow(InspectionItemActivity.this,"上传点位失败,重新上传");
                        }
                    });
                }*/

            }
        });

    }


    private void getFloorList() {
        try {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().getFloorList2(loginBean.getUser_id(), company.getCompany_id(), taskid, new HttpEngine.OnResponseCallback<HttpResponse.BuildingNo>() {
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
        } catch (Exception e) {
            LoadingDialog.disDialog();
            MyToast.getInstands().toastShow(InspectionItemActivity.this, "没有获取到楼号信息");
        }

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


    private String BuildInId = "";

    private void showFloorTxt() {
        if (buildingNoBean == null || floorBean == null || directionBean == null) {
            return;
        }


        //BuildName=buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName();
        BuildInId = buildingNoBean.getId() + "-" + floorBean.getId() + "-" + directionBean.getId();
        loutextview.setText(buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName());


        // loutextview.setText("请选择");
        //验证

    }

    private void closeFloorDialog() {
        if (floorDialog != null) {
            floorDialog.cancel();
            floorDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("离开当前页面22", "进入" + BuildName + "::" + BuildId + ":" + imgAllList.size());
        /* if (imgAllList.size()==0){
         *//*  NeiRong="";
            BuildId="";
            BuildName="";*//*
        }else {
          //  NeiRong=miaoshu.getText().toString();
        }*/

    }

    //private List<PicBean> picsList=new ArrayList<>();
    //刷新图片
    private void setAdapterImg() {
        LoadingDialog.disDialog();
        if (imgAllList2.size() == 0) {
            NeiRong = "";
            BuildId = "";
            BuildName = "";
        } else {
            if (!NeiRong.equals("")) {
                miaoshu.setText(NeiRong);
            }
            if (!BuildName.equals("")) {
                loutextview.setText(BuildName);
            }

            if (!BuildId.equals("")) {
                String[] split = BuildId.split("-");
                BuildInId = split[0] + "-" + split[1] + "-" + split[2];
            }

        }

        header2 = View.inflate(InspectionItemActivity.this, R.layout.firstimg2, null);
        ImgAdapter = new TuiJian2Adapter(InspectionItemActivity.this, imgAllList2);
        ImgAdapter.setHeaderView(header2);
        rvList.setAdapter(ImgAdapter);

        header2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showLoading(InspectionItemActivity.this);
                takePhoto();
            }
        });

        ImgAdapter.setOnItemClickListener(new TuiJian2Adapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, TuiJianHolder holder, List<PicBean> list) {
                Map<String, Object> pram = new HashMap<>();
                pram.put("missionID", taskid);
                pram.put("picID", list.get(position).getPi_id());
                Log.d("删除传参", GsonUtils.toJson(pram));
                OkHttp3Util.doPost(URL.BASE_URL_4 + "app/delete_pic_from_mission", pram, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("删除图片", string);
                    }
                });
                imgAllList2.remove(list.get(position));
                ImgAdapter.notifyItemRemoved(position);
                ImgAdapter.notifyDataSetChanged();
                Log.d("删除图片", "::" + list.size());

            }
        });

    }

    /**
     * 调用相机
     */

    private void takePhoto() {
        initPermission();
        boolean openLocService = LocationServiceUtils.isOpenLocService(InspectionItemActivity.this);
        // Log.d("qqqqq9",openLocService+"::");
        if (!openLocService) {
            LoadingDialog.disDialog();
            ToastUtil.showShort(InspectionItemActivity.this, "请先打开GPS~");
        } else {
            File file;
            String cc = "qc-" + System.currentTimeMillis();
            String fileName = "IMG-" + cc + ".jpg";
            if (!uriImg.equals("2")) {
                //以fid为文件夹的名字
                file = new File(
                        uriImg, fileName);
                file.getParentFile().mkdirs();
            } else {
                file = new File(
                        Environment.getExternalStorageDirectory(), fileName);
                //file.getParentFile().mkdirs();
            }
            Log.d("wenjian", file.getPath());
            imageUri = Uri.fromFile(file);
            //imglist.add(imageUri.getPath());
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(captureIntent, REQUEST_CODE);
            overridePendingTransition(0, 0);

        }
        // }

    }

    //设置水印
    private void shuiYin() {
        final File file = new File(imageUri.getPath());
        if (file.length() > 0) {
            // Bitmap smallBitmap = getSmallBitmap(imageUri.getPath());
            //获取账号
            // String adminname = (String) SPUtils.get(getActivity(), "adminname", "");
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            yinname.setText(sdf.format(date));
// Log.d("dingweichakan",dianwei+":::");
            if (!TextUtils.isEmpty(didain)) {
                jingweidu.setText(didain);
            } else {
                jingweidu.setText("没有获取到位置信息");
            }
            if (!TextUtils.isEmpty(dingwei)) {
                yinshebei.setText(dingwei);
            } else {
                yinshebei.setText("没有获取到位置信息");
            }
            xiangmu.setText(taskName + " ");

            yintime.setText(loginBean.getUser_name());
            huoqu.setDrawingCacheEnabled(true);
            huoqu.buildDrawingCache();
            String cc = "qc-" + System.currentTimeMillis() + ".jpg";
            File newfile = new File(
                    uriImg + "/" + cc);
            final File file2 = new File(newfile.getPath());
            //旋转
            int bitmapDegree = UiUtils.getBitmapDegree(newfile.getPath());
            //保存生成的图片
            FileOutputStream fos = null;
            Bitmap bitmap1 = null;
            FileOutputStream yuanfos = null;
            try {
                Bitmap drawingCache = huoqu.getDrawingCache();
                //生成bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
                Bitmap bitmap2 = UiUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
                //俩个图片合成一个
                bitmap1 = UiUtils.mergeBitmap_TB(bitmap2, drawingCache, false);
                fos = new FileOutputStream(file2);
                yuanfos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, yuanfos);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 60, fos);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("图片路径", file.getPath() + "::" + file2.getPath());
                        HashMap<String, Object> pram = new HashMap<>();
                        pram.put("type", "img");
                        pram.put("file", file);
                        pram.put("is_thumb", "1");
                        OkHttp3Util.upLoadFile(URL.BASE_URL + "/Admin/file/fileUpload", pram, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    String string = response.body().string();
                                    Log.d("上传图片", string);
                                    Gson gson = new Gson();
                                    FileBean fileBean = gson.fromJson(string, FileBean.class);
                                    if (fileBean.getCode() == 0) {
                                        Message obtain = Message.obtain();
                                        obtain.obj = fileBean.getData().getFile_url();
                                        obtain.what = 1;
                                        handler.sendMessage(obtain);
                                    } else {
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.disDialog();
                                                MyToast.getInstands().toastShow(InspectionItemActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(InspectionItemActivity.this, "上传图片失败，请重新上传");
                                        }
                                    });

                                }

                            }
                        });

                        HashMap<String, Object> pram2 = new HashMap<>();
                        pram2.put("type", "img");
                        pram2.put("file", file2);
                        pram2.put("is_thumb", "1");
                        OkHttp3Util.upLoadFile(URL.BASE_URL + "/Admin/file/fileUpload", pram2, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    String string = response.body().string();
                                    Log.d("上传图片2", string);
                                    Gson gson = new Gson();
                                    FileBean fileBean = gson.fromJson(string, FileBean.class);
                                    if (fileBean.getCode() == 0) {
                                        Message obtain = Message.obtain();
                                        obtain.obj = fileBean.getData().getFile_url();
                                        obtain.what = 2;
                                        handler.sendMessage(obtain);
                                    } else {
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.disDialog();
                                                MyToast.getInstands().toastShow(InspectionItemActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(InspectionItemActivity.this, "上传图片失败，请重新上传");
                                        }
                                    });

                                }

                            }
                        });

                    } catch (Exception e) {
                        LoadingDialog.disDialog();
                    }
                }
            });
        }
    }

    private void initPermission() {
        String[] permissions = CheckPermissionUtils.checkPermission(InspectionItemActivity.this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(InspectionItemActivity.this, permissions, 100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            try {
                LoadingDialog.showLoading(this);
                if (RESULT_OK == resultCode) {
                    shuiYin();
                } else {
                    LoadingDialog.disDialog();
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(imageUri);
                sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //获取数据
    public void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("mci_id", ciid);
        Log.d("考核项id", "::" + ciid);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/write_data_qualified_detail", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    Log.d("点位详情", string);
                    Gson gson = new Gson();
                    PointDailsBean pointDailsBean = gson.fromJson(string, PointDailsBean.class);
                    if (null != pointDailsBean) {
                        if (pointDailsBean.getCode() == 0) {
                            data = pointDailsBean.getData();
                            if (null != data) {
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (name != null) {
                                            nametv.setText(name);
                                        }
                                        need.setText(data.getMci_explain());
                                        pointnum.setText("" + data.getMci_min_point());
                                        remark.setText(data.getMci_remark());
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.disDialog();
                                                if (!data.getPoint().isEmpty()) {
                                                    initRV(data.getPoint(), data.getMci_type());
                                                } else {
                                                    initRV(data.getPoint(), data.getMci_type());
                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initLoad() {
        super.initLoad();

    }

    private InspectionItemDialog inspectionItemDialog;

    private void showInspectionItemDialog(String s, boolean b, List<PointDailsBean.DataBean.PointBean> point) {
        closeInspectionItemDialog();
        inspectionItemDialog = new InspectionItemDialog(this, s, b, point, loginBean.getUser_id(), new InspectionItemDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                closeInspectionItemDialog();
            }
        });
        inspectionItemDialog.show();
    }

    private void closeInspectionItemDialog() {
        if (inspectionItemDialog != null) {
            inspectionItemDialog.cancel();
        }
        inspectionItemDialog = null;
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private String dingwei = "";
    private String didain = "";
    private String weidu = "";
    private String jingdu = "";
    private String height = "";


    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****// 单位：米
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                double altitude = location.getAltitude();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("点位定位数据", altitude + "::" + latitude + "::" + longitude);
                if (latitude == 4.9E-324 || longitude == 4.9E-324) {

//                    DecimalFormat df = new DecimalFormat("0.00");
//                    jingdu = df.format(latitude);
//                    weidu = df.format(longitude);
//                    height = df.format(altitude);

                    dingwei = "";
                } else {
                    jingdu = "" + latitude + "";
                    weidu = "" + longitude + "";
                    height = "" + altitude + "";
                    String beiWei = "N: " + location.getLatitude();
                    String dongJing = "E: " + location.getLongitude();
                    String beistring = beiWei.substring(0, 9);
                    String nanstring = dongJing.substring(0, 10);
                    if (altitude == 4.9E-324) {
                        dingwei = beistring + "°  " + nanstring + "°  " + "H: " + "  m";
                    } else {
                        dingwei = beistring + "°  " + nanstring + "°  " + "H: " + location.getAltitude() + " m";
                    }
                }
                if (null != location.getAddrStr() && null != location.getLocationDescribe()) {
                    didain = location.getAddrStr() + location.getLocationDescribe();
                }

            }
        }

    };
}

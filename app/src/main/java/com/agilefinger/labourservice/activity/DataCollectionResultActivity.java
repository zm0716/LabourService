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
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.BathAddAdaper;
import com.agilefinger.labourservice.adapter.DataCollectionResultAdapter;
import com.agilefinger.labourservice.adapter.TuiJianAdapter;
import com.agilefinger.labourservice.adapter.TuiJianHolder;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.FileBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.ImgBean;
import com.agilefinger.labourservice.bean.PiLiangBean;
import com.agilefinger.labourservice.bean.PicBean;
import com.agilefinger.labourservice.bean.PointMapBean;
import com.agilefinger.labourservice.bean.SonAndItemsBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.CatalogueDataUtils;
import com.agilefinger.labourservice.utils.CheckPermissionUtils;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.LocationService;
import com.agilefinger.labourservice.utils.LocationServiceUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.utils.UiUtils;
import com.agilefinger.labourservice.view.dialog.FloorDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


public class DataCollectionResultActivity extends BaseActivity {
    private static final int REQUEST_CODE = 88;


    @BindView(R.id.activity_data_collection_result_rv_images)
    RecyclerView rvList;
    @BindView(R.id.activity_data_collection_result_rv_result)
    RecyclerView item_rv;
    @BindView(R.id.activity_add_rectification_tv_measure_position)
    TextView louhao;

    @BindView(R.id.checktv)
    RoundTextView checktv;
    @BindView(R.id.endtv)
    RoundTextView endtv;
    @BindView(R.id.destv)
    EditText destv;

    @BindView(R.id.remark_tv)
    EditText remark_tv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;


    private CompanyBean company = new CompanyBean();
    private DataCollectionResultAdapter dataCollectionResultAdapter;
    private String projectid;
    private String taskId;
    private String itemID;
    private BathAddAdaper bathAddAdaper;
    private LocationService locationService;
    private String uriImg;
    private Uri imageUri;
    private TextView yinname;
    private TextView yinshebei;
    private TextView xiangmu;
    private TextView yintime;
    private TextView jingweidu;
    private LinearLayout huoqu;
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
                    pram.put("missionID", taskId);
                    pram.put("userID", loginBean.getUser_id());
                    pram.put("la", weidu);
                    pram.put("lo", jingdu);
                    pram.put("height", "");
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
    private View header2;
    private TuiJianAdapter ImgAdapter;

    private FloorDialog floorDialog;
    private BuildingNoBean buildingNoBean;
    private FloorBean floorBean;
    private DirectionBean directionBean;
    private List<PiLiangBean.DataBean.InfoBean> infoBeanList;
    private List<CompanyBean> companyList;
    private String json;
    private ArrayList<SonAndItemsBean> items1;
    private SonAndItemsBean sonAndItemsBean;
    private String type;

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
        return R.layout.activity_data_collection_result;
    }

    private void initCompany() {
        CompanyDao companyDao = new CompanyDao(LSApplication.context);
        companyList = companyDao.queryCompanyAll();
        if (companyList != null && companyList.size() > 0) {
            company = companyList.get(0);

        }
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }


    private List<PicBean> imgAllList2 = new ArrayList<>();
    private String ciid;

    @Override
    public void initView() {
        super.initView();

        setToolbar("检测结果", true, false, "");
        Bundle bundle = getIntent().getExtras();
        taskId = getIntent().getStringExtra("taskid");
        projectid = getIntent().getStringExtra("projectid");
        ciid = getIntent().getStringExtra("ciid");
        itemID = getIntent().getStringExtra("itemID");
        type = getIntent().getStringExtra("type");
//        items1 = (ArrayList<SonAndItemsBean>) bundle.getSerializable("items");
        sonAndItemsBean = (SonAndItemsBean) getIntent().getSerializableExtra("items");
        if (sonAndItemsBean != null) {
            items1 = CatalogueDataUtils.getInstance().getItems(sonAndItemsBean);
            Log.e("AAA", items1.size() + "");
        }

        LoadingDialog.showLoading(this);
        imgAllList2.clear();

        List<PicBean> imgList = DataCollectionActivity.getImgList();
        for (int i = 0; i < imgList.size(); i++) {
            imgAllList2.add(imgList.get(i));
        }
        initCompany();
        locationService = ((LSApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        boolean start = locationService.isStart();
        if (!start) {
            locationService.start();
        }
        uriImg = Environment.getExternalStorageDirectory() + "/lwtxunjian";
        UiUtils.fileMkdirs(uriImg);
        yinname = (TextView) findViewById(R.id.yinname);
        yinshebei = (TextView) findViewById(R.id.yinshebei);
        yintime = (TextView) findViewById(R.id.yintime);
        jingweidu = (TextView) findViewById(R.id.jingweidu);
        xiangmu = (TextView) findViewById(R.id.xiangmuming);
        huoqu = (LinearLayout) findViewById(R.id.huoqu);

        getData();
        //数据
        item_rv.setNestedScrollingEnabled(false);//关闭嵌套滑动
        item_rv.setLayoutManager(new LinearLayoutManager(DataCollectionResultActivity.this));
        item_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 8;
                outRect.left = 14;
                outRect.right = 14;
            }
        });
        item_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(DataCollectionResultActivity.this, 4);

        rvList.setLayoutManager(gridLayoutManager);
        rvList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.right = 20;
            }
        });

        setAdapterImg();

        louhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择楼号
                getFloorList();
            }
        });

        scrollview.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollview.setFocusable(true);
        scrollview.setFocusableInTouchMode(true);
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("离开当前页面", "进入");
        /*   if (imgAllList2.size()==0){
         *//*   NeiRong="";
            BuildId="";
            BuildName="";
            destv.setText("");
            louhao.setText("请选择");*//*
        }else {
            NeiRong=destv.getText().toString();
        }*/

    }

    //数据
    private void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskId);
        pram.put("itemID", itemID);
        Log.d("获取批量数据传参", GsonUtils.toJson(pram));
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_batch_info", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("获取批量数据", string);
                Gson gson = new Gson();
                PiLiangBean piLiangBean = gson.fromJson(string, PiLiangBean.class);
                if (piLiangBean != null) {
                    if (piLiangBean.getCode() == 0) {
                        final PiLiangBean.DataBean data = piLiangBean.getData();
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!data.getInfo().isEmpty()) {
                                    infoBeanList = data.getInfo();
                                    setItemRvAdapter(infoBeanList);
                                }

                            }
                        });
                    }
                }

                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.disDialog();
                    }
                });

            }
        });
    }

    private int isPass = 0;

    //item适配器
    private void setItemRvAdapter(List<PiLiangBean.DataBean.InfoBean> info) {
        if (bathAddAdaper == null) {
            bathAddAdaper = new BathAddAdaper(DataCollectionResultActivity.this, info, checktv, endtv);
            item_rv.setAdapter(bathAddAdaper);
        } else {
            bathAddAdaper.notifyDataSetChanged();
        }

        bathAddAdaper.setOnItemClickListener(new BathAddAdaper.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, BathAddAdaper.Hm2Holder holder, List<PiLiangBean.DataBean.InfoBean> data) {
                isPass = 0;
                String s = louhao.getText().toString();
                //上传点位数据
                //上传点位数据
                Map<String, Object> pram = new HashMap<>();
                Map<String, PointMapBean> pram2 = new HashMap<>();
                for (int i = 0; i < data.size(); i++) {

                    if (data.get(i).getMci_type().equals("4") || data.get(i).getMci_type().equals("5")) {
                        PointMapBean pointMapBean = null;
                        if (data.get(i).isIsxuan()) {
                            if (data.get(i).getCheck().equals("合格")) {
                                pointMapBean = new PointMapBean("合格", "y");
                                pram2.put(data.get(i).getMci_id(), pointMapBean);
                            } else if (data.get(i).getCheck().equals("不合格")) {
                                pointMapBean = new PointMapBean("不合格", "n");
                                pram2.put(data.get(i).getMci_id(), pointMapBean);
                            }
                        }
                    } else {
                        if (!data.get(i).getResult().equals("")) {
                            if (data.get(i).getMci_type().equals("1")) {
                                List<PiLiangBean.DataBean.InfoBean.TypeInfoBean> type_info = data.get(i).getType_info();
                                if (!type_info.isEmpty() && null != type_info) {
                                    String s1 = data.get(i).getResult();

                                    float min = type_info.get(0).getMv_min() / 100;

                                    float max = type_info.get(0).getMv_max() / 100;

                                    float num = Float.parseFloat(s1);
                                    Log.d("获取的值", min + ":;" + max + "::" + num);
                                    if (min <= num && num <= max) {
                                        PointMapBean pointMapBean = new PointMapBean(data.get(i).getResult(), "y");
                                        pram2.put(data.get(i).getMci_id(), pointMapBean);
                                    } else {
                                        PointMapBean pointMapBean = new PointMapBean(data.get(i).getResult(), "n");
                                        pram2.put(data.get(i).getMci_id(), pointMapBean);
                                    }
                                }
                            } else {
                                if (data.get(i).getResult().length() > 7) {
                                    isPass = 1;
                                }
                                PointMapBean pointMapBean = new PointMapBean(data.get(i).getResult(), "n");
                                pram2.put(data.get(i).getMci_id(), pointMapBean);
                            }
                        }
                    }
                }
                Log.d("当前字段", s);
                if (s.equals("请选择")) {
                    ToastUtil.showShort(DataCollectionResultActivity.this, "请选择楼号");
                } else if (imgAllList2.size() == 0) {
                    ToastUtil.showShort(DataCollectionResultActivity.this, "请添加影像留存");
                } else if (pram2.size() == 0) {
                    ToastUtil.showShort(DataCollectionResultActivity.this, "至少填写一条检测结果");
                } else if (isPass == 1) {
                    ToastUtil.showShort(DataCollectionResultActivity.this, "检测结果应为小数点前4位");
                } else {
                    String pid = "";
                    for (int i = 0; i < imgAllList2.size(); i++) {
                        if (pid.equals("")) {
                            pid = imgAllList2.get(i).getPi_id();
                        } else {
                            pid = pid + "," + imgAllList2.get(i).getPi_id();
                        }
                    }

                    pram.put("missionID", taskId);
                    pram.put("checkResult", GsonUtils.toJson(pram2));
                    pram.put("picID", pid);
                    pram.put("bID", BuildInId.split("-")[0]);
                    pram.put("fID", BuildInId.split("-")[1]);
                    pram.put("iID", BuildInId.split("-")[2]);
                    pram.put("des", destv.getText().toString());
                    pram.put("userID", loginBean.getUser_id());
                    pram.put("remark", remark_tv.getText().toString());
                    Log.d("添加点位传值", GsonUtils.toJson(pram));
                    OkHttp3Util.doPost(URL.BASE_URL_4 + "app/upload_batch_info", pram, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String string = response.body().string();
                            imgAllList.clear();
                            imgAllList2.clear();
                            NeiRong = "";
                            BuildId = "";
                            BuildName = "";
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("添加点位", string);
                                    /*
                                     *4163
                                     * */
                                    if (null!=type&&type.equals("0")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("projectid", projectid + "");
                                        bundle.putString("taskid", taskId + "");
                                        bundle.putString("taskname", taskName + "");
                                        IntentUtils.startActivity(DataCollectionResultActivity.this, DataCollectionActivity.class, bundle);
                                    } else {
                                        if (null != items1) {
                                            for (int i = 0; i < items1.size(); i++) {
                                                EventBus.getDefault().post(new CatalogueEvenBus("Text",1));
                                            }
                                        }
                                    }
                                    finish();
                                }
                            });

                        }
                    });
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void initLoad() {
        super.initLoad();

        // dataCollectionResultAdapter.setNewData(LoadData.getStringList(itemsList2));
    }

    private void getFloorList() {
        try {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().getFloorList2(loginBean.getUser_id(), company.getCompany_id(), taskId, new HttpEngine.OnResponseCallback<HttpResponse.BuildingNo>() {
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
            MyToast.getInstands().toastShow(DataCollectionResultActivity.this, "没有获取到楼号信息");
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
        // BuildName=buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName();
        BuildInId = buildingNoBean.getId() + "-" + floorBean.getId() + "-" + directionBean.getId();
        louhao.setText(buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName());

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
    protected void toOperate() {
        super.toOperate();
        UiUtils.uploadDialog(DataCollectionResultActivity.this, taskId, projectid);
    }


    //刷新图片
    private void setAdapterImg() {
        try {
            if (imgAllList2.size() == 0) {
                NeiRong = "";
                BuildId = "";
                BuildName = "";
            } else {
                if (!NeiRong.equals("")) {
                    destv.setText(NeiRong);
                }
                if (!BuildName.equals("")) {
                    louhao.setText(BuildName);
                }

                if (!BuildId.equals("")) {
                    String[] split = BuildId.split("-");
                    BuildInId = split[0] + "-" + split[1] + "-" + split[2];
                }


            }
            header2 = View.inflate(DataCollectionResultActivity.this, R.layout.firstimg, null);
            header2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadingDialog.showLoading(DataCollectionResultActivity.this);
                    takePhoto();
                }
            });
            ImgAdapter = new TuiJianAdapter(DataCollectionResultActivity.this, imgAllList2);
            ImgAdapter.setHeaderView(header2);
            rvList.setAdapter(ImgAdapter);

            ImgAdapter.setOnItemClickListener(new TuiJianAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, TuiJianHolder holder, List<PicBean> list) {
                    Map<String, Object> pram = new HashMap<>();
                    pram.put("missionID", taskId);
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
            LoadingDialog.disDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 调用相机
     */

    private void takePhoto() {
        initPermission();
        boolean openLocService = LocationServiceUtils.isOpenLocService(DataCollectionResultActivity.this);
        // Log.d("qqqqq9",openLocService+"::");
        if (!openLocService) {
            ToastUtil.showShort(DataCollectionResultActivity.this, "请先打开GPS~");
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
                Uri uri = Uri.fromFile(file2);

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
                                                MyToast.getInstands().toastShow(DataCollectionResultActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(DataCollectionResultActivity.this, "上传图片失败，请重新上传");
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
                                                MyToast.getInstands().toastShow(DataCollectionResultActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(DataCollectionResultActivity.this, "上传图片失败，请重新上传");
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
        String[] permissions = CheckPermissionUtils.checkPermission(DataCollectionResultActivity.this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(DataCollectionResultActivity.this, permissions, 100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE) {
                LoadingDialog.showLoading(this);
                Log.d("paizhao", requestCode + "::" + resultCode + "::" + data);
                if (RESULT_OK == resultCode) {
                    shuiYin();
                } else {
                    LoadingDialog.disDialog();
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(imageUri);
                sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
                if (latitude == 4.9E-324 || longitude == 4.9E-324) {
                    dingwei = "";
                } else {
//                    DecimalFormat df = new DecimalFormat("0.00");
//                    jingdu = df.format(latitude);
//                    weidu = df.format(longitude);
//                    height = df.format(altitude);
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
                Log.d("www", ":::" + didain + "::" + weidu);
            }
        }

    };
}

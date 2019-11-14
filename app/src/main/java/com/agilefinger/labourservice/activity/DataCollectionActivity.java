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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.DataCollectionKind2FilterAdapter;
import com.agilefinger.labourservice.adapter.DataCollectionKind2TabAdapter;
import com.agilefinger.labourservice.adapter.DataCollectionListAdapter;
import com.agilefinger.labourservice.adapter.EndTaskAdaper;
import com.agilefinger.labourservice.adapter.TabCheckAdaper;
import com.agilefinger.labourservice.adapter.TuiJianAdapter;
import com.agilefinger.labourservice.adapter.TuiJianHolder;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.BuildingNoBean;
import com.agilefinger.labourservice.bean.CompanyBean;
import com.agilefinger.labourservice.bean.DataCollectionFilterBean;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.agilefinger.labourservice.bean.DetailFirstBean;
import com.agilefinger.labourservice.bean.DirectionBean;
import com.agilefinger.labourservice.bean.EndTaskBean;
import com.agilefinger.labourservice.bean.FatherBean;
import com.agilefinger.labourservice.bean.FileBean;
import com.agilefinger.labourservice.bean.FloorBean;
import com.agilefinger.labourservice.bean.ImageDaoBean;
import com.agilefinger.labourservice.bean.ImgBean;
import com.agilefinger.labourservice.bean.Item2Bean;
import com.agilefinger.labourservice.bean.ItemBean;
import com.agilefinger.labourservice.bean.PiHeGeBean;
import com.agilefinger.labourservice.bean.PicBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.db.CompanyDao;
import com.agilefinger.labourservice.db.ImageDao;
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
import com.agilefinger.labourservice.view.dialog.EndTaskDialog;
import com.agilefinger.labourservice.view.dialog.FloorDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

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
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.agilefinger.labourservice.activity.InspectionDetailActivity.BuildId;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.BuildName;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.NeiRong;
import static com.agilefinger.labourservice.activity.InspectionDetailActivity.imgAllList;

public class DataCollectionActivity extends BaseActivity {

    private static final int REQUEST_CODE = 33;
    @BindView(R.id.activity_data_collection_ll_without_picture)
    LinearLayout llWithoutPicture;
    @BindView(R.id.activity_data_collection_ll_with_picture)
    LinearLayout llWithPicture;
    @BindView(R.id.activity_data_collection_ll_kind_2)
    LinearLayout llKind2;
    @BindView(R.id.activity_data_collection_rv_list)
    RecyclerView rvList;
    @BindView(R.id.activity_data_collection_rv_tab)
    RecyclerView rvTab;
    @BindView(R.id.activity_data_collection_rv_filter)
    RecyclerView rvFilter;
    @BindView(R.id.activity_data_collection_rv_kind_2_list)
    RecyclerView rvKind2List;
    @BindView(R.id.activity_data_collection_rv_images)
    RecyclerView rvImages;
    @BindView(R.id.activity_data_collection_type)
    RoundTextView ivToggle;
    @BindView(R.id.activity_data_collection_rtv_multi)
    RoundTextView rtvMulti;
    @BindView(R.id.search)
    RoundLinearLayout search;
    @BindView(R.id.desc_et)
    EditText desc_et;

    @BindView(R.id.activity_add_rectification_tv_measure_position)
    TextView lou_hao;
    @BindView(R.id.activity_data_collection_ll_measure_position)
    RelativeLayout measure_position;

    @BindView(R.id.search2)
    RoundLinearLayout search2;
    private boolean isCatalogue = true;
    private DataCollectionListAdapter dataCollectionListAdapter;
    private DataCollectionKind2TabAdapter dataCollectionKind2TabAdapter;
    private DataCollectionKind2FilterAdapter dataCollectionKind2FilterAdapter;
    private int curTabPosition = 0, curFilterPosition = 0;
    private String taskId;
    private LocationService locationService;
    private String uriImg;
    private Uri imageUri;
    private TextView yinname;
    private TextView yinshebei;
    private TextView xiangmu;
    private TextView yintime;
    private TextView jingweidu;
    private LinearLayout huoqu;
    private File mImageFile;
    private String mImagePath;
    public static List<ItemBean> treeList2 = new ArrayList<>();
    List<ItemBean> sonList2 = new ArrayList<>();
    List<ItemBean> itemsList2 = new ArrayList<>();
    public static List<ItemBean> AllitemsList = new ArrayList<>();
    private View header;
    private TuiJianAdapter adapter;
    public static List<PicBean> picsList = new ArrayList<>();
    private String projectid;
    private TabCheckAdaper tabAdapter;
    private String taskname;
    private RoundLinearLayout dialog_end_task_rll_sign;
    private RoundLinearLayout dialog_end_task_rll_ok;
    private RecyclerView rv_list;
    private TextView number_tv;
    private TextView queshao_tv;
    private FloorDialog floorDialog;
    private BuildingNoBean buildingNoBean;
    private FloorBean floorBean;
    private DirectionBean directionBean;
    private CompanyBean company = new CompanyBean();
    private List<CompanyBean> companyList;
    private List<DataCollectionFilterBean> dataCollectionFilterList;
    private ImageDao imageDao;
    private List<PicBean> imageDaoBeans;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public int initLayoutView() {
        return R.layout.activity_data_collection;
    }

    private void initCompany() {
        CompanyDao companyDao = new CompanyDao(LSApplication.context);
        companyList = companyDao.queryCompanyAll();
        if (companyList != null && companyList.size() > 0) {
            company = companyList.get(0);
        }
    }

    @Override
    protected void toOperate() {
        UiUtils.uploadDialog(DataCollectionActivity.this, taskId, projectid);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("数据采集", true, false, "");

        LSApplication.addActivity(DataCollectionActivity.this);
        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskid");
        projectid = bundle.getString("projectid");
        taskname = bundle.getString("taskname");
        firstFather.clear();
        treeList2.clear();

        yinname = (TextView) findViewById(R.id.yinname);
        yinshebei = (TextView) findViewById(R.id.yinshebei);
        yintime = (TextView) findViewById(R.id.yintime);
        jingweidu = (TextView) findViewById(R.id.jingweidu);
        xiangmu = (TextView) findViewById(R.id.xiangmuming);
        huoqu = (LinearLayout) findViewById(R.id.huoqu);

        GridLayoutManager filterLinearLayout = new GridLayoutManager(DataCollectionActivity.this, 4);

     /*   LinearLayoutManager filterLinearLayout = new LinearLayoutManager(this);
        filterLinearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);*/
        rvImages.setLayoutManager(filterLinearLayout);
        rvImages.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 10;
                outRect.right = 20;
            }
        });


        //查表
//        imageDao = new ImageDao(LSApplication.context);
//        imageDaoBeans = imageDao.queryImageAll();
//        if (imageDaoBeans.size() > 0) {
//            for (int i = 0; i < imageDaoBeans.size(); i++) {
//                if (imageDaoBeans.get(i).getPi_tastId().equals(taskId)) {
//                    imgAllList.add(imageDaoBeans.get(i));
//                }
//            }
//        }
//
//        if (imgAllList.size() > 0) {
//            llWithPicture.setVisibility(View.VISIBLE);
//            llWithoutPicture.setVisibility(View.GONE);
//        }
//
//        if (adapter == null) {
//            header = View.inflate(DataCollectionActivity.this, R.layout.firstimg, null);
//            adapter = new TuiJianAdapter(DataCollectionActivity.this, imgAllList);
//            adapter.setHeaderView(header);
//            rvImages.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//
//
//        //删除操作
//        adapter.setOnItemClickListener(new TuiJianAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position, TuiJianHolder holder, List<PicBean> list) {
//                //删除当前图片
//                imgAllList.remove(position);
//                adapter.notifyItemRemoved(position);
//                adapter.notifyDataSetChanged();
//
//                if (imgAllList.size() == 0) {
//                    llWithPicture.setVisibility(View.GONE);
//                    llWithoutPicture.setVisibility(View.VISIBLE);
//                }
//
//
//                if (imageDaoBeans.size() > 0) {
//                    imageDao.deleteProgress();
//                    for (int i = 0; i < imgAllList.size(); i++) {
//                        imageDao.addImage(imgAllList.get(i));
//                    }
////                    imageDao.addImage();
//                }
//
//            }
//        });
//
//
//        header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //  LoadingDialog.showLoading(DataCollectionActivity.this);
//                takePhoto();
//            }
//        });


//        setAda();

        initCompany();
        lou_hao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择楼号
                getFloorList();
            }
        });

        //获取页面信息
        //LoadingDialog.showLoading(this);
        //getData();

        initRVKind2();
        //开启定位
        locationService = ((LSApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        boolean start = locationService.isStart();
        if (!start) {
            locationService.start();
        }
        //创建存图片文件夹
        uriImg = Environment.getExternalStorageDirectory() + "/lwtxunjian";
        UiUtils.fileMkdirs(uriImg);


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
            MyToast.getInstands().toastShow(DataCollectionActivity.this, "没有获取到楼号信息");
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

    private void showFloorTxt() {
        if (buildingNoBean == null || floorBean == null || directionBean == null) {
            return;
        }

        BuildName = buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName();
        BuildId = buildingNoBean.getId() + "-" + floorBean.getId() + "-" + directionBean.getId();
        lou_hao.setText(buildingNoBean.getName() + "-" + floorBean.getName() + "-" + directionBean.getName());


    }

    private void closeFloorDialog() {
        if (floorDialog != null) {
            floorDialog.cancel();
            floorDialog = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //获取页面信息
    private void getData() {

        Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskId);
        Log.d("传参", taskId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_mission_detail", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();

                Log.d("返回数据", string);
                final Gson gson = new Gson();
                final DetailFirstBean detailFirstBean = gson.fromJson(string, DetailFirstBean.class);
                if (detailFirstBean.getCode() == 0) {
                    firstFather.clear();
                    treeList2.clear();
                    picsList.clear();
                    final boolean hasInput = detailFirstBean.getData().isHasInput();
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("是否存在图片", hasInput + "::");
                            //刷新图片
                            setAda();

//                            }else {
//                            }

                          /*  if (hasInput){
                                llWithoutPicture.setVisibility(View.GONE);
                                llWithPicture.setVisibility(View.VISIBLE);
                                if (null!=detailFirstBean.getData().getInfo().getExplain()){
                                    desc_et.setText(detailFirstBean.getData().getInfo().getExplain());
                                }

                                //刷新适配器
                                Log.d("返回图片的size",detailFirstBean.getData().getInfo().getPics().size()+"::");
                                for (int i=0;i<detailFirstBean.getData().getInfo().getPics().size();i++){
                                    PicBean picBean=new
                                            PicBean(detailFirstBean.getData().getInfo().getPics().get(i).getPi_id(),
                                            detailFirstBean.getData().getInfo().getPics().get(i).getPi_url()
                                            ,detailFirstBean.getData().getInfo().getPics().get(i).getPi_watermark_url());
                                    picsList.add(picBean);
                                    imgAllList.add(picBean);
                                }
                                setAda();
                            }else {
                                llWithoutPicture.setVisibility(View.VISIBLE);
                                llWithPicture.setVisibility(View.GONE);
                            }*/
                            treeList2.clear();

                            List<DetailFirstBean.DataBean.TreeBean> tree = detailFirstBean.getData().getTree();
                            for (int i = 0; i < tree.size(); i++) {
                                ItemBean itemBean = new ItemBean();
                                itemBean.setMci_id(tree.get(i).getMct_id());
                                itemBean.setMci_name(tree.get(i).getMct_name());
                                itemBean.setMci_mct_id(tree.get(i).getMct_p_id());
                                itemBean.setMct_is_batch(tree.get(i).getMct_is_batch());
                                treeList2.add(itemBean);
                            }
                            JSONObject jsonObject = JSONObject.parseObject(string);
                            JSONObject data = jsonObject.getJSONObject("data");
                            Log.e("分类完成", data.toString());
                            JSONArray tree2 = data.getJSONArray("tree");
                            array = tree2;
                            for (int i = 0; i < tree2.size(); i++) {
                                JSONObject treeObject = tree2.getJSONObject(i);
                                FatherBean fatherBean = new FatherBean(treeObject);
                                addList(treeObject, fatherBean);
                                firstFather.add(fatherBean);
                            }

                            //每个父级下数据
                            List<ItemBean> itemBeans = retrunData(0);
                            Log.d("所有子集", itemBeanList.size() + ":" + GsonUtils.toJson(itemBeans));
                            setItemAdapter(itemBeans);
                            //展示数据
                            dataCollectionKind2TabAdapter.setNewData(LoadData.getDataCollectionTabList(treeList2));
//                            if (dataCollectionListAdapter == null) {
                            dataCollectionListAdapter.setNewData(LoadData.getDataCollectionTabList(treeList2));
//                            } else {
//                                dataCollectionListAdapter.notifyDataSetChanged();
//                            }
                            LoadingDialog.disDialog();
                        }

                    });
                }
            }
        });
    }

    private int itemType = 0;

    //返回顶级根据全部部分采集条目的数据
    private List<ItemBean> retrunTypeData(int index) {
        try {
            if (null == firstFather) {
                MyToast.getInstands().toastShow(DataCollectionActivity.this, "数据为空");
            } else {
                itemsList2.clear();
                itemType = index;
                List<Item2Bean> items = firstFather.get(zongType).getItems();
                Log.d("传入下标", "::" + index);
                for (int g = 0; g < items.size(); g++) {
                    if (index == 0) {
                        //全部
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    } else if (index == 1) {
                        //部分采集
                        if (items.get(g).getP_count() < items.get(g).getMci_min_point() && items.get(g).getP_count() > 0) {
                            ItemBean itemBean = getItemBean(items, g);
                            itemsList2.add(itemBean);
                        }

                    } else if (index == 2) {
                        //完成
                        if (items.get(g).getP_count() > 0 && items.get(g).getP_count() >= items.get(g).getMci_min_point()) {
                            ItemBean itemBean = getItemBean(items, g);
                            itemsList2.add(itemBean);
                        }
                    } else if (index == 3) {
                        //未开始
                        if (items.get(g).getP_count() == 0) {
                            ItemBean itemBean = getItemBean(items, g);
                            itemsList2.add(itemBean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemsList2;
    }

    @NonNull
    private ItemBean getItemBean(List<Item2Bean> items, int g) {
        ItemBean itemBean = new ItemBean();
        itemBean.setMci_id(items.get(g).getMci_id());
        String path = "";
        if (items.get(g).getFatherBean().getPath().equals("")) {
            path = items.get(g).getFatherBean().getMct_name();
        } else {
            path = items.get(g).getFatherBean().getPath() + ">" + items.get(g).getFatherBean().getMct_name();
        }
        itemBean.setPath(path);
        itemBean.setMci_name(items.get(g).getMci_name());
        itemBean.setMci_mct_id(items.get(g).getMct_p_id());
        itemBean.setP_count(items.get(g).getP_count());
        itemBean.setMci_min_point(items.get(g).getMci_min_point());
        itemBean.setMci_no(items.get(g).getMci_no());
        return itemBean;
    }

    private int zongType = 0;

    //返回顶级下子条目的数据
    private List<ItemBean> retrunData(int index) {
        try {
            itemsList2.clear();
            zongType = index;

            List<Item2Bean> items = firstFather.get(index).getItems();
            Log.d("所有子集遍历", "::" + items.size());
            for (int g = 0; g < items.size(); g++) {
                if (itemType == 0) {
                    //全部
                    ItemBean itemBean = getItemBean(items, g);
                    itemsList2.add(itemBean);
                } else if (itemType == 1) {
                    //部分采集
                    if (items.get(g).getP_count() < items.get(g).getMci_min_point() && items.get(g).getP_count() > 0) {
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }

                } else if (itemType == 2) {
                    //完成
                    if (items.get(g).getP_count() > 0 && items.get(g).getP_count() > items.get(g).getMci_min_point()) {
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                } else if (itemType == 3) {
                    //未开始
                    if (items.get(g).getP_count() == 0) {
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }
            }


           /* for (int g=0;g<items.size();g++){
                Log.d("所有子集遍历3","::"+items.get(g).getMci_name());
                ItemBean itemBean = getItemBean(items, g);
                itemsList2.add(itemBean);
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsList2;
    }

    //强制提交原因
    private void addReson() {
        final Dialog dialog = new Dialog(DataCollectionActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(DataCollectionActivity.this).inflate(R.layout.dialog_reson, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(AutoSizeUtils.dp2px(DataCollectionActivity.this, 24),
                0, AutoSizeUtils.dp2px(DataCollectionActivity.this, 24), 0);

        // window.getDecorView().setPadding(50, 0, 50, 0);
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
        final EditText dialog_stop_inspection_et_extra = view.findViewById(R.id.dialog_stop_inspection_et_extra);
        RoundTextView ok = view.findViewById(R.id.dialog_stop_inspection_rtv_ok);
        RoundTextView cancel = view.findViewById(R.id.dialog_stop_inspection_rtv_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_stop_inspection_et_extra.getText().toString().equals("")) {
                    ToastUtil.showShort(DataCollectionActivity.this, "请输入原因");
                } else {
                    Map<String, Object> pram = new HashMap<>();
                    pram.put("missionID", taskId);
                    pram.put("reason", dialog_stop_inspection_et_extra.getText().toString());
                    OkHttp3Util.doPost(URL.BASE_URL_4 + "app/save_unfinished_reason", pram, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            Log.d("强制原因", string);
                        }
                    });

                    dialog.dismiss();
                    Intent intent = new Intent(DataCollectionActivity.this, ConfirmSubmitInspectionActivity.class);
                    if (jingdu.isEmpty()) {
                        intent.putExtra("jingdu", "39.92");
                        intent.putExtra("weidu", "116.60");
                    } else {
                        intent.putExtra("jingdu", jingdu.substring(0, 5));
                        intent.putExtra("weidu", weidu.substring(0, 6));
                    }
                    intent.putExtra("taskid", taskId);
                    intent.putExtra("project", projectid);
                    startActivity(intent);

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("离开当前页面", "进入");
        if (imgAllList.size() == 0) {
            NeiRong = "";
            BuildId = "";
            BuildName = "";
            desc_et.setText("");
            lou_hao.setText("请选择");
        } else {
            NeiRong = desc_et.getText().toString();
        }

    }


    public static List<PicBean> getImgList() {
        return imgAllList;
    }

    //顶级下的数据
    private void setItemAdapter(List<ItemBean> itemBeans) {
        tabAdapter = new TabCheckAdaper(DataCollectionActivity.this, itemBeans);
        rvKind2List.setLayoutManager(new LinearLayoutManager(this));
        rvKind2List.setNestedScrollingEnabled(false);
        rvKind2List.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener(new TabCheckAdaper.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, TabCheckAdaper.Hm2Holder holder, List<ItemBean> list) {

                Intent intent = new Intent(DataCollectionActivity.this, InspectionItemActivity.class);
                Log.d("当前数据", list.get(position).getMci_name() + "::" + list.get(position).getMci_id());
                intent.putExtra("name", list.get(position).getMci_name());
                intent.putExtra("taskid", taskId);
                intent.putExtra("projectid", projectid);
                intent.putExtra("ciid", list.get(position).getMci_id());
                startActivity(intent);
            }
        });
    }

    List<Item2Bean> itemBeanList = new ArrayList<>();
    List<Item2Bean> mItems = new ArrayList<>();
    public static List<FatherBean> firstFather = new ArrayList<>();


    private void addList(JSONObject treeObject,
                         FatherBean fatherBean) {
        JSONArray son = treeObject.getJSONArray("son");
        if (son != null && !son.isEmpty()) {
            setItem(son, fatherBean, false);
        }
        JSONArray items = treeObject.getJSONArray("items");
        if (items != null && !items.isEmpty()) {
            setItem(items, fatherBean, true);
        }

    }

    private void setItem(JSONArray jsonArray, FatherBean fatherBean, boolean isItem) {
        FatherBean mFatherBean = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.e("分支" + i, jsonObject.toString());
            if (isItem) {
                Item2Bean itemBean = new Item2Bean(jsonObject, isItem);
                itemBean.setFatherBean(fatherBean);
                itemBeanList.add(itemBean);
                mItems.add(itemBean);
                if (i == jsonArray.size() - 1) {
                    addItems(fatherBean);
                }
            } else {
//                if (mFatherBean == null) {
                mFatherBean = new FatherBean(jsonObject);
                mFatherBean.setFatherBean(fatherBean);
                if (fatherBean != null) {
                    mFatherBean.setPath(fatherBean.getPath() +
                            (TextUtils.isEmpty(fatherBean.getPath()) ? "" : ">")
                            + (fatherBean.getMct_name() != null ? fatherBean.getMct_name() : ""));
                    mFatherBean.setPath_id(fatherBean.getPath_id() +
                            (TextUtils.isEmpty(fatherBean.getPath_id()) ? "" : "_")
                            + (fatherBean.getMct_id() != null ? fatherBean.getMct_id() : ""));
                }
//                }
//                if (i == 0) {
                Item2Bean itemBean = new Item2Bean(jsonObject, isItem);
                itemBean.setFatherBean(fatherBean);
                itemBeanList.add(itemBean);

//                }
                addList(jsonObject, mFatherBean);
            }
        }
    }

    private void addItems(FatherBean fatherBean) {
        if (null != fatherBean) {
            List<Item2Bean> items = fatherBean.getItems();
            items.addAll(mItems);
            fatherBean.setItems(items);
            if (fatherBean.getFatherBean() != null) {
                addItems(fatherBean.getFatherBean());
            } else {
                mItems.clear();
            }
        }
    }

//    private void addPic(String url, String url2) {
//
//    }

    public void setAda() {
//        LoadingDialog.showLoading(this);
        Log.d("当前图片长度", "::" + imgAllList.size());


//        imgAllList.add(picBean);

        //存库
//            imageDao.addImage(picBean);


        if (imgAllList.size() > 0) {
            llWithPicture.setVisibility(View.VISIBLE);
            llWithoutPicture.setVisibility(View.GONE);
        } else {
            llWithPicture.setVisibility(View.GONE);
            llWithoutPicture.setVisibility(View.VISIBLE);
        }

        if (adapter == null) {
            header = View.inflate(DataCollectionActivity.this, R.layout.firstimg, null);

            adapter = new TuiJianAdapter(DataCollectionActivity.this, imgAllList);
            adapter.setHeaderView(header);
            rvImages.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  LoadingDialog.showLoading(DataCollectionActivity.this);
                takePhoto();
            }
        });


        //删除操作
        adapter.setOnItemClickListener(new TuiJianAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, TuiJianHolder holder, List<PicBean> list) {
                //删除当前图片
                imgAllList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();

                if (imgAllList.size() == 0) {
                    llWithPicture.setVisibility(View.GONE);
                    llWithoutPicture.setVisibility(View.VISIBLE);
                }


                //清库
//                if (imageDaoBeans.size() > 0) {
//                    imageDao.deleteProgress();
//                    for (int i = 0; i < imgAllList.size(); i++) {
//                        imageDao.addImage(imgAllList.get(i));
//                    }
////                    imageDao.addImage();
//                }

            }
        });

//        try {
//            if (imgAllList.size() == 0) {
//                llWithoutPicture.setVisibility(View.VISIBLE);
//                llWithPicture.setVisibility(View.GONE);
//                NeiRong = "";
//                BuildId = "";
//                BuildName = "";
//            } else {
//                llWithoutPicture.setVisibility(View.GONE);
//                llWithPicture.setVisibility(View.VISIBLE);
//
//                if (!NeiRong.equals("")) {
//                    desc_et.setText(NeiRong);
//                }
//                if (!BuildName.equals("")) {
//                    lou_hao.setText(BuildName);
//                }
//            }
//            header = View.inflate(DataCollectionActivity.this, R.layout.firstimg, null);
//
//            adapter = new TuiJianAdapter(DataCollectionActivity.this, imgAllList);
//            adapter.setHeaderView(header);
//            rvImages.setAdapter(adapter);
//
//
//            header.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //  LoadingDialog.showLoading(DataCollectionActivity.this);
//                    takePhoto();
//                }
//            });
//            LoadingDialog.disDialog();
//
//            //删除操作
//            adapter.setOnItemClickListener(new TuiJianAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(View view, int position, TuiJianHolder holder, List<PicBean> list) {
//                    Map<String, Object> pram = new HashMap<>();
//                    pram.put("missionID", taskId);
//                    pram.put("picID", list.get(position).getPi_id());
//                    Log.d("删除传参", GsonUtils.toJson(pram));
//                    OkHttp3Util.doPost(URL.BASE_URL_4 + "app/delete_pic_from_mission", pram, new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            String string = response.body().string();
//                            Log.d("删除图片", string);
//                        }
//                    });
//                    //删除当前图片
//                    imgAllList.remove(list.get(position));
//                    adapter.notifyItemRemoved(position);
//                    adapter.notifyDataSetChanged();
//                    Log.d("删除图片", "::" + list.size());
//                    if (imgAllList.size() == 0) {
//                        ThreadUtils.runOnMainThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                llWithoutPicture.setVisibility(View.VISIBLE);
//                                llWithPicture.setVisibility(View.GONE);
//                                NeiRong = "";
//                                BuildId = "";
//                                BuildName = "";
//                                desc_et.setText("");
//                                lou_hao.setText("请选择");
//                            }
//                        });
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // }


    }

    private void initRVKind2() {
        //列表
        dataCollectionListAdapter = new DataCollectionListAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(dataCollectionListAdapter);
        //tab
        dataCollectionKind2TabAdapter = new DataCollectionKind2TabAdapter();
        LinearLayoutManager tabLinearLayout = new LinearLayoutManager(this);
        tabLinearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTab.setLayoutManager(tabLinearLayout);
        rvTab.setAdapter(dataCollectionKind2TabAdapter);
        //filter
        dataCollectionKind2FilterAdapter = new DataCollectionKind2FilterAdapter();
        LinearLayoutManager filterLinearLayout = new LinearLayoutManager(this);
        filterLinearLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvFilter.setLayoutManager(filterLinearLayout);
        rvFilter.setAdapter(dataCollectionKind2FilterAdapter);
        dataCollectionFilterList = LoadData.getDataCollectionFilterList();
        dataCollectionKind2FilterAdapter.setNewData(dataCollectionFilterList);

        //列表

    }


    @Override
    public void initLoad() {
        super.initLoad();
        // zgImagesAdapter.addData("");
        //  dataCollectionListAdapter.addData(LoadData.getDataCollectionTabList(itemsList));
        //    dataCollectionKind2TabAdapter.setNewData(LoadData.getDataCollectionTabList());
        // dataCollectionKind2Adapter.setNewData(LoadData.getStringList());
    }

    JSONArray array = new JSONArray();

    @Override
    public void initListener() {
        super.initListener();
        dataCollectionListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DataCollectionTabBean dataCollectionTabBean = (DataCollectionTabBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, dataCollectionTabBean.getName());
                JSONObject jsonObject = array.getJSONObject(position);
                CatalogueDataUtils.getInstance().setJsonObject(jsonObject);
                CatalogueDataUtils.getInstance().setJsonFather(firstFather.get(position).getItems());
                bundle.putString("taskid", taskId);
                bundle.putString("projectid", projectid);
                bundle.putString("index", "" + position + "");
                bundle.putString("jingdu", "" + jingdu + "");
                bundle.putString("weidu", "" + weidu + "");
                bundle.putString("taskname", "" + taskname + "");
                Log.d("接受参数2", projectid + "::" + taskId + "::" + taskname);
                IntentUtils.startActivity(DataCollectionActivity.this, CatalogueActivity.class, bundle);

            }
        });
        dataCollectionListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                // ToastUtil.showShort(DataCollectionActivity.this,"点击");
                List<Item2Bean> items = firstFather.get(position).getItems();

                String itemid = "";
                for (int g = 0; g < items.size(); g++) {
                    if (itemid.equals("")) {
                        itemid = items.get(g).getMci_id();
                    } else {
                        itemid = itemid + "," + items.get(g).getMci_id();
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("projectid", projectid + "");
                bundle.putString("taskid", taskId + "");
                bundle.putString("itemID", itemid + "");
                IntentUtils.startActivity(DataCollectionActivity.this, DataCollectionResultActivity.class, bundle);
            }
        });
        dataCollectionKind2TabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //每个父级下数据
                List<ItemBean> itemBeans = retrunData(position);
                Log.d("所有子集2", itemBeanList.size() + ":" + GsonUtils.toJson(itemBeans));
                setItemAdapter(itemBeans);

                for (int i = 0; i < treeList2.size(); i++) {
                    if (i == position) {
                        DataCollectionTabBean dataCollectionTab = (DataCollectionTabBean) adapter.getItem(position);
                        dataCollectionTab.setCheck(true);
                    } else {
                        DataCollectionTabBean dataCollectionTabOld = (DataCollectionTabBean) adapter.getItem(i);
                        dataCollectionTabOld.setCheck(false);
                    }
                }
                dataCollectionKind2TabAdapter.notifyDataSetChanged();

            }
        });
        dataCollectionKind2FilterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //每个父级下数据
                List<ItemBean> itemBeans = retrunTypeData(position);
                Log.d("所有子集2", itemBeanList.size() + ":" + GsonUtils.toJson(itemBeans));
                setItemAdapter(itemBeans);
                for (int i = 0; i < dataCollectionFilterList.size(); i++) {
                    if (i == position) {
                        DataCollectionFilterBean dataCollectionFilter = (DataCollectionFilterBean) adapter.getItem(position);
                        dataCollectionFilter.setCheck(true);

                    } else {
                        DataCollectionFilterBean dataCollectionFilterOld =
                                (DataCollectionFilterBean) adapter.getItem(i);
                        dataCollectionFilterOld.setCheck(false);
                    }
                }

                dataCollectionKind2FilterAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    @OnClick({
            R.id.activity_data_collection_rtv_upload,
            R.id.activity_data_collection_type,
            R.id.activity_data_collection_rtv_multi,
            R.id.activity_data_collection_rtv_end,
            R.id.search,
            R.id.search2
    })
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_data_collection_rtv_multi:
                Bundle bundle = new Bundle();
                bundle.putString("projectid", projectid + "");
                bundle.putString("taskid", taskId + "");
                IntentUtils.startActivity(DataCollectionActivity.this, DataCollectionMultiActivity.class, bundle);
                break;
            case R.id.activity_data_collection_rtv_upload:
                takePhoto();
                break;
            case R.id.activity_data_collection_type:
                toggle();
                break;
            case R.id.activity_data_collection_rtv_end:
                //结束判断
                isEndTask();
                //showEndTaskDialog();
                break;
            case R.id.search:
                Intent intent = new Intent(DataCollectionActivity.this, SeachItemActivity.class);
                intent.putExtra("projectid", projectid);
                intent.putExtra("taskid", taskId);
                startActivity(intent);
                break;
            case R.id.search2:
                Intent intent2 = new Intent(DataCollectionActivity.this, SeachItemActivity.class);
                intent2.putExtra("projectid", projectid);
                intent2.putExtra("taskid", taskId);
                startActivity(intent2);
                break;
        }
    }

    //结束判断
    private void isEndTask() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/check_point", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("结束任务", string);
                Gson gson = new Gson();
                final EndTaskBean endTaskBean = gson.fromJson(string, EndTaskBean.class);
                if (endTaskBean.getCode() == 0) {
                    ThreadUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            EndTaskBean.DataBean data = endTaskBean.getData();
                            if (null != data) {
                                //判断是否有满足补全的数据
                                if (data.getNot_much_item_count() == 0) {
                                    if (data.getReal_item_count() == 0) {
                                        MyToast.getInstands().toastShow(DataCollectionActivity.this, "请至少填写一条检测项！！！");
                                    } else {
                                        //输入强制提交原因
                                        if (data.getReal_item_count() < data.getNeed_item_count()) {
                                            //强制提交原因
                                            addReson();
                                        } else {
                                            Intent intent = new Intent(DataCollectionActivity.this, ConfirmSubmitInspectionActivity.class);
                                            if (jingdu.isEmpty()) {
                                                intent.putExtra("jingdu", jingdu);
                                                intent.putExtra("weidu", weidu);
                                            } else {
                                                intent.putExtra("jingdu", jingdu.substring(0, 5));
                                                intent.putExtra("weidu", weidu.substring(0, 6));
                                            }
                                            intent.putExtra("taskid", taskId);
                                            intent.putExtra("project", projectid);
                                            startActivity(intent);
                                        }
                                    }
                                } else {
                                    //结束弹框
                                    endDialog(data);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //结束弹框
    private void endDialog(final EndTaskBean.DataBean data) {
        final Dialog dialog = new Dialog(DataCollectionActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(DataCollectionActivity.this).inflate(R.layout.dialog_end_task, null);
//        ((ViewGroup) view.getParent()).removeView(view);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(10, 0, 10, 0);
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
        dialog_end_task_rll_sign = view.findViewById(R.id.dialog_end_task_rll_sign);
        dialog_end_task_rll_ok = view.findViewById(R.id.dialog_end_task_rll_ok);
        rv_list = view.findViewById(R.id.dialog_end_task_rv_list);
        number_tv = view.findViewById(R.id.number_tv);
        queshao_tv = view.findViewById(R.id.queshao_tv);
        final LinearLayoutManager gridLayoutManager = new LinearLayoutManager(DataCollectionActivity.this);

        rv_list.setLayoutManager(gridLayoutManager);
        rv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 10;
                outRect.right = 10;
            }
        });
        rv_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        if (data.isEnable_batch()) {
            dialog_end_task_rll_sign.setVisibility(View.VISIBLE);
        } else {
            dialog_end_task_rll_sign.setVisibility(View.GONE);
        }
        number_tv.setText("共" + data.getNeed_item_count() + "个检测项，您已检测" + data.getReal_item_count() + "项");
        queshao_tv.setText("其中有以下" + data.getNot_much_item_count() + "项检测项缺少点位数据，请检测完成后再提交!");
        List<EndTaskBean.DataBean.NotMuchItemArrBean> not_much_item_arr = data.getNot_much_item_arr();
        if (null != not_much_item_arr && !not_much_item_arr.isEmpty()) {

            EndTaskAdaper endTaskAdaper = new EndTaskAdaper(DataCollectionActivity.this, not_much_item_arr);
            rv_list.setAdapter(endTaskAdaper);
            endTaskAdaper.setOnItemClickListener(new EndTaskAdaper.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, EndTaskAdaper.Hm2Holder holder, List<EndTaskBean.DataBean.NotMuchItemArrBean> list) {
                    dialog.dismiss();
                    Intent intent = new Intent(DataCollectionActivity.this, InspectionItemActivity.class);
                    Log.d("当前数据", list.get(position).getMci_name() + "::" + list.get(position).getMci_id());
                    intent.putExtra("name", list.get(position).getMci_name());
                    intent.putExtra("projectid", projectid);
                    intent.putExtra("taskid", taskId);
                    intent.putExtra("ciid", list.get(position).getMci_id());
                    startActivity(intent);
                }
            });
        }

        dialog_end_task_rll_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定
                dialog.dismiss();
                //调补全点位接口
                Map<String, Object> pram = new HashMap<>();
                pram.put("missionID", taskId);
                pram.put("userID", loginBean.getUser_id());
                OkHttp3Util.doPost(URL.BASE_URL_4 + "app/set_point_pass", pram, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("批量合格", string);
                        Gson gson = new Gson();
                        PiHeGeBean piHeGeBean = gson.fromJson(string, PiHeGeBean.class);
                        if (piHeGeBean.getCode() == 0) {
                            final String data1 = piHeGeBean.getData();
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (data1.equals("0")) {
                                        MyToast.getInstands().toastShow(DataCollectionActivity.this,
                                                "没有可以自动标记合格的点位");

                                    } else {
                                        MyToast.getInstands().toastShow(DataCollectionActivity.this,
                                                "标记成功");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        dialog_end_task_rll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void toggle() {
        if (isCatalogue) {
            //显示列表
            ivToggle.setText("全部");
            isCatalogue = false;
            rvList.setVisibility(View.VISIBLE);
            llKind2.setVisibility(View.GONE);


            rtvMulti.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            search2.setVisibility(View.VISIBLE);

        } else {
            //显示目录
            ivToggle.setText("目录");
            isCatalogue = true;
            rvList.setVisibility(View.GONE);
            llKind2.setVisibility(View.VISIBLE);

            rtvMulti.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            search2.setVisibility(View.GONE);
        }
    }


    private EndTaskDialog endTaskDialog;

    private void showEndTaskDialog() {
        closeEndTaskDialog();
        endTaskDialog = new EndTaskDialog(this, new EndTaskDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                closeEndTaskDialog();
            }
        });
        endTaskDialog.show();
    }

    private void closeEndTaskDialog() {
        if (endTaskDialog != null) {
            endTaskDialog.cancel();
        }
        endTaskDialog = null;
    }


    int index = 0;
    String url;
    String url2;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 1) {
                    url = (String) msg.obj;
                    Log.d("返回", ":" + url);
                    handler.sendEmptyMessage(3);
                } else if (msg.what == 2) {
                    url2 = (String) msg.obj;
                    Log.d("返回", ":" + url2);
                    handler.sendEmptyMessage(3);
                } else if (msg.what == 3) {

                    index++;
                    if (index == 2) {
                        index = 0;
                        Map<String, Object> pram = new HashMap<>();
                        pram.put("missionID", taskId);
                        pram.put("userID", loginBean.getUser_id());
                        pram.put("la", jingdu);
                        pram.put("lo", weidu);
                        pram.put("height", height);
                        pram.put("url", url);
                        pram.put("wurl", url2);
                        pram.put("address", didain);
                        //点位app/upload_point_pic   任务app/upload_mission_pic_info
                        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/upload_point_pic", pram, new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String string = response.body().string();
                                Log.d("监测数据22", string);
                                Gson gson = new Gson();
                                final ImgBean imgBean = gson.fromJson(string, ImgBean.class);
                                ThreadUtils.runOnMainThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        picBean = new PicBean();
                                        picBean.setPi_id(imgBean.getData().getPi_id());

                                        picBean.setPi_url(imgBean.getData().getPi_url());
                                        picBean.setPi_watermark_url(imgBean.getData().getPi_watermark_url());
                                        picBean.setPi_tastId(taskId);

                                        imgAllList.add(picBean);
                                        setAda();

                                    }
                                });

                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 调用相机
     */

    private int istake = 0;

    private void takePhoto() {
        istake = 1;
        initPermission();
        boolean openLocService = LocationServiceUtils.isOpenLocService(DataCollectionActivity.this);
        // Log.d("qqqqq9",openLocService+"::");
        if (!openLocService) {
            LoadingDialog.disDialog();
            Toast.makeText(DataCollectionActivity.this, "请先打开GPS~", Toast.LENGTH_SHORT).show();
            // ToastUtil.showShort(DataCollectionActivity.this,"请先打开GPS~");
        } else {
            boolean start = locationService.isStart();
            if (!start) {
                locationService.start();
            }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            istake = 0;
            if (requestCode == REQUEST_CODE) {
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

    @Override
    protected void onResume() {
        try {
            super.onResume();
            LoadingDialog.showLoading(this);
            firstFather.clear();
            treeList2.clear();
            getData();
            initRVKind2();
            initListener();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> imgList = new ArrayList<>();
    private PicBean picBean;

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

            if (!TextUtils.isEmpty(dingwei)) {
                yinshebei.setText(dingwei);
            } else {
                yinshebei.setText("没有获取到位置信息");
            }

            Log.d("dingweichakan", didain + ":::");
            if (!TextUtils.isEmpty(didain)) {
                jingweidu.setText(didain);
            } else {
                jingweidu.setText("没有获取到位置信息");
            }
            xiangmu.setText(taskname + " ");

            yintime.setText(loginBean.getUser_name());
            huoqu.setDrawingCacheEnabled(true);
            huoqu.buildDrawingCache();
            String cc = "qc-" + System.currentTimeMillis() + ".jpg";
            File newfile = new File(
                    uriImg + "/" + cc);
            final File file2 = new File(newfile.getPath());

            /*
             * 因为抛异常全部注释
             * */
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

                yuanfos = new FileOutputStream(file);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, yuanfos);

                Bitmap bitmap2 = UiUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
                //俩个图片合成一个
                bitmap1 = UiUtils.mergeBitmap_TB(bitmap2, drawingCache, false);

                fos = new FileOutputStream(file2);

                if (bitmap1 != null) {
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 70, fos);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ThreadUtils.runOnSubThread(new Runnable() {


                @Override
                public void run() {
                    try {
                        Log.d("图片路径", file.getPath() + "::" + file2.getPath());


//                        imageDaoBean = new ImageDaoBean();
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

                                        //存入图片bean
//                                        imageDaoBean.setImage(fileBean.getData().getFile_url());

                                        Message obtain = Message.obtain();
                                        obtain.obj = fileBean.getData().getFile_url();
                                        obtain.what = 1;
                                        handler.sendMessage(obtain);
                                    } else {
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.disDialog();
                                                MyToast.getInstands().toastShow(DataCollectionActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(DataCollectionActivity.this, "上传图片失败，请重新上传");
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

                                        //存入图片bean
//                                        imageDaoBean.setShiYinIMage(fileBean.getData().getFile_url());

                                        Message obtain = Message.obtain();
                                        obtain.obj = fileBean.getData().getFile_url();
                                        obtain.what = 2;
                                        handler.sendMessage(obtain);
                                    } else {
                                        ThreadUtils.runOnMainThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.disDialog();
                                                MyToast.getInstands().toastShow(DataCollectionActivity.this, "上传图片失败，请重新上传");
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    ThreadUtils.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadingDialog.disDialog();
                                            MyToast.getInstands().toastShow(DataCollectionActivity.this, "上传图片失败，请重新上传");
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

    //获取权限
    private void initPermission() {
        String[] permissions = CheckPermissionUtils.checkPermission(DataCollectionActivity.this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(DataCollectionActivity.this, permissions, 100);
        }
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private String dingwei = "";
    private String didain = "";
    private String jingdu = "";
    private String weidu = "";
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
                  Log.d("定位数据",altitude+"::"+latitude+"::"+longitude+":"+location.getAddrStr()+"::"+location.getLocationDescribe());
//                jingdu = "" + latitude + "";
//                weidu = "" + longitude + "";
//                height = "" + altitude + "";

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

                Log.d("获取地址", location.getAddrStr() + "::" + location.getLocationDescribe());
                if (null != location.getAddrStr() && null != location.getLocationDescribe() && null != String.valueOf(location.getAltitude())) {
                    didain = location.getAddrStr() + location.getLocationDescribe() + String.valueOf(location.getAltitude());
                }
            }
        }

    };
}

package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.CatalogueAdapter;
import com.agilefinger.labourservice.adapter.EndTaskAdaper;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.CatalogueEvenBus;
import com.agilefinger.labourservice.bean.EndTaskBean;
import com.agilefinger.labourservice.bean.Item2Bean;
import com.agilefinger.labourservice.bean.PiHeGeBean;
import com.agilefinger.labourservice.bean.SonAndItemsBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.CatalogueDataUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.utils.UiUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
 * 巡检任务 下级
 * */
public class CatalogueActivity extends BaseActivity {

    @BindView(R.id.activity_catalogue_tv_top)
    TextView tvTop;
    @BindView(R.id.activity_catalogue_rv_list)
    RecyclerView rvList;

    @BindView(R.id.activity_data_collection_rtv_end2)
    RoundTextView activity_data_collection_rtv_end;

    @BindView(R.id.layout_toolbar_iv_back)
    ImageView layout_toolbar_iv_back;
    @BindView(R.id.layout_toolbar_tv_title)
    TextView layout_toolbar_tv_title;
    @BindView(R.id.firsttv)
    TextView firsttv;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView layout_toolbar_iv_operate;


    private String crumb = "首页";
    private String title;
    private CatalogueAdapter catalogueAdapter;
    private ArrayList<SonAndItemsBean> next;
    private String taskid;
    private String projectid;
    private String index;
    private RoundLinearLayout dialog_end_task_rll_sign;
    private RoundLinearLayout dialog_end_task_rll_ok;
    private RecyclerView rv_list;
    private TextView number_tv;
    private TextView queshao_tv;
    private String jingdu;
    private String weidu;
    private String taskname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int initLayoutView() {
        return R.layout.activity_catalogue;
    }

    @Override
    public void initData() {
        super.initData();
        title = getIntent().getExtras().getString(Constants.EXTRA_DATA);//标题
        String t = getIntent().getExtras().getString(Constants.EXTRA_DATA_);
        projectid = getIntent().getExtras().getString("projectid");
        taskid = getIntent().getExtras().getString("taskid");
        index = getIntent().getExtras().getString("index");
        jingdu = getIntent().getExtras().getString("jingdu");
        weidu = getIntent().getExtras().getString("weidu");
        taskname = getIntent().getExtras().getString("taskname");
        back = getIntent().getExtras().getBoolean("back");
        crumb = TextUtils.isEmpty(t) ? "首页" : t;
        Log.d("接受参数", projectid + "::" + taskid + "::" + taskname + "::" + t + "::" + title);


    }

    @Override
    public void initView() {
        super.initView();
        // setToolbar(title, true, false, "");
        // setToolbarWhite(title);
        layout_toolbar_tv_title.setText(title);
        showCrumb(title);
        initRV();
        activity_data_collection_rtv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEndTask();
            }
        });

        firsttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataCollectionActivity.treeList2.clear();
                Bundle bundle = new Bundle();
                bundle.putString("projectid", projectid + "");
                bundle.putString("taskid", taskid + "");
                bundle.putString("taskname", taskname + "");
                Log.d("传递参数", projectid + "::" + taskid + "::" + taskname);
                IntentUtils.startActivity(CatalogueActivity.this, DataCollectionActivity.class, bundle);
                finish();
            }
        });
        layout_toolbar_iv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.uploadDialog(CatalogueActivity.this, taskid, projectid);
            }
        });
        layout_toolbar_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initRV() {
        catalogueAdapter = new CatalogueAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(catalogueAdapter);
    }


    private SonAndItemsBean catalogue;

    @Override
    public void initListener() {
        super.initListener();
        catalogueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                catalogue = (SonAndItemsBean) adapter.getItem(position);
                if (catalogue.isSon()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_DATA_, title);
                    bundle.putString(Constants.EXTRA_DATA, catalogue.getName());
                    bundle.putString("taskid", taskid);
                    bundle.putString("projectid", projectid);
                    bundle.putString("index", "" + position + "");
                    bundle.putString("jingdu", "" + jingdu + "");
                    bundle.putString("weidu", "" + weidu + "");
                    bundle.putString("taskname", "" + taskname + "");
                    bundle.putBoolean("back", back);
//                    if (!back) {
                    CatalogueDataUtils.getInstance().setJsonObject(catalogue.getJsonObject());
//                    }else {
//
//                    }
                    IntentUtils
                            .startActivity(CatalogueActivity.this, CatalogueActivity.class, bundle);
                } else {
                    Intent intent = new Intent(CatalogueActivity.this,
                            InspectionItemActivity.class);
                    intent.putExtra("projectid", projectid);
                    intent.putExtra("name", next.get(position).getName());
                    intent.putExtra("taskid", taskid);
                    intent.putExtra("ciid", next.get(position).getMci_id());
                    intent.putExtra("jingdu", "" + jingdu + "");
                    intent.putExtra("weidu", "" + weidu + "");

                    Log.d("传递参数2", projectid + "::" + taskid + "::" + taskname);
                    startActivity(intent);
                }
            }
        });
        catalogueAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_catalogue_rtv_multi) {
                    SonAndItemsBean o = (SonAndItemsBean) adapter.getData().get(position);
                    ArrayList<SonAndItemsBean> items = CatalogueDataUtils.getInstance()
                            .getItems(o);
                    //  ToastUtil.showShort(CatalogueActivity.this,"点击了批量"+items1.size());
                    //List<Item2Bean> items = firstFather.get(Integer.parseInt(index)).getItems();
                    String itemid = "";
                    for (int g = 0; g < items.size(); g++) {
                        if (itemid.equals("")) {
                            itemid = items.get(g).getMci_id();
                        } else {
                            itemid = itemid + "," + items.get(g).getMci_id();
                        }
                    }

                    Intent intent = new Intent(CatalogueActivity.this, DataCollectionResultActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtra("projectid", projectid + "");
                    intent.putExtra("taskid", taskid + "");
                    intent.putExtra("itemID", itemid + "");
                    intent.putExtra("items", (Serializable) o);

                    intent.putExtra("ciid", o.getMci_id());

                    startActivity(intent);

//                    JSONObject jsonObject = o.getJsonObject();
//                            intent.putExtra("object", jsonObject);


//
//                    bundle.putString("object", jsonObject.toString());


//                    IntentUtils.startActivity(CatalogueActivity.this,
//                            DataCollectionResultActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();

        next = CatalogueDataUtils.getInstance().next();
        catalogueAdapter.setNewData(next);


//        catalogueAdapter.setNewData(LoadData.getCatalogueList());
//        if (next == null)
//            if (!back) {
//                next = CatalogueDataUtils.getInstance().next();
////                back = false;
//            } else {
//                next = CatalogueDataUtils.getInstance().next2();
//            }
//        catalogueAdapter.setNewData(next);
//
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

    }

    private void showCrumb(String title) {
        if (TextUtils.isEmpty(title)) {
            this.title = crumb;
        } else {
            this.title = crumb + "/" + title;
        }
        String[] parts = this.title.split("/");
        SpannableString spannableString = new SpannableString(this.title);
        for (int i = 0; i < parts.length - 1; i++) {
            int start = this.title.indexOf(parts[i]);
            if (start > -1) {
                spannableString
                        .setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_0079e4)),
                                start, parts[i].length() + start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        tvTop.setText(spannableString);
    }

    private boolean back = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CatalogueEvenBus event) {

        ArrayList<SonAndItemsBean> next = this.next;
        for (SonAndItemsBean item : next) {
            if (item.isSon()) {
                if (event.getId().equals(item.getMct_id())) {
                    JSONObject jsonObject = item.getJsonObject();
                    if (null != jsonObject) {
                        JSONArray items = jsonObject.getJSONArray("items");
                        if (null != items) {
                            for (int i = 0; i < items.size(); i++) {
                                JSONObject itemsJSONObject = items.getJSONObject(i);
                                if(itemsJSONObject.getString("mci_id").equals(event.getChildId())){
                                    items.set(i,event.getChildJsonObject());
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            } else {
                if (event.getId().equals(item.getMci_id())) {

                    //处理父级点位要求
                   /* List<Item2Bean> listAllFather = CatalogueDataUtils.getInstance().getListAllFather();
                    for (int i=0;i<listAllFather.size();i++){
                        Log.d("添加的点位",listAllFather.get(i).getMci_id()+"::"+event.getId());
                        if (listAllFather.get(i).getMci_id().equals(event.getId())){
                            Log.d("他的父级id",
                                    listAllFather.get(i).getFatherBean().getPath_id()+"_"+listAllFather.get(i).getMci_id());
                        }
                    }*/

                    //处理item的数量变化
                    item.setP_count(event.getCoupon());
                    event.setChildId(item.getMci_id());
                    event.setId(item.getMci_mct_id());
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(item));
                    event.setChildJsonObject(jsonObject);
                    EventBus.getDefault().post(event);
                }
            }
            if (catalogueAdapter != null) {
                catalogueAdapter.setNewData(next);
            }
        }



//        if (event.getId().equals("Text")) {
//            if (catalogueAdapter != null) {
//                back = true;
//                catalogueAdapter.setNewData(next);
//            }
//        } else {
//            ArrayList<SonAndItemsBean> next = this.next;
//            for (SonAndItemsBean item : next) {
//                if (item.isSon()) {
//                    if (event.getId().equals(item.getMct_id())) {
//                        JSONObject jsonObject = item.getJsonObject();
//                        if (null != jsonObject) {
//                            JSONArray items = jsonObject.getJSONArray("items");
//                            if (null != items) {
//                                for (int i = 0; i < items.size(); i++) {
//                                    JSONObject itemsJSONObject = items.getJSONObject(i);
//                                    if (itemsJSONObject.getString("mci_id").equals(event.getChildId())) {
//                                        items.set(i, event.getChildJsonObject());
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        break;
//                    }
//                } else {
//                    if (event.getId().equals(item.getMci_id())) {
//
//                        //处理父级点位要求
////                   List<Item2Bean> listAllFather = CatalogueDataUtils.getInstance().getListAllFather();
////                    for (int i=0;i<listAllFather.size();i++){
////                        Log.d("添加的点位",listAllFather.get(i).getMci_id()+"::"+event.getId());
////                        if (listAllFather.get(i).getMci_id().equals(event.getId())){
////                            Log.d("他的父级id",
////                                    listAllFather.get(i).getFatherBean().getPath_id()+"_"+listAllFather.get(i).getMci_id());
////                        }
////                    }
//
//                        //处理item的数量变化
//                        item.setP_count(event.getCoupon());
//                        event.setChildId(item.getMci_id());
//                        event.setId(item.getMci_mct_id());
//                        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(item));
//                        event.setChildJsonObject(jsonObject);
//                        EventBus.getDefault().post(event);
//                    }
//                }
//                if (catalogueAdapter != null) {
//                    catalogueAdapter.setNewData(next);
//                }
//            }
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    //结束判断
    private void isEndTask() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("missionID", taskid);

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
                                        MyToast.getInstands().toastShow(CatalogueActivity.this, "请至少填写一条检测项！！！");
                                    } else {
                                        //输入强制提交原因
                                        if (data.getReal_item_count() < data.getNeed_item_count()) {
                                            //强制提交原因
                                            addReson();
                                        } else {
                                            Intent intent = new Intent(CatalogueActivity.this, ConfirmSubmitInspectionActivity.class);
                                            if (jingdu.isEmpty()) {
                                                intent.putExtra("jingdu", "39.92");
                                                intent.putExtra("weidu", "116.60");
                                            } else {
                                                intent.putExtra("jingdu", jingdu.substring(0, 5));
                                                intent.putExtra("weidu", weidu.substring(0, 6));
                                            }
                                            intent.putExtra("taskid", taskid);
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
        final Dialog dialog = new Dialog(CatalogueActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(CatalogueActivity.this).inflate(R.layout.dialog_end_task, null);
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
        final LinearLayoutManager gridLayoutManager = new LinearLayoutManager(CatalogueActivity.this);

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

            EndTaskAdaper endTaskAdaper = new EndTaskAdaper(CatalogueActivity.this, not_much_item_arr);
            rv_list.setAdapter(endTaskAdaper);
            endTaskAdaper.setOnItemClickListener(new EndTaskAdaper.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, EndTaskAdaper.Hm2Holder holder, List<EndTaskBean.DataBean.NotMuchItemArrBean> list) {
                    dialog.dismiss();
                    Intent intent = new Intent(CatalogueActivity.this, InspectionItemActivity.class);
                    Log.d("当前数据", list.get(position).getMci_name() + "::" + list.get(position).getMci_id());
                    intent.putExtra("name", list.get(position).getMci_name());
                    intent.putExtra("projectid", projectid);
                    intent.putExtra("taskid", taskid);
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
                pram.put("missionID", taskid);
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
                                        MyToast.getInstands().toastShow(CatalogueActivity.this,
                                                "没有可以自动标记合格的点位");

                                    } else {
                                        MyToast.getInstands().toastShow(CatalogueActivity.this,
                                                "标记成功");


                                        EventBus.getDefault().post(new CatalogueEvenBus(null, 1));
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


    //强制提交原因
    private void addReson() {
        final Dialog dialog = new Dialog(CatalogueActivity.this, R.style.Theme_Light_Dialog);
        View view = LayoutInflater.from(CatalogueActivity.this).inflate(R.layout.dialog_reson, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(AutoSizeUtils.dp2px(CatalogueActivity.this, 24),
                0, AutoSizeUtils.dp2px(CatalogueActivity.this, 24), 0);

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
                    ToastUtil.showShort(CatalogueActivity.this, "请输入原因");
                } else {
                    Map<String, Object> pram = new HashMap<>();
                    pram.put("missionID", taskid);
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
                    Intent intent = new Intent(CatalogueActivity.this, ConfirmSubmitInspectionActivity.class);
                    if (jingdu.isEmpty()) {
                        intent.putExtra("jingdu", "39.92");
                        intent.putExtra("weidu", "116.60");
                    } else {
                        intent.putExtra("jingdu", jingdu.substring(0, 5));
                        intent.putExtra("weidu", weidu.substring(0, 6));
                    }
                    intent.putExtra("taskid", taskid);
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
}

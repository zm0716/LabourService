package com.agilefinger.labourservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.AnQuanAdapter;
import com.agilefinger.labourservice.adapter.ClassificationAdapter;
import com.agilefinger.labourservice.adapter.ItemAdapter;
import com.agilefinger.labourservice.adapter.TestitemAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.AddJcnRongBean;
import com.agilefinger.labourservice.bean.CatalogNodeBean;
import com.agilefinger.labourservice.bean.CheckItemBean;
import com.agilefinger.labourservice.db.CatalogueDao;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.agilefinger.labourservice.fragment.CreateInspectionTaskStepTwoFragment.isOk;

public class ChooseInspectionContentOneActivity extends BaseActivity {
    @BindView(R.id.activity_data_collection_rv_aq)
    RecyclerView activityDataCollectionRvAq;
    @BindView(R.id.activity_data_collection_rv_item)
    RecyclerView activityDataCollectionRvItem;
    @BindView(R.id.activity_queding)
    RoundTextView activityQueding;
    @BindView(R.id.activity_data_collection_ll_kind_liebiao)
    LinearLayout activityDataCollectionLlKindLiebiao;
    @BindView(R.id.activity_data_collection_ll_kind_mulu)
    LinearLayout activityDataCollectionLlKindMulu;
    @BindView(R.id.activity_checkAll)
    RoundTextView activityCheckAll;
    @BindView(R.id.checkAll)
    LinearLayout checkAll;

    @BindView(R.id.image_checkeAll)
    ImageView imageCheckeAll;
    @BindView(R.id.activity_choose_inspection_content_one_rv_tab)
    RecyclerView activityChooseInspectionContentOneRvTab;
    @BindView(R.id.recyclerView_fenlei)
    RecyclerView recyclerViewFenlei;

    private CatalogueDao catalogueDao = new CatalogueDao(LSApplication.context);
    private AnQuanAdapter anQuanAdapter;
    private AddJcnRongBean addJcnRongBean;
    private List<CatalogNodeBean> data;
    private ItemAdapter itemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private ImageView layout_toolbar_iv_back;
    private String flag;
    private CatalogNodeBean currentNode;
    private ArrayList<CatalogNodeBean> showNodeHistoryList;
    private TextView layout_toolbar_tv_operate;
    private boolean isCatalogue = true;
    private boolean checkedAll = false;
    private boolean isRoot;
    private ClassificationAdapter classificationAdapter;
    private ArrayList<CheckItemBean> shownCheckItemList;//列表模式显示的检查项列表
    private String templateID;
    private String companyID;

    @Override
    public int initLayoutView() {
        return R.layout.activity_choose_inspection_content_one;
    }

    @Override
    public void initView() {
        super.initView();
        LoadingDialog.showLoading(this);
        isOk = 0;
        layout_toolbar_iv_back = findViewById(R.id.layout_toolbar_iv_back);
        layout_toolbar_tv_operate = findViewById(R.id.layout_toolbar_tv_operate);
        setToolbar("选择检测内容", false, true, "目录");

        Intent intent = getIntent();
        companyID = intent.getStringExtra("companyID");
        templateID = intent.getStringExtra("templateID");
        //安全评价数据
        anquanData();
        //列表目录切换
        layout_toolbar_tv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchlayout();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseInspectionContentOneActivity.this);
                linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                recyclerViewFenlei.setLayoutManager(linearLayoutManager);
                data.get(0).setCheck(true);

                classificationAdapter = new ClassificationAdapter(R.layout.item_fenlei, data);
                recyclerViewFenlei.setAdapter(classificationAdapter);
                showCheckItemList(0);
                setCheckAllButtonStatus(false);
                /**
                 * 点击分类
                 */
                classificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (int i = 0; i < data.size(); i++) {
                            if (i == position) {
                                if (data.get(i).isCheck()) {
                                    data.get(i).setCheck(false);
                                } else {
                                    data.get(i).setCheck(true);
                                }
                            } else {
                                data.get(i).setCheck(false);
                            }
                        }
                        showCheckItemList(position);
                        setCheckAllButtonStatus(false);
                        classificationAdapter.notifyDataSetChanged();
                        // Toast.makeText(ChooseInspectionContentOneActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        /**
         * 点击全选
         */
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedAll = !checkedAll;
                setCheckAllButtonStatus(checkedAll);
                if (isCatalogue) {//显示根目录
                    checkAllNodeAndItem(checkedAll);
                    if (flag != "root") {
                        showCatalogList();
                        showItemList(currentNode);
                    } else {
                        showRootList();//正在显示列表
                    }
                } else { //正在目录
                    checkAllItemsInList(checkedAll);
                    TestitemAdapter testitemAdapter = new TestitemAdapter(R.layout.item_data_collection_multi, shownCheckItemList);
                    activityChooseInspectionContentOneRvTab.setAdapter(testitemAdapter);

                }

            }
        });

        //点击确定回传数据
        returnData();
    }

    /**
     * 点击确定回传数据
     */
    private void returnData() {
        activityQueding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder resultCatalogString = new StringBuilder("");
                StringBuilder resultItemString = new StringBuilder("");
                isOk = 1;
                addJcnRongBean.getCheckedResult(resultCatalogString, resultItemString);
                Log.i("处理结果" + resultCatalogString, "" + resultItemString);

                String a = "";
                SharedPreferences login = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = login.edit();
                edit.clear();
                edit.commit();
                edit.putString("resultCatalogString", String.valueOf(resultCatalogString));
                edit.putString("resultItemString", String.valueOf(resultItemString));
                edit.commit();
                finish();
            }
        });
    }

    /**
     * 选中或者取消列表中的所有检查项
     *
     * @param checkedAll
     */
    private void checkAllItemsInList(boolean checkedAll) {
        if (shownCheckItemList.size() > 0) {
            for (int i = 0; i < shownCheckItemList.size(); i++) {
                shownCheckItemList.get(i).setSelected(checkedAll);
            }
        }
    }

    /**
     * 设置全选按钮的状态
     *
     * @param status
     */
    private void setCheckAllButtonStatus(boolean status) {
        if (!status) {
            activityCheckAll.setBackgroundResource(R.color.gray_ededed);
            activityCheckAll.setTextColor(getResources().getColor(R.color.gray_999999));
            imageCheckeAll.setImageResource(R.drawable.ic_unchecked);
        } else {
            activityCheckAll.setBackgroundResource(R.color.blue_2093ff);
            activityCheckAll.setTextColor(getResources().getColor(R.color.white));
            imageCheckeAll.setImageResource(R.drawable.ic_checked);
        }
    }

    private void checkAllNodeAndItem(boolean status) {
        if (flag == "root") {
            for (CatalogNodeBean node : data) {
                node.setChecked(status, true, true);
            }
        } else {
            //选中所有分类
            if (currentNode.getSon() != null) {
                for (CatalogNodeBean node : currentNode.getSon()) {
                    node.setChecked(status, true, true);
                }
            }
            if (currentNode.getItems() != null) {
                //选中所有检测项
                for (CheckItemBean item : currentNode.getItems()) {
                    item.setSelected(status);
                }
            }
        }
    }

    /**
     * 列表目录切换
     */
    private void switchlayout() {
        if (isCatalogue) {
            layout_toolbar_tv_operate.setText("全部");
            isCatalogue = false;
            activityDataCollectionLlKindLiebiao.setVisibility(View.GONE);
            activityDataCollectionLlKindMulu.setVisibility(View.VISIBLE);
//            isCatalogue = false;
        } else {
            layout_toolbar_tv_operate.setText("目录");
            isCatalogue = true;
            activityDataCollectionLlKindLiebiao.setVisibility(View.VISIBLE);
            activityDataCollectionLlKindMulu.setVisibility(View.GONE);

        }
    }

    /**
     * 展示列表模式的检查项列表
     *
     * @param index
     */
    private void showCheckItemList(int index) {
        if (shownCheckItemList == null) {
            shownCheckItemList = new ArrayList<CheckItemBean>();
        }
        shownCheckItemList.clear();
        getAllCheckItem(index);
        //将shownCheckItemList绑定到Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseInspectionContentOneActivity.this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        activityChooseInspectionContentOneRvTab.setLayoutManager(linearLayoutManager);
        TestitemAdapter testitemAdapter = new TestitemAdapter(R.layout.item_data_collection_multi, shownCheckItemList);
        activityChooseInspectionContentOneRvTab.setAdapter(testitemAdapter);
    }

    /**
     * 将制定根结点的所有检查项（含子节点）放到shownCheckItemList中
     *
     * @param index
     */
    private void getAllCheckItem(int index) {
        CatalogNodeBean node = data.get(index);
        addCheckItem(node);
    }

    /**
     * 添加当前结点及所有子节点的检查项，递归
     *
     * @param node
     */
    private void addCheckItem(CatalogNodeBean node) {
        if (node.getItems() != null) {
            for (CheckItemBean item : node.getItems()) {
                shownCheckItemList.add(item);
            }
        }
        if (node.getSon() != null) {
            for (CatalogNodeBean subNode : node.getSon()) {
                addCheckItem(subNode);
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 安全评价
     */
    private void anquanData() {
        HttpManager.getInstance().jcNeiRong(companyID, templateID, new HttpEngine.OnResponseJsonCallback<HttpResponse.NeiRong>() {
            @Override
            public void onResponse(int result, String data) {
                if (result == 1) {
                    //提示错误信息
                } else {
                    Log.d("检测内容", companyID + "::" + templateID + ":" + data);
                    jiance(data);
                }
            }
        });
    }

    /**
     * 显示分类树的根列表
     */
    private void showRootList() {
        anQuanAdapter = new AnQuanAdapter(R.layout.item_jiance, data);
        activityDataCollectionRvAq.setAdapter(anQuanAdapter);
        /**
         * 点击检测分类
         */
        anQuanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //展示选中分类的子分类列表
                showCatalogList(position);
                //展示选中分类的检测项列表
                showItemList(currentNode);
                checkedAll = false;
                setCheckAllButtonStatus(checkedAll);
            }
        });
    }

    /**
     * 选择检测内容
     *
     * @param dataString
     */
    private void jiance(String dataString) {

        try {
            Gson gson = new Gson();
            addJcnRongBean = gson.fromJson(dataString, AddJcnRongBean.class);
            addJcnRongBean.initAddJcnRongBean();//初始化数据中的父节点指针
            data = addJcnRongBean.getData();
            showNodeHistoryList = new ArrayList<CatalogNodeBean>();
            /**
             * 设置展示根结点
             */
            flag = "root";
            isRoot = true;
            /**
             * 选择检测分类
             */
            linearLayoutManager = new LinearLayoutManager(ChooseInspectionContentOneActivity.this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            activityDataCollectionRvAq.setLayoutManager(linearLayoutManager);
            showRootList();
            /**
             * 检测项
             */
            linearLayoutManager1 = new LinearLayoutManager(ChooseInspectionContentOneActivity.this);
            activityDataCollectionRvItem.setLayoutManager(this.linearLayoutManager1);
            linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
            itemAdapter = new ItemAdapter(R.layout.item_list, null);
            activityDataCollectionRvItem.setAdapter(itemAdapter);
            LoadingDialog.disDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 展示选中分类的子分类列表（选中了position）
     *
     * @param position
     */
    private void showCatalogList(int position) {
        if (flag == "root") {
            currentNode = addJcnRongBean.getData().get(position);
            //展示分类列表
            showCatalogList();
            flag = "sub";
            isRoot = false;
        } else {
            isRoot = false;
            showNodeHistoryList.add(currentNode);
            //检测分类的检测项
            currentNode = currentNode.getSon().get(position);
            showCatalogList();
        }

    }

    /**
     * 展示分类列表
     */
    private void showCatalogList() {
        anQuanAdapter = new AnQuanAdapter(R.layout.item_jiance, currentNode.getSon());
        activityDataCollectionRvAq.setAdapter(anQuanAdapter);
        activityDataCollectionRvAq.setVisibility(View.VISIBLE);
        anQuanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //记录点击的结点（这个结点就是下次展示的父节点）
                showCatalogList(position);
                //展示该节点的子节点（son）
                showItemList(currentNode);
            }
        });
    }

    /**
     * 展示选中分类的检测项列表
     */
    private void showItemList(CatalogNodeBean currentNode) {
        if (currentNode != null) {
            itemAdapter = new ItemAdapter(R.layout.item_list, currentNode.getItems());
            activityDataCollectionRvItem.setAdapter(itemAdapter);
        } else {
            activityDataCollectionRvItem.setAdapter(null);
        }
    }


    @Override
    protected void toOperate() {
        super.toOperate();
    }

    /**
     * 点击返回上一级
     */
    @OnClick(R.id.layout_toolbar_iv_back)
    public void onViewClicked() {
        if (isCatalogue) {//显示根目录
            checkedAll = false;
            setCheckAllButtonStatus(checkedAll);
            if (showNodeHistoryList.size() > 0) {
                isRoot = false;
                //检测项
                currentNode = showNodeHistoryList.get(showNodeHistoryList.size() - 1);
                showNodeHistoryList.remove(showNodeHistoryList.size() - 1);
                showCatalogList();
                showItemList(currentNode);
            } else {
                if (!isRoot) {
                    //分类
                    isRoot = true;
                    flag = "root";
                    showNodeHistoryList.clear();
                    anQuanAdapter = new AnQuanAdapter(R.layout.item_jiance, data);
                    backAdapter();//展示分类列表（根结点的子节点）
                    showItemList(null);//展示检测项列表
                } else {
                    //退页
                    //  Toast.makeText(this, "退页", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else { //正在目录
            finish();

        }

    }

    //检测项
    private void backAdapter() {
        activityDataCollectionRvAq.setAdapter(anQuanAdapter);
        activityDataCollectionRvAq.setVisibility(View.VISIBLE);
        anQuanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //记录点击的结点（这个结点就是下次展示的父节点）
                showCatalogList(position);
                //展示该节点的子节点（son）
                showItemList(currentNode);
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

package com.agilefinger.labourservice.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.DataCollectionKind2FilterAdapter;
import com.agilefinger.labourservice.adapter.DataCollectionKind2TabAdapter;
import com.agilefinger.labourservice.adapter.TabCheck3Adaper;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.DataCollectionFilterBean;
import com.agilefinger.labourservice.bean.DataCollectionTabBean;
import com.agilefinger.labourservice.bean.Item2Bean;
import com.agilefinger.labourservice.bean.ItemBean;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.agilefinger.labourservice.utils.UiUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.agilefinger.labourservice.activity.DataCollectionActivity.firstFather;
import static com.agilefinger.labourservice.activity.DataCollectionActivity.treeList2;
import static com.agilefinger.labourservice.adapter.TabCheck3Adaper.checkList;
import static com.agilefinger.labourservice.adapter.TabCheck3Adaper.map;

public class DataCollectionMultiActivity extends BaseActivity {
    @BindView(R.id.activity_data_collection_multi_rv_tab)
    RecyclerView rvTab;
    @BindView(R.id.activity_data_collection_multi_rv_filter)
    RecyclerView rvFilter;
    @BindView(R.id.activity_data_collection_multi_rv_list)
    RecyclerView rvKind2List;
    @BindView(R.id.edittext)
    EditText edittext;

    @BindView(R.id.activity_data_collection_multi_rtv_next)
    RoundTextView next;
    private DataCollectionKind2TabAdapter dataCollectionKind2TabAdapter;
    private DataCollectionKind2FilterAdapter dataCollectionKind2FilterAdapter;

    private int curTabPosition = 0, curFilterPosition = 0;
    private TabCheck3Adaper tabAdapter;
    private String taskId;
    private String projectid;

    @Override
    public int initLayoutView() {
        return R.layout.activity_data_collection_multi;
    }
    @Override
    protected void toOperate() {
        UiUtils.uploadDialog(DataCollectionMultiActivity.this,taskId,projectid);
    }
    @Override
    public void initView() {
        super.initView();
        setToolbar("选择检测项", true, false, "");
        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskid");
        projectid = bundle.getString("projectid");
        checkList.clear();
        map.clear();
        initRV();
    }

    private void initRV() {
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

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,KeyEvent event)  {
                if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    Log.d("输入",edittext.getText().toString().trim());
                    boolean isActionUp = (event.getAction()==KeyEvent.ACTION_UP);
                    if(!isActionUp)
                    {
                        List<ItemBean> itemBeans = retrunSearchTypeData(edittext.getText().toString().trim());
                        Log.d("查到的数据",GsonUtils.toJson(itemBeans)+"");
                        if (!itemsList2.isEmpty()){
                            setItemAdapter(itemBeans);
                        }else {
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShort(DataCollectionMultiActivity.this,"没有此检测项");
                                }
                            });
                        }
                    }
                    return true;
                }
                return false;
            }
        });

    }

    //顶级下的数据
    private void setItemAdapter(List<ItemBean> itemBeans) {
        tabAdapter = new TabCheck3Adaper(DataCollectionMultiActivity.this,itemBeans,next,taskId,projectid);
        rvKind2List.setLayoutManager(new LinearLayoutManager(this));
        rvKind2List.setNestedScrollingEnabled(false);
        rvKind2List.setAdapter(tabAdapter);

    }
    @Override
    public void initLoad() {
        super.initLoad();
        //每个父级下数据
        List<ItemBean> itemBeans = retrunData(0);
        Log.d("所有子集",":"+GsonUtils.toJson(itemBeans));
        setItemAdapter(itemBeans);
        dataCollectionKind2TabAdapter.setNewData(LoadData.getDataCollectionTabList(treeList2));
        dataCollectionKind2FilterAdapter.setNewData(LoadData.getDataCollectionFilterList());
        //dataCollectionMultiAdapter.setNewData(LoadData.getDataCollectionList());
    }
    List<ItemBean> itemsList2=new ArrayList<>();
    private int zongType=0;
    private int ziType=0;
    private int itemType=0;
    //返回顶级下子条目的数据
    private List<ItemBean> retrunData(int index) {
        try {
            itemsList2.clear();
            zongType=index;
            List<Item2Bean> items = firstFather.get(index).getItems();
            Log.d("所有子集遍历2","::"+items.size());
            for (int g=0;g<items.size();g++){
                Log.d("所有子集遍历3","::"+items.get(g).getMci_name());
            /*    ItemBean itemBean = getItemBean(items, g);
                itemsList2.add(itemBean);*/

                if (itemType==0){
                    //全部
                    ItemBean itemBean = getItemBean(items, g);
                    itemsList2.add(itemBean);
                }else if (itemType==1){
                    //部分采集
                    if (items.get(g).getP_count()<items.get(g).getMci_min_point()&&items.get(g).getP_count()>0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (itemType==2){
                    //完成
                    if (items.get(g).getP_count()>0&&items.get(g).getP_count()>items.get(g).getMci_min_point()){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (itemType==3){
                    //未开始
                    if (items.get(g).getP_count()==0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return itemsList2;
    }

    @NonNull
    private ItemBean getItemBean(List<Item2Bean> items, int g) {
        ItemBean itemBean = new ItemBean();
        itemBean.setMci_id(items.get(g).getMci_id());
        String path="";
        if (items.get(g).getFatherBean().getPath().equals("")){
            path=items.get(g).getFatherBean().getMct_name();
        }else {
            path= items.get(g).getFatherBean().getPath()+">"+items.get(g).getFatherBean().getMct_name();
        }
        itemBean.setPath(path);
        itemBean.setMci_name(items.get(g).getMci_name());
        itemBean.setMci_mct_id(items.get(g).getMct_p_id());
        itemBean.setP_count(items.get(g).getP_count());
        itemBean.setMci_min_point(items.get(g).getMci_min_point());
        itemBean.setMci_no(items.get(g).getMci_no());
        return itemBean;
    }

    //返回顶级根据全部部分采集条目的数据
    private List<ItemBean> retrunSearchTypeData(String search) {
        itemsList2.clear();
        List<Item2Bean> items = firstFather.get(zongType).getItems();
        Log.d("传入下标","::");
        for (int g=0;g<items.size();g++){
            String mci_name = items.get(g).getMci_name();
            if (mci_name.contains(search)){
                if (ziType==0){
                    //全部
                    ItemBean itemBean = getItemBean(items, g);
                    itemsList2.add(itemBean);
                }else if (ziType==1){
                    //部分采集
                    if (items.get(g).getP_count()<items.get(g).getMci_min_point()&&items.get(g).getP_count()>0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (ziType==2){
                    //完成
                    if (items.get(g).getP_count()>0&&items.get(g).getP_count()>items.get(g).getMci_min_point()){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (ziType==3){
                    //未开始
                    if (items.get(g).getP_count()==0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }
            }

        }
        return itemsList2;
    }

    //返回顶级根据全部部分采集条目的数据
    private List<ItemBean> retrunTypeData(int index) {
        try {
            ziType=index;
            itemType=index;
            itemsList2.clear();
            List<Item2Bean> items = firstFather.get(zongType).getItems();
            Log.d("传入下标","::"+index);
            for (int g=0;g<items.size();g++){
                if (index==0){
                    //全部
                    ItemBean itemBean = getItemBean(items, g);
                    itemsList2.add(itemBean);
                }else if (index==1){
                    //部分采集
                    if (items.get(g).getP_count()<items.get(g).getMci_min_point()&&items.get(g).getP_count()>0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (index==2){
                    //完成
                    if (items.get(g).getP_count()>0&&items.get(g).getP_count()>items.get(g).getMci_min_point()){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }else if (index==3){
                    //未开始
                    if (items.get(g).getP_count()==0){
                        ItemBean itemBean = getItemBean(items, g);
                        itemsList2.add(itemBean);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return itemsList2;
    }
    @Override
    public void initListener() {
        super.initListener();
        dataCollectionKind2TabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DataCollectionTabBean dataCollectionTab = (DataCollectionTabBean) adapter.getItem(position);
                //每个父级下数据
                List<ItemBean> itemBeans = retrunData(position);
                Log.d("所有子集2",":"+GsonUtils.toJson(itemBeans));
                setItemAdapter(itemBeans);
                if (curTabPosition == position) {//刷新
                } else {
                    DataCollectionTabBean dataCollectionTabOld = (DataCollectionTabBean) adapter.getItem(curTabPosition);
                    dataCollectionTabOld.setCheck(false);
                }
                dataCollectionTab.setCheck(true);
                dataCollectionKind2TabAdapter.notifyItemChanged(curTabPosition);
                dataCollectionKind2TabAdapter.notifyItemChanged(position);
                curTabPosition = position;
            }
        });
        dataCollectionKind2FilterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DataCollectionFilterBean dataCollectionFilter = (DataCollectionFilterBean) adapter.getItem(position);
                //每个父级下数据
                List<ItemBean> itemBeans = retrunTypeData(position);
                Log.d("所有子集2",":"+GsonUtils.toJson(itemBeans));
                setItemAdapter(itemBeans);
                if (curFilterPosition == position) {//刷新

                } else {
                    DataCollectionFilterBean dataCollectionFilterOld = (DataCollectionFilterBean) adapter.getItem(curFilterPosition);
                    dataCollectionFilterOld.setCheck(false);
                }
                dataCollectionFilter.setCheck(true);
                dataCollectionKind2FilterAdapter.notifyItemChanged(curFilterPosition);
                dataCollectionKind2FilterAdapter.notifyItemChanged(position);
                curFilterPosition = position;
            }
        });
        /*dataCollectionMultiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DataCollectionBean
                        dataCollection = (DataCollectionBean) adapter.getItem(position);
                dataCollection.setCheck(!dataCollection.isCheck());
                dataCollectionMultiAdapter.notifyItemChanged(position);
            }
        });*/
    }

   /* @Override
    @OnClick({R.id.activity_data_collection_multi_rtv_next})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_data_collection_multi_rtv_next:
                IntentUtils.startActivity(DataCollectionMultiActivity.this, DataCollectionResultActivity.class);
                break;
        }
    }*/
}

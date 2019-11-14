package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.TabCheckAdaper;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.Item2Bean;
import com.agilefinger.labourservice.bean.ItemBean;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.agilefinger.labourservice.activity.DataCollectionActivity.firstFather;

public class SeachItemActivity extends BaseActivity {
    @BindView(R.id.layout_toolbar_search_2_et_search)
    EditText etSearch;
    @BindView(R.id.activity_inspection_search_rv_list)
    RecyclerView sourecycleview;
    private TabCheckAdaper tabAdapter;
    private String taskid;
    private String projectid;

    @Override
    public int initLayoutView() {
        return R.layout.activity_seach_item;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarSearch2("搜索检查项名称");
        Intent intent = getIntent();
        taskid = intent.getStringExtra("taskid");
        projectid = intent.getStringExtra("projectid");

        sourecycleview.setLayoutManager(new LinearLayoutManager(SeachItemActivity.this));
        sourecycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 10;
                outRect.left = 20;
                outRect.right = 16;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = etSearch.getText().toString().trim();
                    List<ItemBean> itemBeans = retrunData(search);
                    if (!itemBeans.isEmpty()){
                        setItemAdapter(itemBeans);
                    }else {
                        ToastUtil.showShort(SeachItemActivity.this,"没有搜索到检测项");
                    }
                    return true;
                }
                return false;
            }
        });
    }


    //顶级下的数据
    private void setItemAdapter(final List<ItemBean> itemBeans) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                tabAdapter = new TabCheckAdaper(SeachItemActivity.this,itemBeans);
                sourecycleview.setLayoutManager(new LinearLayoutManager(SeachItemActivity.this));
                sourecycleview.setNestedScrollingEnabled(false);
                sourecycleview.setAdapter(tabAdapter);
                tabAdapter.setOnItemClickListener(new TabCheckAdaper.OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, TabCheckAdaper.Hm2Holder holder, List<ItemBean> list) {
                        Intent intent=new Intent(SeachItemActivity.this,InspectionItemActivity.class);
                        intent.putExtra("name",list.get(position).getMci_name());
                        intent.putExtra("taskid",taskid);
                        intent.putExtra("projectid",projectid);
                        intent.putExtra("ciid",list.get(position).getMci_id());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    List<ItemBean> itemsList2=new ArrayList<>();

    //返回顶级下子条目的数据
    private List<ItemBean> retrunData(String search) {
        itemsList2.clear();
        for (int i=0;i<firstFather.size();i++){
            List<Item2Bean> items = firstFather.get(i).getItems();
            Log.d("所有子集遍历2","::"+items.size());
            for (int g=0;g<items.size();g++){
                if (items.get(g).getMci_name().contains(search)){
                    Log.d("所有子集遍历3","::"+items.get(g).getMci_name());
                    ItemBean itemBean = new ItemBean();
                    itemBean.setMci_id(items.get(g).getMci_id());
                    itemBean.setMci_name(items.get(g).getMci_name());
                    itemBean.setMci_mct_id(items.get(g).getMct_p_id());
                    String path="";
                    if (items.get(g).getFatherBean().getPath().equals("")){
                        path=items.get(g).getFatherBean().getMct_name();
                    }else {
                        path= items.get(g).getFatherBean().getPath()+">"+items.get(g).getFatherBean().getMct_name();
                    }
                    itemBean.setPath(path);
                    itemBean.setP_count(items.get(g).getP_count());
                    itemBean.setMci_min_point(items.get(g).getMci_min_point());
                    itemBean.setMci_no(items.get(g).getMci_no());
                    itemsList2.add(itemBean);
                }
            }
        }

        return itemsList2;
    }
}

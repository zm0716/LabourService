package com.agilefinger.labourservice.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

import butterknife.BindView;

public class InspectionSearchKBActivity extends BaseActivity {
    @BindView(R.id.layout_toolbar_search_2_et_search)
    EditText etSearch;
    @BindView(R.id.activity_inspection_search_rv_list)
    RecyclerView rvList;
    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_search_kb;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbarSearch2("搜索项目名称");
    }
}

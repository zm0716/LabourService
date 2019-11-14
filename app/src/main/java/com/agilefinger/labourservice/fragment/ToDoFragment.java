package com.agilefinger.labourservice.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.JBaseFragment;

/**
 * Created by 86251 on 2019/5/28.
 * 待办
 */

public class ToDoFragment extends JBaseFragment {


    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_to_do;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {

    }


    @Override
    protected void lazyLoadOnlyOne() {

    }

}

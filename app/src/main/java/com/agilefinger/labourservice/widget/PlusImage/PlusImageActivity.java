package com.agilefinger.labourservice.widget.PlusImage;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PlusImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    private String state;

    @Override
    public int initLayoutView() {
        return R.layout.activity_plus_image;
    }

    @Override
    public void initData() {
        super.initData();
        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        if (getIntent().getStringExtra("State") != null) {
            state = getIntent().getStringExtra("State");
        }
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        positionTv = (TextView) findViewById(R.id.layout_toolbar_tv_title);
        viewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(this, imgList);

        mAdapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, List<String> list) {
                back();
            }
        });
        viewPager.setAdapter(mAdapter);

        setToolbar(mPosition + 1 + "/" + imgList.size(), false, false,"");
        viewPager.setCurrentItem(mPosition);


    }

    //设置当前位置
    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

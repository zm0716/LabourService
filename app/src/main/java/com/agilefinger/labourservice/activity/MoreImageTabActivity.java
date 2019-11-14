package com.agilefinger.labourservice.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreImageTabActivity extends BaseActivity {

    @BindView(R.id.layout_toolbar_iv_back)
    ImageView layoutToolbarIvBack;
    @BindView(R.id.layout_toolbar_tv_title)
    TextView layoutToolbarTvTitle;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView layoutToolbarIvOperate;
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView layoutToolbarIvOperate2;
    @BindView(R.id.layout_toolbar_tv_operate)
    TextView layoutToolbarTvOperate;
    @BindView(R.id.layout_toolbar_ll_wrapper)
    LinearLayout layoutToolbarLlWrapper;
    @BindView(R.id.m_rb_one)
    RadioButton mRbOne;
    @BindView(R.id.m_rb_two)
    RadioButton mRbTwo;
    @BindView(R.id.m_rb_three)
    RadioButton mRbThree;
    @BindView(R.id.m_rb_four)
    RadioButton mRbFour;
    @BindView(R.id.m_rg)
    RadioGroup mRg;
    @BindView(R.id.m_frame)
    FrameLayout mFrame;

    @Override
    public int initLayoutView() {
        return R.layout.activity_more_image_tab;
    }

    @Override
    public void initView() {
        super.initView();
        initRV();
    }

    private void initRV() {
//        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
//                transaction1.hide(createKanBanDatilsOneFragment);
//                transaction1.hide(createKanBanDatilsTwoFragment);
//                transaction1.hide(createKanBanDetailsThreeFragment);
//                transaction1.hide(createKanBanDatilsFourFragment);
//                transaction1.hide(createKanBanDatilsFiveFragment);
//                switch (checkedId) {
//                    case R.id.redio_one:
//                        transaction1.show(createKanBanDatilsOneFragment);
//                        break;
//                    case R.id.redio_two:
//                        transaction1.show(createKanBanDatilsTwoFragment);
//                        break;
//                    case R.id.redio_three:
//                        transaction1.show(createKanBanDetailsThreeFragment);
//                        break;
//                    case R.id.redio_four:
//                        transaction1.show(createKanBanDatilsFourFragment);
//                        break;
//                    case R.id.redio_five:
//                        transaction1.show(createKanBanDatilsFiveFragment);
//                        break;
//                }
//                transaction1.commit();
//            }
//        });


    }


}

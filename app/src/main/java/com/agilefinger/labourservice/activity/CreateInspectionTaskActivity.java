package com.agilefinger.labourservice.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.CreateInspectionTaskAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.callback.InspectionCreateListener;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.fragment.CreateInspectionTaskStepOneFragment;
import com.agilefinger.labourservice.fragment.CreateInspectionTaskStepThreeFragment;
import com.agilefinger.labourservice.fragment.CreateInspectionTaskStepTwoFragment;
import com.agilefinger.labourservice.fragment.MessageFragment;
import com.agilefinger.labourservice.fragment.MyFragment;
import com.agilefinger.labourservice.fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新建检测任务
 */
public class CreateInspectionTaskActivity extends BaseActivity implements InspectionCreateListener {
    @BindView(R.id.activity_create_inspection_task_iv_step)
    ImageView ivStep;
    @BindView(R.id.activity_create_inspection_task_tv_step_one)
    TextView tvStepOne;
    @BindView(R.id.activity_create_inspection_task_tv_step_two)
    TextView tvStepTwo;
    @BindView(R.id.activity_create_inspection_task_tv_step_three)
    TextView tvStepThree;

    @BindView(R.id.layout_toolbar_iv_back)
    ImageView goback;

    private int curStep = 0;
    private TextView[] step = new TextView[3];
    private List<Fragment> mFragments;
    private Fragment[] fragments = new Fragment[3];
    private String companyId;
    public static CreateInspectionTaskStepOneFragment createInspectionTaskStepOneFragment;
    public static CreateInspectionTaskStepTwoFragment createInspectionTaskStepTwoFragment;

    @Override
    public int initLayoutView() {
        return R.layout.activity_create_inspection_task;
    }

    @Override
    public void initView() {
        super.initView();
        // setToolbar("新建检测任务", false, false, "");
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
        step = new TextView[]{tvStepOne, tvStepTwo, tvStepThree};
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        createInspectionTaskStepOneFragment = new CreateInspectionTaskStepOneFragment(companyId);
        createInspectionTaskStepTwoFragment = new CreateInspectionTaskStepTwoFragment(companyId);

        mFragments.add(createInspectionTaskStepOneFragment);
        mFragments.add(createInspectionTaskStepTwoFragment);
        mFragments.add(new CreateInspectionTaskStepThreeFragment(companyId));
        fragments[0] = mFragments.get(0);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.activity_create_inspection_task_fl_wrapper, fragments[0]);
        tx.commitAllowingStateLoss();
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index.equals("0")) {
                    finish();
                } else if (index.equals("1")) {
                    index = "0";
                    showStep("0");
                } else if (index.equals("2")) {
                    index = "1";
                    showStep("1");
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d("点击返回", "返回");
            if (index.equals("0")) {
                finish();
            } else if (index.equals("1")) {
                index = "0";
                showStep("0");
            } else if (index.equals("2")) {
                index = "1";
                showStep("1");
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private String index = "0";

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void toNextStepCallback(String... flag) {
        switch (flag[0]) {
            case "1":
                index = "1";
                showStep("1");
                break;
            case "2":
                index = "2";
//                createInspectionTaskStepTwoFragment.textDx.setText("AAAAAAA");
                showStep("2");
                break;
            case "3":
                break;
        }
    }

    private void showStep(String i) {
        int index = Integer.parseInt(i);
        //step[curStep].setTextColor(getResources().getColor(R.color.black_333333));
        step[index].setTextColor(getResources().getColor(R.color.blue_0079e4));
        if (index == 1) {
            ivStep.setImageResource(R.mipmap.step_2);
        } else if (index == 2) {
            ivStep.setImageResource(R.mipmap.step_3);
        }
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        if (fragments[index] == null) {
            fragments[index] = mFragments.get(index);
            tx.add(R.id.activity_create_inspection_task_fl_wrapper, fragments[index]);
        }
        tx.hide(fragments[curStep]).show(fragments[index]);
        tx.commitAllowingStateLoss();
        curStep = index;
    }
}

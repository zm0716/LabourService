package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.CreateInspectionTaskActivity;
import com.agilefinger.labourservice.activity.InspectionListActivity;
import com.agilefinger.labourservice.adapter.CreateInspectionTaskAdapter;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.data.LoadData;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.flyco.roundview.RoundTextView;

import butterknife.BindView;

/**
 * Created by 86251 on 2019/5/28.
 */

@SuppressLint("ValidFragment")
public class CreateInspectionTaskStepThreeFragment extends JBaseFragment {
    @BindView(R.id.checktask_tv)
    RoundTextView checktask_tv;
    @BindView(R.id.continue_tv)
    RoundTextView continue_tv;

    String companyId;
    @SuppressLint("ValidFragment")
    public CreateInspectionTaskStepThreeFragment(String companyId) {
        this.companyId=companyId;
    }


    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_create_inspection_task_step_three;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        //查看任务
        checktask_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                //设置参数，未完成，待解决
                bundle1.putString(Constants.EXTRA_DATA, "");
                bundle1.putString(Constants.EXTRA_TYPE, "");
                bundle1.putString("ischuang", "");
                bundle1.putString(Constants.EXTRA_DATA_COMPANY,companyId);
                IntentUtils.startActivity(getActivity(), InspectionListActivity.class,bundle1);
                getActivity().finish();
            }
        });

        //继续创建
        continue_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(getActivity(), CreateInspectionTaskActivity.class, bundle);
                getActivity().finish();
            }
        });

    }


    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void lazyLoadOnlyOne() {
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }

}

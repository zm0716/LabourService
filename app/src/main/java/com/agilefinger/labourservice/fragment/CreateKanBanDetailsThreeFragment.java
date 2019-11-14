package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.AddBuildingActivity;
import com.agilefinger.labourservice.activity.BatchesBuildingsActivity;
import com.agilefinger.labourservice.adapter.ProjectBuildAdaptere;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.LouDongBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class CreateKanBanDetailsThreeFragment extends JBaseFragment {

    @BindView(R.id.lou_rv)
    RecyclerView lou_rv;

    @BindView(R.id.activity_inspection_detail_rtv_upload)
    RoundTextView addBuilding;
    @BindView(R.id.activity_inspection_detail_rtv_stop)
    RoundTextView pLAddBuilding;

    String projectId;
    private ProjectBuildAdaptere projectBuildAdaptere;
    private LinearLayoutManager filterLinearLayout;

    public CreateKanBanDetailsThreeFragment(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //frament可见时调用
        Log.d("进入frament3", "进入" + hidden);
    }

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.item_kanbanthree_fragment;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {

        LinearLayoutManager filterLinearLayout = new LinearLayoutManager(getActivity());
        filterLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        lou_rv.setLayoutManager(filterLinearLayout);
        lou_rv.setNestedScrollingEnabled(false);
        lou_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 12;
//                outRect.right =10;
//                outRect.left =10;
            }
        });
        //分割线
        // lou_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

    }

    @Override
    protected void lazyLoadOnlyOne() {
        //获取信息
        getData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

    }

    //获取信息
    private void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
//        pram.put("tab", "2");

        OkHttp3Util.doPost(URL.URL_PROJECT + "app/detail/building", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("楼栋信息", string);
                Gson gson = new Gson();
                final LouDongBean louDongBean = gson.fromJson(string, LouDongBean.class);
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {

                        if (louDongBean.getCode() == 0) {
                            List<LouDongBean.DataBean> data = louDongBean.getData();
                            projectBuildAdaptere = new ProjectBuildAdaptere(getActivity(), data);
                            lou_rv.setAdapter(projectBuildAdaptere);
                            projectBuildAdaptere.setOnItemClickListener(new ProjectBuildAdaptere.OnItemClickListener() {
                                @Override
                                public void onClick(View view, int position, ProjectBuildAdaptere.MyHolder holder, List<LouDongBean.DataBean> list) {

                                }
                            });

                        }
                    }
                });

            }
        });
    }

    @OnClick({R.id.activity_inspection_detail_rtv_upload, R.id.activity_inspection_detail_rtv_stop})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //新增
            case R.id.activity_inspection_detail_rtv_upload:
                IntentUtils.startActivity(getActivity(), AddBuildingActivity.class);
                break;
            //批量新增
            case R.id.activity_inspection_detail_rtv_stop:
                IntentUtils.startActivity(getActivity(), BatchesBuildingsActivity.class);
                break;
        }
    }
}

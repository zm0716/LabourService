package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.AddGroupLeaderActivity;
import com.agilefinger.labourservice.adapter.BanZuAdaptere;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.BanZuBean;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class CreateKanBanDatilsFiveFragment extends JBaseFragment {
    @BindView(R.id.fragment_add_zuzhang_rtv_upload)
    RoundTextView fragmentAddZuzhangRtvUpload;
    @BindView(R.id.fragment_add_xzyou_rtv_stop)
    RoundTextView fragmentAddXzyouRtvStop;

    @BindView(R.id.banzu_rv)
    RecyclerView banzu_rv;

    Unbinder unbinder;
    String projectId;

    public CreateKanBanDatilsFiveFragment(String projectId) {
        this.projectId = projectId;
    }

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.item_kanbanfive_fragment;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoadOnlyOne() {
        getData();
    }

    private void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
//        pram.put("tab","4");
        OkHttp3Util.doPost(URL.BASE_URL_7 + "/index/LaborLab/project_labor", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("班组信息", string);
                Gson gson = new Gson();
                BanZuBean banZuBean = gson.fromJson(string, BanZuBean.class);
                if (banZuBean.getCode() == 0) {

                    final List<BanZuBean.DataBean> data = banZuBean.getData();
                    if (null != data && !data.isEmpty()) {
                        LinearLayoutManager filterLinearLayout = new LinearLayoutManager(getActivity());
                        filterLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
                        banzu_rv.setLayoutManager(filterLinearLayout);
                        banzu_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
                            @Override
                            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                                       RecyclerView.State state) {
                                outRect.right = 10;
                                outRect.left = 10;
                            }
                        });
                        banzu_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {

                                BanZuAdaptere banZuAdaptere = new BanZuAdaptere(getActivity(), data);
                                banzu_rv.setAdapter(banZuAdaptere);

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_add_zuzhang_rtv_upload, R.id.fragment_add_xzyou_rtv_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //添加组长
            case R.id.fragment_add_zuzhang_rtv_upload:
                IntentUtils.startActivity(getActivity(), AddGroupLeaderActivity.class);
                break;
            //选择已有组长
            case R.id.fragment_add_xzyou_rtv_stop:

                break;
        }
    }
}

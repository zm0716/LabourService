package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.AddmembersActivity;
import com.agilefinger.labourservice.activity.ManagerActivity;
import com.agilefinger.labourservice.adapter.JLAdapter;
import com.agilefinger.labourservice.adapter.TeamGroupAdaptere;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.TeamGroupPeopleBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CreateKanBanDatilsFourFragment extends JBaseFragment {

    @BindView(R.id.fragment_xmjl_detail_rtv_upload)
    RoundTextView fragmentXmjlDetailRtvUpload;
    @BindView(R.id.fragment_add_detail_rtv_stop)
    RoundTextView fragmentAddDetailRtvStop;
    @BindView(R.id.m_no_line)
    LinearLayout m_no_line;

//    @BindView(R.id.item_tv_name)
//    TextView item_tv_name;

    @BindView(R.id.group_rv)
    RecyclerView group_rv;
    @BindView(R.id.m_jl_recy)
    RecyclerView m_jl_recy;
    Unbinder unbinder;
    String projectId;
    String projectjl;
    private List<TeamGroupPeopleBean.DataBean.MemberBean> member;
    private JLAdapter jlAdapter;
    //    public CreateKanBanDatilsFourFragment(String projectId, String projectjl) {
//        this.projectId = projectId;
//        this.projectjl = projectjl;
//    }

    public CreateKanBanDatilsFourFragment(String projectId) {
        this.projectId = projectId;
//        this.projectjl = projectjl;
    }

    private TeamGroupAdaptere teamGroupAdaptere;

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.item_kanbanfour_fragment;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {

        group_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        group_rv.setNestedScrollingEnabled(false);
        m_jl_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        m_jl_recy.setNestedScrollingEnabled(false);
        group_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                //设置距离为20dp
                outRect.top = 10;
//                outRect.right = 10;
//                outRect.left = 10;
            }
        });
        //分割线
        group_rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));

        jlAdapter = new JLAdapter();
        m_jl_recy.setAdapter(jlAdapter);

//        item_tv_name.setText(projectjl);
    }

    @Override
    protected void lazyLoadOnlyOne() {
        getData();
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

    @OnClick({R.id.fragment_xmjl_detail_rtv_upload, R.id.fragment_add_detail_rtv_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //移交项目经理
            case R.id.fragment_xmjl_detail_rtv_upload:
                IntentUtils.startActivity(getActivity(), ManagerActivity.class);
                break;
            //添加成员
            case R.id.fragment_add_detail_rtv_stop:
                IntentUtils.startActivity(getActivity(), AddmembersActivity.class);
                break;
        }
    }

    //获取信息
    private void getData() {
        Map<String, Object> pram = new HashMap<>();
        pram.put("project_id", projectId);
//        pram.put("tab", "3");

        OkHttp3Util.doPost(URL.URL_PROJECT + "index/ProjectTab/get_project_members", pram, new Callback() {
            private List<TeamGroupPeopleBean.DataBean.ManagerBean> manager;
            private TeamGroupPeopleBean teamGroupBean;


            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("项目组信息", string);
                Gson gson = new Gson();
                teamGroupBean = gson.fromJson(string, TeamGroupPeopleBean.class);
                if (null != teamGroupBean) {
                    if (teamGroupBean.getCode() == 0) {
                        member = teamGroupBean.getData().getMember();
                        if (!member.isEmpty() && member.size() > 0) {
                            teamGroupAdaptere = new TeamGroupAdaptere(getActivity(), member);
                            group_rv.setAdapter(teamGroupAdaptere);
                        } else {
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    m_no_line.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        manager = teamGroupBean.getData().getManager();

                        jlAdapter.setNewData(manager);


                    }
                }

//                teamGroupAdaptere.setOnItemClickListener(new TeamGroupAdaptere.OnItemClickListener() {
////            @Override
////            public void onClick(View view, int position, TeamGroupAdaptere.MyHolder holder, List<TeamGroupBean.DataBean> list) {
////
////            }
//
//                    @Override
//                    public void onDeleteClick(final int postion) {
//
//                        Integer user_id = mlist.get(postion).getUser_id();
//
//
//                        Date date = new Date();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String sim = dateFormat.format(date);
//                        HttpManager.getInstance().removeGroupPeople(projectId, String.valueOf(user_id), sim, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
//                            @Override
//                            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray array) {
//                                if (result == 0) {
//                                    mlist.remove(postion);
//                                    teamGroupAdaptere.notifyDataSetChanged();
//                                } else {
//                                    ToastUtil.showShort(getActivity(), retmsg);
//                                }
//                            }
//                        });
////                data.get(postion).
////                HttpManager.getInstance().removeGroupPeople();
//                    }
//                });
            }
        });
    }
}

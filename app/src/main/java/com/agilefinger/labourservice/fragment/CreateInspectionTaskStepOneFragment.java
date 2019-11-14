package com.agilefinger.labourservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.CreateInspectionTaskActivity;
import com.agilefinger.labourservice.adapter.CreateInspectionTaskAdapter;
import com.agilefinger.labourservice.base.JBaseFragment;
import com.agilefinger.labourservice.bean.AddOneBean;
import com.agilefinger.labourservice.bean.SearchTaskBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.MyToast;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtils;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
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

/**
 * Created by 86251 on 2019/5/28.
 */

@SuppressLint("ValidFragment")
public class CreateInspectionTaskStepOneFragment extends JBaseFragment {

    @BindView(R.id.activity_create_inspection_task_rv_list)
    RecyclerView rvList;
    @BindView(R.id.search)
    EditText edittext;

    public CreateInspectionTaskAdapter createInspectionTaskAdapter;

    private List<AddOneBean> data1;
    String companyId;

    @SuppressLint("ValidFragment")
    public CreateInspectionTaskStepOneFragment(String companyId) {
        this.companyId = companyId;
    }

    @Override
    protected int getCreateViewLayoutId() {
        return R.layout.fragment_create_inspection_task_step_one;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState) {
        initRV();
    }

    private void initRV() {
//回车搜索
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    boolean isActionUp = (event.getAction() == KeyEvent.ACTION_UP);
                    if (!isActionUp) {
                        Log.d("输入", edittext.getText().toString().trim() + "::");
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getIndex2(edittext.getText().toString().trim());

                            }
                        });
                        return true;
                    }

                    return true;
                }
                return false;
            }
        });
    }

    private void getIndex2(String text) {
        LoadingDialog.showLoading(getActivity());
        ischeck = 0;
        Map<String, Object> pram = new HashMap<>();
        pram.put("companyID", companyId);
        pram.put("text", text);
        pram.put("userID", loginBean.getUser_id());
        OkHttp3Util.doPost(URL.XUNJIAN_LIST, pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LoadingDialog.disDialog();
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Log.d("搜索数据", string);
                    Gson gson = new Gson();
                    final SearchTaskBean searchTaskBean = gson.fromJson(string, SearchTaskBean.class);
                    if (null != searchTaskBean) {
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (searchTaskBean.getCode() == 0) {

                                    data1 = searchTaskBean.getData();
                                    if (!data1.isEmpty()) {
                                        rvList.setVisibility(View.VISIBLE);
                                        createInspectionTaskAdapter = new CreateInspectionTaskAdapter(data1, getActivity());
                                        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        rvList.setAdapter(createInspectionTaskAdapter);
                                        //点击选中
                                        createInspectionTaskAdapter.setiClickIem(new CreateInspectionTaskAdapter.IClickIem() {
                                            @Override
                                            public void getClickItem(View view, int position) {
                                                ischeck = 1;
                                                createInspectionTaskAdapter.setPosition(position);
                                            }
                                        });
                                    } else {
                                        MyToast.getInstands().toastShow(getActivity(), "没有查到此项目");
                                        rvList.setVisibility(View.INVISIBLE);
                                    }

                                } else {
                                    MyToast.getInstands().toastShow(getActivity(), "没有查到此项目");
                                    rvList.setVisibility(View.INVISIBLE);
                                }
                            }
                        });

                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void lazyLoadOnlyOne() {
        getIndex("");
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getIndex("");
    }

    private int ischeck = 0;

    private void getIndex(String text) {
        ischeck = 0;
        HttpManager.getInstance().addOneList(companyId, text, loginBean.getUser_id(), new HttpEngine.OnResponseCallback<HttpResponse.ZengOne>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.ZengOne data) {
                if (result == 1) {
                    ToastUtils.showShort(retmsg);
                    return;
                }
                data1 = data.getData();
                createInspectionTaskAdapter = new CreateInspectionTaskAdapter(data1, getActivity());
                rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvList.setAdapter(createInspectionTaskAdapter);
                //点击选中
                createInspectionTaskAdapter.setiClickIem(new CreateInspectionTaskAdapter.IClickIem() {
                    @Override
                    public void getClickItem(View view, int position) {
                        ischeck = 1;
                        createInspectionTaskAdapter.setPosition(position);
                    }
                });
            }
        });
    }


    @Override
    @OnClick({R.id.fragment_create_inspection_task_step_one_rtv_next})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fragment_create_inspection_task_step_one_rtv_next://下一步
                if (ischeck == 1) {
                    ((CreateInspectionTaskActivity) getActivity()).toNextStepCallback("1");
                } else {
                    Toast.makeText(getActivity(), "请选择项目", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

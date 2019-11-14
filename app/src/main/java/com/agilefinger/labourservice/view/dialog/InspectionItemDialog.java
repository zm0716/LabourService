package com.agilefinger.labourservice.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.activity.InspectionItemActivity;
import com.agilefinger.labourservice.adapter.ImagesSmallAdapter;
import com.agilefinger.labourservice.bean.PointDailsBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.flyco.roundview.RoundTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
 * 上传点位弹框
 * */
public class InspectionItemDialog extends Dialog implements View.OnClickListener {
    String pointnum;
    private InspectionItemActivity mContext;
    private OnCloseListener listener;
    private RecyclerView rvList;
    private ImagesSmallAdapter imagesSmallAdapter;

    private TextView point;
    private TextView loutextview;
    private EditText shuoming;
    private EditText miaoshu;
    private TextView jieguo;
    private RoundTextView dialog_inspection_item_rtv_upload;
    boolean isHas;
    List<PointDailsBean.DataBean.PointBean> pointList;
    private View header;
    String user_id;
    private PointDailsBean.DataBean.PointBean pointBean;

    public InspectionItemDialog(InspectionItemActivity context, String pointnum, boolean isHas, List<PointDailsBean.DataBean.PointBean> point, String user_id, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = listener;
        this.pointnum = pointnum;
        this.isHas = isHas;
        this.pointList = point;
        this.user_id = user_id;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_inspection_item2);
        initView();
    }

    List<String> imgwaterList = new ArrayList<>();

    private void initView() {

        rvList = findViewById(R.id.dialog_inspection_item_rv_image);
        point = findViewById(R.id.dialog_inspection_item_tv_title);
        loutextview = findViewById(R.id.dialog_inspection_item_tv_position);
        shuoming = findViewById(R.id.shuoming);
        miaoshu = findViewById(R.id.miaoshu);
        jieguo = findViewById(R.id.jieguo);
        dialog_inspection_item_rtv_upload = findViewById(R.id.dialog_inspection_item_rtv_upload);
        loutextview.setOnClickListener(this);
        dialog_inspection_item_rtv_upload.setOnClickListener(this);
        pointBean = pointList.get((Integer.parseInt(pointnum) - 1));
        Log.d("当前数据", pointBean.getRp_result());
        dialog_inspection_item_rtv_upload.setText("编辑");
        loutextview.setText(pointBean.getName());
        loutextview.setClickable(false);
        loutextview.setTextColor(Color.parseColor("#333333"));
        shuoming.setText(("" + pointBean.getRp_remark() + "").replace("null", "") + "");
        jieguo.setText("" + pointBean.getRp_result() + "");
        miaoshu.setText("" + ("" + pointBean.getRp_position_des() + "").replace("null", "") + "");
        miaoshu.setEnabled(false);
        shuoming.setEnabled(false);
        imagesSmallAdapter = new ImagesSmallAdapter();
        if (pointList != null) {
            List<PointDailsBean.DataBean.PointBean.ImgListBean> imgList = pointBean.getImgList();
            for (int i = 0; i < imgList.size(); i++) {
                imgwaterList.add(imgList.get(i).getPi_watermark_url());
            }
            imagesSmallAdapter.addData(imgwaterList);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(imagesSmallAdapter);
        point.setText(pointnum);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_inspection_item_tv_position:
                ToastUtil.showShort(mContext, "点击");
                break;
            case R.id.dialog_inspection_item_rtv_upload:

                if (isHas) {
                    if (dialog_inspection_item_rtv_upload.getText().equals("保存")) {
                        //上传备注
                        Map<String, Object> pram = new HashMap<>();
                        pram.put("rp_remark", shuoming.getText().toString());
                        pram.put("rp_mci_id", pointBean.getRp_mci_id());
                        pram.put("rp_no", pointBean.getRp_no());
                        pram.put("rp_mi_no", pointBean.getRp_mi_no());
                        Log.d("上传点位数据", GsonUtils.toJson(pram));
                        OkHttp3Util.doPost(URL.BASE_URL_4 + "msn/write_data_qualified_edit_point", pram, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String string = response.body().string();
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismiss();
                                        mContext.getData();

                                    }
                                });
                            }
                        });

                    } else {
                        dialog_inspection_item_rtv_upload.setText("保存");
                        shuoming.setEnabled(true);
                        shuoming.setFocusable(true);
                        shuoming.setCursorVisible(true);
                        shuoming.setFocusableInTouchMode(true);
                        shuoming.requestFocus();
                        shuoming.setSelection(shuoming.getText().length());
                    }
                }
                break;

        }
    }


    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String... param);
    }

    @Override
    public void show() {
        super.show();
/*
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);*/
        Window window = getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }
}
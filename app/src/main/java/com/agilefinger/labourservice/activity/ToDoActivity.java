package com.agilefinger.labourservice.activity;

import android.Manifest;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.ZGImagesAdapter;
import com.agilefinger.labourservice.adapter.ZGVoiceAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.agilefinger.labourservice.widget.voice.EaseVoiceRecorderView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ToDoActivity extends BaseActivity {
    @BindView(R.id.activity_reply_rv_images)
    RecyclerView rvImages;
    @BindView(R.id.activity_reply_rv_voice)
    RecyclerView rvVoice;
    @BindView(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorderView;
    @BindView(R.id.activity_reply_ll_bottom)
    LinearLayout linearVoice;
    @BindView(R.id.activity_reply_et_remark)
    EditText etRemark;
    @BindView(R.id.activity_to_do_tv_title_voice)
    TextView tvVoice;
    @BindView(R.id.activity_to_do_tv_title_remark)
    TextView tvRemark;
    private ZGImagesAdapter zgImagesAdapter;
    private ZGVoiceAdapter zgVoiceAdapter;
    private String zid, type, manId, manName;

    @Override
    public int initLayoutView() {
        return R.layout.activity_to_do;
    }

    @Override
    public void initData() {
        super.initData();
        if (!checkLogin()) finish();
        zid = getIntent().getExtras().getString(Constants.EXTRA_DATA);
        type = getIntent().getExtras().getString(Constants.EXTRA_TYPE);
        manId = getIntent().getExtras().getString(Constants.EXTRA_DATA_);
        manName = getIntent().getExtras().getString(Constants.EXTRA_DATA__);
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                },
                1
        );
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(type, false, true, "");
        if (Constants.ZG_BOHUI.equals(type) || Constants.ZG_TONGGUO.equals(type) || Constants.ZG_HUIFU.equals(type)) {
            showRequiredFields();
        }
        initRV();
    }

    private void initRV() {
        LinearLayoutManager zgLinearLayoutManager = new LinearLayoutManager(this);
        zgLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zgImagesAdapter = new ZGImagesAdapter();
        rvImages.setLayoutManager(zgLinearLayoutManager);
        rvImages.setAdapter(zgImagesAdapter);
        zgVoiceAdapter = new ZGVoiceAdapter();
        rvVoice.setLayoutManager(new LinearLayoutManager(this));
        rvVoice.setAdapter(zgVoiceAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        zgImagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String path = adapter.getItem(position).toString();
                if (TextUtils.isEmpty(path)) {
                    hideSoftKeyboard();
                    showPhotoPop();
                } else {
                    plusImage(zgImagesAdapter.getData(), position);
                }
            }
        });
        zgImagesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_zg_images_iv_del:
                        zgImagesAdapter.remove(position);
                        break;
                }
            }
        });
        zgVoiceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                VoiceBean voice = (VoiceBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_zg_voice_rll_voice:
                        playVoice(voice.getoPath(), (ImageView) view.findViewById(R.id.item_zg_voice_iv_play));//URL.BASE_URL + voice.getPath()
                        break;
                    case R.id.item_zg_voice_iv_del:
                        zgVoiceAdapter.remove(position);
                        break;
                }
            }
        });
        linearVoice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return voiceRecorderView.onPressToSpeakBtnTouch(view, motionEvent, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                      /*  List<VoiceBean> data = zgVoiceAdapter.getData();
                        if (data.size()>4){
                            ThreadUtils.runOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.getInstands().toastShow(ToDoActivity.this,"语音限制为5条以内");
                                }
                            });
                        }else {
                            uploadVoice(voiceTimeLength, voiceFilePath);
                        }*/
                        uploadVoice(voiceTimeLength, voiceFilePath);
                    }
                });
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        List<String> imageList = new ArrayList<>();
        imageList.add("");
        zgImagesAdapter.setNewData(imageList);
    }

    private void uploadVoice(final float len, final String savePath) {
        File file = new File(savePath);
        if (file.exists()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().uploadVoice(file, new HttpEngine.OnResponseCallback<HttpResponse.UploadFile>() {
                @Override
                public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.UploadFile data) {
                    LoadingDialog.disDialog();
                    if (result != 0) {
                        showToast(retmsg);
                        return;
                    }
                    VoiceBean voice = new VoiceBean(len, data.getData().getFile_url(), savePath);
                    zgVoiceAdapter.addData(voice);
                }
            });
        }
    }

    @Override
    protected void showImage(String image_path) {
        File file = new File(image_path);
        if (file.exists()) {
            LoadingDialog.showLoading(this);
            HttpManager.getInstance().uploadImage(file, new HttpEngine.OnResponseCallback<HttpResponse.UploadFile>() {
                @Override
                public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.UploadFile data) {
                    LoadingDialog.disDialog();
                    if (result != 0) {
                        showToast(retmsg);
                        return;
                    }
                    zgImagesAdapter.addData(zgImagesAdapter.getData().size() - 1, data.getData().getFile_url());
                    smoothMoveToPosition(rvImages, zgImagesAdapter.getData().size() - 1);
                }
            });
        }
    }

    @Override
    protected void toOperate() {
        super.toOperate();
        toDo();
    }

    private void toDo() {
        String reason = etRemark.getText().toString().trim();
        List<String> imageList = zgImagesAdapter.getData();
        List<VoiceBean> voiceList = zgVoiceAdapter.getData();
        if (Constants.ZG_BOHUI.equals(type) || Constants.ZG_TONGGUO.equals(type) || Constants.ZG_HUIFU.equals(type)) {
            if ((imageList == null || imageList.size() < 2) && (voiceList == null || voiceList.size() < 1) && TextUtils.isEmpty(reason)) {
                showToast("请提供语音、文字或图片说明");
                return;
            }
        }
        if (TextUtils.isEmpty(reason)) {
            reason = "暂无内容";
        }
        LoadingDialog.showLoading(this);
        String images = "";
        if (imageList != null && imageList.size() > 1) {
            imageList.remove(imageList.size() - 1);
            for (int i = 0; i < imageList.size(); i++) {
                if (i == imageList.size() - 1) {
                    images += imageList.get(i);
                } else {
                    images += imageList.get(i) + ",";
                }
            }
        }
        String voice = "", voice_second = "";
        if (voiceList != null && voiceList.size() > 0) {
            for (int i = 0; i < voiceList.size(); i++) {
                if (i == voiceList.size() - 1) {
                    voice += voiceList.get(i).getPath();
                    voice_second += voiceList.get(i).getLen();
                } else {
                    voice += voiceList.get(i).getPath() + ",";
                    voice_second += voiceList.get(i).getLen() + ",";
                }
            }
        }
        switch (type) {
            case Constants.ZG_BOHUI:
                HttpManager.getInstance().reject(loginBean.getUser_id(), zid, reason, images, voice, voice_second, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                        LoadingDialog.disDialog();
                        showToast(retmsg);
                        if (result != 0) {
                            return;
                        }
                        finish();
                    }
                });
                break;
            case Constants.ZG_HUIFU:
                HttpManager.getInstance().reply(loginBean.getUser_id(), zid, reason, images, voice, voice_second, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                        LoadingDialog.disDialog();
                        showToast(retmsg);
                        if (result != 0) {
                            return;
                        }
                        finish();
                    }
                });
                break;
            case Constants.ZG_JUJUE:
                HttpManager.getInstance().refuse(loginBean.getUser_id(), zid, reason, images, voice, voice_second, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                        LoadingDialog.disDialog();
                        showToast(retmsg);
                        if (result != 0) {
                            return;
                        }
                        finish();
                    }
                });
                break;
            case Constants.ZG_TONGGUO:
                HttpManager.getInstance().pass(loginBean.getUser_id(), zid, reason, images, voice, voice_second, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                        LoadingDialog.disDialog();
                        showToast(retmsg);
                        if (result != 0) {
                            return;
                        }
                        finish();
                    }
                });
                break;
            case Constants.ZG_ZHIPAI:
                HttpManager.getInstance().appoint(loginBean.getUser_id(), zid, reason, images, voice, voice_second, manId, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
                    @Override
                    public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
                        LoadingDialog.disDialog();
                        showToast(retmsg);
                        if (result != 0) {
                            return;
                        }
                        finish();
                    }
                });
                break;
        }
    }

    private void showRequiredFields() {
        SpannableString spannableString1 = new SpannableString("请输入备注说明*");
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_fb5215)), 7, 8, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvRemark.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString("语音说明*");
        spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_fb5215)), 4, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvVoice.setText(spannableString2);
    }
}

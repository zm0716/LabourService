package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.agilefinger.labourservice.LSApplication;
import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.adapter.RectificationAdapter;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.ImagesBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.db.RectificationDao;
import com.agilefinger.labourservice.db.VoiceDao;
import com.agilefinger.labourservice.utils.GsonUtils;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.agilefinger.labourservice.utils.SpacesItemDecoration;
import com.agilefinger.labourservice.view.dialog.ConfirmCusDelDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;


/**
 * 整改派单
 */
public class DraftListActivity extends BaseActivity implements RectificationAdapter.CallbackListener {

    @BindView(R.id.activity_draft_list_rv_list)
    RecyclerView rvList;
    RectificationAdapter rectificationAdapter;
    private String companyId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_draft_list;
    }

    @Override
    public void initData() {
        super.initData();
        companyId = getIntent().getExtras().getString(Constants.EXTRA_DATA_COMPANY);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("草稿箱", false, false,"");
        initRV();
    }

    private void initRV() {
     /*   RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
        VoiceDao voiceDao = new VoiceDao(LSApplication.context);
        List<RectificationBean> mList = rectificationDao.queryRectificationAll();
        Log.d("草稿箱",mList.size()+"::");
        String user_id = loginBean.getUser_id();*/


        rectificationAdapter = new RectificationAdapter(this);
        rectificationAdapter.setEmptyView(emptyView(rvList, "暂无草稿"));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new SpacesItemDecoration(AutoSizeUtils.dp2px(this, 1)));
        rvList.setAdapter(rectificationAdapter);
    }

    @Override
    public void initLoad() {
        super.initLoad();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getRectificationList();
    }

    private void getRectificationList() {
        RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
        VoiceDao voiceDao = new VoiceDao(LSApplication.context);
        List<RectificationBean> mList = rectificationDao.queryRectificationAll();
        String user_id = loginBean.getUser_id();
        List<RectificationBean> mList2=new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            Log.d("当前用户id",user_id+"::"+GsonUtils.toJson(mList.get(i)));

            if (user_id.equals(mList.get(i).getTime2())){
                mList2.add(mList.get(i));
                List<VoiceBean> voiceList = voiceDao.queryVoice(mList.get(i).getId());
                List<String> voice = new ArrayList<>();
                List<String> image = new ArrayList<>();
                List<String> voice_second = new ArrayList<>();
                for (int j = 0; j < voiceList.size(); j++) {
                    voice.add(voiceList.get(j).getPath());
                    voice_second.add(String.valueOf(voiceList.get(j).getLen()));
                }
                if (!TextUtils.isEmpty(mList.get(i).getImage())) {
                    image = Arrays.asList(mList.get(i).getImage().split(","));
                    List<ImagesBean> imagesList = new ArrayList<>();
                    if (image != null) {
                        int size = image.size() > 4 ? 4 : image.size();
                        for (int j = 0; j < size; j++) {
                            ImagesBean imagesBean = new ImagesBean(image.get(j), 0);
                            imagesList.add(imagesBean);
                        }
                        if (image.size() > 4) {
                            imagesList.get(3).setNum(image.size());
                        }
                    }
                    mList2.get(i).setImagesList(imagesList);
                }
                mList2.get(i).setVoice(voice);
                mList2.get(i).setVoice_second(voice_second);
            }

        }
        rectificationAdapter.setNewData(mList2);
    }

    @Override
    public void initListener() {
        super.initListener();
        rectificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RectificationBean rectification = (RectificationBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_DATA, rectification.getId());
                bundle.putString(Constants.EXTRA_TYPE, Constants.TYPE_EDIT);
                bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
                IntentUtils.startActivity(DraftListActivity.this, AddRectificationActivity.class, bundle);
            }
        });
        rectificationAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                RectificationBean rectification = (RectificationBean) adapter.getItem(position);
                showConfirmDialog(rectification, position);
                return true;
            }
        });
    }


    private ConfirmCusDelDialog confirmDialog;

    private void showConfirmDialog(final RectificationBean rectification, final int position) {
        closeConfirmDialog();
        confirmDialog = new ConfirmCusDelDialog(this, "确定删除草稿？", true, new ConfirmCusDelDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                if (confirm) {
                    if (!TextUtils.isEmpty(rectification.getId())) {
                        RectificationDao rectificationDao = new RectificationDao(LSApplication.context);
                        rectificationDao.deleteRectification(rectification.getId());
                        rectificationAdapter.remove(position);
                    }
                }
                closeConfirmDialog();
            }
        });
        confirmDialog.show();
    }

    private void closeConfirmDialog() {
        if (confirmDialog != null) {
            confirmDialog.cancel();
            confirmDialog = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeConfirmDialog();
    }

    @Override
    public void todo(Object object, int position) {
        RectificationBean rectification = (RectificationBean) object;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_DATA, rectification.getId());
        bundle.putString(Constants.EXTRA_TYPE, Constants.TYPE_EDIT);
        bundle.putString(Constants.EXTRA_DATA_COMPANY, companyId);
        IntentUtils.startActivity(DraftListActivity.this, AddRectificationActivity.class, bundle);
    }

    @Override
    public void todoLong(Object object, int position) {
        RectificationBean rectification = (RectificationBean) object;
        showConfirmDialog(rectification, position);
    }
}

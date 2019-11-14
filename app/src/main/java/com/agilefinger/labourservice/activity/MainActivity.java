package com.agilefinger.labourservice.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.VersionCodeBean;
import com.agilefinger.labourservice.fragment.MessageFragment;
import com.agilefinger.labourservice.fragment.MyFragment;
import com.agilefinger.labourservice.fragment.WorkFragment;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.VersionUtils;
import com.agilefinger.labourservice.view.dialog.ConfirmDialog;
import com.agilefinger.labourservice.view.dialog.DonLodDialog;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_pn_bottom)
    PageNavigationView pageNavigationView;
    private List<Fragment> mFragments;
    private Fragment[] fragments = new Fragment[3];
    private NavigationController navigationController;
    private DonLodDialog donLodDialog;

    @Override
    public int initLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        initFragment();
        initBottomTab();
    }

    private boolean resume = false;

    @Override
    protected void onResume() {
        super.onResume();
        checkVersion();
    }

    @Override
    public void initLoad() {
        super.initLoad();
//        checkVersion();
    }

    private void checkVersion() {
        LoadingDialog.showLoading(this);
        HttpManager.getInstance().getAppVersion(new HttpEngine.OnResponseCallback<HttpResponse.VersionBean>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.VersionBean data) {
                if (result == 0) {
                    VersionCodeBean versionCodeBean = data.getData();
                    if (versionCodeBean != null) {
                        LoadingDialog.disDialog();
                        if (versionCodeBean.getApp_phone_type().equals("Android")) {
                            //后台版本号
                            String app_version = versionCodeBean.getApp_version();
                            String app_versionString = app_version.replace(".", ",");
                            String[] app_versionsplit = app_versionString.split(",");
                            //本地版本号
                            String versionName = VersionUtils.getVersionName(MainActivity.this);
                            String app_versionName = versionName.replace(".", ",");
                            String[] versionNamesplit = app_versionName.split(",");




                            if (Integer.parseInt(app_versionsplit[0]) > Integer.parseInt(versionNamesplit[0])) {

                                if (versionCodeBean.getApp_update_install().equals("y")) {
                                    showUpdateAppDialog(versionCodeBean);
                                } else {
                                    if (!resume) {
                                        showUpdateAppDialog(versionCodeBean);
                                    } else {
                                        return;
                                    }
                                }
                            } else if (Integer.parseInt(app_versionsplit[0]) == Integer.parseInt(versionNamesplit[0])) {
                                if (Integer.parseInt(app_versionsplit[1]) > Integer.parseInt(versionNamesplit[1])) {
                                    if (versionCodeBean.getApp_update_install().equals("y")) {
                                        showUpdateAppDialog(versionCodeBean);
                                    } else {
                                        if (!resume) {
                                            showUpdateAppDialog(versionCodeBean);
                                        } else {
                                            return;
                                        }
                                    }
                                } else if (Integer.parseInt(app_versionsplit[1]) == Integer.parseInt(versionNamesplit[1])) {
                                    if (Integer.parseInt(app_versionsplit[2]) > Integer.parseInt(versionNamesplit[2])) {
                                        if (versionCodeBean.getApp_update_install().equals("y")) {
                                            showUpdateAppDialog(versionCodeBean);
                                        } else {
                                            if (!resume) {
                                                showUpdateAppDialog(versionCodeBean);
                                            } else {
                                                return;
                                            }
                                        }
                                    } else if (Integer.parseInt(app_versionsplit[2]) == Integer.parseInt(versionNamesplit[2])) {
                                        return;
                                    } else {
                                        //
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            } else {
                                return;
                            }


//                            if (versionCodeBean.getApp_version().equals("V" + VersionUtils.getVersionName(MainActivity.this)) || versionCodeBean.getApp_version().equals("v" + VersionUtils.getVersionName(MainActivity.this)) || versionCodeBean.getApp_version().equals(VersionUtils.getVersionName(MainActivity.this))) {
//                                return;
//                            } else {
//
//                            }

                        }
                    }

                }
            }

        });
    }

    private void showUpdateAppDialog(final VersionCodeBean versionCodeBean) {
        donLodDialog = new DonLodDialog(this, R.style.dialog, versionCodeBean.getApp_version(), versionCodeBean.getApp_update_time(), versionCodeBean.getApp_update_install(), new DonLodDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String... param) {
                resume = true;
                AllenVersionChecker
                        .getInstance()
                        .downloadOnly(
                                UIData.create().setDownloadUrl(versionCodeBean.getApp_url())
                        ).setDirectDownload(true)
                        .executeMission(MainActivity.this);
                dialog.dismiss();
            }

            @Override
            public void onBackClick(Dialog dialog) {
                finish();
            }

            @Override
            public void onBackClick(Dialog dialog, Boolean a) {
                resume = true;
                dialog.dismiss();
            }
        });
        donLodDialog.show();

    }


    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new MessageFragment());
        mFragments.add(new WorkFragment());
        mFragments.add(new MyFragment());
        fragments[0] = mFragments.get(0);
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.add(R.id.activity_main_fl_wrapper, fragments[0]);
        tx.commitAllowingStateLoss();
    }

    private void initBottomTab() {
        navigationController = pageNavigationView.custom()
                .addItem(newItem(R.mipmap.ic_bottom_message, R.mipmap.ic_bottom_message_selected, "消息"))
                .addItem(newItem(R.mipmap.ic_bottom_work, R.mipmap.ic_bottom_work_selected, "工作"))
                .addItem(newItem(R.mipmap.ic_bottom_my, R.mipmap.ic_bottom_my_selected, "我的"))
                .build();
        navigationController.setSelect(0);
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                if (fragments[index] == null) {
                    fragments[index] = mFragments.get(index);
                    tx.add(R.id.activity_main_fl_wrapper, fragments[index]);
                }
                tx.hide(fragments[old]).show(fragments[index]);
                tx.commitAllowingStateLoss();
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(ContextCompat.getColor(this, R.color.black_333333));
        normalItemView.setTextCheckedColor(ContextCompat.getColor(this, R.color.blue_003093));
        return normalItemView;
    }


}


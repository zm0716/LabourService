package com.agilefinger.labourservice.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.http.HttpEngine;
import com.agilefinger.labourservice.http.HttpManager;
import com.agilefinger.labourservice.http.HttpResponse;
import com.agilefinger.labourservice.view.dialog.LoadingDialog;
import com.flyco.roundview.RoundTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *批量新增楼栋
 * */
public class BatchesBuildingsActivity extends BaseActivity {

    @BindView(R.id.layout_toolbar_iv_back)
    ImageView layoutToolbarIvBack;
    @BindView(R.id.layout_toolbar_tv_title)
    TextView layoutToolbarTvTitle;
    @BindView(R.id.layout_toolbar_iv_operate)
    ImageView layoutToolbarIvOperate;
    @BindView(R.id.layout_toolbar_iv_operate_2)
    ImageView layoutToolbarIvOperate2;
    @BindView(R.id.layout_toolbar_tv_operate)
    TextView layoutToolbarTvOperate;
    @BindView(R.id.layout_toolbar_ll_wrapper)
    LinearLayout layoutToolbarLlWrapper;
    @BindView(R.id.m_batches_title)
    TextView mBatchesTitle;
    @BindView(R.id.m_project_name)
    TextView mProjectName;
    @BindView(R.id.m_project_name_editext)
    EditText mProjectNameEditext;
    @BindView(R.id.m_down_number)
    TextView mDownNumber;
    @BindView(R.id.m_down_number_editext)
    EditText mDownNumberEditext;
    @BindView(R.id.m_up_number)
    TextView mUpNumber;
    @BindView(R.id.m_up_number_editext)
    EditText mUpNumberEditext;
    @BindView(R.id.activity_next_creat_project)
    RoundTextView activityNextCreatProject;
    @BindView(R.id.m_dong)
    CheckBox mDong;
    @BindView(R.id.m_nan)
    CheckBox mNan;
    @BindView(R.id.m_dongbei)
    CheckBox mDongbei;
    @BindView(R.id.m_xibei)
    CheckBox mXibei;


    //    protected LoginBean loginBean;
//    private ArrayList<String> mlist = new ArrayList<>();

    private Map<Integer, String> map = new HashMap<>();

    @Override
    public int initLayoutView() {
        return R.layout.activity_batches_buildings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        setToolbar("批量新增楼栋", false, false, "");
        mBatchesTitle.setText(Html.fromHtml("说明：<font color='#999999'>使用批量新增楼栋功能，将按照以下信息统一创建一批楼栋，楼号自动按照“1号楼，2号楼，三号楼......”进行命名。如需调整，请在批量创建完成后单独编辑楼栋信息。</font>"));

        mDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDong.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(0, "东立面");
                } else {
                    mDong.setTextColor(Color.parseColor("#999999"));
                    map.remove(0);
                }
            }
        });

        mNan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNan.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(1, "南立面");
                } else {
                    mNan.setTextColor(Color.parseColor("#999999"));
                    map.remove(1);
                }
            }
        });
        mDongbei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDongbei.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(2, "东北立面");
                } else {
                    mDongbei.setTextColor(Color.parseColor("#999999"));
                    map.remove(2);
                }
            }
        });
        mXibei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mXibei.setTextColor(Color.parseColor("#FFFFFF"));
                    map.put(3, "西北立面");

                } else {
                    mXibei.setTextColor(Color.parseColor("#999999"));
                    map.remove(3);
                }
            }
        });
    }

    @Override
    @OnClick({R.id.activity_next_creat_project})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_next_creat_project:
                creatBuilding();
                break;
        }
    }

    private void creatBuilding() {
        Bundle extras = getIntent().getExtras();
        String project_id = extras.getString("project_id");
        String mProjectNumberText = mProjectNameEditext.getText().toString().trim();
        String downNumber = mDownNumberEditext.getText().toString().trim();
        String upNumber = mUpNumberEditext.getText().toString().trim();
        String user_id = loginBean.getUser_id();
        LoadingDialog.showLoading(BatchesBuildingsActivity.this);
        HttpManager.getInstance().addBatchesBuilding(project_id, user_id, mProjectNumberText, downNumber, upNumber, map, new HttpEngine.OnResponseCallback<HttpResponse.StringsArray>() {
            @Override
            public void onResponse(int result, @Nullable String retmsg, @Nullable HttpResponse.StringsArray data) {
//                ToastUtil.showShort(BatchesBuildingsActivity.this, retmsg);
                LoadingDialog.disDialog();
                finish();
            }

        });

    }


}

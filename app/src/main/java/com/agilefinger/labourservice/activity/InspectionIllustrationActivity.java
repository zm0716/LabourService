package com.agilefinger.labourservice.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.StateBean;
import com.agilefinger.labourservice.common.URL;
import com.agilefinger.labourservice.utils.OkHttp3Util;
import com.agilefinger.labourservice.utils.ThreadUtils;
import com.agilefinger.labourservice.utils.ToastUtil;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InspectionIllustrationActivity extends BaseActivity {
    @BindView(R.id.stateEdittext)
    EditText stateEdittext;
    @BindView(R.id.activity_inspection_illustration_rtv_save)
    RoundTextView activity_inspection_illustration_rtv_save;
    @BindView(R.id.numText)
    TextView numText;
    @BindView(R.id.layout_toolbar_white_iv_back)
    ImageView layout_toolbar_white_iv_back;
    private String taskId;

    @Override
    public int initLayoutView() {
        return R.layout.activity_inspection_illustration;
    }

    @Override
    public void initView() {
        super.initView();

        taskId = getIntent().getStringExtra("taskid");

        setToolbarWhite("检测说明");
        //获取检测说明
        getState();

        stateEdittext.addTextChangedListener(mTextWatcher);
    }
    //获取检测说明
    private void getState() {
        Map<String, Object> pram=new HashMap<>();
        pram.put("missionID",taskId);
        Log.d("传参",taskId);
        OkHttp3Util.doPost(URL.BASE_URL_4 + "app/get_mission_detail", pram, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Log.d("获取检测说明", string);
                    if (!TextUtils.isEmpty(string)) {
                        Gson gson = new Gson();
                        final StateBean shuoMingBean = gson.fromJson(string, StateBean.class);
                        if (shuoMingBean.getCode() == 0) {
                            if (null!=shuoMingBean.getData()){
                                ThreadUtils.runOnMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!TextUtils.isEmpty(shuoMingBean.getData().getInfo().getExplain())) {
                                            stateEdittext.setText(shuoMingBean.getData().getInfo().getExplain());
                                            stateEdittext.setSelection(stateEdittext.getText().length());
                                        }
                                    }
                                });
                            }
                        }
                    }

                }else {
                    String string = response.body().string();
                    Log.d("获取检测说明", string);
                }
            }
        });
    }

    @Override
    @OnClick({R.id.activity_inspection_illustration_rtv_save, R.id.layout_toolbar_white_iv_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_inspection_illustration_rtv_save:
                Map<String, Object> pram=new HashMap<>();
                pram.put("task_id",taskId);
                pram.put("task_explain",stateEdittext.getText().toString());
                OkHttp3Util.doPost(URL.BASE_URL_4+"app/task_check_explanation", pram, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("保存说明", string);
                        ThreadUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showShort(InspectionIllustrationActivity.this, "保存成功");
                                finish();
                            }
                        });
                    }
                });
                break;
            case R.id.layout_toolbar_white_iv_back:
                finish();
                break;
        }
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = stateEdittext.getSelectionStart();
            editEnd = stateEdittext.getSelectionEnd();
            numText.setText(temp.length() + "/500");
            if (temp.length() > 500) {
                Toast.makeText(InspectionIllustrationActivity.this,
                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
                        .show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                stateEdittext.setText(s);
                stateEdittext.setSelection(tempSelection);
            }
        }
    };
}

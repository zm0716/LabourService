package com.agilefinger.labourservice.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.common.Constants;
import com.agilefinger.labourservice.utils.IntentUtils;
import com.j256.ormlite.stmt.query.In;

public class LaunchActivity extends BaseActivity {

    private CountDownTimer countDownTimer;

    @Override
    public int initLayoutView() {
        return R.layout.activity_launch;
    }


    @Override
    public void initView() {
        super.initView();
        startCountDownTimer();
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(Constants.LAUNCH_DELAY_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }
            @Override
            public void onFinish() {
                if(checkLogin()){
                    IntentUtils.startActivity(LaunchActivity.this, MainActivity.class);
                }else{
                    IntentUtils.startActivity(LaunchActivity.this, LoginPwdActivity.class);
                }
                finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}

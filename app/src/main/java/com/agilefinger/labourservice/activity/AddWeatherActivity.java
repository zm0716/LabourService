package com.agilefinger.labourservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.agilefinger.labourservice.R;
import com.agilefinger.labourservice.base.BaseActivity;
import com.agilefinger.labourservice.bean.WeatherBean;
import com.agilefinger.labourservice.common.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class AddWeatherActivity extends BaseActivity {

    @BindView(R.id.activity_add_weather_et_weather)
    EditText etWeather;
    @BindView(R.id.activity_add_weather_et_temperature_min)
    EditText etTemperatureMin;
    @BindView(R.id.activity_add_weather_et_temperature_max)
    EditText etTemperatureMax;
    @BindView(R.id.activity_add_weather_et_wind_direction)
    EditText etWindDirection;
    @BindView(R.id.activity_add_weather_et_wind_power)
    EditText etWindPower;
    @BindView(R.id.activity_add_weather_et_precipitation)
    EditText etPrecipitation;
    @BindView(R.id.activity_add_weather_et_dampness)
    EditText etDampness;
    private WeatherBean weather;

    @Override
    public int initLayoutView() {
        return R.layout.activity_add_weather;
    }

    @Override
    public void initData() {
        super.initData();
        weather = (WeatherBean) getIntent().getExtras().getSerializable(Constants.EXTRA_DATA);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar("天气", false, false,"");
        if (weather != null) {
            etWeather.setText(weather.getWeather());
            etTemperatureMin.setText(weather.getTemperature_min());
            etTemperatureMax.setText(weather.getTemperature_max());
            etWindDirection.setText(weather.getWind_direction());
            etWindPower.setText(weather.getWind_power());
            etPrecipitation.setText(weather.getPrecipitation());
            etDampness.setText(weather.getDampness());
        }
    }

    @Override
    @OnClick({R.id.activity_add_weather_rtv_ok})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.activity_add_weather_rtv_ok:
                addWeather();
                break;
        }
    }

    private void addWeather() {
        String weather = etWeather.getText().toString().trim();
        String temperatureMin = etTemperatureMin.getText().toString().trim();
        String temperatureMax = etTemperatureMax.getText().toString().trim();
        String windDirection = etWindDirection.getText().toString().trim();
        String windPower = etWindPower.getText().toString().trim();
        String precipitation = etPrecipitation.getText().toString().trim();
        String dampness = etDampness.getText().toString().trim();
        if (TextUtils.isEmpty(weather)) {
            showToast("请填写天气");
            return;
        } else if (TextUtils.isEmpty(temperatureMin)) {
            showToast("请填写温度(最低)");
            return;
        } else if (TextUtils.isEmpty(temperatureMax)) {
            showToast("请填写温度(最高)");
            return;
        } else if (TextUtils.isEmpty(windDirection)) {
            showToast("请填写风向");
            return;
        } else if (TextUtils.isEmpty(windPower)) {
            showToast("请填写风力");
            return;
        } else if (TextUtils.isEmpty(precipitation)) {
            showToast("请填写湿度");
            return;
        } else if (TextUtils.isEmpty(dampness)) {
            showToast("请填写降水量");
            return;
        }
        WeatherBean weatherBean = new WeatherBean();
        weatherBean.setWeather(weather);
        weatherBean.setTemperature_min(temperatureMin);
        weatherBean.setTemperature_max(temperatureMax);
        weatherBean.setWind_direction(windDirection);
        weatherBean.setWind_power(windPower);
        weatherBean.setPrecipitation(precipitation);
        weatherBean.setDampness(dampness);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.EXTRA_DATA, weatherBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}

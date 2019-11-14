package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.ProgressItemBean;
import com.agilefinger.labourservice.bean.WeatherBean;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yunshan on 17/3/22.
 */

public class WeatherDao {

    private String TAG = WeatherDao.class.getSimpleName();
    private DatabaseHelper helper;

    public WeatherDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteWeather() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(WeatherBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }


    public List<WeatherBean> queryWeather(String pid) {
        List<WeatherBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(WeatherBean.class).queryBuilder();
            list = queryBuilder.where().eq("pid", pid).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<WeatherBean> queryWeatherAll() {
        List<WeatherBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(WeatherBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addWeather(final List<WeatherBean> list) {
        try {
            helper.getDao(WeatherBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (WeatherBean weather : list) {
                        helper.getDao(WeatherBean.class).create(weather);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

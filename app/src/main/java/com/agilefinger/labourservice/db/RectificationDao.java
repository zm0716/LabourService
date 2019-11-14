package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.RectificationBean;
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

public class RectificationDao {

    private String TAG = RectificationDao.class.getSimpleName();
    private DatabaseHelper helper;

    public RectificationDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteRectification() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(RectificationBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public int deleteRectification(String id) {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(RectificationBean.class).deleteBuilder();
            deleteBuilder.where().eq("id", id);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<RectificationBean> queryRectification(String id) {
        List<RectificationBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(RectificationBean.class).queryBuilder();
            list = queryBuilder.where().eq("id", id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RectificationBean> queryRectificationAll() {
        List<RectificationBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(RectificationBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addRectification(final List<RectificationBean> list) {
        try {
            helper.getDao(RectificationBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (RectificationBean weather : list) {
                        helper.getDao(RectificationBean.class).create(weather);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addRectification(final RectificationBean rectificationBean) {
        try {
            helper.getDao(RectificationBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(RectificationBean.class).create(rectificationBean);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

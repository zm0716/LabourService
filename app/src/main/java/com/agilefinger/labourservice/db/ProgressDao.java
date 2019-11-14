package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.JsonProgressBean;
import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.RoleBean;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yunshan on 17/3/22.
 */

public class ProgressDao {

    private String TAG = ProgressDao.class.getSimpleName();
    private DatabaseHelper helper;

    public ProgressDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteProgress() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(JsonProgressBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public int deleteProgress(int id) {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(JsonProgressBean.class).deleteBuilder();
            deleteBuilder.where().eq("id", id);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public List<JsonProgressBean> queryProgressAll() {
        List<JsonProgressBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(JsonProgressBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addProgress(final JsonProgressBean jsonProgressBean) {
        try {
            helper.getDao(JsonProgressBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(JsonProgressBean.class).create(jsonProgressBean);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.LoginBean;
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

public class LoginDao {

    private String TAG = LoginDao.class.getSimpleName();
    private DatabaseHelper helper;

    public LoginDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteLogin() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(LoginBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }


    public List<LoginBean> queryLogin(String user_id) {
        List<LoginBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(LoginBean.class).queryBuilder();
            list = queryBuilder.where().eq("user_id", user_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoginBean> queryLoginAll() {
        List<LoginBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(LoginBean.class).queryBuilder();

            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addLogin(final List<LoginBean> list) {
        try {
            helper.getDao(LoginBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (LoginBean login : list) {
                        helper.getDao(LoginBean.class).create(login);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addLogin(final LoginBean login) {
        try {
            helper.getDao(LoginBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(LoginBean.class).create(login);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

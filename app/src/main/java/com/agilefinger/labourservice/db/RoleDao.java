package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

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

public class RoleDao {

    private String TAG = RoleDao.class.getSimpleName();
    private DatabaseHelper helper;

    public RoleDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteRole() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(RoleBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }


    public List<RoleBean> queryRole(String auth) {
        List<RoleBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(RoleBean.class).queryBuilder();
            list = queryBuilder.where().eq("auth", auth).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RoleBean> queryRoleAll() {
        List<RoleBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(RoleBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addRole(final List<RoleBean> list) {
        try {
            helper.getDao(RoleBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (RoleBean baseInfo : list) {
                        helper.getDao(RoleBean.class).create(baseInfo);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.CompanyBean;
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

public class CompanyDao {

    private String TAG = CompanyDao.class.getSimpleName();
    private DatabaseHelper helper;

    public CompanyDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteCompany() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(CompanyBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }


    public List<CompanyBean> queryCompany(String auth) {
        List<CompanyBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(CompanyBean.class).queryBuilder();
            list = queryBuilder.where().eq("company_name", auth).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CompanyBean> queryCompanyAll() {
        List<CompanyBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(CompanyBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCompany(final List<CompanyBean> list) {
        try {
            helper.getDao(CompanyBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (CompanyBean baseInfo : list) {
                        helper.getDao(CompanyBean.class).create(baseInfo);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

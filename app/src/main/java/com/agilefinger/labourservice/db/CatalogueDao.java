package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.CatalogueBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yunshan on 17/3/22.
 */

public class CatalogueDao {

    private String TAG = CatalogueDao.class.getSimpleName();
    private DatabaseHelper helper;

    public CatalogueDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteCatalogue() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(CatalogueBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }

    public int deleteCatalogue(String id) {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(CatalogueBean.class).deleteBuilder();
            deleteBuilder.where().eq("tci_id", id);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public List<CatalogueBean> queryCatalogue(String id) {
        List<CatalogueBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(CatalogueBean.class).queryBuilder();
            list = queryBuilder.where().eq("tct_p_id", id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CatalogueBean> queryCatalogueAll() {
        List<CatalogueBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(CatalogueBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCatalogue(final List<CatalogueBean> list) {
        try {
            helper.getDao(CatalogueBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (CatalogueBean catalogue : list) {
                        helper.getDao(CatalogueBean.class).create(catalogue);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addCatalogueBean(final CatalogueBean catalogueBean) {
        try {
            helper.getDao(CatalogueBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(CatalogueBean.class).create(catalogueBean);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

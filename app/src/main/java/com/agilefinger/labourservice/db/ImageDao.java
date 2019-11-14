package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.ImageDaoBean;
import com.agilefinger.labourservice.bean.JsonProgressBean;
import com.agilefinger.labourservice.bean.PicBean;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ImageDao {

    private String TAG = ImageDao.class.getSimpleName();
    private DatabaseHelper helper;

    public ImageDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteProgress() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(PicBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public int deleteProgress(int id) {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(PicBean.class).deleteBuilder();
            deleteBuilder.where().eq("id", id);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public List<PicBean> queryImageAll() {
        List<PicBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(PicBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addImage(final PicBean imageDaoBean) {
        try {
            helper.getDao(PicBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(PicBean.class).create(imageDaoBean);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

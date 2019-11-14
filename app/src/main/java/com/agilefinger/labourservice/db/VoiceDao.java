package com.agilefinger.labourservice.db;

import android.content.Context;
import android.util.Log;

import com.agilefinger.labourservice.bean.ProgressBean;
import com.agilefinger.labourservice.bean.RectificationBean;
import com.agilefinger.labourservice.bean.VoiceBean;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yunshan on 17/3/22.
 */

public class VoiceDao {

    private String TAG = VoiceDao.class.getSimpleName();
    private DatabaseHelper helper;

    public VoiceDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
    }

    public int deleteVoice() {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(VoiceBean.class).deleteBuilder();
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }
    public int deleteVoice(String zid) {
        try {
            DeleteBuilder deleteBuilder = helper.getDao(VoiceBean.class).deleteBuilder();
            deleteBuilder.where().eq("zid", zid);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            return 0;
        }
    }


    public List<VoiceBean> queryVoice(String zid) {
        List<VoiceBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(VoiceBean.class).queryBuilder();
            list = queryBuilder.where().eq("zid", zid).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<VoiceBean> queryVoiceAll() {
        List<VoiceBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = helper.getDao(VoiceBean.class).queryBuilder();
            list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addVoice(final List<VoiceBean> list) {
        try {
            helper.getDao(VoiceBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (VoiceBean weather : list) {
                        helper.getDao(VoiceBean.class).create(weather);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void addVoice(final VoiceBean voiceBean) {
        try {
            helper.getDao(VoiceBean.class).callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    helper.getDao(VoiceBean.class).create(voiceBean);
                    return null;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

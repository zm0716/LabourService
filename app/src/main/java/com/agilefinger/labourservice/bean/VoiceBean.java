package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 86251 on 2019/6/4.
 */
@DatabaseTable(tableName = "Voice")
public class VoiceBean {
    @DatabaseField(useGetSet = true, columnName = "zid")
    private String zid;
    @DatabaseField(useGetSet = true, columnName = "path")
    private String path;
    private String oPath;
    @DatabaseField(useGetSet = true, columnName = "len")
    private float len;

    public VoiceBean() {
    }

    public VoiceBean(float len, String path, String oPath) {
        this.len = len;
        this.path = path;
        this.oPath = oPath;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getoPath() {
        return oPath;
    }

    public void setoPath(String oPath) {
        this.oPath = oPath;
    }

    public float getLen() {
        return len;
    }

    public void setLen(float len) {
        this.len = len;
    }
}

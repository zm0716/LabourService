package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "PicBean")
public class PicBean {
    @DatabaseField(useGetSet = true, columnName = "id")
    private String pi_id;
    @DatabaseField(useGetSet = true, columnName = "pi_url")
    private String pi_url;
    @DatabaseField(useGetSet = true, columnName = "pi_watermark_url")
    private String pi_watermark_url;
    @DatabaseField(useGetSet = true, columnName = "pi_tastId")
    private String pi_tastId;

//    public PicBean(String pi_id, String pi_url, String pi_watermark_url) {
//        this.pi_id = pi_id;
//        this.pi_url = pi_url;
//        this.pi_watermark_url = pi_watermark_url;
//    }


    public String getPi_tastId() {
        return pi_tastId;
    }

    public void setPi_tastId(String pi_tastId) {
        this.pi_tastId = pi_tastId;
    }

    @Override
    public String toString() {
        return "PicBean{" +
                "pi_id='" + pi_id + '\'' +
                ", pi_url='" + pi_url + '\'' +
                ", pi_watermark_url='" + pi_watermark_url + '\'' +
                '}';
    }

    public String getPi_id() {
        return pi_id;
    }

    public void setPi_id(String pi_id) {
        this.pi_id = pi_id;
    }

    public String getPi_url() {
        return pi_url;
    }

    public void setPi_url(String pi_url) {
        this.pi_url = pi_url;
    }

    public String getPi_watermark_url() {
        return pi_watermark_url;
    }

    public void setPi_watermark_url(String pi_watermark_url) {
        this.pi_watermark_url = pi_watermark_url;
    }
}

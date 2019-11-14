package com.agilefinger.labourservice.bean;

public class ItemBean {

    /**
     * mci_id : 0D8E642EAA1B6B4647591E1321AA89D7
     * mci_mct_id : 2630142B30C18E060691035F63B32C85
     * mci_name : 检测项编号X068
     * mci_no : 16
     * mci_min_point : 0
     * p_count : 0
     */

    private String mci_id;
    private String mci_mct_id;
    private String mci_name;
    private String mci_no;
    private long mci_min_point;
    private long p_count;
    private String zong;
    private String mct_is_batch;
    private String path;

    private boolean isxuan;

    public boolean isIsxuan() {
        return isxuan;
    }

    public void setIsxuan(boolean isxuan) {
        this.isxuan = isxuan;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMct_is_batch() {
        return mct_is_batch;
    }

    public void setMct_is_batch(String mct_is_batch) {
        this.mct_is_batch = mct_is_batch;
    }

    public String getZong() {
        return zong;
    }

    public void setZong(String zong) {
        this.zong = zong;
    }

    public String getMci_id() {
        return mci_id;
    }

    public void setMci_id(String mci_id) {
        this.mci_id = mci_id;
    }

    public String getMci_mct_id() {
        return mci_mct_id;
    }

    public void setMci_mct_id(String mci_mct_id) {
        this.mci_mct_id = mci_mct_id;
    }

    public String getMci_name() {
        return mci_name;
    }

    public void setMci_name(String mci_name) {
        this.mci_name = mci_name;
    }

    public String getMci_no() {
        return mci_no;
    }

    public void setMci_no(String mci_no) {
        this.mci_no = mci_no;
    }

    public long getMci_min_point() {
        return mci_min_point;
    }

    public void setMci_min_point(long mci_min_point) {
        this.mci_min_point = mci_min_point;
    }

    public long getP_count() {
        return p_count;
    }

    public void setP_count(long p_count) {
        this.p_count = p_count;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "mci_id='" + mci_id + '\'' +
                ", mci_mct_id='" + mci_mct_id + '\'' +
                ", mci_name='" + mci_name + '\'' +
                ", mci_no='" + mci_no + '\'' +
                ", mci_min_point=" + mci_min_point +
                ", p_count=" + p_count +
                '}';
    }
}

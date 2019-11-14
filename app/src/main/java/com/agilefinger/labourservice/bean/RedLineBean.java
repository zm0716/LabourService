package com.agilefinger.labourservice.bean;

public class RedLineBean {


    /**
     * mci_name : 配重上锁
     * mci_no : TC00501001
     * mci_number : 1
     * rate : 50%
     */

    private String mci_name;
    private String mci_no;
    private int mci_number;
    private String rate;

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

    public int getMci_number() {
        return mci_number;
    }

    public void setMci_number(int mci_number) {
        this.mci_number = mci_number;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

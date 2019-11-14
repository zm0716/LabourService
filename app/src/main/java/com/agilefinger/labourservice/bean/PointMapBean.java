package com.agilefinger.labourservice.bean;

/**
 * Created by Ocean on 2019/8/17.
 */

public class PointMapBean {
    private String result;
    private String isPass;

    public PointMapBean(String result, String isPass) {
        this.result = result;
        this.isPass = isPass;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }
}

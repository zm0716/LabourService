package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/6.
 */

public class WorkBean {
    private String rectification;
    private String acceptance;
    private String completed;
    private String rejected;

    public String getRectification() {
        return rectification;
    }

    public void setRectification(String rectification) {
        this.rectification = rectification;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }
}

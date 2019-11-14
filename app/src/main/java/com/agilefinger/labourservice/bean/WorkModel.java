package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/6.
 */

public class WorkModel {
    private WorkBean responsibility;
    private WorkBean dispatch;

    public WorkBean getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(WorkBean responsibility) {
        this.responsibility = responsibility;
    }

    public WorkBean getDispatch() {
        return dispatch;
    }

    public void setDispatch(WorkBean dispatch) {
        this.dispatch = dispatch;
    }
}

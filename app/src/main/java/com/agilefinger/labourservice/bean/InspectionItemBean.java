package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/7/12.
 */

public class InspectionItemBean {
    private int state;//1 合格 ，2 不合格 ，3 未设置 ，4 空的
    private boolean isCheck;

    public InspectionItemBean(int state, boolean isCheck) {
        this.state = state;
        this.isCheck = isCheck;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

package com.agilefinger.labourservice.bean;

public class CheckBean {
    private Boolean one;
    private Boolean two;

    public CheckBean(Boolean one, Boolean two) {
        this.one = one;
        this.two = two;
    }

    public Boolean getOne() {
        return one;
    }

    public void setOne(Boolean one) {
        this.one = one;
    }

    public Boolean getTwo() {
        return two;
    }

    public void setTwo(Boolean two) {
        this.two = two;
    }
}

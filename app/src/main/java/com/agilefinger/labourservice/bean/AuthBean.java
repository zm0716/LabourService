package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/5.
 */

public class AuthBean {
    private String status;
    private String correct;
    private String creater;
    private String group;
    private String correct_in_group;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCorrect_in_group() {
        return correct_in_group;
    }

    public void setCorrect_in_group(String correct_in_group) {
        this.correct_in_group = correct_in_group;
    }
}

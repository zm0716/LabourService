package com.agilefinger.labourservice.bean;

public class NoticeMultipleBean {

    public static final int TYPE_CORRECT = 1;
    public static final int TYPE_PROGRESS = 2;

    public int type;
    public String content;

    public NoticeMultipleBean(int type) {
        this.type = type;
    }

    public NoticeMultipleBean(int type, String content) {
        this.type = type;
        this.content = content;
    }
}

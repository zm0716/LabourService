package com.agilefinger.labourservice.bean;

import java.util.List;

/**
 * Created by 86251 on 2019/6/5.
 */

public class RectificationItemBean {
    private String id;
    private String name1;
    private String name2;
    private String operate;
    private String portrait;
    private String reply;
    private List<String> images;
    private List<String> voice;
    private List<String> voice_second;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVoice() {
        return voice;
    }

    public void setVoice(List<String> voice) {
        this.voice = voice;
    }

    public List<String> getVoice_second() {
        return voice_second;
    }

    public void setVoice_second(List<String> voice_second) {
        this.voice_second = voice_second;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

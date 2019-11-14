package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/10.
 */

public class AddProgressContentBean {
    private String buildId;//楼号
    private String sggx;//施工工序
    private String wcl;//完成量
    private String wcRate;//完成比例
    private String sgbz;//施工班组
    private String num;//人数

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getSggx() {
        return sggx;
    }

    public void setSggx(String sggx) {
        this.sggx = sggx;
    }

    public String getWcl() {
        return wcl;
    }

    public void setWcl(String wcl) {
        this.wcl = wcl;
    }

    public String getWcRate() {
        return wcRate;
    }

    public void setWcRate(String wcRate) {
        this.wcRate = wcRate;
    }

    public String getSgbz() {
        return sgbz;
    }

    public void setSgbz(String sgbz) {
        this.sgbz = sgbz;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

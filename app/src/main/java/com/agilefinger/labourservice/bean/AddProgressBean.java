package com.agilefinger.labourservice.bean;

import java.util.List;

/**
 * Created by 86251 on 2019/6/10.
 */

public class AddProgressBean {
    private String projectId;
    private String projectName;
    private String buildId;
    private WeatherBean weatherBean;
    private String images;
    private String remark;
    private List<AddProgressContentBean> content;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public WeatherBean getWeatherBean() {
        return weatherBean;
    }

    public void setWeatherBean(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<AddProgressContentBean> getContent() {
        return content;
    }

    public void setContent(List<AddProgressContentBean> content) {
        this.content = content;
    }
}

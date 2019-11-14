package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86251 on 2019/6/5.
 */
@DatabaseTable(tableName = "Progress")
public class ProgressBean implements Serializable {
    @DatabaseField(useGetSet = true, columnName = "id")
    private String id;
    @DatabaseField(useGetSet = true, columnName = "username")
    private String username;
    @DatabaseField(useGetSet = true, columnName = "project_id")
    private String project_id;//项目ID
    @DatabaseField(useGetSet = true, columnName = "project_name")
    private String project_name;//项目名称
    @DatabaseField(useGetSet = true, columnName = "date")
    private String date;//日期
    @DatabaseField(useGetSet = true, columnName = "remark")
    private String remark;//备注
    @DatabaseField(useGetSet = true, columnName = "image")
    private String image;//,,,,, 图片
    private WeatherBean weather;
    private List<ProgressBuildingBean> content;
    private String description;
    private String date_ymd;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public void setWeather(WeatherBean weather) {
        this.weather = weather;
    }

    public List<ProgressBuildingBean> getContent() {
        return content;
    }

    public void setContent(List<ProgressBuildingBean> content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_ymd() {
        return date_ymd;
    }

    public void setDate_ymd(String date_ymd) {
        this.date_ymd = date_ymd;
    }
}

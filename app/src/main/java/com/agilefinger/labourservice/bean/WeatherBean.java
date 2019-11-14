package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 86251 on 2019/6/5.
 */
@DatabaseTable(tableName = "Weather")
public class WeatherBean implements Serializable{
    @DatabaseField(useGetSet = true, generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true, columnName = "pid")
    private String pid;
    @DatabaseField(useGetSet = true, columnName = "weather")
    private String weather;
    @DatabaseField(useGetSet = true, columnName = "temperature_max")
    private String temperature_max;//最高气温
    @DatabaseField(useGetSet = true, columnName = "temperature_min")
    private String temperature_min;//最低气温
    @DatabaseField(useGetSet = true, columnName = "wind_direction")
    private String wind_direction;//风向
    @DatabaseField(useGetSet = true, columnName = "wind_power")
    private String wind_power;//风力
    @DatabaseField(useGetSet = true, columnName = "precipitation")
    private String precipitation;//降水量
    @DatabaseField(useGetSet = true, columnName = "dampness")
    private String dampness;//湿度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(String temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(String temperature_min) {
        this.temperature_min = temperature_min;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_power() {
        return wind_power;
    }

    public void setWind_power(String wind_power) {
        this.wind_power = wind_power;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getDampness() {
        return dampness;
    }

    public void setDampness(String dampness) {
        this.dampness = dampness;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}

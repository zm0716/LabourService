package com.agilefinger.labourservice.bean;

import android.media.Image;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

@DatabaseTable(tableName = "ImageDao")
public class ImageDaoBean {
    @DatabaseField(useGetSet = true, columnName = "id")
    private String tci_id;
    @DatabaseField(useGetSet = true, columnName = "tab_Image")
    private String Image;
    @DatabaseField(useGetSet = true, columnName = "tab_shiYinIMage")
    private String shiYinIMage;

    public String getShiYinIMage() {
        return shiYinIMage;
    }

    public void setShiYinIMage(String shiYinIMage) {
        this.shiYinIMage = shiYinIMage;
    }

    public String getTci_id() {
        return tci_id;
    }

    public void setTci_id(String tci_id) {
        this.tci_id = tci_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

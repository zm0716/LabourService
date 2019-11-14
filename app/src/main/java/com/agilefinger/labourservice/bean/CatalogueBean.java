package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 86251 on 2019/7/26.
 */
@DatabaseTable(tableName = "Catalogue")
public class CatalogueBean {
    private String id;
    private String name;
    @DatabaseField(useGetSet = true, columnName = "tci_id")
    private String tci_id;
    @DatabaseField(useGetSet = true, columnName = "tci_tct_id")
    private String tci_tct_id;
    @DatabaseField(useGetSet = true, columnName = "tci_name")
    private String tci_name;
    @DatabaseField(useGetSet = true, columnName = "tci_front_show")
    private String tci_front_show;
    @DatabaseField(useGetSet = true, columnName = "tct_name")
    private String tct_name;
    @DatabaseField(useGetSet = true, columnName = "tct_front_show")
    private String tct_front_show;
    @DatabaseField(useGetSet = true, columnName = "tct_p_id")
    private String tct_p_id;
    @DatabaseField(useGetSet = true, columnName = "tct_id")
    private String tct_id;

    public String getTci_name() {
        return tci_name;
    }

    public void setTci_name(String tci_name) {
        this.tci_name = tci_name;
    }

    public String getTci_front_show() {
        return tci_front_show;
    }

    public void setTci_front_show(String tci_front_show) {
        this.tci_front_show = tci_front_show;
    }

    public String getTct_name() {
        return tct_name;
    }

    public void setTct_name(String tct_name) {
        this.tct_name = tct_name;
    }

    public String getTct_front_show() {
        return tct_front_show;
    }

    public void setTct_front_show(String tct_front_show) {
        this.tct_front_show = tct_front_show;
    }

    public String getTct_p_id() {
        return tct_p_id;
    }

    public void setTct_p_id(String tct_p_id) {
        this.tct_p_id = tct_p_id;
    }

    public String getTct_id() {
        return tct_id;
    }

    public void setTct_id(String tct_id) {
        this.tct_id = tct_id;
    }

    public CatalogueBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTci_id() {
        return tci_id;
    }

    public void setTci_id(String tci_id) {
        this.tci_id = tci_id;
    }

    public String getTci_tct_id() {
        return tci_tct_id;
    }

    public void setTci_tct_id(String tci_tct_id) {
        this.tci_tct_id = tci_tct_id;
    }

}

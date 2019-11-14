package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 86251 on 2019/6/5.
 */
@DatabaseTable(tableName = "Role")
public class RoleBean {
    @DatabaseField(useGetSet = true, columnName = "auth")
    private String auth;
    @DatabaseField(useGetSet = true, columnName = "title")
    private String title;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

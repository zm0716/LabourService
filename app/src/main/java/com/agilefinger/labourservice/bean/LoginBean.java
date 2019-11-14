package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by 86251 on 2019/6/5.
 */
@DatabaseTable(tableName = "Login")
public class LoginBean {
    @DatabaseField(useGetSet = true, columnName = "user_id")
    private String user_id;
    @DatabaseField(useGetSet = true, columnName = "login_token")
    private String login_token;
    @DatabaseField(useGetSet = true, columnName = "user_account")
    private String user_account;
    @DatabaseField(useGetSet = true, columnName = "user_name")
    private String user_name;
    private List<RoleBean> role_code;
    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public List<RoleBean> getRole_code() {
        return role_code;
    }

    public void setRole_code(List<RoleBean> role_code) {
        this.role_code = role_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}

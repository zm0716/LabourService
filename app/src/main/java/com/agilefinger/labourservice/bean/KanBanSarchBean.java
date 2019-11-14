package com.agilefinger.labourservice.bean;

import java.util.List;

public class KanBanSarchBean {

    /**
     * company_name : 中恒营造华东区
     * manager_name : 蒋旭
     * manager_user_id : 16489
     * org_id : 377
     * project_address : 合肥市南京路与华山路交口
     * project_en_name : ["涂料","保温"]
     * project_id : 957
     * project_title : 合肥新城樾府都会项目
     * project_work_start : 2019-07-01 00:00:00
     */

    private String company_name;
    private String manager_name;
    private int manager_user_id;
    private int org_id;
    private String project_address;
    private int project_id;
    private String project_title;
    private String project_work_start;
    private List<String> project_en_name;
    public String getCustom_state_name() {
        return custom_state_name;
    }

    public void setCustom_state_name(String custom_state_name) {
        this.custom_state_name = custom_state_name;
    }

    private String custom_state_name;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public int getManager_user_id() {
        return manager_user_id;
    }

    public void setManager_user_id(int manager_user_id) {
        this.manager_user_id = manager_user_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getProject_address() {
        return project_address;
    }

    public void setProject_address(String project_address) {
        this.project_address = project_address;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_work_start() {
        return project_work_start;
    }

    public void setProject_work_start(String project_work_start) {
        this.project_work_start = project_work_start;
    }

    public List<String> getProject_en_name() {
        return project_en_name;
    }

    public void setProject_en_name(List<String> project_en_name) {
        this.project_en_name = project_en_name;
    }
}

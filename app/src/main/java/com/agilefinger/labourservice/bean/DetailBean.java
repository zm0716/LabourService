package com.agilefinger.labourservice.bean;

import java.util.List;

public class DetailBean {

    /**
     * mi_id : 068A11E0D2FD7BE1C334CFF058A59D32
     * mi_no : 1565599231
     * mi_p_id :
     * mi_c_id : 1
     * mi_dev_id : 1
     * mi_creator : 32
     * mi_createtime : 2019-08-12 16:40:00
     * mi_endtime : 2009-08-12 23:59:59
     * mi_inspector : 1
     * mi_ti_id : 1
     * mi_type : check
     * mi_times : 2
     * mi_explain :
     * mi_can_batch : y
     * mi_do_status : wait
     * mi_stop_reason : null
     * mi_status : normal
     * rule_list : []
     * project_name : 龙湖天街翻新
     * project_no : P2019030111XC022
     * project_type : 保温、涂料
     * project_address : 北京市通州区常营天街23号
     * project_begin : 2018年5月18日
     * project_predict : 60天
     * project_area : 1298300m²
     * project_bmc : 北京器材建设
     * project_developers : 龙湖地产
     * project_connect_user : 孙建国
     * project_connect_phone : 13998769823
     * inspector_name : 王大锤
     * template_name : 测试通用模版
     * project_sign: "吃点饭个人钛合金",
     * status : 未启动
     */

    private String mi_id;
    private String mi_no;
    private String mi_p_id;
    private String mi_c_id;
    private String mi_dev_id;
    private String mi_creator;
    private String mi_createtime;
    private String mi_endtime;
    private String mi_inspector;
    private String mi_ti_id;
    private String mi_type;
    private int mi_times;
    private String mi_explain;
    private String mi_can_batch;
    private String mi_do_status;
    private Object mi_stop_reason;
    private String mi_status;
    private String project_name;
    private String project_no;
    private String project_type;
    private String project_address;
    private String project_begin;
    private String project_predict;
    private String project_area;
    private String project_art;

    private String project_bmc;
    private String project_developers;
    private String project_connect_user;
    private String project_connect_phone;
    private String inspector_name;
    private String template_name;
    private String project_sign;
    private boolean has_position;

    public String getProject_art() {
        return project_art;
    }

    public void setProject_art(String project_art) {
        this.project_art = project_art;
    }

    public boolean isHas_position() {
        return has_position;
    }

    public void setHas_position(boolean has_position) {
        this.has_position = has_position;
    }

    public String getProject_sign() {
        return project_sign;
    }

    public void setProject_sign(String project_sign) {
        this.project_sign = project_sign;
    }

    private String status;
    private String mi_no_no;

    private String resultPoint;
    private String resultNo;
    private String resultTime;
    private String resultUser;


    private List<?> rule_list;

    public String getMi_no_no() {
        return mi_no_no;
    }

    public void setMi_no_no(String mi_no_no) {
        this.mi_no_no = mi_no_no;
    }

    public String getResultPoint() {
        return resultPoint;
    }

    public void setResultPoint(String resultPoint) {
        this.resultPoint = resultPoint;
    }

    public String getResultNo() {
        return resultNo;
    }

    public void setResultNo(String resultNo) {
        this.resultNo = resultNo;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getResultUser() {
        return resultUser;
    }

    public void setResultUser(String resultUser) {
        this.resultUser = resultUser;
    }

    public String getMi_id() {
        return mi_id;
    }

    public void setMi_id(String mi_id) {
        this.mi_id = mi_id;
    }

    public String getMi_no() {
        return mi_no;
    }

    public void setMi_no(String mi_no) {
        this.mi_no = mi_no;
    }

    public String getMi_p_id() {
        return mi_p_id;
    }

    public void setMi_p_id(String mi_p_id) {
        this.mi_p_id = mi_p_id;
    }

    public String getMi_c_id() {
        return mi_c_id;
    }

    public void setMi_c_id(String mi_c_id) {
        this.mi_c_id = mi_c_id;
    }

    public String getMi_dev_id() {
        return mi_dev_id;
    }

    public void setMi_dev_id(String mi_dev_id) {
        this.mi_dev_id = mi_dev_id;
    }

    public String getMi_creator() {
        return mi_creator;
    }

    public void setMi_creator(String mi_creator) {
        this.mi_creator = mi_creator;
    }

    public String getMi_createtime() {
        return mi_createtime;
    }

    public void setMi_createtime(String mi_createtime) {
        this.mi_createtime = mi_createtime;
    }

    public String getMi_endtime() {
        return mi_endtime;
    }

    public void setMi_endtime(String mi_endtime) {
        this.mi_endtime = mi_endtime;
    }

    public String getMi_inspector() {
        return mi_inspector;
    }

    public void setMi_inspector(String mi_inspector) {
        this.mi_inspector = mi_inspector;
    }

    public String getMi_ti_id() {
        return mi_ti_id;
    }

    public void setMi_ti_id(String mi_ti_id) {
        this.mi_ti_id = mi_ti_id;
    }

    public String getMi_type() {
        return mi_type;
    }

    public void setMi_type(String mi_type) {
        this.mi_type = mi_type;
    }

    public int getMi_times() {
        return mi_times;
    }

    public void setMi_times(int mi_times) {
        this.mi_times = mi_times;
    }

    public String getMi_explain() {
        return mi_explain;
    }

    public void setMi_explain(String mi_explain) {
        this.mi_explain = mi_explain;
    }

    public String getMi_can_batch() {
        return mi_can_batch;
    }

    public void setMi_can_batch(String mi_can_batch) {
        this.mi_can_batch = mi_can_batch;
    }

    public String getMi_do_status() {
        return mi_do_status;
    }

    public void setMi_do_status(String mi_do_status) {
        this.mi_do_status = mi_do_status;
    }

    public Object getMi_stop_reason() {
        return mi_stop_reason;
    }

    public void setMi_stop_reason(Object mi_stop_reason) {
        this.mi_stop_reason = mi_stop_reason;
    }

    public String getMi_status() {
        return mi_status;
    }

    public void setMi_status(String mi_status) {
        this.mi_status = mi_status;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getProject_address() {
        return project_address;
    }

    public void setProject_address(String project_address) {
        this.project_address = project_address;
    }

    public String getProject_begin() {
        return project_begin;
    }

    public void setProject_begin(String project_begin) {
        this.project_begin = project_begin;
    }

    public String getProject_predict() {
        return project_predict;
    }

    public void setProject_predict(String project_predict) {
        this.project_predict = project_predict;
    }

    public String getProject_area() {
        return project_area;
    }

    public void setProject_area(String project_area) {
        this.project_area = project_area;
    }

    public String getProject_bmc() {
        return project_bmc;
    }

    public void setProject_bmc(String project_bmc) {
        this.project_bmc = project_bmc;
    }

    public String getProject_developers() {
        return project_developers;
    }

    public void setProject_developers(String project_developers) {
        this.project_developers = project_developers;
    }

    public String getProject_connect_user() {
        return project_connect_user;
    }

    public void setProject_connect_user(String project_connect_user) {
        this.project_connect_user = project_connect_user;
    }

    public String getProject_connect_phone() {
        return project_connect_phone;
    }

    public void setProject_connect_phone(String project_connect_phone) {
        this.project_connect_phone = project_connect_phone;
    }

    public String getInspector_name() {
        return inspector_name;
    }

    public void setInspector_name(String inspector_name) {
        this.inspector_name = inspector_name;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<?> getRule_list() {
        return rule_list;
    }

    public void setRule_list(List<?> rule_list) {
        this.rule_list = rule_list;
    }

}

package com.agilefinger.labourservice.bean;

import java.util.List;

public class CompanyListBean {

    /**
     * code : 0
     * data : [{"id":"21","name":"54545"},{"id":"20","name":"我忘了的开发商"},{"id":"19","name":"77777"},{"id":"18","name":"123"},{"id":"17","name":"新增开放商2019.2.16"},{"id":"16","name":"123456765432"},{"id":"15","name":"12345"},{"id":"14","name":"水电费规划局"},{"id":"13","name":"阿斯顿法国和健康"},{"id":"12","name":"22"}]
     * msg : 成功！
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 21
         * name : 54545
         */

        private String id;
        private String name;
        private boolean Check;

        public boolean isCheck() {
            return Check;
        }

        public void setCheck(boolean check) {
            Check = check;
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
    }
}

package com.agilefinger.labourservice.bean;

import java.util.List;

public class TeamGroupBean {

    /**
     * code : 0
     * message : 获取成功
     * data : [{"user_name":"","type":2,"join_time":null}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_name :
         * type : 2
         * join_time : null
         */

        private String user_name;
        private int type;
        private Object join_time;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getJoin_time() {
            return join_time;
        }

        public void setJoin_time(Object join_time) {
            this.join_time = join_time;
        }
    }
}

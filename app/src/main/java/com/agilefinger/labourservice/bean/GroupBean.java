package com.agilefinger.labourservice.bean;

import java.util.List;

public class GroupBean {

    /**
     * code : 0
     * data : [{"group_id":1,"group_name":"小席席"},{"group_id":2,"group_name":"小明童鞋"}]
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
         * group_id : 1
         * group_name : 小席席
         */

        private int group_id;
        private String group_name;

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }
    }
}

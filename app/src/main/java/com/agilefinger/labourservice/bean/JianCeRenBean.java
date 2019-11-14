package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.List;

public class JianCeRenBean implements  Serializable{

    /**
     * code : 0
     * data : [{"id":2551,"name":"巡检APP新增自定义角色用户"},{"id":2507,"name":"新巡检APP租户用户"},{"id":2506,"name":"巡检分公司用户"},{"id":2503,"name":"新巡检APP部门内用户"}]
     * message : 成功！
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

    public static class DataBean implements Serializable {
        /**
         * id : 2551
         * name : 巡检APP新增自定义角色用户
         */
        private boolean isAdd = false;
        private String id;
        private String name;
        private boolean check;

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
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

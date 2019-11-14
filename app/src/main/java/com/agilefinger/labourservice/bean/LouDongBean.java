package com.agilefinger.labourservice.bean;

import java.util.List;

public class LouDongBean {


    /**
     * code : 0
     * message : 获取成功
     * data : [{"b_floor":4,"f_floor":4,"building_num":"4","inside_name":"东立面、南立面、西立面、北立面"}]
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
         * b_floor : 4
         * f_floor : 4
         * building_num : 4
         * inside_name : 东立面、南立面、西立面、北立面
         */

        private int b_floor;
        private int f_floor;
        private String building_num;
        private String inside_name;

        public int getB_floor() {
            return b_floor;
        }

        public void setB_floor(int b_floor) {
            this.b_floor = b_floor;
        }

        public int getF_floor() {
            return f_floor;
        }

        public void setF_floor(int f_floor) {
            this.f_floor = f_floor;
        }

        public String getBuilding_num() {
            return building_num;
        }

        public void setBuilding_num(String building_num) {
            this.building_num = building_num;
        }

        public String getInside_name() {
            return inside_name;
        }

        public void setInside_name(String inside_name) {
            this.inside_name = inside_name;
        }
    }
}

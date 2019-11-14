package com.agilefinger.labourservice.bean;

public class ImgBean {

    /**
     * code : 0
     * data : {"pi_id":"DB3C2D6F990F8A823A758F8953B5E575","pi_creator":"32","pi_p_id":"1","pi_url":"/Public/Uploads/img/2fb147386a54fcde61e416cc8ff87259.jpg","pi_watermark_url":"/Public/Uploads/img/92c28a45cc306580086ad81fdc0188a7.jpg","pi_longitude":"112.16","pi_latitude":"39.23","pi_height":"12","pi_address":"天津","pi_createtime":"2019-08-15 13:22:14"}
     * msg : 成功！
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * pi_id : DB3C2D6F990F8A823A758F8953B5E575
         * pi_creator : 32
         * pi_p_id : 1
         * pi_url : /Public/Uploads/img/2fb147386a54fcde61e416cc8ff87259.jpg
         * pi_watermark_url : /Public/Uploads/img/92c28a45cc306580086ad81fdc0188a7.jpg
         * pi_longitude : 112.16
         * pi_latitude : 39.23
         * pi_height : 12
         * pi_address : 天津
         * pi_createtime : 2019-08-15 13:22:14
         */

        private String pi_id;
        private String pi_creator;
        private String pi_p_id;
        private String pi_url;
        private String pi_watermark_url;
        private String pi_longitude;
        private String pi_latitude;
        private String pi_height;
        private String pi_address;
        private String pi_createtime;

        public String getPi_id() {
            return pi_id;
        }

        public void setPi_id(String pi_id) {
            this.pi_id = pi_id;
        }

        public String getPi_creator() {
            return pi_creator;
        }

        public void setPi_creator(String pi_creator) {
            this.pi_creator = pi_creator;
        }

        public String getPi_p_id() {
            return pi_p_id;
        }

        public void setPi_p_id(String pi_p_id) {
            this.pi_p_id = pi_p_id;
        }

        public String getPi_url() {
            return pi_url;
        }

        public void setPi_url(String pi_url) {
            this.pi_url = pi_url;
        }

        public String getPi_watermark_url() {
            return pi_watermark_url;
        }

        public void setPi_watermark_url(String pi_watermark_url) {
            this.pi_watermark_url = pi_watermark_url;
        }

        public String getPi_longitude() {
            return pi_longitude;
        }

        public void setPi_longitude(String pi_longitude) {
            this.pi_longitude = pi_longitude;
        }

        public String getPi_latitude() {
            return pi_latitude;
        }

        public void setPi_latitude(String pi_latitude) {
            this.pi_latitude = pi_latitude;
        }

        public String getPi_height() {
            return pi_height;
        }

        public void setPi_height(String pi_height) {
            this.pi_height = pi_height;
        }

        public String getPi_address() {
            return pi_address;
        }

        public void setPi_address(String pi_address) {
            this.pi_address = pi_address;
        }

        public String getPi_createtime() {
            return pi_createtime;
        }

        public void setPi_createtime(String pi_createtime) {
            this.pi_createtime = pi_createtime;
        }
    }
}

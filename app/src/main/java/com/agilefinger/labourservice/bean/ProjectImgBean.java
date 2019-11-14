package com.agilefinger.labourservice.bean;

import java.util.List;

public class ProjectImgBean {

    /**
     * code : 0
     * message : 加载成功
     * data : [{"img_url":"/Public/Uploads/img/f27078c5d78a0c94751ba8f88f1cea81.jpg","time":"2019-08-16 13:59:55","desc":"整改派单"},{"img_url":"/Public/Uploads/img/f27078c5d78a0c94751ba8f88f1cea81.jpg","time":"2019-08-14 13:59:30","desc":"整改派单"},{"img_url":"/Public/Uploads/img/f27078c5d78a0c94751ba8f88f1cea81.jpg","time":"2019-08-13 14:59:30","desc":"整改派单"},{"img_url":"/Public/Uploads/img/1.jpg","time":"2019-08-13 13:59:57","desc":"整改派单"},{"img_url":"/Public/Uploads/audio/ee547e0647e70c8ae82fb6d070a0e6f9.WAV","time":"2019-08-13 13:59:30","desc":"整改派单"},{"img_url":"/Public/Uploads/audio/ee547e0647e70c8ae82fb6d070a0e6f9.WAV","time":"2019-08-13 13:59:30","desc":"整改派单"},{"img_url":"/Public/Uploads/audio/ee547e0647e70c8ae82fb6d070a0e6f9.WAV","time":"2019-08-13 13:59:30","desc":"整改派单"},{"img_url":"/Public/Uploads/img/1.jpg","time":"2019-08-13 13:59:30","desc":"整改派单"}]
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
         * img_url : /Public/Uploads/img/f27078c5d78a0c94751ba8f88f1cea81.jpg
         * time : 2019-08-16 13:59:55
         * desc : 整改派单
         */

        private String img_url;
        private String time;
        private String desc;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

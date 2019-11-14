package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.List;

public class MoreImgBean implements Serializable{


    /**
     * code : 0
     * data : {"list":[{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/ee3b6ebd91cb71d4e9df40400b97e5dd.jpg","time":"2019-10-22 11:08:20","building_id":"1172","building_name":"10号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/f18559b83e81539712c499f86187ca6c.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/fae7361e43183df9e1afe35faef7a504.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/84504c49fdaf2a6a747e3c295a230bb8.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/79152e0d057538f0d58cee3f3fb00029.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/9c8832e4afdbfcc40ada6785c767e6fc.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/4bc653bf7240e2d47a34be3aa44a0fcf.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/af5a39b77286b3fb24c3241b7f435f40.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/b58eeb9d8752823363c989c5c31aef4a.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/1ce0ed230aedef4d6d29083fa2b2b3ce.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"}],"count":492}
     * msg : 加载成功
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

    public static class DataBean implements Serializable {
        /**
         * list : [{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/ee3b6ebd91cb71d4e9df40400b97e5dd.jpg","time":"2019-10-22 11:08:20","building_id":"1172","building_name":"10号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/f18559b83e81539712c499f86187ca6c.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/fae7361e43183df9e1afe35faef7a504.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/84504c49fdaf2a6a747e3c295a230bb8.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/79152e0d057538f0d58cee3f3fb00029.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/9c8832e4afdbfcc40ada6785c767e6fc.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/4bc653bf7240e2d47a34be3aa44a0fcf.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/af5a39b77286b3fb24c3241b7f435f40.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/b58eeb9d8752823363c989c5c31aef4a.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"},{"project_id":"905","type":2,"img_url":"/Public/Uploads/img/1ce0ed230aedef4d6d29083fa2b2b3ce.jpg","time":"2019-10-16 11:08:20","building_id":"1174","building_name":"11号楼"}]
         * count : 492
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {

            /**
             * address :
             * building_id : 2485
             * building_name :
             * creator : 16511
             * floor_id : 42043
             * height :
             * img_url : /Public/Uploads/img/576e1ead5533cf2eb707d9bff3e70ffc.jpg
             * inside_id : 7417
             * latitude :
             * longitude :
             * position : 北京市市辖区朝阳区长楹龙湖天街西区
             * project_id : 1055
             * project_name : 长楹龙湖天街
             * time : 2019-10-09 21:54:48
             * type : 2
             * user_name : 汪敏
             */

            private String address;
            private String building_id;
            private String building_name;
            private String creator;
            private String floor_id;
            private String height;
            private String img_url;
            private String inside_id;
            private String latitude;
            private String longitude;
            private String position;
            private String project_id;
            private String project_name;
            private String time;
            private int type;
            private String user_name;
            private String target_id;

            public String getTarget_id() {
                return target_id;
            }

            public void setTarget_id(String target_id) {
                this.target_id = target_id;
            }


            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBuilding_id() {
                return building_id;
            }

            public void setBuilding_id(String building_id) {
                this.building_id = building_id;
            }

            public String getBuilding_name() {
                return building_name;
            }

            public void setBuilding_name(String building_name) {
                this.building_name = building_name;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getFloor_id() {
                return floor_id;
            }

            public void setFloor_id(String floor_id) {
                this.floor_id = floor_id;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getInside_id() {
                return inside_id;
            }

            public void setInside_id(String inside_id) {
                this.inside_id = inside_id;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getProject_id() {
                return project_id;
            }

            public void setProject_id(String project_id) {
                this.project_id = project_id;
            }

            public String getProject_name() {
                return project_name;
            }

            public void setProject_name(String project_name) {
                this.project_name = project_name;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }
}

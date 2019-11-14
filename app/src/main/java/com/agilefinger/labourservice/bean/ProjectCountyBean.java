package com.agilefinger.labourservice.bean;

import java.util.List;

public class ProjectCountyBean {

    /**
     * code : 0
     * message : 加载成功
     * data : {"project_id":532,"project_title":"张三的项目","project_number":"P2019080015","status_name":"啊啊","level_name":"C","custom_state_code":"0000000007","level_code":"0000000002","en_arr":[1,2],"en_name":"涂料、保温","cartf_code":[{"id":54,"cartf_code":"1000000011","project_id":532,"company_id":1},{"id":55,"cartf_code":"1000000002","project_id":532,"company_id":1}],"cartf":[{"name_arr":["涂料下级1"],"en_name":"涂料","have_sub":1,"en_id":1,"code":"1000000011","name":"涂料下级1","parent_code_str":"0,1000000011","parent_code":"0"},{"name_arr":[],"en_name":"","have_sub":2,"en_id":"","code":"1000000002","name":"","parent_code_str":"","parent_code":""}],"project_area":2,"project_work_start":"2019-08-16 00:00:00","project_deadline":2,"project_province":"210000","project_city":"210100","project_county":"210101","project_province_name":"辽宁省","project_city_name":"沈阳市","project_county_name":"市辖区","project_address":"2","company_id":1,"org_id":1,"company_name":"张三的工队","org_name":"张三的工队","developer_id":16,"developer_name":"123456765432","project_contact_id":null,"user_name":null,"user_mobile":null,"developer_signed_company":"343"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /*project_area : 2
          project_work_start : 2019-08-16 00:00:00
         project_deadline : 2
          project_province : 210000
          project_city : 210100
          project_county : 210101*/


        @Override
        public String toString() {
            return "DataBean{" +
                    "project_id=" + project_id +
                    ", project_title='" + project_title + '\'' +
                    ", project_area=" + project_area +
                    ", project_province='" + project_province + '\'' +
                    ", project_city='" + project_city + '\'' +
                    ", project_county='" + project_county + '\'' +
                    '}';
        }

        private int project_id;
        private String project_title;
        private int project_area;
        private String project_province;
        private String project_city;
        private String project_county;

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

        public int getProject_area() {
            return project_area;
        }

        public void setProject_area(int project_area) {
            this.project_area = project_area;
        }

        public String getProject_province() {
            return project_province;
        }

        public void setProject_province(String project_province) {
            this.project_province = project_province;
        }

        public String getProject_city() {
            return project_city;
        }

        public void setProject_city(String project_city) {
            this.project_city = project_city;
        }

        public String getProject_county() {
            return project_county;
        }

        public void setProject_county(String project_county) {
            this.project_county = project_county;
        }
    }
}

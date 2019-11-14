package com.agilefinger.labourservice.bean;

public class ProjectDailsBean {

    /**
     * code : 0
     * message : 加载成功
     * data : {"project_title":"磊哥威威111","project_number":"P2019067003","en_name":"","craft_name":"测试11、测试2、测试新增、呃呃呃、Ada、ada2、劳物1、ceshi2、3、222、涂料下级1、测试、eee、3333333333不v、323、444、4242、6、还好、三级工序","project_area":22,"project_work_start":"2019-06-26 15:36:36","project_deadline":0,"project_province":"210000","project_city":"210100","project_county":"210102","project_address":"常营","project_province_name":"辽宁省","project_city_name":"沈阳市","project_county_name":"和平区","org_id":8,"org_name":"test0611002001","developer_name":null,"user_name":null,"developer_signed_company":null,"level_name":null,"creater":"","operator":"","create_time":"2019-07-23 13:12:21","operating_time":"2019-07-23 13:12:21"}
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
        /**
         * project_title : 磊哥威威111
         * project_number : P2019067003
         * en_name :
         * craft_name : 测试11、测试2、测试新增、呃呃呃、Ada、ada2、劳物1、ceshi2、3、222、涂料下级1、测试、eee、3333333333不v、323、444、4242、6、还好、三级工序
         * project_area : 22
         * project_work_start : 2019-06-26 15:36:36
         * project_deadline : 0
         * project_province : 210000
         * project_city : 210100
         * project_county : 210102
         * project_address : 常营
         * project_province_name : 辽宁省
         * project_city_name : 沈阳市
         * project_county_name : 和平区
         * org_id : 8
         * org_name : test0611002001
         * developer_name : null
         * user_name : null
         * developer_signed_company : null
         * level_name : null
         * creater :
         * operator :
         * create_time : 2019-07-23 13:12:21
         * operating_time : 2019-07-23 13:12:21
         */

        private String project_title;
        private String project_number;
        private String en_name;
        private String craft_name;
        private int project_area;
        private String project_work_start;
        private int project_deadline;
        private String project_province;
        private String project_city;
        private String project_county;
        private String project_address;
        private String project_province_name;
        private String project_city_name;
        private String project_county_name;
        private int org_id;
        private String org_name;
        private Object developer_name;
        private Object user_name;
        private Object developer_signed_company;
        private Object level_name;
        private String creater;
        private String operator;
        private String create_time;
        private String operating_time;

        public String getProject_title() {
            return project_title;
        }

        public void setProject_title(String project_title) {
            this.project_title = project_title;
        }

        public String getProject_number() {
            return project_number;
        }

        public void setProject_number(String project_number) {
            this.project_number = project_number;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getCraft_name() {
            return craft_name;
        }

        public void setCraft_name(String craft_name) {
            this.craft_name = craft_name;
        }

        public int getProject_area() {
            return project_area;
        }

        public void setProject_area(int project_area) {
            this.project_area = project_area;
        }

        public String getProject_work_start() {
            return project_work_start;
        }

        public void setProject_work_start(String project_work_start) {
            this.project_work_start = project_work_start;
        }

        public int getProject_deadline() {
            return project_deadline;
        }

        public void setProject_deadline(int project_deadline) {
            this.project_deadline = project_deadline;
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

        public String getProject_address() {
            return project_address;
        }

        public void setProject_address(String project_address) {
            this.project_address = project_address;
        }

        public String getProject_province_name() {
            return project_province_name;
        }

        public void setProject_province_name(String project_province_name) {
            this.project_province_name = project_province_name;
        }

        public String getProject_city_name() {
            return project_city_name;
        }

        public void setProject_city_name(String project_city_name) {
            this.project_city_name = project_city_name;
        }

        public String getProject_county_name() {
            return project_county_name;
        }

        public void setProject_county_name(String project_county_name) {
            this.project_county_name = project_county_name;
        }

        public int getOrg_id() {
            return org_id;
        }

        public void setOrg_id(int org_id) {
            this.org_id = org_id;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public Object getDeveloper_name() {
            return developer_name;
        }

        public void setDeveloper_name(Object developer_name) {
            this.developer_name = developer_name;
        }

        public Object getUser_name() {
            return user_name;
        }

        public void setUser_name(Object user_name) {
            this.user_name = user_name;
        }

        public Object getDeveloper_signed_company() {
            return developer_signed_company;
        }

        public void setDeveloper_signed_company(Object developer_signed_company) {
            this.developer_signed_company = developer_signed_company;
        }

        public Object getLevel_name() {
            return level_name;
        }

        public void setLevel_name(Object level_name) {
            this.level_name = level_name;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOperating_time() {
            return operating_time;
        }

        public void setOperating_time(String operating_time) {
            this.operating_time = operating_time;
        }
    }
}

package com.agilefinger.labourservice.bean;

import java.util.ArrayList;
import java.util.List;

public class KanBanListBean {


    /**
     * code : 0
     * message : 查询成功
     * data : {"projectData":[{"project_id":1071,"project_title":"华润国际社区C地块","project_address":"江北新区广西埂大街100号","project_work_start":"2019-06-22 00:00:00","manager_user_id":16337,"org_id":319,"company_name":"江苏经泰总部直属公司","manager_name":"刘靖","project_en_name":"涂料"}],"engineeringType":[{"id":1,"name":"涂料","code":"0000000001"},{"id":2,"name":"保温","code":"0000000002"}],"projectStatus":[{"id":1,"name":"施工中","sort":1}]}
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
        private List<ProjectDataBean> projectData;
        private List<EngineeringTypeBean> engineeringType;
        private List<ProjectStatusBean> projectStatus;

        public List<ProjectDataBean> getProjectData() {
            return projectData;
        }

        public void setProjectData(List<ProjectDataBean> projectData) {
            this.projectData = projectData;
        }

        public List<EngineeringTypeBean> getEngineeringType() {
            return engineeringType;
        }

        public void setEngineeringType(List<EngineeringTypeBean> engineeringType) {
            this.engineeringType = engineeringType;
        }

        public List<ProjectStatusBean> getProjectStatus() {
            return projectStatus;
        }

        public void setProjectStatus(List<ProjectStatusBean> projectStatus) {
            this.projectStatus = projectStatus;
        }

        public static class ProjectDataBean {
            /**
             * project_id : 1071
             * project_title : 华润国际社区C地块
             * project_address : 江北新区广西埂大街100号
             * project_work_start : 2019-06-22 00:00:00
             * manager_user_id : 16337
             * org_id : 319
             * company_name : 江苏经泰总部直属公司
             * manager_name : 刘靖
             * project_en_name : 涂料
             */

            private int project_id;
            private String project_title;
            private String project_address;
            private String project_work_start;
            private int manager_user_id;
            private int org_id;
            private String company_name;
            private String manager_name;
            private ArrayList<String> project_en_name;

            public String getCustom_state_name() {
                return custom_state_name;
            }

            public void setCustom_state_name(String custom_state_name) {
                this.custom_state_name = custom_state_name;
            }

            private String custom_state_name;


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

            public String getProject_address() {
                return project_address;
            }

            public void setProject_address(String project_address) {
                this.project_address = project_address;
            }

            public String getProject_work_start() {
                return project_work_start;
            }

            public void setProject_work_start(String project_work_start) {
                this.project_work_start = project_work_start;
            }

            public int getManager_user_id() {
                return manager_user_id;
            }

            public void setManager_user_id(int manager_user_id) {
                this.manager_user_id = manager_user_id;
            }

            public int getOrg_id() {
                return org_id;
            }

            public void setOrg_id(int org_id) {
                this.org_id = org_id;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getManager_name() {
                return manager_name;
            }

            public void setManager_name(String manager_name) {
                this.manager_name = manager_name;
            }

            public ArrayList<String> getProject_en_name() {
                return project_en_name;
            }

            public void setProject_en_name(ArrayList<String> project_en_name) {
                this.project_en_name = project_en_name;
            }
        }

        public static class EngineeringTypeBean {
            /**
             * id : 1
             * name : 涂料
             * code : 0000000001
             */

            private int id;
            private String name;
            private String code;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }

        public static class ProjectStatusBean {

            /**
             * code : 0000000001
             * id : 1
             * name : 施工中
             * sort : 1
             */

            private String code;
            private int id;
            private String name;
            private int sort;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}

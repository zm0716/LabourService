package com.agilefinger.labourservice.bean;

import java.util.List;

public class KanBanProjectBoardBean {

    /**
     * code : 0
     * data : {"engineeringType":[{"code":"0000000001","id":1,"name":"涂料"},{"code":"0000000002","id":2,"name":"保温"}],"projectStatus":[{"id":1,"name":"施工中","sort":1}]}
     * message : 查询成功
     */
    private List<EngineeringTypeBean> engineeringType;
    private List<ProjectStatusBean> projectStatus;

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

    public static class EngineeringTypeBean {
        /**
         * code : 0000000001
         * id : 1
         * name : 涂料
         */

        private String code;
        private int id;
        private String name;

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
    }

    public static class ProjectStatusBean {
        /**
         * id : 1
         * name : 施工中
         * sort : 1
         */

        private int id;
        private String name;
        private int sort;

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

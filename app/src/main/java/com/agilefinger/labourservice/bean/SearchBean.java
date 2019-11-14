package com.agilefinger.labourservice.bean;

import java.util.List;

public class SearchBean {



        /**
         * missionID : 093CA68D776F3CA9687E87E5D4F89E46
         * projectName : 劳务通
         * missionNo : 1564735.727
         * doUser : 王大锤
         * createTime : 2019-08-02 16:48:47
         * endTime : 2019-08-02
         * status : 未启动
         */

        private String missionID;
        private String projectName;
        private String missionNo;
        private String doUser;
        private String createTime;
        private String endTime;
        private String status;

        public String getMissionID() {
            return missionID;
        }

        public void setMissionID(String missionID) {
            this.missionID = missionID;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getMissionNo() {
            return missionNo;
        }

        public void setMissionNo(String missionNo) {
            this.missionNo = missionNo;
        }

        public String getDoUser() {
            return doUser;
        }

        public void setDoUser(String doUser) {
            this.doUser = doUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


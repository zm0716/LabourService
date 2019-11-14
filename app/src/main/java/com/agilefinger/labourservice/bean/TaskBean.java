package com.agilefinger.labourservice.bean;

import java.util.List;

public class TaskBean {


        /**
         * missionID : 89FAAD4EEFD512884862CEA1137BE9D5
         * projectName : 劳务通
         * missionNo : 1565593933
         * doUser : 王大锤
         * createTime : 2019-08-12 15:12:00
         * endTime : 2019-08-01
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

package com.agilefinger.labourservice.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KanBanDetailBean implements Serializable {

    /**
     * ProjectCorrect : {"NotOverDue":0,"OverDueFinish":0,"OverDueNotFinish":0,"averageTime":"0天0小时0分","dateData":[{"date":"2019-10-30","number":0},{"date":"2019-10-31","number":0},{"date":"2019-11-01","number":0},{"date":"2019-11-02","number":0},{"date":"2019-11-03","number":0},{"date":"2019-11-04","number":5},{"date":"2019-11-05","number":0},{"date":"2019-11-06","number":0}],"disposableRate":0,"doingStatus":5,"finishRate":0,"finishStatus":0,"missionRedUnfinish":0,"refuseRate":0,"refuseStatus":0,"waitStatus":0}
     * details : {"company_name":"0920租户","custom_state_code":"0000000001","custom_state_name":"状态1","manager_user_id":2747,"org_id":607,"project_address":"济公活佛固定方式的","project_id":191,"project_member":"0920用户0","project_number":"P2019110002","project_title":"苹果项目","project_type":[{"en_id":1,"en_name":"涂料","project_id":191},{"en_id":2,"en_name":"保温","project_id":191}],"project_work_start":"2019-11-04"}
     * patrol : {"missionAll":5,"missionComplete":1,"missionRed":1,"missionRedRate":33,"missionScore":0.55}
     * project_img : [{"building_id":558,"building_name":"份饭","height":"","img_url":"/Public/Uploads/img/06428ba282a9ad86999eba6a68fbf137.jpg","latitude":"","longitude":"","position":"","project_id":191,"project_name":"苹果项目","time":"2019-11-04","type":2,"user_name":"0920用户0"},{"building_id":559,"building_name":"#第三代","height":"","img_url":"/Public/Uploads/img/3c1c645b8a61a3ce12cebf65e72de14c.jpg","latitude":"","longitude":"","position":"","project_id":191,"project_name":"苹果项目","time":"2019-11-04","type":2,"user_name":"0920用户0"},{"building_name":"","height":65,"img_url":"/Public/Uploads/img/50a8cf65ff57a87f0bd3e8a552f28f3b.jpg","latitude":40,"longitude":116,"position":"北京市东城区建国门北大街靠近日化家属院","project_id":"191","project_name":"苹果项目","time":"2019-11-04","type":1,"user_name":"0920用户0"},{"building_id":558,"building_name":"份饭","height":"","img_url":"/Public/Uploads/img/665bef56bc2c82e95a5712ecf053420f.jpg","latitude":"","longitude":"","position":"","project_id":191,"project_name":"苹果项目","time":"2019-11-04","type":2,"user_name":"0920用户0"},{"building_id":558,"building_name":"份饭","height":"","img_url":"/Public/Uploads/img/93897cb277c47d8cdb0a26905f66ea3e.jpg","latitude":"","longitude":"","position":"他 bra 不","project_id":191,"project_name":"苹果项目","time":"2019-11-04","type":2,"user_name":"0920用户0"},{"building_name":"","height":65,"img_url":"/Public/Uploads/img/b8d02955a66ec4f641848ed9f49574ac.jpg","latitude":40,"longitude":116,"position":"北京市东城区建国门北大街靠近金成建国5号","project_id":"191","project_name":"苹果项目","time":"2019-11-04","type":1,"user_name":"0920用户0"},{"building_name":"","height":65,"img_url":"/Public/Uploads/img/d988036b749f3738712696aa287c086a.jpg","latitude":40,"longitude":116,"position":"北京市东城区建国门北大街靠近日化家属院","project_id":"191","project_name":"苹果项目","time":"2019-11-04","type":1,"user_name":"0920用户0"}]
     */

    private ProjectCorrectBean ProjectCorrect;
    private DetailsBean details;
    private PatrolBean patrol;
    private List<ProjectImgBean> project_img;

    public ProjectCorrectBean getProjectCorrect() {
        return ProjectCorrect;
    }

    public void setProjectCorrect(ProjectCorrectBean ProjectCorrect) {
        this.ProjectCorrect = ProjectCorrect;
    }

    public DetailsBean getDetails() {
        return details;
    }

    public void setDetails(DetailsBean details) {
        this.details = details;
    }

    public PatrolBean getPatrol() {
        return patrol;
    }

    public void setPatrol(PatrolBean patrol) {
        this.patrol = patrol;
    }

    public List<ProjectImgBean> getProject_img() {
        return project_img;
    }

    public void setProject_img(List<ProjectImgBean> project_img) {
        this.project_img = project_img;
    }

    public static class ProjectCorrectBean {
        /**
         * NotOverDue : 0
         * OverDueFinish : 0
         * OverDueNotFinish : 0
         * averageTime : 0天0小时0分
         * dateData : [{"date":"2019-10-30","number":0},{"date":"2019-10-31","number":0},{"date":"2019-11-01","number":0},{"date":"2019-11-02","number":0},{"date":"2019-11-03","number":0},{"date":"2019-11-04","number":5},{"date":"2019-11-05","number":0},{"date":"2019-11-06","number":0}]
         * disposableRate : 0
         * doingStatus : 5
         * finishRate : 0
         * finishStatus : 0
         * missionRedUnfinish : 0
         * refuseRate : 0
         * refuseStatus : 0
         * waitStatus : 0
         */

        private int NotOverDue;
        private int OverDueFinish;
        private int OverDueNotFinish;
        private String averageTime;
        private int disposableRate;
        private int doingStatus;
        private int finishRate;
        private int finishStatus;
        private int missionRedUnfinish;
        private int refuseRate;
        private int refuseStatus;
        private int waitStatus;
        private int allStatus;
        private ArrayList<DateDataBean> dateData;

        public int getAllStatus() {
            return allStatus;
        }

        public void setAllStatus(int allStatus) {
            this.allStatus = allStatus;
        }

        public int getNotOverDue() {
            return NotOverDue;
        }

        public void setNotOverDue(int NotOverDue) {
            this.NotOverDue = NotOverDue;
        }

        public int getOverDueFinish() {
            return OverDueFinish;
        }

        public void setOverDueFinish(int OverDueFinish) {
            this.OverDueFinish = OverDueFinish;
        }

        public int getOverDueNotFinish() {
            return OverDueNotFinish;
        }

        public void setOverDueNotFinish(int OverDueNotFinish) {
            this.OverDueNotFinish = OverDueNotFinish;
        }

        public String getAverageTime() {
            return averageTime;
        }

        public void setAverageTime(String averageTime) {
            this.averageTime = averageTime;
        }

        public int getDisposableRate() {
            return disposableRate;
        }

        public void setDisposableRate(int disposableRate) {
            this.disposableRate = disposableRate;
        }

        public int getDoingStatus() {
            return doingStatus;
        }

        public void setDoingStatus(int doingStatus) {
            this.doingStatus = doingStatus;
        }

        public int getFinishRate() {
            return finishRate;
        }

        public void setFinishRate(int finishRate) {
            this.finishRate = finishRate;
        }

        public int getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(int finishStatus) {
            this.finishStatus = finishStatus;
        }

        public int getMissionRedUnfinish() {
            return missionRedUnfinish;
        }

        public void setMissionRedUnfinish(int missionRedUnfinish) {
            this.missionRedUnfinish = missionRedUnfinish;
        }

        public int getRefuseRate() {
            return refuseRate;
        }

        public void setRefuseRate(int refuseRate) {
            this.refuseRate = refuseRate;
        }

        public int getRefuseStatus() {
            return refuseStatus;
        }

        public void setRefuseStatus(int refuseStatus) {
            this.refuseStatus = refuseStatus;
        }

        public int getWaitStatus() {
            return waitStatus;
        }

        public void setWaitStatus(int waitStatus) {
            this.waitStatus = waitStatus;
        }

        public ArrayList<DateDataBean> getDateData() {
            return dateData;
        }

        public void setDateData(ArrayList<DateDataBean> dateData) {
            this.dateData = dateData;
        }

        public static class DateDataBean {
            /**
             * date : 2019-10-30
             * number : 0
             */

            private String date;
            private int number;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }

    public static class DetailsBean {
        /**
         * company_name : 0920租户
         * custom_state_code : 0000000001
         * custom_state_name : 状态1
         * manager_user_id : 2747
         * org_id : 607
         * project_address : 济公活佛固定方式的
         * project_id : 191
         * project_member : 0920用户0
         * project_number : P2019110002
         * project_title : 苹果项目
         * project_type : [{"en_id":1,"en_name":"涂料","project_id":191},{"en_id":2,"en_name":"保温","project_id":191}]
         * project_work_start : 2019-11-04
         */

        private String company_name;
        private String custom_state_code;
        private String custom_state_name;
        private int manager_user_id;
        private int org_id;
        private String project_address;
        private int project_id;
        private String project_member;
        private String project_number;
        private String project_title;
        private String project_work_start;
        private List<ProjectTypeBean> project_type;
        private String create_time;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCustom_state_code() {
            return custom_state_code;
        }

        public void setCustom_state_code(String custom_state_code) {
            this.custom_state_code = custom_state_code;
        }

        public String getCustom_state_name() {
            return custom_state_name;
        }

        public void setCustom_state_name(String custom_state_name) {
            this.custom_state_name = custom_state_name;
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

        public String getProject_address() {
            return project_address;
        }

        public void setProject_address(String project_address) {
            this.project_address = project_address;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getProject_member() {
            return project_member;
        }

        public void setProject_member(String project_member) {
            this.project_member = project_member;
        }

        public String getProject_number() {
            return project_number;
        }

        public void setProject_number(String project_number) {
            this.project_number = project_number;
        }

        public String getProject_title() {
            return project_title;
        }

        public void setProject_title(String project_title) {
            this.project_title = project_title;
        }

        public String getProject_work_start() {
            return project_work_start;
        }

        public void setProject_work_start(String project_work_start) {
            this.project_work_start = project_work_start;
        }

        public List<ProjectTypeBean> getProject_type() {
            return project_type;
        }

        public void setProject_type(List<ProjectTypeBean> project_type) {
            this.project_type = project_type;
        }

        public static class ProjectTypeBean {
            /**
             * en_id : 1
             * en_name : 涂料
             * project_id : 191
             */

            private int en_id;
            private String en_name;
            private int project_id;

            public int getEn_id() {
                return en_id;
            }

            public void setEn_id(int en_id) {
                this.en_id = en_id;
            }

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }
        }
    }

    public static class PatrolBean {
        /**
         * missionAll : 5
         * missionComplete : 1
         * missionRed : 1
         * missionRedRate : 33
         * missionScore : 0.55
         */

        private int missionAll;
        private int missionComplete;
        private int missionRed;
        private int missionRedRate;
        private double missionScore;

        public int getMissionAll() {
            return missionAll;
        }

        public void setMissionAll(int missionAll) {
            this.missionAll = missionAll;
        }

        public int getMissionComplete() {
            return missionComplete;
        }

        public void setMissionComplete(int missionComplete) {
            this.missionComplete = missionComplete;
        }

        public int getMissionRed() {
            return missionRed;
        }

        public void setMissionRed(int missionRed) {
            this.missionRed = missionRed;
        }

        public int getMissionRedRate() {
            return missionRedRate;
        }

        public void setMissionRedRate(int missionRedRate) {
            this.missionRedRate = missionRedRate;
        }

        public double getMissionScore() {
            return missionScore;
        }

        public void setMissionScore(double missionScore) {
            this.missionScore = missionScore;
        }
    }

    public static class ProjectImgBean implements Serializable {
        /**
         * building_id : 558
         * building_name : 份饭
         * height :
         * img_url : /Public/Uploads/img/06428ba282a9ad86999eba6a68fbf137.jpg
         * latitude :
         * longitude :
         * position :
         * project_id : 191
         * project_name : 苹果项目
         * time : 2019-11-04
         * type : 2
         * user_name : 0920用户0
         */

        private int building_id;
        private String building_name;
        private String height;
        private String img_url;
        private String latitude;
        private String longitude;
        private String position;
        private int project_id;
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

        public int getBuilding_id() {
            return building_id;
        }

        public void setBuilding_id(int building_id) {
            this.building_id = building_id;
        }

        public String getBuilding_name() {
            return building_name;
        }

        public void setBuilding_name(String building_name) {
            this.building_name = building_name;
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

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
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

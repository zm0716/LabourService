package com.agilefinger.labourservice.bean;

import java.util.List;

public class BanZuBean {

    /**
     * code : 0
     * data : [{"building":[{"building_id":216,"building_name":"4#","en_id":1,"en_name":"涂料"},{"building_id":376,"building_name":"7#","en_id":1,"en_name":"涂料"},{"building_id":1861,"building_name":"5#","en_id":1,"en_name":"涂料"},{"building_id":1863,"building_name":"1号楼","en_id":1,"en_name":"涂料"}],"group_id":135,"group_mobile":"13626888018","group_name":"贾丙军","team_name":"贾丙军的工队"},{"building":[{"building_id":217,"building_name":"1#","en_id":1,"en_name":"涂料"},{"building_id":1859,"building_name":"2#","en_id":1,"en_name":"涂料"},{"building_id":1860,"building_name":"3#","en_id":1,"en_name":"涂料"}],"group_id":134,"group_mobile":"15921196905","group_name":"张飞","team_name":"张飞的工队"}]
     * message : 加载成功
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
         * building : [{"building_id":216,"building_name":"4#","en_id":1,"en_name":"涂料"},{"building_id":376,"building_name":"7#","en_id":1,"en_name":"涂料"},{"building_id":1861,"building_name":"5#","en_id":1,"en_name":"涂料"},{"building_id":1863,"building_name":"1号楼","en_id":1,"en_name":"涂料"}]
         * group_id : 135
         * group_mobile : 13626888018
         * group_name : 贾丙军
         * team_name : 贾丙军的工队
         */

        private int group_id;
        private String group_mobile;
        private String group_name;
        private String team_name;
        private String building_str;
        private List<BuildingBean> building;

        public String getBuilding_str() {
            return building_str;
        }

        public void setBuilding_str(String building_str) {
            this.building_str = building_str;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_mobile() {
            return group_mobile;
        }

        public void setGroup_mobile(String group_mobile) {
            this.group_mobile = group_mobile;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }

        public List<BuildingBean> getBuilding() {
            return building;
        }

        public void setBuilding(List<BuildingBean> building) {
            this.building = building;
        }

        public static class BuildingBean {
            /**
             * building_id : 216
             * building_name : 4#
             * en_id : 1
             * en_name : 涂料
             */

            private int building_id;
            private String building_name;
            private int en_id;
            private String en_name;

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
        }
    }
}

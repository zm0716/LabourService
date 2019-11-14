package com.agilefinger.labourservice.bean;

import java.util.List;

public class AllBuildBean {


    /**
     * code : 0
     * data : {"group":[],"building":[{"building_num":"4","id":755}],"has":[{"cpi_mi_id":"19BB22D4D3C44D4CF66F94A904197878","cpi_mi_no":1,"cpi_b_id":"755","cpi_type":"user","name":"4"}]}
     * msg : 成功！
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

    public static class GroupBean {
            /**
             * group_id : 1
             * group_name : 小席席
             */

            private int group_id;
            private String group_name;
            private boolean groupisxuan;

            public boolean isGroupisxuan() {
                return groupisxuan;
            }

            public void setGroupisxuan(boolean groupisxuan) {
                this.groupisxuan = groupisxuan;
            }

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }
        }

    public static class DataBean {
        private List<GroupBean> group;
        private List<BuildingBean> building;
        private List<HasBean> has;

        public List<GroupBean> getGroup() {
            return group;
        }

        public void setGroup(List<GroupBean> group) {
            this.group = group;
        }

        public List<BuildingBean> getBuilding() {
            return building;
        }

        public void setBuilding(List<BuildingBean> building) {
            this.building = building;
        }

        public List<HasBean> getHas() {
            return has;
        }

        public void setHas(List<HasBean> has) {
            this.has = has;
        }

        public static class BuildingBean {
            /**
             * building_num : 4
             * id : 755
             */

            private String building_num;
            private int id;
            private boolean groupisxuan;

            public int getIs_select() {
                return is_select;
            }

            public void setIs_select(int is_select) {
                this.is_select = is_select;
            }

            private int is_select;

            public boolean isGroupisxuan() {
                return groupisxuan;
            }

            public void setGroupisxuan(boolean groupisxuan) {
                this.groupisxuan = groupisxuan;
            }

            public String getBuilding_num() {
                return building_num;
            }

            public void setBuilding_num(String building_num) {
                this.building_num = building_num;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class HasBean {
            /**
             * name : 1号楼
             * cpi_mi_id : F72F236A8BABE25429FD371497C40B6D
             * cpi_mi_no : 1
             * cpi_b_id : 198
             * cpi_f_id : null
             * cpi_i_id : null
             * cpi_type : user
             * cpi_cl_id : null
             * floorList : []
             * groupList : []
             */

            private String name;
            private String cpi_mi_id;
            private int cpi_mi_no;
            private String cpi_b_id;
            private Object cpi_f_id;
            private Object cpi_i_id;
            private String cpi_type;
            private Object cpi_cl_id;
            private List<?> floorList;
            private List<?> groupList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCpi_mi_id() {
                return cpi_mi_id;
            }

            public void setCpi_mi_id(String cpi_mi_id) {
                this.cpi_mi_id = cpi_mi_id;
            }

            public int getCpi_mi_no() {
                return cpi_mi_no;
            }

            public void setCpi_mi_no(int cpi_mi_no) {
                this.cpi_mi_no = cpi_mi_no;
            }

            public String getCpi_b_id() {
                return cpi_b_id;
            }

            public void setCpi_b_id(String cpi_b_id) {
                this.cpi_b_id = cpi_b_id;
            }

            public Object getCpi_f_id() {
                return cpi_f_id;
            }

            public void setCpi_f_id(Object cpi_f_id) {
                this.cpi_f_id = cpi_f_id;
            }

            public Object getCpi_i_id() {
                return cpi_i_id;
            }

            public void setCpi_i_id(Object cpi_i_id) {
                this.cpi_i_id = cpi_i_id;
            }

            public String getCpi_type() {
                return cpi_type;
            }

            public void setCpi_type(String cpi_type) {
                this.cpi_type = cpi_type;
            }

            public Object getCpi_cl_id() {
                return cpi_cl_id;
            }

            public void setCpi_cl_id(Object cpi_cl_id) {
                this.cpi_cl_id = cpi_cl_id;
            }

            public List<?> getFloorList() {
                return floorList;
            }

            public void setFloorList(List<?> floorList) {
                this.floorList = floorList;
            }

            public List<?> getGroupList() {
                return groupList;
            }

            public void setGroupList(List<?> groupList) {
                this.groupList = groupList;
            }


            /**
             * cpi_mi_id : 19BB22D4D3C44D4CF66F94A904197878
             * cpi_mi_no : 1
             * cpi_b_id : 755
             * cpi_type : user
             * name : 4
             */
//
//            private String cpi_mi_id;
//            private int cpi_mi_no;
//            private String cpi_b_id;
//            private String cpi_type;
//            private String name;
//
//            public String getCpi_mi_id() {
//                return cpi_mi_id;
//            }
//
//            public void setCpi_mi_id(String cpi_mi_id) {
//                this.cpi_mi_id = cpi_mi_id;
//            }
//
//            public int getCpi_mi_no() {
//                return cpi_mi_no;
//            }
//
//            public void setCpi_mi_no(int cpi_mi_no) {
//                this.cpi_mi_no = cpi_mi_no;
//            }
//
//            public String getCpi_b_id() {
//                return cpi_b_id;
//            }
//
//            public void setCpi_b_id(String cpi_b_id) {
//                this.cpi_b_id = cpi_b_id;
//            }
//
//            public String getCpi_type() {
//                return cpi_type;
//            }
//
//            public void setCpi_type(String cpi_type) {
//                this.cpi_type = cpi_type;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
        }
    }

}

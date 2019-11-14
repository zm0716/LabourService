package com.agilefinger.labourservice.bean;

import java.util.List;

public class TeamGroupPeopleBean {

    /**
     * code : 0
     * data : {"manager":[{"join_time":"2019-05-06 10:21:38","project_id":807,"type":2,"user_id":16254,"user_name":"向彬"}],"member":[{"join_time":"2019-10-10 09:54:00","project_id":807,"type":1,"user_id":16373,"user_name":"张强"},{"join_time":"2019-10-10 09:55:00","project_id":807,"type":1,"user_id":16257,"user_name":"雷耀俊"},{"join_time":"2019-10-10 14:25:00","project_id":807,"type":1,"user_id":16256,"user_name":"陈志"},{"join_time":"2019-10-11 15:48:00","project_id":807,"type":1,"user_id":16194,"user_name":"阿奎"}]}
     * message : 加载成功
     */

    private int code;
    private DataBean data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<ManagerBean> manager;
        private List<MemberBean> member;

        public List<ManagerBean> getManager() {
            return manager;
        }

        public void setManager(List<ManagerBean> manager) {
            this.manager = manager;
        }

        public List<MemberBean> getMember() {
            return member;
        }

        public void setMember(List<MemberBean> member) {
            this.member = member;
        }

        public static class ManagerBean {
            /**
             * join_time : 2019-05-06 10:21:38
             * project_id : 807
             * type : 2
             * user_id : 16254
             * user_name : 向彬
             */

            private String join_time;
            private int project_id;
            private int type;
            private int user_id;
            private String user_name;

            public String getJoin_time() {
                return join_time;
            }

            public void setJoin_time(String join_time) {
                this.join_time = join_time;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

        public static class MemberBean {
            /**
             * join_time : 2019-10-10 09:54:00
             * project_id : 807
             * type : 1
             * user_id : 16373
             * user_name : 张强
             */

            private String join_time;
            private int project_id;
            private int type;
            private int user_id;
            private String user_name;

            public String getJoin_time() {
                return join_time;
            }

            public void setJoin_time(String join_time) {
                this.join_time = join_time;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

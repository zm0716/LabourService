package com.agilefinger.labourservice.bean;

import java.util.List;

public class EndBean {


    /**
     * code : 0
     * data : {"position":[{"name":"第一个楼栋","group":"班组一 15322112000"},{"name":"","group":"班组二 15100009909"},{"name":"","group":"班组三 19099990000"},{"name":"第二个楼栋","group":"班组一 15322112000"},{"name":"","group":"班组二 15100009909"},{"name":"","group":"班组三 19099990000"}],"remark":"","items":[{"mci_id":"2328F5EBE98EBEEF2BAE3C79B43BB750","mci_mct_id":"9263CDF1F25DB397C5F17CA66F31509B","mci_name":"上墙岩棉条最小长度D","mci_no":"TC01791000","mci_min_point":1,"p_count":1,"names":"主材施工>EPS板>保温观感>工序预留"},{"mci_id":"7D1370DB9584F993C1C7A46D1A3B22B2","mci_mct_id":"A72EF5D95852FEB15E3D301334DF65BC","mci_name":"平整度平整度","mci_no":"TC01789000","mci_min_point":3,"p_count":3,"names":"主材施工>锚固"},{"mci_id":"E97E6B8F9726D7FE09F24E4F5165F4C1","mci_mct_id":"9263CDF1F25DB397C5F17CA66F31509B","mci_name":"禁止朝天缝RW","mci_no":"TC01790000","mci_min_point":1,"p_count":0,"names":"主材施工>EPS板>保温观感>工序预留"},{"mci_id":"E0D98F750460CECEF2352DCEB4F802F4","mci_mct_id":"A72EF5D95852FEB15E3D301334DF65BC","mci_name":"大面平整度","mci_no":"TC01788000","mci_min_point":3,"p_count":0,"names":"主材施工>锚固"},{"mci_id":"DC6B27D89F5C1221587ACFD138234BA9","mci_mct_id":"761D6A033BA906440A144E4E3F5863CC","mci_name":"底漆实干后施工（25℃，4H）","mci_no":"TC01792000","mci_min_point":1,"p_count":0,"names":"岩棉条、线条粘结施工>分色工艺>质感>真石漆"},{"mci_id":"FE7ADA8DB7D7444BE11903D476800454","mci_mct_id":"761D6A033BA906440A144E4E3F5863CC","mci_name":"板面平整度","mci_no":"TC01785000","mci_min_point":3,"p_count":0,"names":"岩棉条、线条粘结施工>分色工艺>质感>真石漆"}],"total":6,"done":2}
     * message : 成功！
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
        /**
         * position : [{"name":"第一个楼栋","group":"班组一 15322112000"},{"name":"","group":"班组二 15100009909"},{"name":"","group":"班组三 19099990000"},{"name":"第二个楼栋","group":"班组一 15322112000"},{"name":"","group":"班组二 15100009909"},{"name":"","group":"班组三 19099990000"}]
         * remark :
         * items : [{"mci_id":"2328F5EBE98EBEEF2BAE3C79B43BB750","mci_mct_id":"9263CDF1F25DB397C5F17CA66F31509B","mci_name":"上墙岩棉条最小长度D","mci_no":"TC01791000","mci_min_point":1,"p_count":1,"names":"主材施工>EPS板>保温观感>工序预留"},{"mci_id":"7D1370DB9584F993C1C7A46D1A3B22B2","mci_mct_id":"A72EF5D95852FEB15E3D301334DF65BC","mci_name":"平整度平整度","mci_no":"TC01789000","mci_min_point":3,"p_count":3,"names":"主材施工>锚固"},{"mci_id":"E97E6B8F9726D7FE09F24E4F5165F4C1","mci_mct_id":"9263CDF1F25DB397C5F17CA66F31509B","mci_name":"禁止朝天缝RW","mci_no":"TC01790000","mci_min_point":1,"p_count":0,"names":"主材施工>EPS板>保温观感>工序预留"},{"mci_id":"E0D98F750460CECEF2352DCEB4F802F4","mci_mct_id":"A72EF5D95852FEB15E3D301334DF65BC","mci_name":"大面平整度","mci_no":"TC01788000","mci_min_point":3,"p_count":0,"names":"主材施工>锚固"},{"mci_id":"DC6B27D89F5C1221587ACFD138234BA9","mci_mct_id":"761D6A033BA906440A144E4E3F5863CC","mci_name":"底漆实干后施工（25℃，4H）","mci_no":"TC01792000","mci_min_point":1,"p_count":0,"names":"岩棉条、线条粘结施工>分色工艺>质感>真石漆"},{"mci_id":"FE7ADA8DB7D7444BE11903D476800454","mci_mct_id":"761D6A033BA906440A144E4E3F5863CC","mci_name":"板面平整度","mci_no":"TC01785000","mci_min_point":3,"p_count":0,"names":"岩棉条、线条粘结施工>分色工艺>质感>真石漆"}]
         * total : 6
         * done : 2
         */

        private String remark;
        private int total;
        private int done;
        private List<PositionBean> position;
        private List<ItemsBean> items;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getDone() {
            return done;
        }

        public void setDone(int done) {
            this.done = done;
        }

        public List<PositionBean> getPosition() {
            return position;
        }

        public void setPosition(List<PositionBean> position) {
            this.position = position;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class PositionBean {
            /**
             * name : 第一个楼栋
             * group : 班组一 15322112000
             */

            private String name;
            private String group;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }
        }

        public static class ItemsBean {
            /**
             * mci_id : 2328F5EBE98EBEEF2BAE3C79B43BB750
             * mci_mct_id : 9263CDF1F25DB397C5F17CA66F31509B
             * mci_name : 上墙岩棉条最小长度D
             * mci_no : TC01791000
             * mci_min_point : 1
             * p_count : 1
             * names : 主材施工>EPS板>保温观感>工序预留
             */

            private String mci_id;
            private String mci_mct_id;
            private String mci_name;
            private String mci_no;
            private int mci_min_point;
            private int p_count;
            private String names;

            public String getMci_id() {
                return mci_id;
            }

            public void setMci_id(String mci_id) {
                this.mci_id = mci_id;
            }

            public String getMci_mct_id() {
                return mci_mct_id;
            }

            public void setMci_mct_id(String mci_mct_id) {
                this.mci_mct_id = mci_mct_id;
            }

            public String getMci_name() {
                return mci_name;
            }

            public void setMci_name(String mci_name) {
                this.mci_name = mci_name;
            }

            public String getMci_no() {
                return mci_no;
            }

            public void setMci_no(String mci_no) {
                this.mci_no = mci_no;
            }

            public int getMci_min_point() {
                return mci_min_point;
            }

            public void setMci_min_point(int mci_min_point) {
                this.mci_min_point = mci_min_point;
            }

            public int getP_count() {
                return p_count;
            }

            public void setP_count(int p_count) {
                this.p_count = p_count;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }
        }
    }
}

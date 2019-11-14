package com.agilefinger.labourservice.bean;

import java.util.List;

public class ProjectConditionBean {

    private List<DeveloperListBean> developerList;
    private List<ManagerListBean> managerList;
    private List<RegionListBean> regionList;

    public List<DeveloperListBean> getDeveloperList() {
        return developerList;
    }

    public void setDeveloperList(List<DeveloperListBean> developerList) {
        this.developerList = developerList;
    }

    public List<ManagerListBean> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<ManagerListBean> managerList) {
        this.managerList = managerList;
    }

    public List<RegionListBean> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<RegionListBean> regionList) {
        this.regionList = regionList;
    }

    public static class DeveloperListBean {
        /**
         * developer_name : 万科
         * id : 83
         */

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ManagerListBean {
        /**
         * id : 31
         * user_name : 陈鑫
         */

        private int id;
        private String user_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }

    public static class RegionListBean {
        /**
         * city : [{"code":110100,"country":[{"code":110101,"name":"东城区"},{"code":110102,"name":"西城区"},{"code":110103,"name":"崇文区"},{"code":110104,"name":"宣武区"},{"code":110105,"name":"朝阳区"},{"code":110106,"name":"丰台区"},{"code":110107,"name":"石景山区"},{"code":110108,"name":"海淀区"},{"code":110109,"name":"门头沟区"},{"code":110111,"name":"房山区"},{"code":110112,"name":"通州区"},{"code":110113,"name":"顺义区"},{"code":110114,"name":"昌平区"},{"code":110115,"name":"大兴区"},{"code":110116,"name":"怀柔区"},{"code":110117,"name":"平谷区"}],"name":"市辖区"},{"code":110200,"country":[{"code":110228,"name":"密云县"},{"code":110229,"name":"延庆县"}],"name":"县"}]
         * code : 110000
         * name : 北京市
         */

        private int code;
        private String name;
        private List<CityBean> city;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * code : 110100
             * country : [{"code":110101,"name":"东城区"},{"code":110102,"name":"西城区"},{"code":110103,"name":"崇文区"},{"code":110104,"name":"宣武区"},{"code":110105,"name":"朝阳区"},{"code":110106,"name":"丰台区"},{"code":110107,"name":"石景山区"},{"code":110108,"name":"海淀区"},{"code":110109,"name":"门头沟区"},{"code":110111,"name":"房山区"},{"code":110112,"name":"通州区"},{"code":110113,"name":"顺义区"},{"code":110114,"name":"昌平区"},{"code":110115,"name":"大兴区"},{"code":110116,"name":"怀柔区"},{"code":110117,"name":"平谷区"}]
             * name : 市辖区
             */

            private int code;
            private String name;
            private List<CountryBean> country;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<CountryBean> getCountry() {
                return country;
            }

            public void setCountry(List<CountryBean> country) {
                this.country = country;
            }

            public static class CountryBean {
                /**
                 * code : 110101
                 * name : 东城区
                 */

                private int code;
                private String name;

                public int getCode() {
                    return code;
                }

                public void setCode(int code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}

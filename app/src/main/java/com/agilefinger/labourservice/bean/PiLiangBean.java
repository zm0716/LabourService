package com.agilefinger.labourservice.bean;

import java.util.List;

/**
 * Created by Ocean on 2019/8/18.
 */

public class PiLiangBean {

    /**
     * code : 0
     * data : {"info":[{"mci_id":"9EC20997E2EA2844459722A9E3E55FEE","mci_type":"4","mci_mct_id":"9B7224E88336E56BE350FC4217FFD24C","mci_name":"分类1-1-3/检测项1","mci_no":"773","mci_min_point":2,"p_count":1,"type_info":[]},{"mci_id":"9EC20997E2EA2844459722A9E3E55FFF","mci_type":"4","mci_mct_id":"9B7224E88336E56BE350FC4217FFD24A","mci_name":"分类1-1-1/检测项1","mci_no":"773","mci_min_point":3,"p_count":1,"type_info":[]}],"pid":"1"}
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

    public static class DataBean {
        /**
         * info : [{"mci_id":"9EC20997E2EA2844459722A9E3E55FEE","mci_type":"4","mci_mct_id":"9B7224E88336E56BE350FC4217FFD24C","mci_name":"分类1-1-3/检测项1","mci_no":"773","mci_min_point":2,"p_count":1,"type_info":[]},{"mci_id":"9EC20997E2EA2844459722A9E3E55FFF","mci_type":"4","mci_mct_id":"9B7224E88336E56BE350FC4217FFD24A","mci_name":"分类1-1-1/检测项1","mci_no":"773","mci_min_point":3,"p_count":1,"type_info":[]}]
         * pid : 1
         */

        private String pid;
        private List<InfoBean> info;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * mci_id : 9EC20997E2EA2844459722A9E3E55FEE
             * mci_type : 4
             * mci_mct_id : 9B7224E88336E56BE350FC4217FFD24C
             * mci_name : 分类1-1-3/检测项1
             * mci_no : 773
             * mci_min_point : 2
             * p_count : 1
             * type_info : []
             */

            private String mci_id;
            private String mci_type;
            private String mci_mct_id;
            private String mci_name;
            private String mci_no;
            private int mci_min_point;
            private int p_count;
            private boolean isxuan;
            private String check;
            private String result="";
            private String unit;
            private List<TypeInfoBean> type_info;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public boolean isIsxuan() {
                return isxuan;
            }

            public void setIsxuan(boolean isxuan) {
                this.isxuan = isxuan;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }

            public String getMci_id() {
                return mci_id;
            }

            public void setMci_id(String mci_id) {
                this.mci_id = mci_id;
            }

            public String getMci_type() {
                return mci_type;
            }

            public void setMci_type(String mci_type) {
                this.mci_type = mci_type;
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

            public List<TypeInfoBean> getType_info() {
                return type_info;
            }

            public void setType_info(List<TypeInfoBean> type_info) {
                this.type_info = type_info;
            }

            public static class TypeInfoBean {

                /**
                 * mv_mci_id : 0AE082CC2F8315FC1C6FC7F8D38536E7
                 * mv_no : 1
                 * mv_min : 0
                 * mv_max : 300
                 * mv_score : 0
                 * mv_unit : mm
                 * mv_creator : 2507
                 * mv_createtime : 2019-09-08 17:57:29
                 */

                private String mv_mci_id;
                private int mv_no;
                private int mv_min;
                private int mv_max;
                private int mv_score;
                private String mv_unit;
                private String mv_creator;
                private String mv_createtime;

                public String getMv_mci_id() {
                    return mv_mci_id;
                }

                public void setMv_mci_id(String mv_mci_id) {
                    this.mv_mci_id = mv_mci_id;
                }

                public int getMv_no() {
                    return mv_no;
                }

                public void setMv_no(int mv_no) {
                    this.mv_no = mv_no;
                }

                public int getMv_min() {
                    return mv_min;
                }

                public void setMv_min(int mv_min) {
                    this.mv_min = mv_min;
                }

                public int getMv_max() {
                    return mv_max;
                }

                public void setMv_max(int mv_max) {
                    this.mv_max = mv_max;
                }

                public int getMv_score() {
                    return mv_score;
                }

                public void setMv_score(int mv_score) {
                    this.mv_score = mv_score;
                }

                public String getMv_unit() {
                    return mv_unit;
                }

                public void setMv_unit(String mv_unit) {
                    this.mv_unit = mv_unit;
                }

                public String getMv_creator() {
                    return mv_creator;
                }

                public void setMv_creator(String mv_creator) {
                    this.mv_creator = mv_creator;
                }

                public String getMv_createtime() {
                    return mv_createtime;
                }

                public void setMv_createtime(String mv_createtime) {
                    this.mv_createtime = mv_createtime;
                }
            }
        }
    }
}

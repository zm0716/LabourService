package com.agilefinger.labourservice.bean;

import java.util.List;

public class StateBean {

    /**
     * code : 0
     * data : {"info":{"pics":[],"other":null,"explain":"cgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbdcbhdbchsbcubdhcbshbhcgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbd"}}
     */

    private int code;
    private DataBean data;

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

    public static class DataBean {
        /**
         * info : {"pics":[],"other":null,"explain":"cgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbdcbhdbchsbcubdhcbshbhcgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbd"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * pics : []
             * other : null
             * explain : cgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbdcbhdbchsbcubdhcbshbhcgyfycycycycycgcgcgcgcgcgcgcgcgcgcgcfcgcgcyvyvhvyecybdcunceudnubeuecuebcuencubfuebcuebcuhcuhcheuhufheubcuebcuubecbucbsjncunsbcusncusncuebcubububcdbcusbcusbcuscbububcubcubjbdcbdjcbjdbcjdbchdbchdbcjbjbshcbdhcbhdbchdbchdbchdbchdbchdbchsbchscbd
             */

            private Object other;
            private String explain;
            private List<?> pics;

            public Object getOther() {
                return other;
            }

            public void setOther(Object other) {
                this.other = other;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public List<?> getPics() {
                return pics;
            }

            public void setPics(List<?> pics) {
                this.pics = pics;
            }
        }
    }
}

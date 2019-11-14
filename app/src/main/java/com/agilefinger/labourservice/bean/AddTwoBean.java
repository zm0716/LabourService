package com.agilefinger.labourservice.bean;

import java.util.List;

public class AddTwoBean {
    /**
     * defaultTemplate : [{"tid":"1","name":"测试通用模版"}]
     * ptid :
     * pname :
     * cvid : 1
     * cvnum : 204
     * cvname : 测试通用模版
     * "is_batch": "y",
     * "is_check": "y",
     */

    private String ptid;
    private String pname;
    private String cvid;
    private int cvnum;
    private String cvname;
    private String is_batch;
    private String is_check;

    public String getIs_batch() {
        return is_batch;
    }

    public void setIs_batch(String is_batch) {
        this.is_batch = is_batch;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    private List<DefaultTemplateBean> defaultTemplate;

    public String getPtid() {
        return ptid;
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCvid() {
        return cvid;
    }

    public void setCvid(String cvid) {
        this.cvid = cvid;
    }

    public int getCvnum() {
        return cvnum;
    }

    public void setCvnum(int cvnum) {
        this.cvnum = cvnum;
    }

    public String getCvname() {
        return cvname;
    }

    public void setCvname(String cvname) {
        this.cvname = cvname;
    }

    public List<DefaultTemplateBean> getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(List<DefaultTemplateBean> defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }

    public static class DefaultTemplateBean {
        /**
         * tid : 1
         * name : 测试通用模版
         */

        private String tid;
        private String name;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

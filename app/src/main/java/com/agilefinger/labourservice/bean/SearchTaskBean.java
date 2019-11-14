package com.agilefinger.labourservice.bean;

import java.util.List;

public class SearchTaskBean {

    /**
     * code : 0
     * data : [{"projectID":7,"projectName":"测试项目0712","projectType":0,"projectNo":"P2019070007","companyName":"信发集团_test","endTime":"0000-00-00 00:00:00","projectFrom":"信发集团_test"},{"projectID":18,"projectName":"测试项目啦啦","projectType":0,"projectNo":"P2019070018","companyName":"信发集团_test","endTime":"0000-00-00 00:00:00","projectFrom":"信发集团_test"},{"projectID":20,"projectName":"测试项目经理","projectType":0,"projectNo":"P2019070020","companyName":"信发集团_test","endTime":"0000-00-00 00:00:00","projectFrom":"信发集团_test"}]
     * msg : 成功！
     */

    private int code;
    private String msg;
    private List<AddOneBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AddOneBean> getData() {
        return data;
    }

    public void setData(List<AddOneBean> data) {
        this.data = data;
    }


}

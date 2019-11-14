package com.agilefinger.labourservice.bean;

import java.util.List;

public class AddJcnRongBean {

    private int code;
    private String msg;
    private List<CatalogNodeBean> data;

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

    public List<CatalogNodeBean> getData() {
        return data;
    }

    public void setData(List<CatalogNodeBean> data) {
        this.data = data;
    }

    /**
     * 初始化数据的父节点指针和每个结点的分类全称
     */
    public void initAddJcnRongBean(){
//        initParentAddJcnRongBean();//初始化父节点指针
//        initCatalogFullName();//初始化每个结点的分类全称
        AddJcnRongBean addJcnRongBean = this;
        List<CatalogNodeBean> nodeList = addJcnRongBean.getData();
        if (nodeList.size() > 0) {
            for (int i = 0; i < nodeList.size(); i++) {
                //初始化每个结点的分类全称
                String perName = "";
                nodeList.get(i).initCatalogFullName(perName);
                //初始化父节点指针
                nodeList.get(i).initNodeParent();
            }
        }
    }

    /**
     * 获得选中结果的字符串
     * @return
     */
    public void getCheckedResult(StringBuilder resultCatalogString, StringBuilder resultItemString) {
        if(this.getData().size()>0){
            for (CatalogNodeBean node:this.getData()) {
                node.getCheckedResult(resultCatalogString, resultItemString);
            }
            if(resultCatalogString.length()>0){
                resultCatalogString.delete(0,1);
            }
            if(resultItemString.length()>0){
                resultItemString.delete(0,1);
            }
        }
    }

}

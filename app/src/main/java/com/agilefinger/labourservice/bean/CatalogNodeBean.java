package com.agilefinger.labourservice.bean;

import java.util.List;

public class CatalogNodeBean {
    private String tct_id;
    private String tct_p_id;
    private String tct_name;
    private String tct_front_show;
    private List<CheckItemBean> items;
    private List<CatalogNodeBean> son;
    private CatalogNodeBean parent; //父节点的指针
    private String catalogFullName;//当前结点的分类全称
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCatalogFullName() {
        return catalogFullName;
    }

    public void setCatalogFullName(String catalogFullName) {
        this.catalogFullName = catalogFullName;
    }

    public CatalogNodeBean getParent() {
        return parent;
    }

    public void setParent(CatalogNodeBean parent) {
        this.parent = parent;
    }

//    public int getParentIndex() {
//        return parentIndex;
//    }
//
//    public void setParentIndex(int parentIndex) {
//        this.parentIndex = parentIndex;
//    }

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 设置当前结点的选中状态
     *
     * @param checked
     */
    public void setChecked(boolean checked, boolean updateUp, boolean updateDown) {
        isChecked = checked;
        if (checked) { //如果是选中状态
            if (updateUp) {
                if (this.parent != null) {
                    //选中该结点的父节点
                    if (!this.parent.isChecked()) {
                        this.parent.setChecked(true, true, false);
                    }
                }
            }
            if (updateDown) {
                checkAllSub(true);//将当前分类下的所有分类及检查项全部选中
            }
        } else {
            if (updateUp) {
                if (this.parent != null) {
                    //检查其父节点的所有检查项是否都被取消，如果是，则清除父节点的选中状态
                    boolean clearParent = true;
                    if (this.parent.getItems() != null) {
                        for (CheckItemBean item : this.parent.getItems()) {
                            if (item.isSelected()) {
                                clearParent = false;
                                break;
                            }
                        }
                    }
                    if (clearParent) {
                        //检查其父节点的所有子节点是否都被取消，如果是，则清除父节点的选中状态
                        for (CatalogNodeBean subNode : this.parent.getSon()) {
                            if (subNode.isChecked()) {
                                clearParent = false;
                                break;
                            }
                        }
                    }
                    if (clearParent) {
                        if (this.parent.isChecked()) {
                            this.parent.setChecked(false, true, false);
                        }
                    }
                }
            }
            if (updateDown) {
                //清除当前结点下的所有检查项和子节点的选中状态
                checkAllSub(false);//将当前分类下的所有分类及检查项全部清除
            }
        }
    }

    public String getTct_id() {
        return tct_id;
    }

    public void setTct_id(String tct_id) {
        this.tct_id = tct_id;
    }

    public String getTct_p_id() {
        return tct_p_id;
    }

    public void setTct_p_id(String tct_p_id) {
        this.tct_p_id = tct_p_id;
    }

    public String getTct_name() {
        return tct_name;
    }

    public void setTct_name(String tct_name) {
        this.tct_name = tct_name;
    }

    public String getTct_front_show() {
        return tct_front_show;
    }

    public void setTct_front_show(String tct_front_show) {
        this.tct_front_show = tct_front_show;
    }

    public List<CheckItemBean> getItems() {
        return items;
    }

    public void setItems(List<CheckItemBean> items) {
        this.items = items;
    }

    public List<CatalogNodeBean> getSon() {
        return son;
    }

    public void setSon(List<CatalogNodeBean> son) {
        this.son = son;
    }


//    /*
//    将当前结点的所有父结点都设置为选中
//     */
//    public void checkAllParent() {
//        CatalogNodeBean node = this;
//        while(node.getParent()!=null){
//            CatalogNodeBean parentNode = node.getParent();
//            parentNode.setChecked(true);
//            node = parentNode;
//        }
//    }

    /*
    选中/清除当前结点的所有子节点的选中状态（以及所有检测项）
     */
    private void checkAllSub(boolean status) {
        CatalogNodeBean node = this;
        if (node.getSon() != null) {
            for (CatalogNodeBean subNode : node.getSon()) {
                if (subNode.isChecked() != status) {
                   // if (subNode.getTct_front_show().equals("y")){
                        subNode.setChecked(status, false, true);
                   // }

                }
            }
        }
        if (node.getItems() != null) {
            for (CheckItemBean item : node.getItems()) {
                if (item.isSelected() != status) {
                    item.setSelected(status);
                }

            }
        }
    }

    /*
    设置当前结点所有子节点的父节点指针
     */
    public void initNodeParent() {
        CatalogNodeBean node = this;
        if (node.getSon() != null) {
            for (CatalogNodeBean subNode : node.getSon()) {
               // if (subNode.getTct_front_show().equals("y")){
                    subNode.setParent(node);
                    subNode.initNodeParent();
             //   }

            }
        }
        if (node.getItems() != null) {
            for (CheckItemBean item : node.getItems()) {
                item.setParent(node);
            }
        }
    }

    /**
     * 设置当前结点的分类全称，递归
     *
     * @param perName 前缀
     */
    public void initCatalogFullName(String perName) {
        CatalogNodeBean node = this;
        String fullName = "";
        if(perName.length()>0){
            fullName = perName + ">" + node.getTct_name(); //全称=前缀+本级名称
        }else {
            fullName = node.getTct_name(); //全称=前缀+本级名称
        }
        node.setCatalogFullName(fullName); //设置全称
        if (node.getItems() != null) {
            for (CheckItemBean item : node.getItems()) {
                item.setCatalogFullName(fullName);
            }
        }
        if (node.getSon() != null) {
            //设置以下所有子节点的全称
            for (CatalogNodeBean subNode : node.getSon()) {
                subNode.initCatalogFullName(fullName);
            }
        }

    }

    /**
     * 获得选中结果的字符串
     * @param resultCatalogString
     * @param resultItemString
     */
    public void getCheckedResult(StringBuilder resultCatalogString, StringBuilder resultItemString) {
        if(this.isChecked()){
            resultCatalogString.append("," + this.getTct_id());
        }
        if(this.getItems() != null){
            for (CheckItemBean item:this.getItems()) {
                if(item.isSelected()){
                    resultItemString.append("," + item.getTci_id());
                }
            }
        }
        if(this.getSon() != null){
            for (CatalogNodeBean subNode:this.getSon()) {
               // if (subNode.getTct_front_show().equals("y")){
                    subNode.getCheckedResult(resultCatalogString, resultItemString);
              //  }
            }
        }
    }
}

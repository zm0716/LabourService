package com.agilefinger.labourservice.bean;

public class CheckItemBean {
    private String tci_id;
    private String tci_tct_id;
    private String tci_name;
    private String tci_front_show;
    private String tct_front_show;
    private String tct_p_id;
    private boolean selected = false;
    private String catalogFullName;//当前结点的分类全称
    private CatalogNodeBean parent;

    public CatalogNodeBean getParent() {
        return parent;
    }

    public void setParent(CatalogNodeBean parent) {
        this.parent = parent;
    }

    public String getCatalogFullName() {
        return catalogFullName;
    }

    public void setCatalogFullName(String catalogFullName) {
        this.catalogFullName = catalogFullName;
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * 选中/清除选中当前检查项
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected){ //如果是选中状态
            if(!this.parent.isChecked()) {
                //选中该检查项所属结点
                this.parent.setChecked(true, true, false);
            }
        }else {
            //检查其父节点的所有检查项是否都被取消，如果是，则清除父节点的选中状态
            boolean clearParent = true;
            for (CheckItemBean item:this.parent.getItems()) {
                if(item.isSelected()){
                    clearParent = false;
                    break;
                }
            }
            if(clearParent){
                this.parent.setChecked(false, true, false);
            }
        }
    }

    public String getTci_id() {
        return tci_id;
    }

    public void setTci_id(String tci_id) {
        this.tci_id = tci_id;
    }

    public String getTci_tct_id() {
        return tci_tct_id;
    }

    public void setTci_tct_id(String tci_tct_id) {
        this.tci_tct_id = tci_tct_id;
    }

    public String getTci_name() {
        return tci_name;
    }

    public void setTci_name(String tci_name) {
        this.tci_name = tci_name;
    }

    public String getTci_front_show() {
        return tci_front_show;
    }

    public void setTci_front_show(String tci_front_show) {
        this.tci_front_show = tci_front_show;
    }

    public String getTct_front_show() {
        return tct_front_show;
    }

    public void setTct_front_show(String tct_front_show) {
        this.tct_front_show = tct_front_show;
    }

    public String getTct_p_id() {
        return tct_p_id;
    }

    public void setTct_p_id(String tct_p_id) {
        this.tct_p_id = tct_p_id;
    }
}

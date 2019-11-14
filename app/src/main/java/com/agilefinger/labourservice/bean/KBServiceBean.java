package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;

import java.io.Serializable;
import java.util.List;

public class KBServiceBean implements PickerView.PickerItem, Serializable {


    /**
     * id : 278
     * name : 线下拓展专员总公司
     * parent_id : 0
     * subCompany : [{"id":279,"name":"线下拓展专员分公司","parent_id":278}]
     */

    private int id;
    private String name;
    private int parent_id;
    private boolean isCheck;
    private List<KBServiceBean> subCompany;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<KBServiceBean> getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(List<KBServiceBean> subCompany) {
        this.subCompany = subCompany;
    }

//    public static class SubCompanyBean implements PickerView.PickerItem, Serializable{
//        /**
//         * id : 279
//         * name : 线下拓展专员分公司
//         * parent_id : 278
//         */
//
//        private int id;
//        private String name;
//        private int parent_id;
//        private boolean isCheck;
//        private List<SubCompanyBean> subCompany;
//
//        public boolean isCheck() {
//            return isCheck;
//        }
//
//        public void setCheck(boolean check) {
//            isCheck = check;
//        }
//
//        public List<SubCompanyBean> getSubCompany() {
//            return subCompany;
//        }
//
//        public void setSubCompany(List<SubCompanyBean> subCompany) {
//            this.subCompany = subCompany;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getParent_id() {
//            return parent_id;
//        }
//
//        public void setParent_id(int parent_id) {
//            this.parent_id = parent_id;
//        }
//
//        @Override
//        public String getText() {
//            return null;
//        }
//    }

    @Override
    public String getText() {
        return null;
    }
}

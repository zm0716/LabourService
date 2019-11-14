package com.agilefinger.labourservice.bean;

import java.util.List;

public class AddressBean {
        private List<CanChooseBean> canChoose;
        private List<?> hasChoose;

        public List<CanChooseBean> getCanChoose() {
            return canChoose;
        }

        public void setCanChoose(List<CanChooseBean> canChoose) {
            this.canChoose = canChoose;
        }

        public List<?> getHasChoose() {
            return hasChoose;
        }

        public void setHasChoose(List<?> hasChoose) {
            this.hasChoose = hasChoose;
        }

        public static class CanChooseBean {
            /**
             * bz_id : 1
             * bz_name : 王大锤班组
             * bID : 1
             * fID : 1
             * iID : 1
             * name : 1号楼1F东立面
             */

            private String bz_id;
            private String bz_name;
            private String bID;
            private String fID;
            private String iID;
            private String name;

            public String getBz_id() {
                return bz_id;
            }

            public void setBz_id(String bz_id) {
                this.bz_id = bz_id;
            }

            public String getBz_name() {
                return bz_name;
            }

            public void setBz_name(String bz_name) {
                this.bz_name = bz_name;
            }

            public String getBID() {
                return bID;
            }

            public void setBID(String bID) {
                this.bID = bID;
            }

            public String getFID() {
                return fID;
            }

            public void setFID(String fID) {
                this.fID = fID;
            }

            public String getIID() {
                return iID;
            }

            public void setIID(String iID) {
                this.iID = iID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

}

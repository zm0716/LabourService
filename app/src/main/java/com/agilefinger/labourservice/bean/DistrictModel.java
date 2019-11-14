package com.agilefinger.labourservice.bean;

import java.io.Serializable;

public class DistrictModel implements Serializable {
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

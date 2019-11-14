package com.agilefinger.labourservice.bean;

public class WeatherEndBean {

    /**
     * code : 0
     * message : 成功
     * data : {"tq_info_id":108,"province":210000,"city":210100,"area":2,"parent_city":"天津","admin_area":"天津","location":"南开","create_time":"2019-08-21 17:10:21","tq_content":{"basic":{"cid":"CN101031500","location":"南开","parent_city":"天津","admin_area":"天津","cnty":"中国","lat":"39.12047577","lon":"117.16414642","tz":"+8.00"},"update":{"loc":"2019-08-21 17:09","utc":"2019-08-21 09:09"},"status":"ok","now":{"cloud":"10","cond_code":"100","cond_txt":"晴","fl":"30","hum":"30","pcpn":"0.0","pres":"1008","tmp":"30","vis":"16","wind_deg":"286","wind_dir":"西北风","wind_sc":"1","wind_spd":"3"}},"longitude":"39.10","latitude":"117.15","addition":""}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tq_info_id : 108
         * province : 210000
         * city : 210100
         * area : 2
         * parent_city : 天津
         * admin_area : 天津
         * location : 南开
         * create_time : 2019-08-21 17:10:21
         * tq_content : {"basic":{"cid":"CN101031500","location":"南开","parent_city":"天津","admin_area":"天津","cnty":"中国","lat":"39.12047577","lon":"117.16414642","tz":"+8.00"},"update":{"loc":"2019-08-21 17:09","utc":"2019-08-21 09:09"},"status":"ok","now":{"cloud":"10","cond_code":"100","cond_txt":"晴","fl":"30","hum":"30","pcpn":"0.0","pres":"1008","tmp":"30","vis":"16","wind_deg":"286","wind_dir":"西北风","wind_sc":"1","wind_spd":"3"}}
         * longitude : 39.10
         * latitude : 117.15
         * addition :
         */

        private int tq_info_id;
        private int province;
        private int city;
        private int area;
        private String parent_city;
        private String admin_area;
        private String location;
        private String create_time;
        private TqContentBean tq_content;
        private String longitude;
        private String latitude;
        private String addition;

        public int getTq_info_id() {
            return tq_info_id;
        }

        public void setTq_info_id(int tq_info_id) {
            this.tq_info_id = tq_info_id;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public TqContentBean getTq_content() {
            return tq_content;
        }

        public void setTq_content(TqContentBean tq_content) {
            this.tq_content = tq_content;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getAddition() {
            return addition;
        }

        public void setAddition(String addition) {
            this.addition = addition;
        }

        public static class TqContentBean {
            /**
             * basic : {"cid":"CN101031500","location":"南开","parent_city":"天津","admin_area":"天津","cnty":"中国","lat":"39.12047577","lon":"117.16414642","tz":"+8.00"}
             * update : {"loc":"2019-08-21 17:09","utc":"2019-08-21 09:09"}
             * status : ok
             * now : {"cloud":"10","cond_code":"100","cond_txt":"晴","fl":"30","hum":"30","pcpn":"0.0","pres":"1008","tmp":"30","vis":"16","wind_deg":"286","wind_dir":"西北风","wind_sc":"1","wind_spd":"3"}
             */

            private BasicBean basic;
            private UpdateBean update;
            private String status;
            private NowBean now;

            public BasicBean getBasic() {
                return basic;
            }

            public void setBasic(BasicBean basic) {
                this.basic = basic;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public NowBean getNow() {
                return now;
            }

            public void setNow(NowBean now) {
                this.now = now;
            }

            public static class BasicBean {
                /**
                 * cid : CN101031500
                 * location : 南开
                 * parent_city : 天津
                 * admin_area : 天津
                 * cnty : 中国
                 * lat : 39.12047577
                 * lon : 117.16414642
                 * tz : +8.00
                 */

                private String cid;
                private String location;
                private String parent_city;
                private String admin_area;
                private String cnty;
                private String lat;
                private String lon;
                private String tz;

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getParent_city() {
                    return parent_city;
                }

                public void setParent_city(String parent_city) {
                    this.parent_city = parent_city;
                }

                public String getAdmin_area() {
                    return admin_area;
                }

                public void setAdmin_area(String admin_area) {
                    this.admin_area = admin_area;
                }

                public String getCnty() {
                    return cnty;
                }

                public void setCnty(String cnty) {
                    this.cnty = cnty;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLon() {
                    return lon;
                }

                public void setLon(String lon) {
                    this.lon = lon;
                }

                public String getTz() {
                    return tz;
                }

                public void setTz(String tz) {
                    this.tz = tz;
                }
            }

            public static class UpdateBean {
                /**
                 * loc : 2019-08-21 17:09
                 * utc : 2019-08-21 09:09
                 */

                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }

            public static class NowBean {
                /**
                 * cloud : 10
                 * cond_code : 100
                 * cond_txt : 晴
                 * fl : 30
                 * hum : 30
                 * pcpn : 0.0
                 * pres : 1008
                 * tmp : 30
                 * vis : 16
                 * wind_deg : 286
                 * wind_dir : 西北风
                 * wind_sc : 1
                 * wind_spd : 3
                 */

                private String cloud;
                private String cond_code;
                private String cond_txt;
                private String fl;
                private String hum;
                private String pcpn;
                private String pres;
                private String tmp;
                private String vis;
                private String wind_deg;
                private String wind_dir;
                private String wind_sc;
                private String wind_spd;

                public String getCloud() {
                    return cloud;
                }

                public void setCloud(String cloud) {
                    this.cloud = cloud;
                }

                public String getCond_code() {
                    return cond_code;
                }

                public void setCond_code(String cond_code) {
                    this.cond_code = cond_code;
                }

                public String getCond_txt() {
                    return cond_txt;
                }

                public void setCond_txt(String cond_txt) {
                    this.cond_txt = cond_txt;
                }

                public String getFl() {
                    return fl;
                }

                public void setFl(String fl) {
                    this.fl = fl;
                }

                public String getHum() {
                    return hum;
                }

                public void setHum(String hum) {
                    this.hum = hum;
                }

                public String getPcpn() {
                    return pcpn;
                }

                public void setPcpn(String pcpn) {
                    this.pcpn = pcpn;
                }

                public String getPres() {
                    return pres;
                }

                public void setPres(String pres) {
                    this.pres = pres;
                }

                public String getTmp() {
                    return tmp;
                }

                public void setTmp(String tmp) {
                    this.tmp = tmp;
                }

                public String getVis() {
                    return vis;
                }

                public void setVis(String vis) {
                    this.vis = vis;
                }

                public String getWind_deg() {
                    return wind_deg;
                }

                public void setWind_deg(String wind_deg) {
                    this.wind_deg = wind_deg;
                }

                public String getWind_dir() {
                    return wind_dir;
                }

                public void setWind_dir(String wind_dir) {
                    this.wind_dir = wind_dir;
                }

                public String getWind_sc() {
                    return wind_sc;
                }

                public void setWind_sc(String wind_sc) {
                    this.wind_sc = wind_sc;
                }

                public String getWind_spd() {
                    return wind_spd;
                }

                public void setWind_spd(String wind_spd) {
                    this.wind_spd = wind_spd;
                }
            }
        }
    }
}

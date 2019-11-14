package com.agilefinger.labourservice.bean;

import java.util.List;

public class PointDailsBean {

    /**
     * code : 0
     * message : 成功
     * data : {"mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","mci_name":"平整度平整度","mci_no":"TC01789000","mci_type":"1","mci_explain":"\n不大于3毫米","mci_remark":"\n不大于3毫米","mci_need_pid":"y","mci_min_point":3,"mi_p_id":"121","mi_id":"2B3F569AF6A18B02DE460B8F3ECEEB5F","unit":"mm","p_name":"精准算分","type_info":[{"mv_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","mv_no":1,"mv_min":0,"mv_max":300,"mv_score":0,"mv_unit":"mm","mv_creator":"2507","mv_createtime":"2019-09-08 17:57:29"}],"point":[{"rp_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","rp_mi_no":1,"rp_no":1,"rp_b_id":"210","rp_f_id":"6111","rp_i_id":"837","rp_position_des":"","rp_is_batch":"y","rp_result":"88","rp_is_pass":"n","rp_is_desc_del":"y","rp_remark":"","rp_creator":"2507","rp_createtime":"2019-09-09 14:47:38","imgList":[{"pp_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","pp_rp_no":1,"pp_mi_no":"1","pp_pi_id":"1ED5B8E10202FE160268B7815D11EE53","pi_id":"1ED5B8E10202FE160268B7815D11EE53","pi_creator":"2507","pi_p_id":"121","pi_b_id":null,"pi_f_id":null,"pi_i_id":null,"pi_url":"/Public/Uploads/img/41a3a3489b57a9522ad2bacc493d57fc.jpg","pi_watermark_url":"/Public/Uploads/img/a89865a5ca8a6c707fcaa3b35c928719.jpg","pi_longitude":0,"pi_latitude":0,"pi_height":0,"pi_address":"中国天津市南开区欣苑路11在天津市南开区水上公园街社区卫生服务中心附近","pi_remark":null,"pi_createtime":"2019-09-09 14:47:37"}],"name":"#1 B2-东1"}]}
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
         * mci_id : 0AE082CC2F8315FC1C6FC7F8D38536E7
         * mci_name : 平整度平整度
         * mci_no : TC01789000
         * mci_type : 1
         * mci_explain :
         不大于3毫米
         * mci_remark :
         不大于3毫米
         * mci_need_pid : y
         * mci_min_point : 3
         * mi_p_id : 121
         * mi_id : 2B3F569AF6A18B02DE460B8F3ECEEB5F
         * unit : mm
         * p_name : 精准算分
         * type_info : [{"mv_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","mv_no":1,"mv_min":0,"mv_max":300,"mv_score":0,"mv_unit":"mm","mv_creator":"2507","mv_createtime":"2019-09-08 17:57:29"}]
         * point : [{"rp_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","rp_mi_no":1,"rp_no":1,"rp_b_id":"210","rp_f_id":"6111","rp_i_id":"837","rp_position_des":"","rp_is_batch":"y","rp_result":"88","rp_is_pass":"n","rp_is_desc_del":"y","rp_remark":"","rp_creator":"2507","rp_createtime":"2019-09-09 14:47:38","imgList":[{"pp_mci_id":"0AE082CC2F8315FC1C6FC7F8D38536E7","pp_rp_no":1,"pp_mi_no":"1","pp_pi_id":"1ED5B8E10202FE160268B7815D11EE53","pi_id":"1ED5B8E10202FE160268B7815D11EE53","pi_creator":"2507","pi_p_id":"121","pi_b_id":null,"pi_f_id":null,"pi_i_id":null,"pi_url":"/Public/Uploads/img/41a3a3489b57a9522ad2bacc493d57fc.jpg","pi_watermark_url":"/Public/Uploads/img/a89865a5ca8a6c707fcaa3b35c928719.jpg","pi_longitude":0,"pi_latitude":0,"pi_height":0,"pi_address":"中国天津市南开区欣苑路11在天津市南开区水上公园街社区卫生服务中心附近","pi_remark":null,"pi_createtime":"2019-09-09 14:47:37"}],"name":"#1 B2-东1"}]
         */

        private String mci_id;
        private String mci_name;
        private String mci_no;
        private String mci_type;
        private String mci_explain;
        private String mci_remark;
        private String mci_need_pid;
        private int mci_min_point;
        private String mi_p_id;
        private String mi_id;
        private String unit;
        private String p_name;
        private List<TypeInfoBean> type_info;
        private List<PointBean> point;

        public String getMci_id() {
            return mci_id;
        }

        public void setMci_id(String mci_id) {
            this.mci_id = mci_id;
        }

        public String getMci_name() {
            return mci_name;
        }

        public void setMci_name(String mci_name) {
            this.mci_name = mci_name;
        }

        public String getMci_no() {
            return mci_no;
        }

        public void setMci_no(String mci_no) {
            this.mci_no = mci_no;
        }

        public String getMci_type() {
            return mci_type;
        }

        public void setMci_type(String mci_type) {
            this.mci_type = mci_type;
        }

        public String getMci_explain() {
            return mci_explain;
        }

        public void setMci_explain(String mci_explain) {
            this.mci_explain = mci_explain;
        }

        public String getMci_remark() {
            return mci_remark;
        }

        public void setMci_remark(String mci_remark) {
            this.mci_remark = mci_remark;
        }

        public String getMci_need_pid() {
            return mci_need_pid;
        }

        public void setMci_need_pid(String mci_need_pid) {
            this.mci_need_pid = mci_need_pid;
        }

        public int getMci_min_point() {
            return mci_min_point;
        }

        public void setMci_min_point(int mci_min_point) {
            this.mci_min_point = mci_min_point;
        }

        public String getMi_p_id() {
            return mi_p_id;
        }

        public void setMi_p_id(String mi_p_id) {
            this.mi_p_id = mi_p_id;
        }

        public String getMi_id() {
            return mi_id;
        }

        public void setMi_id(String mi_id) {
            this.mi_id = mi_id;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public List<TypeInfoBean> getType_info() {
            return type_info;
        }

        public void setType_info(List<TypeInfoBean> type_info) {
            this.type_info = type_info;
        }

        public List<PointBean> getPoint() {
            return point;
        }

        public void setPoint(List<PointBean> point) {
            this.point = point;
        }

        public static class TypeInfoBean {
            /**
             * mv_mci_id : 0AE082CC2F8315FC1C6FC7F8D38536E7
             * mv_no : 1
             * mv_min : 0
             * mv_max : 300
             * mv_score : 0
             * mv_unit : mm
             * mv_creator : 2507
             * mv_createtime : 2019-09-08 17:57:29
             */

            private String mv_mci_id;
            private int mv_no;
            private int mv_min;
            private int mv_max;
            private int mv_score;
            private String mv_unit;
            private String mv_creator;
            private String mv_createtime;

            public String getMv_mci_id() {
                return mv_mci_id;
            }

            public void setMv_mci_id(String mv_mci_id) {
                this.mv_mci_id = mv_mci_id;
            }

            public int getMv_no() {
                return mv_no;
            }

            public void setMv_no(int mv_no) {
                this.mv_no = mv_no;
            }

            public int getMv_min() {
                return mv_min;
            }

            public void setMv_min(int mv_min) {
                this.mv_min = mv_min;
            }

            public int getMv_max() {
                return mv_max;
            }

            public void setMv_max(int mv_max) {
                this.mv_max = mv_max;
            }

            public int getMv_score() {
                return mv_score;
            }

            public void setMv_score(int mv_score) {
                this.mv_score = mv_score;
            }

            public String getMv_unit() {
                return mv_unit;
            }

            public void setMv_unit(String mv_unit) {
                this.mv_unit = mv_unit;
            }

            public String getMv_creator() {
                return mv_creator;
            }

            public void setMv_creator(String mv_creator) {
                this.mv_creator = mv_creator;
            }

            public String getMv_createtime() {
                return mv_createtime;
            }

            public void setMv_createtime(String mv_createtime) {
                this.mv_createtime = mv_createtime;
            }
        }

        public static class PointBean {

            /**
             * imgList : [{"pi_address":"天津市南开区欣苑路靠近水上公园医院","pi_createtime":"2019-10-15 14:07:31","pi_creator":"2507","pi_height":105.405,"pi_id":"879CAB8FE5DB178EB3EBC2A41C812D30","pi_latitude":39,"pi_longitude":117,"pi_p_id":"147","pi_url":"/Public/Uploads/img/85b882bb20073fe0a6fde14e675e878b.jpg","pi_watermark_url":"/Public/Uploads/img/4cdf7f0d18a2a37bddb79b069218afe8.jpg","pp_mci_id":"0CA2E49112018B802A5688DAF16286C9","pp_mi_no":"1","pp_pi_id":"879CAB8FE5DB178EB3EBC2A41C812D30","pp_rp_no":4}]
             * name : #3 B3-东立面
             * rp_b_id : 403
             * rp_createtime : 2019-10-15 14:07:40
             * rp_creator : 2507
             * rp_f_id : 19787
             * rp_i_id : 1511
             * rp_is_batch : y
             * rp_is_desc_del : y
             * rp_is_pass : y
             * rp_mci_id : 0CA2E49112018B802A5688DAF16286C9
             * rp_mi_no : 1
             * rp_no : 4
             * rp_position_des : kl
             * rp_remark :
             * rp_result : 合格
             */

            private String name;
            private String rp_b_id;
            private String rp_createtime;
            private String rp_creator;
            private String rp_f_id;
            private String rp_i_id;
            private String rp_is_batch;
            private String rp_is_desc_del;
            private String rp_is_pass;
            private String rp_mci_id;
            private int rp_mi_no;
            private int rp_no;
            private String rp_position_des;
            private String rp_remark;
            private String rp_result;
            private List<ImgListBean> imgList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRp_b_id() {
                return rp_b_id;
            }

            public void setRp_b_id(String rp_b_id) {
                this.rp_b_id = rp_b_id;
            }

            public String getRp_createtime() {
                return rp_createtime;
            }

            public void setRp_createtime(String rp_createtime) {
                this.rp_createtime = rp_createtime;
            }

            public String getRp_creator() {
                return rp_creator;
            }

            public void setRp_creator(String rp_creator) {
                this.rp_creator = rp_creator;
            }

            public String getRp_f_id() {
                return rp_f_id;
            }

            public void setRp_f_id(String rp_f_id) {
                this.rp_f_id = rp_f_id;
            }

            public String getRp_i_id() {
                return rp_i_id;
            }

            public void setRp_i_id(String rp_i_id) {
                this.rp_i_id = rp_i_id;
            }

            public String getRp_is_batch() {
                return rp_is_batch;
            }

            public void setRp_is_batch(String rp_is_batch) {
                this.rp_is_batch = rp_is_batch;
            }

            public String getRp_is_desc_del() {
                return rp_is_desc_del;
            }

            public void setRp_is_desc_del(String rp_is_desc_del) {
                this.rp_is_desc_del = rp_is_desc_del;
            }

            public String getRp_is_pass() {
                return rp_is_pass;
            }

            public void setRp_is_pass(String rp_is_pass) {
                this.rp_is_pass = rp_is_pass;
            }

            public String getRp_mci_id() {
                return rp_mci_id;
            }

            public void setRp_mci_id(String rp_mci_id) {
                this.rp_mci_id = rp_mci_id;
            }

            public int getRp_mi_no() {
                return rp_mi_no;
            }

            public void setRp_mi_no(int rp_mi_no) {
                this.rp_mi_no = rp_mi_no;
            }

            public int getRp_no() {
                return rp_no;
            }

            public void setRp_no(int rp_no) {
                this.rp_no = rp_no;
            }

            public String getRp_position_des() {
                return rp_position_des;
            }

            public void setRp_position_des(String rp_position_des) {
                this.rp_position_des = rp_position_des;
            }

            public String getRp_remark() {
                return rp_remark;
            }

            public void setRp_remark(String rp_remark) {
                this.rp_remark = rp_remark;
            }

            public String getRp_result() {
                return rp_result;
            }

            public void setRp_result(String rp_result) {
                this.rp_result = rp_result;
            }

            public List<ImgListBean> getImgList() {
                return imgList;
            }

            public void setImgList(List<ImgListBean> imgList) {
                this.imgList = imgList;
            }

            public static class ImgListBean {
                /**
                 * pi_address : 天津市南开区欣苑路靠近水上公园医院
                 * pi_createtime : 2019-10-15 14:07:31
                 * pi_creator : 2507
                 * pi_height : 105.405
                 * pi_id : 879CAB8FE5DB178EB3EBC2A41C812D30
                 * pi_latitude : 39
                 * pi_longitude : 117
                 * pi_p_id : 147
                 * pi_url : /Public/Uploads/img/85b882bb20073fe0a6fde14e675e878b.jpg
                 * pi_watermark_url : /Public/Uploads/img/4cdf7f0d18a2a37bddb79b069218afe8.jpg
                 * pp_mci_id : 0CA2E49112018B802A5688DAF16286C9
                 * pp_mi_no : 1
                 * pp_pi_id : 879CAB8FE5DB178EB3EBC2A41C812D30
                 * pp_rp_no : 4
                 */

                private String pi_address;
                private String pi_createtime;
                private String pi_creator;
                private double pi_height;
                private String pi_id;
                private int pi_latitude;
                private int pi_longitude;
                private String pi_p_id;
                private String pi_url;
                private String pi_watermark_url;
                private String pp_mci_id;
                private String pp_mi_no;
                private String pp_pi_id;
                private int pp_rp_no;

                public String getPi_address() {
                    return pi_address;
                }

                public void setPi_address(String pi_address) {
                    this.pi_address = pi_address;
                }

                public String getPi_createtime() {
                    return pi_createtime;
                }

                public void setPi_createtime(String pi_createtime) {
                    this.pi_createtime = pi_createtime;
                }

                public String getPi_creator() {
                    return pi_creator;
                }

                public void setPi_creator(String pi_creator) {
                    this.pi_creator = pi_creator;
                }

                public double getPi_height() {
                    return pi_height;
                }

                public void setPi_height(double pi_height) {
                    this.pi_height = pi_height;
                }

                public String getPi_id() {
                    return pi_id;
                }

                public void setPi_id(String pi_id) {
                    this.pi_id = pi_id;
                }

                public int getPi_latitude() {
                    return pi_latitude;
                }

                public void setPi_latitude(int pi_latitude) {
                    this.pi_latitude = pi_latitude;
                }

                public int getPi_longitude() {
                    return pi_longitude;
                }

                public void setPi_longitude(int pi_longitude) {
                    this.pi_longitude = pi_longitude;
                }

                public String getPi_p_id() {
                    return pi_p_id;
                }

                public void setPi_p_id(String pi_p_id) {
                    this.pi_p_id = pi_p_id;
                }

                public String getPi_url() {
                    return pi_url;
                }

                public void setPi_url(String pi_url) {
                    this.pi_url = pi_url;
                }

                public String getPi_watermark_url() {
                    return pi_watermark_url;
                }

                public void setPi_watermark_url(String pi_watermark_url) {
                    this.pi_watermark_url = pi_watermark_url;
                }

                public String getPp_mci_id() {
                    return pp_mci_id;
                }

                public void setPp_mci_id(String pp_mci_id) {
                    this.pp_mci_id = pp_mci_id;
                }

                public String getPp_mi_no() {
                    return pp_mi_no;
                }

                public void setPp_mi_no(String pp_mi_no) {
                    this.pp_mi_no = pp_mi_no;
                }

                public String getPp_pi_id() {
                    return pp_pi_id;
                }

                public void setPp_pi_id(String pp_pi_id) {
                    this.pp_pi_id = pp_pi_id;
                }

                public int getPp_rp_no() {
                    return pp_rp_no;
                }

                public void setPp_rp_no(int pp_rp_no) {
                    this.pp_rp_no = pp_rp_no;
                }
            }
        }
    }
}

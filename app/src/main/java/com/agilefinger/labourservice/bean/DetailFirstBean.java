package com.agilefinger.labourservice.bean;

import java.util.List;

public class DetailFirstBean {



    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {


        private InfoBean info;
        private boolean hasInput;
        private String pID;
        private List<TreeBean> tree;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public boolean isHasInput() {
            return hasInput;
        }

        public void setHasInput(boolean hasInput) {
            this.hasInput = hasInput;
        }

        public String getPID() {
            return pID;
        }

        public void setPID(String pID) {
            this.pID = pID;
        }

        public List<TreeBean> getTree() {
            return tree;
        }

        public void setTree(List<TreeBean> tree) {
            this.tree = tree;
        }

        public static class InfoBean {


            private OtherBean other;
            private String explain;
            private List<PicsBean> pics;

            public OtherBean getOther() {
                return other;
            }

            public void setOther(OtherBean other) {
                this.other = other;
            }

            public String getExplain() {
                return explain;
            }

            public void setExplain(String explain) {
                this.explain = explain;
            }

            public List<PicsBean> getPics() {
                return pics;
            }

            public void setPics(List<PicsBean> pics) {
                this.pics = pics;
            }

            public static class OtherBean {
                /**
                 * moi_mi_id : 19EEDA27BCB1CD37FCAF4921A64B79FF
                 * moi_mi_no : 1
                 * moi_b_id : 1
                 * moi_f_id : 1
                 * moi_i_id : 1
                 * moi_poi_des :
                 * moi_b_name :
                 * moi_f_name :
                 * moi_i_name :
                 */

                private String moi_mi_id;
                private int moi_mi_no;
                private String moi_b_id;
                private String moi_f_id;
                private String moi_i_id;
                private String moi_poi_des;
                private String moi_b_name;
                private String moi_f_name;
                private String moi_i_name;

                public String getMoi_mi_id() {
                    return moi_mi_id;
                }

                public void setMoi_mi_id(String moi_mi_id) {
                    this.moi_mi_id = moi_mi_id;
                }

                public int getMoi_mi_no() {
                    return moi_mi_no;
                }

                public void setMoi_mi_no(int moi_mi_no) {
                    this.moi_mi_no = moi_mi_no;
                }

                public String getMoi_b_id() {
                    return moi_b_id;
                }

                public void setMoi_b_id(String moi_b_id) {
                    this.moi_b_id = moi_b_id;
                }

                public String getMoi_f_id() {
                    return moi_f_id;
                }

                public void setMoi_f_id(String moi_f_id) {
                    this.moi_f_id = moi_f_id;
                }

                public String getMoi_i_id() {
                    return moi_i_id;
                }

                public void setMoi_i_id(String moi_i_id) {
                    this.moi_i_id = moi_i_id;
                }

                public String getMoi_poi_des() {
                    return moi_poi_des;
                }

                public void setMoi_poi_des(String moi_poi_des) {
                    this.moi_poi_des = moi_poi_des;
                }

                public String getMoi_b_name() {
                    return moi_b_name;
                }

                public void setMoi_b_name(String moi_b_name) {
                    this.moi_b_name = moi_b_name;
                }

                public String getMoi_f_name() {
                    return moi_f_name;
                }

                public void setMoi_f_name(String moi_f_name) {
                    this.moi_f_name = moi_f_name;
                }

                public String getMoi_i_name() {
                    return moi_i_name;
                }

                public void setMoi_i_name(String moi_i_name) {
                    this.moi_i_name = moi_i_name;
                }
            }

            public static class PicsBean {
                /**
                 * cp_mi_id : 19EEDA27BCB1CD37FCAF4921A64B79FF
                 * cp_mi_no : 1
                 * cp_pi_id : C1C60705A7EA76025C25146EBF9551A6
                 * pi_id : C1C60705A7EA76025C25146EBF9551A6
                 * pi_creator : 44
                 * pi_p_id : 1
                 * pi_b_id : null
                 * pi_f_id : null
                 * pi_i_id : null
                 * pi_url : /Public/Uploads/img/0ac44852d7276c1803c0a773f5748deb.jpg
                 * pi_watermark_url : /Public/Uploads/img/df100a79f81c86ccec88afa5f0561de0.jpg
                 * pi_longitude : 117
                 * pi_latitude : 39
                 * pi_height : 65
                 * pi_address : 天津市西青区金峰路靠近金瀚园
                 * pi_remark : null
                 * pi_createtime : 2019-08-12 20:19:04
                 */

                private String cp_mi_id;
                private int cp_mi_no;
                private String cp_pi_id;
                private String pi_id;
                private String pi_creator;
                private String pi_p_id;
                private Object pi_b_id;
                private Object pi_f_id;
                private Object pi_i_id;
                private String pi_url;
                private String pi_watermark_url;
                private long pi_longitude;
                private long pi_latitude;
                private long pi_height;
                private String pi_address;
                private Object pi_remark;
                private String pi_createtime;

                public String getCp_mi_id() {
                    return cp_mi_id;
                }

                public void setCp_mi_id(String cp_mi_id) {
                    this.cp_mi_id = cp_mi_id;
                }

                public int getCp_mi_no() {
                    return cp_mi_no;
                }

                public void setCp_mi_no(int cp_mi_no) {
                    this.cp_mi_no = cp_mi_no;
                }

                public String getCp_pi_id() {
                    return cp_pi_id;
                }

                public void setCp_pi_id(String cp_pi_id) {
                    this.cp_pi_id = cp_pi_id;
                }

                public String getPi_id() {
                    return pi_id;
                }

                public void setPi_id(String pi_id) {
                    this.pi_id = pi_id;
                }

                public String getPi_creator() {
                    return pi_creator;
                }

                public void setPi_creator(String pi_creator) {
                    this.pi_creator = pi_creator;
                }

                public String getPi_p_id() {
                    return pi_p_id;
                }

                public void setPi_p_id(String pi_p_id) {
                    this.pi_p_id = pi_p_id;
                }

                public Object getPi_b_id() {
                    return pi_b_id;
                }

                public void setPi_b_id(Object pi_b_id) {
                    this.pi_b_id = pi_b_id;
                }

                public Object getPi_f_id() {
                    return pi_f_id;
                }

                public void setPi_f_id(Object pi_f_id) {
                    this.pi_f_id = pi_f_id;
                }

                public Object getPi_i_id() {
                    return pi_i_id;
                }

                public void setPi_i_id(Object pi_i_id) {
                    this.pi_i_id = pi_i_id;
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

                public long getPi_longitude() {
                    return pi_longitude;
                }

                public void setPi_longitude(long pi_longitude) {
                    this.pi_longitude = pi_longitude;
                }

                public long getPi_latitude() {
                    return pi_latitude;
                }

                public void setPi_latitude(long pi_latitude) {
                    this.pi_latitude = pi_latitude;
                }

                public long getPi_height() {
                    return pi_height;
                }

                public void setPi_height(long pi_height) {
                    this.pi_height = pi_height;
                }

                public String getPi_address() {
                    return pi_address;
                }

                public void setPi_address(String pi_address) {
                    this.pi_address = pi_address;
                }

                public Object getPi_remark() {
                    return pi_remark;
                }

                public void setPi_remark(Object pi_remark) {
                    this.pi_remark = pi_remark;
                }

                public String getPi_createtime() {
                    return pi_createtime;
                }

                public void setPi_createtime(String pi_createtime) {
                    this.pi_createtime = pi_createtime;
                }
            }
        }

        public static class TreeBean {


            private String mct_id;
            private String mct_p_id;
            private String mct_name;
            private String mct_is_batch;
            private List<SonBeanX> son;
            private List<ItemsBeanX> items;

            public String getMct_is_batch() {
                return mct_is_batch;
            }

            public void setMct_is_batch(String mct_is_batch) {
                this.mct_is_batch = mct_is_batch;
            }

            public String getMct_id() {
                return mct_id;
            }

            public void setMct_id(String mct_id) {
                this.mct_id = mct_id;
            }

            public String getMct_p_id() {
                return mct_p_id;
            }

            public void setMct_p_id(String mct_p_id) {
                this.mct_p_id = mct_p_id;
            }

            public String getMct_name() {
                return mct_name;
            }

            public void setMct_name(String mct_name) {
                this.mct_name = mct_name;
            }

            public List<SonBeanX> getSon() {
                return son;
            }

            public void setSon(List<SonBeanX> son) {
                this.son = son;
            }

            public List<ItemsBeanX> getItems() {
                return items;
            }

            public void setItems(List<ItemsBeanX> items) {
                this.items = items;
            }

            public static class SonBeanX {

                private String mct_id;
                private String mct_p_id;
                private String mct_name;
                private List<SonBean> son;

                public String getMct_id() {
                    return mct_id;
                }

                public void setMct_id(String mct_id) {
                    this.mct_id = mct_id;
                }

                public String getMct_p_id() {
                    return mct_p_id;
                }

                public void setMct_p_id(String mct_p_id) {
                    this.mct_p_id = mct_p_id;
                }

                public String getMct_name() {
                    return mct_name;
                }

                public void setMct_name(String mct_name) {
                    this.mct_name = mct_name;
                }

                public List<SonBean> getSon() {
                    return son;
                }

                public void setSon(List<SonBean> son) {
                    this.son = son;
                }

                public static class SonBean {


                    private String mct_id;
                    private String mct_p_id;
                    private String mct_name;
                    private List<ItemsBean> items;

                    public String getMct_id() {
                        return mct_id;
                    }

                    public void setMct_id(String mct_id) {
                        this.mct_id = mct_id;
                    }

                    public String getMct_p_id() {
                        return mct_p_id;
                    }

                    public void setMct_p_id(String mct_p_id) {
                        this.mct_p_id = mct_p_id;
                    }

                    public String getMct_name() {
                        return mct_name;
                    }

                    public void setMct_name(String mct_name) {
                        this.mct_name = mct_name;
                    }

                    public List<ItemsBean> getItems() {
                        return items;
                    }

                    public void setItems(List<ItemsBean> items) {
                        this.items = items;
                    }

                    public static class ItemsBean {
                        /**
                         * mci_id : 07F6854CBB8D2B6F7C8D093F358F3828
                         * mci_mct_id : 3D9A916391A00BEA568A221682AAE0AD
                         * mci_name : 检测项编号X073
                         * mci_no : 164
                         * mci_min_point : 0
                         * p_count : 0
                         */

                        private String mci_id;
                        private String mci_mct_id;
                        private String mci_name;
                        private String mci_no;
                        private int mci_min_point;
                        private int p_count;

                        public String getMci_id() {
                            return mci_id;
                        }

                        public void setMci_id(String mci_id) {
                            this.mci_id = mci_id;
                        }

                        public String getMci_mct_id() {
                            return mci_mct_id;
                        }

                        public void setMci_mct_id(String mci_mct_id) {
                            this.mci_mct_id = mci_mct_id;
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

                        public int getMci_min_point() {
                            return mci_min_point;
                        }

                        public void setMci_min_point(int mci_min_point) {
                            this.mci_min_point = mci_min_point;
                        }

                        public int getP_count() {
                            return p_count;
                        }

                        public void setP_count(int p_count) {
                            this.p_count = p_count;
                        }
                    }
                }
            }

            public static class ItemsBeanX {
                /**
                 * mci_id : 0EEB4508D9C86103AC634D385BA8B5B2
                 * mci_mct_id : A25CD5F823798942D00482B2EC096511
                 * mci_name : 检测项编号X118
                 * mci_no : 100
                 * mci_min_point : 0
                 * p_count : 0
                 */

                private String mci_id;
                private String mci_mct_id;
                private String mci_name;
                private String mci_no;
                private int mci_min_point;
                private int p_count;

                public String getMci_id() {
                    return mci_id;
                }

                public void setMci_id(String mci_id) {
                    this.mci_id = mci_id;
                }

                public String getMci_mct_id() {
                    return mci_mct_id;
                }

                public void setMci_mct_id(String mci_mct_id) {
                    this.mci_mct_id = mci_mct_id;
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

                public int getMci_min_point() {
                    return mci_min_point;
                }

                public void setMci_min_point(int mci_min_point) {
                    this.mci_min_point = mci_min_point;
                }

                public int getP_count() {
                    return p_count;
                }

                public void setP_count(int p_count) {
                    this.p_count = p_count;
                }
            }
        }
    }
}

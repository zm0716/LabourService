package com.agilefinger.labourservice.bean;

public class FileBean {

    /**
     * code : 0
     * message : success
     * data : {"id":29856,"filename":"a1c4dcc0fe5d997c8af1720a2b498711.jpg","file_url":"/Public/Uploads/img/a1c4dcc0fe5d997c8af1720a2b498711.jpg","original_name":"qc-1565782287236.jpg"}
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
         * id : 29856
         * filename : a1c4dcc0fe5d997c8af1720a2b498711.jpg
         * file_url : /Public/Uploads/img/a1c4dcc0fe5d997c8af1720a2b498711.jpg
         * original_name : qc-1565782287236.jpg
         */

        private int id;
        private String filename;
        private String file_url;
        private String original_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public void setOriginal_name(String original_name) {
            this.original_name = original_name;
        }
    }
}

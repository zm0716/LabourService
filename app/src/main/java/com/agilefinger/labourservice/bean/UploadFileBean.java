package com.agilefinger.labourservice.bean;

/**
 * Created by 86251 on 2019/6/4.
 */

public class UploadFileBean {
    private int id;
    private String filename;
    private String file_url;

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
}

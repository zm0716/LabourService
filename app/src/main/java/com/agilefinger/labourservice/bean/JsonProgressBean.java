package com.agilefinger.labourservice.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 86251 on 2019/6/10.
 */
@DatabaseTable(tableName = "JsonProgress")
public class JsonProgressBean {
    @DatabaseField(useGetSet = true, generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true, columnName = "content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

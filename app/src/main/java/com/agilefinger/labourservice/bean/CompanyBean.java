package com.agilefinger.labourservice.bean;

import com.agilefinger.labourservice.widget.datetimepicker.PickerView;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 86251 on 2019/6/3.
 */
@DatabaseTable(tableName = "Company")
public class CompanyBean implements PickerView.PickerItem {
    @DatabaseField(useGetSet = true, columnName = "company_id")
    private String company_id;
    @DatabaseField(useGetSet = true, columnName = "company_name")
    private String company_name;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String getText() {
        return this.company_name;
    }
}

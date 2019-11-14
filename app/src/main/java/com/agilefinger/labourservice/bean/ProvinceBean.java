package com.agilefinger.labourservice.bean;

import java.util.List;

public class ProvinceBean {
    /**
     * city : [{"code":110100,"country":[{"code":110101,"name":"东城区"},{"code":110102,"name":"西城区"},{"code":110103,"name":"崇文区"},{"code":110104,"name":"宣武区"},{"code":110105,"name":"朝阳区"},{"code":110106,"name":"丰台区"},{"code":110107,"name":"石景山区"},{"code":110108,"name":"海淀区"},{"code":110109,"name":"门头沟区"},{"code":110111,"name":"房山区"},{"code":110112,"name":"通州区"},{"code":110113,"name":"顺义区"},{"code":110114,"name":"昌平区"},{"code":110115,"name":"大兴区"},{"code":110116,"name":"怀柔区"},{"code":110117,"name":"平谷区"}],"name":"市辖区"}]
     * code : 110000
     * name : 北京市
     */

    private int code;
    private String name;
    private List<CityModel> city;

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

    public List<CityModel> getCity() {
        return city;
    }

    public void setCity(List<CityModel> city) {
        this.city = city;
    }

}

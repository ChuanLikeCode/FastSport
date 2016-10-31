package com.sibo.fastsport.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/28.
 */
public class SportDetail extends BmobObject {
    private int id;
    private String name;
    private String part;
    private String equipment;
    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

package com.sibo.fastsport.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/28.
 */
public class SportDetail extends BmobObject {
    private int id;
    private String name;
    private String exercise_part;
    private String need_equipment;
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

    public String getExercise_part() {
        return exercise_part;
    }

    public void setExercise_part(String exercise_part) {
        this.exercise_part = exercise_part;
    }

    public String getNeed_equipment() {
        return need_equipment;
    }

    public void setNeed_equipment(String need_equipment) {
        this.need_equipment = need_equipment;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

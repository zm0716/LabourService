package com.agilefinger.labourservice.bean;

import java.io.Serializable;

/**
 * Created by 86251 on 2019/6/11.
 */

public class TeamBean implements Serializable{
    private String team_id;
    private String team_name;
    private boolean isCheck;
    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

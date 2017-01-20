package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sabbir on 8/8/16.
 */
public class ProjectTeamDto {

    private String projectTeamId;

    private String teamId;

    private String name;

    private String floor;

    private String room;

    private boolean isActive;

    private int activity;

    public String getProjectTeamId() {
        return projectTeamId;
    }

    public void setProjectTeamId(String projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @JsonIgnore
    public int getActivity() {
        return activity;
    }

    @JsonProperty
    public void setActivity(int activity) {
        this.activity = activity;
    }
}

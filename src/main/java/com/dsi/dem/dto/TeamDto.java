package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class TeamDto {

    private String teamId;

    private String name;

    private String floor;

    private String room;

    private int memberCount;

    private boolean isActive;

    private int version;

    private List<TeamMemberDto> memberList = new ArrayList<>();

    private List<String> projectIds = new ArrayList<>();

    private List<TeamProjectDto> projectList = new ArrayList<>();

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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TeamMemberDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<TeamMemberDto> memberList) {
        this.memberList = memberList;
    }

    @JsonIgnore
    public List<String> getProjectIds() {
        return projectIds;
    }

    @JsonProperty
    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public List<TeamProjectDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<TeamProjectDto> projectList) {
        this.projectList = projectList;
    }
}

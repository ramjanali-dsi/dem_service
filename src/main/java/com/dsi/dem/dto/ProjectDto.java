package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/8/16.
 */
public class ProjectDto {

    private String projectId;

    private String projectName;

    private String description;

    private String projectStatusId;

    private String projectStatusName;

    private int version;

    private List<ProjectTeamDto> teamList = new ArrayList<>();

    private List<ProjectClientDto> clientList = new ArrayList<>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectStatusId() {
        return projectStatusId;
    }

    public void setProjectStatusId(String projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    public String getProjectStatusName() {
        return projectStatusName;
    }

    public void setProjectStatusName(String projectStatusName) {
        this.projectStatusName = projectStatusName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ProjectTeamDto> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<ProjectTeamDto> teamList) {
        this.teamList = teamList;
    }

    public List<ProjectClientDto> getClientList() {
        return clientList;
    }

    public void setClientList(List<ProjectClientDto> clientList) {
        this.clientList = clientList;
    }
}

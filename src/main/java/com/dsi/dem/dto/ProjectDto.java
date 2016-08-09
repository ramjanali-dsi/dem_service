package com.dsi.dem.dto;

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

    private List<String> teamIds = new ArrayList<>();

    private List<String> clientIds = new ArrayList<>();

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

    public List<String> getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(List<String> teamIds) {
        this.teamIds = teamIds;
    }

    public List<String> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<String> clientIds) {
        this.clientIds = clientIds;
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

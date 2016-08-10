package com.dsi.dem.dto;

/**
 * Created by sabbir on 8/9/16.
 */
public class ClientProjectDto {

    private String projectClientId;

    private String projectId;

    private String projectName;

    private String description;

    private String projectStatusId;

    private String projectStatusName;

    public String getProjectClientId() {
        return projectClientId;
    }

    public void setProjectClientId(String projectClientId) {
        this.projectClientId = projectClientId;
    }

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
}

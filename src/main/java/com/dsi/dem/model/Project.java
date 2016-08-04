package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "dsi_project")
public class Project {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "project_id", length = 40)
    private String projectId;

    @Column(length = 50)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProjectStatus status;

    private int version;

    @Transient
    private List<ProjectTeam> teamList = new ArrayList<>();

    @Transient
    private List<ProjectClient> clientList = new ArrayList<>();

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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ProjectTeam> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<ProjectTeam> teamList) {
        this.teamList = teamList;
    }

    public List<ProjectClient> getClientList() {
        return clientList;
    }

    public void setClientList(List<ProjectClient> clientList) {
        this.clientList = clientList;
    }
}

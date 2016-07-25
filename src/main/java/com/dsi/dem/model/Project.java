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
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProjectStatus status;

    @Transient
    private List<ProjectTeam> projectTeamList = new ArrayList<>();

    @Transient
    private List<ProjectClient> projectClientList = new ArrayList<>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ProjectTeam> getProjectTeamList() {
        return projectTeamList;
    }

    public void setProjectTeamList(List<ProjectTeam> projectTeamList) {
        this.projectTeamList = projectTeamList;
    }

    public List<ProjectClient> getProjectClientList() {
        return projectClientList;
    }

    public void setProjectClientList(List<ProjectClient> projectClientList) {
        this.projectClientList = projectClientList;
    }
}

package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "ref_project_status")
public class ProjectStatus {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "project_status_id", length = 40)
    private String projectStatusId;

    @Column(name = "name", length = 50)
    private String projectStatusName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

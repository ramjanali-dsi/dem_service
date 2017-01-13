package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 1/11/17.
 */
@Entity
@Table(name = "ref_work_from_home_status")
public class WorkFormHomeStatus {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "work_from_home_status_id", length = 40)
    private String workFromHomeStatusId;

    @Column(name = "name", length = 50)
    private String workFromHomeStatusName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int priority;

    @Column(name = "is_active")
    private boolean isActive;

    public String getWorkFromHomeStatusId() {
        return workFromHomeStatusId;
    }

    public void setWorkFromHomeStatusId(String workFromHomeStatusId) {
        this.workFromHomeStatusId = workFromHomeStatusId;
    }

    public String getWorkFromHomeStatusName() {
        return workFromHomeStatusName;
    }

    public void setWorkFromHomeStatusName(String workFromHomeStatusName) {
        this.workFromHomeStatusName = workFromHomeStatusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
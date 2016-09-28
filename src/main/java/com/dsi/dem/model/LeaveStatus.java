package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "ref_leave_status")
public class LeaveStatus {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "leave_status_id", length = 40)
    private String leaveStatusId;

    @Column(name = "name", length = 50)
    private String leaveStatusName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getLeaveStatusId() {
        return leaveStatusId;
    }

    public void setLeaveStatusId(String leaveStatusId) {
        this.leaveStatusId = leaveStatusId;
    }

    public String getLeaveStatusName() {
        return leaveStatusName;
    }

    public void setLeaveStatusName(String leaveStatusName) {
        this.leaveStatusName = leaveStatusName;
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

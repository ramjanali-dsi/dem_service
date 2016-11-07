package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 9/30/16.
 */

@Entity
@Table(name = "ref_leave_request_type")
public class LeaveRequestType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "leave_request_type_id", length = 40)
    private String leaveRequestTypeId;

    @Column(name = "name", length = 50)
    private String leaveRequestTypeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getLeaveRequestTypeId() {
        return leaveRequestTypeId;
    }

    public void setLeaveRequestTypeId(String leaveRequestTypeId) {
        this.leaveRequestTypeId = leaveRequestTypeId;
    }

    public String getLeaveRequestTypeName() {
        return leaveRequestTypeName;
    }

    public void setLeaveRequestTypeName(String leaveRequestTypeName) {
        this.leaveRequestTypeName = leaveRequestTypeName;
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

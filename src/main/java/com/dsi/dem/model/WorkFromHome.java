package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 1/11/17.
 */
@Entity
@Table(name = "dsi_work_from_home")
public class WorkFromHome {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "work_from_home_id", length = 40)
    private String workFromHomeId;

    @ManyToOne
    @JoinColumn(name = "apply_id")
    private Employee employee;

    @Column(name = "apply_date")
    @Type(type = "date")
    private Date applyDate;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private WorkFormHomeStatus status;

    @Column(name = "approved_by", length = 40)
    private String approvedBy;

    @Column(name = "approved_date")
    @Type(type = "date")
    private Date approvedDate;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name = "last_modified_date")
    @Type(type = "date")
    private Date lastModifiedDate;

    private int version;

    public String getWorkFromHomeId() {
        return workFromHomeId;
    }

    public void setWorkFromHomeId(String workFromHomeId) {
        this.workFromHomeId = workFromHomeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WorkFormHomeStatus getStatus() {
        return status;
    }

    public void setStatus(WorkFormHomeStatus status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

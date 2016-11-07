package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "dsi_leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "leave_request_id", length = 40)
    private String leaveRequestId;

    @ManyToOne
    @JoinColumn(name = "apply_id")
    private Employee employee;

    @Column(name = "apply_date")
    @Type(type = "date")
    private Date applyDate;

    @ManyToOne
    @JoinColumn(name = "request_type_id")
    private LeaveRequestType requestType;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;

    @Column(name = "start_date")
    @Type(type = "date")
    private Date startDate;

    @Column(name = "end_date")
    @Type(type = "date")
    private Date endDate;

    @Column(name = "days_count")
    private int daysCount;

    @Column(name = "last_modified_date")
    @Type(type = "date")
    private Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "leave_status_id")
    private LeaveStatus leaveStatus;

    @Column(name = "approved_start_date")
    @Type(type = "date")
    private Date approvedStartDate;

    @Column(name = "approved_end_date")
    @Type(type = "date")
    private Date approvedEndDate;

    @Column(name = "approved_days_count")
    private int approvedDaysCount;

    @Column(name = "approval_id", length = 40)
    private String approvalId;

    @Column(name = "approved_date")
    @Type(type = "date")
    private Date approvedDate;

    @Column(name = "leave_reason", columnDefinition = "TEXT")
    private String leaveReason;

    @Column(name = "denied_reason", columnDefinition = "TEXT")
    private String deniedReason;

    @Column(name = "is_client_notify")
    private boolean isClientNotify;

    private int version;

    public String getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(String leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
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

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Date getApprovedStartDate() {
        return approvedStartDate;
    }

    public void setApprovedStartDate(Date approvedStartDate) {
        this.approvedStartDate = approvedStartDate;
    }

    public Date getApprovedEndDate() {
        return approvedEndDate;
    }

    public void setApprovedEndDate(Date approvedEndDate) {
        this.approvedEndDate = approvedEndDate;
    }

    public int getApprovedDaysCount() {
        return approvedDaysCount;
    }

    public void setApprovedDaysCount(int approvedDaysCount) {
        this.approvedDaysCount = approvedDaysCount;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public boolean isClientNotify() {
        return isClientNotify;
    }

    public void setClientNotify(boolean clientNotify) {
        isClientNotify = clientNotify;
    }

    public LeaveRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(LeaveRequestType requestType) {
        this.requestType = requestType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

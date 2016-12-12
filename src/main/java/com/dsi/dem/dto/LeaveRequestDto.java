package com.dsi.dem.dto;

import com.dsi.dem.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveRequestDto {

    private String leaveRequestId;

    private String employeeId;

    private String employeeNo;

    private Date applyDate;

    private String leaveRequestTypeId;

    private String leaveRequestTypeName;

    private String leaveTypeId;

    private String leaveTypeName;

    private Date startDate;

    private Date endDate;

    private int daysCount;

    private Date lastModifiedDate;

    private String leaveStatusId;

    private String leaveStatusName;

    private Date approvedStartDate;

    private Date approvedEndDate;

    private int approvedDaysCount;

    private String approvalId;

    private String approvedBy;

    private Date approvedDate;

    private String leaveReason;

    private String deniedReason;

    private boolean isClientNotify;

    private int mode;

    private int version;

    public String getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(String leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
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

    @JsonProperty
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @JsonIgnore
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

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

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getApprovedStartDate() {
        return approvedStartDate;
    }

    public void setApprovedStartDate(Date approvedStartDate) {
        this.approvedStartDate = approvedStartDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
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

    @JsonProperty
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getApprovedDate() {
        return approvedDate;
    }

    @JsonIgnore
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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public boolean isClientNotify() {
        return isClientNotify;
    }

    public void setClientNotify(boolean clientNotify) {
        isClientNotify = clientNotify;
    }

    @JsonIgnore
    public int getMode() {
        return mode;
    }

    @JsonProperty
    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

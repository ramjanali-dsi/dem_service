package com.dsi.dem.dto;

import com.dsi.dem.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by sabbir on 1/11/17.
 */
public class WorkFromHomeDto {

    private String workFromHomeId;

    private String employeeId;

    private Date applyDate;

    private String workFromHomeStatusId;

    private String workFromHomeStatusName;

    private String approvedBy;

    private String reason;

    private String deniedReason;

    private int mode;

    private int version;

    public String getWorkFromHomeId() {
        return workFromHomeId;
    }

    public void setWorkFromHomeId(String workFromHomeId) {
        this.workFromHomeId = workFromHomeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }

    public int getMode() {
        return mode;
    }

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

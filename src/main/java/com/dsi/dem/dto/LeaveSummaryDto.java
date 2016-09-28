package com.dsi.dem.dto;

/**
 * Created by sabbir on 9/9/16.
 */
public class LeaveSummaryDto {

    private String employeeLeaveId;

    private String employeeId;

    private String employeeNo;

    private String firstName;

    private String lastName;

    private int totalLeave;

    private int totalLeaveUsed;

    private int totalSick;

    private int totalSickUsed;

    private int totalCasual;

    private int totalCasualUsed;

    private int totalNotNotify;

    private int totalSpecialLeave;

    private int version;

    public String getEmployeeLeaveId() {
        return employeeLeaveId;
    }

    public void setEmployeeLeaveId(String employeeLeaveId) {
        this.employeeLeaveId = employeeLeaveId;
    }

    public int getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(int totalLeave) {
        this.totalLeave = totalLeave;
    }

    public int getTotalSick() {
        return totalSick;
    }

    public void setTotalSick(int totalSick) {
        this.totalSick = totalSick;
    }

    public int getTotalCasual() {
        return totalCasual;
    }

    public void setTotalCasual(int totalCasual) {
        this.totalCasual = totalCasual;
    }

    public int getTotalNotNotify() {
        return totalNotNotify;
    }

    public void setTotalNotNotify(int totalNotNotify) {
        this.totalNotNotify = totalNotNotify;
    }

    public int getTotalSpecialLeave() {
        return totalSpecialLeave;
    }

    public void setTotalSpecialLeave(int totalSpecialLeave) {
        this.totalSpecialLeave = totalSpecialLeave;
    }

    public int getTotalLeaveUsed() {
        return totalLeaveUsed;
    }

    public void setTotalLeaveUsed(int totalLeaveUsed) {
        this.totalLeaveUsed = totalLeaveUsed;
    }

    public int getTotalSickUsed() {
        return totalSickUsed;
    }

    public void setTotalSickUsed(int totalSickUsed) {
        this.totalSickUsed = totalSickUsed;
    }

    public int getTotalCasualUsed() {
        return totalCasualUsed;
    }

    public void setTotalCasualUsed(int totalCasualUsed) {
        this.totalCasualUsed = totalCasualUsed;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

package com.dsi.dem.dto;

import com.dsi.dem.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by sabbir on 1/12/17.
 */
public class WorkFromHomeDetails {

    private String workFromHomeId;

    private String employeeId;

    private String employeeNo;

    private String firstName;

    private String lastName;

    private String nickName;

    private String designation;

    private String phone;

    private String email;

    private String skypeId;

    private String team;

    private String project;

    private boolean isActive;

    private Date applyDate;

    private String workFromHomeStatusId;

    private String workFromHomeStatusName;

    private String reason;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.dsi.dem.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 9/26/16.
 */
public class LeaveDetailsDto {

    private String employeeId;

    private String employeeNo;

    private String firstName;

    private String lastName;

    private String nickName;

    private boolean isActive;

    private String designation;

    private String phone;

    private String email;

    private String skypeId;

    private String team;

    private String project;

    private LeaveSummaryDto leaveSummary = new LeaveSummaryDto();

    private List<LeaveRequestDto> leaveDetails = new ArrayList<>();

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

    public LeaveSummaryDto getLeaveSummary() {
        return leaveSummary;
    }

    public void setLeaveSummary(LeaveSummaryDto leaveSummary) {
        this.leaveSummary = leaveSummary;
    }

    public List<LeaveRequestDto> getLeaveDetails() {
        return leaveDetails;
    }

    public void setLeaveDetails(List<LeaveRequestDto> leaveDetails) {
        this.leaveDetails = leaveDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

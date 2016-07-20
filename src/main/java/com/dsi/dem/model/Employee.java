package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_employee")
public class Employee {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_id", length = 40)
    private String employeeId;

    @Column(name = "employee_no", length = 20, unique = true)
    private String employeeNo;

    @Column(name = "first_name", length = 40)
    private String firstName;

    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "nick_name", length = 30)
    private String nickName;

    @Column(name = "user_id", length = 40)
    private String userId;

    @Column(name = "bank_ac_no", length = 50)
    private String bankAcNo;

    @Column(name = "ip_address", length = 40)
    private String ipAddress;

    @Column(name = "mac_address", length = 40)
    private String macAddress;

    @Column(name = "github_id", length = 50)
    private String githubId;

    @Column(name = "skype_id", length = 50)
    private String skypeId;

    @Column(name = "national_id", length = 50)
    private String nationalId;

    @Column(name = "e_tin_id", length = 50)
    private String eTinId;

    @Column(name = "joining_date")
    private Date joiningDate;

    @Column(name = "date_of_confirmation")
    private Date dateOfConfirmation;

    @Column(name = "resign_date")
    private Date resignDate;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Transient
    private EmployeeInfo employeeInfo;

    @Transient
    private EmployeeDesignation employeeDesignation;

    @Transient
    private List<EmployeeContact> employeeContactList = new ArrayList<>();

    @Transient
    private List<EmployeeEmail> employeeEmailList = new ArrayList<>();

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

    public String getBankAcNo() {
        return bankAcNo;
    }

    public void setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getDateOfConfirmation() {
        return dateOfConfirmation;
    }

    public void setDateOfConfirmation(Date dateOfConfirmation) {
        this.dateOfConfirmation = dateOfConfirmation;
    }

    public Date getResignDate() {
        return resignDate;
    }

    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String geteTinId() {
        return eTinId;
    }

    public void seteTinId(String eTinId) {
        this.eTinId = eTinId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public EmployeeDesignation getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public List<EmployeeContact> getEmployeeContactList() {
        return employeeContactList;
    }

    public void setEmployeeContactList(List<EmployeeContact> employeeContactList) {
        this.employeeContactList = employeeContactList;
    }

    public List<EmployeeEmail> getEmployeeEmailList() {
        return employeeEmailList;
    }

    public void setEmployeeEmailList(List<EmployeeEmail> employeeEmailList) {
        this.employeeEmailList = employeeEmailList;
    }
}

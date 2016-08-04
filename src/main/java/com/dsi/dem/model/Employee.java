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
    private String etinId;

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

    private int version;

    @Transient
    private EmployeeInfo info = new EmployeeInfo();

    @Transient
    private List<EmployeeDesignation> designations = new ArrayList<>();

    @Transient
    private List<EmployeeContact> contactInfo = new ArrayList<>();

    @Transient
    private List<EmployeeEmail> emailInfo = new ArrayList<>();

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

    public String getETinId() {
        return etinId;
    }

    public void setETinId(String etinId) {
        this.etinId = etinId;
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

    public String getEtinId() {
        return etinId;
    }

    public void setEtinId(String etinId) {
        this.etinId = etinId;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void setInfo(EmployeeInfo info) {
        this.info = info;
    }

    public List<EmployeeDesignation> getDesignations() {
        return designations;
    }

    public void setDesignations(List<EmployeeDesignation> designations) {
        this.designations = designations;
    }

    public List<EmployeeContact> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<EmployeeContact> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<EmployeeEmail> getEmailInfo() {
        return emailInfo;
    }

    public void setEmailInfo(List<EmployeeEmail> emailInfo) {
        this.emailInfo = emailInfo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}

package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 8/3/16.
 */
public class EmployeeDto {

    private String employeeId;

    @ApiModelProperty(required = true)
    private String employeeNo;

    @ApiModelProperty(required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    private String nickName;

    private String userId;

    private String bankAcNo;

    private String ipAddress;

    private String macAddress;

    private String githubId;

    private String skypeId;

    private String nationalId;

    private String etinId;

    private Date joiningDate;

    private Date dateOfConfirmation;

    private Date resignDate;

    private Date createdDate;

    private Date lastModifiedDate;

    private boolean isActive;

    private int version;

    private EmployeeInfoDto employeeInfo = new EmployeeInfoDto();

    private List<EmployeeDesignationDto> designationList = new ArrayList<>();

    private List<EmployeeEmailDto> emailList = new ArrayList<>();

    private List<EmployeeContactDto> contactList = new ArrayList<>();

    @JsonProperty
    public String getEmployeeId() {
        return employeeId;
    }

    @JsonIgnore
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEtinId() {
        return etinId;
    }

    public void setEtinId(String etinId) {
        this.etinId = etinId;
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

    @JsonProperty
    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonIgnore
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @JsonIgnore
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public EmployeeInfoDto getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfoDto employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public List<EmployeeDesignationDto> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<EmployeeDesignationDto> designationList) {
        this.designationList = designationList;
    }

    public List<EmployeeEmailDto> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<EmployeeEmailDto> emailList) {
        this.emailList = emailList;
    }

    public List<EmployeeContactDto> getContactList() {
        return contactList;
    }

    public void setContactList(List<EmployeeContactDto> contactList) {
        this.contactList = contactList;
    }
}

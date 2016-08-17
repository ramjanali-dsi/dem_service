package com.dsi.dem.dto;

import com.dsi.dem.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by sabbir on 8/3/16.
 */

public class EmployeeInfoDto {

    private String employeeInfoId;

    private String gender;

    private String photoUrl;

    private String fatherName;

    private String motherName;

    private String spouseName;

    @ApiModelProperty(required = true)
    private String presentAddress;

    private String permanentAddress;

    @ApiModelProperty(required = true)
    private Date dateOfBirth;

    private String bloodGroup;

    private int version;

    @JsonProperty
    public String getEmployeeInfoId() {
        return employeeInfoId;
    }

    @JsonIgnore
    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

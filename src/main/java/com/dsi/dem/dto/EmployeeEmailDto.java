package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by sabbir on 8/3/16.
 */
public class EmployeeEmailDto {

    private String emailId;

    @ApiModelProperty(required = true)
    private String email;

    @ApiModelProperty(required = true)
    private String emailTypeId;

    private String emailTypeName;

    private int version;

    @JsonProperty
    public String getEmailId() {
        return emailId;
    }

    @JsonIgnore
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailTypeId() {
        return emailTypeId;
    }

    public void setEmailTypeId(String emailTypeId) {
        this.emailTypeId = emailTypeId;
    }

    public String getEmailTypeName() {
        return emailTypeName;
    }

    public void setEmailTypeName(String emailTypeName) {
        this.emailTypeName = emailTypeName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by sabbir on 8/3/16.
 */
public class EmployeeContactDto {

    private String contactNumberId;

    @ApiModelProperty(required = true)
    private String phone;

    @ApiModelProperty(required = true)
    private String contactTypeId;

    private String contactTypeName;

    private int version;

    private int activity;

    public String getContactNumberId() {
        return contactNumberId;
    }

    public void setContactNumberId(String contactNumberId) {
        this.contactNumberId = contactNumberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(String contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
}

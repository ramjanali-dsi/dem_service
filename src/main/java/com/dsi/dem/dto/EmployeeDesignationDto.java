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
public class EmployeeDesignationDto {

    private String designationId;

    @ApiModelProperty(required = true)
    private String name;

    private Date designationDate;

    private boolean isCurrent;

    private int version;

    private int activity;

    public String getDesignationId() {
        return designationId;
    }

    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDesignationDate() {
        return designationDate;
    }

    public void setDesignationDate(Date designationDate) {
        this.designationDate = designationDate;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
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

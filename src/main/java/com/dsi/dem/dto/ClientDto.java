package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/9/16.
 */
public class ClientDto {

    private String clientId;

    @ApiModelProperty(required = true)
    private String memberName;

    private String memberPosition;

    @ApiModelProperty(required = true)
    private String memberEmail;

    @ApiModelProperty(required = true)
    private boolean isNotify;

    @ApiModelProperty(required = true)
    private String organization;

    private int version;

    private List<ClientProjectDto> projectList = new ArrayList<>();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPosition() {
        return memberPosition;
    }

    public void setMemberPosition(String memberPosition) {
        this.memberPosition = memberPosition;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ClientProjectDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ClientProjectDto> projectList) {
        this.projectList = projectList;
    }
}

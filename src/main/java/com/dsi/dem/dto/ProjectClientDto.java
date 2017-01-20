package com.dsi.dem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sabbir on 8/8/16.
 */
public class ProjectClientDto {

    private String projectClientId;

    private String clientId;

    private String memberName;

    private String memberPosition;

    private String memberEmail;

    private String organization;

    private int activity;

    public String getProjectClientId() {
        return projectClientId;
    }

    public void setProjectClientId(String projectClientId) {
        this.projectClientId = projectClientId;
    }

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonIgnore
    public int getActivity() {
        return activity;
    }

    @JsonProperty
    public void setActivity(int activity) {
        this.activity = activity;
    }
}

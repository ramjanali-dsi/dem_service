package com.dsi.dem.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 9/6/16.
 */
public class AdditionalInfo {

    private List<EmployeeEmailDto> emailList = new ArrayList<>();

    private List<EmployeeContactDto> contactList = new ArrayList<>();

    private List<EmployeeDesignationDto> designationList = new ArrayList<>();

    private String action;

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

    public List<EmployeeDesignationDto> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<EmployeeDesignationDto> designationList) {
        this.designationList = designationList;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

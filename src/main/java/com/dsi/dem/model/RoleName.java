package com.dsi.dem.model;

/**
 * Created by sabbir on 1/17/17.
 */
public enum RoleName {

    LEAD("Lead"), HR("HR"), MANAGER("Manager"), MEMBER("Member");
    private String value;

    RoleName(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

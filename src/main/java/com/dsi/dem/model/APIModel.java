package com.dsi.dem.model;

import scala.collection.immutable.List;

/**
 * Created by sabbir on 7/20/16.
 */
public class APIModel {

    private List<EmployeeContact> employeeContactList;

    private List<EmployeeEmail> employeeEmailList;

    public List<EmployeeContact> getEmployeeContactList() {
        return employeeContactList;
    }

    public void setEmployeeContactList(List<EmployeeContact> employeeContactList) {
        this.employeeContactList = employeeContactList;
    }

    public List<EmployeeEmail> getEmployeeEmailList() {
        return employeeEmailList;
    }

    public void setEmployeeEmailList(List<EmployeeEmail> employeeEmailList) {
        this.employeeEmailList = employeeEmailList;
    }
}

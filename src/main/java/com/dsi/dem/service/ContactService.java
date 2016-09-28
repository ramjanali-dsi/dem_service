package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.model.EmployeeEmail;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public interface ContactService {

    void saveEmployeeContactInfo(List<EmployeeContact> contactList, String employeeID) throws CustomException;
    void saveEmployeeContactInfo(EmployeeContact contact, String employeeID) throws CustomException;
    void updateEmployeeContactInfo(EmployeeContact employeeContact, String employeeID) throws CustomException;
    void deleteEmployeeContactInfo(String contactInfoID) throws CustomException;
    List<EmployeeContact> getEmployeesContactInfoByEmployeeID(String employeeID) throws CustomException;
    EmployeeContact getEmployeeContactInfo(String contactInfoID, String employeeID) throws CustomException;
}

package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.model.EmployeeDesignation;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public interface DesignationService {

    void saveEmployeeDesignation(List<EmployeeDesignation> designationList, String employeeID) throws CustomException;
    void saveEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException;
    void updateEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException;
    void deleteEmployeeDesignation(String designationID) throws CustomException;
    List<EmployeeDesignation> getEmployeesDesignationByEmployeeID(String employeeID) throws CustomException;
    EmployeeDesignation getEmployeeDesignation(String designationID, String employeeID) throws CustomException;
}

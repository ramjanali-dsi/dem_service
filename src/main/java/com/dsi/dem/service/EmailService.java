package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeEmail;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface EmailService {

    void saveEmployeeEmail(List<EmployeeEmail> employeeEmailList, String employeeID) throws CustomException;
    void saveEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException;
    void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException;
    void deleteEmployeeEmail(String emailID) throws CustomException;
    List<EmployeeEmail> getEmployeesEmailByEmployeeID(String employeeID) throws CustomException;
    EmployeeEmail getEmployeeEmail(String emailID, String employeeID) throws CustomException;
}

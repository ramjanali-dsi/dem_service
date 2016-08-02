package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeEmail;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface EmployeeEmailService {

    void saveEmployeeEmail(List<EmployeeEmail> employeeEmailList, String employeeID) throws CustomException;
    void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException;
    void deleteEmployeeEmail(String emailID) throws CustomException;
    EmployeeEmail getEmployeeEmailByEmailAndType(String email, String type);
}

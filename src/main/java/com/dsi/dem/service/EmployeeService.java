package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public interface EmployeeService {

    void saveEmployee(Employee employee) throws CustomException;
    void updateEmployee(Employee employee) throws CustomException;
    void deleteEmployee(String employeeID) throws CustomException;
    Employee getEmployeeByID(String employeeID) throws CustomException;
    Employee getEmployeeByUserID(String userID) throws CustomException;
    Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException;
    List<Employee> getAllEmployees() throws CustomException;
}

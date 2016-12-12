package com.dsi.dem.service;

import com.dsi.dem.dto.EmployeeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public interface EmployeeService {

    Employee saveEmployee(Employee employee) throws CustomException;
    void validateInputForCreation(Employee employee) throws CustomException;
    Employee updateEmployee(Employee employee) throws CustomException;
    void deleteEmployee(String employeeID) throws CustomException;
    Employee getEmployeeByID(String employeeID) throws CustomException;
    Employee getEmployeeByUserID(String userID) throws CustomException;
    Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException;
    List<Employee> getAllEmployees() throws CustomException;
    List<Employee> searchEmployees(String employeeNo, String firstName, String lastName, String nickName,
                                   String accountID, String ipAddress, String nationalID, String tinID,
                                   String phone, String email, String active, String joiningDate, String teamName,
                                   String projectName, String userID, String from, String range) throws CustomException;
}

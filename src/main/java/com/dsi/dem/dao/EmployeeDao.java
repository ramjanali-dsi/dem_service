package com.dsi.dem.dao;

import com.dsi.dem.model.*;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public interface EmployeeDao {

    boolean saveEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(Employee employee);
    Employee getEmployeeByID(String employeeID);
    Employee getEmployeeByUserID(String userID);
    Employee getEmployeeByEmployeeNO(String employeeNO);
    List<Employee> getAllEmployees();

    boolean saveEmployeeInfo(EmployeeInfo employeeInfo);
    boolean updateEmployeeInfo(EmployeeInfo employeeInfo);
    boolean deleteEmployeeInfo(EmployeeInfo employeeInfo);
    EmployeeInfo getEmployeeInfoByEmployeeID(String employeeID);

    boolean saveEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean updateEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean deleteEmployeeDesignation(EmployeeDesignation employeeDesignation);
    List<EmployeeDesignation> getEmployeeDesignationsByEmployeeID(String employeeID);

    boolean saveEmployeeEmail(EmployeeEmail employeeEmail);
    boolean updateEmployeeEmail(EmployeeEmail employeeEmail);
    boolean deleteEmployeeEmail(EmployeeEmail employeeEmail);
    List<EmployeeEmail> getEmployeeEmailsByEmployeeID(String employeeID);
    EmployeeEmail getEmployeeEmailByEmailName(String email);
    EmployeeEmail getEmployeeEmailByEmailAndEmployeeID(String email, String employeeID);
    EmployeeEmail getEmployeeEmailByEmailAndType(String email, String type);

    boolean saveEmployeeContactInfo(EmployeeContact employeeContact);
    boolean updateEmployeeContactInfo(EmployeeContact employeeContact);
    boolean deleteEmployeeContactInfo(EmployeeContact employeeContact);
    List<EmployeeContact> getEmployeeContactsByEmployeeID(String employeeID);
    EmployeeContact getEmployeeContactByPhoneAndType(String phone, String type);
}

package com.dsi.dem.dao;

import com.dsi.dem.model.*;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public interface EmployeeDao {

    boolean saveEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(String employeeID);
    Employee getEmployeeByID(String employeeID);
    Employee getEmployeeByUserID(String userID);
    Employee getEmployeeByEmployeeNO(String employeeNO);
    List<Employee> getAllEmployees();

    boolean saveEmployeeInfo(EmployeeInfo employeeInfo);
    boolean updateEmployeeInfo(EmployeeInfo employeeInfo);
    boolean deleteEmployeeInfo(String employeeID);
    EmployeeInfo getEmployeeInfoByEmployeeID(String employeeID);

    boolean saveEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean updateEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean deleteEmployeeDesignation(String employeeID, String designationID);
    List<EmployeeDesignation> getEmployeeDesignationsByEmployeeID(String employeeID);
    EmployeeDesignation getEmployeeDesignationByDesignationIDAndEmployeeID(String designationID, String employeeID);

    boolean saveEmployeeEmail(EmployeeEmail employeeEmail);
    boolean updateEmployeeEmail(EmployeeEmail employeeEmail);
    boolean deleteEmployeeEmail(String employeeID, String emailID);
    List<EmployeeEmail> getEmployeeEmailsByEmployeeID(String employeeID);
    EmployeeEmail getEmployeeEmailByEmailName(String email);
    EmployeeEmail getEmployeeEmailByEmailIDAndEmployeeID(String emailID, String employeeID);
    EmployeeEmail getEmployeeEmailByEmployeeIDAndTypeID(String employeeID, String typeID);

    boolean saveEmployeeContactInfo(EmployeeContact employeeContact);
    boolean updateEmployeeContactInfo(EmployeeContact employeeContact);
    boolean deleteEmployeeContactInfo(String employeeID, String contactInfoID);
    List<EmployeeContact> getEmployeeContactsByEmployeeID(String employeeID);
    EmployeeContact getEmployeeContactByEmailIDAndEmployeeID(String contactID, String employeeID);
    EmployeeContact getEmployeeContactByPhoneAndType(String phone, String type);
}

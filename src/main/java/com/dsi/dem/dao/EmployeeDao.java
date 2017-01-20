package com.dsi.dem.dao;

import com.dsi.dem.model.*;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public interface EmployeeDao {

    void setSession(Session session);

    boolean saveEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(String employeeID);
    Employee getEmployeeByID(String employeeID);
    Employee getEmployeeByUserID(String userID);
    Employee getEmployeeByEmployeeNO(String employeeNO);
    List<Employee> getAllEmployees();
    List<Employee> searchEmployees(String employeeNo, String firstName, String lastName, String nickName,
                                   String accountID, String ipAddress, String nationalID, String tinID,
                                   String phone, String email, String active, String joiningDate, String teamName,
                                   String projectName, String userID, String from, String range);

    boolean checkEmployeeAsLead(String employeeId);
    boolean checkEmployeeHasTeam(String employeeId);
    List<Employee> getTeamLeadsProfileOfAnEmployee(String employeeId);

    EmployeeStatus getEmployeeStatusById(String statusId);

    boolean saveEmployeeInfo(EmployeeInfo employeeInfo);
    boolean updateEmployeeInfo(EmployeeInfo employeeInfo);
    boolean deleteEmployeeInfo(String employeeID);
    EmployeeInfo getEmployeeInfoByEmployeeID(String employeeID);

    boolean saveEmployeeLeaveSummary(EmployeeLeave employeeLeave);
    boolean deleteEmployeeLeaveSummary(String employeeID);
    EmployeeLeave getEmployeeLeaveSummaryByEmployeeID(String employeeID);

    boolean saveEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean updateEmployeeDesignation(EmployeeDesignation employeeDesignation);
    boolean updatePrevEmployeeDesignations(String employeeID);
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
    EmployeeContact getEmployeeContactByIDAndEmployeeID(String contactID, String employeeID);
    EmployeeContact getEmployeeContactByPhoneAndType(String phone, String type);
}

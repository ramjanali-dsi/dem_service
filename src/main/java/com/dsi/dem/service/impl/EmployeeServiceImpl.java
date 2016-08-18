package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.BaseDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.SessionUtil;
import com.dsi.dem.util.Utility;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployee(Employee employee) throws CustomException {

        List<EmployeeDesignation> employeeDesignationList = employee.getDesignations();
        List<EmployeeEmail> employeeEmailList = employee.getEmailInfo();
        List<EmployeeContact> employeeContactList = employee.getContactInfo();

        employee.setCreatedDate(Utility.today());
        employee.setLastModifiedDate(Utility.today());

        boolean res = employeeDao.saveEmployee(employee);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employee success");

        EmployeeInfo employeeInfo = employee.getInfo();
        employeeInfo.setEmployee(employee);

        employeeDao.saveEmployeeInfo(employeeInfo);
        logger.info("Save employee info success");

        saveEmployeesDesignation(employeeDesignationList, employee);

        saveEmployeesEmails(employeeEmailList, employee);

        saveEmployeesContacts(employeeContactList, employee);
    }

    private void saveEmployeesDesignation(List<EmployeeDesignation> employeeDesignationList, Employee employee) {
        for(EmployeeDesignation employeeDesignation : employeeDesignationList){
            employeeDesignation.setEmployee(employee);

            employeeDao.saveEmployeeDesignation(employeeDesignation);
        }
        logger.info("Save employee designation success");
    }

    private void saveEmployeesContacts(List<EmployeeContact> employeeContactList, Employee employee) {
        for(EmployeeContact employeeContact : employeeContactList){
            employeeContact.setEmployee(employee);

            employeeDao.saveEmployeeContactInfo(employeeContact);
        }
        logger.info("Save employee contact info success");
    }

    private void saveEmployeesEmails(List<EmployeeEmail> employeeEmailList, Employee employee) {
        for(EmployeeEmail employeeEmail : employeeEmailList){
            employeeEmail.setEmployee(employee);

            employeeDao.saveEmployeeEmail(employeeEmail);
        }
        logger.info("Save employee email info success");
    }

    public void validateInputForCreation(Employee employee) throws CustomException {
        if(employee.getFirstName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "First Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employee.getLastName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Last Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employee.getRoleId() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "RoleID not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employee.getInfo().getPresentAddress() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeInfo", "Address not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getDesignations())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Employee designation not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getEmailInfo())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Employee email not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getContactInfo())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Employee contact not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailName(employee.getEmailInfo().get(0).getEmail()) != null){
            ErrorContext errorContext = new ErrorContext(employee.getEmailInfo().get(0).getEmail(), "Employee",
                    "Employee already exist by this email.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeByEmployeeNO(employee.getEmployeeNo()) != null){
            ErrorContext errorContext = new ErrorContext(employee.getEmployeeNo(), "Employee",
                    "Employee already exist by this employee No.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws CustomException {
        validateInputForUpdate(employee);

        employee.setLastModifiedDate(Utility.today());
        EmployeeInfo employeeInfo = employee.getInfo();

        boolean res = employeeDao.updateEmployee(employee);
        if(!res){
            ErrorContext errorContext = new ErrorContext(employee.getEmployeeId(), "Employee", "Employee update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Employee update success");

        employeeInfo.setEmployeeInfoId(employeeDao.getEmployeeInfoByEmployeeID(employee.getEmployeeId()).getEmployeeInfoId());
        employeeInfo.setEmployee(employee);
        employeeDao.updateEmployeeInfo(employeeInfo);
        logger.info("Employee info update success");
    }

    private void validateInputForUpdate(Employee employee) throws CustomException {
        if(employee.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeByID(employee.getEmployeeId()) == null){
            ErrorContext errorContext = new ErrorContext(employee.getEmployeeId(), "Employee", "Employee not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployee(String employeeID) throws CustomException {
        employeeDao.deleteEmployeeInfo(employeeID);
        employeeDao.deleteEmployeeEmail(employeeID, null);
        employeeDao.deleteEmployeeContactInfo(employeeID, null);
        employeeDao.deleteEmployeeDesignation(employeeID, null);

        boolean res = employeeDao.deleteEmployee(employeeID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(employeeID, "Employee", "Employee delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Employee delete success");
    }

    @Override
    public Employee getEmployeeByID(String employeeID) throws CustomException {
        Employee employee = employeeDao.getEmployeeByID(employeeID);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(employeeID, "Employee", "Employee not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employeeID ,employee);
    }

    @Override
    public Employee getEmployeeByUserID(String userID) throws CustomException {
        Employee employee = employeeDao.getEmployeeByUserID(userID);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(userID, "Employee", "Employee not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException {
        Employee employee = employeeDao.getEmployeeByEmployeeNO(employeeNO);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(employeeNO, "Employee", "Employee not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {
        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(employeeList == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllProperty(employee.getEmployeeId(), employee));
        }
        return employees;
    }

    @Override
    public List<Employee> searchEmployees(String employeeNo, String firstName, String lastName, String nickName,
                                          String accountID, String ipAddress, String nationalID, String tinID, String phone,
                                          String email, String active, String joiningDate, String teamName, String projectName)
            throws CustomException {

        List<Employee> employeeList = employeeDao.searchEmployees(employeeNo, firstName, lastName, nickName, accountID,
                ipAddress, nationalID, tinID, phone, email, active, joiningDate, teamName, projectName);
        if(employeeList == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllProperty(employee.getEmployeeId(), employee));
        }
        return employees;
    }

    public Employee setEmployeesAllProperty(String employeeID, Employee employee) {
        EmployeeInfo info = employeeDao.getEmployeeInfoByEmployeeID(employeeID);
        if(info != null){
            employee.setInfo(info);
        }

        List<EmployeeDesignation> designations = employeeDao.getEmployeeDesignationsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(designations)){
            employee.setDesignations(designations);
        }

        List<EmployeeEmail> emails = employeeDao.getEmployeeEmailsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(emails)){
            employee.setEmailInfo(emails);
        }

        List<EmployeeContact> contacts = employeeDao.getEmployeeContactsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(contacts)){
            employee.setContactInfo(contacts);
        }

        return employee;
    }
}

package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.util.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeServiceImpl extends CommonService implements EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    private static final CallAnotherResource callAnotherService = new CallAnotherResource();
    private static final NotificationService notificationService = new NotificationServiceImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public Employee saveEmployee(Employee employee, String currentUserID, String tenantName) throws CustomException {

        validateInputForCreation(employee);

        try {
            logger.info("Employee Create:: Start");
            JSONObject resultObj = callAnotherService.sendPost(APIProvider.API_LOGIN_SESSION_CREATE,
                    Utility.getLoginObject(employee, currentUserID));

            List<EmployeeDesignation> employeeDesignationList = employee.getDesignations();
            List<EmployeeEmail> employeeEmailList = employee.getEmailInfo();
            List<EmployeeContact> employeeContactList = employee.getContactInfo();

            employee.setVersion(1);
            employee.setCreatedDate(Utility.today());
            employee.setLastModifiedDate(Utility.today());
            employee.setUserId(resultObj.getString("user_id"));

            boolean res = employeeDao.saveEmployee(employee);
            if (!res) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }
            logger.info("Save employee success");

            EmployeeInfo employeeInfo = employee.getInfo();
            employeeInfo.setEmployee(employee);
            employeeInfo.setStatus(employeeDao.getEmployeeStatusById(employee.getInfo().getStatus().getEmployeeStatusId()));
            employeeInfo.setVersion(1);

            employeeDao.saveEmployeeInfo(employeeInfo);
            logger.info("Save employee info success");

            EmployeeLeave employeeLeave = new EmployeeLeave();
            employeeLeave.setEmployee(employee);
            employeeLeave.setVersion(1);

            EmployeeLeave leaveSummary = employee.getLeaveInfo();
            if(leaveSummary != null){
                employeeLeave.setTotalSick(leaveSummary.getTotalSick());
                employeeLeave.setTotalCasual(leaveSummary.getTotalCasual());

            } else {
                employeeLeave.setTotalSick(Constants.TOTAL_SICK);
                employeeLeave.setTotalCasual(Constants.TOTAL_CASUAL);
            }

            employeeDao.saveEmployeeLeaveSummary(employeeLeave);
            logger.info("Save employee leave summary success");

            saveEmployeesDesignation(employeeDesignationList, employee);
            saveEmployeesEmails(employeeEmailList, employee);
            saveEmployeesContacts(employeeContactList, employee);
            logger.info("Employee Create:: End");

            logger.info("User context create.");
            callAnotherService.sendPost(APIProvider.API_USER_CONTEXT, Utility.
                    getContextObject(employee, employee.getUserId(), null, 1));

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            String email = employeeEmailList.get(0).getEmail();
            String employeeProfilelink = RoutingConstant.EMPLOYEE_PROFILE + employee.getEmployeeId();

            JSONObject contentObj = EmailContent.getContentObjForEmployee(employee, tenantName,
                    resultObj.getString("password"), email, employeeProfilelink);

            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.EMPLOYEE_CREATE_TEMPLATE_ID_FOR_EMPLOYEE));

            JSONArray emailList = notificationService.getHrManagerEmailList();
            contentObj = EmailContent.getContentObjForGlobal(employee, tenantName, emailList, employeeProfilelink);

            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.EMPLOYEE_CREATE_TEMPLATE_ID_FOR_MANAGER_HR));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

            return setEmployeesAllProperty(employee.getEmployeeId(), employee);

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    private void saveEmployeesDesignation(List<EmployeeDesignation> employeeDesignationList, Employee employee) throws CustomException {
        for(EmployeeDesignation employeeDesignation : employeeDesignationList){
            employeeDesignation.setEmployee(employee);
            employeeDesignation.setVersion(1);

            employeeDao.saveEmployeeDesignation(employeeDesignation);
        }
        logger.info("Save employee designation success");
    }

    private void saveEmployeesContacts(List<EmployeeContact> employeeContactList, Employee employee) throws CustomException {
        for(EmployeeContact employeeContact : employeeContactList){
            employeeContact.setEmployee(employee);
            employeeContact.setVersion(1);

            employeeDao.saveEmployeeContactInfo(employeeContact);
        }
        logger.info("Save employee contact info success");
    }

    private void saveEmployeesEmails(List<EmployeeEmail> employeeEmailList, Employee employee) throws CustomException {
        for(EmployeeEmail employeeEmail : employeeEmailList){
            employeeEmail.setEmployee(employee);
            employeeEmail.setVersion(1);
            employeeEmail.setPreferred(true);

            employeeDao.saveEmployeeEmail(employeeEmail);
        }
        logger.info("Save employee email info success");
    }

    public void validateInputForCreation(Employee employee) throws CustomException {

        if(employee.getFirstName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(employee.getLastName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(employee.getRoleId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }

        if(employee.getInfo().getPresentAddress() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(employee.getInfo().getStatus().getEmployeeStatusId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0018);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getDesignations())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getEmailInfo())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getContactInfo())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailName(employee.getEmailInfo().get(0).getEmail()) != null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeByEmployeeNO(employee.getEmployeeNo()) != null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Employee updateEmployee(Employee employee, String currentUserId, String tenantName) throws CustomException {
        validateInputForUpdate(employee);

        try {
            logger.info("Employee update:: Start");

            Employee existEmployee = employeeDao.getEmployeeByID(employee.getEmployeeId());
            if (existEmployee == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                        Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }

            employee.setUserId(existEmployee.getUserId());
            employee.setCreatedDate(existEmployee.getCreatedDate());
            employee.setLastModifiedDate(Utility.today());
            EmployeeInfo employeeInfo = employee.getInfo();

            boolean res = employeeDao.updateEmployee(employee);
            if (!res) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                        Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }
            logger.info("Employee update success");

            employeeInfo.setEmployeeInfoId(employeeDao.getEmployeeInfoByEmployeeID(employee.getEmployeeId()).getEmployeeInfoId());
            employeeInfo.setEmployee(employee);
            employeeInfo.setStatus(employeeDao.getEmployeeStatusById(employee.getInfo().getStatus().getEmployeeStatusId()));
            employeeDao.updateEmployeeInfo(employeeInfo);
            logger.info("Employee info update success");

            EmployeeLeave leaveSummary = employee.getLeaveInfo();
            if(leaveSummary != null) {
                EmployeeLeave employeeLeave = employeeDao.getEmployeeLeaveSummaryByEmployeeID(employee.getEmployeeId());
                employeeLeave.setTotalCasual(leaveSummary.getTotalCasual());
                employeeLeave.setTotalSick(leaveSummary.getTotalSick());
                employeeDao.updateEmployeeLeaveSummary(employeeLeave);
                logger.info("Employee leave summary updated.");
            }

            setEmployeesAllProperty(employee.getEmployeeId(), employee);

            callAnotherService.sendPut(APIProvider.API_LOGIN_SESSION_UPDATE + employee.getUserId(),
                    Utility.getLoginObject(employee, currentUserId));

            callAnotherService.sendPut(APIProvider.API_USER + employee.getUserId(),
                    Utility.getUserObject(employee, currentUserId));

            logger.info("Employee update:: End");

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            String email = employeeDao.getPreferredEmail(employee.getEmployeeId()).getEmail();
            String employeeProfilelink = RoutingConstant.EMPLOYEE_PROFILE + employee.getEmployeeId();

            JSONObject empContentObj = EmailContent.getContentObjForEmployee(employee, tenantName, null, email, employeeProfilelink);
            notificationList.put(EmailContent.getNotificationObject(empContentObj,
                    NotificationConstant.EMPLOYEE_UPDATE_TEMPLATE_ID_FOR_EMPLOYEE));

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentObjForGlobal(employee, tenantName, emailList, employeeProfilelink);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.EMPLOYEE_UPDATE_TEMPLATE_ID_FOR_MANAGER_HR));

            if(!employee.isActive()){
                logger.info("Notification create for in-active member.");

                notificationList.put(EmailContent.getNotificationObject(empContentObj,
                        NotificationConstant.EMPLOYEE_INACTIVE_TEMPLATE_ID_FOR_EMPLOYEE));

                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.EMPLOYEE_INACTIVE_TEMPLATE_ID_FOR_MANAGER_HR));

                logger.info("Notification create for in-active member to lead.");
                List<Employee> leadList = employeeDao.getTeamLeadsProfileOfAnEmployee(employee.getEmployeeId());
                if(!Utility.isNullOrEmpty(leadList)){

                    emailList = new JSONArray();
                    for(Employee teamLead : leadList){
                        emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                    }

                    globalContentObj = EmailContent.getContentObjForGlobal(employee, tenantName, emailList, employeeProfilelink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.EMPLOYEE_INACTIVE_TEMPLATE_ID_FOR_LEAD));
                }
            }
            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

            return setEmployeesAllProperty(employee.getEmployeeId(), employee);

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    private void validateInputForUpdate(Employee employee) throws CustomException {
        if(employee.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        if(employee.getRoleId() != null){
            logger.info("Request for employee role update to: " + employee.getRoleName());
            if(employee.getRoleName().equals(NotificationConstant.MEMBER_ROLE_TYPE)){

                if(employeeDao.checkEmployeeAsLead(employee.getEmployeeId())){
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0020);
                    throw new CustomException(errorMessage);
                }

            } else if(employee.getRoleName().equals(NotificationConstant.HR_ROLE_TYPE)
                    || employee.getRoleName().equals(NotificationConstant.MANAGER_ROLE_TYPE)){

                if(employeeDao.checkEmployeeHasTeam(employee.getEmployeeId())){
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0021);
                    throw new CustomException(errorMessage);
                }
            }
        }
    }

    @Override
    public void deleteEmployee(String employeeID) throws CustomException {
        logger.info("Employees Info delete start.");

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        String userID = employee.getUserId();

        if(employeeDao.isEmployeeLinkWithTeamOrLeaveOrAttendance(employeeID)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        logger.info("User info delete:: start");
        callAnotherService.sendDelete(APIProvider.API_USER + userID);
        logger.info("User info delete:: end");

        logger.info("Login info delete:: start");
        callAnotherService.sendDelete(APIProvider.API_LOGIN_SESSION_DELETE + userID);
        logger.info("Login info delete:: end");

        employeeDao.deleteEmployeeInfo(employeeID);
        employeeDao.deleteEmployeeLeaveSummary(employeeID);
        employeeDao.deleteEmployeeEmail(employeeID, null);
        employeeDao.deleteEmployeeContactInfo(employeeID, null);
        employeeDao.deleteEmployeeDesignation(employeeID, null);

        boolean res = employeeDao.deleteEmployee(employeeID);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Employee delete success");
    }

    @Override
    public Employee getEmployeeByID(String employeeID, String context) throws CustomException {
        logger.info("Read an employee by ID:: " + employeeID);

        try {
            ContextDto contextDto = Utility.getContextDtoObj(context);
            if(contextDto != null){
                if(Utility.isNullOrEmpty(contextDto.getTeamId())){

                    if(!Utility.isNullOrEmpty(contextDto.getEmployeeId())){
                        if(!contextDto.getEmployeeId().equals(employeeID)){
                            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                            throw new CustomException(errorMessage);
                        }
                    }
                }
            }

            Employee employee = employeeDao.getEmployeeByIDAndContext(employeeID, contextDto);
            if (employee == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                        Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
                throw new CustomException(errorMessage);
            }

            JSONObject resultObj = callAnotherService.getRequest(APIProvider.API_USER + employee.getUserId());
            employee.setRoleId(resultObj.getString("roleId"));
            employee.setRoleName(resultObj.getString("roleName"));

            if(contextDto != null){
                if(!Utility.isNullOrEmpty(contextDto.getTeamId())){

                    if(!Utility.isNullOrEmpty(contextDto.getEmployeeId())) {
                        if (!contextDto.getEmployeeId().equals(employeeID)) {
                            employee.setBankAcNo(null);
                            employee.setETinId(null);
                            employee.setEtinId(null);
                            employee.setNationalId(null);
                        }
                    }
                }
            }

            return setEmployeesAllProperty(employeeID, employee);

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Employee getEmployeeByUserID(String userID) throws CustomException {

        Employee employee = employeeDao.getEmployeeByUserID(userID);
        if(employee == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException {

        Employee employee = employeeDao.getEmployeeByEmployeeNO(employeeNO);
        if(employee == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public List<Employee> getTeamLeadsProfile(String employeeId) throws CustomException {

        List<Employee> employeeList = employeeDao.getTeamLeadsProfileOfAnEmployee(employeeId);
        if(employeeList == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllProperty(employee.getEmployeeId(), employee));
        }
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {

        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(employeeList == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
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
                                          String email, String active, String joiningDate, String teamName, String projectName,
                                          String myId, String context, String from, String range) throws CustomException {

        if(!Utility.isNullOrEmpty(joiningDate)){
            if(Utility.getDateFromString(joiningDate) == null){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                        Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
                throw new CustomException(errorMessage);
            }
        }

        ContextDto contextDto = Utility.getContextDtoObj(context);
        List<Employee> employeeList = employeeDao.searchEmployees(employeeNo, firstName, lastName, nickName, accountID, ipAddress,
                nationalID, tinID, phone, email, active, joiningDate, teamName, projectName, myId, contextDto, from, range);
        if(employeeList == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        logger.info("Employee list size:: " + employeeList.size());
        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllPropertyForDashboard(employee));
        }
        return employees;
    }

    private Employee setEmployeesAllProperty(String employeeID, Employee employee) {
        EmployeeInfo info = employeeDao.getEmployeeInfoByEmployeeID(employeeID);
        if(info != null){
            employee.setInfo(info);
        }

        EmployeeLeave leave = employeeDao.getEmployeeLeaveSummaryByEmployeeID(employeeID);
        if(leave != null){
            employee.setLeaveInfo(leave);
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

    private Employee setEmployeesAllPropertyForDashboard(Employee employee) {
        EmployeeInfo info = employeeDao.getEmployeeInfoByEmployeeID(employee.getEmployeeId());
        if(info != null){
            employee.setInfo(info);
        }

        EmployeeLeave leave = employeeDao.getEmployeeLeaveSummaryByEmployeeID(employee.getEmployeeId());
        if(leave != null){
            employee.setLeaveInfo(leave);
        }

        List<EmployeeDesignation> designations = employeeDao.getEmployeeCurrentDesignation(employee.getEmployeeId());
        if(!Utility.isNullOrEmpty(designations)){
            employee.setDesignations(designations);
        }

        List<EmployeeEmail> emails = employeeDao.getPreferredEmailByEmployeeId(employee.getEmployeeId());
        if(!Utility.isNullOrEmpty(emails)){
            employee.setEmailInfo(emails);
        }

        List<EmployeeContact> contacts = employeeDao.getEmployeeOfficialContact(employee.getEmployeeId());
        if(!Utility.isNullOrEmpty(contacts)){
            employee.setContactInfo(contacts);
        }
        return employee;
    }
}

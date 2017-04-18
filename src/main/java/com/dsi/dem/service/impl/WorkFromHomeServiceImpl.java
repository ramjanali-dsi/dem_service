package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.HolidayDao;
import com.dsi.dem.dao.WorkFromHomeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.HolidayDaoImpl;
import com.dsi.dem.dao.impl.WorkFromHomeDaoImpl;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.dto.WorkFromHomeDetails;
import com.dsi.dem.dto.WorkFromHomeDto;
import com.dsi.dem.dto.transformer.WFHDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.WorkFormHomeStatus;
import com.dsi.dem.model.WorkFromHome;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.service.WorkFromHomeService;
import com.dsi.dem.util.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 1/11/17.
 */
public class WorkFromHomeServiceImpl extends CommonService implements WorkFromHomeService {

    private static final Logger logger = Logger.getLogger(WorkFromHomeServiceImpl.class);

    private static final WFHDtoTransformer TRANSFORMER = new WFHDtoTransformer();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final HolidayDao holidayDao = new HolidayDaoImpl();
    private static final WorkFromHomeDao dao = new WorkFromHomeDaoImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public WorkFromHomeDto saveWorkFromHomeRequest(WorkFromHomeDto workFromHomeDto, String userId,
                                                   String tenantName) throws CustomException {
        logger.info("Employees work from home request create:: Start");
        logger.info("Apply user ID: " + userId);
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        WorkFromHome workFromHome = TRANSFORMER.getWorkFromHomeRequest(workFromHomeDto);

        Session session = getSession();
        dao.setSession(session);
        holidayDao.setSession(session);

        validateInputForCreate(workFromHome, userId, session);

        workFromHome.setStatus(dao.getWFHStatusByName(Constants.APPLIED_WFH_REQUEST));
        workFromHome.setEmployee(employeeDao.getEmployeeByUserID(userId));
        workFromHome.setLastModifiedDate(Utility.today());
        workFromHome.setVersion(1);
        dao.saveWorkFromHomeRequest(workFromHome);
        logger.info("Save work from home request success");
        logger.info("Employees work from home request create:: End");

        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForWFHRequest(workFromHome, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.WFH_APPLY_TEMPLATE_ID_FOR_MANAGER_HR));

            List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(workFromHome.getEmployee().getEmployeeId());
            if(!Utility.isNullOrEmpty(teamLeads)){

                emailList = new JSONArray();
                for(Employee teamLead : teamLeads){
                    emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                }

                globalContentObj = EmailContent.getContentForWFHRequest(workFromHome, tenantName, emailList);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_APPLY_TEMPLATE_ID_FOR_LEAD));
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getWFHRequestDto(workFromHome);
    }

    private void validateInputForCreate(WorkFromHome workFromHome, String userId, Session session) throws CustomException {

        if(workFromHome.getApplyDate() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }

        if(workFromHome.getReason() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        //Holiday check.
        if(holidayDao.checkHolidayForDate(workFromHome.getApplyDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        //Weekend check
        if(Utility.checkWeekendOfDate(workFromHome.getApplyDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        //Approved/applied leave request check
        Employee applier = employeeDao.getEmployeeByUserID(userId);
        if(dao.checkLeaveRequest(applier.getEmployeeId(), workFromHome.getApplyDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        //Approved WFH request check
        if(dao.alreadyApprovedWFHExist(applier.getEmployeeId(), workFromHome.getApplyDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        //Applied WFH request more than 0 check
        if(dao.countAppliedWFH(applier.getEmployeeId()) > 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        //Applied time after 2pm check
        if (Utility.isGreater02PM(workFromHome.getApplyDate())) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        //Per month, more that 2 approved WFH request check
        Date firstDayOfMonth = Utility.getFirstDay(workFromHome.getApplyDate());
        Date lastDayOfMonth = Utility.getLastDay(workFromHome.getApplyDate());
        if(dao.countApprovedWFHForMonth(applier.getEmployeeId(), firstDayOfMonth, lastDayOfMonth) >= 2){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public WorkFromHomeDto updateWorkFromHomeRequest(WorkFromHomeDto workFromHomeDto, String userId,
                                                     String tenantName) throws CustomException {
        logger.info("Employees work form home request update:: Start");
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        int mode = workFromHomeDto.getMode();
        WorkFromHome workFromHome = TRANSFORMER.getWorkFromHomeRequest(workFromHomeDto);

        Session session = getSession();
        dao.setSession(session);

        WorkFromHome existWFH = dao.getWFHByIdAndUserId(workFromHome.getWorkFromHomeId(), userId);
        if(existWFH == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        String statusName = existWFH.getStatus().getWorkFromHomeStatusName();

        validateInputForUpdate(workFromHome, mode, existWFH, session);
        if(mode == 1){
            logger.info("Edit mode.");
            existWFH.setApplyDate(workFromHome.getApplyDate());
            existWFH.setReason(workFromHome.getReason());

        } else if(mode == 2){
            logger.info("Cancel mode.");
            existWFH.setStatus(dao.getWFHStatusByName(Constants.CANCELLER_WFH_REQUEST));
            existWFH.setDeniedReason(workFromHome.getReason());
        }
        existWFH.setLastModifiedDate(Utility.today());
        existWFH.setVersion(workFromHome.getVersion());
        dao.updateWorkFromHomeRequest(existWFH);
        logger.info("Update work from home request success");
        logger.info("Employees work form home request update:: End");
        close(session);

        JSONArray hrEmailList, leadEmails;
        try {
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            hrEmailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, hrEmailList);

            leadEmails = new JSONArray();
            List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(existWFH.getEmployee().getEmployeeId());
            if (!Utility.isNullOrEmpty(teamLeads)) {
                for (Employee teamLead : teamLeads) {
                    leadEmails.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                }
            }

            if(mode == 1){
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_PENDING_TEMPLATE_ID_FOR_MANAGER_HR));

                if (leadEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, leadEmails);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.WFH_PENDING_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && !statusName.equals(Constants.APPROVED_WFH_REQUEST)){
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_PENDING_CANCEL_TEMPLATE_ID_FOR_MANAGER_HR));

                if (leadEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, leadEmails);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.WFH_PENDING_CANCEL_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && statusName.equals(Constants.APPROVED_WFH_REQUEST)) {
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_APPROVED_CANCEL_TEMPLATE_ID_FOR_MANAGER_HR));

                if (leadEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, leadEmails);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.WFH_APPROVED_CANCEL_TEMPLATE_ID_FOR_LEAD));
                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getWFHRequestDto(existWFH);
    }

    private void validateInputForUpdate(WorkFromHome workFromHome, int mode, WorkFromHome existWFH,
                                        Session session) throws CustomException {
        if(workFromHome.getVersion() == 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        if(existWFH == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(!(existWFH.getStatus().getWorkFromHomeStatusName().equals(Constants.APPROVED_WFH_REQUEST)
                || existWFH.getStatus().getWorkFromHomeStatusName().equals(Constants.APPLIED_WFH_REQUEST))){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(mode == 2){
            if(workFromHome.getReason() == null){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0002);
                throw new CustomException(errorMessage);
            }
            if(existWFH.getStatus().getWorkFromHomeStatusName().equals(Constants.APPROVED_WFH_REQUEST)) {
                if (!Utility.today().before(existWFH.getApprovedDate())) {
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0005);
                    throw new CustomException(errorMessage);
                }
            }
        }
    }

    @Override
    public WorkFromHomeDto getWorkFromHomeRequestById(String wfhId) throws CustomException {
        return null;
    }

    @Override
    public List<WorkFromHomeDto> searchOrReadWorkFormHomeRequests(String userId, String date, String reason, String statusName,
                                                                  String from, String range) throws CustomException {

        logger.info("Search or read employees work from home requests by user id: " + userId);
        if(!Utility.isNullOrEmpty(date) && Utility.getDateFromString(date) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        dao.setSession(session);

        List<WorkFromHome> workFromHomeList = dao.searchOrReadWorkFromHomeRequest(userId, date, reason, statusName, from, range);
        if(workFromHomeList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Work form home requests list size:: " + workFromHomeList.size());

        close(session);
        return TRANSFORMER.getAllWFHRequestDto(workFromHomeList);
    }

    @Override
    public WorkFromHomeDetails approveWFHRequest(WorkFromHomeDto wfhDto, String userId, String tenantName) throws CustomException {
        logger.info("Employees work form home request approval:: Start");
        logger.info("Approved user id: " + userId);
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        WorkFromHome workFromHome = TRANSFORMER.getWorkFromHomeRequest(wfhDto);
        validateForApproval(workFromHome);

        Session session = getSession();
        dao.setSession(session);
        TRANSFORMER.setSession(session);

        WorkFromHome existWFH = dao.getWorkFromHomeById(wfhDto.getWorkFromHomeId());
        if(existWFH == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(!existWFH.getStatus().getWorkFromHomeStatusName().equals(Constants.APPLIED_WFH_REQUEST)){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0011);
            throw new CustomException(errorMessage);
        }

        WorkFormHomeStatus status = dao.getWFHStatusByName(wfhDto.getWorkFromHomeStatusName());
        existWFH.setApprovedDate(Utility.today());
        existWFH.setLastModifiedDate(Utility.today());
        existWFH.setApprovedBy(userId);
        existWFH.setStatus(status);
        existWFH.setDeniedReason(wfhDto.getReason());
        dao.updateWorkFromHomeRequest(existWFH);
        logger.info("Work from home request approved/denied success");

        WorkFromHomeDetails wfhDetails = TRANSFORMER.getWFHRequestDetailsDto(existWFH);
        logger.info("Employees work form home request approval:: End");
        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            String email = employeeDao.getPreferredEmail(existWFH.getEmployee().getEmployeeId()).getEmail();
            JSONObject employeeContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, new JSONArray().put(email));

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, emailList);
            if(status.getWorkFromHomeStatusName().equals(Constants.DENIED_WFH_REQUEST)){
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_DENIED_TEMPLATE_ID_FOR_MANAGER_HR));

                notificationList.put(EmailContent.getNotificationObject(employeeContentObj,
                        NotificationConstant.WFH_DENIED_TEMPLATE_ID_FOR_EMPLOYEE));

            } else {
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.WFH_APPROVED_TEMPLATE_ID_FOR_MANAGER_HR));

                notificationList.put(EmailContent.getNotificationObject(employeeContentObj,
                        NotificationConstant.WFH_APPROVED_TEMPLATE_ID_FOR_EMPLOYEE));
            }

            List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(existWFH.getEmployee().getEmployeeId());
            if(!Utility.isNullOrEmpty(teamLeads)){

                emailList = new JSONArray();
                for(Employee teamLead : teamLeads){
                    emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                }

                globalContentObj = EmailContent.getContentForWFHRequest(existWFH, tenantName, emailList);
                if(status.getWorkFromHomeStatusName().equals(Constants.DENIED_WFH_REQUEST)){
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.WFH_DENIED_TEMPLATE_ID_FOR_LEAD));

                } else {
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.WFH_APPROVED_TEMPLATE_ID_FOR_LEAD));
                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return wfhDetails;
    }

    private void validateForApproval(WorkFromHome workFromHome) throws CustomException {
        if(workFromHome.getReason() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<WorkFromHomeDetails> searchOrReadEmployeesWFHRequests(String userId, String date, String reason, String statusName, String employeeNo,
                                                                      String firstName, String lastName, String nickName, String wfhId,
                                                                      String context, String from, String range) throws CustomException {

        logger.info("Search or read all employees work from home requests");
        if(!Utility.isNullOrEmpty(date) && Utility.getDateFromString(date) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        dao.setSession(session);
        TRANSFORMER.setSession(session);

        ContextDto contextDto = Utility.getContextDtoObj(context);
        List<WorkFromHome> workFromHomeList = dao.searchOrReadEmployeesWFHRequests(userId, date, reason, statusName, employeeNo, firstName,
                lastName, nickName, wfhId, contextDto, from, range);
        if(workFromHomeList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Work form home requests list size:: " + workFromHomeList.size());
        List<WorkFromHomeDetails> wfhDetails = TRANSFORMER.getWFHRequestDetailsDto(workFromHomeList);

        close(session);
        return wfhDetails;
    }
}

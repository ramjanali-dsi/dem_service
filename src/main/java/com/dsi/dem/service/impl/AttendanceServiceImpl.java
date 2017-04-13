package com.dsi.dem.service.impl;

import com.dsi.dem.dao.*;
import com.dsi.dem.dao.impl.*;
import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.util.*;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by sabbir on 10/19/16.
 */
public class AttendanceServiceImpl extends CommonService implements AttendanceService {

    private static final Logger logger = Logger.getLogger(AttendanceServiceImpl.class);

    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final ClientDao clientDao = new ClientDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final AttendanceDao attendanceDao = new AttendanceDaoImpl();
    private static final WorkFromHomeDao wfhDao = new WorkFromHomeDaoImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public void saveAttendance(List<AttendanceDto> attendanceDtoList, String userID,
                               String attendanceDate, String tenantName) throws CustomException {

        Session session = getSession();
        try {
            logger.info("Employees attendance schedule create: start");
            Date date = attendanceDateValidation(attendanceDate);

            JSONArray notificationList = new JSONArray();

            attendanceDao.setSession(session);
            leaveDao.setSession(session);
            wfhDao.setSession(session);

            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForAttendance(attendanceDate, tenantName, hrManagerEmailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.ATTENDANCE_CONFIRM_TEMPLATE_ID_FOR_MANAGER_HR));

            List<String> attendanceIgnoreList = Utility.getAttendanceIgnoreList();

            int count = 0;
            for (AttendanceDto attendanceDto : attendanceDtoList) {

                JSONArray hrManagerLeadEmailList = new JSONArray();
                TemporaryAttendance temporaryAttendance = attendanceDao.getTemporaryAttendance(
                        attendanceDto.getTempAttendanceId());
                if (temporaryAttendance == null) {
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                            Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
                    throw new CustomException(errorMessage);
                }

                validationForAttendance(session, attendanceDto);

                EmployeeAttendance attendance = new EmployeeAttendance();
                attendance.setAbsent(attendanceDto.isAbsent());
                attendance.setCheckInTime(attendanceDto.getCheckInTime());
                attendance.setCheckOutTime(attendanceDto.getCheckOutTime());

                if(attendanceDto.isAbsent()){
                    attendance.setTotalHour(attendanceDto.getTotalHour());

                } else {
                    attendance.setTotalHour(Utility.getTimeCalculation(attendanceDto.getCheckInTime(),
                            attendanceDto.getCheckOutTime()));
                }

                attendance.setEmployee(temporaryAttendance.getEmployee());
                attendance.setAttendanceDate(temporaryAttendance.getAttendanceDate());
                attendance.setCreatedBy(temporaryAttendance.getCreatedBy());
                attendance.setModifiedBy(employeeDao.getEmployeeByUserID(userID).getEmployeeId());
                attendance.setCreatedDate(Utility.today());
                attendance.setLastModifiedDate(Utility.today());
                attendance.setComment(temporaryAttendance.getComment());
                attendance.setVersion(1);

                String email;
                JSONArray leadEmails = new JSONArray();
                EmployeeLeave leaveSummary;
                WorkFromHome workFromHome = wfhDao.getWFHByEmployeeIdAndDate(attendance.getEmployee().getEmployeeId(), date);

                if (attendance.isAbsent()) {

                    if(!attendanceIgnoreList.contains(attendance.getEmployee().getEmployeeNo())) {

                        if (!leaveDao.getLeaveRequestByRequestTypeAndEmployeeNo(
                                attendance.getEmployee().getEmployeeNo(), date) && workFromHome == null) {
                            logger.info("Employee has no pre/post approved leave & work from home request.");

                            leaveSummary = leaveDao.getEmployeeLeaveSummary(attendance.getEmployee().getEmployeeId());
                            leaveSummary.setTotalNotNotify(leaveSummary.getTotalNotNotify() + 1);
                            leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed()
                                    + leaveSummary.getTotalSickUsed()
                                    + leaveSummary.getTotalNotNotify()
                                    + leaveSummary.getTotalSpecialLeave());
                            leaveDao.updateEmployeeLeaveSummary(leaveSummary);
                            logger.info("Leave summary updated for absent.");

                            email = employeeDao.getPreferredEmail(temporaryAttendance.getEmployee().getEmployeeId()).getEmail();
                            globalContentObj = EmailContent.getContentForAttendanceForEmployee(temporaryAttendance.getEmployee(),
                                    attendanceDate, tenantName, new JSONArray().put(email));

                            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                    NotificationConstant.ATTENDANCE_UN_NOTIFIED_TEMPLATE_ID_FOR_EMPLOYEE));

                            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                    NotificationConstant.ATTENDANCE_NOTIFIED_TEMPLATE_ID_FOR_EMPLOYEE));

                            List<Employee> leadList = employeeDao.getTeamLeadsProfileOfAnEmployee(temporaryAttendance.getEmployee().getEmployeeId());
                            if (!Utility.isNullOrEmpty(leadList)) {
                                for (Employee employee : leadList) {
                                    leadEmails.put(employeeDao.getPreferredEmail(employee.getEmployeeId()).getEmail());
                                }
                            }

                            if (hrManagerEmailList.length() > 0) {
                                for (int i = 0; i < hrManagerEmailList.length(); i++) {
                                    hrManagerLeadEmailList.put(hrManagerEmailList.get(i));
                                }
                            }

                            if (leadEmails.length() > 0) {
                                for (int i = 0; i < leadEmails.length(); i++) {
                                    hrManagerLeadEmailList.put(leadEmails.get(i));
                                }
                            }

                            globalContentObj = EmailContent.getContentForAttendanceForEmployee(temporaryAttendance.getEmployee(),
                                    attendanceDate, tenantName, hrManagerLeadEmailList);
                            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                    NotificationConstant.ATTENDANCE_UN_NOTIFIED_TEMPLATE_ID_FOR_MANAGER_HR_LEAD));

                            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                    NotificationConstant.ATTENDANCE_NOTIFIED_TEMPLATE_ID_FOR_MANAGER_HR_LEAD));

                        } else if (workFromHome != null) {
                            attendance.setAbsent(false);
                            attendance.setCheckInTime(Constants.CHECK_IN_TIME);
                            attendance.setCheckOutTime(Constants.CHECK_OUT_TIME);
                            attendance.setTotalHour(Utility.getTimeCalculation(Constants.CHECK_IN_TIME,
                                    Constants.CHECK_OUT_TIME));
                        }
                    }

                } else {
                    //TODO Approved pre & post leave request check for this date

                    logger.info("Employee present.");
                    LeaveRequest leaveRequest = leaveDao.getLeaveRequestByStatusAndEmployee(
                            attendance.getEmployee().getEmployeeNo(), date);

                    if (leaveRequest != null) {
                        logger.info("Employee has approved leave request.");
                        leaveSummary = leaveDao.getEmployeeLeaveSummary(attendance.getEmployee().getEmployeeId());

                        switch (leaveRequest.getLeaveType().getLeaveTypeName()) {
                            case Constants.SICK_TYPE_NAME:
                                leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - 1);

                                break;
                            case Constants.CASUAL_TYPE_NAME:
                                leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - 1);

                                break;
                            case Constants.SPECIAL_TYPE_NAME:
                                leaveSummary.setTotalSpecialLeave(leaveSummary.getTotalSpecialLeave() - 1);
                                break;
                        }

                        leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed()
                                + leaveSummary.getTotalSickUsed()
                                + leaveSummary.getTotalNotNotify()
                                + leaveSummary.getTotalSpecialLeave());
                        leaveDao.updateEmployeeLeaveSummary(leaveSummary);
                        logger.info("Leave summary updated for present.");

                        attendance.setComment(Constants.LEAVE_CANCEL_COMMENT);

                        email = employeeDao.getPreferredEmail(temporaryAttendance.getEmployee().getEmployeeId()).getEmail();
                        globalContentObj = EmailContent.getContentForAttendanceApproveLeave(temporaryAttendance.getEmployee(),
                                leaveRequest, attendanceDate, tenantName, new JSONArray().put(email));

                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.ATTENDANCE_PRESENT_APPROVE_TEMPLATE_ID_FOR_EMPLOYEE));

                        List<Employee> leadList = employeeDao.getTeamLeadsProfileOfAnEmployee(temporaryAttendance.getEmployee().getEmployeeId());
                        if (!Utility.isNullOrEmpty(leadList)) {
                            for (Employee employee : leadList) {
                                leadEmails.put(employeeDao.getPreferredEmail(employee.getEmployeeId()).getEmail());
                            }
                        }

                        if(hrManagerEmailList.length() > 0) {
                            for (int i = 0; i < hrManagerEmailList.length(); i++) {
                                hrManagerLeadEmailList.put(hrManagerEmailList.get(i));
                            }
                        }

                        if(leadEmails.length() > 0) {
                            for (int i = 0; i<leadEmails.length(); i++) {
                                hrManagerLeadEmailList.put(leadEmails.get(i));
                            }
                        }

                        globalContentObj = EmailContent.getContentForAttendanceApproveLeave(temporaryAttendance.getEmployee(),
                                leaveRequest, attendanceDate, tenantName, hrManagerLeadEmailList);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.ATTENDANCE_PRESENT_APPROVE_TEMPLATE_ID_FOR_MANAGER_HR_LEAD));

                        if(leaveRequest.isClientNotify()){
                            JSONArray clientEmails = new JSONArray();
                            clientDao.setSession(session);

                            List<Client> clientList = clientDao.getAllClientsByEmployeeId(leaveRequest.getEmployee().getEmployeeId());

                            if (!Utility.isNullOrEmpty(clientList)) {
                                for (Client client : clientList) {
                                    if (client.isNotify()) {
                                        clientEmails.put(client.getMemberEmail());
                                    }
                                }
                            }

                            if(clientEmails.length() > 0) {
                                globalContentObj = EmailContent.getContentForAttendanceApproveLeave(temporaryAttendance.getEmployee(),
                                        leaveRequest, attendanceDate, tenantName, clientEmails);
                                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                        NotificationConstant.ATTENDANCE_PRESENT_APPROVE_TEMPLATE_ID_FOR_CLIENT));
                            }
                        }

                    } else if(workFromHome != null){
                        logger.info("Employee has approved work from home request.");

                        workFromHome.setStatus(wfhDao.getWFHStatusByName(Constants.CANCELLER_WFH_REQUEST));
                        workFromHome.setLastModifiedDate(Utility.today());
                        workFromHome.setReason("Already present that day.");
                        wfhDao.updateWorkFromHomeRequest(workFromHome);
                        logger.info("Work form home request updated to cancel status.");

                        attendance.setComment(Constants.WFH_CANCEL_COMMENT);

                        if(hrManagerEmailList.length() > 0) {
                            for (int i = 0; i < hrManagerEmailList.length(); i++) {
                                hrManagerLeadEmailList.put(hrManagerEmailList.get(i));
                            }
                        }

                        if(leadEmails.length() > 0) {
                            for (int i = 0; i<leadEmails.length(); i++) {
                                hrManagerLeadEmailList.put(leadEmails.get(i));
                            }
                        }
                        //hrManagerLeadEmailList.put(hrManagerEmailList);
                        //hrManagerLeadEmailList.put(leadEmails);

                        globalContentObj = EmailContent.getContentForAttendanceApproveWFH(workFromHome, attendanceDate,
                                tenantName, hrManagerLeadEmailList);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.WFH_APPROVED_PRESENT_TEMPLATE_ID_FOR_MANAGER_HR_LEAD));

                        email = employeeDao.getPreferredEmail(temporaryAttendance.getEmployee().getEmployeeId()).getEmail();
                        globalContentObj = EmailContent.getContentForAttendanceApproveWFH(workFromHome, attendanceDate, tenantName,
                                new JSONArray().put(email));
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.WFH_APPROVED_PRESENT_TEMPLATE_ID_FOR_EMPLOYEE));
                    }
                }

                attendanceDao.saveAttendance(attendance);
                logger.info("Save employee attendance success");

                if(count % 20 == 0){
                    session.flush();
                    session.clear();
                }
                count++;
            }

            logger.info("Delete all temporary attendances.");
            attendanceDao.deleteTempAttendance(Utility.getDateFromString(attendanceDate));

            logger.info("Delete attendance draft file.");
            attendanceDao.deleteAttendanceDraft(Utility.getDateFromString(attendanceDate));

            logger.info("Employees attendance schedule create: End");
            close(session);

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    private void validationForAttendance(Session session, AttendanceDto attendanceDto) throws CustomException {

        if(!attendanceDto.isAbsent()) {
            if (Utility.isNullOrEmpty(attendanceDto.getCheckInTime())
                    || Utility.isNullOrEmpty(attendanceDto.getCheckOutTime())) {
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0002);
                throw new CustomException(errorMessage);
            }

        } else {
            if (!Utility.isNullOrEmpty(attendanceDto.getCheckInTime())
                    || !Utility.isNullOrEmpty(attendanceDto.getCheckOutTime())) {
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0003);
                throw new CustomException(errorMessage);
            }
        }
    }

    private Date attendanceDateValidation(String attendanceDate) throws CustomException {
        if(Utility.isNullOrEmpty(attendanceDate)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        Date date = Utility.getDateFromString(attendanceDate);
        if(date == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        return date;
    }

    @Override
    public void updateAttendance(EmployeeAttendance attendance) throws CustomException {
        Session session = getSession();
        attendanceDao.setSession(session);

        attendanceDao.updateAttendance(attendance);
        logger.info("Update employee attendance success");

        close(session);
    }

    @Override
    public void deleteAttendance(String attendanceDate, String tenantName) throws CustomException {
        logger.info("Delete all attendances:: Start");
        attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        /*attendanceDao.deleteTempAttendance(Utility.getDateFromString(attendanceDate));
        attendanceDao.deleteAttendanceDraft(Utility.getDateFromString(attendanceDate));

        logger.info("Delete temporary attendances & draft file success");
        logger.info("Delete all attendances:: End");
        close(session);*/

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForAttendance(attendanceDate, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.ATTENDANCE_DELETE_TEMPLATE_ID_FOR_MANAGER_HR));

            attendanceDao.deleteTempAttendance(Utility.getDateFromString(attendanceDate));
            attendanceDao.deleteAttendanceDraft(Utility.getDateFromString(attendanceDate));

            logger.info("Delete temporary attendances & draft file success");
            logger.info("Delete all attendances:: End");
            close(session);

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId) throws CustomException {
        Session session = getSession();
        attendanceDao.setSession(session);

        EmployeeAttendance attendance = attendanceDao.getAttendanceByID(attendanceId, null);
        if(attendance == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return attendance;
    }

    @Override
    public List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId) throws CustomException {
        Session session = getSession();
        attendanceDao.setSession(session);

        List<EmployeeAttendance> attendanceList = attendanceDao.getEmployeesAllAttendances(employeeId);
        if(attendanceList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> getAllAttendancesByDate(String attendanceDate) throws CustomException {
        logger.info("Read all confirmed attendances by attendanceDate: " + attendanceDate);
        Date date = attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        List<EmployeeAttendance> attendanceList = attendanceDao.getAllAttendancesByDate(date);
        if(attendanceList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> searchOrReadAttendances(String employeeNo, String isAbsent, String firstName,
                                                            String lastName, String nickName, String attendanceDate, String teamName,
                                                            String projectName, String context, String from, String range) throws CustomException {
        if(!Utility.isNullOrEmpty(attendanceDate)){
            attendanceDateValidation(attendanceDate);
        }

        Session session = getSession();
        attendanceDao.setSession(session);

        ContextDto contextDto = Utility.getContextDtoObj(context);
        //List<String> contextList = Utility.getContextObj(context);
        List<EmployeeAttendance> attendanceList = attendanceDao.searchOrReadAttendances(employeeNo, isAbsent, firstName,
                lastName, nickName, attendanceDate, teamName, projectName, contextDto, from, range);
        if(attendanceList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return attendanceList;
    }

    @Override
    public boolean isAvailableEmployeeOrTempAttendanceSheet(String attendanceDate) throws CustomException {

        logger.info("Check attendance schedule availability for this date: " + attendanceDate);
        Date date = attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        boolean result = attendanceDao.isAvailableEmployeeOrTempAttendanceSheet(date);

        close(session);
        return result;
    }

    @Override
    public boolean getAttendanceStatus(String attendanceDate, String mode) throws CustomException {
        Date date = attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        AttendanceStatus attendanceStatus = null;
        if(mode.equals("1")){
            attendanceStatus = attendanceDao.getAttendanceStatus(date, Constants.TEMPORARY_ATTENDANCE_TYPE);

        } else if(mode.equals("2")){
            attendanceStatus = attendanceDao.getAttendanceStatus(date, Constants.CONFIRM_ATTENDANCE_TYPE);
        }

        if(attendanceStatus == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return attendanceStatus.isStatus();
    }


    @Override
    public List<DraftAttendance> saveTempAttendance(InputStream inputStream, String userID,
                                                    String tenantName) throws CustomException {

        logger.info("Attendance sheet upload:: Start");
        if (inputStream == null) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
            throw new CustomException(errorMessage);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String csvSplitBy = ",";

        String attendanceDateTime = "";
        boolean checkDateFlag = true;
        boolean once = true;

        Map<String, String> inMap = new HashMap<>();
        Map<String, String> outMap = new HashMap<>();

        Session session = getSession();
        attendanceDao.setSession(session);

        try {
            int CSV_TYPE_COLUMN = 0;
            int CSV_EMPLOYEE_ID_COLUMN = 0;
            int CSV_DATE_TIME_COLUMN = 0;
            int CSV_FUNCTION_KEY_COLUMN = 0;

            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(csvSplitBy);

                if(lineSplit.length > 0) {

                    if(!Utility.isNullOrEmpty(lineSplit[0])){

                        if(once) {

                            logger.info("CSV index config:: start");
                            for(int i=0; i<lineSplit.length; i++){

                                if(lineSplit[i].equals(ReadXMLFile.EVENT_TIME)){
                                    CSV_DATE_TIME_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.USER_ID)){
                                    CSV_EMPLOYEE_ID_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.TERMINAL_ID)){
                                    CSV_TYPE_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.RESULT)){
                                    CSV_FUNCTION_KEY_COLUMN = i;
                                }
                            }
                            logger.info("CSV index config:: end");
                            once = false;

                        } else {

                            if (lineSplit[CSV_FUNCTION_KEY_COLUMN].equals(ReadXMLFile.STATUS)) {

                                if (checkDateFlag) {
                                    attendanceDateTime = Utility.getDateFromStringForAttendance(
                                            lineSplit[CSV_DATE_TIME_COLUMN]);

                                    logger.info("Attendance check: " + attendanceDateTime);
                                    if (attendanceDao.isAvailableEmployeeOrTempAttendanceSheet(Utility.
                                            getDateFromString(attendanceDateTime))) {

                                        close(session);
                                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                                                Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0007);
                                        throw new CustomException(errorMessage);
                                    }

                                    close(session);
                                    checkDateFlag = false;
                                }

                                String employeeIdVal = lineSplit[CSV_EMPLOYEE_ID_COLUMN].replace("\'", "");
                                Timestamp newTime = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);

                                if(inMap.get(employeeIdVal) != null && outMap.get(employeeIdVal) != null) {
                                    Timestamp prevInTime = Utility.getTimeStampFromString(inMap.get(employeeIdVal));
                                    Timestamp prevOutTime = Utility.getTimeStampFromString(outMap.get(employeeIdVal));

                                    if (prevInTime.after(newTime)) {
                                        inMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                    if (prevOutTime.before(newTime)) {
                                        inMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    inMap.put(employeeIdVal,
                                            lineSplit[CSV_DATE_TIME_COLUMN]);

                                    outMap.put(employeeIdVal,
                                            lineSplit[CSV_DATE_TIME_COLUMN]);
                                }

                                /*if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.IN_TIME)) {

                                    if (inMap.get(employeeIdVal) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(inMap.get(employeeIdVal));

                                        if (prevDate.after(nextDate)) {
                                            inMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        inMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                } else if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.OUT_TIME)) {
                                    if (outMap.get(employeeIdVal) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(outMap.get(employeeIdVal));

                                        if (prevDate.before(nextDate)) {
                                            outMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        outMap.put(employeeIdVal, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }
                                }*/
                            }
                        }

                    }
                }
            }

        } catch (IOException io) {
            close(session);
            io.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
            throw new CustomException(errorMessage);
        }

        logger.info("InTimeMap: -------> " + new Gson().toJson(inMap));
        logger.info("\n\nOutTimeMap: ------> " + new Gson().toJson(outMap));

        DraftAttendance draftAttendance = null;
        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(!Utility.isNullOrEmpty(employeeList)){
            logger.info("Total employee list: " + employeeList.size());
            Employee currentEmployee = employeeDao.getEmployeeByUserID(userID);
            if(currentEmployee == null){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                        Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }

            session = getSession();
            attendanceDao.setSession(session);
            wfhDao.setSession(session);
            leaveDao.setSession(session);
            once = true;

            int count = 0;
            for(Employee employee : employeeList){
                String employeeNo = employee.getEmployeeNo();
                logger.info("Employee no: " + employeeNo);

                if(employee.isActive()) {
                    if (inMap.containsKey(employeeNo) && outMap.containsKey(employeeNo)) {
                        TemporaryAttendance tempAttendance = new TemporaryAttendance();
                        tempAttendance.setEmployee(employee);
                        tempAttendance.setAbsent(false);
                        tempAttendance.setAttendanceDate(Utility.getDateFromString(attendanceDateTime));
                        tempAttendance.setCheckInTime(Utility.getTime(inMap.get(employeeNo)));
                        tempAttendance.setCheckOutTime(Utility.getTime(outMap.get(employeeNo)));
                        tempAttendance.setTotalHour(Utility.getTimeCalculation(
                                Utility.getTime(inMap.get(employeeNo)), Utility.getTime(outMap.get(employeeNo))));
                        tempAttendance.setCreatedDate(Utility.today());
                        tempAttendance.setLastModifiedDate(Utility.today());
                        tempAttendance.setCreatedBy(currentEmployee.getEmployeeId());
                        tempAttendance.setModifiedBy(currentEmployee.getEmployeeId());
                        tempAttendance.setVersion(1);

                        attendanceDao.saveTempAttendance(tempAttendance);

                    } else {
                        TemporaryAttendance tempAttendance = new TemporaryAttendance();
                        tempAttendance.setEmployee(employee);
                        tempAttendance.setAbsent(true);
                        tempAttendance.setAttendanceDate(Utility.getDateFromString(attendanceDateTime));
                        tempAttendance.setCreatedDate(Utility.today());
                        tempAttendance.setLastModifiedDate(Utility.today());
                        tempAttendance.setCreatedBy(currentEmployee.getEmployeeId());
                        tempAttendance.setModifiedBy(currentEmployee.getEmployeeId());
                        tempAttendance.setVersion(1);

                        logger.info("Check employee has approved leave/wfh");
                        Date date = Utility.getDateFromString(attendanceDateTime);
                        WorkFromHome workFromHome = wfhDao.getWFHByEmployeeIdAndDate(employee.getEmployeeId(), date);

                        if (leaveDao.getLeaveRequestByRequestTypeAndEmployeeNo(employeeNo, date)) {
                            logger.info("Employee has approved pre/post leave request.");
                            tempAttendance.setComment(Constants.LEAVE_COMMENT);

                        } else if (workFromHome != null) {
                            logger.info("Employee has approved work form home request.");
                            tempAttendance.setComment(Constants.WFH_COMMENT);
                        }

                        attendanceDao.saveTempAttendance(tempAttendance);
                    }

                    if (once) {
                        Date date = attendanceDateValidation(attendanceDateTime);

                        draftAttendance = attendanceDao.getDraftAttendanceFileByDate(date);
                        if (draftAttendance == null) {
                            draftAttendance = new DraftAttendance();
                            draftAttendance.setAttendanceDate(Utility.getDateFromString(attendanceDateTime));
                            draftAttendance.setCreatedDate(Utility.todayTimeStamp());
                            draftAttendance.setLastModifiedDate(Utility.todayTimeStamp());
                            attendanceDao.saveAttendanceDraft(draftAttendance);
                        }
                        once = false;
                    }

                    if (count % 20 == 0) {
                        session.flush();
                        session.clear();
                    }
                    count++;

                    logger.info("Save temporary attendance success");
                }
            }
        }

        List<DraftAttendance> draftAttendances = attendanceDao.getAllDraftAttendanceFileDetails(
                Constants.FROM, Constants.RANGE);
        logger.info("Attendance sheet upload:: End");
        close(session);

        try{
            if(draftAttendance != null) {
                logger.info("Notification create:: Start");
                JSONArray notificationList = new JSONArray();

                JSONArray emailList = notificationService.getHrManagerEmailList();
                JSONObject globalContentObj = EmailContent.getContentForAttendance(attendanceDateTime,
                        tenantName, emailList);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.ATTENDANCE_UPLOAD_TEMPLATE_ID_FOR_MANAGER_HR));

                notificationService.createNotification(notificationList.toString());
                logger.info("Notification create:: End");
            }

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return draftAttendances;
    }

    @Override
    public void updateTempAttendance(List<AttendanceDto> attendanceDtoList, String userID,
                                     String attendanceDate, String tenantName) throws CustomException {

        logger.info("Update employees temporary attendance: Start");
        attendanceDateValidation(attendanceDate);

        if(Utility.isNullOrEmpty(attendanceDtoList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        attendanceDao.setSession(session);

        int count = 0;
        for(AttendanceDto attendanceDto : attendanceDtoList){
            if(attendanceDto.getVersion() == 0){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
                throw new CustomException(errorMessage);
            }

            TemporaryAttendance existTempAttendance = attendanceDao.getTemporaryAttendance(attendanceDto.getTempAttendanceId());
            if(existTempAttendance == null){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                        Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }

            validationForAttendance(session, attendanceDto);

            if(!attendanceDto.isAbsent()){
                existTempAttendance.setCheckInTime(attendanceDto.getCheckInTime());
                existTempAttendance.setCheckOutTime(attendanceDto.getCheckOutTime());
                existTempAttendance.setTotalHour(Utility.getTimeCalculation(attendanceDto.getCheckInTime(),
                        attendanceDto.getCheckOutTime()));
                existTempAttendance.setComment(attendanceDto.getComment());

            } else {
                existTempAttendance.setCheckInTime(null);
                existTempAttendance.setCheckOutTime(null);
                existTempAttendance.setTotalHour(null);
            }
            existTempAttendance.setAbsent(attendanceDto.isAbsent());
            existTempAttendance.setLastModifiedDate(Utility.today());
            existTempAttendance.setModifiedBy(employeeDao.getEmployeeByUserID(userID).getEmployeeId());
            existTempAttendance.setVersion(attendanceDto.getVersion());

            attendanceDao.updateTempAttendance(existTempAttendance);

            if(count % 20 == 0){
                session.flush();
                session.clear();
            }
            count++;

            logger.info("Update temporary attendance success");
        }
        logger.info("Update employees temporary attendance: End");
        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForAttendance(attendanceDate, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.ATTENDANCE_SAVE_DRAFT_TEMPLATE_ID_FOR_MANAGER_HR));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTempAttendance() throws CustomException {
        Date currentDate = Utility.getDateFormatFromDate(Utility.today());
        Date daysAgo = Utility.getDateFormatFromDate(new DateTime(currentDate).minusDays(Constants.DAYS_AGO_COUNT).toDate());

        logger.info("Delete temporary attendances & draft file for this date: " + daysAgo);
        Session session = getSession();
        attendanceDao.setSession(session);

        attendanceDao.deleteTemporaryAttendanceFromCron(daysAgo);
        attendanceDao.deleteAttendanceDraftFromCron(daysAgo);
        logger.info("Delete temporary attendance success");

        close(session);
    }

    @Override
    public List<TemporaryAttendance> getAllTempAttendances(String attendanceDate) throws CustomException {

        logger.info("Read all temporary attendances for this date: " + attendanceDate);
        Date date = attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        List<TemporaryAttendance> temporaryAttendances = attendanceDao.getAllTempAttendances(date);
        if(temporaryAttendances == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Temporary attendance list size: " + temporaryAttendances.size());

        close(session);
        return temporaryAttendances;
    }

    @Override
    public TemporaryAttendance getTemporaryAttendance(String tempAttendanceId) throws CustomException {
        Session session = getSession();
        attendanceDao.setSession(session);

        TemporaryAttendance temporaryAttendance = attendanceDao.getTemporaryAttendance(tempAttendanceId);
        if(temporaryAttendance == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return temporaryAttendance;
    }

    @Override
    public List<DraftAttendance> getDraftAttendanceFileDetails(String from, String range) throws CustomException {
        if(Utility.isNullOrEmpty(from) || Utility.isNullOrEmpty(range)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_009);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        attendanceDao.setSession(session);

        List<DraftAttendance> draftAttendances = attendanceDao.getAllDraftAttendanceFileDetails(from, range);
        if(draftAttendances == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return draftAttendances;
    }

    @Override
    public void getDraftAttendanceFileDetailsByDate(Date createDate) {
        logger.info("Read all draft attendance files for this date: " + createDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        List<DraftAttendance> draftAttendances = attendanceDao.getAllDraftAttendanceFileByCreatedDate(createDate);
        if(!Utility.isNullOrEmpty(draftAttendances)){
            logger.info("Draft attendance file list size: " + draftAttendances.size());
            try {
                logger.info("Notification create:: Start");
                JSONArray notificationList = new JSONArray();

                JSONArray emailList = notificationService.getHrManagerEmailList();
                JSONObject globalContentObj;

                int cnt = 0;
                String draftAttendanceFile = "";
                for (DraftAttendance draftAttendance : draftAttendances) {
                    draftAttendanceFile += draftAttendance.getAttendanceDate();

                    if(cnt != draftAttendances.size() - 1){
                        draftAttendanceFile += ", ";
                    }
                    cnt++;
                }

                globalContentObj = EmailContent.getContentForDraftAttendance(draftAttendanceFile, Constants.TENANT_NAME, emailList);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.AUTO_NOTIFY_DRAFT_ATTENDANCE_EXPIRY_TEMPLATE_ID));

                notificationService.createNotification(notificationList.toString());
                logger.info("Notification create:: End");

            } catch (Exception e){
                close(session);
                e.printStackTrace();
            }

        } else {
            logger.info("Draft attendance file list is empty.");
        }
        close(session);
    }
}

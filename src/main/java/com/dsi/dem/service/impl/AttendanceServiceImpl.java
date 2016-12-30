package com.dsi.dem.service.impl;

import com.dsi.dem.dao.AttendanceDao;
import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.impl.AttendanceDaoImpl;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.ReadXMLFile;
import com.dsi.dem.util.Utility;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 10/19/16.
 */
public class AttendanceServiceImpl extends CommonService implements AttendanceService {

    private static final Logger logger = Logger.getLogger(AttendanceServiceImpl.class);

    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final AttendanceDao attendanceDao = new AttendanceDaoImpl();

    @Override
    public void saveAttendance(List<AttendanceDto> attendanceDtoList, String userID,
                               String attendanceDate) throws CustomException {

        logger.info("Employees attendance schedule create: start");
        Date date = attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);
        leaveDao.setSession(session);
        //employeeDao.setSession(session);

        for(AttendanceDto attendanceDto : attendanceDtoList){

            TemporaryAttendance temporaryAttendance = attendanceDao.getTemporaryAttendance(
                    attendanceDto.getTempAttendanceId());
            if(temporaryAttendance == null){
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
            attendance.setTotalHour(Utility.getTimeCalculation(attendanceDto.getCheckInTime(),
                    attendanceDto.getCheckOutTime()));

            attendance.setEmployee(temporaryAttendance.getEmployee());
            attendance.setAttendanceDate(temporaryAttendance.getAttendanceDate());
            attendance.setCreatedBy(temporaryAttendance.getCreatedBy());
            attendance.setModifiedBy(employeeDao.getEmployeeByUserID(userID).getEmployeeId());
            attendance.setCreatedDate(Utility.today());
            attendance.setLastModifiedDate(Utility.today());
            attendance.setVersion(1);
            attendanceDao.saveAttendance(attendance);
            logger.info("Save employee attendance success");

            EmployeeLeave leaveSummary;
            if(attendance.isAbsent()){
                //TODO Approved pre & post leave request check for this date

                if(!leaveDao.getLeaveRequestByRequestTypeAndEmployeeNo(
                        attendance.getEmployee().getEmployeeNo(), date)){

                    logger.info("Employee has no pre & post leave request.");

                    LeaveRequest leaveRequest = new LeaveRequest();

                    leaveSummary = leaveDao.getEmployeeLeaveSummary(attendance.getEmployee().getEmployeeId());
                    leaveSummary.setTotalNotNotify(leaveSummary.getTotalNotNotify() + 1);
                    leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed()
                            + leaveSummary.getTotalSickUsed()
                            + leaveSummary.getTotalNotNotify()
                            + leaveSummary.getTotalSpecialLeave());
                    leaveDao.updateEmployeeLeaveSummary(leaveSummary);
                    logger.info("Leave summary updated for absent.");
                }

            } else {
                //TODO Approved pre & post leave request check for this date

                LeaveRequest leaveRequest = leaveDao.getLeaveRequestByStatusAndEmployee(
                        attendance.getEmployee().getEmployeeNo(), date);

                if(leaveRequest != null){
                    leaveSummary = leaveDao.getEmployeeLeaveSummary(attendance.getEmployee().getEmployeeId());

                    if(leaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
                        leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - 1);

                    } else if(leaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                        leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - 1);

                    } else if(leaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SPECIAL_TYPE_NAME)){
                        leaveSummary.setTotalSpecialLeave(leaveSummary.getTotalSpecialLeave() - 1);
                    }

                    leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed()
                            + leaveSummary.getTotalSickUsed()
                            + leaveSummary.getTotalNotNotify()
                            + leaveSummary.getTotalSpecialLeave());
                    leaveDao.updateEmployeeLeaveSummary(leaveSummary);
                    logger.info("Leave summary updated for present.");
                }
            }
        }

        logger.info("Delete all temporary attendances.");
        attendanceDao.deleteTempAttendance(Utility.getDateFromString(attendanceDate));

        logger.info("Delete attendance draft file.");
        attendanceDao.deleteAttendanceDraft(Utility.getDateFromString(attendanceDate));

        logger.info("Employees attendance schedule create: End");

        close(session);
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
    public void deleteAttendance(String attendanceDate) throws CustomException {
        logger.info("Delete all attendances:: Start");
        attendanceDateValidation(attendanceDate);

        Session session = getSession();
        attendanceDao.setSession(session);

        attendanceDao.deleteTempAttendance(Utility.getDateFromString(attendanceDate));
        attendanceDao.deleteAttendanceDraft(Utility.getDateFromString(attendanceDate));

        logger.info("Delete temporary attendances & draft file success");
        logger.info("Delete all attendances:: End");
        close(session);
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
    public List<EmployeeAttendance> searchOrReadAttendances(String userId, String employeeNo, String isAbsent, String firstName,
                                                            String lastName, String nickName, String attendanceDate, String teamName,
                                                            String projectName, String from, String range) throws CustomException {
        if(!Utility.isNullOrEmpty(attendanceDate)){
            attendanceDateValidation(attendanceDate);
        }

        Session session = getSession();
        attendanceDao.setSession(session);

        List<EmployeeAttendance> attendanceList = attendanceDao.searchOrReadAttendances(userId, employeeNo, isAbsent, firstName,
                lastName, nickName, attendanceDate, teamName, projectName, from, range);
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
            attendanceStatus = attendanceDao.getAttendanceStatus(date, Constants.TEMPORARY_LEAVE_TYPE);

        } else if(mode.equals("2")){
            attendanceStatus = attendanceDao.getAttendanceStatus(date, Constants.CONFIRM_LEAVE_TYPE);
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
    public List<DraftAttendance> saveTempAttendance(InputStream inputStream, String userID) throws CustomException {

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
        //employeeDao.setSession(session);
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
                                    checkDateFlag = false;
                                }

                                if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.IN_TIME)) {

                                    if (inMap.get(lineSplit[CSV_EMPLOYEE_ID_COLUMN]) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(inMap.get(lineSplit[CSV_EMPLOYEE_ID_COLUMN]));

                                        if (prevDate.after(nextDate)) {
                                            inMap.put(lineSplit[CSV_EMPLOYEE_ID_COLUMN],
                                                    lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        inMap.put(lineSplit[CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                } else if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.OUT_TIME)) {
                                    if (outMap.get(lineSplit[CSV_EMPLOYEE_ID_COLUMN]) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(outMap.get(lineSplit[CSV_EMPLOYEE_ID_COLUMN]));

                                        if (prevDate.before(nextDate)) {
                                            outMap.put(lineSplit[CSV_EMPLOYEE_ID_COLUMN],
                                                    lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        outMap.put(lineSplit[CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }
                                }
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

        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(!Utility.isNullOrEmpty(employeeList)){
            logger.info("Total employee list: " + employeeList.size());
            Employee currentEmployee = employeeDao.getEmployeeByUserID(userID);
            if(currentEmployee == null){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                        Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
                throw new CustomException(errorMessage);
            }

            once = true;
            DraftAttendance draftAttendance;

            for(Employee employee : employeeList){
                String employeeNo = employee.getEmployeeNo();
                logger.info("Employee no: " + employeeNo);

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

                    if (once) {
                        Date date = attendanceDateValidation(attendanceDateTime);

                        draftAttendance = attendanceDao.getDraftAttendanceFileByDate(date);
                        if(draftAttendance == null) {
                            draftAttendance = new DraftAttendance();
                            draftAttendance.setAttendanceDate(Utility.getDateFromString(attendanceDateTime));
                            draftAttendance.setCreatedDate(Utility.todayTimeStamp());
                            draftAttendance.setLastModifiedDate(Utility.todayTimeStamp());
                            attendanceDao.saveAttendanceDraft(draftAttendance);
                        }
                        once = false;
                    }

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

                    attendanceDao.saveTempAttendance(tempAttendance);
                }
                logger.info("Save temporary attendance success");
            }
        }

        List<DraftAttendance> draftAttendances = attendanceDao.getAllDraftAttendanceFileDetails(
                Constants.FROM, Constants.RANGE);
        logger.info("Attendance sheet upload:: End");

        close(session);
        return draftAttendances;
    }

    @Override
    public void updateTempAttendance(List<AttendanceDto> attendanceDtoList, String userID,
                                     String attendanceDate) throws CustomException {

        logger.info("Update employees temporary attendance: Start");
        attendanceDateValidation(attendanceDate);

        if(Utility.isNullOrEmpty(attendanceDtoList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        //employeeDao.setSession(session);
        attendanceDao.setSession(session);

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
            }
            existTempAttendance.setAbsent(attendanceDto.isAbsent());
            existTempAttendance.setLastModifiedDate(Utility.today());
            existTempAttendance.setModifiedBy(employeeDao.getEmployeeByUserID(userID).getEmployeeId());
            existTempAttendance.setVersion(attendanceDto.getVersion());

            attendanceDao.updateTempAttendance(existTempAttendance);
            logger.info("Update temporary attendance success");
        }
        logger.info("Update employees temporary attendance: End");

        close(session);
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
}

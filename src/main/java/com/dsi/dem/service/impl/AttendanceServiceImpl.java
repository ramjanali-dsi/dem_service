package com.dsi.dem.service.impl;

import com.dsi.dem.dao.AttendanceDao;
import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.impl.AttendanceDaoImpl;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 10/19/16.
 */
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger logger = Logger.getLogger(AttendanceServiceImpl.class);

    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final AttendanceDao attendanceDao = new AttendanceDaoImpl();

    @Override
    public boolean saveAttendance(String attendanceDate) throws CustomException {
        logger.info("Check attendance schedule availability for this date: " + attendanceDate);
        if(Utility.getDateFromString(attendanceDate) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        List<TemporaryAttendance> tempAttendances = attendanceDao.getAllTempAttendances(attendanceDate);
        if(tempAttendances == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Temporary attendances list size: " + tempAttendances.size());

        new Thread(() -> {
            AttendanceStatus attendanceStatus;
            try {

                attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.CONFIRM_LEAVE_TYPE);
                if(attendanceStatus == null){
                    attendanceStatus = new AttendanceStatus();
                    attendanceStatus.setStatus(false);
                    attendanceStatus.setType(Constants.CONFIRM_LEAVE_TYPE);
                    attendanceStatus.setAttendanceDate(Utility.getDateFromString(attendanceDate));
                    attendanceDao.saveAttendanceStatus(attendanceStatus);
                }

                for (TemporaryAttendance tempAttendance : tempAttendances) {
                    EmployeeAttendance attendance = new EmployeeAttendance();
                    BeanUtils.copyProperties(attendance, tempAttendance);

                    attendance.setEmployee(tempAttendance.getEmployee());
                    attendance.setLastModifiedDate(Utility.today());

                    attendanceDao.saveAttendance(attendance);

                    EmployeeLeave leaveSummary;
                    if(attendance.isAbsent()){
                        //TODO Approved pre & post leave request check for this date

                        if(!leaveDao.getLeaveRequestByRequestTypeAndEmployeeNo(attendance.getEmployee().getEmployeeNo(), attendanceDate)){
                            logger.info("Employee has no pre & post leave request.");

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

                        LeaveRequest leaveRequest = leaveDao.getLeaveRequestByStatusAndEmployee(attendance.getEmployee().getEmployeeNo(), attendanceDate);
                        if(leaveRequest != null){
                            leaveSummary = leaveDao.getEmployeeLeaveSummary(attendance.getEmployee().getEmployeeId());

                            if(leaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
                                leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - 1);

                            } else if(leaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                                leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - 1);
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

                attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.CONFIRM_LEAVE_TYPE);
                attendanceStatus.setStatus(true);
                attendanceDao.updateAttendanceStatus(attendanceStatus);

            } catch (Exception e) {
                logger.info("Employees attendance save failed: " + e.getMessage());
            }
        }).start();

        logger.info("Save employee attendance success");
        return true;
    }

    @Override
    public void updateAttendance(EmployeeAttendance attendance) throws CustomException {
        boolean res = attendanceDao.updateAttendance(attendance);
        if(!res) {
            //ErrorContext errorContext = new ErrorContext(attendance.getEmployeeAttendanceId(), "Attendance","Employee attendance update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employee attendance success");
    }

    @Override
    public void deleteAttendance(String attendanceId, String employeeId) throws CustomException {
        boolean res = attendanceDao.deleteAttendance(attendanceId, null);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(attendanceId, "Attendance", "Employee attendance delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employee attendance success");
    }

    @Override
    public EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId) throws CustomException {
        EmployeeAttendance attendance = attendanceDao.getAttendanceByID(attendanceId, null);
        if(attendance == null){
            //ErrorContext errorContext = new ErrorContext(attendanceId, "Attendance", "Employee attendance not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return attendance;
    }

    @Override
    public List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId) throws CustomException {
        List<EmployeeAttendance> attendanceList = attendanceDao.getEmployeesAllAttendances(employeeId);
        if(attendanceList == null){
            //ErrorContext errorContext = new ErrorContext(employeeId, "Attendance", "Employees attendance list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> getAllAttendancesByDate(String attendanceDate) throws CustomException {
        List<EmployeeAttendance> attendanceList = attendanceDao.getAllAttendancesByDate(attendanceDate);
        if(attendanceList == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> searchOrReadAttendances(String userId, String employeeNo, String isAbsent, String firstName,
                                                            String lastName, String nickName, String attendanceDate, String teamName,
                                                            String projectName, String from, String range) throws CustomException {

        List<EmployeeAttendance> attendanceList = attendanceDao.searchOrReadAttendances(userId, employeeNo, isAbsent, firstName,
                lastName, nickName, attendanceDate, teamName, projectName, from, range);
        if(attendanceList == null){
            //ErrorContext errorContext = new ErrorContext(null, "Attendance", "Employees attendance list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return attendanceList;
    }

    @Override
    public boolean isAvailableEmployeeOrTempAttendanceSheet(String attendanceDate) throws CustomException {

        logger.info("Check attendance schedule availability for this date: " + attendanceDate);
        if(Utility.getDateFromString(attendanceDate) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        return attendanceDao.isAvailableEmployeeOrTempAttendanceSheet(attendanceDate);
    }

    @Override
    public boolean getAttendanceStatus(String attendanceDate, String mode) throws CustomException {

        if(Utility.getDateFromString(attendanceDate) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }

        AttendanceStatus attendanceStatus = null;
        if(mode.equals("1")){
            attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.TEMPORARY_LEAVE_TYPE);

        } else if(mode.equals("2")){
            attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.CONFIRM_LEAVE_TYPE);
        }

        if(attendanceStatus == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return attendanceStatus.isStatus();
    }


    @Override
    public boolean saveTempAttendance(InputStream inputStream, String userID) throws CustomException {

        if (inputStream == null) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
            throw new CustomException(errorMessage);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String csvSplitBy = ",";

        Map<String, String> inMap = new HashMap<>();
        Map<String, String> outMap = new HashMap<>();
        try {
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(csvSplitBy);

                if(lineSplit.length > 0) {
                    i++;

                    if(i > 2){

                        if(lineSplit[lineSplit.length - 1].equals(Constants.SUCCESS)) {

                            if(lineSplit[Constants.CSV_TYPE_COLUMN].equals(Constants.INT_TIME)){
                                if(inMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]) != null){
                                    Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    Timestamp prevDate = Utility.getTimeStampFromString(inMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]));

                                    if(prevDate.after(nextDate)){
                                        inMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    inMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                            lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                }

                            } else if(lineSplit[Constants.CSV_TYPE_COLUMN].equals(Constants.OUT_TIME)){
                                if(outMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]) != null){
                                    Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    Timestamp prevDate = Utility.getTimeStampFromString(outMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]));

                                    if(prevDate.before(nextDate)){
                                        outMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    outMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                            lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                }
                            }
                        }
                    }
                }
            }

            List<Employee> employeeList = employeeDao.getAllEmployees();
            if(!Utility.isNullOrEmpty(employeeList)){
                logger.info("Total employee list: " + employeeList.size());
                Employee currentEmployee = employeeDao.getEmployeeByUserID(userID);

                new Thread(() -> {
                    String attendanceDate = "";
                    boolean flag = true;
                    AttendanceStatus attendanceStatus;

                    for(Employee employee : employeeList){
                        String employeeNo = employee.getEmployeeNo();
                        logger.info("Employee no: " + employeeNo);

                        if(inMap.containsKey(employeeNo) && outMap.containsKey(employeeNo)){
                            TemporaryAttendance tempAttendance = new TemporaryAttendance();
                            tempAttendance.setEmployee(employee);
                            tempAttendance.setAbsent(false);
                            tempAttendance.setAttendanceDate(Utility.getDateFromString(Utility.
                                    getDateFromStringForAttendance(inMap.get(employeeNo))));
                            tempAttendance.setCheckInTime(Utility.getTime(inMap.get(employeeNo)));
                            tempAttendance.setCheckOutTime(outMap.get(employeeNo));
                            tempAttendance.setTotalHour(Utility.getTimeCalculation(inMap.get(employeeNo), outMap.get(employeeNo)));
                            tempAttendance.setCreatedDate(Utility.today());
                            tempAttendance.setLastModifiedDate(Utility.today());
                            tempAttendance.setCreatedBy(currentEmployee.getEmployeeId());
                            tempAttendance.setModifiedBy(currentEmployee.getEmployeeId());
                            tempAttendance.setVersion(1);

                            attendanceDao.saveTempAttendance(tempAttendance);

                            //Find attendance date
                            if(flag){
                                attendanceDate += tempAttendance.getAttendanceDate();

                                attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.TEMPORARY_LEAVE_TYPE);
                                if(attendanceStatus == null) {
                                    attendanceStatus = new AttendanceStatus();
                                    attendanceStatus.setAttendanceDate(Utility.getDateFromString(attendanceDate));
                                    attendanceStatus.setStatus(false);
                                    attendanceStatus.setType(Constants.TEMPORARY_LEAVE_TYPE);
                                    attendanceDao.saveAttendanceStatus(attendanceStatus);
                                }

                                flag = false;
                            }
                        } else {
                            TemporaryAttendance tempAttendance = new TemporaryAttendance();
                            tempAttendance.setEmployee(employee);
                            tempAttendance.setAbsent(true);
                            tempAttendance.setAttendanceDate(Utility.getDateFromString(Utility.
                                    getDateFromStringForAttendance(inMap.get(employeeNo))));
                            tempAttendance.setCreatedDate(Utility.today());
                            tempAttendance.setLastModifiedDate(Utility.today());
                            tempAttendance.setCreatedBy(currentEmployee.getEmployeeId());
                            tempAttendance.setModifiedBy(currentEmployee.getEmployeeId());
                            tempAttendance.setVersion(1);

                            attendanceDao.saveTempAttendance(tempAttendance);
                        }
                    }

                    attendanceStatus = attendanceDao.getAttendanceStatus(attendanceDate, Constants.TEMPORARY_LEAVE_TYPE);
                    attendanceStatus.setStatus(true);
                    attendanceDao.updateAttendanceStatus(attendanceStatus);

                }).start();
            }
            logger.info("Save temporary attendance success");
            return true;

        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateTempAttendance(AttendanceDto attendanceDto, String userID, String tempAttendanceID) throws CustomException {
        validateForUpdate(attendanceDto, tempAttendanceID);

        TemporaryAttendance existTempAttendance = attendanceDao.getTemporaryAttendance(tempAttendanceID);
        existTempAttendance.setCheckInTime(attendanceDto.getCheckInTime());
        existTempAttendance.setCheckOutTime(attendanceDto.getCheckOutTime());
        existTempAttendance.setTotalHour(Utility.getTimeCalculation(attendanceDto.getCheckInTime(),
                attendanceDto.getCheckOutTime()));
        existTempAttendance.setAbsent(attendanceDto.isAbsent());
        existTempAttendance.setLastModifiedDate(Utility.today());
        existTempAttendance.setModifiedBy(employeeDao.getEmployeeByUserID(userID).getEmployeeId());
        existTempAttendance.setVersion(attendanceDto.getVersion());

        boolean res = attendanceDao.updateTempAttendance(existTempAttendance);
        if(!res) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Update temporary attendance success");
    }

    private void validateForUpdate(AttendanceDto attendanceDto, String tempAttendanceID) throws CustomException {
        if(attendanceDao.getTemporaryAttendance(tempAttendanceID) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(attendanceDto.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTempAttendance() throws CustomException {
        Date currentDate = Utility.getDateFormatFromDate(Utility.today());
        Date daysAgo = Utility.getDateFormatFromDate(new DateTime(currentDate).minusDays(Constants.DAYS_AGO_COUNT).toDate());

        boolean res = attendanceDao.deleteTempAttendance(daysAgo);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete temporary attendance success");
    }

    @Override
    public List<TemporaryAttendance> getAllTempAttendances(String attendanceDate) throws CustomException {
        List<TemporaryAttendance> temporaryAttendances = attendanceDao.getAllTempAttendances(attendanceDate);
        if(temporaryAttendances == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Temporary attendance list size: " + temporaryAttendances.size());
        return temporaryAttendances;
    }

    @Override
    public TemporaryAttendance getTemporaryAttendance(String tempAttendanceId) throws CustomException {
        TemporaryAttendance temporaryAttendance = attendanceDao.getTemporaryAttendance(tempAttendanceId);
        if(temporaryAttendance == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return temporaryAttendance;
    }
}

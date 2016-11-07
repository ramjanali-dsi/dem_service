package com.dsi.dem.dao;

import com.dsi.dem.model.AttendanceStatus;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 10/18/16.
 */
public interface AttendanceDao {

    boolean saveAttendance(EmployeeAttendance attendance);
    boolean updateAttendance(EmployeeAttendance attendance);
    boolean deleteAttendance(String attendanceId, String employeeId);
    EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId);
    List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId);
    List<EmployeeAttendance> getAllAttendancesByDate(String attendanceDate);
    List<EmployeeAttendance> searchOrReadAttendances(String userId, String employeeNo, String isAbsent, String firstName,
                                                       String lastName, String nickName, String attendanceDate, String teamName,
                                                       String projectName, String from, String range);

    boolean isAvailableEmployeeOrTempAttendanceSheet(String attendanceDate);

    boolean saveAttendanceStatus(AttendanceStatus attendanceStatus);
    boolean updateAttendanceStatus(AttendanceStatus attendanceStatus);
    AttendanceStatus getAttendanceStatus(String attendanceDate, String type);

    boolean saveTempAttendance(TemporaryAttendance tempAttendance);
    boolean updateTempAttendance(TemporaryAttendance tempAttendance);
    boolean deleteTempAttendance(Date attendanceDate);
    List<TemporaryAttendance> getAllTempAttendances(String attendanceDate);
    TemporaryAttendance getTemporaryAttendance(String tempAttendanceId);
}

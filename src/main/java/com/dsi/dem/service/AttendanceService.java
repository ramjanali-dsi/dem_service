package com.dsi.dem.service;

import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.AttendanceStatus;
import com.dsi.dem.model.DraftAttendance;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sabbir on 10/19/16.
 */
public interface AttendanceService {

    void saveAttendance(List<AttendanceDto> attendanceDtoList, String userID, String attendanceDate) throws CustomException;
    void updateAttendance(EmployeeAttendance attendance) throws CustomException;
    void deleteAttendance(String attendanceDate) throws CustomException;
    EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId) throws CustomException;
    List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId) throws CustomException;
    List<EmployeeAttendance> getAllAttendancesByDate(String attendanceDate) throws CustomException;
    List<EmployeeAttendance> searchOrReadAttendances(String userId, String employeeNo, String isAbsent, String firstName,
                                                     String lastName, String nickName, String attendanceDate, String teamName,
                                                     String projectName, String from, String range) throws CustomException;

    boolean isAvailableEmployeeOrTempAttendanceSheet(String attendanceDate) throws CustomException;
    boolean getAttendanceStatus(String attendanceDate, String mode) throws CustomException;

    List<DraftAttendance> saveTempAttendance(InputStream inputStream, String userID) throws CustomException;
    void updateTempAttendance(List<AttendanceDto> attendanceDtoList, String userID, String attendanceDate) throws CustomException;
    void deleteTempAttendance() throws CustomException;
    List<TemporaryAttendance> getAllTempAttendances(String attendanceDate) throws CustomException;
    TemporaryAttendance getTemporaryAttendance(String tempAttendanceId) throws CustomException;

    List<DraftAttendance> getDraftAttendanceFileDetails(String from, String range) throws CustomException;
}
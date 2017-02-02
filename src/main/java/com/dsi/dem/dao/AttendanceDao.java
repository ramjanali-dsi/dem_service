package com.dsi.dem.dao;

import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.AttendanceStatus;
import com.dsi.dem.model.DraftAttendance;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 10/18/16.
 */
public interface AttendanceDao {

    void setSession(Session session);
    void saveAttendance(EmployeeAttendance attendance) throws CustomException;
    void updateAttendance(EmployeeAttendance attendance) throws CustomException;
    void deleteAttendance(String attendanceId, String employeeId) throws CustomException;
    EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId);
    int getAttendanceCountByIdAndDate(String employeeId, Date startDate, Date endDate);
    List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId);
    List<EmployeeAttendance> getAllAttendancesByDate(Date attendanceDate);
    List<EmployeeAttendance> searchOrReadAttendances(String employeeNo, String isAbsent, String firstName,
                                                     String lastName, String nickName, String attendanceDate, String teamName,
                                                     String projectName, ContextDto contextDto, String from, String range);

    boolean isAvailableEmployeeOrTempAttendanceSheet(Date attendanceDate);

    void saveAttendanceStatus(AttendanceStatus attendanceStatus) throws CustomException;
    void updateAttendanceStatus(AttendanceStatus attendanceStatus) throws CustomException;
    AttendanceStatus getAttendanceStatus(Date attendanceDate, String type);

    void saveTempAttendance(TemporaryAttendance tempAttendance) throws CustomException;
    void updateTempAttendance(TemporaryAttendance tempAttendance) throws CustomException;
    void deleteTempAttendance(Date attendanceDate) throws CustomException;
    void deleteTemporaryAttendanceFromCron(Date createdDate) throws CustomException;
    List<TemporaryAttendance> getAllTempAttendances(Date attendanceDate);
    TemporaryAttendance getTemporaryAttendance(String tempAttendanceId);

    void saveAttendanceDraft(DraftAttendance draftAttendance) throws CustomException;
    void deleteAttendanceDraft(Date attendanceDate) throws CustomException;
    void deleteAttendanceDraftFromCron(Date createdDate) throws CustomException;
    DraftAttendance getDraftAttendanceFileByDate(Date attendanceDate) throws CustomException;
    List<DraftAttendance> getAllDraftAttendanceFileDetails(String from, String range);
    List<DraftAttendance> getAllDraftAttendanceFileByCreatedDate(Date createdDate);
}

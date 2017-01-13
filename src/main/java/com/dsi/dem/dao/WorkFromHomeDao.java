package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.WorkFormHomeStatus;
import com.dsi.dem.model.WorkFromHome;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 1/11/17.
 */
public interface WorkFromHomeDao {

    void setSession(Session session);
    void saveWorkFromHomeRequest(WorkFromHome workFromHome) throws CustomException;
    void updateWorkFromHomeRequest(WorkFromHome workFromHome) throws CustomException;
    WorkFromHome getWorkFromHomeById(String wfhId);
    WorkFromHome getWFHByIdAndUserId(String wfhId, String userId);
    List<WorkFromHome> searchOrReadWorkFromHomeRequest(String userId, String date, String reason,
                                                       String statusId, String from, String range);

    int countAppliedWFH(String employeeId);
    int countApprovedWFHForMonth(String employeeId, Date startDate, Date endDate);
    boolean alreadyApprovedWFHExist(String employeeId, Date applyDate);
    WorkFormHomeStatus getWFHStatusById(String statusId);
    WorkFormHomeStatus getWFHStatusByName(String name);

    List<WorkFromHome> searchOrReadEmployeesWFHRequests(String date, String reason, String statusId, String employeeNo,
                                                               String firstName, String lastName, String nickName, String wfhId,
                                                               String from, String range);
}

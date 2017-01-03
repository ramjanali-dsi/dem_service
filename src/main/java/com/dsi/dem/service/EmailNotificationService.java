package com.dsi.dem.service;


import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;

/**
 * Created by sabbir on 1/3/17.
 */
public interface EmailNotificationService {

    void notificationForEmployeeCreate(Employee employee, String tenantName,
                                       String password, String email) throws CustomException;
}

package com.dsi.dem.cronjob;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.impl.AttendanceServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.log4j.Logger;

/**
 * Created by sabbir on 11/3/16.
 */
public class AttendanceCron {

    private static final Logger logger = Logger.getLogger(AttendanceCron.class);

    private static final AttendanceService attendanceService = new AttendanceServiceImpl();

    public static void main(String[] args) throws CustomException {

        logger.info("Delete temporary attendances, those are more than 5 days from creation date");

        try {
            attendanceService.deleteTempAttendance();
            logger.info("Delete temporary attendances success.");

        } catch (Exception e){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
    }
}

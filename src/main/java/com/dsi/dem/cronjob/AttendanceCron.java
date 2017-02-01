package com.dsi.dem.cronjob;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.DraftAttendance;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.impl.AttendanceServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 11/3/16.
 */
public class AttendanceCron {

    private static final Logger logger = Logger.getLogger(AttendanceCron.class);

    private static final AttendanceService attendanceService = new AttendanceServiceImpl();

    public static void main(String[] args) {

        logger.info("Delete temporary attendances, those are more than 5 days from creation date");

        try {
            attendanceService.deleteTempAttendance();
            logger.info("Delete temporary attendances success.");

            Date currentDate = Utility.getDateFormatFromDate(Utility.today());
            Date daysAgo = Utility.getDateFormatFromDate(new DateTime(currentDate).minusDays(Constants.DAYS_AGO_COUNT - 1).toDate());
            logger.info("Notify expired draft data for this date: " + daysAgo);

            attendanceService.getDraftAttendanceFileDetailsByDate(daysAgo);
            logger.info("Notify expired draft data done.");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

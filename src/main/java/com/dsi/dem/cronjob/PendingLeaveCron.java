package com.dsi.dem.cronjob;

import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by sabbir on 1/24/17.
 */
public class PendingLeaveCron {

    private static final Logger logger = Logger.getLogger(PendingLeaveCron.class);

    private static final LeaveService leaveService = new LeaveServiceImpl();

    public static void main(String[] args) {
        logger.info("Auto notify pending pre leave request application:: Start");
        Date daysAfter = Utility.getDateFormatFromDate(new DateTime(Utility.today()).plusDays(1).toDate());

        leaveService.getPendingLeaveApplication(daysAfter);
        logger.info("Auto notify pending pre leave request application:: End");

        logger.info("Auto notify approved leave request application:: Start");
        leaveService.getApproveLeaveApplication(daysAfter);
        logger.info("Auto notify approved leave request application:: End");
    }
}

package com.dsi.dem.cronjob;

import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by sabbir on 1/24/17.
 */
public class PendingLeaveCron {

    private static final Logger logger = Logger.getLogger(PendingLeaveCron.class);

    private static final LeaveService leaveService = new LeaveServiceImpl();

    public static void main(String[] args) {
        logger.info("Auto notify pending pre leave request application.");
        Date currentDate = Utility.today();

        //TODO lot things.
    }
}

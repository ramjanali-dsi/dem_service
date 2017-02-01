package com.dsi.dem.cronjob;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.HolidayService;
import com.dsi.dem.service.impl.HolidayServiceImpl;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sabbir on 1/24/17.
 */
public class HolidayWeeklyCron {

    private static final Logger logger = Logger.getLogger(HolidayWeeklyCron.class);

    private static final HolidayService holidayService = new HolidayServiceImpl();

    public static void main(String[] args) {

        logger.info("Auto notify next weeks holidays.");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 7);

        logger.info("Auto notify between date: " + currentDate + " - " + calendar.getTime() + " holidays.");
        holidayService.getAllHolidaysBetweenDate(currentDate, calendar.getTime());
        logger.info("Auto notify weeks holidays done.");
    }
}

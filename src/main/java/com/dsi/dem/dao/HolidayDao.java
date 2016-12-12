package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Holiday;
import com.dsi.dem.model.HolidayType;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 11/30/16.
 */
public interface HolidayDao {

    void setSession(Session session);
    void saveHoliday(Holiday holiday) throws CustomException;
    void updateHoliday(Holiday holiday) throws CustomException;
    void deleteHoliday(String holidayId) throws CustomException;
    boolean checkHolidayFromRangeAndYear(Date startDate, Date endDate, int year);
    Holiday getHolidayById(String holidayId);
    Holiday getHolidayByNameAndYear(String holidayName, int year);
    List<Holiday> getHolidayByYear(int year);
    List<Holiday> searchOrReadHolidays(String holidayName, String year, String holidayId,
                                       String from, String range);

    HolidayType getHolidayTypeById(String typeId);
}

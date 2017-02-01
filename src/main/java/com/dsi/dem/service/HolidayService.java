package com.dsi.dem.service;

import com.dsi.dem.dto.HolidayDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Holiday;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 11/30/16.
 */
public interface HolidayService {

    HolidayDto saveHoliday(HolidayDto holidayDto) throws CustomException;
    HolidayDto updateHoliday(HolidayDto holidayDto, String holidayId) throws CustomException;
    void deleteHoliday(String holidayId) throws CustomException;
    void getAllHolidaysBetweenDate(Date startDate, Date endDate);
    void getHolidaysByDate(Date date);
    List<HolidayDto> searchOrReadAllHolidays(String holidayName, String year, String holidayId,
                                             String from, String range) throws CustomException;

    boolean copyHoliday(List<HolidayDto> holidayDtoList) throws CustomException;
    boolean publishHoliday(List<HolidayDto> holidayDtoList, String tenantName) throws CustomException;
}

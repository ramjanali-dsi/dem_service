package com.dsi.dem.service.impl;

import com.dsi.dem.dao.HolidayDao;
import com.dsi.dem.dao.impl.HolidayDaoImpl;
import com.dsi.dem.dto.HolidayDto;
import com.dsi.dem.dto.transformer.HolidayDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Holiday;
import com.dsi.dem.service.HolidayService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 11/30/16.
 */
public class HolidayServiceImpl extends CommonService implements HolidayService {

    private static final Logger logger = Logger.getLogger(HolidayServiceImpl.class);

    private static final HolidayDtoTransformer TRANSFORMER = new HolidayDtoTransformer();
    private static final HolidayDao holidayDao = new HolidayDaoImpl();

    @Override
    public HolidayDto saveHoliday(HolidayDto holidayDto) throws CustomException {
        logger.info("Create holiday:: Start");
        logger.info("Convert Holiday Dto to Holiday Object");
        Holiday holiday = TRANSFORMER.getHoliday(holidayDto);

        validateInputForCreation(holiday);

        Session session = getSession();
        holidayDao.setSession(session);

        if(holidayDao.getHolidayByNameAndYear(holiday.getHolidayName(),
                Utility.getYearFromDate(holiday.getStartDate())) != null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        holiday.setYear(Utility.getYearFromDate(holiday.getStartDate()));
        holiday.setCreatedDate(Utility.today());
        holiday.setLastModifiedDate(Utility.today());
        holiday.setActive(true);
        holiday.setPublish(0);
        holiday.setVersion(1);
        holidayDao.saveHoliday(holiday);

        logger.info("Holiday save successfully.");
        logger.info("Create holiday:: End");

        close(session);
        return TRANSFORMER.getHolidayDto(holiday);
    }

    private void validateInputForCreation(Holiday holiday) throws CustomException {
        if(Utility.isNullOrEmpty(holiday.getHolidayName())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        if(holiday.getStartDate() == null || holiday.getEndDate() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public HolidayDto updateHoliday(HolidayDto holidayDto, String holidayId) throws CustomException {
        logger.info("Update holiday:: Start");
        logger.info("Convert Holiday Dto to Holiday Object");
        Holiday holiday = TRANSFORMER.getHoliday(holidayDto);

        validateInputForUpdate(holiday);

        Session session = getSession();
        holidayDao.setSession(session);

        Holiday existHoliday = holidayDao.getHolidayById(holidayId);
        if(existHoliday == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        existHoliday.setHolidayName(holiday.getHolidayName());
        existHoliday.setDescription(holiday.getDescription());
        existHoliday.setStartDate(holiday.getStartDate());
        existHoliday.setEndDate(holiday.getEndDate());
        existHoliday.setHolidayType(holidayDao.getHolidayTypeById(holidayDto.getHolidayTypeId()));
        existHoliday.setYear(Utility.getYearFromDate(holiday.getStartDate()));
        existHoliday.setLastModifiedDate(Utility.today());
        existHoliday.setVersion(holiday.getVersion());
        holidayDao.updateHoliday(existHoliday);

        logger.info("Holiday update successfully.");
        logger.info("Update holiday:: End");

        close(session);
        return TRANSFORMER.getHolidayDto(existHoliday);
    }

    private void validateInputForUpdate(Holiday holiday) throws CustomException {
        if(holiday.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteHoliday(String holidayId) throws CustomException {
        logger.info("Holiday delete:: Start");
        Session session = getSession();
        holidayDao.setSession(session);

        holidayDao.deleteHoliday(holidayId);

        logger.info("Holiday delete:: End");
        close(session);
    }

    @Override
    public List<HolidayDto> searchOrReadAllHolidays(String holidayName, String year, String holidayId,
                                                    String from, String range) throws CustomException {
        logger.info("Search or read all holidays");

        Session session = getSession();
        holidayDao.setSession(session);

        List<Holiday> holidayList = holidayDao.searchOrReadHolidays(holidayName, year, holidayId, from, range);
        if(holidayList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Holiday list size:: " + holidayList.size());

        close(session);
        return TRANSFORMER.getHolidaysDto(holidayList);
    }

    @Override
    public boolean copyHoliday(List<HolidayDto> holidayDtoList) throws CustomException {
        logger.info("Copy holiday:: Start");

        if(!Utility.isNullOrEmpty(holidayDtoList)){
            Session session = getSession();
            holidayDao.setSession(session);

            for(HolidayDto holidayDto : holidayDtoList){
                Holiday holiday = holidayDao.getHolidayById(holidayDto.getHolidayId());
                if(holiday == null){
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                            Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
                    throw new CustomException(errorMessage);
                }
                copyHolidayFromExist(holiday, session);
            }
            close(session);
        }

        logger.info("Copy holiday:: End");
        return true;
    }

    @Override
    public boolean publishHoliday(List<HolidayDto> holidayDtoList) throws CustomException {
        logger.info("Holiday publish:: Start");

        if(!Utility.isNullOrEmpty(holidayDtoList)){
            Session session = getSession();
            holidayDao.setSession(session);

            for(HolidayDto holidayDto : holidayDtoList){
                Holiday holiday = holidayDao.getHolidayById(holidayDto.getHolidayId());
                if(holiday == null){
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                            Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
                    throw new CustomException(errorMessage);
                }

                holiday.setPublish(holiday.getPublish() + 1);
                holidayDao.updateHoliday(holiday);
                //TODO sent email;
            }
            close(session);
        }

        logger.info("Holiday publish:: End");
        return true;
    }

    private void copyHolidayFromExist(Holiday holiday, Session session) throws CustomException {

        logger.info("Holiday year: " + holiday.getYear());
        Holiday nextYearHoliday = holidayDao.getHolidayByNameAndYear(holiday.getHolidayName(), holiday.getYear() + 1);
        if(nextYearHoliday != null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
        logger.info("Next year holiday not found.");

        Holiday newHoliday = new Holiday();
        newHoliday.setHolidayName(holiday.getHolidayName());
        newHoliday.setDescription(holiday.getDescription());
        newHoliday.setHolidayType(holiday.getHolidayType());
        newHoliday.setStartDate(Utility.getDateFromAddYear(holiday.getStartDate()));
        newHoliday.setEndDate(Utility.getDateFromAddYear(holiday.getEndDate()));
        newHoliday.setYear(holiday.getYear() + 1);
        newHoliday.setCreatedDate(Utility.today());
        newHoliday.setLastModifiedDate(Utility.today());
        newHoliday.setActive(holiday.isActive());
        newHoliday.setPublish(0);
        newHoliday.setVersion(1);
        holidayDao.saveHoliday(newHoliday);
        logger.info("Next year holiday copy success.");
    }
}
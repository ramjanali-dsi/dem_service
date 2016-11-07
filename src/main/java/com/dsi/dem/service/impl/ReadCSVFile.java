package com.dsi.dem.service.impl;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sabbir on 7/14/16.
 */
public class ReadCSVFile {

    public void readCSV() throws CustomException {
        BufferedReader csvBuffer = null;
        try {
            String csvLine;
            csvBuffer = new BufferedReader(new FileReader("/..../CSV-to-ArrayList.txt"));

            while ((csvLine = csvBuffer.readLine()) != null) {
                System.out.println("Raw CSV data: " + csvLine);
                System.out.println("Converted ArrayList data: " + csvToArrayList(csvLine) + "\n");
            }

        } catch (IOException e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0010,
                    Constants.DEM_SERVICE_0010_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
            throw new CustomException(errorMessage);

        } finally {
            try {
                if (csvBuffer != null) csvBuffer.close();

            } catch (IOException ie) {
                //ErrorContext errorContext = new ErrorContext(null, null, ie.getMessage());
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0010,
                        Constants.DEM_SERVICE_0010_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_005);
                throw new CustomException(errorMessage);
            }
        }
    }

    private ArrayList<String> csvToArrayList(String rawCSV) {
        ArrayList<String> csvResult = new ArrayList<>();

        if (rawCSV != null) {
            String[] splitData = rawCSV.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    csvResult.add(splitData[i].trim());
                }
            }
        }
        return csvResult;
    }
}

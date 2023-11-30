package com.bozan;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;

public class LoadData {

    public void processToDb(String csvFileName){

        LinkedHashMap cityMap = new LinkedHashMap();
        try (Reader in = new FileReader(csvFileName)){
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {
                String city = record.get("city");
                String lat = record.get("lat");
                String lng = record.get("lng");

                cityMap.put(lat,lat + "," + lng);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

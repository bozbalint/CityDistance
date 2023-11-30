package hu.bozbalint.util;

import hu.bozbalint.model.Coordinate;
import hu.bozbalint.model.MapData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class Csv {

    public MapData readCsv(String csvFileName) {

        List<Coordinate> cityList = new LinkedList<>();

        // rectangle of the coordinates
        double x1 = Double.POSITIVE_INFINITY;
        double y1 = Double.POSITIVE_INFINITY;
        double x2 = Double.NEGATIVE_INFINITY;
        double y2 = Double.NEGATIVE_INFINITY;

        try (Reader in = new FileReader(csvFileName)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withSkipHeaderRecord(false).parse(in);
            for (CSVRecord record : records) {
                String city = record.get("city") + ", " + record.get("state_name");

                try {
                    double lat = Double.parseDouble(record.get("lat"));
                    double lng = Double.parseDouble(record.get("lng"));

                    cityList.add(new Coordinate(lat, lng, city));
                    x1 = Math.min(x1, lng);
                    x2 = Math.max(x2, lng);
                    y1 = Math.min(y1, lat);
                    y2 = Math.max(y2, lat);

                } catch (NumberFormatException e) {
                    System.err.println(String.format("Invalid coordinates: %s, %s - %s", city, record.get("lat"), record.get("lng")));
                }
            }

            return new MapData(y1, x1, y2, x2, cityList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

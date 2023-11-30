package com.bozan;

import com.bozan.model.Coordinate;
import com.bozan.model.MapData;
import com.bozan.util.Calculate;
import com.bozan.util.Csv;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        System.out.println("Start Loading ...");

        Csv csvUtil = new Csv();
        Calculate calculateUtil = new Calculate();

        MapData mapData = csvUtil.readCsv("uscities.csv");

        Map<String, List<Coordinate>> cityClusterMap = calculateUtil.addCitiesToCluster(mapData);

        System.out.println("End Loading ...");

        // iterate and find the closest
        mapData.cityList.forEach(coordinate -> {
            System.out.print("Get closest: " + coordinate.cityName);

            String key = Calculate.getClusterPosition(coordinate, mapData);
            Coordinate closestCity = Calculate.getClosestCity(cityClusterMap, coordinate, Collections.singleton(key), new HashSet<>());
            if (closestCity != null) {
                System.out.println("     -> " + closestCity.cityName);
            } else {
                System.err.println("No closest found: " + coordinate.cityName);
            }
        });
    }
}

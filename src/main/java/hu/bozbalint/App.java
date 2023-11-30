package hu.bozbalint;

import hu.bozbalint.model.Coordinate;
import hu.bozbalint.model.MapData;
import hu.bozbalint.util.Calculate;
import hu.bozbalint.util.Csv;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            List<Coordinate> closestCities = Calculate.getClosestCity(cityClusterMap, coordinate, Collections.singleton(key), new HashSet<>());
            if (closestCities != null) {
                System.out.println("     -> " + closestCities.stream().map(c -> c.cityName).collect( Collectors.joining( "; " )));
            } else {
                System.err.println("No closest found: " + coordinate.cityName);
            }
        });
    }
}

package com.bozan.util;

import com.bozan.model.Coordinate;
import com.bozan.model.MapData;

import java.util.*;

public class Calculate {
    private static final double EARTH_RADIUS = 6371; // Approx Earth radius in KM
    public static final int NUM_OF_CITY_IN_RESULT = 2;

    public static double calculateDistance(Coordinate c1, Coordinate c2) {

        double dLat = Math.toRadians((c2.latitude - c1.latitude));
        double dLong = Math.toRadians((c2.longitude - c1.longitude));
        double a = haversine(dLat) + Math.cos(Math.toRadians(c1.latitude)) * Math.cos(Math.toRadians(c2.latitude)) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.abs(EARTH_RADIUS * c);
    }

    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static double getClusterSizeLng(int numOfCity, double mostWest, double mostEast) {
        return Math.abs(mostWest - mostEast) / Math.sqrt(numOfCity);
    }

    public static double getClusterSizeLat(int numOfCity, double mostNorth, double mostSouth) {
        return Math.abs(mostNorth - mostSouth) / Math.sqrt(numOfCity);
    }

    /**
     * Split the whole map area to small rectangles (clusters) and add the cities in the right cluster. The closest search will happen on the cluster only
     *
     * @param mapData map value data object
     * @return the map of the city clusters
     */
    public Map<String, List<Coordinate>> addCitiesToCluster(MapData mapData) {
        Map<String, List<Coordinate>> clusterMap = new HashMap<>();

        mapData.cityList.forEach(coordinate -> {
            String key = getClusterPosition(coordinate, mapData);
            if (!clusterMap.containsKey(key)) {
                clusterMap.put(key, new LinkedList<>());
            }
            clusterMap.get(key).add(coordinate);
        });

        return clusterMap;
    }

    /**
     * key of the city cluster map is the X,Y position
     *
     * @param coordinate search to X,Y key position
     * @param mapData    map value data object
     * @return X + ":" + Y string
     */
    public static String getClusterPosition(Coordinate coordinate, MapData mapData) {
        int keyX = (int) ((coordinate.latitude - mapData.mostNorthLat) / mapData.clusterSizeLat);
        int keyY = (int) ((coordinate.longitude - mapData.mostWestLng) / mapData.clusterSizeLng);

        return keyX + ":" + keyY;
    }

    public static List<Coordinate> getClosestCity(Map<String, List<Coordinate>> clusterMap, Coordinate fromCity, Set<String> clusterKeystoCheck, Set<String> clusterKeysWasCheck) {

        List<Coordinate> closestCityList = new LinkedList<>();
        Set<String> keySetToCheck = new LinkedHashSet<>();

        List<Coordinate> citiesInCluster = new LinkedList<>();

        // load the cities in scope of this check
        clusterKeystoCheck.forEach(key -> {
            if (clusterMap.containsKey(key)) {
                citiesInCluster.addAll(clusterMap.get(key));
            }
            clusterKeysWasCheck.add(key);
        });

        // have city in the same cluster
        if (citiesInCluster.size() > 1 || citiesInCluster.size() == 1 && !citiesInCluster.contains(fromCity)) {
            closestCityList.addAll(Coordinate.shortByDist(fromCity, citiesInCluster));
        } else {
            // find cities in the surrounding clusters
            clusterKeystoCheck.forEach(key -> {
                String[] keys = key.split(":");

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        keySetToCheck.add((Integer.parseInt(keys[0]) + i) + ":" + (Integer.parseInt(keys[1]) + j));
                    }
                }
                keySetToCheck.removeAll(clusterKeysWasCheck);
            });
        }

        if (!closestCityList.isEmpty()) {
            // happy to found closest
            return Coordinate.shortByDist(fromCity, closestCityList).subList(0, Math.min(NUM_OF_CITY_IN_RESULT, closestCityList.size()));
        } else {
            if (!keySetToCheck.isEmpty()) {
                // recursive search on the surrounding clusters
                return getClosestCity(clusterMap, fromCity, keySetToCheck, clusterKeysWasCheck);
            }
        }

        // if not data at all
        return null;
    }
}

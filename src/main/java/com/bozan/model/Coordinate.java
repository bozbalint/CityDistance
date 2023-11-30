package com.bozan.model;

import com.bozan.util.Calculate;

import java.util.*;

public class Coordinate {

    final public double latitude;
    final public double longitude;
    final public String cityName;

    public Coordinate(double latitude, double longitude, String cityName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
    }

    public static LinkedList<Coordinate> shortByDist(Coordinate from, Collection<Coordinate> coordinates) {

        LinkedList<Coordinate> coordinateList = new LinkedList<>(coordinates);
        coordinateList.remove(from);
        Collections.sort(coordinateList, new Comparator<>() {
            public int compare(Coordinate c1, Coordinate c2) {
                return Double.compare(Calculate.calculateDistance(from, c1), Calculate.calculateDistance(from, c2));
            }
        });

        return coordinateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && cityName.equals(that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, cityName);
    }
}

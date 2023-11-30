package com.bozan.model;

import com.bozan.util.Calculate;

import java.util.List;

public class MapData {
    public static final String NORTH_WEST_CORNER = "##TopLeft##";
    public static final String SOUTH_EAST_CORNER = "##BottomRight##";
    public final double clusterSizeLat;
    public final double clusterSizeLng;
    public final double mostNorthLat;
    public final double mostWestLng;
    public final double mostSouthLat;
    public final double mostEastLng;

    public final List<Coordinate> cityList;

    public MapData(double mostNorthLat, double mostWestLng, double mostSouthLat, double mostEastLng, List<Coordinate> cityList) {
        this.clusterSizeLat = Calculate.getClusterSizeLat(cityList.size(), mostNorthLat, mostSouthLat);
        this.clusterSizeLng = Calculate.getClusterSizeLng(cityList.size(), mostWestLng, mostEastLng);
        this.mostNorthLat = mostNorthLat;
        this.mostWestLng = mostWestLng;
        this.mostSouthLat = mostSouthLat;
        this.mostEastLng = mostEastLng;
        this.cityList = cityList;
    }
}

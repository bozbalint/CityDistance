package com.bozan.model;

public class Constans {
    public static final String NORTH_WEST_CORNER = "##TopLeft##";
    public static final String SOUTH_EAST_CORNER = "##BottomRight##";
    public final double clusterSizeLat;
    public final double clusterSizeLng;
    public final double mostNorthLat;
    public final double mostWestLng;
    public final double mostSouthLat;
    public final double mostEastLng;

    public Constans(double clusterSizeLat, double clusterSizeLng, double mostNorthLat, double mostWestLng, double mostSouthLat, double mostEastLng) {
        this.clusterSizeLat = clusterSizeLat;
        this.clusterSizeLng = clusterSizeLng;
        this.mostNorthLat = mostNorthLat;
        this.mostWestLng = mostWestLng;
        this.mostSouthLat = mostSouthLat;
        this.mostEastLng = mostEastLng;
    }
}

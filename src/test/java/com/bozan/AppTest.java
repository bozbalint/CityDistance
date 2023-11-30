package com.bozan;

import com.bozan.model.Coordinate;
import com.bozan.util.Calculate;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testCalculateDistance() {

        Coordinate c1 = new Coordinate(40.6943, -73.9249, "New York");
        Coordinate c2 = new Coordinate(34.1141, -118.4068, "Los Angeles");

        double dist = Calculate.calculateDistance(c1, c2);

        System.out.println("distance (km) : " + dist);
        assertTrue(dist - 3962.36 < 5);
    }

    public void testShortByDistance() {

        List<Coordinate> coordinateList = new LinkedList<>();
        coordinateList.add(new Coordinate(40.6943, -73.9249, "New York"));
        coordinateList.add(new Coordinate(37.7558, -122.4449, "San Francisco"));
        coordinateList.add(new Coordinate(34.1141, -118.4068, "Los Angeles"));
        coordinateList.add(new Coordinate(41.8375, -87.6866, "Chicago"));

        LinkedList<Coordinate> orderedList = Coordinate.shortByDist(new Coordinate(37.7558, -122.4449, "San Francisco"), coordinateList);

        assertEquals(orderedList.size(), 3);
        assertEquals(orderedList.get(0).cityName, "Los Angeles");
        assertEquals(orderedList.get(1).cityName, "Chicago");
        assertEquals(orderedList.get(2).cityName, "New York");
    }

}

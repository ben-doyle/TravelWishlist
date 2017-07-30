package com.codechallenge.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SimpleLocationDaoTest extends TestCase {

    /**
     * Simple add location test.
     */
    public void testAdd() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        SimpleLocationDao sld = new SimpleLocationDao();
        sld.add(location);
    }

    /**
     * Test the find all method which returns all locations entered.
     */
    public void testFindAll() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Location location2 = new Location("Logan, Queensland", "Benjamin", "The Not So Great " +
                "City");
        SimpleLocationDao sld = new SimpleLocationDao();
        ArrayList<Location> result = new ArrayList<>();

        sld.add(location);
        result.add(location);
        Assert.assertEquals(result, sld.findAll());

        sld.add(location2);
        result.add(location2);
        Assert.assertEquals(result, sld.findAll());
    }

    /**
     * Test the find by slug method.
     */
    public void testFindBySlug() {

        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        SimpleLocationDao sld = new SimpleLocationDao();
        sld.add(location);

        Location location2 = sld.findBySlug("berlin-germany");

        Assert.assertEquals(location, location2);
    }

    /**
     * Adds two locations, and uses find user locations to return locations
     * by that user.
     */
    public void testFindUserLocations() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Location location2 = new Location("Logan, Queensland", "Emily", "The Not So Great " +
                "City");
        SimpleLocationDao sld = new SimpleLocationDao();
        sld.add(location);
        sld.add(location2);

        ArrayList<Location> expected = new ArrayList<>();
        expected.add(location);

        List<Location> actual = sld.findUserLocations("Benjamin");

        Assert.assertEquals(expected, actual);
    }
}
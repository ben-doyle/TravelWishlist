package com.codechallenge.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


public class LocationTest extends TestCase {

    /**
     * Test the getName method.
     */
    public void testGetName() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals("Berlin, Germany", location.getName());
    }

    /**
     * Test that after 5 people vote for a location, it becomes popular
     */
    public void testIsPopular() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertFalse(location.isPopular());
        location.addVoter("Ben");
        Assert.assertFalse(location.isPopular());
        location.addVoter("John");
        Assert.assertFalse(location.isPopular());
        location.addVoter("Suzie");
        Assert.assertFalse(location.isPopular());
        location.addVoter("Fred");
        Assert.assertFalse(location.isPopular());
        location.addVoter("Tom");
        Assert.assertTrue(location.isPopular());
    }

    /**
     * Test getter for description is working.
     */
    public void testGetDescription() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals("The Great City", location.getDescription());
    }

    /**
     * Test slugify is working, and getter for slug is working.
     */
    public void testGetSlug() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals("berlin-germany", location.getSlug());
    }

    /**
     * Test get list of voters is working.
     */
    public void testGetVotersAndVoteCount() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        location.addVoter("Benjamin");
        List<String> voters = new ArrayList<>();
        voters.add("Benjamin");
        Assert.assertEquals(1, location.getVoteCount());
        Assert.assertEquals(voters, location.getVoters());
        location.addVoter("Emily");
        voters.add("Emily");
        Assert.assertEquals(2, location.getVoteCount());
//        Assert.assertTrue(location.getVoters().equals(voters));
    }

    /**
     * Test the get suggested by method.
     */
    public void testGetSuggestedBy() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals("Benjamin", location.getSuggestedBy());
    }

    public void testGetLattitudeAndLongitude() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals("52.52000659999999 13.404954", location.getLattitude() + " " + location.getLongitude());
    }
}
package com.codechallenge.model;

import junit.framework.Assert;
import junit.framework.TestCase;


public class LocationTest extends TestCase {

    /**
     * Test the getName method.
     */
    public void testGetName() {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals(location.getName(), "Berlin, Germany");
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

    public void testGetDescription() throws Exception {
        Location location = new Location("Berlin", "Benjamin", "The Great " +
                "City");
        Assert.assertEquals(location.getDescription(), "The Great Citygit a");
    }

    public void testGetSlug() throws Exception {

    }

    public void testGetVoters() throws Exception {

    }

    public void testAddVoter() throws Exception {

    }

    public void testGetSuggestedBy() throws Exception {

    }

    public void testGetLattitude() throws Exception {

    }

    public void testGetLongitude() throws Exception {

    }

    public void testGetVoteCount() throws Exception {

    }

    public void testEquals() throws Exception {

    }

    public void testHashCode() throws Exception {

    }

}
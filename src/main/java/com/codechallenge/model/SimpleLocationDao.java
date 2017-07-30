package com.codechallenge.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleLocationDao implements LocationDAO {
    private List<Location> locations;

    public SimpleLocationDao() {
        locations = new ArrayList<>();
    }

    @Override
    public boolean add(Location location) {
        return locations.add(location);
    }

    @Override
    public List<Location> findAll() {
        return new ArrayList<>(locations);
    }

    @Override
    public Location findBySlug(String slug) {
        return locations.stream()
                .filter(location -> location.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Location> findUserLocations(String user) {
        ArrayList<Location> result = new ArrayList<>();
        for (Location l:locations) {
            if (l.getSuggestedBy().equals(user)) {
                result.add(l);
            }
        }
        return result;
    }

    @Override
    public String getLocations() {
        String result = "";
        for (Location l:locations) {
           result += "[" + l.getLattitude() + "," + l.getLongitude() + "],";
        }
        return result;
    }
}

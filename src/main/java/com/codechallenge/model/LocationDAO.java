package com.codechallenge.model;

import java.util.List;

public interface LocationDAO {
    boolean add(Location location);

    List<Location> findAll();

    Location findBySlug(String slug);

    List<Location> findUserLocations(String user);

    String getLocations();

}

package com.codechallenge.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleWishListDao implements WishListDAO {
    private List<WishListIdea> locations;

    public SimpleWishListDao() {
        locations = new ArrayList<>();
    }

    @Override
    public boolean add(WishListIdea locationIdea) {
        return locations.add(locationIdea);
    }

    @Override
    public List<WishListIdea> findAll() {
        return new ArrayList<>(locations);
    }

    @Override
    public WishListIdea findBySlug(String slug) {
        return locations.stream()
                .filter(locationIdea -> locationIdea.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}

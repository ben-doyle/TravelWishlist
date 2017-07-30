package com.codechallenge.model;

import java.util.List;

public interface WishListDAO {
    boolean add(WishListIdea locationIdea);

    List<WishListIdea> findAll();

    WishListIdea findBySlug(String slug);
}

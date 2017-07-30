package com.codechallenge.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WishListIdea {
    private String name;
    private String suggestedBy;
    private String slug;
    private Set<String> voters;

    public WishListIdea(String name, String company) {
        voters = new HashSet<>();
        this.name = name;
        this.suggestedBy = company;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return suggestedBy;
    }

    public String getSlug() {
        return slug;
    }

    public List<String> getVoters() {
        return new ArrayList<>(voters);
    }

    public boolean addVoter(String voterUserName) {
        return voters.add(voterUserName);
    }

    public int getVoteCount() {
        return voters.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishListIdea coffeeIdea = (WishListIdea) o;

        if (name != null ? !name.equals(coffeeIdea.name) : coffeeIdea.name != null)
            return false;
        return suggestedBy != null ? suggestedBy.equals(coffeeIdea.suggestedBy) : coffeeIdea.suggestedBy == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (suggestedBy != null ? suggestedBy.hashCode() : 0);
        return result;
    }
}

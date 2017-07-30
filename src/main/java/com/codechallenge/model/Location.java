package com.codechallenge.model;

import com.github.slugify.Slugify;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Location {

    private static final String API_KEY = "AIzaSyDNb9_4ZFwEFfovzr899TZXB16BtPS_xyo";
    private String name;
    private String suggestedBy;
    private Double lattitude;
    private Double longitude;
    private String slug;
    private Set<String> voters;

    public Location(String name, String suggestedBy) {
        voters = new HashSet<>();
        this.name = name;
        this.suggestedBy = suggestedBy;
        this.setLocation(name);
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

    public String getSuggestedBy() {
        return suggestedBy;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getVoteCount() {
        return voters.size();
    }

    public void setLocation(String name) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    name).await();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.lattitude = results[0].geometry.location.lat;
            this.longitude = results[0].geometry.location.lng;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location locationIdea = (Location) o;

        if (name != null ? !name.equals(locationIdea.name) : locationIdea.name != null)
            return false;
        return suggestedBy != null ? suggestedBy.equals(locationIdea.suggestedBy) : locationIdea.suggestedBy == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (suggestedBy != null ? suggestedBy.hashCode() : 0);
        return result;
    }
}

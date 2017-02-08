package com.mint.coffeeshops.model;

import org.json.JSONArray;
import org.json.JSONException;

public class CoffeeShop {

    private String coffeeShopName;
    private String coffeeShopRating;
    private String venueIcon;
    private String address;
    private String distance;
    private String coffeeShopUrl;

    private final static String venueIconSize = "64";

    public CoffeeShop(String coffeeShopName, String coffeeShopRating, JSONArray venueCategories) {
        this.coffeeShopName = coffeeShopName;
        this.coffeeShopRating = coffeeShopRating;
        this.venueIcon = buildIconUri(venueCategories);
    }

    public CoffeeShop(String coffeeShopName, String coffeeShopRating, JSONArray venueCategories,
                      String address
            , String distance
            , String coffeeShopUrl
    ) {
        this.coffeeShopName = coffeeShopName;
        this.coffeeShopRating = coffeeShopRating;
        this.venueIcon = buildIconUri(venueCategories);
        this.address = address;
        this.distance = distance;
        this.coffeeShopUrl = coffeeShopUrl;

    }

    private String buildIconUri(JSONArray venueCategories) {

        try {
            return venueCategories.getJSONObject(0).getJSONObject("icon").getString("prefix") + venueIconSize
                    + venueCategories.getJSONObject(0).getJSONObject("icon").getString("suffix");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCoffeeShopName() {
        return coffeeShopName;
    }

    public String getCoffeeShopRating() {
        return coffeeShopRating;
    }


    public String getVenueIcon() {
        return venueIcon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCoffeeShopUrl() {
        return coffeeShopUrl;
    }

    public void setCoffeeShopUrl(String coffeeShopUrl) {
        this.coffeeShopUrl = coffeeShopUrl;
    }
}
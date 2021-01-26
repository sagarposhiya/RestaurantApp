package com.example.restaurantapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResturantList {
    @SerializedName("results")
    private List<Restaurant> restaurantList;

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}

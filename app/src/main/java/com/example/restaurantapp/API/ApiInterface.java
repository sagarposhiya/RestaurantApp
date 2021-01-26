package com.example.restaurantapp.API;

import com.example.restaurantapp.pojos.ResturantList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("nearbysearch/json?")
    Call<ResturantList> getList(@Query("location") String location, @Query("radius") String radius, @Query("type") String type, @Query("keyword") String keyWord,@Query("key") String key);

}

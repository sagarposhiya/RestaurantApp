package com.example.restaurantapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurant {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;
    @SerializedName("formatted_address")
    private String formatted_address;
    @SerializedName("opening_hours")
    private OpeningHours opening_hours;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("price_level")
    private String price_level;
    @SerializedName("rating")
    private String rating;
    @SerializedName("nareferenceme")
    private String reference;
    @SerializedName("user_ratings_total")
    private String user_ratings_total;
    @SerializedName("plus_code")
    private PlusCode plus_code;
    @SerializedName("types")
    private List<String> types;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public OpeningHours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getPrice_level() {
        return price_level;
    }

    public void setPrice_level(String price_level) {
        this.price_level = price_level;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(String user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

   public class OpeningHours{
       @SerializedName("open_now")
      private String open_now;

       public String getOpen_now() {
           return open_now;
       }
   }

   public class PlusCode {
        @SerializedName("compound_code")
        private String compound_code;
        @SerializedName("global_code")
        private String global_code;

    }

    class Types {
        private String type;
    }
}

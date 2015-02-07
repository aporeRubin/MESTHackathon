package com.edzeban.app.service;

import com.edzeban.app.model.Meal;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by rubin on 2/6/15.
 */
public interface EdzinbanPaService {
    static final String domain = "https://edzibanpa.herokuapp.com/api";
    static final int port = 80 ;

    static final String api  = domain;

    //static final String asset_path = "/assets/images/" ;
    static final String asset_path = "/assets/exempt/" ;
    static final String socket_path = "/socket.io-client/" ;
    static final String randomImage = "" ;
    static final String s3Base = "";
    //static final String api  = "http://www.google.com";

    @GET("/meals.json")
    void getMeals(
            @Query("X-EDZI-MEAL-TYPE") String mealType,
            @Query("X-EDZI-DIABETIC") String diabetic,
            @Query("X-EDZI-HYPERTENSIVE") String hypertensive,
            @Query("X-EDZI-LOOSING-WEIGHT") String weight,
            @Query("X-EDZI-RECUPERATING") String recup,
            @Query("X-EDZI-LACTOSE-INTOLERANT") String lactose,
            @Query("X-EDZI-ACTIVITY-LEVEL") String activity,
            Callback<List<Meal>> meals
    );

    @GET("/meals/{id}.json")
    void getMeal(
            @Path("id") int id,
            Callback<Meal> meal
    );

    @GET("/search.json")
    void doSearch(
            @Query("X-EDZI-MEAL-TYPE") String mealType,
            @Query("X-EDZI-DIABETIC") String diabetic,
            @Query("X-EDZI-HYPERTENSIVE") String hypertensive,
            @Query("X-EDZI-LOOSING-WEIGHT") String weight,
            @Query("X-EDZI-RECUPERATING") String recup,
            @Query("X-EDZI-LACTOSE-INTOLERANT") String lactose,
            @Query("X-EDZI-ACTIVITY-LEVEL") String activity,
            @Query("query") String query,
            Callback<List<Meal>> meals
    );

}

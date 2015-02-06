package com.edzeban.app.service;

import com.edzeban.app.model.Meal;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
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
            @Header("X-EDZI-MEAL_TYPE") String mealType,
            @Header("X-EDZI-DIABETIC") String diabetic,
            @Header("X-EDZI-HYPERTENSIVE") String hypertensive,
            @Header("X-EDZI-LOOSING_WEIGHT") String weight,
            @Header("X-EDZI-RECUPERATING") String recup,
            @Header("X-EDZI-LACTOSE_INTOLERANT") String lactose,
            @Header("X-EDZI-ACTIVITY_LEVEL") String activity,
            Callback<List<Meal>> meals
            );

    @GET("/search.json")
    void doSearch(
            @Header("X-EDZI-MEAL_TYPE") String mealType,
            @Header("X-EDZI-DIABETIC") String diabetic,
            @Header("X-EDZI-HYPERTENSIVE") String hypertensive,
            @Header("X-EDZI-LOOSING_WEIGHT") String weight,
            @Header("X-EDZI-RECUPERATING") String recup,
            @Header("X-EDZI-LACTOSE_INTOLERANT") String lactose,
            @Header("X-EDZI-ACTIVITY_LEVEL") String activity,
            @Query("query") String query,
            Callback<List<Meal>> meals
    );
    /*//auth
    @POST("/auth/local")
    void login(@Body User user, Callback<JsonObject> cb);

    //user
    @GET("/api/users/me")
    void me(@Query("access_token") String token, Callback<User> thisUser);

    @GET("/api/users/find/")
    void getUser(@Query("access_token") String token , @Query("q") String condition , Callback<List<User>> thisUser);

    @POST("/api/users/")
    void createUser(@Body User user, Callback<JsonObject> cb);

    @POST("/api/users/{id}")
    void updateUser(@Query("access_token") String token ,@Path("id") String id, @Body User user ,  Callback<User> callback);

    @GET("/api/users/ping/")
    void doPing(@Query("access_token") String token , Callback<JsonObject> status);


    //messages
    @GET("/api/messages/")
    void getMessages(@Query("q") String q, Callback<List<Message>> messages);

    @GET("/api/messages/")
    void getCustomerMessages(@QueryMap Map<String, String> query, Callback<List<Message>> cb) ;

    @GET("/api/messages/")
    void getCustomersIds(@QueryMap Map<String, String> query, Callback<List<String>> cb) ;

    @POST("/api/messages/{id}")
    void updateMessage(@Path("id") String id, @Body Message message , Callback<Message> cb ) ;

    @POST("/api/messages/")
    void createMessage( @Body Message message , Callback<Message> cb ) ;

    //business
    @POST("/api/business/")
    void createBusiness( @Body Business business , Callback<Business> cb);
    @POST("/api/business/{id}")
    void updateBusiness( @Path("id")String id,  @Body Business business , Callback<Business> cb);


    @GET("/api/business/")
    void getBusinesses( @Query("q") String condition , Callback<List<Business>> businesses);

    @GET("/api/business/{id}")
    void getBusiness( @Path("id") String id, Callback<Business> business);

    //Tag
    @GET("/api/tags/{id}")
    void getTag(@Path("id") String tagId, Callback<Tag> cb);

    @GET("/api/tags/")
    void getTags(Callback<List<Tag>> cb);

    //Broadcast
    @POST("/api/broadcasts/")
    void sendBroadCast(@Body BroadCast broadCast, Callback<BroadCast> cb);

    @GET("/api/broadcasts/")
    void getBusinessBroadCast(@Query("q") String condition, Callback<List<BroadCast>> cb);

    @POST("/api/broadcasts/{id}")
    void updateBroadCast(@Path("id") String id, @Body BroadCast broadCast , Callback<BroadCast> cb ) ;*/
}

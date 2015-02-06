package com.edzeban.app;

import com.edzeban.app.controller.EdzinbanPa;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rubin on 2/6/15.
 */
public class Meal {
    public int id,
            general_rating,
            diabetic_rating,
            hypertensive_rating,
            weightloss_rating,
            recuperation_rating,
            fibre_rating,
            calories_per_portion;

    public String name,
            description,
            food_group,
            image_url,
            portion_type,
            contains_lactose,
            is_breakfast,
            is_lunch,
            is_supper,
            is_snack;

    public ArrayList<ImageObject> imageObjects;


    class ImageObject {
        int id, meal_item_id;
        String name, url;
    }

}

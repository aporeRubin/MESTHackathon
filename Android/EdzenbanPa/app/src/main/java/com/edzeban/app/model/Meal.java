package com.edzeban.app.model;

import java.util.ArrayList;

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

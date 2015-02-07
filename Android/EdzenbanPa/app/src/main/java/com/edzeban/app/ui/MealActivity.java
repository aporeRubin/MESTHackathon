package com.edzeban.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edzeban.app.EdzinbanPaApp;
import com.edzeban.app.R;
import com.edzeban.app.model.Meal;
import com.edzeban.app.util.Inflector;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gidis on 2/6/15.
 */
public class MealActivity extends ActionBarActivity {

    private int mMealID;
    private View mFeedback;
    private View mLoadingProgress;
    private TextView mErrorText;
    private Button mRetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFeedback = findViewById(R.id.feedback_view);
        mLoadingProgress = findViewById(R.id.loading_progress);
        mErrorText = (TextView) findViewById(R.id.error_message);

        mRetryButton = (Button) findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMeal();
            }
        });




        mMealID = getIntent().getIntExtra("id", -1);
        loadMeal();
    }


    protected  void loadMeal(){

        mFeedback.setVisibility(View.VISIBLE);
        mLoadingProgress.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.GONE);

        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeal(mMealID, new Callback<Meal>() {

            @Override
            public void success(Meal meal, Response response) {

                Log.d("meals", meal.toString());
                mLoadingProgress.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.GONE);
                mFeedback.setVisibility(View.GONE);

                getSupportActionBar().setTitle(meal.name);

                ImageView image = (ImageView) findViewById(R.id.meal_image);
                TextView description = (TextView) findViewById(R.id.meal_description);
                TextView general_rating = (TextView) findViewById(R.id.general_rating);
                TextView diabetic_rating = (TextView) findViewById(R.id.diabetic_rating);
                TextView hypertensive_rating = (TextView) findViewById(R.id.hypertensive_rating);
                TextView weightloss_rating = (TextView) findViewById(R.id.weightloss_rating);
                TextView recuperation_rating = (TextView) findViewById(R.id.recuperation_rating);
                TextView fibre_rating = (TextView) findViewById(R.id.fibre_rating);
                TextView food_group = (TextView) findViewById(R.id.food_group);
                TextView portion_type = (TextView) findViewById(R.id.portion_type);
                TextView calories_per_portion = (TextView) findViewById(R.id.calories_per_portion);
                TextView contains_lactose = (TextView) findViewById(R.id.contains_lactose);

                Picasso.with(MealActivity.this).load(meal.image_url).into(image);
                description.setText(meal.description);
                general_rating.setText(String.valueOf(meal.general_rating));
                diabetic_rating.setText(String.valueOf(meal.diabetic_rating));
                hypertensive_rating.setText(String.valueOf(meal.hypertensive_rating));
                weightloss_rating.setText(String.valueOf(meal.weightloss_rating));
                recuperation_rating.setText(String.valueOf(meal.recuperation_rating));
                fibre_rating.setText(String.valueOf(meal.fibre_rating));
                food_group.setText(Inflector.camelCase(meal.food_group));
                portion_type.setText(Character.toUpperCase(meal.portion_type.charAt(0)) + meal.portion_type.substring(1));
                calories_per_portion.setText(String.valueOf(meal.calories_per_portion));
                contains_lactose.setText(meal.contains_lactose.equals("1") ? "Yes" : "No");
            }

            @Override
            public void failure(RetrofitError error) {
                mFeedback.setVisibility(View.VISIBLE);
                mLoadingProgress.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.VISIBLE);
                mErrorText.setVisibility(View.VISIBLE);
                mErrorText.setText(error.getMessage());
            }
        });
    }


}

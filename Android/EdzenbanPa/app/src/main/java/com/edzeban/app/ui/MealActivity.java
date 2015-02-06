package com.edzeban.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.edzeban.app.EdzinbanPaApp;
import com.edzeban.app.R;
import com.edzeban.app.model.Meal;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gidis on 2/6/15.
 */
public class MealActivity extends ActionBarActivity {

    private int mMealID;
    private View mLoadingProgress1;
    private TextView mErrorText1;
    private View mFeedback1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        mLoadingProgress1 = findViewById(R.id.loading_progress);
        mErrorText1 = (TextView) findViewById(R.id.error_message);
        mFeedback1 = findViewById(R.id.feedback_view);



        mMealID = getIntent().getIntExtra("id", -1);
        loadMealDetails();
    }


    protected  void loadMealDetails(){
        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeal(mMealID, new Callback<Meal>() {

            @Override
            public void success(Meal meal, Response response) {
                mFeedback1.setVisibility(View.VISIBLE);
                mLoadingProgress1.setVisibility(View.VISIBLE);
                mErrorText1.setVisibility(View.GONE);


                getSupportActionBar().setTitle(meal.name);
                TextView description=(TextView) findViewById(R.id.meal_description);
                ImageView image = (ImageView) findViewById(R.id.meal_image);


                description.setText(meal.description);
                Picasso.with(MealActivity.this).load(meal.image_url).into(image);
            }

            @Override
            public void failure(RetrofitError error) {
                mLoadingProgress1.setVisibility(View.GONE);
                mErrorText1.setVisibility(View.VISIBLE);
                mErrorText1.setText(error.getMessage());
            }
        });
    }


}

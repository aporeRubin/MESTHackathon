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
        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeal(mMealID, new Callback<Meal>() {

            @Override
            public void success(Meal meal, Response response) {

                Log.d("meals", meal.toString());
                mLoadingProgress.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.GONE);
                mFeedback.setVisibility(View.GONE);

                getSupportActionBar().setTitle(meal.name);

                TextView description=(TextView) findViewById(R.id.meal_description);
                ImageView image = (ImageView) findViewById(R.id.meal_image);

                description.setText(meal.description);
                Picasso.with(MealActivity.this).load(meal.image_url).into(image);
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

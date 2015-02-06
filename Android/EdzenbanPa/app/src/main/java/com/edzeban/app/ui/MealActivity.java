package com.edzeban.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.edzeban.app.EdzinbanPaApp;
import com.edzeban.app.R;
import com.edzeban.app.model.Meal;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gidis on 2/6/15.
 */
public class MealActivity extends ActionBarActivity {
    private ListView mListView;

    private View mFeedback;

    private TextView mErrorText;
    private View mLoadingProgress;
    private int mMealID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        mLoadingProgress = findViewById(R.id.loading_progress);
        mErrorText = (TextView) findViewById(R.id.error_message);
        mMealID = getIntent().getIntExtra("id", -1);
        loadMealDetails();
    }

    protected  void loadMealDetails(){
        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeal(mMealID, new Callback<Meal>() {
            @Override
            public void success(Meal meal, Response response) {
                mLoadingProgress.setVisibility(View.GONE);

                if(mMealID == 0) {
                    mErrorText.setVisibility(View.VISIBLE);
                    mErrorText.setText(getString(R.string.no_items));
                }
                else {
                    mFeedback.setVisibility(View.GONE);

                    mFeedback.setVisibility(View.GONE);
                }


            }

            @Override
            public void failure(RetrofitError error) {
                mLoadingProgress.setVisibility(View.GONE);
                mErrorText.setVisibility(View.VISIBLE);
                mErrorText.setText(error.getMessage());
            }
        });
    }


}

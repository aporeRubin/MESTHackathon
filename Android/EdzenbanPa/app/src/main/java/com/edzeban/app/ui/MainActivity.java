package com.edzeban.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.edzeban.app.EdzinbanPaApp;
import com.edzeban.app.R;
import com.edzeban.app.model.Meal;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {


    private ListView mListView;
    private MealAdapter mMealAdapter;
    private View mFeedback;
    private View mLoadingProgress;
    private TextView mErrorText;
    private String mCurrentMealType = "breakfast";
    private Button mRetryButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.meal_list);
        mFeedback = findViewById(R.id.feedback_view);
        mLoadingProgress = findViewById(R.id.loading_progress);
        mErrorText = (TextView) findViewById(R.id.error_message);

        mRetryButton = (Button) findViewById(R.id.retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMeals(mCurrentMealType);
            }
        });


        int meal_type = 0;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        if(hour >= 0 && hour <= 11) {
            meal_type = 0;
            mCurrentMealType = "breakfast";
        }
        else if(hour > 11 && hour < 18) {
            meal_type = 1;
            mCurrentMealType = "lunch";
        }
        else if(hour > 18 && hour <= 23) {
            meal_type = 2;
            mCurrentMealType = "supper";
        }

//        ArrayAdapter<String> spinner  = new ArrayAdapter<String>(
//                getSupportActionBar().getThemedContext(),
//                R.layout.actionbar_spinner,
//                android.R.id.text1, getResources().getStringArray(R.array.meal_types));
//        spinner .setDropDownViewResource(R.layout.actionbar_spinner_dropdown);
//        actionBar.setListNavigationCallbacks(adapter, null);

        SpinnerAdapter spinner = ArrayAdapter.createFromResource(this, R.array.meal_types, R.layout.actionbar_spinner_dropdown);
        getSupportActionBar().setListNavigationCallbacks(spinner, new ActionBar.OnNavigationListener() {
            public boolean mSpinnerLoaded;

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if(!mSpinnerLoaded) {
                    mSpinnerLoaded = true;
                    return false;
                }

                String meal_type = "breakfast";
                switch (position) {
                    case 0:
                        meal_type = "breakfast";
                        break;
                    case 1:
                        meal_type = "lunch";
                        break;
                    case 2:
                        meal_type = "supper";
                        break;
                    case 3:
                        meal_type = "snack";
                        break;
                }
                loadMeals(meal_type);

                return true;
            }
        });

        //initiate load
        getSupportActionBar().setSelectedNavigationItem(meal_type);
        loadMeals(mCurrentMealType);
    }

    protected void loadMeals(final String meal_type) {
        mCurrentMealType = meal_type;

        mFeedback.setVisibility(View.VISIBLE);
        mLoadingProgress.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.GONE);

        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeals(meal_type, "", "", "", "", "", "",
                new Callback<List<Meal>>() {
                    @Override
                    public void success(List<Meal> meals, Response response) {
                        Log.d("meals", meals.toString());
                        mLoadingProgress.setVisibility(View.GONE);
                        mRetryButton.setVisibility(View.GONE);

                        if(meals.size() == 0) {
                            mErrorText.setVisibility(View.VISIBLE);
                            mErrorText.setText(getString(R.string.no_items));
                        }
                        else {
                            mFeedback.setVisibility(View.GONE);
                        }

                        mMealAdapter = new MealAdapter(MainActivity.this, meals);
                        mListView.setAdapter(mMealAdapter);
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

    class MealAdapter extends ArrayAdapter<Meal> {
        public MealAdapter(Context context, List<Meal> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Meal meal = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_meallistcarditem, parent, false);
            }

            TextView text = (TextView) convertView.findViewById(R.id.meal_name);
            TextView description=(TextView) convertView.findViewById(R.id.meal_description);
            ImageView image = (ImageView) convertView.findViewById(R.id.meal_image);

            text.setText(meal.name);
            description.setText(meal.description);
            Picasso.with(getContext()).load(meal.image_url).into(image);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MealActivity.class);
                    intent.putExtra("id", meal.id);

                    startActivity(intent);
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }


}
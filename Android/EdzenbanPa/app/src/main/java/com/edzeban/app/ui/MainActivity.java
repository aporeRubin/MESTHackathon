package com.edzeban.app.ui;

import android.content.Context;
import android.content.DialogInterface;
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
import com.edzeban.app.util.ProgressHUB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private static String TAG = "MainActivity" ;
    private ListView mListView;
    private MealAdapter mMealAdapter;
    private View mFeedback;
    private ProgressHUB mLoadingProgress;
    private TextView mErrorText;
    private String mCurrentMealType = "breakfast";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.meal_list);
        mFeedback = findViewById(R.id.feedback_view);
        //mLoadingProgress = findViewById(R.id.loading_progress);
        mErrorText = (TextView) findViewById(R.id.error_message);

        Button retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
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
        }
        else if(hour > 11 && hour < 18) {
            meal_type = 1;
        }
        else if(hour > 18 && hour <= 23) {
            meal_type = 2;
        }

        SpinnerAdapter spinner = ArrayAdapter.createFromResource(this, R.array.meal_types,android.R.layout.simple_spinner_dropdown_item);
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
    }

    protected void loadMeals(final String meal_type) {
        mCurrentMealType = meal_type;

        mFeedback.setVisibility(View.VISIBLE);
        mLoadingProgress = ProgressHUB.show(this, "loading . . .", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e(TAG, "the progress cancelled");
            }
        });
        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeals(meal_type, "", "", "", "", "", "",
                new Callback<List<Meal>>() {
                    @Override
                    public void success(List<Meal> meals, Response response) {
                        Log.d("meals", meals.toString());
                        mLoadingProgress.hide();

                        if(meals.size() == 0) {
                            mErrorText.setVisibility(View.VISIBLE);
                            mErrorText.setText(getString(R.string.no_items));
                        }
                        else {
                            mFeedback.setVisibility(View.GONE);

                            mFeedback.setVisibility(View.GONE);
                        }

                        mMealAdapter = new MealAdapter(MainActivity.this, meals);
                        mListView.setAdapter(mMealAdapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mLoadingProgress.hide();
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
            Meal meal = getItem(position);
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

            // Return the completed view to render on screen
            return convertView;
        }
    }


}
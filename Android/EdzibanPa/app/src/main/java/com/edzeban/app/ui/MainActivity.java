package com.edzeban.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.edzeban.app.EdzinbanPaApp;
import com.edzeban.app.R;
import com.edzeban.app.model.Meal;
import com.edzeban.app.ui.fragment.NavigationDrawerFragment;
import com.edzeban.app.util.Prefs;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
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


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();



        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

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

        Prefs prefs = new Prefs(this);

        EdzinbanPaApp.edzinbanPa.edzinbanPaService.getMeals(meal_type, prefs.getDiabetic(),prefs.getHypertensive(), prefs.getLosingWeight(), prefs.getRecuperating(), prefs.getLactoseIntolerant(), prefs.getActivityLevel(),
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(this)
                    .setTitle("About")
                    .setMessage(R.string.dialog_string);

            mMaterialDialog.show();
            return true;
        }
        else if (id == R.id.action_refresh) {
            loadMeals(mCurrentMealType);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle(mTitle);
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
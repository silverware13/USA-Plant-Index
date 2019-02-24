package com.example.android.lifecycleweather;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.android.lifecycleweather.utils.NetworkUtils;
import com.example.android.lifecycleweather.utils.USDAUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PlantAdapter.OnPlantItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mPlantLocationTV;
    private RecyclerView mPlantItemsRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private PlantAdapter mPlantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove shadow under action bar.
        getSupportActionBar().setElevation(0);

        mPlantLocationTV = findViewById(R.id.tv_forecast_location);
        mPlantLocationTV.setText(WeatherPreferences.getDefaultPlantLocation());

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mPlantItemsRV = findViewById(R.id.rv_forecast_items);

        mPlantAdapter = new PlantAdapter(this);
        mPlantItemsRV.setAdapter(mPlantAdapter);
        mPlantItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mPlantItemsRV.setHasFixedSize(true);

        loadPlant();
    }

    @Override
    public void onPlantItemClick(USDAUtils.PlantItem plantItem) {
        Intent intent = new Intent(this, PlantItemDetailActivity.class);
        intent.putExtra(USDAUtils.EXTRA_FORECAST_ITEM, plantItem);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                showPlantLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadPlant() {
        String openWeatherMapPlantURL = USDAUtils.buildPlantURL(
                WeatherPreferences.getDefaultPlantLocation(),
                WeatherPreferences.getDefaultTemperatureUnits()
        );
        Log.d(TAG, "got forecast url: " + openWeatherMapPlantURL);
        new PlantTask().execute(openWeatherMapPlantURL);
    }

    public void showPlantLocation() {
        Uri geoUri = Uri.parse("geo:0,0").buildUpon()
                .appendQueryParameter("q", WeatherPreferences.getDefaultPlantLocation())
                .build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    class PlantTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String openWeatherMapURL = params[0];
            String plantJSON = null;
            try {
                plantJSON = NetworkUtils.doHTTPGet(openWeatherMapURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return plantJSON;
        }

        @Override
        protected void onPostExecute(String plantJSON) {
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (plantJSON != null) {
                mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                mPlantItemsRV.setVisibility(View.VISIBLE);
                ArrayList<USDAUtils.PlantItem> plantItems = USDAUtils.parsePlantJSON(plantJSON);
                mPlantAdapter.updatePlantItems(plantItems);
            } else {
                mPlantItemsRV.setVisibility(View.INVISIBLE);
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }
}

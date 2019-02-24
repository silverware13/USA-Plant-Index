package com.example.android.lifecycleweather;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.android.lifecycleweather.utils.USDAUtils;

import java.text.DateFormat;

public class PlantItemDetailActivity extends AppCompatActivity {

    private TextView mPlantSciTV;
    private TextView mPlantComTV;
    private TextView mPlantDetails;
    private ImageView mPlantPicIV;

    private USDAUtils.PlantItem mPlantItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_item_detail);

        mPlantSciTV = findViewById(R.id.tv_plant_scientific);
        mPlantComTV = findViewById(R.id.tv_plant_common);
        mPlantDetails = findViewById(R.id.tv_plant_details);
        mPlantPicIV = findViewById(R.id.iv_plant_pic);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USDAUtils.EXTRA_FORECAST_ITEM)) {
            mPlantItem = (USDAUtils.PlantItem)intent.getSerializableExtra(
                    USDAUtils.EXTRA_FORECAST_ITEM
            );
            fillInLayout(mPlantItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plant_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                sharePlant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sharePlant() {
        if (mPlantItem != null) {

            String sciString = mPlantItem.Scientific_Name_x;
            String comString = mPlantItem.Common_Name;
            String detailString =
                    "Symbol: " + mPlantItem.Symbol + "\n" +
                    "Group: " + mPlantItem.Group + "\n" +
                    "Family: " + mPlantItem.Family + "\n" +
                    "Duration: " + mPlantItem.Duration + "\n" +
                    "Growth Habit: " + mPlantItem.Growth_Habit + "\n" +
                    "Native status: " + mPlantItem.Native_Status + "\n" +
                    "Category: " + mPlantItem.Category + "\n" +
                    "Order: " + mPlantItem.xOrder + "\n" +
                    "Class: " + mPlantItem.Class + "\n" +
                    "Subclass: " + mPlantItem.SubClass + "\n" +
                    "Kingdom: " + mPlantItem.Kingdom + "\n" +
                    "Species: " + mPlantItem.Species + "\n" +
                    "Subspecies: " + mPlantItem.Subspecies + "\n" +
                    "State and Province: " + mPlantItem.State_and_Province;

            String shareText = R.string.plant_item_share_text + "\n" +
                    sciString + "\n" + comString + "\n" +  detailString;
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareText)
                    .setChooserTitle(R.string.share_chooser_title)
                    .startChooser();
        }
    }

    private void fillInLayout(USDAUtils.PlantItem plantItem) {
        String sciString = plantItem.Scientific_Name_x;
        String comString = plantItem.Common_Name;
        String detailString =
                "Symbol: " + mPlantItem.Symbol + "\n" +
                "Group: " + mPlantItem.Group + "\n" +
                "Family: " + mPlantItem.Family + "\n" +
                "Duration: " + mPlantItem.Duration + "\n" +
                "Growth Habit: " + mPlantItem.Growth_Habit + "\n" +
                "Native status: " + mPlantItem.Native_Status + "\n" +
                "Category: " + mPlantItem.Category + "\n" +
                "Order: " + mPlantItem.xOrder + "\n" +
                "Class: " + mPlantItem.Class + "\n" +
                "Subclass: " + mPlantItem.SubClass + "\n" +
                "Kingdom: " + mPlantItem.Kingdom + "\n" +
                "Species: " + mPlantItem.Species + "\n" +
                "Subspecies: " + mPlantItem.Subspecies + "\n" +
                "State and Province: " + mPlantItem.State_and_Province;

        //String iconURL = USDAUtils.buildIconURL(plantItem.icon);

        mPlantSciTV.setText(sciString);
        mPlantComTV.setText(comString);
        mPlantDetails.setText(detailString);
        //Glide.with(this).load(iconURL).into(mPlantPicIV);
    }
}

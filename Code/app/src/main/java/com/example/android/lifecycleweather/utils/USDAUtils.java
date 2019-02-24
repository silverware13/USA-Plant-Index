package com.example.android.lifecycleweather.utils;

import android.net.Uri;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class USDAUtils {

    public static final String EXTRA_FORECAST_ITEM = "com.example.android.lifecycleweather.utils.PlantItem";

    private final static String OWM_FORECAST_BASE_URL = "https://plantsdb.xyz/search?limit=25&offset=0";
    private final static String OWM_ICON_URL_FORMAT_STR = "https://openweathermap.org/img/w/%s.png";
    private final static String OWM_FORECAST_QUERY_PARAM = "q";
    private final static String OWM_FORECAST_UNITS_PARAM = "units";
    private final static String OWM_FORECAST_APPID_PARAM = "appid";
    private final static String OWM_FORECAST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String OWM_FORECAST_TIME_ZONE = "UTC";

    /*
     * This class is used as a final representation of a single plant item.
     * It condenses the classes below that are used for parsing the OWN JSON response with Gson.
     */
    public static class PlantItem implements Serializable {
        public String Scientific_Name_x;
        public String Common_Name;
        public String Symbol;
        public String Group;
        public String Family;
        public String Duration;
        public String Growth_Habit;
        public String Native_Status;
        public String Category;
        public String xOrder;
        public String SubClass;
        public String Class;
        public String Kingdom;
        public String Species;
        public String Subspecies;
        public String State_and_Province;
    }

    /*
     * The below several classes are used only for JSON parsing with Gson.
     */
    static class plantResults implements Serializable {
        public ArrayList<PlantItem> data;
    }

    public static String buildPlantURL(String forecastLocation, String temperatureUnits) {
        return Uri.parse(OWM_FORECAST_BASE_URL)
                .buildUpon().build().toString();
                //.appendQueryParameter(OWM_FORECAST_QUERY_PARAM, forecastLocation)
                //.appendQueryParameter(OWM_FORECAST_UNITS_PARAM, temperatureUnits)
                //.appendQueryParameter(OWM_FORECAST_APPID_PARAM, OWM_FORECAST_APPID)
                //.build()
                //.toString();
    }

    public static String buildIconURL(String icon) {
        return String.format(OWM_ICON_URL_FORMAT_STR, icon);
    }

    public static ArrayList<PlantItem> parsePlantJSON(String plantJSON) {
        Gson gson = new Gson();
        plantResults results = gson.fromJson(plantJSON, plantResults.class);
        if (results != null && results.data != null) {
            return results.data;
        } else {
            return null;
        }
    }
}

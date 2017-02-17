package com.umg.edu.colorweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DATA = "data";
    public static final String SUMMARY = "summary";
    public static final String PRECIP_PROBABILITY = "precipProbability";
    public static final String TIME = "time";
    public static final String CURRENTLY = "currently";
    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";
    public static final String MINUTELY = "minutely";
    public static final String TIMEZONE = "timezone";
    public static final String DAYS_ARRAY_LIST = "DAYS_ARRAY_LIST";
    public static final String HOURS_ARRAY_LIST = "HOURS_ARRAY_LIST";
    public static final String MINUTELY_ARRAY_LIST = "MINUTELY_ARRAY_LIST";
    @BindView(R.id.iconImageView)
    ImageView iconImageView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.currentTempTextView)
    TextView currentTempTextView;
    @BindView(R.id.degreeImageView)
    ImageView degreeImageView;
    @BindView(R.id.highestTextView)
    TextView highestTextView;
    @BindView(R.id.lowestTempTextView)
    TextView lowestTempTextView;
    @BindView(R.id.dailyWeatherTextView)
    TextView dailyWeatherTextView;
    @BindView(R.id.hourlyWeatherTextView)
    TextView hourlyWeatherTextView;
    @BindView(R.id.minutelyWeatherTextView)
    TextView minutelyWeatherTextView;

    @BindDrawable(R.drawable.clear_night)
    Drawable clearNight;

    ArrayList<Day> days;
    ArrayList<Hour> hours;
    ArrayList<Minute> minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        RequestQueue queue = Volley.newRequestQueue(this);

        /*String forecastURL = "https://api.darksky.net/forecast";
        String apiKey = "c5c8d8e0562afebefd2d536df4cd7917";
        String latitude = "37.8267";
        String longitude = "-122.4233";
        String units = "units=si";*/

        //String url = forecastURL + "/" + apiKey + "/" + latitude + "," + longitude + "?" + units;

       // String url = "https://api.darksky.net/forecast/c5c8d8e0562afebefd2d536df4cd7917/37.8267,-122.4233?units=si";
         String url = "https://api.darksky.net/forecast/c5c8d8e0562afebefd2d536df4cd7917/14.07417,-90.41667?units=si";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            CurrentWeather currentWeather = getCurrentWeatherFromJson(response);

                            days = getDailyWeatherFromJson(response);
                            hours = getHourlyWeather(response);
                            minutes = getMinutelyWeatherFromJson(response);

                          /*  for (Hour hour : hours) {
                                Log.d(TAG,"Debug para: " + hour.getTitle());
                                Log.d(TAG,"Debug para: " + hour.getWeatherDescription());
                            }
                            for (Day day : days) {
                                Log.d(TAG,"Debug para: " + day.getDayName());
                                Log.d(TAG,"Debug para: " + day.getWeatherDescription());
                                Log.d(TAG,"Debug para: " + day.getRainProbability());

                            }*/
                            /*for(Minute minute : minutes){
                                Log.d(TAG,minute.getTitle());
                                Log.d(TAG,minute.getMinuteWeatherDescription());
                            }*/

                            iconImageView.setImageDrawable(currentWeather.getIconDrawableResource());
                            descriptionTextView.setText(currentWeather.getDescription());
                            currentTempTextView.setText(currentWeather.getCurrentTemperature());
                            highestTextView.setText(String.format("H: %s°",currentWeather.getHighestTemperature()));
                            lowestTempTextView.setText(String.format("L: %s°",currentWeather.getLowestTemperature()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error en la conexión",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }

    @OnClick(R.id.dailyWeatherTextView)
    public void handlerDailyWeatherTextView() {
        Intent dailyActivityIntent = new Intent(MainActivity.this, DailyWeatherActivity.class);
        dailyActivityIntent.putParcelableArrayListExtra(DAYS_ARRAY_LIST,days);
        startActivity(dailyActivityIntent);
    }

    @OnClick(R.id.hourlyWeatherTextView)
    public void handlerHourlyWeatherTextView() {
        Intent hourlyActivityIntent = new Intent(MainActivity.this, HourlyWeatherActivity.class);
        hourlyActivityIntent.putParcelableArrayListExtra(HOURS_ARRAY_LIST,hours);
        startActivity(hourlyActivityIntent);
    }

    @OnClick(R.id.minutelyWeatherTextView)
    public void handlerMinutelyWeatherTextView() {
        Intent minutelyActivityIntent = new Intent(MainActivity.this, MinutelyWeatherActivity.class);
        minutelyActivityIntent.putParcelableArrayListExtra(MINUTELY_ARRAY_LIST,minutes);
        startActivity(minutelyActivityIntent);
    }

    private CurrentWeather getCurrentWeatherFromJson(String json) throws JSONException{

        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonWithCurrentWeather = jsonObject.getJSONObject(CURRENTLY);

        JSONObject jsonWithDailyWeather = jsonObject.getJSONObject(DAILY);

        JSONArray jsonWithDailyWeatherData = jsonWithDailyWeather.getJSONArray(DATA);

        JSONObject jsonWithTodayData = jsonWithDailyWeatherData.getJSONObject(0);

        String summary = jsonWithCurrentWeather.getString(SUMMARY);
        String icon = jsonWithCurrentWeather.getString("icon");
        String temperature =  Math.round(jsonWithCurrentWeather.getDouble("temperature")) + "";

        String maxTemperature = Math.round(jsonWithTodayData.getDouble("temperatureMax")) + "";
        String minTemperature = Math.round(jsonWithTodayData.getDouble("temperatureMin")) + "";

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);

        currentWeather.setDescription(summary);
        currentWeather.setIconImage(icon);
        currentWeather.setCurrentTemperature(temperature);
        currentWeather.setHighestTemperature(maxTemperature);
        currentWeather.setLowestTemperature(minTemperature);

        return currentWeather;

    }

    private ArrayList<Day> getDailyWeatherFromJson(String json) throws JSONException{

        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        ArrayList<Day> days = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getDefault().getTimeZone(timeZone));

        JSONObject jsonWithDailyWeather = jsonObject.getJSONObject(DAILY);

        JSONArray jsonWithDailyWeatherData = jsonWithDailyWeather.getJSONArray(DATA);

        for (int i = 0; i < jsonWithDailyWeatherData.length(); i++ ){
            Day day = new Day();

            JSONObject jsonWithDayData = jsonWithDailyWeatherData.getJSONObject(i);

            String rainProbability = "Rain Probability: " + jsonWithDayData.getDouble(PRECIP_PROBABILITY) * 100 + "%";

            String description = jsonWithDayData.getString(SUMMARY);

            String date = dateFormat.format(jsonWithDayData.getDouble(TIME) * 1000);

            day.setDayName(date);
            day.setRainProbability(rainProbability);
            day.setWeatherDescription(description);
            days.add(day);

        }

        return days;
    }

    public ArrayList<Hour> getHourlyWeather(String json) throws JSONException{

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        ArrayList<Hour> hours = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getDefault().getTimeZone(timeZone));

        JSONObject jsonWithHourlyWeather = jsonObject.getJSONObject(HOURLY);

        JSONArray jsonWithHourlyWeatherData = jsonWithHourlyWeather.getJSONArray(DATA);

        for (int i = 0; i < jsonWithHourlyWeatherData.length(); i++){
            Hour hour = new Hour();
            JSONObject jsonWithHourData = jsonWithHourlyWeatherData.getJSONObject(i);
            String title = dateFormat.format(jsonWithHourData.getDouble(TIME) * 1000);

            String description = jsonWithHourData.getString(SUMMARY);

            hour.setTitle(title);
            hour.setWeatherDescription(description);

            hours.add(hour);

        }



        return hours;

    }

    public ArrayList<Minute> getMinutelyWeatherFromJson(String json) throws JSONException{

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getDefault().getTimeZone("America/Los Angeles"));

        ArrayList<Minute> minutes = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        String timeZone = jsonObject.getString(TIMEZONE);

        dateFormat.setTimeZone(TimeZone.getDefault().getTimeZone(timeZone));

        JSONObject jsonWithMinutelyWeather = jsonObject.getJSONObject(MINUTELY);

        JSONArray jsonWithMinutelyWeatherData = jsonWithMinutelyWeather.getJSONArray(DATA);

        for(int i = 0; i < jsonWithMinutelyWeatherData.length(); i++){

            Minute minute = new Minute();

            JSONObject jsonWithMinuteData = jsonWithMinutelyWeatherData.getJSONObject(i);

            String title = dateFormat.format(jsonWithMinuteData.getDouble(TIME) * 1000);


            String precipProbability = "Rain Probability: " + jsonWithMinuteData.getDouble(PRECIP_PROBABILITY) * 100 + "%";

            minute.setTitle(title);
            minute.setMinuteWeatherDescription(precipProbability);
            minutes.add(minute);


        }

        return  minutes;
    }
}

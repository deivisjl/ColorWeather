package com.umg.edu.colorweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.umg.edu.colorweather.Adapters.DailyWeatherAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyWeatherActivity extends Activity {

    @BindView(R.id.dailyWeatherTitleTextView)
    TextView dailyWeatherTitleTextView;
    @BindView(R.id.dailyListView)
    ListView dailyListView;
    @BindView(R.id.empty)
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        ArrayList<Day> days = intent.getParcelableArrayListExtra(MainActivity.DAYS_ARRAY_LIST);

        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(days, this);
        dailyListView.setAdapter(dailyWeatherAdapter);
        dailyListView.setEmptyView(empty);
    }
}

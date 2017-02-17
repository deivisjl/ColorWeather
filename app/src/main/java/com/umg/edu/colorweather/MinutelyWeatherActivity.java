package com.umg.edu.colorweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.umg.edu.colorweather.Adapters.MinutelyWeatherAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinutelyWeatherActivity extends AppCompatActivity {

    @BindView(R.id.minutelyWeatherTitleTextView)
    TextView minutelyWeatherTitleTextView;
    @BindView(R.id.mainRecyclerView)
    RecyclerView mainRecyclerView;
    @BindView(R.id.minutelyNoDataTextView)
    TextView minutelyNoDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutely_weather);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        ArrayList<Minute> minutes = intent.getParcelableArrayListExtra(MainActivity.MINUTELY_ARRAY_LIST);

        if(minutes != null && !minutes.isEmpty()){
            minutelyNoDataTextView.setVisibility(View.GONE);
            mainRecyclerView.setVisibility(View.VISIBLE);

            MinutelyWeatherAdapter minutelyWeatherAdapter = new MinutelyWeatherAdapter(this, minutes);
            mainRecyclerView.setAdapter(minutelyWeatherAdapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mainRecyclerView.setLayoutManager(layoutManager);
            mainRecyclerView.setHasFixedSize(true);
        }else{
            minutelyNoDataTextView.setVisibility(View.VISIBLE);
            mainRecyclerView.setVisibility(View.GONE);

        }



    }
}

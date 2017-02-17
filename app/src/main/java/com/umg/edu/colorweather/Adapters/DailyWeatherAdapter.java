package com.umg.edu.colorweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umg.edu.colorweather.Day;
import com.umg.edu.colorweather.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SAMSUNG on 16/11/2016.
 */
public class DailyWeatherAdapter extends BaseAdapter {

    ArrayList<Day> days;
    Context context;

    public DailyWeatherAdapter(ArrayList<Day> days, Context context) {
        this.days = days;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(days == null){
            return 0;
        }else{
            return days.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Day day = days.get(position);

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.daily_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dayTitle = (TextView) convertView.findViewById(R.id.dailyListTitle);
            viewHolder.dayRainProbability = (TextView) convertView.findViewById(R.id.dailyListProbability);
            viewHolder.dayDescription = (TextView) convertView.findViewById(R.id.dailyListDescription);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dayTitle.setText(day.getDayName());
        viewHolder.dayDescription.setText(day.getWeatherDescription());
        viewHolder.dayRainProbability.setText(day.getRainProbability());
        return convertView;
    }

    static class ViewHolder{
        TextView dayTitle;
        TextView dayDescription;
        TextView dayRainProbability;
    }


}

package com.umg.edu.colorweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umg.edu.colorweather.Hour;
import com.umg.edu.colorweather.R;

import java.util.ArrayList;

/**
 * Created by SAMSUNG on 17/11/2016.
 */
public class HourlyWeatherAdapter extends BaseAdapter {

    ArrayList<Hour> hours;
    Context context;

    public HourlyWeatherAdapter(ArrayList<Hour> hours, Context context) {
        this.hours = hours;
        this.context = context;
    }

    @Override
    public int getCount() {

        if(hours == null){
            return 0;
        }else{
            return hours.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return hours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Hour hour = hours.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.hourly_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.hourlyTitleTextView);
            viewHolder.description = (TextView) convertView.findViewById(R.id.hourlyDescriptionTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.description.setText(hour.getWeatherDescription());
        viewHolder.title.setText(hour.getTitle());

        return convertView;
    }

    static class ViewHolder{
        TextView title;
        TextView description;
    }

}

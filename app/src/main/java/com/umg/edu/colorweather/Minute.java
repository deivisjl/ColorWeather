package com.umg.edu.colorweather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SAMSUNG on 17/11/2016.
 */
public class Minute implements Parcelable {

    private String title;
    private String minuteWeatherDescription;

    public Minute() {
    }

    protected Minute(Parcel in) {
        title = in.readString();
        minuteWeatherDescription = in.readString();
    }

    public static final Creator<Minute> CREATOR = new Creator<Minute>() {
        @Override
        public Minute createFromParcel(Parcel in) {
            return new Minute(in);
        }

        @Override
        public Minute[] newArray(int size) {
            return new Minute[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMinuteWeatherDescription() {
        return minuteWeatherDescription;
    }

    public void setMinuteWeatherDescription(String minuteWeatherDescription) {
        this.minuteWeatherDescription = minuteWeatherDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(minuteWeatherDescription);
    }
}

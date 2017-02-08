package com.sam_nguyen.atmstored.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samnguyen on 2/9/17.
 */

public final class AtmMarker implements Parcelable {

    private final String displayName;

    private final double distance;

    private final double latitude;

    private final double longitude;


    public AtmMarker(String displayName, double distance, double latitude, double longitude) {
        this.displayName = displayName;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDistance() {
        return distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeDouble(this.distance);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected AtmMarker(Parcel in) {
        this.displayName = in.readString();
        this.distance = in.readDouble();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Creator<AtmMarker> CREATOR = new Creator<AtmMarker>() {
        @Override
        public AtmMarker createFromParcel(Parcel source) {
            return new AtmMarker(source);
        }

        @Override
        public AtmMarker[] newArray(int size) {
            return new AtmMarker[size];
        }
    };
}

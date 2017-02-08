package com.sam_nguyen.atmstored.data.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.sam_nguyen.atmstored.data.AtmMarker;

import java.util.List;

/**
 * Created by samnguyen on 2/9/17.
 */

public class AtmMarkersParcelable implements Parcelable {

    private List<AtmMarker> atmMarkers;

    public AtmMarkersParcelable(List<AtmMarker> atmMarkers) {
        this.atmMarkers = atmMarkers;
    }

    public List<AtmMarker> getAtmMarkers() {
        return atmMarkers;
    }

    public void setAtmMarkers(List<AtmMarker> atmMarkers) {
        this.atmMarkers = atmMarkers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.atmMarkers);
    }

    protected AtmMarkersParcelable(Parcel in) {
        this.atmMarkers = in.createTypedArrayList(AtmMarker.CREATOR);
    }

    public static final Parcelable.Creator<AtmMarkersParcelable> CREATOR = new Parcelable.Creator<AtmMarkersParcelable>() {
        @Override
        public AtmMarkersParcelable createFromParcel(Parcel source) {
            return new AtmMarkersParcelable(source);
        }

        @Override
        public AtmMarkersParcelable[] newArray(int size) {
            return new AtmMarkersParcelable[size];
        }
    };
}

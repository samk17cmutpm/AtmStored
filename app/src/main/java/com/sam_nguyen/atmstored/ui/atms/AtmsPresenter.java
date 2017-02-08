package com.sam_nguyen.atmstored.ui.atms;

import android.location.Location;
import android.util.Log;

import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.data.AtmMarker;
import com.sam_nguyen.atmstored.data.source.AtmsDataSource;
import com.sam_nguyen.atmstored.data.source.AtmsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AtmsPresenter implements AtmsContract.Presenter {

    private static final String TAG = AtmsPresenter.class.getName();

    private final AtmsRepository repository;

    private final AtmsContract.View view;

    public AtmsPresenter(AtmsRepository repository, AtmsContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        this.repository = repository;
    }

    @Override
    public void loadAtms() {
        view.showIndicator(true);
        repository.getAtms(new AtmsDataSource.LoadAtmsCallBack() {
            @Override
            public void onAtmsLoaded(List<Atm> atms) {
                view.showAtms(atms);
                view.showIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                view.showNoAtms();
                view.showIndicator(false);
            }
        });
    }

    @Override
    public void refreshAtms() {
        view.showIndicator(true);

        repository.getAtms(new AtmsDataSource.LoadAtmsCallBack() {
            @Override
            public void onAtmsLoaded(List<Atm> atms) {
                view.refreshAtms(atms);
                view.showIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                view.showIndicator(false);
            }
        });
    }

    @Override
    public void saveAtm(Atm atm) {
        repository.saveAtm(atm);
    }

    @Override
    public void caculateDistances(Location location, List<Atm> atms) {

        List<AtmMarker> atmMarkers = new ArrayList<>();

        boolean stopSearching = false;

        int Meter_To_Kilometer = 1000;

        for (double distance : AtmRadius.LIST) {

            if (stopSearching)
                break;

            for (Atm atm : atms) {
                Location tempLocation = new Location("Temp_Location");
                tempLocation.setLatitude(atm.getLatitude());
                tempLocation.setLongitude(atm.getLongitude());
                float distanceInMeters = location.distanceTo(tempLocation);

                if (distanceInMeters <= distance) {
                    atmMarkers.add(new AtmMarker(
                            atm.getName() + ", " + atm.getAddress(),
                            distance / Meter_To_Kilometer,
                            atm.getLatitude(),
                            atm.getLongitude())
                    );
                    stopSearching = true;
                }

            }
        }

        if (atmMarkers.isEmpty()) {
            view.atmMarkersNotFound();
        } else {
            view.showAtmMarkers(atmMarkers);
        }

    }

    @Override
    public void start() {

    }
}

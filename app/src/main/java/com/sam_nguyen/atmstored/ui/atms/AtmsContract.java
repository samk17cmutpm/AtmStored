package com.sam_nguyen.atmstored.ui.atms;

import android.location.Location;

import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.data.AtmMarker;
import com.sam_nguyen.atmstored.ui.base.BasePresenter;
import com.sam_nguyen.atmstored.ui.base.BaseView;

import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public interface AtmsContract {

    interface View extends BaseView<Presenter> {

        void initializeViewComponents();

        void showUI();

        void showIndicator(boolean active);

        void showAtms(List<Atm> atms);

        void showNoAtms();

        void refreshAtms(List<Atm> atms);

        void navigateToAddNewAtm();

        void askForLocationPermission();

        void atmMarkersNotFound();

        void showAtmMarkers(List<AtmMarker> markers);
    }

    interface Presenter extends BasePresenter {
        void loadAtms();
        void refreshAtms();
        void saveAtm(Atm atm);
        void caculateDistances(Location location, List<Atm> atms);
    }
}

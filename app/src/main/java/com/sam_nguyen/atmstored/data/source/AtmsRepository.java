package com.sam_nguyen.atmstored.data.source;

import android.database.sqlite.SQLiteDatabase;

import com.sam_nguyen.atmstored.data.Atm;

import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AtmsRepository implements AtmsDataSource {

    private static AtmsRepository INSTANCE = null;

    private final AtmsDataSource atmsLocalDataSource;

    private AtmsRepository(AtmsDataSource atmsLocalDataSource) {
        this.atmsLocalDataSource = atmsLocalDataSource;
    }

    public static AtmsRepository getInstance(AtmsDataSource atmsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AtmsRepository(atmsLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getAtms(final LoadAtmsCallBack callBack) {
        atmsLocalDataSource.getAtms(new LoadAtmsCallBack() {
            @Override
            public void onAtmsLoaded(List<Atm> atms) {

                callBack.onAtmsLoaded(atms);

            }

            @Override
            public void onDataNotAvailable() {

                callBack.onDataNotAvailable();

            }
        });
    }

    @Override
    public void getAtm(String atmId, GetAtmsCallBack callBack) {

    }

    @Override
    public void saveAtm(Atm atm) {
        atmsLocalDataSource.saveAtm(atm);
    }

    @Override
    public void deleteAllAtms() {

    }

    @Override
    public void deleteAtm(String atmId) {

    }
}

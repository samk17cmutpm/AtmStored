package com.sam_nguyen.atmstored.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.data.source.AtmsDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AtmsLocalDataSource implements AtmsDataSource {

    private static AtmsLocalDataSource INSTANCE;

    private AtmStoredDbHelper atmStoredDbHelper;

    public AtmsLocalDataSource(Context context) {
        this.atmStoredDbHelper = new AtmStoredDbHelper(context);
    }

    public static AtmsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AtmsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getAtms(LoadAtmsCallBack callBack) {
        List<Atm> atms = new ArrayList<>();

        SQLiteDatabase db = atmStoredDbHelper.getReadableDatabase();

        String[] projection = {
                AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ATM_ID,
                AtmsPersistenceContract.AtmEntry.COLUMN_NAME_NAME,
                AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ADDRESS,
                AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LATITUDE,
                AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LONGTITUDE
        };

        Cursor cursor = db.query(
                AtmsPersistenceContract.AtmEntry.TABLE_NAME, projection, null, null, null, null, null
        );

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String atmId = cursor.getString(cursor.getColumnIndexOrThrow(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ATM_ID));
                String atmName = cursor.getString(cursor.getColumnIndexOrThrow(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_NAME));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ADDRESS));
                float latitude = cursor.getFloat(cursor.getColumnIndexOrThrow(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LATITUDE));
                float longtitude = cursor.getFloat(cursor.getColumnIndexOrThrow(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LONGTITUDE));

                Atm atm = new Atm(atmId, atmName, address, latitude, longtitude);
                atms.add(atm);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (atms.isEmpty()) {
            callBack.onDataNotAvailable();
        } else {
            callBack.onAtmsLoaded(atms);
        }

    }

    @Override
    public void getAtm(String atmId, GetAtmsCallBack callBack) {

    }

    @Override
    public void saveAtm(Atm atm) {
        SQLiteDatabase db = atmStoredDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ATM_ID, atm.getId());
        values.put(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_NAME, atm.getName());
        values.put(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ADDRESS, atm.getAddress());
        values.put(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LATITUDE, atm.getLatitude());
        values.put(AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LONGTITUDE, atm.getLongitude());

        db.insert(AtmsPersistenceContract.AtmEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void deleteAllAtms() {

    }

    @Override
    public void deleteAtm(String atmId) {

    }
}

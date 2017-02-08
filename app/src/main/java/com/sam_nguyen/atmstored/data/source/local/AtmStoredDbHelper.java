package com.sam_nguyen.atmstored.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AtmStoredDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "AtmStored.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES_ATMS =
            "CREATE TABLE " + AtmsPersistenceContract.AtmEntry.TABLE_NAME + " (" +
                    AtmsPersistenceContract.AtmEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ATM_ID + TEXT_TYPE + COMMA_SEP +
                    AtmsPersistenceContract.AtmEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    AtmsPersistenceContract.AtmEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LATITUDE + REAL_TYPE + COMMA_SEP +
                    AtmsPersistenceContract.AtmEntry.COLUMN_NAME_LONGTITUDE + REAL_TYPE +
                    " )";

    public AtmStoredDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("=======>", "=========>");

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_ATMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AtmsPersistenceContract.AtmEntry.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);

    }
}

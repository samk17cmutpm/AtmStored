package com.sam_nguyen.atmstored.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by samnguyen on 2/8/17.
 */

public final class AtmsPersistenceContract {

    private AtmsPersistenceContract() {}

    public static abstract class AtmEntry implements BaseColumns {
        public static final String TABLE_NAME = "atm";

        public static final String COLUMN_NAME_ATM_ID = "id";

        public static final String COLUMN_NAME_NAME = "name";

        public static final String COLUMN_NAME_ADDRESS = "address";

        public static final String COLUMN_NAME_LATITUDE = "latitude";

        public static final String COLUMN_NAME_LONGTITUDE= "longitude";
    }
}

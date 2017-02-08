package com.sam_nguyen.atmstored.data.source;

import com.sam_nguyen.atmstored.data.Atm;

import java.util.List;

/**
 * Created by samnguyen on 2/8/17.
 */

public interface AtmsDataSource {

    interface LoadAtmsCallBack {

        void onAtmsLoaded(List<Atm> atms);

        void onDataNotAvailable();

    }

    interface GetAtmsCallBack {

        void onAtmLoaded(Atm atm);

        void onDataNotAvailable();

    }

    void getAtms(LoadAtmsCallBack callBack);

    void getAtm(String atmId, GetAtmsCallBack callBack);

    void saveAtm(Atm atm);

    void deleteAllAtms();

    void deleteAtm(String atmId);
}

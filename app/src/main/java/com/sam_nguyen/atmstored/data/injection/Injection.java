package com.sam_nguyen.atmstored.data.injection;

import android.content.Context;

import com.sam_nguyen.atmstored.data.source.AtmsRepository;
import com.sam_nguyen.atmstored.data.source.local.AtmsLocalDataSource;

/**
 * Created by samnguyen on 2/8/17.
 */

public class Injection {
    public static AtmsRepository provideAtmsRespository(Context context) {
        return AtmsRepository.getInstance(AtmsLocalDataSource.getInstance(context));
    }
}

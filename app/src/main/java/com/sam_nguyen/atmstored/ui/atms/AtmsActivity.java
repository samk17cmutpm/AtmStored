package com.sam_nguyen.atmstored.ui.atms;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.injection.Injection;
import com.sam_nguyen.atmstored.data.source.AtmsRepository;
import com.sam_nguyen.atmstored.data.source.local.AtmStoredDbHelper;
import com.sam_nguyen.atmstored.data.source.local.AtmsLocalDataSource;
import com.sam_nguyen.atmstored.ui.base.BaseActivity;
import com.sam_nguyen.atmstored.utils.ActivityUtils;
import com.sam_nguyen.atmstored.utils.GPSTracker;

public class AtmsActivity extends BaseActivity {

    private AtmsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atms);

        AtmsFragment fragment =
                (AtmsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = AtmsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame
            );
        }

        presenter = new AtmsPresenter(Injection.provideAtmsRespository(getApplicationContext()), fragment);


    }


}

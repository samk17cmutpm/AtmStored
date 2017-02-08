package com.sam_nguyen.atmstored.ui.add_new_atm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.injection.Injection;
import com.sam_nguyen.atmstored.ui.base.BaseActivity;
import com.sam_nguyen.atmstored.utils.ActivityUtils;

public class AddNewAtmActivity extends BaseActivity {

    private AddNewAtmContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_atm);

        AddNewAtmFragment fragment =
                (AddNewAtmFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = AddNewAtmFragment.newInstance();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame
            );
        }

        presenter = new AddNewAtmPresenter(Injection.provideAtmsRespository(getApplicationContext()), fragment);
    }

}

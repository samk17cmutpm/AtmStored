package com.sam_nguyen.atmstored.ui.add_new_atm;

import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.data.source.AtmsRepository;

/**
 * Created by samnguyen on 2/8/17.
 */

public class AddNewAtmPresenter implements AddNewAtmContract.Presenter {

    private static final String TAG = AddNewAtmPresenter.class.getName();

    private final AtmsRepository repository;

    private final AddNewAtmContract.View view;

    public AddNewAtmPresenter(AtmsRepository repository, AddNewAtmContract.View view) {
        this.repository = repository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void saveAtm(Atm atm) {
        repository.saveAtm(atm);
        view.navigateBackToAtms();
    }

    @Override
    public void start() {

    }
}

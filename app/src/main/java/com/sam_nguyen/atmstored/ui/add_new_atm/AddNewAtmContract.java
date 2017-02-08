package com.sam_nguyen.atmstored.ui.add_new_atm;

import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.ui.base.BasePresenter;
import com.sam_nguyen.atmstored.ui.base.BaseView;

/**
 * Created by samnguyen on 2/8/17.
 */

public interface AddNewAtmContract {

    interface View extends BaseView<Presenter> {

        void initializeViewComponents();

        void showUI();

        void navigateBackToAtms();

        boolean validateInputs();
    }


    interface Presenter extends BasePresenter {
        void saveAtm(Atm atm);
    }
}

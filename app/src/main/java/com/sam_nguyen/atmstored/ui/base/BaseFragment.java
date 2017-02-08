package com.sam_nguyen.atmstored.ui.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by samnguyen on 2/8/17.
 */

public abstract class BaseFragment extends Fragment {
    public void showMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }



}

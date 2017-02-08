package com.sam_nguyen.atmstored.ui.add_new_atm;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.ui.atms.AtmsActivity;
import com.sam_nguyen.atmstored.ui.base.BaseFragment;
import com.sam_nguyen.atmstored.utils.ToolbarUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewAtmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewAtmFragment extends BaseFragment implements AddNewAtmContract.View, View.OnClickListener{

    private AddNewAtmContract.Presenter presenter;

    private View root;

    private Toolbar toolbar;

    private Activity activity;

    private EditText edAtmName;

    private EditText edAtmAddress;

    private EditText edAtmLatitude;

    private EditText edAtmLongtitude;

    private Button btAddNewAtm;

    public AddNewAtmFragment() {
        // Required empty public constructor
    }

    public static AddNewAtmFragment newInstance() {
        AddNewAtmFragment fragment = new AddNewAtmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_new_atm, container, false);

        initializeViewComponents();

        showUI();

        return root;
    }

    @Override
    public void initializeViewComponents() {

        activity = getActivity();

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        edAtmName = (EditText) root.findViewById(R.id.ed_atm_name);

        edAtmAddress = (EditText) root.findViewById(R.id.ed_atm_address);

        edAtmLatitude = (EditText) root.findViewById(R.id.ed_atm_latitude);

        edAtmLongtitude = (EditText) root.findViewById(R.id.ed_atm_longitude);

        btAddNewAtm = (Button) root.findViewById(R.id.bt_add_new_atm);

        btAddNewAtm.setOnClickListener(this);

    }

    @Override
    public void showUI() {
        ToolbarUtils.initialize(toolbar, activity, R.string.title_activity_add_new_atm, R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });

        edAtmLongtitude.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.submit || id == EditorInfo.IME_NULL) {
                    if (validateInputs())
                        presenter.saveAtm(new Atm(
                                edAtmName.getText().toString(),
                                edAtmAddress.getText().toString(),
                                Float.parseFloat(edAtmLatitude.getText().toString()),
                                Float.parseFloat(edAtmLongtitude.getText().toString())
                        ));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void navigateBackToAtms() {
        Intent intent = new Intent(activity, AtmsActivity.class);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public boolean validateInputs() {

        edAtmName.setError(null);
        edAtmAddress.setError(null);
        edAtmLatitude.setError(null);
        edAtmLongtitude.setError(null);

        String atmName = edAtmName.getText().toString();
        String atmAddress = edAtmAddress.getText().toString();
        String atmLatitude = edAtmLatitude.getText().toString();
        String atmLongitude = edAtmLongtitude.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(atmLongitude)) {
            this.edAtmLongtitude.setError(getString(R.string.error_not_empty));
            focusView = this.edAtmLongtitude;
            cancel = true;
        }

        if (TextUtils.isEmpty(atmLatitude)) {
            this.edAtmLatitude.setError(getString(R.string.error_not_empty));
            focusView = this.edAtmLatitude;
            cancel = true;
        }

        if (TextUtils.isEmpty(atmAddress)) {
            this.edAtmAddress.setError(getString(R.string.error_not_empty));
            focusView = this.edAtmAddress;
            cancel = true;
        }

        if (TextUtils.isEmpty(atmName)) {
            this.edAtmName.setError(getString(R.string.error_not_empty));
            focusView = this.edAtmName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
        }
        return !cancel;
    }

    @Override
    public void setPresenter(AddNewAtmContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_new_atm:
                if (validateInputs())
                    presenter.saveAtm(new Atm(
                            edAtmName.getText().toString(),
                            edAtmAddress.getText().toString(),
                            Float.parseFloat(edAtmLatitude.getText().toString()),
                            Float.parseFloat(edAtmLongtitude.getText().toString())
                    ));
                break;
            default:
                break;
        }
    }
}

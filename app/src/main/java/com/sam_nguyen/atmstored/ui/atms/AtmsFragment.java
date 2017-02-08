package com.sam_nguyen.atmstored.ui.atms;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.Atm;
import com.sam_nguyen.atmstored.data.AtmMarker;
import com.sam_nguyen.atmstored.data.parcelable.AtmMarkersParcelable;
import com.sam_nguyen.atmstored.data.parcelable.ParcelableKey;
import com.sam_nguyen.atmstored.ui.add_new_atm.AddNewAtmActivity;
import com.sam_nguyen.atmstored.ui.base.BaseFragment;
import com.sam_nguyen.atmstored.ui.map.MapsActivity;
import com.sam_nguyen.atmstored.utils.GPSTracker;
import com.sam_nguyen.atmstored.utils.SpacesItemDecoration;
import com.sam_nguyen.atmstored.utils.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

public class AtmsFragment extends BaseFragment implements AtmsContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private AtmsContract.Presenter presenter;

    private View root;

    private Toolbar toolbar;

    private RelativeLayout rlAtmsList;

    private RelativeLayout rlAtmsEmpty;

    private RecyclerView recyclerViewAtms;

    private SwipeRefreshLayout swipeRefreshLayout;

    private AtmsAdapter atmsAdapter;

    private LinearLayoutManager layoutManager;

    private List<Atm> atms;

    private Activity activity;

    private FloatingActionButton fabAddAtm;

    private PermissionListener accessLocationPermission;

    private PermissionListener permissionListener;

    private GPSTracker gpsTracker;

    private Location currentLocation;

    public AtmsFragment() {
        // Required empty public constructor
    }

    public static AtmsFragment newInstance() {
        AtmsFragment fragment = new AtmsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (atms.isEmpty()) {
                    showMessage(R.string.no_atms_data_to_search);
                    return true;
                }

                if (currentLocation == null) {
                    showMessage(R.string.restart_app_to_get_correct_location);
                    break;
                }

                presenter.caculateDistances(currentLocation, atms);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_atms, container, false);

        initializeViewComponents();

        showUI();

        askForLocationPermission();

        presenter.loadAtms();

        return root;
    }

    @Override
    public void initializeViewComponents() {

        activity = getActivity();

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        fabAddAtm = (FloatingActionButton) activity.findViewById(R.id.fab_add_atm);
        fabAddAtm.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(activity);

        rlAtmsList = (RelativeLayout) root.findViewById(R.id.atms_list);

        rlAtmsEmpty = (RelativeLayout) root.findViewById(R.id.atms_empty);

        recyclerViewAtms = (RecyclerView) root.findViewById(R.id.recycler_view_atms);

        recyclerViewAtms.setHasFixedSize(true);
        recyclerViewAtms.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        SpacesItemDecoration decoration = new SpacesItemDecoration(1);
        recyclerViewAtms.addItemDecoration(decoration);

        atms = new ArrayList<>();
    }

    @Override
    public void showUI() {

        ToolbarUtils.initialize(toolbar, activity, R.string.app_name, 0);

        atmsAdapter = new AtmsAdapter(atms, getContext());
        recyclerViewAtms.setAdapter(atmsAdapter);

    }

    @Override
    public void showIndicator(final boolean active) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showAtms(List<Atm> atms) {
        this.atms.addAll(atms);
        atmsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoAtms() {
        rlAtmsList.setVisibility(View.GONE);
        rlAtmsEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshAtms(List<Atm> atms) {
        this.atms.clear();
        this.atms.addAll(atms);
        atmsAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToAddNewAtm() {
        Intent intent = new Intent(activity, AddNewAtmActivity.class);
        startActivity(intent);
    }

    @Override
    public void askForLocationPermission() {


        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {

                gpsTracker = new GPSTracker(activity);

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    if (latitude == 0 && longitude == 0) {
                        showMessage(R.string.restart_app_to_get_correct_location);
                    } else {
                        currentLocation = new Location("Current Location");
                        currentLocation.setLatitude(latitude);
                        currentLocation.setLongitude(longitude);
                    }
                } else {
                    gpsTracker.showSettingsAlert();
                }

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        };

        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(activity)
                        .withTitle(R.string.access_fine_location_title)
                        .withMessage(R.string.access_fine_location_message)
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.drawable.ic_add)
                        .build();

        accessLocationPermission = new CompositePermissionListener(permissionListener, dialogOnDeniedPermissionListener);

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(accessLocationPermission)
                .check();
    }

    @Override
    public void atmMarkersNotFound() {
        showMessage(R.string.atm_no_result);
    }

    @Override
    public void showAtmMarkers(List<AtmMarker> markers) {
        showMessage(markers.size() + " Atm(s) have been found ");

        AtmMarkersParcelable atmMarkersParcelable = new AtmMarkersParcelable(markers);
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putExtra(ParcelableKey.ATM_MARKERS, atmMarkersParcelable);
        startActivity(intent);
    }

    @Override
    public void setPresenter(AtmsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        presenter.refreshAtms();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_atm:
                navigateToAddNewAtm();
                break;
            default:
                break;
        }
    }

    @Override
    public void showMessage(int message) {
        super.showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }
}

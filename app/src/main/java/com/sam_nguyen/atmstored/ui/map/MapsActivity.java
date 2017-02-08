package com.sam_nguyen.atmstored.ui.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sam_nguyen.atmstored.R;
import com.sam_nguyen.atmstored.data.AtmMarker;
import com.sam_nguyen.atmstored.data.parcelable.AtmMarkersParcelable;
import com.sam_nguyen.atmstored.data.parcelable.ParcelableKey;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String TAG = MapsActivity.class.getName();

    private static final int ZOOM_CAMERA = 18;

    private static final String KML = " Kms";

    private List<AtmMarker> atmMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        AtmMarkersParcelable atmMarkersParcelable = (AtmMarkersParcelable) getIntent().getParcelableExtra(ParcelableKey.ATM_MARKERS);
        atmMarkers = atmMarkersParcelable.getAtmMarkers();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkersToMap(atmMarkers);
    }

    private void addMarkersToMap(List<AtmMarker> atmMarkers) {
        List<Marker> markers = new ArrayList<>();
        for (AtmMarker atmMarker : atmMarkers) {

            LatLng latLng = new LatLng(atmMarker.getLatitude(), atmMarker.getLongitude());

            BitmapDescriptor bitmapMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

            markers.add(mMap.addMarker(new MarkerOptions().position(latLng).title(atmMarker.getDisplayName())
                    .snippet(atmMarker.getDistance() + KML).icon(bitmapMarker)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            CameraUpdate zoom=CameraUpdateFactory.zoomTo(ZOOM_CAMERA);

            mMap.animateCamera(zoom);

        }
    }


}

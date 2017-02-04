package com.stewart.loyaltypoints;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import static com.stewart.loyaltypoints.R.id.map;


/**
 * Created by stewart on 17/01/2017.
 */

public class googleMapsFragment extends Fragment implements OnMapReadyCallback {
    //LOCATIONS LATLNG
    private static final LatLng PORTLAND = new LatLng(50.798217,-1.1013153);
    private static final LatLng THEHUB = new LatLng(50.797831, -1.098707);
    private static final LatLng LIBRARY = new LatLng(50.793659,-1.0999547);
    private static final LatLng PARK = new LatLng(50.7976403,-1.0962662);
    private static final LatLng SU = new LatLng(50.794473,-1.0993537);
    private static final LatLng ELDON = new LatLng(50.794731,-1.0931314);
    private static final LatLng STARBUCKS = new LatLng(50.794473,-1.0993537);
    private static final LatLng ANGLESEA = new LatLng(50.7977465,-1.0986508);
    private static final LatLng STGEORGE = new LatLng(50.7923127,-1.1022768);
    private static final LatLng STANDREW = new LatLng(50.7958415,-1.0969598);
    private static final LatLng COCO = new LatLng(51.2706229,-1.2104218);
    private static final LatLng Portsmouth = new LatLng(50.798217,-1.1013153);


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    public googleMapsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.googlefragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.googlemap);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        
        
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(PORTLAND).title("Portland").snippet("08:30 - 18:30"));
        googleMap.addMarker(new MarkerOptions().position(THEHUB).title("The Hub").snippet("07:30 - 17:00"));
        googleMap.addMarker(new MarkerOptions().position(LIBRARY).title("The Library").snippet("08:00 - 20:00"));
        googleMap.addMarker(new MarkerOptions().position(PARK).title("Park").snippet("08:30 - 14:30"));
        googleMap.addMarker(new MarkerOptions().position(SU).title("Student Union").snippet("10:00 - 00:00"));
        googleMap.addMarker(new MarkerOptions().position(ELDON).title("Eldon").snippet("08:30 - 14:30"));
        googleMap.addMarker(new MarkerOptions().position(STARBUCKS).title("Starbucks").snippet("09:30 - 16:00"));
        googleMap.addMarker(new MarkerOptions().position(ANGLESEA).title("Anglesea").snippet("08:30 - 14:00"));
        googleMap.addMarker(new MarkerOptions().position(STGEORGE).title("St Georges").snippet("10:00 - 15:00"));
        googleMap.addMarker(new MarkerOptions().position(STANDREW).title("St Andrews").snippet("08:00 - 16:00"));
        googleMap.addMarker(new MarkerOptions().position(COCO).title("Cafe Coco").snippet("08:30 - 15:00"));

        mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(Portsmouth , 15.0f) );
    }
}

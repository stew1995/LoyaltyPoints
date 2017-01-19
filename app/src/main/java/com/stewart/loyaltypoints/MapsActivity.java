package com.stewart.loyaltypoints;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.stewart.loyaltypoints.R.id.view;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ListView l;
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

    //Marker
    private Marker mPortland, mTheHub, mLibrary, mPark, mSU, mEldon, mStarbucks, mAnglesea,
            mStGeorges, mStAndrew, mCoco;





    protected void onCreate(Bundle savedInstanceState, LayoutInflater inflater, ViewGroup container) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        // Add a marker in Sydney and move the camera
        mPortland = mMap.addMarker(new MarkerOptions().position(PORTLAND).title("Portland"));
        mTheHub = mMap.addMarker(new MarkerOptions().position(THEHUB).title("Portland"));
        mLibrary = mMap.addMarker(new MarkerOptions().position(LIBRARY).title("Portland"));
        mPark = mMap.addMarker(new MarkerOptions().position(PARK).title("Portland"));
        mSU = mMap.addMarker(new MarkerOptions().position(SU).title("Portland"));
        mEldon = mMap.addMarker(new MarkerOptions().position(ELDON).title("Portland"));
        mStarbucks = mMap.addMarker(new MarkerOptions().position(STARBUCKS).title("Portland"));
        mAnglesea = mMap.addMarker(new MarkerOptions().position(ANGLESEA).title("Portland"));
        mStGeorges = mMap.addMarker(new MarkerOptions().position(STGEORGE).title("Portland"));
        mStAndrew = mMap.addMarker(new MarkerOptions().position(STANDREW).title("Portland"));
        mCoco = mMap.addMarker(new MarkerOptions().position(COCO).title("Portland"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PORTLAND, 15));

        mMap.setOnMarkerClickListener(this);


    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}

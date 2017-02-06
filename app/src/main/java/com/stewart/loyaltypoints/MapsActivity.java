package com.stewart.loyaltypoints;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static com.google.android.gms.analytics.internal.zzy.f;

import com.stewart.loyaltypoints.googleMapsFragment;

public class MapsActivity extends FragmentActivity  {

    private ListView listView;
    private Context ctx;
    private GoogleMap GoogleMap;
    googleMapsFragment googleMapsFragment;

    //Locations
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
    View myFragementView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        }



}

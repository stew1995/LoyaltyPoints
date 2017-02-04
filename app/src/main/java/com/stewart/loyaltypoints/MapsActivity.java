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

public class MapsActivity extends FragmentActivity  {

    private ListView listView;
    private Context ctx;
    GoogleMap GoogleMap;
    private MapView mGoogleMap;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mGoogleMap = (MapView) findViewById(R.id.googlemap);


        listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Gets the name thats put into the listview
                TextView tv = (TextView) view.findViewById(R.id.tvMapsName);
                String location = tv.getText().toString();
                if(location == "Portland") {
                    GoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(THEHUB, 15.0f ));
                } else if (location == "Dennis Schema (The Hub)") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(THEHUB, 15.0f ) );
                } else if (location == "The Library") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(LIBRARY, 15.0f ) );
                } else if (location == "Park") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(PARK, 15.0f ) );
                } else if (location == "Student Union (The Waterhole)") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(SU, 15.0f ) );
                } else if (location == "Eldon") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(ELDON, 15.0f ) );
                } else if (location == "Starbucks in Student Union") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(STARBUCKS, 15.0f ) );
                } else if (location == "Anglesea") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(ANGLESEA, 15.0f ) );
                } else if (location == "St Georges Coffee Shop") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(STGEORGE, 15.0f ) );
                } else if (location == "St Andrews Court Café") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(STANDREW, 15.0f ) );
                } else if (location == "Café Coco") {
                    GoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(COCO, 15.0f ) );
                }

            }
        });
        }



}

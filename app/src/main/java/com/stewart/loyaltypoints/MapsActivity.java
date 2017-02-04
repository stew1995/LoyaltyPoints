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

public class MapsActivity extends FragmentActivity {

    private ListView listView;
    private Context ctx;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Gets the name thats put into the listview
                TextView tv = (TextView) view.findViewById(R.id.tvMapsName);
                Toast.makeText(getApplicationContext(), tv.getText().toString(), Toast.LENGTH_SHORT).show();

                TextView textview = (TextView) view.findViewById(R.id.tvMapsTime);
                Toast.makeText(getApplicationContext(), textview.getText().toString(), Toast.LENGTH_SHORT).show();

                TextView textview2 = (TextView) view.findViewById(R.id.tvMapsLocation);
                Toast.makeText(getApplicationContext(), textview2.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        }

}

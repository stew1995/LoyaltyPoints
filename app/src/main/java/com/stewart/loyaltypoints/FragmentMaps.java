package com.stewart.loyaltypoints;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stewart.loyaltypoints.models.MapsList;

import java.util.ArrayList;


/**
 * Created by stewart on 17/01/2017.
 */

public class FragmentMaps extends Fragment {
    private DatabaseReference databaseReference;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("PreOrders");

        View view = inflater.inflate(R.layout.maps_fragment, container, false);

        final ArrayList<MapsList> locationsList = new ArrayList<MapsList>();
        MapsList l1 = new MapsList("Portland", "Portland St,\nPO1 3AH", "08:30 - 18:30");
        MapsList l2 = new MapsList("Dennis Schema (The Hub)", "Burnaby Road,\nPO1 3FX", "07:30 - 17:00");
        MapsList l3 = new MapsList("The Library", "Cambridge Rd,\nPO1 2ST", "08:00 - 20:00");
        MapsList l4 = new MapsList("Park", "King Henry 1 Street,\nPO1 2DZ", "08:30 - 14:30");
        MapsList l5 = new MapsList("Student Union (The Waterhole)", "Student Centre,\nCambridge Road,\nPO1 2EF", "10:00 - 00:00");
        MapsList l6 = new MapsList("Eldon", "Winston Churchill Avenue,\nPO1 2DJ", "08:30 - 14:30");
        MapsList l7 = new MapsList("Starbucks in Student Union", "Student Centre,\n" + "Cambridge Road,\nPO1 2EF", "09:30 - 16:00");
        MapsList l8 = new MapsList("Anglesea", "Anglesea Road,\nPO1 3DJ", "08:30 - 14:00");
        MapsList l9 = new MapsList("St Georges Coffee Shop", "141 High Street,\nPO1 2HY", "10:00 - 15:00");
        MapsList l10 = new MapsList("St Andrews Court Café", "St Michael's Rd,\nPO1 2PR", "08:00 - 16:30");
        MapsList l11 = new MapsList("Café Coco", "Cambridge Rd,\nPO1 2EF", "08:30 - 15:00");

        locationsList.add(l1);
        locationsList.add(l2);
        locationsList.add(l3);
        locationsList.add(l4);
        locationsList.add(l5);
        locationsList.add(l6);
        locationsList.add(l7);
        locationsList.add(l8);
        locationsList.add(l9);
        locationsList.add(l10);
        locationsList.add(l11);
        //Get layout for name, location and time of each cafe
        BindDictionary<MapsList> dict = new BindDictionary<MapsList>();
        dict.addStringField(R.id.tvMapsName, new StringExtractor<MapsList>() {
            @Override
            public String getStringValue(MapsList item, int position) {
                return item.getName();
            }
        });


        dict.addStringField(R.id.tvMapsLocation, new StringExtractor<MapsList>() {
            @Override
            public String getStringValue(MapsList item, int position) {
                return item.getLocation();
            }
        });

        dict.addStringField(R.id.tvMapsTime, new StringExtractor<MapsList>() {
            @Override
            public String getStringValue(MapsList item, int position) {
                return item.getHours();
            }
        });



        FunDapter<MapsList> adapter = new FunDapter<>(getActivity(),
                locationsList, R.layout.map_list_layout, dict);
        ListView mapList = (ListView) view.findViewById(R.id.list_view);
        mapList.setAdapter(adapter);



        listView = (ListView)view.findViewById(R.id.list_view);

        return view;
    }
}

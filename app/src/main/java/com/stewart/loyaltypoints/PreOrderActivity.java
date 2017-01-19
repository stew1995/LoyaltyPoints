package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.*;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreOrderActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference mRef;
    private com.firebase.ui.database.FirebaseListAdapter listAdapter;

    //private ArrayList<String> mItems = new ArrayList<>();



   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);


       mRef = FirebaseDatabase.getInstance().getReference().child("Items").child("TypeOfItems");

       listView = (ListView) findViewById(R.id.itemsListView);

        listAdapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, mRef) {


            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

       listView.setAdapter(listAdapter);

/*
       final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);

       listView.setAdapter(adapter);
        //This is needed for the pre order
       //use removed and added
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);

                mItems.add(value);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/
    }

    }




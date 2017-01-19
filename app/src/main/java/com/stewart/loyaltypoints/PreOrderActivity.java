package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreOrderActivity extends AppCompatActivity {

    private Spinner listView;
    private DatabaseReference databaseReference;



   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);


       listView = (Spinner) findViewById(R.id.spinner);
       databaseReference = FirebaseDatabase.getInstance().getReference();

       String Names[]={"Mango","Banana","grapes"};
       String Price[] ={"2.00", "£3.50", "£21.00"};
       String Book[] = {"HSAHJAS", "SHJGS", "DSHDGD"};
       ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.spinnerText,Names);



       listView.setAdapter(adapter);



    }



    private void getData() {
        databaseReference.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Spinner itemSpinner =  (Spinner) findViewById(R.id.spinner);
                    Items item = child.getValue(Items.class);
                    String name = item.getItemName();

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(PreOrderActivity.this,R.layout.spinner_item,name);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}

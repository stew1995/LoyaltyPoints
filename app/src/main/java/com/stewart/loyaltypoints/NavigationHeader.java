package com.stewart.loyaltypoints;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stewart.loyaltypoints.models.User;

import static android.R.attr.id;

/**
 * Created by stewart on 13/02/2017.
 */

public class NavigationHeader extends AppCompatActivity {
    FirebaseAuth mAuth;
    String mUser;
    DatabaseReference mRef;
    TextView userName, userStudentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_navigation );

        //Firebase
        mUser = mAuth.getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( mUser );
        mAuth = FirebaseAuth.getInstance();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        userName = (TextView) header.findViewById( R.id.UserNameNavigation );
        userStudentID = (TextView) header.findViewById( R.id.UserStudentNumber );

        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    String name = user.getfName() + " " + user.getlName();
                    String id = "UP"+user.getStudentID();

                    userName.setText( name );
                    userStudentID.setText( id );
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

    }
}

package com.stewart.loyaltypoints;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        userName = (TextView) findViewById( R.id.UserNameNavigation );
        userStudentID = (TextView) findViewById( R.id.UserStudentNumber );


        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);

                    //String name = user.getfName() + " " + user.getlName();
                    //String id = "UP"+user.getStudentID();

                    //userName.setText( name );
                    //userStudentID.setText( id );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

    }
}

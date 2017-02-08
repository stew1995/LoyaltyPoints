package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class TransactionsActivity extends AppCompatActivity {

    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    //Recycler View
    private RecyclerView mTransactionRecycler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_transactions );
    }
}

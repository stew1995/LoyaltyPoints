package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stewart.loyaltypoints.models.Transactions;
import com.stewart.loyaltypoints.models.TransactionsViewHolder;

public class TransactionsActivity extends AppCompatActivity {

    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    //Recycler View
    private RecyclerView mTransactionRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_transactions );

        //Firebase
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Recycler View
        mTransactionRecycler = (RecyclerView) findViewById( R.id.transaction_activity_recycler );
        mTransactionRecycler.setHasFixedSize( true );
        mTransactionRecycler.setLayoutManager( new LinearLayoutManager( this ) );


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Transactions, TransactionsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Transactions, TransactionsViewHolder>(
                Transactions.class,
                R.layout.transactions_row,
                TransactionsViewHolder.class,
                mRef.child("Users").child( mUser.getUid() ).child( "Transactions" )
        ) {
            @Override
            protected void populateViewHolder(TransactionsViewHolder viewHolder, Transactions model, int position) {
                viewHolder.setTransactionLocation( model.getOrderLocation() );
                viewHolder.setTransactionDate( model.getOrderDate() );
                viewHolder.setItemName0( model.getItem0() );
                viewHolder.setItemName1( model.getItem1() );
                viewHolder.setItemName2( model.getItem2() );
                viewHolder.setItemName3( model.getItem3() );
                viewHolder.setItemQty0( model.getItemQty0() );
                viewHolder.setItemQty1( model.getItemQty1() );
                viewHolder.setItemQty2( model.getItemQty2() );
                viewHolder.setItemQty3( model.getItemQty3() );
                //Price needs implemeting on other pages
                //viewHolder.setTransactionPrice( model.getItemPrice() );

            }
        };

        mTransactionRecycler.setAdapter( firebaseRecyclerAdapter );
    }


}

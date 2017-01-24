package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import static com.stewart.loyaltypoints.R.id.tvItemName1;
import static com.stewart.loyaltypoints.R.id.tvItemName2;
import static com.stewart.loyaltypoints.R.id.tvItemName3;
import static com.stewart.loyaltypoints.R.id.tvItemName4;
import static com.stewart.loyaltypoints.R.id.tvItemQty1;
import static com.stewart.loyaltypoints.R.id.tvItemQty2;
import static com.stewart.loyaltypoints.R.id.tvItemQty3;
import static com.stewart.loyaltypoints.R.id.tvItemQty4;

public class PreOrderDetailsActivity extends AppCompatActivity {
    //Counter for adding into HashMap
    private int counter;

    //Recycler Views
    RecyclerView mPreOrderName, mPreOrderQty;

    //Button for going back to main page
    private Button btnNextPreOrder;
    //RemoveButtons
    private Button btnRemoveItem1;
    private Button btnRemoveItem2;
    private Button btnRemoveItem3;
    private Button btnRemoveItem4;

    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    //Array Lists
    private List<String> preOrderItems, preOrderQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pre_order_details );

        //Initialise ArrayLists
        preOrderItems = new ArrayList<String>();
        preOrderQty = new ArrayList<String>();
        //Initialise Firebase
        //Each page i need to put if the user isnt signed in the go back to sign in screen

        //Initialise Views

        mPreOrderName = (RecyclerView) findViewById( R.id.rVPreOrderName );
        mPreOrderName.setHasFixedSize( true );
        mPreOrderName.setLayoutManager( new LinearLayoutManager( this ) );

        btnNextPreOrder = (Button) findViewById( R.id.btnNextPreOrder );
        btnRemoveItem1 = (Button) findViewById(R.id.btnRemoveItem1);
        btnRemoveItem2 = (Button) findViewById(R.id.btnRemoveItem2);
        btnRemoveItem3 = (Button) findViewById(R.id.btnRemoveItem3);
        btnRemoveItem4 = (Button) findViewById(R.id.btnRemoveItem4);

        //On Create method needs the location of where the items are being bought from



    }

    @Override
    protected void onStart() {
        super.onStart();

        //Calander for the feild
        final Calendar c = Calendar.getInstance();
        int mYear = c.get( Calendar.YEAR );
        int mMonth = c.get( Calendar.MONTH );
        int mDay = c.get( Calendar.DAY_OF_MONTH );
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        Date date = new Date( mYear - 1900, mMonth, mDay );
        final String dateString = (String) DateFormat.format( "yyyy/MM/dd", date );


        FirebaseRecyclerAdapter<PreOrderDetails, PreOrderViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PreOrderDetails, PreOrderViewHolder>(
                PreOrderDetails.class,
                R.layout.pre_order_list_layout,
                PreOrderViewHolder.class,
                mRef.child( "PreOrderHolder" ).child( user.getUid() )
        ) {

            @Override
            protected void populateViewHolder(PreOrderViewHolder viewHolder, final PreOrderDetails model, int position) {


                viewHolder.setName1( model.getItemName0() );
                viewHolder.setQty1( model.getItemQty0() );
                viewHolder.setName2( model.getItemName1() );
                viewHolder.setQty2( model.getItemQty1() );
                viewHolder.setName3( model.getItemName2() );
                viewHolder.setQty3( model.getItemQty2() );
                viewHolder.setName4( model.getItemName3() );
                viewHolder.setQty4( model.getItemQty3() );
                //HashMap for Item Name
                HashMap<String, String> mFinalOrderName = new HashMap<String, String>();
                mFinalOrderName.put("Item"+counter++, model.getItemName0());
                mFinalOrderName.put("Item"+counter++, model.getItemName1());
                mFinalOrderName.put("Item"+counter++, model.getItemName2());
                mFinalOrderName.put("Item"+counter++, model.getItemName3());
                //HashMap for ItemQty
                HashMap<String, String> mFinalOrderQty = new HashMap<String, String>(  );
                mFinalOrderQty.put("ItemQty"+counter++, model.getItemQty0());
                mFinalOrderQty.put("ItemQty"+counter++, model.getItemQty1());
                mFinalOrderQty.put("ItemQty"+counter++, model.getItemQty2());
                mFinalOrderQty.put("ItemQty"+counter++, model.getItemQty3());

                //Join HashMaps
                final HashMap<String, String> mFinalOrder = new HashMap<String, String>(  );
                mFinalOrder.putAll( mFinalOrderName );
                mFinalOrder.putAll( mFinalOrderQty );


                //Locations
                viewHolder.setLocation( model.getLocation() );
                //Click Listeners for Buttons
                btnNextPreOrder.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        mRef.child("PreOrders").child( model.getLocation() ).child( dateString ).child(user.getUid()).setValue(mFinalOrder);

                    }
                } );

                //Remove Buttons

                //This function should update the recycler view

                btnRemoveItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Need to remove from the arraylist
                        mRef.child( "PreOrderHolder" ).child( user.getUid() ).child("itemName0").removeValue();
                        mRef.child( "PreOrderHolder" ).child( user.getUid() ).child("itemQty0").removeValue();

                    }
                });


            }
        };
        mPreOrderName.setAdapter( firebaseRecyclerAdapter );
    }


    public static class PreOrderViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public PreOrderViewHolder(View itemView) {
            super( itemView );

            mView = itemView;
        }

        public void setName1(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName1 );
            item_name.setText( name );
        }

        public void setQty1(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty1 );
            item_qty.setText( qty );
        }

        public void setName2(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName2 );
            item_name.setText( name );
        }

        public void setQty2(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty2 );
            item_qty.setText( qty );
        }

        public void setName3(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName3 );
            item_name.setText( name );
        }

        public void setQty3(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty3 );
            item_qty.setText( qty );
        }

        public void setName4(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName4 );
            item_name.setText( name );
        }

        public void setQty4(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty4 );
            item_qty.setText( qty );
        }

        public void setLocation(String location) {
            TextView order_location = (TextView) mView.findViewById( R.id. tvOrderLocation);
            order_location.setText( location );
        }
    }



}



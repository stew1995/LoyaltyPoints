package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stewart.loyaltypoints.models.PreOrderDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import static com.google.android.gms.analytics.internal.zzy.v;
import static com.stewart.loyaltypoints.R.id.tvItemName1;
import static com.stewart.loyaltypoints.R.id.tvItemName2;
import static com.stewart.loyaltypoints.R.id.tvItemName3;
import static com.stewart.loyaltypoints.R.id.tvItemName4;
import static com.stewart.loyaltypoints.R.id.tvItemQty1;
import static com.stewart.loyaltypoints.R.id.tvItemQty2;
import static com.stewart.loyaltypoints.R.id.tvItemQty3;
import static com.stewart.loyaltypoints.R.id.tvItemQty4;
import static com.stewart.loyaltypoints.R.id.view;

public class PreOrderDetailsActivity extends AppCompatActivity {
    //Counter for adding into HashMap
    private int nameCounter;
    private int qtyCounter;
    View mView;


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
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //Each page i need to put if the user isnt signed in the go back to sign in screen

        //Initialise Views

        mPreOrderName = (RecyclerView) findViewById( R.id.rVPreOrderName );
        mPreOrderName.setHasFixedSize( true );
        mPreOrderName.setLayoutManager( new LinearLayoutManager( this ) );

        //DO if statement for if the field is empty to not show the buttons

        btnNextPreOrder = (Button) findViewById( R.id.btnNextPreOrder );


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
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        Date date = new Date( mYear - 1900, mMonth, mDay );
        final String dateString = (String) DateFormat.format( "yyyy/MM/dd", date );


        FirebaseRecyclerAdapter<PreOrderDetails, PreOrderViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PreOrderDetails, PreOrderViewHolder>(
                PreOrderDetails.class,
                R.layout.pre_order_list_layout,
                PreOrderViewHolder.class,
                mRef.child( "PreOrderHolder" ).child("Order"+user.getUid())
        ) {
            @Override
            protected void populateViewHolder(PreOrderViewHolder viewHolder, final PreOrderDetails model, int position) {
                //textViews to check if empty and not to add to array list to send to database
                //Set Items from PreOrderHolder
                viewHolder.setName1( model.getItemName0() );
                viewHolder.setQty1( model.getItemQty0() );
                viewHolder.setName2( model.getItemName1() );
                viewHolder.setQty2( model.getItemQty1() );
                viewHolder.setName3( model.getItemName2() );
                viewHolder.setQty3( model.getItemQty2() );
                viewHolder.setName4( model.getItemName3() );
                viewHolder.setQty4( model.getItemQty3() );

                //HashMap for Item Name
                HashMap<String, String> mFinalOrderName = new HashMap<String, String>(  );
                //HashMap for Item Qty
                HashMap<String, String> mFinalOrderQty = new HashMap<String, String>(  );

                //Removing the buttons
                if(viewHolder.getName1().isEmpty()) {
                    viewHolder.getBtn1().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn1().setVisibility( View.VISIBLE );
                }
                if(viewHolder.getName2().isEmpty()) {
                    viewHolder.getBtn2().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn2().setVisibility( View.VISIBLE );
                }
                if(viewHolder.getName3().isEmpty()) {
                    viewHolder.getBtn3().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn3().setVisibility( View.VISIBLE );
                }
                if(viewHolder.getName4().isEmpty()) {
                    viewHolder.getBtn4().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn4().setVisibility( View.VISIBLE );
                }

                if(viewHolder.getName1()!=null||!viewHolder.getName1().equals( "" )||viewHolder.getQty1()!=null||!viewHolder.getQty1().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName0());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty0());

                }

                if (viewHolder.getName2()!=null||!viewHolder.getName2().equals( "" )||viewHolder.getQty2()!=null||!viewHolder.getQty2().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName1());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty1());

                }

                if (viewHolder.getName3()!=null||!viewHolder.getName3().equals( "" )||viewHolder.getQty3()!=null||!viewHolder.getQty3().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName2());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty2());

                }

                if (viewHolder.getName4()!=null||!viewHolder.getName4().equals( "" )||viewHolder.getQty4()!=null||!viewHolder.getQty4().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName3());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty3());

                }

                //Removing the buttons if something isnt there


                //Join HashMaps
                final HashMap<String, String> mFinalOrder = new HashMap<String, String>(  );
                mFinalOrder.putAll( mFinalOrderName );
                mFinalOrder.putAll( mFinalOrderQty );

                //Locations
                viewHolder.setLocation( model.getLocation() );

                /*btnNextPreOrder.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();

                        mRef.child("PreOrders").child( model.getLocation() ).child( dateString ).child(user.getUid()).setValue(mFinalOrder);
                        mRef.child("PreOrderHolder").child(user.getUid()).removeValue();

                    }
                } );*/


                //Remove Buttons

                //This function should update the recycler view

               /* btnRemoveItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Need to remove from the arraylist
                        mRef.child( "PreOrderHolder" ).child( user.getUid() ).child("itemName0").removeValue();
                        mRef.child( "PreOrderHolder" ).child( user.getUid() ).child("itemQty0").removeValue();

                    }
                });*/
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

        public String getName1() {
            TextView item_name = (TextView) mView.findViewById( tvItemName1 );
            String text = (String) item_name.getText();
            return text;
        }

        public void setQty1(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty1 );
            item_qty.setText( qty );
        }

        public String getQty1() {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty1 );
            String text = (String) item_qty.getText();
            return text;
        }

        public void setName2(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName2 );
            item_name.setText( name );
        }

        public String getName2() {
            TextView item_name = (TextView) mView.findViewById( tvItemName2 );
            String text = (String) item_name.getText();
            return text;
        }

        public void setQty2(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty2 );
            item_qty.setText( qty );
        }

        public String getQty2() {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty2 );
            String text = (String) item_qty.getText();
            return text;
        }

        public void setName3(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName3 );
            item_name.setText( name );
        }

        public String getName3() {
            TextView item_name = (TextView) mView.findViewById( tvItemName3 );
            String text = (String) item_name.getText();
            return text;
        }

        public void setQty3(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty3 );
            item_qty.setText( qty );
        }

        public String getQty3() {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty3 );
            String text = (String) item_qty.getText();
            return text;
        }

        public void setName4(String name) {
            TextView item_name = (TextView) mView.findViewById( tvItemName4 );
            item_name.setText( name );
        }

        public String getName4() {
            TextView item_name = (TextView) mView.findViewById( tvItemName4 );
            String text = (String) item_name.getText();
            return text;
        }

        public void setQty4(String qty) {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty4 );
            item_qty.setText( qty );
        }

        public String getQty4() {
            TextView item_qty = (TextView) mView.findViewById( tvItemQty4 );
            String text = (String) item_qty.getText();
            return text;
        }

        public void setLocation(String location) {
            TextView order_location = (TextView) mView.findViewById( R.id. tvOrderLocation);
            order_location.setText( location );
        }

        public Button getBtn1() {
            Button btnRemoveItem1 = (Button) mView.findViewById( R.id.btnRemoveItem1 );
            return btnRemoveItem1;
        }

        public Button getBtn2() {
            Button btnRemoveItem2 = (Button) mView.findViewById( R.id.btnRemoveItem2 );
            return btnRemoveItem2;
        }

        public Button getBtn3() {
            Button btnRemoveItem3 = (Button) mView.findViewById( R.id.btnRemoveItem3 );
            return btnRemoveItem3;
        }

        public Button getBtn4() {
            Button btnRemoveItem4 = (Button) mView.findViewById( R.id.btnRemoveItem4 );
            return btnRemoveItem4;

        }
    }



}



package com.stewart.loyaltypoints;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stewart.loyaltypoints.models.PreOrderDetails;
import com.stewart.loyaltypoints.models.User;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import static android.R.attr.data;
import static com.stewart.loyaltypoints.R.id.add;
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
    private int nameCounter;
    private int qtyCounter;
    private int priceCounter;
    private int pointsCounter;


    //Recycler Views
    RecyclerView mPreOrderName;

    //Button for going back to main page
    private Button btnNextPreOrder;

    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    //Hashmaps
    HashMap mFinalOrder;
    HashMap mFinalOrderUser;
    HashMap mFinalOrderName;
    HashMap mFinalOrderDate;
    HashMap mFinalOrderQty;
    HashMap mFinalOrderLocation;
    HashMap mFinalOrderPrice;
    HashMap mFinalOrderPoints;

    //HashMap for showing to points
    HashMap mPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pre_order_details );

        //Initialise ArrayLists

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
    protected void onStop() {
        super.onStop();
        String user = mAuth.getCurrentUser().getUid();
        mRef.child("PreOrderHolder").child("Order"+user).removeValue();
        //Remove all items from HashMaps
        //Doesnt work sends to the database and removes if the user presses button
        //DOesnt remove from array listif user goes back a page like removing from database
        mFinalOrder.clear();
        mFinalOrderUser.clear();
        mFinalOrderUser.clear();
        mFinalOrderName.clear();
        mFinalOrderDate.clear();
        //mFinalOrderQty;

        mFinalOrderLocation.clear();
        mFinalOrderPrice.clear();
        mFinalOrderPoints.clear();

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
        final String userID = user.getUid();

        Date date = new Date( mYear - 1900, mMonth, mDay );
        final String dateString = (String) DateFormat.format( "yyyy/MM/dd", date );
        final String dateFormat = (String) DateFormat.format( "dd.MM.yyyy", date );


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
                mFinalOrderName = new HashMap<String, String>(  );
                //HashMap for Item Qty
                mFinalOrderQty = new HashMap<String, String>(  );
                //HAshMap for when item is ordered
                mFinalOrderDate = new HashMap<String, String>(  );
                //HashMap for Location
                mFinalOrderLocation = new HashMap<String, String>(  );
                //HashMap for Price to be passed to the transaction table to be processed as an Integer
                mFinalOrderPrice = new HashMap<String, String>(  );
                //HashMap for Points to be added onto the users account
                mFinalOrderPoints = new HashMap<String, String>(  );

                mPoints = new HashMap<String, Long>();



                //Removing the buttons if there is no item
                if(viewHolder.getName1().isEmpty()) {
                    viewHolder.getBtn1().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn1().setVisibility( View.VISIBLE );
                    viewHolder.getBtn1().setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Need to remove from the arraylist
                            Query querymRef = mRef.child( "PreOrderHolder" ).child("Order"+user.getUid()).orderByChild( "itemName0" ).equalTo( model.getItemName0() );
                            querymRef.addListenerForSingleValueEvent( new ValueEventListener() {
                                //Removing item from the checkout list
                                //if the name in the database equals the name on the model
                                //then it removes the item
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String name = snapshot.child("itemName0").getValue().toString();

                                        if(name.equals( model.getItemName0() )) {
                                            snapshot.getRef().child("itemName0").removeValue();
                                            snapshot.getRef().child( "itemQty0" ).removeValue();
                                            snapshot.getRef().child( "itemPrice0" ).removeValue();
                                            snapshot.getRef().child( "itemPoints0" ).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );
                            //mRef.child( "PreOrderHolder" ).child("Order"+user.getUid()).removeValue();
                        }
                    } );
                }
                if(viewHolder.getName2().isEmpty()) {
                    viewHolder.getBtn2().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn2().setVisibility( View.VISIBLE );
                    viewHolder.getBtn2().setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Need to remove from the arraylist
                            Query querymRef = mRef.child( "PreOrderHolder" ).child("Order"+user.getUid()).orderByChild( "itemName1" ).equalTo( model.getItemName1() );
                            querymRef.addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String name = snapshot.child("itemName1").getValue().toString();

                                        if(name.equals( model.getItemName1() )) {
                                            snapshot.getRef().child("itemName1").removeValue();
                                            snapshot.getRef().child( "itemQty1" ).removeValue();
                                            snapshot.getRef().child( "itemPrice1" ).removeValue();
                                            snapshot.getRef().child( "itemPoints1" ).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );
                        }
                    } );
                }
                if(viewHolder.getName3().isEmpty()) {
                    viewHolder.getBtn3().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn3().setVisibility( View.VISIBLE );
                    viewHolder.getBtn3().setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Need to remove from the arraylist
                            Query querymRef = mRef.child( "PreOrderHolder" ).child("Order"+user.getUid()).orderByChild( "itemName2" ).equalTo( model.getItemName2() );
                            querymRef.addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String name = snapshot.child("itemName2").getValue().toString();

                                        if(name.equals( model.getItemName2() )) {
                                            snapshot.getRef().child("itemName2").removeValue();
                                            snapshot.getRef().child( "itemQty2" ).removeValue();
                                            snapshot.getRef().child( "itemPrice2" ).removeValue();
                                            snapshot.getRef().child( "itemPoints2" ).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );
                        }
                    } );
                }
                if(viewHolder.getName4().isEmpty()) {
                    viewHolder.getBtn4().setVisibility( View.GONE );
                } else {
                    viewHolder.getBtn4().setVisibility( View.VISIBLE );
                    viewHolder.getBtn4().setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Need to remove from the arraylist
                            Query querymRef = mRef.child( "PreOrderHolder" ).child("Order"+user.getUid()).orderByChild( "itemName3" ).equalTo( model.getItemName3() );
                            querymRef.addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String name = snapshot.child("itemName3").getValue().toString();

                                        if(name.equals( model.getItemName3() )) {
                                            snapshot.getRef().child("itemName3").removeValue();
                                            snapshot.getRef().child( "itemQty3" ).removeValue();
                                            snapshot.getRef().child( "itemPrice3" ).removeValue();
                                            snapshot.getRef().child( "itemPoints3" ).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );
                        }
                    } );
                }

                if(viewHolder.getName1()!=null||!viewHolder.getName1().equals( "" )||viewHolder.getQty1()!=null||!viewHolder.getQty1().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName0());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty0());
                    mFinalOrderPrice.put("ItemPrice"+priceCounter++, model.getItemPrice0());
                    mFinalOrderPoints.put("ItemPoints"+pointsCounter++, model.getItemPoints0());

                } else {
                    //Remove from Array List
                }

                if (viewHolder.getName2()!=null||!viewHolder.getName2().equals( "" )||viewHolder.getQty2()!=null||!viewHolder.getQty2().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName1());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty1());
                    mFinalOrderPrice.put("ItemPrice"+priceCounter++, model.getItemPrice1());
                    mFinalOrderPoints.put("ItemPoints"+pointsCounter++, model.getItemPoints1());
                }

                if (viewHolder.getName3()!=null||!viewHolder.getName3().equals( "" )||viewHolder.getQty3()!=null||!viewHolder.getQty3().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName2());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty2());
                    mFinalOrderPrice.put("ItemPrice"+priceCounter++, model.getItemPrice2());
                    mFinalOrderPoints.put("ItemPoints"+pointsCounter++, model.getItemPoints2());
                }

                if (viewHolder.getName4()!=null||!viewHolder.getName4().equals( "" )||viewHolder.getQty4()!=null||!viewHolder.getQty4().equals( "" )) {

                    mFinalOrderName.put("Item"+nameCounter++, model.getItemName3());
                    mFinalOrderQty.put("ItemQty"+qtyCounter++, model.getItemQty3());
                    mFinalOrderPrice.put("ItemPrice"+priceCounter++, model.getItemPrice3());
                    mFinalOrderPoints.put("ItemPoints"+pointsCounter++, model.getItemPoints3());
                }

                //Storing the data of item purchase for database
                mFinalOrderDate.put("OrderDate", dateFormat);
                mFinalOrderLocation.put( "OrderLocation", model.getLocation() );



                //Join HashMaps
                mFinalOrder = new HashMap<String, String>(  );
                mFinalOrderUser = new HashMap<String, String>(  );
                //May need to put the points accumilated in to the array lists

                mFinalOrder.putAll( mFinalOrderName );
                mFinalOrder.putAll( mFinalOrderQty );
                mFinalOrder.putAll( mFinalOrderPrice );
                mFinalOrderUser.putAll( mFinalOrder );
                mFinalOrderUser.putAll( mFinalOrderPrice );
                mFinalOrderUser.putAll( mFinalOrderDate );
                mFinalOrderUser.putAll( mFinalOrderLocation );
                mFinalOrderUser.putAll( mFinalOrderPoints );

                //Locations
                viewHolder.setLocation( model.getLocation() );

                //Setting the Price

                if(!viewHolder.getName1().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    BigDecimal mTotal0 = mPrice0;
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setPrice( "Price: " +mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        Long add = Long.valueOf( points );
                        viewHolder.setPoints( "Points: "+"" + add );
                        mPoints.put("Points", add);
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setPoints( "Couldnt parse" );
                    }




                }
                if(!viewHolder.getName2().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    BigDecimal mTotal0 = mPrice0;
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setPrice( "Price: " +mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        Long add = Long.valueOf( points )
                                +Long.valueOf( points1 );
                        mPoints.put("Points", add);
                        viewHolder.setPoints( "Points: "+"" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setPoints( "Couldnt parse" );
                    }
                }
                //If 3 items it adds them
                if (!viewHolder.getName3().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    String price1 = model.getItemPrice1().replace( "£","" );
                    BigDecimal mPrice1 = new BigDecimal( price1 );
                    String price2 = model.getItemPrice2().replace( "£","" );
                    BigDecimal mPrice2 = new BigDecimal( price2 );


                    BigDecimal mTotal0 = mPrice0
                            .add( mPrice1 )
                            .add( mPrice2 );
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setPrice( "Price: " +mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        String points2 = model.getItemPoints2();
                        Long add = Long.valueOf( points )
                                +Long.valueOf( points1 )
                                +Long.valueOf( points2 );
                        mPoints.put("Points", add);
                        viewHolder.setPoints( "Points: "+"" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setPoints( "Couldnt parse" );
                    }
                }
                //If item 4 is there it adds all the items
                if (!viewHolder.getName3().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    String price1 = model.getItemPrice1().replace( "£","" );
                    BigDecimal mPrice1 = new BigDecimal( price1 );
                    String price2 = model.getItemPrice2().replace( "£","" );
                    BigDecimal mPrice2 = new BigDecimal( price2 );
                    String price3 = model.getItemPrice2().replace( "£","" );
                    BigDecimal mPrice3 = new BigDecimal( price3 );


                    BigDecimal mTotal0 = mPrice0
                            .add( mPrice1 )
                            .add( mPrice2 )
                            .add( mPrice3 );
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setPrice( "Price: " +mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        String points2 = model.getItemPoints2();
                        String points3 = model.getItemPoints3();
                        Long add = Long.valueOf( points )
                                +Long.valueOf( points1 )
                                +Long.valueOf( points2 )
                                +Long.valueOf( points3 );
                        mPoints.put("Points", add);
                        viewHolder.setPoints( "Points: "+"" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setPoints( "Couldnt parse" );
                    }


                }

                //Puts orders in pre order table for staff and then transactions for the user to look back on
                btnNextPreOrder.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRef.child("PreOrders").child( model.getLocation() ).child( dateString ).child( userID ).setValue( mFinalOrder );
                        mRef.child("Users").child(userID).child( "Transactions" ).push().setValue( mFinalOrderUser );
                        mRef.child("Users").child(userID).child("Recent").child("Holder").setValue(mFinalOrderUser);
                        addPoints();
                        startActivity(new Intent(PreOrderDetailsActivity.this, NavigationActivity.class));
                        finish();

                    }
                } );


            }
        };
        mPreOrderName.setAdapter( firebaseRecyclerAdapter );
    }

    private void addPoints() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();
        mRef.child("Users").child(userID);

        //HashMap of points
        final Integer value = (Integer) mPoints.get("Points");
        //For previous points

        mRef.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long points = null;
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    points = user.getPoints();
                }

                long addedPoints = points + value;
                mRef.child("Users").child(userID).child("Points").setValue(addedPoints);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
        //Remove Buttons
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

        public void setPrice(String price) {
            TextView item_price = (TextView) mView.findViewById(R.id.tvItemPrice);
            item_price.setText(price);
        }

        public void setPoints(String points) {
            TextView item_price = (TextView) mView.findViewById(R.id.tvItemPoints);
            item_price.setText(points);
        }

    }



}



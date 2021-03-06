package com.stewart.loyaltypoints;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.stewart.loyaltypoints.models.Items;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.stewart.loyaltypoints.R.id.buttonSendPreOrder;
import static com.stewart.loyaltypoints.R.id.preOrderLocationSpinner;

public class PreOrderActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference mRef;

    //HashMaps for PreOrderHolder
    private HashMap<String, String> productListing;
    private HashMap<String, String> productListingQty;
    private HashMap<String, String> productListingLocation;
    private HashMap<String, String> productListingPrice;
    private HashMap<String, String> productListingPoints;
    private HashMap<String, String> fullArray;

    //Counters for preorder
    private int counter;

    //Recycler view
    private RecyclerView mItemList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        productListing = new HashMap<String, String>();
        productListingQty = new HashMap<String, String>();
        productListingPrice = new HashMap<String, String>(  );
        fullArray = new HashMap<String, String>();
        productListingLocation = new HashMap<String, String>();
        productListingPoints = new HashMap<String, String>(  );

        mRef = FirebaseDatabase.getInstance().getReference();

        //Syncing data automatically
        mRef.keepSynced(true);
        Spinner locationSpinner = (Spinner) findViewById( preOrderLocationSpinner );
        mItemList = (RecyclerView) findViewById(R.id.item_recycler);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(new LinearLayoutManager(this));


        Resources res = getResources();
        String[] locations = res.getStringArray(R.array.locations);

        ArrayAdapter<String> adapater = new ArrayAdapter<String>( this, R.layout.spinner_item, locations ){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                //Get drop down selection
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView)view.findViewById(R.id.spinnerText);
                text.setTextColor(getResources().getColor(R.color.primaryTextColor));

                return view;
            }
        };
        locationSpinner.setAdapter( adapater );



    }


    //Change this page to save into holder where it will then be sent to the database
    //With the date
    @Override
    protected void onStart() {
        super.onStart();
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        FirebaseRecyclerAdapter<Items, ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, ItemViewHolder>(
                Items.class,
                R.layout.item_row,
                ItemViewHolder.class,
                mRef.child("Items")) {
            @Override
            protected void populateViewHolder(final ItemViewHolder viewHolder, final Items model, int position) {
                viewHolder.setPrice("Price: "+model.getItemPrice());
                viewHolder.setPoints("Points: "+model.getItemPoints().toString());
                viewHolder.setImage(getApplicationContext(), model.getItemImage());
                viewHolder.setCheckbox(model.getItemName());
                Spinner locationSpinner = (Spinner) findViewById( R.id.preOrderLocationSpinner);
                final String boxname = viewHolder.getCheckbox();
                final String boxQty = viewHolder.getQuanity();
                TextView noTitile = viewHolder.getTitleTextView();
                noTitile.setVisibility( View.GONE );


                //Currency
                final String itemPrice = model.getItemPrice();
                //Points
                final String itemPoints = model.getItemPoints().toString();

                final String location = (String) locationSpinner.getSelectedItem();
                viewHolder.setItemClickListener(new ItemClickListener() {

                    @Override
                    public void onItemClick(View v, int pos) {
                        //ALlow user to preorder up to 4 items
                        CheckBox chk = (CheckBox) v;
                        //Check if checkbox is checked



                        if (chk.isChecked()) {
                            //products.put( child, boxname );
                            productListing.put( "itemName"+counter,boxname );
                            productListingQty.put( "itemQty"+counter,boxQty );
                            productListingPrice.put( "itemPrice"+counter, itemPrice );
                            productListingPoints.put( "itemPoints"+counter, itemPoints );
                            //Need new hashmap for the price of each item and changed that varibale to
                            //The big decimal
                            //productsQty.put(childQty, boxQty);

                            counter++;
                        } else if (!chk.isChecked()) {
                            //TODO: Needs checking, almost there however sometimes leaves one there
                            productListing.remove( boxname );
                            productListingQty.remove( boxQty );
                            productListingPrice.remove( itemPrice );
                            productListingPoints.remove( itemPoints );
                            counter--;
                        }
                    }


                });
                Button btnSendOrder = (Button) findViewById( buttonSendPreOrder );
                btnSendOrder.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        productListingLocation.put( "location", location );
                        fullArray.putAll( productListing );
                        fullArray.putAll( productListingQty );
                        fullArray.putAll( productListingPrice );
                        fullArray.putAll( productListingLocation );
                        fullArray.putAll( productListingPoints );


                        //Putting orders into database
                        //Two different tables for quanity and the items
                        //use DataSnapshot to do the pre order basket at the bottom of the screen
                        //Getting and Setters as 0 to 4/5
                        //Need to limit on now much the user can pre order
                        //Need to link points to the user account so when they get pre ordered items it sends it over to the database
                        //with their updated points

                        //Working one however not sure how to get the random key
                        //mRef.child("PreOrderHolder").child(user.getUid()).child(ID).setValue(fullArray);


                        mRef.child("PreOrderHolder").child("Order"+user.getUid()).child(user.getUid()).setValue(fullArray);


                        //mRef.child("PreOrderHolder").child(user.getUid()).child("item").setValue(productListingQty);
                        //Go over this to change where the data is saved
                        //Putting it into the transactions table to then be sent to the Pre Order
                        //Table when the user confirms it on the PreorderDetails Screen

                        startActivity( new Intent( PreOrderActivity.this, PreOrderDetailsActivity.class ) );
                        //Remove all from arraylists and reset the counter
                        counter = 0;
                        fullArray.remove(productListing);
                        fullArray.remove(productListingQty);
                        fullArray.remove(productListingPrice);
                        fullArray.remove(productListingLocation);
                        fullArray.remove(productListingPoints);
                    }
                } );

            }
        };
        mItemList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private FirebaseAuth mAuth;
        private DatabaseReference mRef;
        View mView;
        private String points;
        CheckBox chk;


        ItemClickListener itemClickListener;

        //Set item view value
        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            chk = (CheckBox) mView.findViewById(R.id.postChk);


            chk.setOnClickListener(this);

        }
        //Setting the TextViews in the CardView


        public void setItemTitle (String title) {
            TextView item_title = (TextView) mView.findViewById(R.id.postItemName);
            item_title.setText(title);
        }

        public TextView getTitleTextView() {
            TextView itemTitle2 = (TextView) mView.findViewById(R.id.postItemName);
            return itemTitle2;
        }

        public void setPrice(String price){
            TextView item_price = (TextView) mView.findViewById(R.id.postPrice);
            item_price.setText(price);
        }

        public void setPoints(String points){
            TextView item_points = (TextView) mView.findViewById(R.id.postPoints);
            item_points.setText(points);
        }
        //Need to use context as this is a static class

        public void setImage(final Context context, final String itemImage) {


            final ImageView item_image = (ImageView) mView.findViewById(R.id.postImage);
            //Retreive image
            //GET THE IMAGES JUST FROM OFFLINE
            Picasso.with(context).load(itemImage).networkPolicy(NetworkPolicy.OFFLINE).into(item_image, new Callback() {
                @Override
                public void onSuccess() {
                    //leave as is offline nothing needs to be completed
                }

                @Override
                public void onError() {

                    //Get image from online
                    Picasso.with(context).load(itemImage).into(item_image);
                }
            });

        }

        public void setCheckbox(String name) {
            CheckBox chk = (CheckBox) mView.findViewById(R.id.postChk);
            chk.setText(name);
        }
        public String getCheckbox() {
            CheckBox chk = (CheckBox) mView.findViewById(R.id.postChk);
            String text = (String) chk.getText();
            return text;
        }

        public CheckBox removeCheckBox() {
            CheckBox chk = (CheckBox) mView.findViewById(R.id.postChk);
            return chk;
        }

        public String getQuanity() {
            Spinner spn = (Spinner) mView.findViewById(R.id.postQuanity);
            String text = (String) spn.getSelectedItem();
            return text;
        }

        public Spinner spinnerVis() {
            Spinner spn = (Spinner) mView.findViewById(R.id.postQuanity);
            return spn;
        }

        protected void locationCheckbox(String loc, Spinner spin) {
            if(spin.getSelectedItem()==loc) {
                CheckBox chk = (CheckBox) mView.findViewById(R.id.postChk);
                chk.setChecked( false );
            }

        }


        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }



    }




}





      /* mRef = FirebaseDatabase.getInstance().getReference().child("Items").child("itemName");

        listView = (ListView) findViewById(R.id.itemsListView);
       // Inflate the layout for this fragment
       final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
       listView.setAdapter(adapter);


       mRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot child : dataSnapshot.getChildren()) {
                   String items = child.getValue(String.class);
                   mItems.add(items);

                   adapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });





/*
        Items i1 = new Items("Coffee", "£1.60", "16");
        Items i2 = new Items("Tea", "£1.10", "11");
        Items i3 = new Items("Sausage Roll", "£1.40", "14");
        Items i4 = new Items("Sandwich", "£1.60", "16");
        Items i5 = new Items("Coffee", "£1.60", "16");
        Items i6 = new Items("Coffee", "£1.60", "16");

        itemList.add(i1);
        itemList.add(i2);
        itemList.add(i3);
        itemList.add(i4);
        itemList.add(i5);
        itemList.add(i6);


        BindDictionary<Items> dict = new BindDictionary<Items>();
        dict.addStringField(R.id.tvItemName, new StringExtractor<Items>() {
            @Override
            public String getStringValue(Items item, int position) {
                return item.getItemName();
            }
        });

        dict.addStringField(R.id.tvItemPrice, new StringExtractor<Items>() {
            @Override
            public String getStringValue(Items item, int position) {
                return item.getItemPrice();
            }
        });

        dict.addStringField(R.id.tvItemPoints, new StringExtractor<Items>() {
            @Override
            public String getStringValue(Items item, int position) {
                return item.getItemPoints();
            }
        });

        FunDapter<Items> adapter = new FunDapter<>(getActivity(),
                itemList, R.layout.item_layout, dict);
        ListView mapList = (ListView) view.findViewById(R.id.itemsListView);
        mapList.setAdapter(adapter);

*/


package com.stewart.loyaltypoints;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.*;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.id;
import static com.google.android.gms.analytics.internal.zzy.c;
import static com.google.android.gms.analytics.internal.zzy.i;
import static com.google.android.gms.analytics.internal.zzy.l;
import static com.google.android.gms.analytics.internal.zzy.m;
import static com.google.android.gms.analytics.internal.zzy.o;
import static com.google.android.gms.internal.zzrw.It;
import static com.stewart.loyaltypoints.R.id.view;

public class PreOrderActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference mRef;
    private com.firebase.ui.database.FirebaseListAdapter listAdapter;
    private ArrayList<String> mItems = new ArrayList<String>();
    private StorageReference mStorage;


    //private ArrayList<String> mItems = new ArrayList<>();

    //Recycler view
    private RecyclerView mItemList;


   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

       mRef = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

       //Syncing data automatically
       mRef.keepSynced(true);

       mItemList = (RecyclerView) findViewById(R.id.item_recycler);
       mItemList.setHasFixedSize(true);
       mItemList.setLayoutManager(new LinearLayoutManager(this));





    }


    @Override
    protected void onStart() {
        super.onStart();
        

        FirebaseRecyclerAdapter<Items, ItemViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, ItemViewHolder>(
                Items.class,
                R.layout.item_row,
                ItemViewHolder.class,
                mRef.child("Items")) {
            @Override
            protected void populateViewHolder(final ItemViewHolder viewHolder, Items model, int position) {
                viewHolder.setTitle(model.getItemName());
                viewHolder.setPrice(model.getItemPrice().toString());
                viewHolder.setPoints(model.getItemPoints().toString());
                viewHolder.setImage(getApplicationContext(), model.getItemImage());
                viewHolder.setCheckbox(model.getItemName());



                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        
                        CheckBox chk = (CheckBox) v;
                        Integer counter = 0;
                        //Check if checkbox is checked
                        if(chk.isChecked()) {
                            String boxname = viewHolder.getCheckbox();

                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();


                            mRef.child("PreOrder").child(user.getUid()).child("item" +counter++).setValue(boxname);
                            /*for(int num = 0; num>=5;num++) {
                                mRef.child("PreOrder").child(user.getUid()).child("item" + num).setValue(boxname);
                            }*/



                        } else {
                            if (!chk.isChecked()) {
                                String removename = viewHolder.getCheckbox();

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();

                                mRef.child("PreOrder").child(user.getUid()).child("item" + counter++).removeValue();

                                /*for (int num = 0; num >= 5; num++) {
                                    mRef.child("PreOrder").child(user.getUid()).child("item" + num).removeValue();
                                }*/


                            }
                        }
                    }
                });
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
            mRef = FirebaseDatabase.getInstance().getReference().child("PreOrder");
            mView = itemView;

            chk = (CheckBox) mView.findViewById(R.id.postChk);


            chk.setOnClickListener(this);
        }
        //Setting the TextViews in the CardView
        public void setTitle(String title){
            TextView item_title = (TextView) mView.findViewById(R.id.postName);
            item_title.setText(title);
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


       /* @Override
        public void onClick(View v) {
                TextView item_title = (TextView) mView.findViewById(R.id.postName);
                String name = item_title.toString();
                Items i = new Items(name);

                FirebaseUser user = mAuth.getCurrentUser();

                mRef.child(user.getUid()).setValue(i);
            }*/

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


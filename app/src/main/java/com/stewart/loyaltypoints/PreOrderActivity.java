package com.stewart.loyaltypoints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.*;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreOrderActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference mRef, mRefPrice;
    private com.firebase.ui.database.FirebaseListAdapter listAdapter;
    private ArrayList<String> mItems = new ArrayList<String>();

    //private ArrayList<String> mItems = new ArrayList<>();

    //Recycler view
    private RecyclerView mItemList;


   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

       mRef = FirebaseDatabase.getInstance().getReference().child("Items");



    mItemList = (RecyclerView) findViewById(R.id.item_list);
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
                mRef) {
            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, Items model, int position) {
                viewHolder.setTitle(model.getItemName());
                viewHolder.setPrice(model.getItemPrice());
                viewHolder.setPoints(model.getItemPoints());
            }
        };
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        View mView;
        private String points;

        //Set item view value
        public ItemViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
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


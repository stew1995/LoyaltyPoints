package com.stewart.loyaltypoints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.stewart.loyaltypoints.models.Items;

import java.util.Calendar;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.view.View.GONE;
import static com.stewart.loyaltypoints.R.id.buttonSendPreOrder;
import static com.stewart.loyaltypoints.R.id.preOrderLocationSpinner;

public class ItemsActivity extends PreOrderActivity {

    private ListView listView;
    private DatabaseReference mRef;
    private com.firebase.ui.database.FirebaseListAdapter listAdapter;
    private StorageReference mStorage;
    private HashMap<String, String> productListing;
    private HashMap<String, String> productListingQty;
    private HashMap<String, String> productListingLocation;
    private HashMap<String, String> fullArray;

    private RecyclerView mItemList;


    private Class<?>[] preOrderActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_items );

        //Syncing data automatically
        mItemList = (RecyclerView) findViewById(R.id.items_activity_recycler);
        mItemList.setHasFixedSize(true);
        mItemList.setLayoutManager(new LinearLayoutManager(this));

        mRef = FirebaseDatabase.getInstance().getReference();

    }

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
                TextView txt = viewHolder.getTitleTextView();
                txt.setVisibility( View.VISIBLE );
                viewHolder.setPrice("Price: "+model.getItemPrice().toString());
                viewHolder.setPoints("Points: "+model.getItemPoints().toString());
                viewHolder.setImage(getApplicationContext(), model.getItemImage());
                viewHolder.setCheckbox(model.getItemName());
                viewHolder.setItemTitle( model.getItemName() );

                Spinner spn = viewHolder.spinnerVis();
                spn.setVisibility( View.GONE );

                CheckBox chk = viewHolder.removeCheckBox();
                chk.setVisibility( View.GONE );


            }
        };
        mItemList.setAdapter(firebaseRecyclerAdapter);
    }
}

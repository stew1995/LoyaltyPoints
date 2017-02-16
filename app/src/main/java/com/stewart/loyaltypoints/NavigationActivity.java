package com.stewart.loyaltypoints;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.stewart.loyaltypoints.models.Transactions;
import com.stewart.loyaltypoints.models.TransactionsViewHolder;
import com.stewart.loyaltypoints.models.User;

import java.math.BigDecimal;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Page View doesnt work with the QR Code
    //private ViewPager viewPager;
    //private int[] screens;
    //private ViewPagerAdapter viewPagerAdapter;

    //Firebase
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String mUser;

    //RecyclerView for Transaction
    private RecyclerView mTransactionRecycler;


    //TextView for Outputting the user points
    private TextView tvUserPoints;

    //QRCode Generation
    //Colours of the QR CODE
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    //Dimenions of QRCode
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    //String to to encoded
    public static String STR = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser().getUid();
        //Needed for getting the points and the recent transactions
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser);


        //Inilalising Variables
        tvUserPoints = (TextView) findViewById(R.id.tvUserPoints);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Recycler View
        mTransactionRecycler = (RecyclerView) findViewById(R.id.main_screen_transaction_rv);
        mTransactionRecycler.setHasFixedSize( true );
        mTransactionRecycler.setLayoutManager( new LinearLayoutManager( this ) );
        mTransactionRecycler.setNestedScrollingEnabled(false);



        setUserPoints();
        setNavigation();


        recentTransactions();
    }



    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            //Deciding the type of format and sizes to be generated
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        //Fitting it in with the screen sizes
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        //returs final bitmap image to be generated
        return bitmap;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_store_locator) {
            startActivity(new Intent(NavigationActivity.this, MapsActivity.class));
        } else if (id == R.id.nav_transactions) {
            startActivity(new Intent(NavigationActivity.this, TransactionsActivity.class));
        } else if (id == R.id.nav_pre_order) {
            startActivity(new Intent(NavigationActivity.this, PreOrderActivity.class));
        } else if (id == R.id.nav_items) {
            startActivity(new Intent(NavigationActivity.this, ItemsActivity.class));
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUserPoints() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                tvUserPoints.setText(user.getPoints().toString());
                STR = user.getPoints().toString();
                //For QRCODE Generation
                ImageView imageView = (ImageView) findViewById(R.id.myImage);
                try {
                    //Encoding the image and setting
                    Bitmap bitmap = encodeAsBitmap(STR);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void setNavigation() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        final TextView userName = (TextView) header.findViewById( R.id.UserNameNavigation );
        final TextView userStudentID = (TextView) header.findViewById( R.id.UserStudentNumber );

        mRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String name = user.getfName() + " " + user.getlName();
                String id = "UP"+user.getStudentID();

                userName.setText( name );
                userStudentID.setText( id );
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
    }


    //For the Recent Transactions

    private void recentTransactions() {
        FirebaseRecyclerAdapter<Transactions, TransactionsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Transactions, TransactionsViewHolder>(
                Transactions.class,
                R.layout.transactions_row,
                TransactionsViewHolder.class,
                mRef.child("Recent")

        ) {
            @Override
            protected void populateViewHolder(TransactionsViewHolder viewHolder, Transactions model, int position) {


                //Setting the text fields in the transaction row
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


                //Calculating Price and Points
                //Checking what feilds are not empty
                //Then add the item prices
                if(!viewHolder.getItemName0().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    BigDecimal mTotal0 = mPrice0;
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setTransactionPrice( mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        int add = Integer.valueOf( points );
                        viewHolder.setTransactionPoints( "" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setTransactionPoints( "Couldnt parse" );
                    }




                }

                if (!viewHolder.getItemName1().isEmpty()) {

                    String price0 = model.getItemPrice0().replace( "£","" );
                    BigDecimal mPrice0 = new BigDecimal( price0 );
                    String price1 = model.getItemPrice1().replace( "£","" );
                    BigDecimal mPrice1 = new BigDecimal( price1 );

                    BigDecimal mTotal0 = mPrice0
                            .add( mPrice1 );
                    String mTotalPrice = "£" + mTotal0;
                    viewHolder.setTransactionPrice( mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        int add = Integer.valueOf( points )
                                +Integer.valueOf( points1 );
                        viewHolder.setTransactionPoints( "" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setTransactionPoints( "Couldnt parse" );
                    }

                }

                if (!viewHolder.getItemName2().isEmpty()) {

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
                    viewHolder.setTransactionPrice( mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        String points2 = model.getItemPoints2();
                        int add = Integer.valueOf( points )
                                +Integer.valueOf( points1 )
                                +Integer.valueOf( points2 );
                        viewHolder.setTransactionPoints( "" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setTransactionPoints( "Couldnt parse" );
                    }
                }

                if (!viewHolder.getItemName3().isEmpty()) {

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
                    viewHolder.setTransactionPrice( mTotalPrice );

                    try {
                        String points = model.getItemPoints0();
                        String points1 = model.getItemPoints1();
                        String points2 = model.getItemPoints2();
                        String points3 = model.getItemPoints3();
                        int add = Integer.valueOf( points )
                                +Integer.valueOf( points1 )
                                +Integer.valueOf( points2 )
                                +Integer.valueOf( points3 );
                        viewHolder.setTransactionPoints( "" + add );
                    }  catch(NumberFormatException nfe) {
                        viewHolder.setTransactionPoints( "Couldnt parse" );
                    }


                }
            }
        };

        mTransactionRecycler.setAdapter( firebaseRecyclerAdapter );
    }

}

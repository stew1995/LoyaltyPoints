package com.stewart.loyaltypoints;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.stewart.loyaltypoints.models.User;

import static com.stewart.loyaltypoints.MainActivity.STR;


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
    public final static String STR = "A string to be encoded as QR code";

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

        //For QRCODE Generation
        ImageView imageView = (ImageView) findViewById(R.id.myImage);
        try {
            //Encoding the image and setting
            Bitmap bitmap = encodeAsBitmap(STR);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        setUserPoints();


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
    //TODO: DOESNT WORK GETTING THE POINTS OUTPUTS ERROR SAYING "Can't convert object of type java.lang.String to type com.stewart.loyaltypoints.models.User"
    private void setUserPoints() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);

                    tvUserPoints.setText(user.getPoints().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}

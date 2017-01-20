package com.stewart.loyaltypoints;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by stewart on 20/01/2017.
 */

public class LoyaltyPoints extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //Create Picasso Builder
        Picasso.Builder builder = new Picasso.Builder(this);
        //uses OkHTTPDownloader as the builder
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        //Shows where the image is from network, local, or disk
        //false not to show it
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}

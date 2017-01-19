package com.stewart.loyaltypoints;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by up703451 on 19/01/2017.
 */

public class FirebaseListAdapter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}

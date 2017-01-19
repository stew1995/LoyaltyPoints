package com.stewart.loyaltypoints;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stewart on 16/01/2017.
 */

public class Intromanager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;


    public Intromanager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("first", 0);
        editor = pref.edit();
    }

    //checking if the user is creating it for the first time
    public void setFirst(Boolean isFirst) {
        editor.putBoolean("check", isFirst);
        editor.commit();
    }


    //set value to false for when the user does it for the second time
    public boolean Check() {

        return pref.getBoolean("check", true);

    }
}

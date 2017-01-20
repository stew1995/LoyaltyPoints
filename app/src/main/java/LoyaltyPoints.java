import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by stewart on 20/01/2017.
 */

public class LoyaltyPoints extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }



}

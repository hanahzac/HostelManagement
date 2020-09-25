package com.hanahzac.hostelmanagement;

import android.app.Application;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loggedIn extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            Intent intent = new Intent(loggedIn.this, decideUser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}

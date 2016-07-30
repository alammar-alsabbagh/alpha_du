package com.procasy.dubarah_nocker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.procasy.dubarah_nocker.Helper.SessionManager;

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(sessionManager.isLoggedIn())
                {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}

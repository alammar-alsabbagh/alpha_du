package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.LoginActivity;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.R;

public class WhatIsNockerAbout extends AppCompatActivity {

    LinearLayout teaser,main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_nocker_about);
        teaser = (LinearLayout) findViewById(R.id.teaser);
        main = (LinearLayout) findViewById(R.id.main);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLoggedIn())
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        teaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),JoinOrRecommend.class));
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}

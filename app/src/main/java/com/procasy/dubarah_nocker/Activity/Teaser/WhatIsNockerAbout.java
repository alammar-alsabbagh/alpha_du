package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.LoginActivity;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.R;

public class WhatIsNockerAbout extends AppCompatActivity {

    LinearLayout teaser,main;
    TextView t1,t2,t3,t4,t5,t6,t7 ,t8,t9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_nocker_about);
        teaser = (LinearLayout) findViewById(R.id.teaser);
        main = (LinearLayout) findViewById(R.id.main);
        t1= (TextView) findViewById(R.id.t1)  ;
        t2= (TextView) findViewById(R.id.t2) ;
        t3= (TextView) findViewById(R.id.t3) ;
        t4= (TextView) findViewById(R.id.t4) ;
        t5= (TextView) findViewById(R.id.t5) ;
        //t6= (TextView) findViewById(R.id.t6) ;
        t7= (TextView) findViewById(R.id.t7) ;
        t8= (TextView) findViewById(R.id.t8) ;
        t9 =(TextView)findViewById(R.id.t9) ;
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(typface);
        t3.setTypeface(typface);
        t4.setTypeface(typface);
        t5.setTypeface(typface);
       // t6.setTypeface(typface);
        t7.setTypeface(typface);
        t8.setTypeface(typface);
        t9.setTypeface(typface);
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


package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Activity.Teaser.Join.SignInUPJoin;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.R;


public class JoinOrRecommend extends AppCompatActivity {

    LinearLayout join;
    LinearLayout recommend;


    TextView t1,t2,t3,t4,t5,t7,t8,t9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_or_recommend);
        join = (LinearLayout) findViewById(R.id.join);
        recommend = (LinearLayout) findViewById(R.id.recommend);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);
        t9 = (TextView) findViewById(R.id.t9);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(typface);
        t3.setTypeface(bold);
        t4.setTypeface(bold);
        t7.setTypeface(typface);
        t8.setTypeface(typface);
        t9.setTypeface(typface);
        t5.setTypeface(bold);
        final SessionManager sessionManager = new SessionManager(this);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    join.performClick();
                join.setPressed(true);
                join.invalidate();
                // delay completion till animation completes
                join.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        join.setPressed(false);
                        join.invalidate();*/
                        sessionManager.setJoinRecommend(1);
                        startActivity(new Intent(getApplicationContext(),SignInUPJoin.class));
/*
                        //any other associated action
                    }
                }, 400);
                // .8secs delay time

            }*/
        }});
        recommend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                      /*      recommend.performClick();
                                            recommend.setPressed(true);
                                            recommend.invalidate();
                                            // delay completion till animation completes
                                            recommend.postDelayed(new Runnable() {  //delay button
                                                public void run() {
                                                    recommend.setPressed(false);
                                                    recommend.invalidate();*/
                                                    sessionManager.setJoinRecommend(1);
                                                    startActivity(new Intent(getApplicationContext(),SecondScreenActivity.class));
                                              /*      //any other associated action
                                                }
                                            }, 400);*/
                                        }
                                    }
        );
    }
}

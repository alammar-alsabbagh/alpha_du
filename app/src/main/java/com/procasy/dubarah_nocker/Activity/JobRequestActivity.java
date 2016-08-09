package com.procasy.dubarah_nocker.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.procasy.dubarah_nocker.R;

public class JobRequestActivity extends AppCompatActivity {

    LinearLayout quota;
    ScrollView scrollView;
    Button accept;
    Button pass,send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_request);
        quota = (LinearLayout) findViewById(R.id.quota);
        quota.setVisibility(View.GONE);
        accept = (Button) findViewById(R.id.accept);
        pass = (Button) findViewById(R.id.pass);
        send = (Button) findViewById(R.id.send);
        scrollView = (ScrollView) findViewById(R.id.scroll);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quota.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        scrollView.smoothScrollTo(0, scrollView.getMaxScrollAmount ());

                    }
                }, 500);
            }
        });

    }
}

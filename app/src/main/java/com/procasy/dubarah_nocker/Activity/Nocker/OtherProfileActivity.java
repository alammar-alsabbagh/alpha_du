package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;

import com.procasy.dubarah_nocker.R;

public class OtherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Intent intent = getIntent();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        loadBackdrop();
    }

    private void loadBackdrop() {
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.backdrop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    }


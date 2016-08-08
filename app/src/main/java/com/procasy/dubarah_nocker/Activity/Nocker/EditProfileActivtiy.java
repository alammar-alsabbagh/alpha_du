package com.procasy.dubarah_nocker.Activity.Nocker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.CustomTypefaceSpan;

public class EditProfileActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface typface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/font1.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Edit Profile");
        SS.setSpan (new CustomTypefaceSpan("", typface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(SS);
    }
}

package com.procasy.dubarah_nocker.Activity.EditProfile;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.CustomTypefaceSpan;

public class AddressEdit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface typface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/font1.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Edit Address");
        SS.setSpan (new CustomTypefaceSpan("", typface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(SS);
    }


}

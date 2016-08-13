package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;

import com.procasy.dubarah_nocker.Activity.EditProfile.AddressEdit;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.CustomTypefaceSpan;

public class EditProfileActivtiy extends AppCompatActivity implements View.OnClickListener{

    LinearLayout Address , PhoneNumber,PersonalMessage,MyCause,MySkills,MyLanguages;
    CardView PhotoNameBestSkill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        Address = (LinearLayout) findViewById(R.id.addressedit);
        PhoneNumber = (LinearLayout) findViewById(R.id.phonenumberedit);
        PersonalMessage = (LinearLayout) findViewById(R.id.personalmessageedit);
        MyCause = (LinearLayout) findViewById(R.id.mycauseedit);
        PhotoNameBestSkill = (CardView) findViewById(R.id.card);
        Address.setOnClickListener(this);
        PhoneNumber.setOnClickListener(this);
        PersonalMessage.setOnClickListener(this);
        MyCause.setOnClickListener(this);
        PhotoNameBestSkill.setOnClickListener(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface typface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/font1.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Edit Profile");
        SS.setSpan (new CustomTypefaceSpan("", typface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(SS);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId())
    {
        case R.id.addressedit:
        {
            startActivity(new Intent(getApplicationContext(), AddressEdit.class));
            break;
        }
    }
    }
}

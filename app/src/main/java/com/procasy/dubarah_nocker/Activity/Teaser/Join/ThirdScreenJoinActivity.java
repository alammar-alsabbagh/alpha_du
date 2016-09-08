package com.procasy.dubarah_nocker.Activity.Teaser.Join;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;


public class ThirdScreenJoinActivity extends AppCompatActivity implements Validator.ValidationListener{

    LinearLayout next_btn;
    LinearLayout back_btn;
    LinearLayout Whole_btn;
    TextView t1,t2,t3,t4,t5,t6,person_name,person_skill;
    @NotEmpty
    AutoCompleteTextView editText;
    Skills mskills;
    Validator validator;
    SessionManager sessionManager ;
    String name;
    ImageView PersonPhoto;
    RelativeLayout mobile;
    Animation animFadein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen_join);
        mskills = new Skills(this);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        validator = new Validator(this);
        validator.setValidationListener(this);
        sessionManager = new SessionManager(this);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        PersonPhoto = (ImageView) findViewById(R.id.person_photo);
        person_name = (TextView) findViewById(R.id.person_name);
        person_skill = (TextView) findViewById(R.id.person_skill);
        editText = (AutoCompleteTextView) findViewById(R.id.skill);
        mobile = (RelativeLayout)findViewById(R.id.mobile);
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        // start the animation
        mobile.startAnimation(animFadein);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                person_skill.setText(editText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person_skill.setText(editText.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                person_skill.setText(editText.getText().toString());

            }
        });
        name = sessionManager.getFName()+" "+sessionManager.getLName();
        person_name.setText(name);
        editText.setHint("Add your "+"top skill");
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(bold);
        t3.setTypeface(typface);
        t4.setTypeface(typface);
        t5.setTypeface(typface);
        t6.setTypeface(typface);
        person_name.setTypeface(typface);
        person_skill.setTypeface(typface);
        ArrayList<String> skills_list = new ArrayList<>();
        try {
            mskills.open();
            Cursor cursor = mskills.getAllEntries();
            cursor.moveToFirst();

            try {
                skills_list.add(cursor.getString(1));
                while (cursor.moveToNext()) {
                    skills_list.add(cursor.getString(1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mskills.close();
            }
        }catch (RuntimeException e)
        {
            
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, skills_list);
        editText.setAdapter(adapter2);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          validator.validate();
                // .8secs delay time

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Whole_btn.performClick();
                                            Whole_btn.setPressed(true);
                                            Whole_btn.invalidate();
                                            // delay completion till animation completes
                                            Whole_btn.postDelayed(new Runnable() {  //delay button
                                                public void run() {
                                                    Whole_btn.setPressed(false);
                                                    Whole_btn.invalidate();
                                                    finish();
                                                    //any other associated action
                                                }
                                            }, 400);
                                        }
                                    }
        );
    }

    @Override
    public void onValidationSucceeded() {
        Whole_btn.performClick();
        Whole_btn.setPressed(true);
        Whole_btn.invalidate();
        // delay completion till animation completes
        Whole_btn.postDelayed(new Runnable() {  //delay button
            public void run() {
                Whole_btn.setPressed(false);
                Whole_btn.invalidate();
                Intent intent = new Intent(getApplicationContext(),FourthScreenJoinActivity.class);
                sessionManager.setKeyUrSkill(editText.getText().toString());
                startActivity(intent);
                //any other associated action
            }
        }, 400);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}

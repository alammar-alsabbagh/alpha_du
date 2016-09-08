package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.R;

import java.util.List;


public class RateSkillActivity extends AppCompatActivity implements Validator.ValidationListener{


    LinearLayout next_btn;
    LinearLayout back_btn;
    LinearLayout Whole_btn;
    ImageView iv1,iv2,iv3;
    boolean b1,b2,b3;
    TextView t1,t2,t3,t4,t5,t6,t7,t8;
    @NotEmpty
    EditText rate;
String name;
    Validator validator;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_skill);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);
        rate = (EditText) findViewById(R.id.rate);
        sessionManager = new SessionManager(this);
        name = sessionManager.getKey_his_name();
        System.out.println(name.split("\\s+")[0]);
        t2.setText(name.split("\\s+")[0]);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(bold);
        t3.setTypeface(typface);
        t4.setTypeface(typface);
        t5.setTypeface(typface);
        t6.setTypeface(typface);
        t7.setTypeface(typface);
        t8.setTypeface(typface);

        rate.setTypeface(typface);
        validator = new Validator(this);
        validator.setValidationListener(this);

        b1 = false;
        b2 = false;
        b3 = false;

        iv1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=true;
                        b2=false;
                        b3=false;
                        sessionManager.setKeyHisRating("1");
                        iv1.setImageResource(R.drawable.amazing_yellow);
                        iv2.setImageResource(R.drawable.good_gray);
                        iv3.setImageResource(R.drawable.has_potential_gray);
                    }
                }
        );

        iv2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=false;
                        b2=true;
                        b3=false;
                        sessionManager.setKeyHisRating("2");

                        iv1.setImageResource(R.drawable.amazing_gray);
                        iv2.setImageResource(R.drawable.good_yellow);
                        iv3.setImageResource(R.drawable.has_potential_gray);
                    }
                }
        );

        iv3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1=false;
                        b2=false;
                        b3=true;
                        sessionManager.setKeyHisRating("3");
                        iv1.setImageResource(R.drawable.amazing_gray);
                        iv2.setImageResource(R.drawable.good_gray);
                        iv3.setImageResource(R.drawable.has_potential_yellow);
                    }
                }
        );

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
        if(b1 || b2 || b3) {
            Whole_btn.performClick();
            Whole_btn.setPressed(true);
            Whole_btn.invalidate();
            // delay completion till animation completes
            Whole_btn.postDelayed(new Runnable() {  //delay button
                public void run() {
                    Whole_btn.setPressed(false);
                    Whole_btn.invalidate();
                    sessionManager.setKeyHisRatingText(rate.getText().toString());
                    startActivity(new Intent(getApplicationContext(), SignInUP.class));
                    //any other associated action
                }
            }, 400);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Be sure that you have choosen a rating",Toast.LENGTH_SHORT).show();
        }
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

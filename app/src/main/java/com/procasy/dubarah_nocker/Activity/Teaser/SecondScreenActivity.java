package com.procasy.dubarah_nocker.Activity.Teaser;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.Helper.ContactsDb;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;


public class SecondScreenActivity extends AppCompatActivity implements Validator.ValidationListener {



    LinearLayout next_btn;
    LinearLayout Whole_btn;
    LinearLayout back_btn;
    TextView t1,t2,t3,person_name,person_skill;
    @NotEmpty
    AutoCompleteTextView editText;

    RelativeLayout mobile;
    Animation animFadein;

    ContactsDb mContactsDb;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        person_name = (TextView) findViewById(R.id.person_name);
        person_skill = (TextView) findViewById(R.id.person_skill);
        mobile = (RelativeLayout)findViewById(R.id.mobile);
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        // start the animation
        mobile.startAnimation(animFadein);

        validator = new Validator(this);
        validator.setValidationListener(this);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        editText = (AutoCompleteTextView) findViewById(R.id.name);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                person_name.setText(editText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person_name.setText(editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                person_name.setText(editText.getText().toString());
            }
        });
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        t1.setTypeface(typface);
        t2.setTypeface(typface);
        t3.setTypeface(typface);
        person_name.setTypeface(typface);
        person_skill.setTypeface(typface);
        editText.setTypeface(typface);
        mContactsDb = new ContactsDb(this);
        List<String> contactlist = new ArrayList<>();
        mContactsDb.open();
        Cursor curosr = mContactsDb.getAllEntries();
        curosr.moveToFirst();

        try {
            contactlist.add(curosr.getString(2));
            while (curosr.moveToNext()) {
                contactlist.add(curosr.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mContactsDb.close();
        }
        for (String s:contactlist
             ) {
            System.out.println(s);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, contactlist);
        editText.setAdapter(adapter2);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 validator.validate();
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
                Intent intent = new Intent(getApplicationContext(),ThirdScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",editText.getText().toString());
                intent.putExtras(bundle);
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

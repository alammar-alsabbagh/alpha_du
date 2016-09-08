package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class MainInfoActivity extends AppCompatActivity implements Validator.ValidationListener{

    LinearLayout next_btn;
    LinearLayout back_btn;
    LinearLayout Whole_btn;
    TextView t1,t2,t3,t4,t5,t6;
    @NotEmpty
    EditText firstname,lastname,city,country,province,birthyear,phonenumber;
    @NotEmpty
    AutoCompleteTextView urskill;
    Skills mskills;
    String region;
    SessionManager sessionManager;
    Validator validator;
    RadioGroup toggleGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info);
        sessionManager = new SessionManager(this);
validator = new Validator(this);
        validator.setValidationListener(this);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        t1 = (TextView) findViewById(R.id.t1);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        firstname = (EditText) findViewById(R.id.Firstname);
        lastname = (EditText) findViewById(R.id.Lastname);
        city = (EditText) findViewById(R.id.City);
        country = (EditText) findViewById(R.id.Country);
        province = (EditText) findViewById(R.id.Province);
        birthyear = (EditText) findViewById(R.id.Birthyear);
        phonenumber = (EditText) findViewById(R.id.PhoneNumber);
        urskill = (AutoCompleteTextView) findViewById(R.id.urskill);
        GetInfoRequest();
        mskills = new Skills(this);
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(bold);
        t3.setTypeface(typface);
        t4.setTypeface(typface);
        firstname.setTypeface(typface);
        lastname.setTypeface(typface);
        city.setTypeface(typface);
        country.setTypeface(typface);
        province.setTypeface(typface);
        birthyear.setTypeface(typface);
        phonenumber.setTypeface(typface);
        ArrayList<String> skills_list = new ArrayList<>();
        mskills.open();
        Cursor cursor = mskills.getAllEntries();
        cursor.moveToFirst();
        firstname.setText(sessionManager.getFName());
        lastname.setText(sessionManager.getLName());
        toggleGroup = (RadioGroup) findViewById(R.id.toggleGroup);
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, skills_list);
        urskill.setAdapter(adapter2);

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
        final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                    view.setChecked(view.getId() == i);
                }
            }
        };

        ((RadioGroup) findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());
        // app specific stuff ..
    }
    public void GetInfoRequest() {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Location..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(MainInfoActivity.this);
        String url = "http://ip-api.com/json";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.optString("status");
                            if(status.equals("success"))
                            {
                                country.setText(object.getString("country"));
                                city.setText(object.getString("city"));
                                province.setText(object.getString("regionName"));
                                region = object.getString("city");
                            }
                            else
                            {

                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        dialog.hide();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        dialog.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        queue.add(postRequest);

    }

    @Override
    public void onValidationSucceeded() {

        if(toggleGroup.getCheckedRadioButtonId() != -1) {
            final String gender = ((ToggleButton) toggleGroup.findViewById(toggleGroup.getCheckedRadioButtonId())).getText().toString();
            System.out.println(gender);
            Whole_btn.performClick();
            Whole_btn.setPressed(true);
            Whole_btn.invalidate();
            // delay completion till animation completes
            Whole_btn.postDelayed(new Runnable() {  //delay button
                public void run() {
                    Whole_btn.setPressed(false);
                    Whole_btn.invalidate();
                    Bundle bundle = new Bundle();
                    bundle.putString("city", region);
                    sessionManager.setFName(firstname.getText().toString());
                    sessionManager.setLName(lastname.getText().toString());
                    sessionManager.setCity(city.getText().toString());
                    sessionManager.setKEY_Province(province.getText().toString());
                    sessionManager.setCountry(country.getText().toString());
                    sessionManager.setKEY_Phonenumber(phonenumber.getText().toString());
                    sessionManager.setKEY_gender(gender);
                    sessionManager.setKEY_Birthyear(birthyear.getText().toString());
                    sessionManager.setKeyUrSkill(urskill.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), BroadCastActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //any other associated action
                }
            }, 400);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Make sure you have chosen your gender",Toast.LENGTH_SHORT).show();
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

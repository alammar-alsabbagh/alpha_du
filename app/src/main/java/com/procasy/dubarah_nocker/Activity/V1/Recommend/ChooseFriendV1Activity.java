package com.procasy.dubarah_nocker.Activity.V1.Recommend;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Activity.BeANocker.ChooseLanguagesActivity;
import com.procasy.dubarah_nocker.Activity.Teaser.FourthScreenActivity;
import com.procasy.dubarah_nocker.Adapter.AdapterCallback;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.Model.Responses.SocialSignupResponse;
import com.procasy.dubarah_nocker.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseFriendV1Activity extends AppCompatActivity implements Validator.ValidationListener , AdapterCallback {


    Skills mskills;
    SessionManager sessionManager;
    LinearLayout next_btn;
    LinearLayout Whole_btn;
    LinearLayout back_btn;
    AutoCompleteTextView editText;
    RelativeLayout mobile;
    Animation animFadein;
    Validator validator;
    TagFlowLayout chosen_skills;
    private AdapterCallback mAdapterCallback;
    private APIinterface apiService;


    private JSONArray GetChoosenSkilles() {
        Log.e("chosenskilles", ChoosenSkill.toString());
        Log.e("Email", sessionManager.getEmail());
        Log.e("UDID", sessionManager.getUDID());
        ArrayList<Integer> skills_id = new ArrayList<>();
        for (String s : ChoosenSkill) {
            mskills.open();
            Cursor cursor = mskills.getSingleSkill(s.toString());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                skills_id.add(cursor.getInt(cursor.getColumnIndex("skill_id")));
                cursor.moveToNext();
            }
            cursor.close();
            mskills.close();
        }
        Log.e("chosenskilles_ids", skills_id.toString());
        Log.e("chosenskilles", ChoosenSkill.toString());
        Log.e("Email", sessionManager.getEmail());
        Log.e("UDID", sessionManager.getUDID());
        JSONArray array = new JSONArray();
        for (Integer skill_id : skills_id) {
            array.put(skill_id);
        }

        return array;
    }


    List<String> ChoosenSkill = new ArrayList<>();
    TextView Person_skill;



        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_skills_v1);
            Bundle bundle = getIntent().getExtras();

            mskills = new Skills(this);
            next_btn = (LinearLayout) findViewById(R.id.next_btn);
            back_btn = (LinearLayout) findViewById(R.id.back_btn);
            Whole_btn = (LinearLayout) findViewById(R.id.wholething);
            validator = new Validator(this);
            validator.setValidationListener(this);
            sessionManager = new SessionManager(this);
            editText = (AutoCompleteTextView) findViewById(R.id.skill);
            mobile = (RelativeLayout) findViewById(R.id.mobile);
            chosen_skills = (TagFlowLayout) findViewById(R.id.chosen_skills);
            this.mAdapterCallback = ((AdapterCallback) this);



            animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);

            Person_skill = (TextView) findViewById(R.id.person_skill);
            mobile.startAnimation(animFadein);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(ChoosenSkill.size() > 0)
                        Person_skill.setText(ChoosenSkill.get(0));
                }
            });


            final ArrayList<String> skills_list = new ArrayList<>();
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
            } catch (RuntimeException e) {

            }

            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, skills_list);
            editText.setAdapter(adapter2);

            editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView)view.findViewById(R.id.text_skill);
                    ChoosenSkill.add(new String(textView.getText().toString()));
                    chosen_skills.setAdapter(new TagAdapter<String>(ChoosenSkill)
                    {
                        @Override
                        public View getView(FlowLayout parent, int position, String s)
                        {
                            LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                                    chosen_skills, false);
                            TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                            textView.setText(ChoosenSkill.get(position));
                            return tv;
                        }
                    });
                    editText.setText("");
                    editText.setHint("Add more !");

                }
            });


            chosen_skills.setAdapter(new TagAdapter<String>(ChoosenSkill)
            {
                @Override
                public View getView(FlowLayout parent, int position, String s)
                {
                    LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                            chosen_skills, false);
                    TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                    textView.setText(ChoosenSkill.get(position));
                    return tv;
                }
            });















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
    public void onValidationSucceeded () {
        Whole_btn.performClick();
        Whole_btn.setPressed(true);
        Whole_btn.invalidate();
        // delay completion till animation completes
        apiService =
                ApiClass.getClient().create(APIinterface.class);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SocialSignupResponse> call = apiService.AddUserSkill(sessionManager.getEmail(), sessionManager.getUDID(), GetChoosenSkilles());
                call.enqueue(new Callback<SocialSignupResponse>() {
                    @Override
                    public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {
                        if (response.body().getStatus() == 1) // new user
                        {
                            sessionManager.setKeyIsNocker(1);
                            startActivity(new Intent(getApplicationContext(), ChooseLanguagesActivity.class));
                            finish();

                        } else /// old user
                        {
                            Toast.makeText(getApplicationContext(), getString(R.string.str85), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialSignupResponse> call, Throwable t) {

                    }
                });

            }
        });
        Whole_btn.postDelayed(new Runnable() {  //delay button
            public void run() {
                Whole_btn.setPressed(false);
                Whole_btn.invalidate();
                Intent intent = new Intent(getApplicationContext(), FourthScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("skill", editText.getText().toString());
                sessionManager.setKeyHisSkill(editText.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                //any other associated action
            }
        }, 400);
    }

    @Override
    public void onValidationFailed (List < ValidationError > errors) {
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

    @Override
    public void onMethodCallback(String s) {
        ChoosenSkill.add(new String(s));
        chosen_skills.setAdapter(new TagAdapter<String>(ChoosenSkill)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                        chosen_skills, false);
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(ChoosenSkill.get(position));
                return tv;
            }
        });
    }
}
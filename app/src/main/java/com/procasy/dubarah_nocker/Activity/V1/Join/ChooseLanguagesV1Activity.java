package com.procasy.dubarah_nocker.Activity.V1.Join;

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
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.MainActivity;
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

import static com.procasy.dubarah_nocker.R.id.chosen_skills;


public class ChooseLanguagesV1Activity extends AppCompatActivity implements Validator.ValidationListener {


    Language mLanguage;
    SessionManager sessionManager;
    LinearLayout next_btn;
    LinearLayout Whole_btn;
    LinearLayout back_btn;
    AutoCompleteTextView editText;
    RelativeLayout mobile;
    Animation animFadein;
    Validator validator;
    TagFlowLayout chosen_languages;
    private APIinterface apiService;

    private JSONArray GetChoosenLangages() {
        ArrayList<Integer> languages_ids = new ArrayList<>();
        for (String s : ChoosenLanguages) {
            mLanguage.open();
            Cursor cursor = mLanguage.getSingleLanguage(s.toString());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                languages_ids.add(cursor.getInt(cursor.getColumnIndex("language_id")));
                cursor.moveToNext();
            }
            cursor.close();
            mLanguage.close();
        }
        Log.e("languages_ids", languages_ids.toString());
        Log.e("choosenLanguages", ChoosenLanguages.toString());
        Log.e("Email", sessionManager.getEmail());
        Log.e("UDID", sessionManager.getUDID());
        JSONArray array = new JSONArray();
        for (Integer language_id : languages_ids) {
            array.put(language_id);
        }

        return array;
    }

    private List<String> GetAllLanguages() {
        return ChoosenLanguages;
    }

    private List<String> GetLang(String search_query) {
        try {
            List<String> newdata = new ArrayList<>();
            mLanguage.open();
            Cursor cr = mLanguage.getListLanguage(search_query);
            cr.moveToFirst();
            newdata.add(cr.getString(1));
            while (cr.moveToNext()) {
                newdata.add(cr.getString(1));

            }
            List<String> languages= new ArrayList<>();
            languages.addAll(newdata);
            return languages;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mLanguage.close();
        }
    }


    List<String> ChoosenLanguages = new ArrayList<>();
    TextView Person_skill;



    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skills_v1);
        Bundle bundle = getIntent().getExtras();

        mLanguage = new Language(this);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        validator = new Validator(this);
        validator.setValidationListener(this);
        sessionManager = new SessionManager(this);
        editText = (AutoCompleteTextView) findViewById(R.id.skill);
        mobile = (RelativeLayout) findViewById(R.id.mobile);
        chosen_languages = (TagFlowLayout) findViewById(chosen_skills);



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

            }
        });


        final ArrayList<String> languages_list = new ArrayList<>();
        try {
            mLanguage.open();
            Cursor cursor = mLanguage.getAllEntries();
            cursor.moveToFirst();

            try {
                languages_list.add(cursor.getString(1));
                while (cursor.moveToNext()) {
                    languages_list.add(cursor.getString(1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mLanguage.close();
            }
        } catch (RuntimeException e) {

        }

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, languages_list);
        editText.setAdapter(adapter2);

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.text_skill);
                ChoosenLanguages.add(new String(textView.getText().toString()));
                chosen_languages.setAdapter(new TagAdapter<String>(ChoosenLanguages)
                {
                    @Override
                    public View getView(FlowLayout parent, int position, String s)
                    {
                        LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                                chosen_languages, false);
                        TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                        textView.setText(ChoosenLanguages.get(position));
                        return tv;
                    }
                });
                editText.setText("");
                editText.setHint("Add more !");

            }
        });


        chosen_languages.setAdapter(new TagAdapter<String>(ChoosenLanguages)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                        chosen_languages, false);
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(ChoosenLanguages.get(position));
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
                Call<SocialSignupResponse> call = apiService.AddUserLanguages(sessionManager.getEmail(), sessionManager.getUDID(), GetChoosenLangages());
                call.enqueue(new Callback<SocialSignupResponse>() {
                    @Override
                    public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {
                        if (response.body().getStatus() == 1) // new user
                        {
                            startActivity(new Intent(getApplicationContext(), WhyYouJoinNockerV1Activity.class));
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

}
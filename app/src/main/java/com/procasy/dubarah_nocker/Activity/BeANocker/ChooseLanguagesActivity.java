package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.AdapterCallback;
import com.procasy.dubarah_nocker.Adapter.SkillsAdapter;
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsAndLanguageResponse;
import com.procasy.dubarah_nocker.Model.Responses.SocialSignupResponse;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.RecyclerItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLanguagesActivity extends AppCompatActivity implements AdapterCallback {

    EditText search_lang;
    Language mLanguage;
    SessionManager sessionManager;
    RecyclerView languages_list;
    ArrayList<String> chosenLanguages = new ArrayList<>();
    ArrayList<String> languages = new ArrayList<>();
    LinearLayout next_btn;
    private APIinterface apiService;
    SkillsAdapter LanguagesAdapter;

    private JSONArray GetChoosenLangages() {
        ArrayList<Integer> languages_ids = new ArrayList<>();
        for (String s : chosenLanguages) {
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
        Log.e("choosenLanguages", chosenLanguages.toString());
        Log.e("Email", sessionManager.getEmail());
        Log.e("UDID", sessionManager.getUDID());
        JSONArray array = new JSONArray();
        for (Integer language_id : languages_ids) {
            array.put(language_id);
        }

        return array;
    }

    private List<String> GetAllLanguages() {
        return languages;
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
            languages= new ArrayList<>();
            languages.addAll(newdata);
            return languages;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mLanguage.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_languages);
        languages_list = (RecyclerView) findViewById(R.id.skill_list);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        search_lang = (EditText) findViewById(R.id.search_lang);

        search_lang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.e("search_query = ", s + "");
                try {

                    Log.e("search_query_av=", GetLang(s + "").toString());

                    LanguagesAdapter = new SkillsAdapter(getApplicationContext(), GetLang(s + ""), chosenLanguages);
                    languages_list.setAdapter(LanguagesAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        languages_list.setLayoutManager(linearLayoutManager);
        languages_list.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        sessionManager = new SessionManager(this);
        mLanguage = new Language(this);
        chosenLanguages.add(new String("English"));
        chosenLanguages.add(new String("French"));

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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else /// old user
                        {
                            Toast.makeText(getApplicationContext(), "There Has Been An Error !! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialSignupResponse> call, Throwable t) {

                    }
                });

            }
        });
      /*  chosen_languages.setAdapter(new TagAdapter<String>(chosenLanguages)
        {
            @Override
            public View getView(FlowLayout parent, final int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item, chosen_languages, false);
                Log.e("POSISTION",position+"");
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(chosenLanguages.get(position));
                return tv;
            }
        });

        chosen_languages.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                languages.add(chosenLanguages.get(position));
                languages_list.setAdapter(new SkillsAdapter(getApplicationContext(), languages, mAdapterCallback,chosenLanguages));
                chosenLanguages.remove(position);
                chosen_languages.setAdapter(new TagAdapter<String>(chosenLanguages)
                {
                    @Override
                    public View getView(FlowLayout parent, final int position, String s)
                    {
                        LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                                chosen_languages, false);
                        Log.e("POSISTION",position+"");
                        TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                        textView.setText(chosenLanguages.get(position));
                        return tv;
                    }
                });

                return false;
            }
        });

        search_skill_edit_text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                mLanguage.open();
                Cursor cursor = mLanguage.filterLanguages(s.toString());
                cursor.moveToFirst();
                ArrayList<String> names = new ArrayList<String>();
                while(!cursor.isAfterLast()) {
                    names.add(cursor.getString(cursor.getColumnIndex("skill_name")));
                    cursor.moveToNext();
                }
                cursor.close();
                mLanguage.close();
                for (String aa:names
                     ) {
                    System.out.println("filter result : "+aa);
                }
                languages_list.setAdapter(new SkillsAdapter(getApplicationContext(), names, mAdapterCallback,chosenLanguages));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                System.out.println("On Change :   "+s.toString());
            }
        });*/


        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();


        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        //// TODO: 7/30/2016  dont forget to modify session
        Call<AllSkillsAndLanguageResponse> call = apiService.GetAllSkills(sessionManager.getEmail(), sessionManager.getUDID());
        call.enqueue(new Callback<AllSkillsAndLanguageResponse>() {

            @Override
            public void onResponse(Call<AllSkillsAndLanguageResponse> call, Response<AllSkillsAndLanguageResponse> response) {

                Log.e("all_data_1", response.body().getAllLanguage().get(0).getLang_name());

                mLanguage.open();
                Log.e("remove state ", mLanguage.removeAllEntry() + "");

                languages.clear();
                try {
                    for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Language.COL_language_name, response.body().getAllLanguage().get(i).getLang_name());
                        contentValues.put(Language.COL_language_Id, response.body().getAllLanguage().get(i).getlang_id());
                        long state = mLanguage.insertEntry(contentValues);
                        Log.e("insert state ", state + "");
                        languages.add(response.body().getAllLanguage().get(i).getLang_name());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    mLanguage.close();
                    languages = new ArrayList<String>(GetAllLanguages());
                    LanguagesAdapter = new SkillsAdapter(getApplicationContext(), languages, chosenLanguages);
                    languages_list.setAdapter(LanguagesAdapter);


                }


                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AllSkillsAndLanguageResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
        GetChoosenLangages();

        languages_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        if (chosenLanguages.contains(languages.get(position))) {
                            chosenLanguages.remove(chosenLanguages.indexOf(languages.get(position)));
                            Log.d("Removed : ", languages.get(position));
                            LanguagesAdapter.notifyDataSetChanged();
                        } else {
                            chosenLanguages.add(languages.get(position));
                            Log.d("added : ", languages.get(position));
                            LanguagesAdapter.notifyDataSetChanged();

                        }
                    }
                })
        );

    }

    @Override
    public void onMethodCallback(String s) {
       /* chosenLanguages.add(new String(s));
        chosen_languages.setAdapter(new TagAdapter<String>(chosenLanguages)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                        chosen_languages, false);
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(chosenLanguages.get(position));
                return tv;
            }
        });*/
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

}

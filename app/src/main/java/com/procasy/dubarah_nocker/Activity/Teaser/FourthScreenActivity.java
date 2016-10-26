package com.procasy.dubarah_nocker.Activity.Teaser;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.procasy.dubarah_nocker.Helper.Language;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.WhyUJoinModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;


public class FourthScreenActivity extends AppCompatActivity implements Validator.ValidationListener{
    LinearLayout next_btn;
    LinearLayout back_btn;
    LinearLayout Whole_btn;
    TextView t1,t2,t3,t4,t5,t6,t7;
    @NotEmpty
    AutoCompleteTextView editText;
    Language mLanguages;
    RadioGroup radioGroup ;
   // RecyclerView recyclerView;
   // JoinAdapter adapter;
    List<WhyUJoinModel> data;

    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    SessionManager sessionManager;
    String name;
    Validator validator;
    @Deprecated
    void DemoData() {
        data.add(new WhyUJoinModel("Pay the bills", false));
        data.add(new WhyUJoinModel("Get their business off the ground", false));
        data.add(new WhyUJoinModel("Fund their tuition", false));
        data.add(new WhyUJoinModel("Fund their child's or spouse's tuition", false));
        data.add(new WhyUJoinModel("Fund their child's extra-curricular activities", false));
        data.add(new WhyUJoinModel("Fund their vacation", false));
        data.add(new WhyUJoinModel("Settle in a new country", false));
    }

    // Get All User Reasons
    private List<String> GetReasons() {
        List<String> newdata = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).IsCheck)
                newdata.add(data.get(i).name);

        }

        return newdata;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("I'm Fourth screen avtivity ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fourthscreen);
        Bundle bundle = getIntent().getExtras();
        mLanguages = new Language(this);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        back_btn = (LinearLayout) findViewById(R.id.back_btn);
        Whole_btn = (LinearLayout) findViewById(R.id.wholething);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t4 = (TextView) findViewById(R.id.t4);
        t7 = (TextView) findViewById(R.id.t7);
        editText = (AutoCompleteTextView) findViewById(R.id.skill);
        name = bundle.getString("name");
        System.out.println(name.split("\\s+")[0]);
        t2.setText(name.split("\\s+")[0]);
        editText.setHint("Add "+name.split("\\s+")[0]+"\'s "+"mother language");
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/font3.ttf");

        t1.setTypeface(typface);
        t2.setTypeface(bold);
        t3.setTypeface(typface);
        t5.setTypeface(typface);
        t6.setTypeface(typface);
        t4.setTypeface(typface);
        t7.setTypeface(bold);
        t7.setText(name.split("\\s+")[0]);
        //recyclerView = (RecyclerView) findViewById(R.id.all_reason);
validator = new Validator(this);
        validator.setValidationListener(this);
        data = new ArrayList<>();

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        // TODO Replace This Fucking Method with your Fucking Real Data
        DemoData();
        Fill_Radiogroup();
       // recyclerView.setLayoutManager(gaggeredGridLayoutManager);

       // adapter = new JoinAdapter(getApplicationContext(), data);

       // recyclerView.setAdapter(adapter);

        ArrayList<String> languages_list = new ArrayList<>();
        mLanguages.open();
        Cursor cursor = mLanguages.getAllEntries();
        cursor.moveToFirst();

        try {
            languages_list.add(cursor.getString(1));
            while (cursor.moveToNext()) {
                languages_list.add(cursor.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mLanguages.close();
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.predictionitem, languages_list);
        editText.setAdapter(adapter2);
        sessionManager = new SessionManager(this);

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
                Intent intent = new Intent(getApplicationContext(),RateSkillActivity.class);
                sessionManager.setKeyHisName(name);
                sessionManager.setKeyHisLanguage(editText.getText().toString());
                startActivity(intent);
                //any other associated action
            }
        }, 400);    }

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

    private void Fill_Radiogroup() {
        for (int i = 0; i < data.size(); i++) {
            radioGroup = (RadioGroup) findViewById(R.id.radgroup2);
            final RadioButton rad = new RadioButton(getApplicationContext());
            rad.setButtonDrawable(R.drawable.apptheme_btn_radio_holo_light);
            rad.setChecked(false);
            rad.setText(data.get(i).name);
            rad.setTextColor(getResources().getColor(R.color.intercomsdk_center_white));

            rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rad.isChecked()) {
                        rad.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        rad.setTextColor(getResources().getColor(R.color.intercomsdk_center_white));
                    }
                }
            });
            data.get(i).IsCheck = rad.isChecked();
            radioGroup.addView(rad);

        }

    }
    private class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.ProductViewHolder> {

        List<WhyUJoinModel> mdata;
        Context mContext;

        // Conctracture
        public JoinAdapter(Context context, List<WhyUJoinModel> newdata) {
            this.mdata = newdata;
            mContext = context;
        }


        // Notify Data Changed

        public void setData(List<WhyUJoinModel> homeGroups) {

            mdata.clear();
            mdata.addAll(homeGroups);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return mdata.size();
        }


        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.why_u_join_activity, null);
            return new ProductViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder productViewHolder, int position) {

            // Get one item of AdapterView
            WhyUJoinModel group = mdata.get(position);
            // Set Data One Holder
            productViewHolder.reason_anme.setText(group.name);

            if (group.IsCheck) {
                productViewHolder.isreason.setVisibility(View.VISIBLE);
                productViewHolder.reason_anme.setTextColor(getResources().getColor(R.color.white));          }
            else {
                productViewHolder.isreason.setVisibility(View.GONE);
                productViewHolder.reason_anme.setTextColor(getResources().getColor(R.color.very_primary_dark));
            }
        }

        ///  inner class
        // Put Your Layout Model Here

        public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView isreason;
            public TextView reason_anme;

            public ProductViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                isreason = ((ImageView) itemView.findViewById(R.id.reason_state));
                reason_anme = ((TextView) itemView.findViewById(R.id.reason_name));
                Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
                reason_anme.setTypeface(typface);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                for (int i = 0; i < mdata.size(); i++) {
                    mdata.get(i).IsCheck = false;
                }

                mdata.get(getPosition()).IsCheck = true;

                notifyDataSetChanged();
            }


        }
    }

}

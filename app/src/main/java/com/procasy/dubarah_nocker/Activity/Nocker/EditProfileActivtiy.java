package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.CustomTypefaceSpan;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivtiy extends AppCompatActivity {

    WheelPicker days, time_1, time_2;
    Button add_new_date;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    List<String> mdata;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activtiy);
        recyclerView = (RecyclerView) findViewById(R.id.available_date);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        days = (WheelPicker) findViewById(R.id.days);
        time_1 = (WheelPicker) findViewById(R.id.time_1);
        time_2 = (WheelPicker) findViewById(R.id.time_2);
        add_new_date = (Button) findViewById(R.id.add_new_available_date);
        recyclerView = (RecyclerView) findViewById(R.id.available_date);
        mdata = new ArrayList<>();

        List<String> week_days = new ArrayList<>();
        week_days.add(getString(R.string.str95));
        week_days.add(getString(R.string.str96));
        week_days.add(getString(R.string.str97));
        week_days.add(getString(R.string.str98));
        week_days.add(getString(R.string.str99));
        week_days.add(getString(R.string.str100));
        week_days.add(getString(R.string.str101));

        days.setData(week_days);

        List<String> time_1_data = new ArrayList<>();
        time_1_data.add("1 AM");
        time_1_data.add("2 AM");
        time_1_data.add("3 AM");
        time_1_data.add("4 AM");
        time_1_data.add("5 AM");
        time_1_data.add("6 AM");
        time_1_data.add("7 AM");
        time_1_data.add("8 AM");
        time_1_data.add("9 AM");
        time_1_data.add("10 AM");
        time_1_data.add("11 AM");
        time_1_data.add("12 AM");
        time_1_data.add("1 PM");
        time_1_data.add("2 PM");
        time_1_data.add("3 PM");
        time_1_data.add("4 PM");
        time_1_data.add("5 PM");
        time_1_data.add("6 PM");
        time_1_data.add("7 PM");
        time_1_data.add("8 PM");
        time_1_data.add("9 PM");
        time_1_data.add("10 PM");
        time_1_data.add("11 PM");
        time_1_data.add("12 PM");

        time_1.setData(time_1_data);
        time_2.setData(time_1_data);

        mdata = new ArrayList<>();
        mdata.add("Saturday 9 AM - 3 PM");


        customAdapter = new CustomAdapter(getApplicationContext(), mdata);

        add_new_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recyclerView.setAdapter(customAdapter);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //recyclerView.setLayoutManager(linearLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface typface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/font1.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Edit Profile");
        SS.setSpan(new CustomTypefaceSpan("", typface), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(SS);

    }

    /////////////////////////////////////////////////////////


    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ProductViewHolder> {

        Context mContext;
        List<String> mdata;

        public CustomAdapter(Context context, List<String> newdata) {
            mContext = context;
            this.mdata = newdata;
            Log.e("tagad", "1");

        }


        // Notify Data Changed

        public void setData(List<String> homeGroups) {

            this.mdata.clear();
            this.mdata.addAll(homeGroups);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            Log.e("tagad", "2");

            return this.mdata.size();
        }


        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_listing, null);
            return new ProductViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder productViewHolder, int position) {
            Log.e("tagad", "3");

            // Get one item of AdapterView
            String group = mdata.get(position);
            // Set Data One Holder
            productViewHolder.skill_name.setText(group);

        }

        ///  inner class
        // Put Your Layout Model Here

        public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView skill_name;
            public ImageView skill_add;


            public ProductViewHolder(View itemView) {
                super(itemView);
                Log.e("tagad", "4");

                itemView.setOnClickListener(this);
                skill_name = (TextView) itemView.findViewById(R.id.skill_name);
                skill_add = (ImageView) itemView.findViewById(R.id.add_skill);
                skill_add.setImageResource(R.drawable.one_skill_delete);

            }

            @Override
            public void onClick(View v) {
                System.out.println("Group ");
            }
        }
    }



}

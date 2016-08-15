package com.procasy.dubarah_nocker.Activity.Nocker;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.CustomTypefaceSpan;
import com.procasy.dubarah_nocker.Utils.RecyclerItemClickListener;

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


        days = (WheelPicker) findViewById(R.id.days);
        time_1 = (WheelPicker) findViewById(R.id.time_1);
        time_2 = (WheelPicker) findViewById(R.id.time_2);
        add_new_date = (Button) findViewById(R.id.add_new_available_date);
        recyclerView = (RecyclerView) findViewById(R.id.available_date);
        mdata = new ArrayList<>();

        List<String> week_days = new ArrayList<>();
        week_days.add("Saturday");
        week_days.add("Sunday");
        week_days.add("Monday");
        week_days.add("Tuesday");
        week_days.add("Wednesday");
        week_days.add("Thursday");
        week_days.add("Thursday");

        days.setData(week_days);

        List<String> time_1_data = new ArrayList<>();
        time_1_data.add("1 am");
        time_1_data.add("2 am");
        time_1_data.add("3 am");
        time_1_data.add("4 am");
        time_1_data.add("5 am");
        time_1_data.add("6 am");
        time_1_data.add("7 am");
        time_1_data.add("8 am");
        time_1_data.add("9 am");
        time_1_data.add("10 am");
        time_1_data.add("11 am");
        time_1_data.add("12 am");
        time_1_data.add("1 pm");
        time_1_data.add("2 pm");
        time_1_data.add("3 pm");
        time_1_data.add("4 pm");
        time_1_data.add("5 pm");
        time_1_data.add("6 pm");
        time_1_data.add("7 pm");
        time_1_data.add("8 pm");
        time_1_data.add("9 pm");
        time_1_data.add("10 pm");
        time_1_data.add("11 pm");
        time_1_data.add("12 pm");

        time_1.setData(time_1_data);
        time_2.setData(time_1_data);

        mdata = new ArrayList<>();
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("asd");
        mdata.add("aasdsd");
        mdata.add("asd");

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

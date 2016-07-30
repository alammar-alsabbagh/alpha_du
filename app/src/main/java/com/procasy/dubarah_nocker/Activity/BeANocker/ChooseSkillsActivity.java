package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Adapter.AdapterCallback;
import com.procasy.dubarah_nocker.Adapter.SkillsAdapter;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Helper.Skills;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.Responses.AllSkillsResponse;
import com.procasy.dubarah_nocker.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseSkillsActivity extends AppCompatActivity implements AdapterCallback {

    Skills mskills;
    SessionManager sessionManager;
    RecyclerView skill_list;
    TagFlowLayout chosen_skills;
    ArrayList<String> chosenSkills = new ArrayList<>();
    ArrayList<String> skills = new ArrayList<>();
    private AdapterCallback mAdapterCallback;
    LinearLayout next_btn;

    private List<String> GetAllSkills()
    {

        return skills;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skills);
        skill_list = (RecyclerView) findViewById(R.id.skill_list);
        chosen_skills = (TagFlowLayout) findViewById(R.id.chosen_skills);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        this.mAdapterCallback = ((AdapterCallback) this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        skill_list.setLayoutManager(linearLayoutManager);
        skill_list.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        sessionManager = new SessionManager(this);
        mskills = new Skills(this);
        chosenSkills.add(new String("Piano Lessons"));
        chosenSkills.add(new String("Tap Dancing"));
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        chosen_skills.setAdapter(new TagAdapter<String>(chosenSkills)
        {
            @Override
            public View getView(FlowLayout parent, final int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                        chosen_skills, false);

                Log.e("POSISTION",position+"");
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(chosenSkills.get(position));
                return tv;
            }
        });

        chosen_skills.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                skills.add(chosenSkills.get(position));
                skill_list.setAdapter(new SkillsAdapter(getApplicationContext(), skills, mAdapterCallback));
                chosenSkills.remove(position);
                chosen_skills.setAdapter(new TagAdapter<String>(chosenSkills)
                {
                    @Override
                    public View getView(FlowLayout parent, final int position, String s)
                    {
                        LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                                chosen_skills, false);
                        Log.e("POSISTION",position+"");
                        TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                        textView.setText(chosenSkills.get(position));
                        return tv;
                    }
                });

                return false;
            }
        });



        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Getting Info..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();


        APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
        //// TODO: 7/30/2016  dont forget to modify session
        Call<AllSkillsResponse> call = apiService.GetAllSkills("bilooo@hotmail.com", "User12345");
        call.enqueue(new Callback<AllSkillsResponse>() {
            @Override
            public void onResponse(Call<AllSkillsResponse> call, Response<AllSkillsResponse> response) {

                mskills.open();
                Log.e("remove state ", mskills.removeAllEntry() + "");

                skills.clear();
                try {
                    for (int i = 0; i < response.body().getAllSkills().size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Skills.COL_skillname, response.body().getAllSkills().get(i).getSkill_name());
                        contentValues.put(Skills.COL_is_hidden, response.body().getAllSkills().get(i).getIs_hidden());
                        contentValues.put(Skills.COL_SKILL_ID, response.body().getAllSkills().get(i).getSkill_id());
                        long state = mskills.insertEntry(contentValues);
                        Log.e("insert state ", state + "");
                        skills.add(response.body().getAllSkills().get(i).getSkill_name());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    mskills.close();
                    skills = new ArrayList<String>(GetAllSkills());
                    skill_list.setAdapter(new SkillsAdapter(getApplicationContext(), skills, mAdapterCallback));
                }


                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AllSkillsResponse> call, Throwable t) {
                System.out.println("ERROR 2" + t.toString());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });

    }

    @Override
    public void onMethodCallback(String s) {
        chosenSkills.add(new String(s));
        chosen_skills.setAdapter(new TagAdapter<String>(chosenSkills)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                LinearLayout tv = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.one_skill_item,
                        chosen_skills, false);
                TextView textView = (TextView) tv.findViewById(R.id.skill_name);
                textView.setText(chosenSkills.get(position));
                return tv;
            }
        });
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

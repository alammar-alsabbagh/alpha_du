package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Adapter.AdapterCallback;
import com.procasy.dubarah_nocker.Adapter.SkillsAdapter;
import com.procasy.dubarah_nocker.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class ChooseSkillsActivity extends AppCompatActivity implements AdapterCallback {
    RecyclerView skill_list;
    TagFlowLayout chosen_skills;
    ArrayList<String> chosenSkills = new ArrayList<>();
    ArrayList<String> skills = new ArrayList<>();
    private AdapterCallback mAdapterCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skills);
        skill_list = (RecyclerView) findViewById(R.id.skill_list);
        chosen_skills = (TagFlowLayout) findViewById(R.id.chosen_skills);
        this.mAdapterCallback = ((AdapterCallback) this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        skill_list.setLayoutManager(linearLayoutManager);
        skill_list.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        skills.add(new String("Guitar Lessons"));
        skills.add(new String("Gardening"));
        skills.add(new String("Cook"));
        skills.add(new String("Maid"));
        skills.add(new String("Nanny"));


        chosenSkills.add(new String("Piano Lessons"));
        chosenSkills.add(new String("Tap Dancing"));

        skill_list.setAdapter(new SkillsAdapter(getApplicationContext(), skills,mAdapterCallback));
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

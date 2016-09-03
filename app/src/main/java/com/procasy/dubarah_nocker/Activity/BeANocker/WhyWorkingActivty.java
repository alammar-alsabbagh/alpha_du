package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.Model.WhyModel;
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class WhyWorkingActivty extends AppCompatActivity {


    SessionManager sessionManager;
    RecyclerView causes_list;
    ArrayList<WhyModel> Causes = new ArrayList<>();
    LinearLayout next_btn;
    private APIinterface apiService;
    CausesAdapter causesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_working_activty);
        causes_list = (RecyclerView) findViewById(R.id.skill_list);
        next_btn = (LinearLayout) findViewById(R.id.next_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        causes_list.setLayoutManager(linearLayoutManager);
        causes_list.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        sessionManager = new SessionManager(this);

        Causes.add(new WhyModel(getString(R.string.str86), false));
        Causes.add(new WhyModel(getString(R.string.str87), false));
        Causes.add(new WhyModel(getString(R.string.str88), false));
        Causes.add(new WhyModel(getString(R.string.str89), false));
        Causes.add(new WhyModel(getString(R.string.str90), false));
        Causes.add(new WhyModel(getString(R.string.str91), false));
        Causes.add(new WhyModel(getString(R.string.str92), false));
        Causes.add(new WhyModel(getString(R.string.str93), false));
        Causes.add(new WhyModel(getString(R.string.str94), false));

        causesAdapter = new CausesAdapter(getApplicationContext(), Causes);

        causes_list.setAdapter(causesAdapter);
        apiService = ApiClass.getClient().create(APIinterface.class);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Call<SocialSignupResponse> call = apiService.AddUserSkill(sessionManager.getEmail(),sessionManager.getUDID(),GetChoosenSkilles());
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
                            Toast.makeText(getApplicationContext(),"There Has Been An Error !! ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialSignupResponse> call, Throwable t) {

                    }
                });*/

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

    public class CausesAdapter extends RecyclerView.Adapter<CausesAdapter.CauseViewHolder> {

        List<WhyModel> mdata;
        Context mContext;

        public CausesAdapter(Context context, List<WhyModel> newdata) {
            mContext = context;
            mdata = newdata;
        }


        // Notify Data Changed
        void update(List<WhyModel> newdata) {
            mdata.clear();
            mdata.addAll(newdata);
            notifyDataSetChanged();

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }


        @Override
        public CauseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_cause_item, null);
            return new CauseViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(CausesAdapter.CauseViewHolder holder, int position) {
            WhyModel data = mdata.get(position);
            holder.name.setText(data.question);
            if (data.ischecked) {
                holder.name.setBackgroundColor(getResources().getColor(R.color.blue_grey));
                holder.alliteam.setBackgroundColor(getResources().getColor(R.color.blue_grey));
            }else{
                holder.name.setBackgroundColor(Color.TRANSPARENT);
                holder.alliteam.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        public class CauseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView name;
            private LinearLayout alliteam;

            public CauseViewHolder(View itemView) {
                super(itemView);
                alliteam = (LinearLayout) itemView.findViewById(R.id.allitem);
                name = ((TextView) itemView.findViewById(R.id.skill_name));
                //itemView.setOnClickListener(this);
                alliteam.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                List<WhyModel> newdata = new ArrayList<>();
                for (int i = 0; i < mdata.size(); i++) {
                    Log.e("state",mdata.get(i).ischecked+"");
                    newdata.add(new WhyModel(mdata.get(i).question, false));
                }

                newdata.get(getPosition()).ischecked = true;

                update(newdata);
            }
        }
    }

}

package com.procasy.dubarah_nocker.Activity.BeANocker;

import android.content.Context;
import android.graphics.Canvas;
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
import com.procasy.dubarah_nocker.R;
import com.procasy.dubarah_nocker.Utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class WhyWorkingActivty extends AppCompatActivity {


    SessionManager sessionManager;
    RecyclerView causes_list;
    ArrayList<String> chosenCause = new ArrayList<>();
    ArrayList<String> Causes = new ArrayList<>();
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
        Causes.add("Reasons nocker is looking to work: Pay the bills");
        Causes.add("Get my business off the ground");
        Causes.add("Fund my tuition");
        Causes.add("Fund my child's or spouse's tuition");
        Causes.add("Fund my child's extra-curricular activities");
        Causes.add("Fund my vacation");
        Causes.add("Fund my renovation");
        Causes.add("Fund my retirement");
        Causes.add("Settle in a new country");
        Causes.add("Save to buy a home");
        Causes.add("Fund my next project");
        causesAdapter = new CausesAdapter(getApplicationContext(),Causes,chosenCause);
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



        causes_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new   RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        if(chosenCause.size() > 0) {
                            Log.d("Cleared : ",Causes.get(position));
                            chosenCause.clear();
                            chosenCause.add(Causes.get(position));
                            Log.d("Added : ",Causes.get(position));
                            causesAdapter.notifyDataSetChanged();
                        }
                        else {
                            chosenCause.add(Causes.get(position));
                            Log.d("added : ",Causes.get(position));
                            causesAdapter.notifyDataSetChanged();
                        }
                    }
                })
        );

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

        List<String> mdata,choosen;
        Context mContext;

        public CausesAdapter(Context context, List<String> newdata,List<String>choosen) {
            mContext = context;
            mdata = newdata;
            this.choosen = choosen;
        }


        // Notify Data Changed


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
            String data = mdata.get(position);
            holder.name.setText(data);
          /*  try{
                if(holder.name.equals(choosen.get(0)))
                    holder.wholething.setBackgroundResource(R.drawable.selector1);
            }catch(IndexOutOfBoundsException ex)
            {
                ex.printStackTrace();
            }
*/

        }

        public class CauseViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public LinearLayout wholething;
            public CauseViewHolder(View itemView) {
                super(itemView);
                wholething = (LinearLayout) itemView.findViewById(R.id.linearLayout);
                name = ((TextView) itemView.findViewById(R.id.skill_name));

            }
        }
    }

}

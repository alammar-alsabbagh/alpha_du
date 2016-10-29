package com.procasy.dubarah_nocker.Adapter;

/**
 * Created by Omar on 10/25/2016.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.procasy.dubarah_nocker.API.APIinterface;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Helper.SessionManager;
import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.QouteModel;
import com.procasy.dubarah_nocker.Model.Responses.NormalResponse;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<QouteModel> data;
    private List<Boolean> expandState = new ArrayList<>();
    private String skill;
    SessionManager sessionManager;
    Context context;

    public RecyclerViewRecyclerAdapter(final List<QouteModel> data, String skill,Context context) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.add(i, false);
        }
        this.skill = skill;
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.quota_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QouteModel item = data.get(position);

        holder.setIsRecyclable(false);
        holder.nocker_name.setText(item.nocker_name);
        holder.nocker_skill.setText(item.nocker_address);
        holder.pref_time.setText(item.qoute_time);
        holder.pref_date.setText(item.qoute_date);
        holder.job_name.setText(skill);
        holder.qoute_id = Integer.parseInt(item.qoute_id);

        try {
            if (item.qoute_description.equals("null"))
                holder.description.setText("No content");
            else
                holder.description.setText(item.qoute_description);
        } catch (NullPointerException e) {
            holder.description.setText("No content");
        }

        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIinterface apiService = ApiClass.getClient().create(APIinterface.class);
                Call<NormalResponse> call = apiService.accept_qoute_from_user(sessionManager.getEmail(),sessionManager.getUDID(),holder.qoute_id);
                call.enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        if(response.body().status == 1) {
                            Toast.makeText(context, "You have accepted this qouta please check the calender", Toast.LENGTH_SHORT).show();
                            data.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }else
                        {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        System.out.println("ERROR 2" + t.toString());
                    }

                });
            }
        });

        holder.CheckProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                expandState.set(position, true);
            }

            @Override
            public void onPreClose() {
                expandState.set(position, false);
            }
        });

        //  holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout, position);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout, int position) {
        if (expandState.get(position))
            expandableLayout.collapse();
        else
            expandableLayout.expand();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout buttonLayout;
        private TextView nocker_name, nocker_skill, job_name;
        private CircleImageView circleImageView;
        private ScrollView scrollView;
        private TextView description, pref_date, pref_time;
        public ExpandableRelativeLayout expandableLayout;
        public Button Accept, CheckProfile;
        public int qoute_id;

        public ViewHolder(View v) {
            super(v);
            buttonLayout = (LinearLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout);
            nocker_name = (TextView) v.findViewById(R.id.nocker_name);
            nocker_skill = (TextView) v.findViewById(R.id.nocker_skill);
            scrollView = (ScrollView) v.findViewById(R.id.qoute_request);
            description = (TextView) v.findViewById(R.id.description);
            circleImageView = (CircleImageView) buttonLayout.findViewById(R.id.profile_pic);
            pref_date = (TextView) v.findViewById(R.id.pref_date);
            pref_time = (TextView) v.findViewById(R.id.pref_time);
            job_name = (TextView) v.findViewById(R.id.job_name);
            Accept = (Button) v.findViewById(R.id.accept);
            CheckProfile = (Button) v.findViewById(R.id.check_profile);
        }
    }


}
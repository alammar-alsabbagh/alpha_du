package com.procasy.dubarah_nocker.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.procasy.dubarah_nocker.Model.HelpRequestModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 10/25/2016.
 */

public class QuoteRecyclerViewAdapter  extends RecyclerView.Adapter<QuoteRecyclerViewAdapter.ViewHolder> {

    private final List<HelpRequestModel> data;
    private Context context;
    private List<Boolean> expandState = new ArrayList<>();

    public QuoteRecyclerViewAdapter(final List<HelpRequestModel> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.add(false);
        }
    }


    @Override
    public QuoteRecyclerViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new QuoteRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.help_group, parent, false));
    }

    @Override
    public void onBindViewHolder(final QuoteRecyclerViewAdapter.ViewHolder holder, final int position) {
        final HelpRequestModel item = data.get(position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(item.qouteModels,item.skill_name,context));
        holder.Skill.setText(data.get(position).skill_name);
        if(item.qouteModels.size() == 0)
        {
            holder.Number.setText("No one has responded yet !");
        }else
        {
           if(item.qouteModels.size() == 1)
               holder.Number.setText("1 nocker has responded");
            else
               holder.Number.setText(item.qouteModels.size()+" nockers have responded");
        }
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        holder.expandableLayout.collapse();
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
                onClickButton(holder.expandableLayout);

            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
        System.out.println("haahah");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout buttonLayout;
        public ExpandableRelativeLayout expandableLayout;
        public RecyclerView recyclerView;
        public TextView Skill,Number;

        public ViewHolder(View v) {
            super(v);
            buttonLayout = (LinearLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout);
            recyclerView = (RecyclerView) v.findViewById(R.id.quota_list);
            Skill = (TextView) v.findViewById(R.id.nocker_skill);
            Number = (TextView) v.findViewById(R.id.number);

        }
    }

}
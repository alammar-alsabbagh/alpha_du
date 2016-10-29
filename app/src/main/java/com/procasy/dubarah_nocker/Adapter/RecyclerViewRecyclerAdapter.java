package com.procasy.dubarah_nocker.Adapter;

/**
 * Created by Omar on 10/25/2016.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.procasy.dubarah_nocker.Model.QouteModel;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<QouteModel> data;
    private Context context;
    private List<Boolean> expandState = new ArrayList<>();

    public RecyclerViewRecyclerAdapter(final List<QouteModel> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.add(i, false);
        }
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
       /* Picasso.with(context)
                .load(ApiClass.Pic_Base_URL + item.user_img)
                .placeholder(R.drawable.drawable_photo)
                .error(R.drawable.drawable_photo)
                .into((holder.circleImageView));*/

        holder.description.setText( item.qoute_description);
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));

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
                onClickButton(holder.expandableLayout,position);

            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout , int position) {
        if(expandState.get(position))
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
        private TextView nocker_name , nocker_skill;
        private CircleImageView circleImageView ;
        private ScrollView scrollView ;
        private TextView description ;
        public ExpandableRelativeLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            buttonLayout = (LinearLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableRelativeLayout) v.findViewById(R.id.expandableLayout);
            nocker_name =(TextView) v.findViewById(R.id.nocker_name);
            nocker_skill = (TextView)v.findViewById(R.id.nocker_skill) ;
            scrollView= (ScrollView) v.findViewById(R.id.qoute_request);
            description = (TextView) v.findViewById(R.id.description) ;
            circleImageView = (CircleImageView)buttonLayout.findViewById(R.id.profile_pic) ;

        }
    }


}
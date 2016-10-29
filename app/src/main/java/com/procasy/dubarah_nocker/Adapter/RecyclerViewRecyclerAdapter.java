package com.procasy.dubarah_nocker.Adapter;

/**
 * Created by Omar on 10/25/2016.
 */


import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.google.android.gms.vision.text.Text;
import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Model.QouteModel;
import com.procasy.dubarah_nocker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<QouteModel> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private TextView nocker_name,nocker_skill;
    private CircleImageView circleImageView ;
    private ScrollView scrollView ;
    private String skill_name ;
    private TextView description ;
    public RecyclerViewRecyclerAdapter(final List<QouteModel> data,String skill_name) {
        this.data = data;
        this.skill_name = skill_name ;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
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
        nocker_name =(TextView) holder.buttonLayout.findViewById(R.id.nocker_name);
        nocker_name.setText(data.get(position).nocker_name);
        nocker_skill = (TextView)holder.buttonLayout.findViewById(R.id.nocker_skill) ;
        nocker_skill.setText(skill_name);
        circleImageView = (CircleImageView)holder.buttonLayout.findViewById(R.id.profile_pic) ;
        Picasso.with(context)
                .load(ApiClass.Pic_Base_URL + data.get(position).user_img)
                .placeholder(R.drawable.drawable_photo)
                .error(R.drawable.drawable_photo)
                .into((circleImageView));
        scrollView= (ScrollView)holder.expandableLayout.findViewById(R.id.qoute_request);
        description = (TextView)scrollView.findViewById(R.id.description) ;
        description.setText( data.get(position).qoute_description);
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                expandState.put(position, false);
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout buttonLayout;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            buttonLayout = (LinearLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
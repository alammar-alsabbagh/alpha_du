package com.procasy.dubarah_nocker.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.procasy.dubarah_nocker.API.ApiClass;
import com.procasy.dubarah_nocker.Model.Responses.NockerResponse;
import com.procasy.dubarah_nocker.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Omar on 5/24/2016.
 */
public class NearByNockersAdapter extends RecyclerView.Adapter<NearByNockersAdapter.ViewHolder> {


    private List<NockerResponse> mValues;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView nocker_name;
        public final TextView skill_name;
        public final TextView nocker_distance;
        public final CircleImageView circleImageView;
        public final ImageView is_online;
        public final TextView is_online_text;

        public ViewHolder(View view, final Context context) {
            super(view);

            nocker_name = (TextView) view.findViewById(R.id.nocker_name);
            skill_name = (TextView) view.findViewById(R.id.nocker_skill);
            nocker_distance = (TextView) view.findViewById(R.id.nocker_distance);
            circleImageView = (CircleImageView) view.findViewById(R.id.profilePic);
            is_online = (ImageView) view.findViewById(R.id.is_online);
            is_online_text = (TextView) view.findViewById(R.id.online_text);

            Typeface typface = Typeface.createFromAsset(context.getAssets(), "fonts/font1.ttf");
            nocker_name.setTypeface(typface);
            skill_name.setTypeface(typface);
            nocker_distance.setTypeface(typface);
            is_online_text.setTypeface(typface);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Still Under Develpoment!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + skill_name.getText();
        }
    }

    public NockerResponse getValueAt(int position) {
        return mValues.get(position);
    }

    public NearByNockersAdapter(Context context, List<NockerResponse> items) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.near_by_nocker_listing, parent, false);
        return new ViewHolder(view, context);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.skill_name.setText(mValues.get(position).getSkill());
        holder.nocker_name.setText(mValues.get(position).getuser_fname() + " " + mValues.get(position).getUser_lname());
        holder.nocker_distance.setText(mValues.get(position).getDist_text());
        Picasso.with(context)
                .load(ApiClass.Pic_Base_URL + mValues.get(position).getUser_img())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.circleImageView);
        System.out.println(ApiClass.Pic_Base_URL + mValues.get(position).getUser_img());
        if (mValues.get(position).getUser_is_online() == 1) {
            holder.is_online.setImageResource(R.drawable.online_circle);
            holder.is_online_text.setText("Online");
        } else {
            holder.is_online.setImageResource(R.drawable.offline_circle);
            holder.is_online_text.setText("Offline");
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}

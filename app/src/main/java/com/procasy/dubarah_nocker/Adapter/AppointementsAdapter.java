package com.procasy.dubarah_nocker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Model.Appointement;
import com.procasy.dubarah_nocker.R;

import java.util.List;

/**
 * Created by Omar on 10/22/2016.
 */


public class AppointementsAdapter extends RecyclerView.Adapter<AppointementsAdapter.MyViewHolder> {

    private List<Appointement> appointementList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, time , nocker_name , skill , ammount;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.appointment_row_date);
            time = (TextView) view.findViewById(R.id.appointment_row_time);
            nocker_name = (TextView) view.findViewById(R.id.appointment_row_nocker_name);
            skill = (TextView) view.findViewById(R.id.appointment_row_skill);
            ammount = (TextView) view.findViewById(R.id.appointment_row_cost);
        }
    }


    public AppointementsAdapter(List<Appointement> appointementList) {
        this.appointementList = appointementList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Appointement notification = appointementList.get(position);
        holder.date.setText(notification.getAppointement_date());
        holder.time.setText(notification.getAppointement_time());
        holder.nocker_name.setText(notification.getAppointement_nocker_name());
        holder.skill.setText(notification.getAppointement_skill());
        holder.ammount.setText(notification.getAppointement_ammount());

    }

    @Override
    public int getItemCount() {
        return appointementList.size();
    }
}
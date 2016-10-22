package com.procasy.dubarah_nocker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.procasy.dubarah_nocker.Model.Notification;
import com.procasy.dubarah_nocker.R;

import java.util.List;

/**
 * Created by Omar on 10/22/2016.
 */


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<Notification> notificationsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descr;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.notification_title);
            descr = (TextView) view.findViewById(R.id.notification_desc);
        }
    }


    public NotificationAdapter(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.title.setText(notification.getNotification_title());
        holder.descr.setText(notification.getNotification_descr());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
}
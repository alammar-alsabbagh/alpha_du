package com.procasy.dubarah_nocker.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.procasy.dubarah_nocker.MainActivity;
import com.procasy.dubarah_nocker.Model.Notification;
import com.procasy.dubarah_nocker.R;

import java.util.List;

/**
 * Created by Omar on 10/22/2016.
 */


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<com.procasy.dubarah_nocker.Model.Notification> notificationsList;
    com.procasy.dubarah_nocker.Helper.Notification notification;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descr;
        public ImageView status_cicle;
        public int id;
        public boolean status;


        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.notification_title);
            descr = (TextView) view.findViewById(R.id.notification_desc);
            status_cicle = (ImageView) view.findViewById(R.id.status);
            if(!status)
            {
                status_cicle.setVisibility(View.INVISIBLE);
            }else
            {
                status_cicle.setVisibility(View.VISIBLE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!status)
                    {
                        notification = new com.procasy.dubarah_nocker.Helper.Notification(context);
                        notification.open();
                        notification.updateStatus(id);
                        notification.close();
                        status_cicle.setVisibility(View.INVISIBLE);
                        MainActivity.getInstance().updateNotification();
                    }
                }
            });
        }
    }


    public NotificationAdapter(List<Notification> notificationsList , Context context) {
        this.notificationsList = notificationsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new MyViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.setIsRecyclable(false);
        holder.title.setText(notification.getNotification_title());
        holder.descr.setText(notification.getNotification_descr());
        holder.id = notification.notification_id;
        holder.status = notification.Seen;
        if(holder.status)
            holder.status_cicle.setVisibility(View.INVISIBLE);
        else
            holder.status_cicle.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }
}
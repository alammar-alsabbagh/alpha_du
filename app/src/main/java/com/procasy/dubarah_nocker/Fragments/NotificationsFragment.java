package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.procasy.dubarah_nocker.Adapter.NotificationAdapter;
import com.procasy.dubarah_nocker.Model.Notification;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationsFragment extends Fragment {

    public RecyclerView notificationsRecyclerView;
    public static NotificationAdapter adapter;
    com.procasy.dubarah_nocker.Helper.Notification mNotification;



    public NotificationsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotification = new com.procasy.dubarah_nocker.Helper.Notification(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        List<Notification> notificationList = new ArrayList<>();
        notificationList = GetNotifications();

        notificationsRecyclerView = (RecyclerView) view.findViewById(R.id.notification_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notificationsRecyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationAdapter(notificationList,getActivity());
        notificationsRecyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<Notification> GetNotifications() {
        try {
            List<Notification> newdata = new ArrayList<>();
            mNotification.open();
            Cursor cr = mNotification.getAllEntries();
            cr.moveToFirst();
            while (cr.moveToNext()) {
                Notification notification = new Notification(cr.getInt(0),cr.getString(2),cr.getString(3),cr.getString(1),cr.getString(4), (cr.getInt(5) != 0));
                System.out.println(notification.toString());
                newdata.add(notification);
            }
            Collections.reverse(newdata);
            return newdata;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mNotification.close();
        }
    }


}

package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
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
import java.util.List;

public class NotificationsFragment extends Fragment {

    public RecyclerView notificationsRecyclerView;
    public NotificationAdapter adapter;



    public NotificationsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        List<Notification> notificationList = new ArrayList<>();
        Notification n1 = new Notification("New Update","Master New Version is coming soon","SYSTEM","");
        Notification n2 = new Notification("New Update","Master New Version is coming soon","SYSTEM","");
        Notification n3 = new Notification("New Update","Master New Version is coming soon","SYSTEM","");
        Notification n4 = new Notification("New Update","Master New Version is coming soon","SYSTEM","");
        Notification n5 = new Notification("New Update","Master New Version is coming soon","SYSTEM","");

        notificationList.add(n1);
        notificationList.add(n2);
        notificationList.add(n3);
        notificationList.add(n4);
        notificationList.add(n5);

        notificationsRecyclerView = (RecyclerView) view.findViewById(R.id.notification_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notificationsRecyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationAdapter(notificationList);
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


}

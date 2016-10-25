package com.procasy.dubarah_nocker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.procasy.dubarah_nocker.Adapter.AppointementsAdapter;
import com.procasy.dubarah_nocker.Model.Appointement;
import com.procasy.dubarah_nocker.R;

import java.util.ArrayList;
import java.util.List;

public class AppointementsFragment extends Fragment {

    public RecyclerView appointement_list;
    public AppointementsAdapter adapter;

    public AppointementsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_appointements, container, false);
        List<Appointement> appointementList = new ArrayList<>();
        Appointement n1 = new Appointement("23 JUNE","AM 12:00","$25","Omar Sabbagh","Laptop Maintenance");
        Appointement n2 = new Appointement("4 JULY","PM 04:30","$40","Nabil Saadi","Body Building Instructor");

        appointementList.add(n1);
        appointementList.add(n2);

        appointement_list = (RecyclerView) view.findViewById(R.id.appointement_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        appointement_list.setLayoutManager(layoutManager);
        adapter = new AppointementsAdapter(appointementList);
        appointement_list.setAdapter(adapter);
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
